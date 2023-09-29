package main.java;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        String fileTsv = "RepeatsDB-table.tsv";
        FileManagerTSV fileReader = new FileManagerTSV();

        ArrayList<String> pdbList = fileReader.getPDBList(new File(fileTsv));
        System.out.println("DIO PORCOOOOOOOOOO:["+pdbList.get(17)+"]");
        for(String singlePDB : pdbList) {

            MainTsvReader.main(new String[]{singlePDB,fileTsv});
            MainRing.main(new String[]{singlePDB});
            //MainAasGenerator.main(new String[]{singlePDB,}); TODO qui va messo lo start come secondo paramentro

        }

    }
}
