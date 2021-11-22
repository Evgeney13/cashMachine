package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Goods {
      String position;
      double price;
      double amount;
      ArrayList<Goods> goods;


      public Goods (String position, double price)
      {
           this.position=position;
           this.price=price;
      }

      public Goods() {

      }





public void show(){
            for (int i=0; i<goods.size(); i++){
                  System.out.println(goods.get(i).position+" "+goods.get(i).price);
            }
}


public void updatePrices(String position, String price) {
      Boolean found = false;
      for (int i=0; i<goods.size(); i++) {
            if (Objects.equals(goods.get(i).position, position)) {
                  found = true;
                  goods.add(new Goods(position, Double.parseDouble(price)));
                  goods.remove(i);
                  break;
            }
         }
      if (found) {
            System.out.println("Position "+position+" has been updated. New price is "+price);
      }
      else
      {  System.out.println("Warn! Position "+position+" is not found. New position will be created");
      goods.add(new Goods(position, Double.parseDouble(price)));}
     }


public void removePrices(String position) {
      Boolean found = false;
      for (int i = 0; i < goods.size(); i++) {
            if (Objects.equals(goods.get(i).position, position)) {
                  found = true;
                  goods.remove(i);
                  break;
            }
      }
      if (found) {
            System.out.println("Position "+position+" has been removed");
      }
      else
            System.out.println("Warn! Position "+position+" is not found. New position will be created");
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
            BufferedReader br = new BufferedReader(Files.newBufferedReader(Path.of("src/Res/Goods.txt")));
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
