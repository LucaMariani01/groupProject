package main.java;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileJsonManager {

    /**
     * This method is usedto get start and end of a given pdb
     * @param fileJSON JSON file weare going to analyze
     * @param pdb is the pdb we want to get start and end
     * @return an array containing pdb start at index 0, pdb end at index 1
     */
    public Integer[] getStartEndPdbJson(String fileJSON,String pdb) {
        int minStart=-1, maxEnd=-1;
        boolean first= true;
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileJSON);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                if(((String) jsonObject.get("repeatsdb_id")).compareTo(pdb) == 0) {
                    if (first){
                        minStart = Integer.parseInt((String) jsonObject.get("start"));
                        maxEnd = Integer.parseInt((String) jsonObject.get("end"));
                        first=false;
                    }else {
                        if (Integer.parseInt((String) jsonObject.get("start")) < minStart) minStart = Integer.parseInt((String) jsonObject.get("start"));
                        if (Integer.parseInt((String) jsonObject.get("end")) > maxEnd )  maxEnd = Integer.parseInt((String) jsonObject.get("end"));
                    }
                }
            }
        } catch (IOException | ParseException e) { System.out.println("Something went wrong while obtaining start and end"); }
        Integer[] result = new Integer[2];
        result[0] = minStart;
        result[1] = maxEnd;
        return (result);
    }

    /**
     * This method is used to get pdb object containing pdb data in the json file
     * @param fileJSON JSON file we are going to analyze
     * @param pdb is the pdb we want to get pdb object
     * @return pdb object containing pdb data
     */
    public JSONObject getPdbObject(String fileJSON, String pdb){
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(fileJSON);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                if(((String) jsonObject.get("repeatsdb_id")).compareTo(pdb) == 0) {
                    return jsonObject;
                }
            }
        } catch (IOException | ParseException e) { e.printStackTrace(); }
        return null;
    }
    /**
     * Method used to cut pdb file from start to end
     * @param filePDB is the pdb file we are going to cut
     * @param start is the pdb's start
     * @param end is the pdb's end
     * @param singlePDB is the current pdb
     * @param catena is the pdb's chain
     */
    public  void pdbReaderRefactor(File filePDB,int start, int end, String singlePDB, String catena) {
        String line;
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("molecola"+singlePDB+".pdb")))) { //writing the reduced file in another file
            try (BufferedReader pdbReader = new BufferedReader(new FileReader(filePDB))) { //reading pdb file
                while ((line = pdbReader.readLine()) != null) {
                    if (line.startsWith("ATOM")) {
                        int startTemp = Integer.parseInt(line.substring(22, 26).trim()); //obtaining the start of this current line
                        String chain = String.valueOf(line.charAt(21));                 //obtaining the chain of this current line
                        if (startTemp >= start && startTemp <= end && (chain.compareTo(catena) == 0)) //if the atom number is between the range we are going to write this line
                            writer.println(line);
                    }
                }
                writer.close();
            } catch (Exception e) { System.out.println("Something went wrong during reducing pdb file: " + e); }
        }catch (Exception e) { System.out.println("Something went wrong during writing reduced pdb file: " + e); }
    }

    /**
     * This function is used to get all the pdb in the json file
     * @param repeatsDBFile json file containing PDBs and corresponding data
     * @return list of all the PDBs in the json file
     */
    public ArrayList<String> getPDBListJSON(File repeatsDBFile){
        ArrayList<String> pdbList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(repeatsDBFile);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                if (!pdbList.contains(jsonObject.get("repeatsdb_id")))pdbList.add((String) jsonObject.get("repeatsdb_id")); //if pdb already in the pdb list
            }
        } catch (IOException | ParseException e) { e.printStackTrace(); }
        return pdbList;
    }
}