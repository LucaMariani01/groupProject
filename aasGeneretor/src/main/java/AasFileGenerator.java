package main.java;

import java.io.File;
import java.util.ArrayList;

public class AasFileGenerator {

    public static void AasGenerator(String[] args) {
        String singlePDB = args[0];
        int start = Integer.parseInt(args[1]);
        String path = args[2];
        String[] legami = new String[args.length-3];
        for (int i=0;i <args.length-3;i++) legami[i] = args[i + 3];


        AasGeneretor aasGeneretor = new AasGeneretor();
        if (legami.length>0){

            System.out.println(legami.length);
            for (String l :legami){
                ArrayList<String> result = aasGeneretor.readerEdges(new File(args[2]+"/molecola"+singlePDB+".pdb_ringEdges_"+l),start);
                aasGeneretor.buildAASFile(result.get(0),result.get(1),path,singlePDB);
            }
        }else {
            ArrayList<String> result = aasGeneretor.readerEdges(new File(args[2]+"/molecola"+singlePDB+".pdb_ringEdges"),start);
            aasGeneretor.buildAASFile(result.get(0),result.get(1),path,singlePDB);
        }
    }
}