package ru.tsc.util;

import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashMap;
import java.util.Hashtable;

@Component
public class LdapUtil {

    private static Hashtable makeConfig(String distinguishedname, String password) {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://SDC.tsc.ts:389/");
        env.put(Context.SECURITY_PRINCIPAL, distinguishedname);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        return env;

    }

    public static boolean checkUser(String distinguishedname, String password) {
        HashMap<String,String> attr = new HashMap<String,String>();
        Hashtable env = makeConfig(distinguishedname, password);
        LdapContext ctx = null;
        try {
            ctx = new InitialLdapContext(env, null);
            return true;
        } catch (NamingException e) {
        } finally {
            try {
                ctx.close();
            } catch (NamingException e) {
                return false;
            } catch (NullPointerException ex) {
                return false;
            }
        }
        return false;
    }

    public static String getRole(String username) {
        HashMap<String,String> attr = new HashMap<String,String>();
        Hashtable env = makeConfig("CN=BPM User,OU=SpecialAccount,DC=tsc,DC=ts", "!1@n#L$a%m");
        String[] returnedAtts={"memberOf"};
        String searchFilter = String.format("(sAMAccountName=%s)",username);
        getDataFromLdap(searchFilter, attr, env, returnedAtts);
        return attr.toString().split("CN=")[1].split(",")[0];
    }


    public static HashMap<String,String> getAttributes(String login) {
        HashMap<String,String> attr = new HashMap<String,String>();
        Hashtable env = makeConfig("CN=BPM User,OU=SpecialAccount,DC=tsc,DC=ts", "!1@n#L$a%m");
        String[] returnedAtts={"title", /*"department",*/ "st", "userprincipalname", "distinguishedname"};
        String searchFilter = String.format("(sAMAccountName=%s)",login);
        getDataFromLdap(searchFilter, attr, env, returnedAtts);

        return attr;
    }

    private static void getDataFromLdap(String searchFilter, HashMap<String, String> attr, Hashtable env, String[] returnedAtts) {
        LdapContext ctx = null;
        String searchBase = "OU=Production,OU=TSC,OU=Business,DC=tsc,DC=ts";

        try {
            ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();

            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                attr.clear();
                SearchResult sr = answer.next();
                Attributes attrs = sr.getAttributes();
                for (String returnedAtt : returnedAtts) {
                    Attribute atr = attrs.get(returnedAtt);
                    attr.put(returnedAtt, atr != null ? atr.get(0).toString() : "");
                }

                HashMap<String,String> attr2 = new HashMap<>();


                String[] deps = attr.get("distinguishedname") == null ? new String[]{} : attr.get("distinguishedname").replace(",OU=Production,OU=TSC,OU=Business,DC=tsc,DC=ts","").split(",");

                if (!(deps.length == 0 || deps[0].trim().length() == 0)) {
                    StringBuilder sfsb = new StringBuilder();
                    for (String dep: deps) {
                        if (dep.startsWith("CN=")) {
                            continue;
                        }
                        searchFilter = String.format("(%s)", dep);
                        getDataFromLdap(searchFilter, attr2, env, new String[]{"st"});
                        sfsb.append(attr2.get("st")).append(" / ");
                    }
                    if (sfsb.length() > 0) sfsb.setLength(sfsb.length() - 3);


                    attr.put("depnames", sfsb.toString());
                }

            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

    }

}
