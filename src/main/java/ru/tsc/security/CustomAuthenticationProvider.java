package ru.tsc.security;

import com.unboundid.util.Base64;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.tsc.util.LdapUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * @author Terekhin Nikita
 **/

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            if (checkAuthentication(username, password)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<GrantedAuthority>());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public boolean checkAuthentication(String username, String password) throws IOException {
        try {
            String distinguishedname = LdapUtil.getAttributes(username).get("distinguishedname");
            return LdapUtil.checkUser(distinguishedname, Base64.decodeToString(password));
        } catch (NullPointerException e) {
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
