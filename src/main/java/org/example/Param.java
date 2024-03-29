package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Param {

    public static void main(String[] args) {
        try {

            String studentId = "90180";
            String firstName = "FIRE";
            String email = "aaa.dass@example.com";

            String apiURL = "http://localhost:8080/student/studentParam?";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", "application/json");

            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                String param = "studentId=" + studentId + "&firstName=" + firstName + "&email=" + email;
                byte[] input = param.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String response = "";
                String res;
                while ((res = br.readLine()) != null) {
                    response += res.trim();
                }
                String test = response.toString();
                JSONObject jsonObject = new JSONObject(test);
                String actualJson = jsonObject.toString(4);
                System.out.println("Response from server: " + actualJson);

                int statusCode = con.getResponseCode();
                String statusMessage = con.getResponseMessage();
                System.out.println("Response Code : " + statusCode + " " + statusMessage);

            }



            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
