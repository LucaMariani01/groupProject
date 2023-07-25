import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class provaMain {

    public static void main(String[] args)throws Exception {
        int start=35,end=247;
        String pdb = "6gaoC";
        FileManagerTSV fileReader = new FileManagerTSV();
        ArrayList<String[]> array = fileReader.tsvr(new File("RepeatsDB-table.tsv"),start,end,pdb);

        fileReader.createFileTSV(array);

    }


}
