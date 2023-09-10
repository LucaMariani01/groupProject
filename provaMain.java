import java.io.File;
import java.util.ArrayList;

public class provaMain {
    public static void main(String[] args)throws Exception {
        FileManagerTSV fileReader = new FileManagerTSV();
        String pdb = "6n8tA", filePdb = "6n8t.pdb", fileTsv = "RepeatsDB-table.tsv",catena = "A";

        Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv,pdb); //funzione per ottenere start end di un pdb passato
        System.out.println("start: "+startEndPdb[0]);
        System.out.println("end: "+startEndPdb[1]);

        //funzione per estrarre dati da file .pdb di un certo pdb con i relativi start e end
        ArrayList<String[]> array = fileReader.pdbReader(new File(filePdb),startEndPdb[0],startEndPdb[1],pdb,catena);
        fileReader.createFilePDB(array);
    }
}