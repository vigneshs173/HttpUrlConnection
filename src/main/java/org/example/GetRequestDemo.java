package org.example;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequestDemo {

    public static void main(String[] args) {
        try {

            URL url = new URL("http://localhost:8080/student/getAll");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Accept", "application/json");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                String response = "";
                String res;
                while ((res = br.readLine()) != null) {
                    response += res.trim();
                }

                String test=response.toString();

                JSONObject jsonObject=new JSONObject(test);
                String actualJson=jsonObject.toString(4);
                System.out.println("Response  :"+ actualJson);
            }
            con.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
