package main.java;

import org.apache.commons.cli.*;
import org.biojava.nbio.structure.chem.ChemCompGroupFactory;
import org.biojava.nbio.structure.chem.ReducedChemCompProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ChemCompGroupFactory.setChemCompProvider(new ReducedChemCompProvider());
        Options options = getOptions();

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
                        File fileCsvLabel = new File(outputPath+"/labels.csv");
                        if (!fileCsvLabel.createNewFile()) System.out.println("FILE NOT CREATED ");
                        try {
                            FileWriter writer = new FileWriter(fileCsvLabel, true);
                            writer.write("Id" + ";" +  "Organism" + ";" + "Taxon" + "\n");
                            writer.close();
                        } catch (IOException e) {throw new RuntimeException(e);}
                        if (cmd.hasOption("b")) bondList = new ArrayList<>( Arrays.asList(cmd.getOptionValues("b") ));

                        FileJsonManager fileReader = new FileJsonManager();
                        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));

                        File directory = new File(outputPath+"/aas");
                        if(!directory.mkdir()) System.out.println("FOLDER NOT CREATED");

                        File timesDirectory = new File("execTimes/execTimes.csv");
                        Path fileTimesPath = Paths.get("execTimes","execTimes.csv");

                        if(Files.exists(fileTimesPath)){
                            Files.delete(Paths.get("execTimes/execTimes.csv"));
                            System.out.println("ho cancellato la dir");
                        }else timesDirectory.mkdir();


                        for(String singlePDB : pdbList) {
                            long startJsonMs= System.currentTimeMillis();
                            int start = JsonReader.reader(singlePDB,fileJson,String.valueOf(singlePDB.charAt(singlePDB.length()-1)),fileCsvLabel);
                            long endJsonMs = System.currentTimeMillis();

                            long starRingMs= System.currentTimeMillis();
                            Ring.ringManager(singlePDB, outputPath, bondList);  //passiamo il pdb corrente, il path di destinazione, e i legami da analizzare
                            long endRingMs= System.currentTimeMillis();

                            long startAasMs = System.currentTimeMillis();
                            AasFileGenerator.aasFileGeneratorMain(singlePDB,start,outputPath,bondList);
                            long endAasMs = System.currentTimeMillis();
                            TimeController.executionTimeManager(endJsonMs-startJsonMs,endRingMs-starRingMs,endAasMs-startAasMs,timesDirectory.toString() );
                        }
                    }
                } else System.out.println("ERROR: use -h to view the help.");
            }
        } catch (Exception e) {
            System.err.println("ERROR while parsing options: "+e.getMessage());
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        Option helpOption = new Option("h", "help", false, "Show help");
        options.addOption(helpOption);

        Option inputOption = new Option("i","input",true,"Insert the JSON file path to fetch PDB information.");
        options.addOption(inputOption);

        Option outputOption = new Option("o","output",true,"Insert the path where RING and AAS files will be saved.");
        options.addOption(outputOption);

        Option bondOption = new Option("b","bond",true,"Insert the bond that you want to analyze. EX: HBOND, IONIC, VDW,...");
        options.addOption(bondOption);
        return options;
    }
}
