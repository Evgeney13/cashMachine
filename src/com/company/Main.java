package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        Goods goods = new Goods();
        goods.initialize();



        Scanner scanner = new Scanner(System.in);
                    String input="";
                    while (input != "exit") {
                        input = scanner.next();
                        switch (input){
                            case "show":
                                goods.show();
                                break;
                            case "updatePrice":
                                System.out.println("input position");
                                String position = scanner.next();
                                System.out.println("input price");
                                String price = scanner.next();
                                goods.updatePrices(position,price);
                                break;
                            case "removePrice":
                                System.out.println("input position");
                                position = scanner.next();
                                goods.removePrices(position);
                            case "commit":
                                System.out.println("prices saved");
                                goods.commit();
                                goods.show();
                                break;
                            case "exit":
                                input="exit";
                                break;
                            default:
                                System.out.println("unknown command");
            }
        }

    }
}
