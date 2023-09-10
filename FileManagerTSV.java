import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class FileManagerTSV {

    public String getNextPdb(File fileTSV, ArrayList<String> arrayPDB){
        ArrayList<String[]> Data = new ArrayList<>();
        int cont =0 ;
        // leggo e registro le righe del file tsv
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTSV))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    String[] lineItems = line.split("\t");
                    if (! arrayPDB.contains(lineItems[2])){
                        return lineItems[2];
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return "NULL";
    }

    /**
     * Metodo per leggere il file pdb
     * @param filePDB nome del file da leggere
     * @param start inizio dell'intervallo di interesse
     * @param end fine dell'intervallo di interesse
     * @param pdb
     * @return regione di interesse
     */
    public ArrayList<String[]> pdbReader(File filePDB, int start, int end, String pdb,String catena) {
        ArrayList<String[]> Data = new ArrayList<>(); //Arraylist delle righe lette dal file tsv
        String line = "";
        int cont = 0;

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(filePDB))) { // leggo e registro le righe del file pdb
            while ((line = TSVReader.readLine()) != null) {
                if(cont == 0) cont = 1;
                else {
                    line = line.replaceAll("\\s+", " "); //sostituisco spazi vuoti con un solo spazio vuoto
                    String[] lineItems = line.split(" "); //splitto stringa in un array
                    if(lineItems[0].compareTo("ATOM") == 0)
                        if (Integer.parseInt(lineItems[5]) >= start && Integer.parseInt(lineItems[5]) <= end && (lineItems[4].compareTo(catena) == 0))
                            Data.add(lineItems);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }

    /**
     * Funzione per ottenere start end di un pdb passato
     * @param fileTsv file TSV da analizzare
     * @param pdb pdb da cui vogliamo ottenere start e end
     * @return array contenente in posizione 0 lo start, e in 1 end del pdb passato tra i parametri
     */
    public Integer[] getStartEndPdb(String fileTsv,String pdb) {
        int minStart=-1, maxEnd=-1;
        String line = "";
        int cont =0 ;

        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTsv))) {
            while ((line = TSVReader.readLine()) != null) {
                if(cont == 0) cont = 1;
                else {
                    String[] lineItems = line.split("\t");
                    if(pdb.compareTo(lineItems[2]) == 0) {
                        if (minStart == -1 && maxEnd == -1) {
                            minStart = Integer.parseInt(lineItems[4]);
                            maxEnd = Integer.parseInt(lineItems[5]);
                        }else {
                            if (Integer.parseInt(lineItems[4]) < minStart) minStart = Integer.parseInt(lineItems[4]);
                            if (Integer.parseInt(lineItems[5]) > maxEnd )  maxEnd = Integer.parseInt(lineItems[5]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        Integer[] result = new Integer[2];
        result[0] = minStart;
        result[1] = maxEnd;
        return (result);
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
                        if (minStart == -1 && maxEnd == -1) {
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
        String[] result= new String[10];
        result[0] = ""+pdb+"\t"+minStart+"\t"+maxEnd;
        return (result);
    }

    /**
     * Metodo che crea il nuovo file PDB
     * @param fileData righe da scrivere nel nuovo file
     * @throws IOException
     */
    public void createFilePDB(ArrayList<String[]> fileData)throws IOException {
        //creo il nuovo file tsv contente l'intervallo di interesse
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("molecola.pdb")))) {
            //scrivo nel file le righe intressate
            for (String[] row : fileData) {
                writer.printf("%1$-7s%2$-6s%3$-4s%4$-4s%5$-2s%6$-9s%7$-7s%8$-8s%9$-9s%10$-6s%11$-15s%12$-3s",
                        row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9], row[10], row[11]);
                writer.println();
            }
        }
    }

    public void createFileTSV2(ArrayList<String[]> fileData)throws IOException {
        //creo il nuovo file tsv contente l'intervallo di interesse
        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(Paths.get("RepeatsDB-table-trimmed.tsv")))) {
            writer.println(
                    "RepeatsDB ID\t"+ "start\t"+ "end"
            );
            //scrivo nel file le righe intressate
            for (String[] row : fileData) {
                writer.printf("%1$20s",
                        row[0]);
                writer.println();
            }
        }
    }
}
