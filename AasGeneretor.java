import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class AasGeneretor {
    public ArrayList<String[]> readerEdges(File fileTSV,int start){
        int n1=0,n2=0;
        String val1="",val2="";

        ArrayList<String[]> Data = new ArrayList<>();
        int cont =0 ;
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(fileTSV))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                if(cont== 0) cont=1;
                else {
                    String[] lineItems = line.split(":");
                    n1= (Integer.parseInt(lineItems[1])-start)+1;
                    n2= (Integer.parseInt(lineItems[5])-start)+1;
                    val1= this.parser(lineItems[3].substring(0,2));
                    val2=this.parser(lineItems[5].substring(0,3));
                    System.out.println("N1:"+n1+" N2:"+n2+" val1:"+val1+"val2:"+val2);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

        ArrayList<String[]>  result= new ArrayList<>();
        return (result);
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
            case "TIR" -> "Y";
            default -> "";
        };
    }
}

