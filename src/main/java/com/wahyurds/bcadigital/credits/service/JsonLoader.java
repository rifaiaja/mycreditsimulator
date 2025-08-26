package com.wahyurds.bcadigital.credits.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wahyurds.bcadigital.credits.model.JsonResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String runFromLoadJsonFromUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        BufferedReader in;
        if (status >= 200 && status < 400) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            content.append(line).append("\n");
        }
        in.close();
        con.disconnect();
        return content.toString();
    }

    public static JsonResponse fromJsonString(String json) throws IOException {
        return MAPPER.readValue(json, JsonResponse.class);
    }
}
