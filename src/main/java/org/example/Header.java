package org.example;

import org.json.JSONObject;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class Header {
    public static final String SECRET_KEY = "igrsdjkkeuhdiwdh";
    public static void main(String[] args) {
        try {
            String firstName = "173";
            String email = "vicky@gamil.com";

            String encodedFirstName = aesEncrypt(firstName);
            String encodedEmail = aesEncrypt(email);

            String decodedFirstName = aesDecrypt(encodedFirstName);
            String decodedEmail = aesDecrypt(encodedEmail);


            String apiURL = "http://localhost:8080/student/studentHeader";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("firstName", decodedFirstName);
            con.setRequestProperty("email", decodedEmail);

            con.setDoOutput(true);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                JSONObject jsonResponse = new JSONObject(response.toString());

//                String decodedFirstName = base64Decode(jsonResponse.getString("firstName"));
//                String decodedEmail = base64Decode(jsonResponse.getString("email"));

                String firstNameData = jsonResponse.getString("firstName");
                String emailData = jsonResponse.getString("email");

                jsonResponse.put("firstName", firstNameData);
                jsonResponse.put("email", emailData);

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

    private static String aesEncrypt(String input) throws Exception {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

        System.out.println("Encrypted data: " + encryptedString);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String aesDecrypt(String input) throws Exception {

        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
        String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);

        System.out.println("Decrypted data: " + decryptedString);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
