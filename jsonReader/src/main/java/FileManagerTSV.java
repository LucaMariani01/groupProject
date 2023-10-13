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
     * @param fileJSON file TSV da analizzare
     * @param pdb pdb da cui vogliamo ottenere start e end
     * @return array contenente in posizione 0 lo start, e in 1 end del pdb passato tra i parametri
     */
    public Integer[] getStartEndPdbJson(String fileJSON,String pdb) {
        int minStart=-1, maxEnd=-1;
        boolean first= true;
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileJSON);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) { //scorro tutti i pdb appartenenti file JSON
                JSONObject jsonObject = (JSONObject) obj;
                if(((String) jsonObject.get("repeatsdb_id")).compareTo(pdb) == 0) { //ottengo start e end del pdb passato
                    if (first ){
                        minStart = Integer.parseInt((String) jsonObject.get("start"));
                        maxEnd = Integer.parseInt((String) jsonObject.get("end"));
                        first=false;
                    }else {
                        if (Integer.parseInt((String) jsonObject.get("start")) < minStart) minStart = Integer.parseInt((String) jsonObject.get("start"));
                        if (Integer.parseInt((String) jsonObject.get("end")) > maxEnd )  maxEnd = Integer.parseInt((String) jsonObject.get("end"));
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Something went wrong calcolo max end");
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
     * taglia il file
     * @param filePDB file da tagliare
     * @param start start
     * @param end end
     * @param singlePDB pdb interessato
     * @param catena catena
     */
    public  void pdbReaderRefactor(File filePDB,int start, int end, String singlePDB, String catena) {
        String line;
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("molecola"+singlePDB+".pdb")))) {
            try (BufferedReader tsvReader = new BufferedReader(new FileReader(filePDB))) { // leggo e registro le righe del file pdb

                while ((line = tsvReader.readLine()) != null) {
                    if (line.startsWith("ATOM")) {
                        int startTemp = Integer.parseInt(line.substring(22, 26).trim());
                        String chain = String.valueOf(line.charAt(21));
                        if (startTemp >= start && startTemp <= end && (chain.compareTo(catena) == 0)) writer.println(line);
                    }
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("Something went wrong reduce " + e);
            }
        }catch (Exception e) {
            System.out.println("Something went wrong scrittura " + e);
        }
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