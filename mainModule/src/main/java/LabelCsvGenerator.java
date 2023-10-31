package main.java;

import org.biojava.nbio.structure.*;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LabelCsvGenerator {
    public static  void generator(JSONObject pdbObject, File fileName){

        String pdbId = pdbObject.get("pdb_id").toString();
        String classification =pdbObject.get("class_topology_fold_clan").toString();
        Structure structure;
        try {
            structure = StructureIO.getStructure(pdbId);
        } catch (IOException | StructureException e) {
            throw new RuntimeException(e);
        }

        Chain targetChain = structure.getChain(pdbObject.get("pdb_chain").toString().toUpperCase());
        String app = "";

        if (targetChain != null) {
            for (DBRef dbRef : targetChain.getStructure().getDBRefs())
                if (dbRef.getDatabase().equalsIgnoreCase("UNP")) app = dbRef.getDbAccession();
        } else System.out.println("ID NULL : "+pdbId);
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(pdbId + pdbObject.get("pdb_chain").toString()+ ";" + app + ";" + classification + "\n");
            writer.close();
        } catch (IOException e) { throw new RuntimeException(e); }
    }
}
