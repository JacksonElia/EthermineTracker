package com.traptricker;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVStorer {
    public static void storeCSVData(String filePath, Map<String, Map<String, String>> minerData) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(filePath));
        // Gets the whole CSV File
        List<String[]> allRows = reader.readAll();
        reader.close();

        CSVWriter writer = new CSVWriter(new FileWriter(filePath));

        // Changes the minerData into an array that can be written to the CSV file, [0] is the worker name
        List<String[]> allMinerData = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : minerData.entrySet()) {
            String[] minerDataArray = {entry.getKey(),
                    entry.getValue().get("Reported Hashrate"),
                    entry.getValue().get("Current Hashrate")
            };
            allMinerData.add(minerDataArray);
        }

        // Goes through each row in the csv file and updates the values in it
        for (String[] row : allRows) {
            boolean rowExists = false;
            for (String[] miner : allMinerData) {
                if (row[0].equals(miner[0])) {
                    String[] minerUpdated = {miner[0],
                            Float.toString(Float.parseFloat(miner[1]) + Float.parseFloat(row[1])),
                            Float.toString(Float.parseFloat(miner[2]) + Float.parseFloat(row[2])),
                    };
                    writer.writeNext(minerUpdated);
                    allMinerData.remove(miner);
                    rowExists = true;
                    break;
                }
            }
            // If the worker isn't mining currently but has in the past and there is data stored for it, this makes sure
            // that the data doesn't get deleted and the writer just moves on to the next line
            if (!rowExists) {
                writer.writeNext(row);
            }
        }

        // Adds new miners to the csv file
        for (String[] miner : allMinerData) {
            writer.writeNext(miner);
        }
        writer.close();
    }
}