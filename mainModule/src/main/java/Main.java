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
            TsvReader.reader(new String[]{singlePDB,fileJson});
            Ring.ringManager(new String[]{singlePDB,arg[0]});
            //MainAasGenerator.main(new String[]{singlePDB,}); TODO qui va messo lo start come secondo paramentro
        }

    }


}
