import java.io.File;
import java.util.ArrayList;

public class provaMain {
    public static void main(String[] args)throws Exception {

        Menu m = new Menu();

        FileManagerTSV fileReader = new FileManagerTSV();
        String pdb = "6n8tA", filePdb = "6n8t.pdb", fileTsv = "RepeatsDB-table.tsv",catena = "A";

        m.displayMenu();
        int scelta = m.scelta();
        Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv, pdb); //funzione per ottenere start end di un pdb passato

        System.out.println("start: " + startEndPdb[0]);
        System.out.println("end: " + startEndPdb[1]);

        switch (scelta) {
            case 1 -> {

                ArrayList<String[]> array = fileReader.pdbReader(new File(filePdb), startEndPdb[0], startEndPdb[1], pdb, catena);
                fileReader.createFilePDB(array);
            }
            case 2 -> {
                AasGeneretor aasGeneretor = new AasGeneretor();
                ArrayList<String[]> app = aasGeneretor.readerEdges(new File("molecola.pdb_ringEdges"), startEndPdb[0]);
            }
        }
      /*  Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv,pdb); //funzione per ottenere start end di un pdb passato
        System.out.println("start: "+startEndPdb[0]);
        System.out.println("end: "+startEndPdb[1]);


        ArrayList<String[]> array = fileReader.pdbReader(new File(filePdb),startEndPdb[0],startEndPdb[1],pdb,catena);
        fileReader.createFilePDB(array);

        AasGeneretor aasGeneretor = new AasGeneretor();
        ArrayList<String[]> app = aasGeneretor.readerEdges(new File("molecola.pdb_ringEdges"),startEndPdb[0]);
*/
    }
}