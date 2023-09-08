import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class provaMain {

    public static void main(String[] args)throws Exception {
        int start=437,end=497;

        ArrayList<String> pdbAnalizzati = new ArrayList<>();
        FileManagerTSV fileReader = new FileManagerTSV();
        ArrayList<String[]> array = new ArrayList<>();
        //while (fileReader.getNextPdb(new File("RepeatsDB-table.tsv"),pdbAnalizzati).compareTo("NULL")!=0) {

        // ArrayList<String[]> array = fileReader.tsvr(new File("RepeatsDB-table.tsv"),start,end,pdb);
        //array.add(fileReader.tsvr(new File("RepeatsDB-table.tsv"), fileReader.getNextPdb(new File("RepeatsDB-table.tsv"),pdbAnalizzati)));
        array = fileReader.tsvr(new File("6n8t.pdb"),start,end,"6n8t");

        //(pdbAnalizzati.add(fileReader.getNextPdb(new File("RepeatsDB-table.tsv"),pdbAnalizzati));
        fileReader.createFileTSV(array);
    }
}