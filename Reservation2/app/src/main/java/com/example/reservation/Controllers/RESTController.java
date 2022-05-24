package com.example.reservation.Controllers;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RESTController {

    private static OutputStream outputStream;
    private static BufferedWriter bufferedWriter;

    public static String sendGet(String getUrl) throws IOException {
        URL url = new URL(getUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
        int code = httpURLConnection.getResponseCode();

        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = in.readLine()) != null) {
                response.append(line);
                break;
            }
            in.close();
            return response.toString();
        } else {
            return "";
        }

    }

    public static String sendPost(String urlPost, String postDataParams) throws IOException {
        URL url = new URL(urlPost);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        System.out.println(postDataParams);
        setConnectionParameters(httpURLConnection, "POST");
        outputStream = httpURLConnection.getOutputStream();
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        bufferedWriter.write(postDataParams);
        bufferedWriter.close();
        outputStream.close();

        int code = httpURLConnection.getResponseCode();
        System.out.println("Response code: " + code);

        if (code == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
                break;
            }
            in.close();
            return response.toString();

        } else {
            return "Error";
        }
    }

    public static String sendPut(String p_url, String postDataParameters) throws Exception {
        URL url = new URL(p_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        setConnectionParameters(connection, "PUT");

        OutputStream out = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.write(postDataParameters);
        writer.flush();
        writer.close();
        out.close();

        int resCode = connection.getResponseCode();
        if (resCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();
            return sb.toString();
        }
        return null;
    }

    private static void setConnectionParameters(HttpURLConnection httpURLConnection, String type) throws ProtocolException {
        httpURLConnection.setRequestMethod(type);
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
    }
}