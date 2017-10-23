package ru.tsc.rest;

import com.unboundid.util.Base64;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tsc.util.LdapUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import static ru.tsc.util.ResponseUtill.getResponseWithHeaderBad;
import static ru.tsc.util.ResponseUtill.getResponseWithHeaderOk;

/**
 * @author Terekhin Nikita
 **/

@RestController
@RequestMapping
public class SecurityTemplate {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object authInWebapp(@RequestBody Object request) {
        Map params = (LinkedHashMap) request;
        Map<String, Object> response;
        try {
            if (checkAuthentication(params.get("username").toString(), params.get("password").toString())) {
                response = getResponseWithHeaderOk();
            }else {
                response = getResponseWithHeaderBad();
            }
            return response;

        } catch (IOException e) {
            return getResponseWithHeaderBad();
        }
    }

    public static boolean checkAuthentication(String username, String password) throws IOException {
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
