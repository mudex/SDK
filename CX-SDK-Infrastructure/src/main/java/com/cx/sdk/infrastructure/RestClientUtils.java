package com.cx.sdk.infrastructure;

import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestClientUtils {

    public static <ResponseObj> ResponseObj parseJsonFromResponse(ClientResponse response, Class<ResponseObj> dtoClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(createStringFromResponse(response).toString(), dtoClass);
    }

    private static StringBuilder createStringFromResponse(ClientResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntityInputStream()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result;
    }
}