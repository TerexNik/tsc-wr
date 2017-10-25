package ru.tsc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Terekhin Nikita
 **/

public class ResponseUtil {
    public static Map<String, Object> getResponseWithHeaderBad() {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("status", "ok");
        header.put("auth", false);
        response.put("header", header);
        return response;
    }

    public static String getJsonResponseWithHeaderOk() {
        Map<String, Object> response = getResponseWithHeaderOk();
        ObjectMapper oM = new ObjectMapper();
        try {
            return oM.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return getResponseWithHeaderBad().toString();
    }

    public static String getJsonResponseWithHeaderBad() {
        Map<String, Object> response = getResponseWithHeaderBad();
        ObjectMapper oM = new ObjectMapper();
        try {
            return oM.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return getResponseWithHeaderBad().toString();
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
