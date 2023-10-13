package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Ring {
    public static void ringManager(String[] args) {
        String singlePDB=args[0],path = args[1];
        String[] legami = new String[args.length-2];

        for (int i=0;i <args.length-2;i++) legami[i] = args[i + 2];
        ArrayList<String> comando = new ArrayList<>();
        comando.add("ring");
        comando.add("-i");
        comando.add("molecola"+singlePDB+".pdb");
        comando.add("--out_dir");
        comando.add(path);

        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        try {
            Process processo1 = processBuilder.start();
            processo1.waitFor(); // Attendere il completamento del processo (se necessario)
        } catch (Exception e) {
            System.out.println("RING "+e);
        }

        for (String legame : legami) EdgesSelector.selector(legame,new File(path+"/molecola"+singlePDB+".pdb_ringEdges"),path);
        if (legami.length>0){
            File myFile = new File(path+"/molecola"+singlePDB+".pdb_ringEdges");
           myFile.delete();
        }
        File myFile = new File(path+"/molecola"+singlePDB+".pdb_ringNodes");
        myFile.delete();

    }
}