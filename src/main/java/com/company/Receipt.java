package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Receipt extends Goods {
    double amount;
    ArrayList<Receipt> receipts;

    public Receipt() {
    }

    public Receipt(String position, double price, double amount) {
        this.position = position;
        this.price = price;
        this.amount = amount;
    }

    public ArrayList<Receipt> newSell() throws IOException {
        initialize();
        ArrayList<Receipt> receipts = new ArrayList<Receipt>();
        this.receipts=receipts;
        return receipts;
    }

    public void add() throws IOException {

        System.out.println("Input position");

        String position = readString();
        double price=0;
        double amount=0;
        int index = (findPosition(position));
        int indexReceipt = findReceipt(position);
        Receipt receipt = new Receipt();
        if (indexReceipt == -1) {
            if (index != -1) {
                System.out.println("Input amount");
                amount = readValue();
                price = goods.get(index).price;
                receipt = new Receipt(position, price, amount);
            } else {
                System.out.println("Position does not exist. Free position will be added");
                System.out.println("Input price");
                price = readValue();
                System.out.println("Input amount");
                amount = readValue();
                receipt = new Receipt(position, price, amount);
            }
            receipts.add(receipt);
        }
        else
        {
            System.out.println("Input amount");
            amount = readValue();
            receipt = new Receipt(position, receipts.get(indexReceipt).price, amount+receipts.get(indexReceipt).amount);
            receipts.add(receipt);
            receipts.remove(indexReceipt);
        }
    }

    public int findReceipt(String position) {
        int index = -1;
        for (int i = 0; i < receipts.size(); i++) {
            if (Objects.equals(receipts.get(i).position, position)) {
                index = i;
            }}
            return index;

    }

    public double sum(){
        double sum = 0;
                 for (int i = 0; i < receipts.size(); i++) {
                sum = sum + receipts.get(i).price*receipts.get(i).amount;
            }
        return sum;
    }

    public void remove(){
        System.out.println("Input position");
           String position = readString();
        double amount =0;
        int index = (findReceipt(position));
        if (index!=-1)
        {
            System.out.println("Input amount");
            amount=readValue();
            if ((receipts.get(index).amount-amount)<=0)
            {
                receipts.remove(index);
            }
            else
            {
                receipts.add(new Receipt(position,receipts.get(index).price,receipts.get(index).amount-amount));
                receipts.remove(index);
            }
            showReceipt();
        }
        else
        {
            System.out.println("Position you have requested to remove is absent in the receipt");
        }
    }

    public String makeRecept(){
        String output="Company name\n---------\nposition || price || amount\n";
        for (int i = 0; i < receipts.size(); i++) {
            output= output +(receipts.get(i).position + " " + receipts.get(i).price + " " + receipts.get(i).amount+ "\n");
        }
        Date date = new Date();
        output=output+"---------\nTotal "+sum()+" USD\n---------\n"+date.toString();
        return output;
    }

    public void showReceipt(){
        System.out.println(makeRecept());
    }



     public void saveReceipt() throws IOException {
         long unixTime = Instant.now().getEpochSecond();
         File f = new File(String.valueOf(unixTime));
         f.createNewFile();
         BufferedWriter bw = new BufferedWriter(new FileWriter(f));
         bw.write(makeRecept());
         bw.close();
         System.out.println("Recept have been saved");
         System.out.println("send to email? Yes|No");
         String option = readString();
         if (option.equals("yes"))
         {sendReceipt();}
         Statistic st = new Statistic(unixTime, sum());
         st.save();
         newSell();
     }

     public void sendReceipt() {
         String body = makeRecept();
         System.out.println("Input email");
         String email = readString();
         SendEmail sendEmail = new SendEmail();
         sendEmail.sendAttachment(body,email);
     }


}
