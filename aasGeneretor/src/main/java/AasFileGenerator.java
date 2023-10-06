package main.java;

import java.io.File;
import java.util.ArrayList;

public class AasFileGenerator {

    public static void AasGenerator(String[] args) {
        String singlePDB = args[0];
        int start = Integer.parseInt(args[1]);
        String path = args[2];

        AasGeneretor aasGeneretor = new AasGeneretor();
        ArrayList<String> result = aasGeneretor.readerEdges(new File(args[2]+"/molecola"+singlePDB+".pdb_ringEdges"),start);
        aasGeneretor.buildAASFile(result.get(0),result.get(1),path,singlePDB);
    }
}