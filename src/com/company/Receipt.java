package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
        initialize();
        System.out.println("Input position");
        Scanner scanner = new Scanner(System.in);
        String position = scanner.next();

        int index = (findPosition(position));
        Receipt receipt = new Receipt();
        if (index != -1) {
            System.out.println("Input amount");
            amount = Double.parseDouble(scanner.next());
            price = goods.get(index).price;
            receipt = new Receipt(position, price, amount);
        } else {
            System.out.println("Position does not exist. Free position will be added");
            System.out.println("Input price");
            price = Double.parseDouble(scanner.next());
            System.out.println("Input amount");
            amount = Double.parseDouble(scanner.next());
            receipt = new Receipt(position, price, amount);
        }
        receipts.add(receipt);
    }

    public void showReceipt() throws NullPointerException {
        System.out.println("Company name");
        System.out.println("---------");
        System.out.println("position || price || amount");

        try {
            for (int i = 0; i < receipts.size(); i++) {
                System.out.println(receipts.get(i).position + " " + receipts.get(i).price + " " + receipts.get(i).amount);
            }
        }
        catch (NullPointerException exception)
        {
            System.out.println("Please use newSell before");
        }
        System.out.println("---------");
        Date date = new Date();
        System.out.println(date.toString());

    }
}
