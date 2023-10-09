package main.java;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

        String fileJson = "RepeatsDB-table.json";
        FileManagerTSV fileReader = new FileManagerTSV();
        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));


        System.out.println("NUMERO PDB :["+pdbList.size()+"]");
        int cont = 0;
        for(String singlePDB : pdbList) {
            System.out.println("STO ANALIZZANDO LA NUMERO :["+cont+"], "+singlePDB);
            cont++;
            long startTSV= System.currentTimeMillis();
            int start = JsonReader.reader(new String[]{singlePDB,fileJson,String.valueOf(singlePDB.charAt(singlePDB.length()-1))});
            //System.out.println("START"+start);
            long endTSV= System.currentTimeMillis();
            long starRing= System.currentTimeMillis();

            Ring.ringManager(new String[]{singlePDB,args[0]});
            long endRing= System.currentTimeMillis();
            long startAas= System.currentTimeMillis();
            AasFileGenerator.AasGenerator(new String[]{singlePDB,String.valueOf(start),args[0]});
            long endAas= System.currentTimeMillis();

            TimeController.saveCalculator(endTSV-startTSV,endRing-starRing,endAas-startAas);
        }

    }


}
