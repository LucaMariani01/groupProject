import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class provaMain {

    public static void main(String[] args)throws Exception {
        //int start=35,end=247;

        ArrayList<String> pdbAnalizzati = new ArrayList<>();
        FileManagerTSV fileReader = new FileManagerTSV();
        while (fileReader.getNextPdb(new File("RepeatsDB-table.tsv"),pdbAnalizzati).compareTo("NULL")!=0) {

            // ArrayList<String[]> array = fileReader.tsvr(new File("RepeatsDB-table.tsv"),start,end,pdb);
            ArrayList<String[]> array = new ArrayList<>();
            array.add(fileReader.tsvr(new File("RepeatsDB-table.tsv"), fileReader.getNextPdb(new File("RepeatsDB-table.tsv"),pdbAnalizzati)));
            fileReader.createFileTSV2(array);
            pdbAnalizzati.add(fileReader.getNextPdb(new File("RepeatsDB-table.tsv"),pdbAnalizzati));
        }

    }


}
