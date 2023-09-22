import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInputRingGenerator {
    private final FileManagerTSV fileReader ;
    private final String fileTsv ;

    public FileInputRingGenerator(FileManagerTSV fileReader, String fileTsv) {
        this.fileReader = fileReader;
        this.fileTsv = fileTsv;
    }

    public void fileGenerator()throws Exception{
        ArrayList<String> pdbList = fileReader.getPDBList(new File (fileTsv));
        for(String singlePDB : pdbList){
            Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv, singlePDB); //funzione per ottenere start end di tutti ip db
            //inserire il file pdb ottenuto da biojava
            ArrayList<String[]> contenutoFileTagliato = fileReader.pdbReader(new File(filePdb), startEndPdb[0], startEndPdb[1], singlePDB, singlePDB.charAt(singlePDB.length()-1));


            fileReader.createFilePDB(contenutoFileTagliato,singlePDB);
            Scanner s = new Scanner(System.in);

            System.out.println("Inserisci il percorso di destinazione dei file generati d RING");
            String path = s.next();

            // Specifica il comando e gli argomenti del programma da eseguire
            List<String> comando = new ArrayList<>();
            comando.add("ring");
            comando.add("-i");
            comando.add("molecola"+singlePDB+".pdb");
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

            AasGeneretor aasGeneretor = new AasGeneretor();
            ArrayList<String[]> app = aasGeneretor.readerEdges(new File("molecola"+singlePDB+".pdb_ringEdges"), startEndPdb[0]);
        }

    }
}
