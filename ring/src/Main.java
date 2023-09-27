import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String singlePDB="";
        Scanner s = new Scanner(System.in);
//
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

            // Attendere il completamento del processo (se necessario)
            processo1.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}