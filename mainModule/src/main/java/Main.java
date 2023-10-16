package main.java;
import org.apache.commons.cli.*;
import org.biojava.nbio.structure.chem.ChemCompGroupFactory;
import org.biojava.nbio.structure.chem.ReducedChemCompProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ChemCompGroupFactory.setChemCompProvider(new ReducedChemCompProvider());
        Options options = new Options();
        Option helpOption = new Option("h", "help", false, "Show help");
        options.addOption(helpOption);

        Option inputOption = new Option("i","input",true,"Insert the JSON file path to fetch PDB information.");
        options.addOption(inputOption);

        Option outputOption = new Option("o","output",true,"Insert the path where RING and AAS files will be saved.");
        options.addOption(outputOption);

        Option bondOption = new Option("b","bond",true,"Insert the bond that you want to analyze. EX: HBOND, IONIC, VDW,...");
        options.addOption(bondOption);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("CommandLineExample", options);
            } else {
                if (cmd.hasOption("i") && cmd.hasOption("o")) {
                    String fileJson = cmd.getOptionValue("i");
                    String outputPath = cmd.getOptionValue("o");
                    ArrayList<String> bondList = new ArrayList<>();
                    if(!(fileJson.isEmpty() && outputPath.isEmpty())){
                        if (cmd.hasOption("b")) bondList = new ArrayList<>( Arrays.asList(cmd.getOptionValues("b") ));

                        FileJsonManager fileReader = new FileJsonManager();
                        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));

                        System.out.println("PDB NUMBER: "+pdbList.size());
                        int cont = 0;
                        for(String singlePDB : pdbList) {
                            System.out.println("ANALYZING ["+cont+"]: "+singlePDB);
                            cont++;
                            long startJsonMs= System.currentTimeMillis();
                            //int start = JsonReader.reader(new String[]{singlePDB,fileJson,String.valueOf(singlePDB.charAt(singlePDB.length()-1))});
                            int start = JsonReader.reader(singlePDB,fileJson,String.valueOf(singlePDB.charAt(singlePDB.length()-1)));
                            System.out.println("START: "+start);
                            long endJsonMs = System.currentTimeMillis();

                            long starRingMs= System.currentTimeMillis();
                            Ring.ringManager(singlePDB, outputPath, bondList);  //passiamo il pdb corrente, il path di destinazione, e i legami da analizzare
                            long endRingMs= System.currentTimeMillis();

                            long startAasMs = System.currentTimeMillis();
                            AasFileGenerator.aasFileGeneratorMain(singlePDB,start,outputPath,bondList);
                            long endAasMs = System.currentTimeMillis();
                            TimeController.saveCalculator(endJsonMs-startJsonMs,endRingMs-starRingMs,endAasMs-startAasMs );
                        }
                    }
                } else System.out.println("ERROR: use -h to view the help.");
            }
        } catch (Exception e) {
            System.err.println("ERROR while parsing options: "+e.getMessage());
        }
    }
}
