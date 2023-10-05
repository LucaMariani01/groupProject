package main.java;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] arg;
        String fileJson = "RepeatsDB-table.json";
        FileManagerTSV fileReader = new FileManagerTSV();
        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));
        arg = ArgumentManager.checkArgumentsList(args);


        /*
        Scanner s = new Scanner(System.in);

        System.out.println("Inserisci il percorso di destinazione dei file generati da RING");
        String path = s.next();
*/
        for(String singlePDB : pdbList) {

            long startTSV= System.currentTimeMillis();
            TsvReader.reader(new String[]{singlePDB,fileJson});
            long endTSV= System.currentTimeMillis();
            long starRing= System.currentTimeMillis();
            Ring.ringManager(new String[]{singlePDB,arg[0]});
            long endRing= System.currentTimeMillis();
            long startAas= System.currentTimeMillis();
            //MainAasGenerator.main(new String[]{singlePDB,}); TODO qui va messo lo start come secondo paramentro
            long endAas= System.currentTimeMillis();

            TimeController.saveCalculator(startTSV-endTSV,starRing-endRing,startAas-endAas);
        }

    }


}
