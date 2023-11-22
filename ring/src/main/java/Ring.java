package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Ring {
    public static void ringManager(PDB currentPDB, String outputPath, String pdbCuttedDir, int unitNumber) {

        ArrayList<String> comando = new ArrayList<>();
        comando.add("ring");
        comando.add("-i");
        if(unitNumber != -1) comando.add(pdbCuttedDir+"/molecola"+currentPDB.getRepeatsdbId()+"_"+unitNumber+".pdb");
        else comando.add(pdbCuttedDir+"/molecola"+currentPDB.getRepeatsdbId()+".pdb");
        comando.add("--out_dir");
        comando.add(outputPath);

        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        try {
            Process processo1 = processBuilder.start();
            processo1.waitFor(); // Attendere il completamento del processo (se necessario)
        } catch (Exception e) {
            System.out.println("RING "+e);
        }

        File myFile;
        if(unitNumber != -1) myFile = new File(outputPath+"/molecola"+currentPDB.getRepeatsdbId()+"_"+unitNumber+".pdb_ringNodes");
        else myFile = new File(outputPath+"/molecola"+currentPDB.getRepeatsdbId()+".pdb_ringNodes");
        myFile.delete();
    }
}