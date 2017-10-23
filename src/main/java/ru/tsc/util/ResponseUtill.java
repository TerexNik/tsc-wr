package ru.tsc.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Terekhin Nikita
 **/
public class ResponseUtill {
    public static Map<String, Object> getResponseWithHeaderBad() {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("status", "ok");
        header.put("auth", false);
        response.put("header", header);
        return response;
    }

    public static Map<String, Object> getResponseWithHeaderOk() {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("status", "ok");
        header.put("auth", true);
        response.put("header", header);
        return response;
    }
}
