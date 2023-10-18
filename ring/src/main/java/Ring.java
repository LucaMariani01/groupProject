package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Ring {
    public static void ringManager(String singlePDB, String outputPath, ArrayList<String> bondList) {
        ArrayList<String> comando = new ArrayList<>();
        comando.add("ring");
        comando.add("-i");
        comando.add("molecola"+singlePDB+".pdb");
        comando.add("--out_dir");
        comando.add(outputPath);

        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        try {
            Process processo1 = processBuilder.start();
            processo1.waitFor(); // Attendere il completamento del processo (se necessario)
        } catch (Exception e) {
            System.out.println("RING "+e);
        }


        //for (String legame : bondList) EdgesSelector.selector(legame,new File(outputPath+"/molecola"+singlePDB+".pdb_ringEdges"),outputPath);
        if (!bondList.isEmpty()){
            EdgesSelector.selector(bondList,new File(outputPath+"/molecola"+singlePDB+".pdb_ringEdges"),outputPath);
            File myFile = new File(outputPath+"/molecola"+singlePDB+".pdb_ringEdges");
            myFile.delete();
        }
        File myFile = new File(outputPath+"/molecola"+singlePDB+".pdb_ringNodes");
        myFile.delete();
    }
}