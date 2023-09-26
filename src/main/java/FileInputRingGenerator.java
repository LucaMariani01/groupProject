import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;
import org.biojava.nbio.structure.io.PDBFileReader;

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
        //for(String singlePDB : pdbList){
        String singlePDB = "1avyA";
            Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv, singlePDB); //funzione per ottenere start end di tutti ip db

            /////////////////
            try {
                PDBFileReader pdbReader = new PDBFileReader();
                pdbReader.setPath("path_to_pdb_cache_directory");
                //String pdbId = "6n8t";
                System.out.println("pdb corrente: "+singlePDB.substring(0,singlePDB.length()-1));
                Structure structure = pdbReader.getStructureById(singlePDB.substring(0,singlePDB.length()-1));
                structure.toPDB();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/av/pdb"+singlePDB.substring(0,singlePDB.length()-1)+".ent.gz";
            System.out.println("percorso "+pdbPath);
            List<String> comando = new ArrayList<>();
            comando.add("gunzip");
            comando.add(pdbPath);
            ProcessBuilder processBuilder = new ProcessBuilder(comando);
            Process processo = processBuilder.start();
            processo.waitFor();
            ////////////////

           // in teoria se la struttura è la medesima possiamo legge il file .ent con la stessa funzione del pdb
           // TODO (26/09/23) testiamo che non ho fatto in tempo
            //ArrayList<String[]> contenutoFileTagliato = fileReader.pdbReader(new File(filePdb), startEndPdb[0], startEndPdb[1], singlePDB, singlePDB.charAt(singlePDB.length()-1));
            ArrayList<String[]> contenutoFileTagliato = fileReader.pdbReader(new File(pdbPath), startEndPdb[0], startEndPdb[1], singlePDB, String.valueOf(singlePDB.charAt(singlePDB.length()-1)));

            fileReader.createFilePDB(contenutoFileTagliato,singlePDB);
            Scanner s = new Scanner(System.in);

            System.out.println("Inserisci il percorso di destinazione dei file generati da RING");
            String path = s.next();

            // Specifica il comando e gli argomenti del programma da eseguire
            List<String> comando1 = new ArrayList<>();
            comando.add("ring");
            comando.add("-i");
            comando.add("molecola"+singlePDB+".pdb");
            comando.add("--out_dir");
            comando.add(path);

            ProcessBuilder processBuilder1 = new ProcessBuilder(comando);

            try {
                // Avvia il processo
                Process processo1 = processBuilder.start();

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
        //}

    }
}
