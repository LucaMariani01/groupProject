package main.java;

import java.io.File;
import java.util.ArrayList;

public class AasFileGenerator {

    public static void AasGenerator(String[] args) {
        String singlePDB = args[0];
        int start = Integer.parseInt(args[1]);
        //Integer[] startEndPdb = new Integer[2];

        AasGeneretor aasGeneretor = new AasGeneretor();
        //ArrayList<String[]> app = aasGeneretor.readerEdges(new File("/home/filippo/Scrivania/molecola"+singlePDB+".pdb_ringEdges"), startEndPdb[0]);
        ArrayList<String[]> app = aasGeneretor.readerEdges(new File("/home/filippo/Scrivania/molecola"+singlePDB+".pdb_ringEdges"),start);

    }
}