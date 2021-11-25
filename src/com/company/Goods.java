package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Goods {
      String position;
      double price;

      ArrayList<Goods> goods;
      public static final String ANSI_RED = "\u001B[31m";
      public static final String ANSI_RESET = "\u001B[0m";

      public static final String registerAddress = "src/Res/Goods.txt";
      public static final String receiptsPath = "src/Res/Goods.txt";

      public Goods (String position, double price)
      {
           this.position=position;
           this.price=price;
      }

      public Goods() {

      }

//https://stackoverflow.com/questions/4246077/matching-numbers-with-regular-expressions-only-digits-and-commas/4247184#4247184
public double readValue(){
      Scanner scanner = new Scanner(System.in);
      Pattern pattern = Pattern.compile("^([+-]?)(?=\\d|\\.\\d)\\d*(\\.\\d*)?([Ee]([+-]?\\d+))?$");
      String input="";
      while (true)
       {
            input = scanner.next();
            if (pattern.matches("^([+-]?)(?=\\d|\\.\\d)\\d*(\\.\\d*)?([Ee]([+-]?\\d+))?$",input)){
                  break;
            }
            else {
                  System.out.println("the value seems to be incorrect. Please re-try input");
            }
      }

      double output = Double.parseDouble(input);
      return output;
}

public String readString(){
      Scanner scanner = new Scanner(System.in);
      String input=scanner.next();
      String output=input.toLowerCase();
      return output;
}

public void show(){
            for (int i=0; i<goods.size(); i++){
                  System.out.println(goods.get(i).position+" "+goods.get(i).price);
            }
}



public int findPosition(String position) {
            int index = -1;
      for (int i=0; i<goods.size(); i++) {
            if (Objects.equals(goods.get(i).position, position)) {
                  index=i;
            }
      }
      return index;
}

public void updatePrices(String position, String price) {
      int index=findPosition(position);

      if (index!=-1) {
            goods.add(new Goods(position, Double.parseDouble(price)));
            goods.remove(index);
            System.out.println("Position "+position+" has been updated. New price is "+price);
      }
      else
      {  System.out.println("Warn! Position "+position+" is not found. New position will be created");
      goods.add(new Goods(position, Double.parseDouble(price)));}
     }


public void removePrices(String position) {
      int index=findPosition(position);

      if (index!=-1) {
            goods.remove(index);
            System.out.println("Position "+position+" has been removed");
      }
      else
            System.out.println("Warn! Position "+position+" is not found. Nothing to remove");
      }

public void commit() throws IOException {
      File f = new File("src/Res/Goods.txt");
      BufferedWriter bw = new BufferedWriter(new FileWriter(f));
      for (int i = 0; i < goods.size(); i++) {
            bw.write(goods.get(i).position+","+goods.get(i).price+"\n");
      }
      bw.close();
}

      public  ArrayList<Goods> initialize() throws IOException {
            ArrayList<Goods> goods = new ArrayList<Goods>();
            BufferedReader br = new BufferedReader(Files.newBufferedReader(Path.of(registerAddress)));
            String line = br.readLine();
            while (line != null)
            {
                  goods.add(extractGood(line));
                  line=br.readLine();
            }
            br.close();
            this.goods=goods;
            return goods;
      }


public Goods extractGood(String input)
{
      String position = "";
      int stop = 0;
      String priceString = "";
      for (int i =0; i<input.length(); i++)
      {
            if (Character.toString(input.charAt(i)).equals(","))
            {
                  break;
            }
            position = position + input.charAt(i);
      stop = i + 2;
      }
      for (int i = stop; i<input.length(); i++)
      {
            priceString = priceString + input.charAt(i);
      }
      price = Double.parseDouble(priceString);

return new Goods(position, price);

}
}
