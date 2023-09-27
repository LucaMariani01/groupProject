package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainRing {
    public static void main(String[] args) {
        String singlePDB=args[0];
        Scanner s = new Scanner(System.in);

        System.out.println("Inserisci il percorso di destinazione dei file generati da RING");
        String path = s.next();

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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}