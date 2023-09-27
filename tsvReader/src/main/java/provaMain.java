package main.java;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.io.PDBFileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class provaMain {
    public static void main(String[] args) throws Exception {
        String singlePDB = "1avyA", fileTsv = "RepeatsDB-table.tsv";
        FileManagerTSV fileReader = new FileManagerTSV();
        Integer[] startEndPdb = fileReader.getStartEndPdb(fileTsv, singlePDB); //funzione per ottenere start end di tutti ip db

        PDBFileReader pdbReader = new PDBFileReader();
        pdbReader.setPath("path_to_pdb_cache_directory");
        Structure structure = pdbReader.getStructureById(singlePDB.substring(0, singlePDB.length() - 1));
        structure.toPDB();

        String pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/" +
                singlePDB.substring(1, 3) + "/pdb" + singlePDB.substring(0, singlePDB.length() - 1) + ".ent.gz";

        List<String> comando = new ArrayList<>();
        comando.add("gunzip");
        comando.add(pdbPath);
        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        Process processo = processBuilder.start();
        processo.waitFor();
        pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/" +
                singlePDB.substring(1, 3) + "/pdb" + singlePDB.substring(0, singlePDB.length() - 1) + ".ent";

        fileReader.createFilePDB(fileReader.pdbReader(new File(pdbPath), startEndPdb[0], startEndPdb[1], singlePDB, String.valueOf(singlePDB.charAt(singlePDB.length() - 1))), singlePDB);
    }
}