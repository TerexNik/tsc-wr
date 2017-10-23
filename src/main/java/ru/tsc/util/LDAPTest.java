package ru.tsc.util;

import org.codehaus.jackson.map.ObjectMapper;
import ru.tsc.rest.SecurityTemplate;

import java.io.IOException;
import java.util.LinkedHashMap;

public class LDAPTest {

    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = "{\"username\":\"nterekhin\",\"password\":\"WWJqZjEyMzQ=\"}";
            Object obj = mapper.readValue(json, Object.class);
            LinkedHashMap params = (LinkedHashMap) obj;
            boolean is = SecurityTemplate.checkAuthentication(params.get("username").toString(), params.get("password").toString());
            System.out.println(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
