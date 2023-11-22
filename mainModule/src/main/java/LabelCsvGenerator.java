package main.java;

import org.biojava.nbio.structure.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LabelCsvGenerator {
    public static void generator(PDB currentPDB, File csvFileName, int unitNumber){

        String pdbId = currentPDB.getPdbId();
        String classification =currentPDB.getClassTopologyFoldClan();
        Structure structure;
        try {
            structure = StructureIO.getStructure(pdbId);
        } catch (IOException | StructureException e) {
            throw new RuntimeException(e);
        }

        Chain targetChain = structure.getChain(currentPDB.getPdbChain().toUpperCase());

        String app = "";

        if (targetChain != null) {
            for (DBRef dbRef : targetChain.getStructure().getDBRefs()){
                if (dbRef.getDatabase().equalsIgnoreCase("UNP")) {
                    app = dbRef.getDbAccession();
                }
            }
            if(app.compareTo("")==0) app = "unknown";
        } else app = "unknown";

        if(unitNumber == -1 ){
            writeLabel(pdbId+currentPDB.getPdbChain(), app, classification, csvFileName);
        }else{
            writeLabel(pdbId+currentPDB.getPdbChain()+"_"+unitNumber, app, classification, csvFileName);
        }
    }

    private static void writeLabel(String Id, String uniprot, String classification, File csvFileName){
        try {
            FileWriter writer = new FileWriter(csvFileName, true);
            writer.write(Id+ ";" +uniprot+";" + classification + "\n");
            writer.close();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

}
