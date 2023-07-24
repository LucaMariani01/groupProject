import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class provaMain {

    public static void main(String[] args) {
        int start=35,end=247;
        String pdb = "6gaoC";
        ArrayList<String[]> array= tsvr(new File("RepeatsDB-table.tsv"),start,end,pdb);

        array.forEach(a -> System.out.println(Arrays.toString(a)));

    }

    public static ArrayList<String[]> tsvr(File test2,int start,int end,String pdb) {
        ArrayList<String[]> Data = new ArrayList<>(); //initializing a new ArrayList out of String[]'s
        int cont =0 ;
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(test2))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    String[] lineItems = line.split("\t"); //splitting the line and adding its items in String[]

                    if (Integer.parseInt(lineItems[4]) >= start && Integer.parseInt(lineItems[5]) <= end && pdb.compareTo(lineItems[2]) == 0)
                        Data.add(lineItems); //adding the splitted line array to the ArrayList
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }
}
