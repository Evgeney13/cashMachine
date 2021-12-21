package com.company;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, ParseException {
        Goods goods = new Goods();
        Statistic statistic = new Statistic();
        Receipt receipt = new Receipt();
        goods.initialize();
        receipt.newSell();
        ReadTelegram readTelegram = new ReadTelegram();
        readTelegram.start();


        Scanner scanner = new Scanner(System.in);
                    String input="";
                    while (input != "exit") {
                        input = scanner.next();
                        switch (input){
                            case "show":
                                goods.show();
                                break;
                            case "findPosition":
                                System.out.println("input position");
                                String position = scanner.next();
                                System.out.println(goods.findPosition(position));
                                break;
                            case "updatePrice":
                                System.out.println("input position");
                                position = scanner.next();
                                System.out.println("input price");
                                String price = scanner.next();
                                goods.updatePrices(position,price);
                                break;
                            case "removePrice":
                                System.out.println("input position");
                                position = scanner.next();
                                goods.removePrices(position);
                                break;
                            case "commit":
                                System.out.println("prices saved");
                                goods.commit();
                                goods.show();
                                break;
                            case "newSell":
                                receipt.newSell();
                                break;
                            case "add":
                                receipt.add();
                                System.out.println("Receipt has been updated");
                                receipt.showReceipt();
                                break;
                            case "showReceipt":
                                receipt.showReceipt();
                                break;
                            case "close":
                                receipt.saveReceipt();
                                break;
                            case "remove":
                                receipt.remove();
                                break;
                            case "exit":
                                input="exit";
                                break;
                            case "statistic":
                                statistic.showAll();
                                break;
                            case "report":
                                statistic.showStat();
                                break;
                            default:
                                System.out.println("unknown command");
            }
        }

    }
}
