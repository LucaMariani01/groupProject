package main.java;

import java.io.File;
import java.util.ArrayList;

public class AasFileGenerator {
    public static void aasFileGeneratorMain(PDB currentPDB, int start, String outputPath, ArrayList<String> bondList, String ringResultPath , int unitNumber) {

        AasParser aasParser = new AasParser();
        ArrayList<String> result;
        String edgesFileName ;

        if(unitNumber != -1 ) edgesFileName = currentPDB.getRepeatsdbId()+"_"+unitNumber;
        else edgesFileName = currentPDB.getRepeatsdbId();

        result = aasParser.readerEdges(new File(ringResultPath+"/molecola"+edgesFileName+".pdb_ringEdges"),start,bondList);
        aasParser.buildAASFile(result.get(0),result.get(1),outputPath,edgesFileName);
    }
}