package main.java;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AasGeneretor {


    public ArrayList<String> readerEdges(File fileTSV,int start){
        ArrayList<String>  result= new ArrayList<>();
        String lista_amminoacidi = "";
        String lista_legami="";
        int cont =0;
      //  System.out.println("FILE: "+fileTSV);
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTSV))) {
            String line;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    String[] lineItems = line.split(":");
                    lista_amminoacidi= lista_amminoacidi+this.parser(lineItems[3].substring(0,3))
                            + this.parser(lineItems[7].substring(0,3));
                    lista_legami = lista_legami+ "("+((Integer.parseInt(lineItems[1])-start)+1)+","
                            +((Integer.parseInt(lineItems[5])-start)+1)+");" ;
                    //System.out.println("aggiungo un nuovo amminoacido"+lista_amminoacidi);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong AAS errore :"+e);
        }

        result.add(lista_amminoacidi);
        result.add(lista_legami);
        return result;
    }

    private String parser(String s){
        return switch (s) {
            case "ALA" -> "A";
            case "CYS" -> "C";
            case "ASP" -> "D";
            case "GLU" -> "E";
            case "PHE" -> "F";
            case "GLY" -> "G";
            case "HIS" -> "H";
            case "ILE" -> "I";
            case "LYS" -> "K";
            case "LEU" -> "L";
            case "MET" -> "M";
            case "ASN" -> "N";
            case "PRO" -> "P";
            case "GLN" -> "Q";
            case "ARG" -> "R";
            case "SER" -> "S";
            case "THR" -> "T";
            case "VAL" -> "V";
            case "TRP" -> "W";
            case "TYR" -> "Y";
            default -> "";
        };
    }

    public void buildAASFile(String lista_amminoacidi, String lista_legami,String path,String pdb){
        //try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("legami.aas.txt")))) {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(path+"/aasFile/legami"+pdb+".aas.txt")))) {
            //scrivo nel file le righe interessate
            System.out.println(lista_amminoacidi);
            writer.println(lista_amminoacidi);
            writer.printf(lista_legami);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}