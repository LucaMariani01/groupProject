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
        int start = Integer.parseInt((String) pdbObject.get("start")), end = Integer.parseInt((String) pdbObject.get("end"));

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

        //taglio prima il file da dare a ring con il metodo pdbReaderReduce e poi lo passo a createFilePDB che genera il file da dare a Ring
        fileReader.createFilePDB(fileReader.pdbReaderReduce(new File(pdbPath), start, end, singlePDB, ((String)pdbObject.get("pdb_id"))) , singlePDB);
        return start;
    }
}