package com.traptricker;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class CSVStorer {
    public static void csvWriter(String filePath) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(filePath));

        writer.writeNext(new String[]{"123"});

        writer.close();
    }

}
