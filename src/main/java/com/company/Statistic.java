package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Statistic extends Receipt {
    Long data;
    Double sum;
    ArrayList<Statistic> statistics;

    final String path = "src/data.txt";

    public Statistic(Long data, Double sum) {
        this.data = data;
        this.sum = sum;
    }

    public Statistic() {

    }

    public void save() throws IOException {
        File file = new File(path);
        FileWriter fr = null;
            fr = new FileWriter(file,true);
            fr.write(data.toString() + "," + sum.toString());
            fr.append('\n');
            fr.flush();
    }

    public ArrayList<Statistic>load() throws IOException {
        ArrayList<Statistic> statistics = new ArrayList<Statistic>();
        BufferedReader br = new BufferedReader(Files.newBufferedReader(Path.of(path)));
        String line = br.readLine();
        while (line != null)
        {
            statistics.add(extractStatistic(line));
            line=br.readLine();
        }
        br.close();
        this.statistics=statistics;
        return statistics;
    }

    public Statistic extractStatistic(String input)
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

        return new Statistic(Long.parseLong(position), price);
    }

    public void showAll() throws IOException {
        load();
        for (int i=0; i<statistics.size(); i++){
            System.out.println(formatDate(statistics.get(i).data)+" "+statistics.get(i).sum);
        }
    }

    public void showStat() throws IOException, ParseException {
        load();
        System.out.println("Введите дату начала в формате dd-MM-yyyy: ");
        Date startDate = readDate();
        System.out.println("Введите дату конца в формате dd-MM-yyyy: ");
        Date startEndDate = readDate();
        double total = 0;
        ArrayList<Long> unicDates = new ArrayList<>();
        ArrayList<Statistic> report = new ArrayList<>();
        for (int i=0; i<statistics.size(); i++){
            if (statistics.get(i).data >= startDate.getTime()/1000 && statistics.get(i).data < startEndDate.getTime()/1000)
            {
                if (!unicDates.contains(statistics.get(i).data)){
                    unicDates.add(statistics.get(i).data);
                    report.add(statistics.get(i));
                } else {
                    report.add(statistics.get(i));
                }
                System.out.println( formatDate(statistics.get(i).data)+" "+statistics.get(i).sum);
                total += statistics.get(i).sum;
            }

        }
        System.out.println("Total: " + total);
    }
}
