import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class provaMain {

    public static void main(String[] args)throws Exception {
        int start=35,end=247;
        String pdb = "6gaoC";
        ReadFileTSV fileReader = new ReadFileTSV();
        ArrayList<String[]> array = fileReader.tsvr(new File("RepeatsDB-table.tsv"),start,end,pdb);

        fileReader.createFileTSV(array);
        array.forEach(a -> System.out.println(Arrays.toString(a)));

    }


}
