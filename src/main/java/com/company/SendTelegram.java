package com.company;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class SendTelegram {

    String urlString = "https://api.telegram.org/bot5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY/sendMessage?chat_id=1962387150&text=";
    String apiToken = "5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY";

//https://api.telegram.org/bot5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY/getUpdates?offset=-1
    public void send(String receipt, String chatId) throws IOException {
        URL yahoo = new URL(urlString+URLEncoder.encode(receipt, StandardCharsets.UTF_8));
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
        }


