package main.java;

import org.biojava.nbio.structure.DBRef;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LabelCsvGenerator {

    public static void generetor(String pdbId, String classification, File fileName){

        Structure structure = null;
        try {
            structure = StructureIO.getStructure(pdbId);
        }catch (Exception e){}
        // Print classification information
        System.out.println("Title: " + structure.getPDBHeader().getTitle());

        // Link to UNIPROT
        structure.getChains().forEach(chain -> {
            for (DBRef dbRef : chain.getStructure().getDBRefs()) {

                if (dbRef.getDatabase().equals("UNP")) {
                    //System.out.println("UNIPROT ID for chain " + chain.getId() + ": " + dbRef.getDbAccession());
                    try {
                        FileWriter writer = new FileWriter(fileName, true);
                        writer.write(pdbId+chain.getId() + "\t" +  dbRef.getDbAccession() + "\t" + classification + "\n");
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
