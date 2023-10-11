package main.java;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.io.PDBFileReader;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static int reader(String[] args) throws Exception {

        String singlePDB = args[0], fileJSON = args[1]; //ottengo dagli argomenti pdb e nome file JSON
        FileManagerTSV fileReader = new FileManagerTSV();
        JSONObject pdbObject = fileReader.getPdbObject(fileJSON, singlePDB); //funzione per ottenere oggetto pdb contenente tutte i dati
        Integer[] startEnd = fileReader.getStartEndPdbJson(fileJSON,singlePDB);
        int start = startEnd[0], end = startEnd[1];

        PDBFileReader pdbReader = new PDBFileReader();
        pdbReader.setPath("path_to_pdb_cache_directory"); //directory dove andremo a mettere i file pdb
        Structure structure = pdbReader.getStructureById((String) pdbObject.get("pdb_id"));
        structure.toPDB(); //genero con BioJava i file pdb da mettere nella directory prima dichiarata

        String pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/"+
                singlePDB.substring(1, 3) + "/pdb" + (pdbObject.get("pdb_id")) + ".ent.gz";

        List<String> comando = new ArrayList<>();
        comando.add("gunzip");
        comando.add(pdbPath);
        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        Process processo = processBuilder.start();
        processo.waitFor();
        pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/"+
                singlePDB.substring(1, 3)+"/pdb"+(pdbObject.get("pdb_id"))+".ent"; //percorso dove metteremo i file pdb generati

        fileReader.pdbReaderRefactor(new File(pdbPath), start, end, singlePDB, args[2]);
        return start;
    }
}