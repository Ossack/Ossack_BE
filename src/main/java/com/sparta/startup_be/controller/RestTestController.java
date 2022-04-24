package com.sparta.startup_be.controller;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
public class RestTestController {

    @GetMapping("/api")
    public String callApiWithJson() throws IOException {
        StringBuilder result = new StringBuilder();

        String apiUrl = "https://api.odcloud.kr/api/15090955/v1/uddi:10a9cf5c-77d1-4e5d-b7ff-e527e022612e?" +
                "page=1&perPage=10&" +
                "serviceKey=Im9KjxI5WBHvw1dIE4SaEBBWbKKpmzufxmlNaTkGmAHGX2vV8sSXiG%2FCKxwYleIBuPmNpirT8bvO1Elwe381%2FA%3D%3D";
        URL url = new URL(apiUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;


        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine);

        }

        urlConnection.disconnect();

        System.out.println(result);


        return result.toString();

    }
}