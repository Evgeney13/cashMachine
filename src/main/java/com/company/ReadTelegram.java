package com.company;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;


public class ReadTelegram extends Thread {

    //String urlString = "https://api.telegram.org/bot5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY/sendMessage?chat_id=1962387150&text=";
    String urlString = "https://api.telegram.org/bot5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY/getUpdates?offset=-1";
    String urlStringSend = "https://api.telegram.org/bot5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY/sendMessage?";
    String apiToken = "5003550981:AAEZfM54g1ro0kPuh5Zc-VEuYtdPETtRYOY";

    int chat_id;
    boolean processed;
    int message_id;
    ArrayList<ReadTelegram> chats = new ArrayList<>();

    public ReadTelegram() {
    }

    public ReadTelegram(int chat_id, int message_id, boolean processed) {
        this.chat_id=chat_id;
        this.processed=processed;
        this.message_id=message_id;
    }

    @Override
    public void run()	//Этот метод будет выполнен в побочном потоке
    {

        while (true) {
            try {
                System.out.println("api response:");
                checkMessages();
                System.out.println("internally created messages:");
                showMessages();
                System.out.println("reply to message");
                reply("this is a text");
                clearProcessed();
                System.out.println("updated messages array");
                showMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
                        try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void checkMessages() throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String inputLine;
        String jsonString="";

        while ((inputLine = in.readLine()) != null)
            jsonString=jsonString+inputLine;
        in.close();

        // {"ok":true,"result":[{"update_id":28900873,"message":{"message_id":17,"from":{"id":1962387150,"is_bot":false,"first_name":"Eugen","language_code":"ru"},"chat":{"id":1962387150,"first_name":"Eugen","type":"private"},
        //  "date":1639596533,"text":"/receipt","entities":[{"offset":0,"length":8,"type":"bot_command"}]}}]}
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String ok = jsonObject.get("ok").getAsString();

        System.out.println(jsonString);

        jsonElement = jsonObject.get("result");
        JsonArray results = jsonElement.getAsJsonArray();
        for (JsonElement result : results) {

            JsonObject message = (JsonObject) result.getAsJsonObject().get("message");
            String text = message.get("text").getAsString();
            int message_id = message.get("message_id").getAsInt();
            JsonObject chat = (JsonObject) message.getAsJsonObject().get("chat");
            int chat_id = chat.get("id").getAsInt();

            if (text.equals("/receipt")) {
                int index=findChat(message_id);
                if (index==-1) {
                    chats.add(new ReadTelegram(chat_id, message_id, false));

                }

            }
        }
    }


     public void clearProcessed() {
        int max=findMax();
         for (int i = 0; i < chats.size(); i++) {
             if (chats.get(i).chat_id<max && chats.get(i).processed){
                 chats.remove(i);
             }
         }
     }

     public int findMax(){
         int max=0;
         for (int i = 0; i < chats.size(); i++) {
             if(chats.get(i).chat_id>max)
             {max=chats.get(i).chat_id;
             }
         }
         return max;
     }

     public  void reply(String text) throws IOException {
         for (int i = 0; i < chats.size(); i++) {
             if (chats.get(i).processed)
             {}
             else
             {send(text, String.valueOf(chats.get(i).chat_id));
             }
             chats.get(i).processed=true;
             System.out.println(chats.get(i).processed);
         }
     }

    public void showMessages(){
        for (int i = 0; i < chats.size(); i++) {
            System.out.println("chat_id: "+chats.get(i).chat_id+" message_id "+chats.get(i).message_id);
        }
    }

    public int findChat(int message_id) {
        int index = -1;
        for (int i = 0; i < chats.size(); i++) {
            if (Objects.equals(chats.get(i).message_id, message_id)) {
                index = i;
            }}
        return index;
    }



    public void send(String text, String chatId) throws IOException {
        URL yahoo = new URL(urlStringSend+"chat_id="+chatId+"&text="+URLEncoder.encode(text, StandardCharsets.UTF_8));
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


