package main.java;

import java.io.File;
import java.util.ArrayList;

public class AasFileGenerator {
    public static void aasFileGeneratorMain(String singlePDB, int start, String outputPath, ArrayList<String> bondList, String ringResultPath ) {

        AasParser aasParser = new AasParser();
        ArrayList<String> result;

        //if (!bondList.isEmpty()) result = aasParser.readerEdges(new File(ringResultPath+"/molecola"+singlePDB+".pdb_ringEdges_selBonds"),start);
        //else result = aasParser.readerEdges(new File(ringResultPath+"/molecola"+singlePDB+".pdb_ringEdges"),start);

        result = aasParser.readerEdges(new File(ringResultPath+"/molecola"+singlePDB+".pdb_ringEdges"),start,bondList);
        aasParser.buildAASFile(result.get(0),result.get(1),outputPath,singlePDB);
    }
}