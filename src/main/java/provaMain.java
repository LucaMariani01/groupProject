
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class provaMain {
    public static void main(String[] args)throws Exception {

        Menu m = new Menu();

        FileManagerTSV fileReader = new FileManagerTSV();
        String fileTsv = "RepeatsDB-table.tsv";

        m.displayMenu();
        int scelta = m.scelta();




        do {
            switch (scelta) {
                case 1 -> {

                    // metterlo in una funzione e toglierlo dal main

                    fileReader.createFilePDB(array);
                    Scanner s = new Scanner(System.in);

                    System.out.println("Inserisci il percorso di destinazione dei file generati d RING");
                    String path = s.next();

                    // Specifica il comando e gli argomenti del programma da eseguire
                    List<String> comando = new ArrayList<>();
                    comando.add("ring");
                    comando.add("-i");
                    comando.add("molecola.pdb");
                    comando.add("--out_dir");
                    comando.add(path);

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
            m.displayMenu();
            scelta = m.scelta();
        }while(scelta!=0);
    }



}