package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private List<String> productString = new ArrayList<String>();
    private final String fileLocation;

    public FileReader(String fileLocation){
        this.fileLocation = fileLocation;
    }

    public List<String> productList() {
        File dataFile = new File(fileLocation);

        Scanner dataInput = null;
        try {
            dataInput = new Scanner(dataFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        while (dataInput.hasNextLine()) {
            productString.add(dataInput.nextLine());
        }
        //System.out.println(productString.toString());
        return productString;
    }

}
