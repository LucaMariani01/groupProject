import org.biojava.nbio.structure.Structure;
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
        //ArrayList<String> pdbList = fileReader.getPDBList(new File (fileTsv));
        //for(String singlePDB : pdbList){
        String singlePDB = "1avyA";

        Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv, singlePDB); //funzione per ottenere start end di tutti ip db

        try {
            PDBFileReader pdbReader = new PDBFileReader();
            pdbReader.setPath("path_to_pdb_cache_directory");
            Structure structure = pdbReader.getStructureById(singlePDB.substring(0,singlePDB.length()-1));
            structure.toPDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/av/pdb"
                +singlePDB.substring(0,singlePDB.length()-1)+".ent.gz";

        List<String> comando = new ArrayList<>();
        comando.add("gunzip");
        comando.add(pdbPath);
        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        Process processo = processBuilder.start();
        processo.waitFor();
        pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/av/pdb"
                +singlePDB.substring(0,singlePDB.length()-1)+".ent";

        fileReader.createFilePDB(fileReader.pdbReader(new File(pdbPath), startEndPdb[0], startEndPdb[1], singlePDB, String.valueOf(singlePDB.charAt(singlePDB.length()-1))),singlePDB);
        Scanner s = new Scanner(System.in);
//
        System.out.println("Inserisci il percorso di destinazione dei file generati da RING");
        String path = s.next();

        comando = new ArrayList<>();
        comando.add("ring");
        comando.add("-i");
        comando.add("molecola"+singlePDB+".pdb");
        comando.add("--out_dir");
        comando.add(path);

        processBuilder = new ProcessBuilder(comando);

        try {
            Process processo1 = processBuilder.start();

            // Attendere il completamento del processo (se necessario)
            processo1.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        AasGeneretor aasGeneretor = new AasGeneretor();
        ArrayList<String[]> app = aasGeneretor.readerEdges(new File("/home/filippo/Scrivania/molecola"+singlePDB+".pdb_ringEdges"), startEndPdb[0]);
        //}

    }
}
