package com.codebind;

import jdk.nashorn.internal.runtime.NumberToString;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
    public static void main(String[] args) {
        try {
            File myObj = new File("C:\\Users\\logan\\OneDrive\\Bureau\\4Mi\\NoSql\\mini_dataset.csv");
            Scanner myReader = new Scanner(myObj);
            String origin = "";
            String destination = "";
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] splitrecord = data.split(";");
                origin = splitrecord[0];
                destination = splitrecord[1];
                if (CommandDB.NumberToRID(origin)=="Object not found"){
                    String response = CommandDB.CreateProduct(origin);
                    System.out.println(response);
                }
                if(CommandDB.NumberToRID(destination)=="Object not found"){
                    String response = CommandDB.CreateProduct(destination);
                    System.out.println(response);
                }
                String response = CommandDB.CreateLink(origin,destination);
                System.out.println(response);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}