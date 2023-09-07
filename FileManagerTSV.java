import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class FileManagerTSV {
    /** metodo per leggere il file tsv
     *
     * @param fileTSV nome del file da leggere
     * @param start inizio dell'intervallo di interesse
     * @param end fine dell'intervallo di interesse
     * @param pdb
     * @return regione di interesse
     */
    public ArrayList<String[]> tsvr(File fileTSV, int start, int end, String pdb) {
        /**
         * Arraylist delle righe lette dal file tsv
         */
        ArrayList<String[]> Data = new ArrayList<>();
        int cont =0 ;
        // leggo e registro le righe del file tsv
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTSV))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    String[] lineItems = line.split("\t");

                    if (Integer.parseInt(lineItems[4]) >= start && Integer.parseInt(lineItems[5]) <= end && pdb.compareTo(lineItems[2]) == 0)
                        Data.add(lineItems);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }

    public String[] tsvr(File fileTSV, String pdb){
        int minStart=-1, maxEnd=-1;
        ArrayList<String[]> Data = new ArrayList<>();

        int cont =0 ;
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTSV))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    String[] lineItems = line.split("\t");
                    if(pdb.compareTo(lineItems[2]) == 0) {
                        if (minStart == -1 && maxEnd == -1 ) {
                            minStart = Integer.parseInt(lineItems[4]);
                            maxEnd = Integer.parseInt(lineItems[5]);
                        }else {
                            if (Integer.parseInt(lineItems[4]) < minStart)   minStart = Integer.parseInt(lineItems[4]);
                            if (Integer.parseInt(lineItems[5]) > maxEnd )  maxEnd = Integer.parseInt(lineItems[5]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        String[] ritorno= new String[10];
        ritorno[0] = ""+pdb+"\t"+minStart+"\t"+maxEnd;
        return (ritorno);
    }

    /**
     * Metodo che crea il nuovo file TSV
     * @param fileData righe da scrivere nel nuovo file
     * @throws IOException
     */
    public void createFileTSV(ArrayList<String[]> fileData)throws IOException {
        //creo il nuovo file tsv contente l'intervallo di interesse
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(Paths.get("RepeatsDB-table-trimmed.tsv")))) {
            writer.println(
                    "Reviewed\t"+ "Classification\t"+ "RepeatsDB ID\t"+ "Type\t"+ "start\t"+ "end"
            );
            //scrivo nel file le righe intressate
            for (String[] row : fileData) {
                writer.printf("%1$20s\t%2$3s\t%3$3s\t%4$3s\t%5$s\t%6$s",
                        row[0], row[1], row[2], row[3], row[4], row[5]);
                writer.println();
            }
        }
    }
}
