package main.java;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

        String fileJson = "RepeatsDB-table.json";
        FileManagerTSV fileReader = new FileManagerTSV();
        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));


        /*
        Scanner s = new Scanner(System.in);

        System.out.println("Inserisci il percorso di destinazione dei file generati da RING");
        String path = s.next();
*/
        System.out.println("NUMERO PDB :["+pdbList.size()+"]");
        int cont = 0;
        for(String singlePDB : pdbList) {
            System.out.println("STO ANALIZZANDO LA NUMERO :["+cont+"]");
            cont++;
            long startTSV= System.currentTimeMillis();
            TsvReader.reader(new String[]{singlePDB,fileJson});
            long endTSV= System.currentTimeMillis();
            long starRing= System.currentTimeMillis();

            Ring.ringManager(new String[]{singlePDB,args[0]});
            long endRing= System.currentTimeMillis();
            long startAas= System.currentTimeMillis();
            //MainAasGenerator.main(new String[]{singlePDB,}); TODO qui va messo lo start come secondo paramentro
            long endAas= System.currentTimeMillis();

            TimeController.saveCalculator(endTSV-startTSV,endRing-starRing,endAas-startAas);
        }

    }


}
