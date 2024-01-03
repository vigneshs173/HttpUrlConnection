package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Header {
    public static void main(String[] args) {
        try {
            String firstName = "77";
            String email = "uusd.dass@example.com";

            String encodedFirstName = base64Encode(firstName);
            String encodedEmail = base64Encode(email);

            String apiURL = "http://localhost:8080/student/studentHeader";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("firstName", encodedFirstName);
            con.setRequestProperty("email", encodedEmail);

            con.setDoOutput(true);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONObject jsonResponse = new JSONObject(response.toString());

                String decodedFirstName = base64Decode(jsonResponse.getString("firstName"));
                String decodedEmail = base64Decode(jsonResponse.getString("email"));

                jsonResponse.put("firstName",decodedFirstName);
                jsonResponse.put("email",decodedEmail);
                jsonResponse.remove("lastName");
                jsonResponse.remove("phoneNumber");
                jsonResponse.remove("course");
                jsonResponse.remove("isActive");
                jsonResponse.remove("yearOfStudy");
                jsonResponse.remove("department");

                System.out.println("Response from server: " + jsonResponse.toString(4));
            }

            con.disconnect();

        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("IOException occurred. Please check the server availability.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred.");
        }
    }

    private static String base64Encode(String input) {
        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes);
    }

    private static String base64Decode(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        String decodedValue = new String(decodedBytes, StandardCharsets.UTF_8);

        return decodedValue;
    }
}
