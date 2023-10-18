package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EdgesSelector {

    /*
    public static void selector(String legame, File fileRing,String path){
        boolean first= true;
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(path+"/"+fileRing.getName()+"_"+legame)))) {
            try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileRing))) {

                String line;
                while ((line = TSVReader.readLine()) != null) {
                    if(first) {
                        writer.println(line);
                        first =false;
                    } else {
                        String[] lineItems = line.split(":");
                        if (lineItems[3].contains(legame))  writer.println(line);
                    }
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("ERRORE IN TAGLIA RING:"+e);
            }
        }catch (Exception e) {
            System.out.println("Something went wrong RING " + e);
        }
    }*/

    /**
     * This method creates a file containing only the selected bonds
     * @param bondList is the bond list
     * @param fileRing is the file we are analyzing
     * @param path is the path where the file will be stored
     */
    public static void selector(ArrayList<String> bondList, File fileRing,String path){
        boolean first= true;


        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(path+"/"+fileRing.getName()+"_selBonds")))) {
            try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileRing))) {
                String line;
                while ((line = TSVReader.readLine()) != null) {
                    if(first) {
                        writer.println(line);
                        first = false;
                    } else {
                        String[] lineItems = line.split(":");
                        for(String bond : bondList){
                            if(lineItems[3].contains(bond)) writer.println(line);
                        }
                    }
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("ERRORE IN TAGLIA RING:"+e);
            }
        }catch (Exception e) {
            System.out.println("Something went wrong RING " + e);
        }
    }

}
