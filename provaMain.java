import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            case 1 ->{

                ArrayList<String[]> array = fileReader.pdbReader(new File(filePdb), startEndPdb[0], startEndPdb[1], pdb, catena);
                fileReader.createFilePDB(array);


                // Specifica il comando e gli argomenti del programma da eseguire
                List<String> comando = new ArrayList<>();
                comando.add("ring");
                comando.add("-i");
                comando.add("molecola.pdb");
                comando.add("--out_dir");
                comando.add("/home/diego/Scrivania");

                ProcessBuilder processBuilder = new ProcessBuilder(comando);

                try {
                    // Avvia il processo
                    Process processo = processBuilder.start();

                    // Attendere il completamento del processo (se necessario)
                    int stato = processo.waitFor();

                    // Puoi fare qualcosa con lo stato di uscita, se necessario
                    System.out.println("Il programma ha restituito il codice di uscita: " + stato);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("File generato");


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