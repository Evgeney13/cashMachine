package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Statistic extends Receipt {
    Long data;
    Double sum;

    final String path = "src/data.txt";

    public Statistic(Long data, Double sum) {
        this.data = data;
        this.sum = sum;
    }

    public void save() throws IOException {
        File file = new File(path);
        FileWriter fr = null;
            fr = new FileWriter(file,true);
            fr.write(data.toString() + "," + sum.toString());
            fr.append('\n');
            fr.flush();
    }
}
