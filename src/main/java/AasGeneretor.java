import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AasGeneretor {
    public ArrayList<String[]> readerEdges(File fileTSV,int start){
        int n1,n2;
        String val1,val2;
        ArrayList<String[]>  result= new ArrayList<>();
        String lista_amminoacidi = "";
        String lista_legami="";

        int cont =0 ;
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTSV))) {
            String line;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    //metterlo su una funzione privata
                    String[] lineItems = line.split(":");
                    n1 = (Integer.parseInt(lineItems[1])-start)+1;
                    n2 = (Integer.parseInt(lineItems[5])-start)+1;
                    val1 = this.parser(lineItems[3].substring(0,3));
                    val2 = this.parser(lineItems[7].substring(0,3));
                    lista_amminoacidi= lista_amminoacidi+val1+val2;
                    lista_legami = lista_legami+ "("+n1+","+n2+");" ;
                    System.out.println("\nN1:"+n1+" N2:"+n2+" val1: "+val1+" val2: "+val2);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        this.buildAASFile(lista_amminoacidi,lista_legami);

        return result;
    }

    private String parser(String s){
        return switch (s) {
            case "ALA" -> "A";
            case "CYS" -> "C";
            case "ASP" -> "D";
            case "GLU" -> "E";
            case "PHE" -> "F";
            case "GLY" -> "G";
            case "HIS" -> "H";
            case "ILE" -> "I";
            case "LYS" -> "K";
            case "LEU" -> "L";
            case "MET" -> "M";
            case "ASN" -> "N";
            case "PRO" -> "P";
            case "GLN" -> "Q";
            case "ARG" -> "R";
            case "SER" -> "S";
            case "THR" -> "T";
            case "VAL" -> "V";
            case "TRP" -> "W";
            case "TYR" -> "Y";
            default -> "";
        };
    }

    private void buildAASFile(String lista_amminoacidi, String lista_legami){
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("legami.aas.txt")))) {
            //scrivo nel file le righe interessate
            writer.println(lista_amminoacidi);
            writer.printf(lista_legami);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}