package main.java;

import java.io.FileWriter;
import java.io.IOException;

public class TimeController {

    /**
     * inserisce sul i tempi per calcolare il tempo di tsv, Ring e la generazione del Aas
     * per ogni pdb
     * @param tempoTsv tempo per il tsv
     * @param tempoRing tempo per Ring
     * @param tempoAas tempo per Aas
     */
    public static void saveCalculator(long tempoTsv, long tempoRing, long tempoAas){
        String fileName = "tempi.tsv";
        try {
            // Crea un oggetto FileWriter in modalità "append" passando true come secondo parametro
            FileWriter writer = new FileWriter(fileName, true);
            // Scrivi la riga nel formato TSV
            writer.write(tempoTsv + "\t" + tempoRing + "\t" + tempoAas + "\n");
            // Chiudi il writer per salvare le modifiche
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}



