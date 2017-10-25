package ru.tsc.util;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class Loader {

    public static void main(String[] args) {
        System.out.println(LdapUtil.getRole("asavchenko"));
        int a;
    }
}