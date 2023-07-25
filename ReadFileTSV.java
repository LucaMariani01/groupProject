import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFileTSV {
    public ArrayList<String[]> tsvr(File test2, int start, int end, String pdb) {
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

    public void createFileTSV(ArrayList<String[]> fileData)throws IOException {
        File csvFileRegister = new File("RepeattsDB-table-trimmed.tsv");
        try (PrintWriter pw = new PrintWriter(csvFileRegister)) {
            pw.println(
                    "Reviewed, "+ "Classification, "+ "RepeatsDB ID, "+ "Type, "+ "start, "+ "end"
            );
            for(String[] app : fileData) {
                pw.println(
                    app[0]+", "+app[1]+", "+app[2]+", "+app[3]+", "+app[4]+", "+app[5]
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
