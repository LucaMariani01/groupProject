package main.java;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.io.PDBFileReader;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    public static int reader(String singlePDB, String fileJson,String catena,File fileName) throws Exception {
        FileJsonManager fileReader = new FileJsonManager();
        JSONObject pdbObject = fileReader.getPdbObject(fileJson, singlePDB); //pdbObject contains pdb's data

        LabelCsvGenerator.generator(pdbObject,fileName);
        Integer[] startEnd = fileReader.getStartEndPdbJson(fileJson,singlePDB);
        int start = startEnd[0], end = startEnd[1];

        PDBFileReader pdbReader = new PDBFileReader();
        pdbReader.setPath("path_to_pdb_cache_directory"); //setting path where pdb generated with bioJava will be stored
        Structure structure = pdbReader.getStructureById((String) pdbObject.get("pdb_id"));
        structure.toPDB(); //generating pdb files

        String pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/"+
                singlePDB.substring(1, 3) + "/pdb" + (pdbObject.get("pdb_id")) + ".ent.gz"; //is the pdb file complete path

        List<String> comando = new ArrayList<>();
        comando.add("gunzip"); //unzipping pdb files
        comando.add(pdbPath);
        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        Process processo = processBuilder.start();
        processo.waitFor();
        pdbPath = "path_to_pdb_cache_directory/data/structures/divided/pdb/"+
                singlePDB.substring(1, 3)+"/pdb"+(pdbObject.get("pdb_id"))+".ent"; //path where we are going to unzip pdb files

        fileReader.pdbReaderRefactor(new File(pdbPath), start, end, singlePDB, catena); //method used to cut pdb file
        return start;
    }
}