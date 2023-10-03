package main.java;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        //String fileTsv = "RepeatsDB-table.tsv";
        String fileJson = "RepeatsDB-table.json";
        FileManagerTSV fileReader = new FileManagerTSV();
        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));
        Scanner s = new Scanner(System.in);

        System.out.println("Inserisci il percorso di destinazione dei file generati da RING");
        String path = s.next();

        for(String singlePDB : pdbList) {
            TsvReader.reader(new String[]{singlePDB,fileJson});
            Ring.ringManager(new String[]{singlePDB,path});
            //MainAasGenerator.main(new String[]{singlePDB,}); TODO qui va messo lo start come secondo paramentro
        }

    }
}
