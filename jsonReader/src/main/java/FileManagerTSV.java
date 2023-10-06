package main.java;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManagerTSV {

    /**
     * Funzione per ottenere start end di un pdb passato
     * @param fileTsv file TSV da analizzare
     * @param pdb pdb da cui vogliamo ottenere start e end
     * @return array contenente in posizione 0 lo start, e in 1 end del pdb passato tra i parametri
     */
    public Integer[] getStartEndPdb(String fileTsv,String pdb) {
        int minStart=-1, maxEnd=-1;
        String line;
        int cont =0 ;

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTsv))) {
            while ((line = TSVReader.readLine()) != null) {
                if(cont == 0) cont = 1;
                else {
                    String[] lineItems = line.split("\t");
                    if(pdb.compareTo(lineItems[2]) == 0) {
                        if (minStart == -1 && maxEnd == -1) {
                            minStart = Integer.parseInt(lineItems[4]);
                            maxEnd = Integer.parseInt(lineItems[5]);
                        }else {
                            if (Integer.parseInt(lineItems[4]) < minStart) minStart = Integer.parseInt(lineItems[4]);
                            if (Integer.parseInt(lineItems[5]) > maxEnd )  maxEnd = Integer.parseInt(lineItems[5]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        Integer[] result = new Integer[2];
        result[0] = minStart;
        result[1] = maxEnd;
        return (result);
    }

    /**
     * Funzione per ottenere start end di un pdb passato
     * @param fileJSON file JSON da analizzare
     * @param pdb pdb da cui vogliamo ottenere start e end
     * @return array contenente in posizione 0 lo start, e in 1 end del pdb passato tra i parametri
     */
    public JSONObject getPdbObject(String fileJSON, String pdb){
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileJSON);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) { //scorro tutti i pdb appartenenti file JSON
                JSONObject jsonObject = (JSONObject) obj;
                if(((String) jsonObject.get("repeatsdb_id")).compareTo(pdb) == 0) { //ottengo start e end del pdb passato
                    return jsonObject; //oggetto pdb trovato
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; //pdb non trovato
    }

    /**
     * Metodo per leggere il file pdb e tagliare le informazioni superflue
     * @param filePDB nome del file da leggere
     * @param start inizio dell'intervallo di interesse
     * @param end fine dell'intervallo di interesse
     * @param pdb pdb interessato
     * @return regione di interesse
     */
    public ArrayList<String[]> pdbReaderReduce(File filePDB, int start, int end, String pdb, String catena) {
        ArrayList<String[]> Data = new ArrayList<>(); //Arraylist delle righe lette dal file tsv
        String line;
        int cont = 0;

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(filePDB))) { // leggo e registro le righe del file pdb

            while ((line = TSVReader.readLine()) != null) {
                if(cont == 0) cont = 1;
                else {

                    line = line.replaceAll("\\s+", " "); //sostituisco spazi vuoti con un solo spazio vuoto
                    String[] lineItems = line.split(" "); //splitto stringa in un array
                    if(lineItems[0].compareTo("ATOM") == 0){
                        if (Integer.parseInt(lineItems[5]) >= start && Integer.parseInt(lineItems[5]) <= end && (lineItems[4].compareTo(catena) == 0))
                            Data.add(lineItems);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }

    /**
     * Metodo che crea il nuovo file PDB da dare in input a ring
     * @param fileData righe da scrivere nel nuovo file
     * @throws IOException se forniamo dati sbagliati
     */
    public void createFilePDB(ArrayList<String[]> fileData, String singlePDB){
        //il file che andremo a dare a ring si chiamerà molecolaPDB.pdb dove al posto PDB ci andrà l'id in questione
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("molecola"+singlePDB+".pdb")))) {

            for (String[] row : fileData) { //scrivo nel file le righe interessate
                System.out.println("ALBERTO :"+row.length);
                if(row.length == 12) {
                    writer.printf("%1$-7s%2$-6s%3$-4s%4$-4s%5$-2s%6$-9s%7$-7s%8$-8s%9$-9s%10$-6s%11$-15s%12$-3s",
                            row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10], row[11]);
                    writer.println();
                }
            }
        }catch (Exception e){
            System.out.println("ERRORE TRISTE :"+e.toString());
        }
    }

    /**
     * Funzione che si andrà a richiamare quando si analizzerà tutti i pdb di un certo file tsv
     * @param repeatsDB file da cui prendere tutti i pdb
     * @return lista pdb
     */
    public ArrayList<String> getPDBList(File repeatsDB){
        ArrayList<String> pdbList = new ArrayList<>(); //Arraylist delle righe lette dal file tsv
        String line;
        int cont = 0;

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(repeatsDB))) { // leggo e registro le righe del file pdb
            while ((line = TSVReader.readLine()) != null) {
                if(cont == 0) cont = 1;
                else {
                    line = line.replaceAll("\\s+", " "); //sostituisco spazi vuoti con un solo spazio vuoto
                    String[] lineItems = line.split(" "); //splitto stringa in un array
                    if(lineItems[0].compareTo("RepeatsDB-lite")==0){
                        if(!pdbList.contains(lineItems[5])){
                            pdbList.add(lineItems[5]);
                        }
                    }else {
                        if(!pdbList.contains(lineItems[2])){
                            pdbList.add(lineItems[2]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return pdbList;
    }

    /**
     * Funzione che restituisce la lista di tutti i pdb appartenenti al file json
     * @param repeatsDBFile file json contenente i pdb e i dati corrispondenti
     * @return lista di tutti i pdb
     */
    public ArrayList<String> getPDBListJSON(File repeatsDBFile){
        ArrayList<String> pdbList = new ArrayList<>(); //Arraylist delle righe lette dal file tsv
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(repeatsDBFile);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) { //scorro tutti i pdb appartenenti file JSON
                JSONObject jsonObject = (JSONObject) obj;
                if (!pdbList.contains(jsonObject.get("repeatsdb_id")) )pdbList.add((String) jsonObject.get("repeatsdb_id"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return pdbList;
    }
}