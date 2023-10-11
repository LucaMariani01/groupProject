package main.java;

import java.io.IOException;
import java.util.ArrayList;

public class Ring {
    public static void ringManager(String[] args) {
        String singlePDB=args[0],path = args[1];
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
    }
}