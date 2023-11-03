package main.java;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
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
                        Path fileLabelPath = Paths.get(outputPath, "labels.csv");
                        if (Files.exists(fileLabelPath)) Files.delete(Paths.get(outputPath+"/labels.csv"));
                        if(fileCsvLabel.createNewFile()) System.out.println("LABEL CSV FILE CREATED CORRECTLY");


                        try {
                            FileWriter writer = new FileWriter(fileCsvLabel, true);
                            writer.write("Id" + ";" +  "Organism" + ";" + "Taxon" + "\n");
                            writer.close();
                        } catch (IOException e) {throw new RuntimeException(e);}

                        Path biojavaCachesPath = Paths.get(outputPath + "/path_to_pdb_cache_directory");
                        if(Files.exists(biojavaCachesPath)){
                            FileUtils.cleanDirectory(new File(outputPath+"/path_to_pdb_cache_directory"));
                            Files.delete(biojavaCachesPath);
                        }


                        Path pathCuttedPdb = Paths.get(outputPath + "/cuttedPDB");
                        File cuttedPDBdir = new File(pathCuttedPdb.toString());
                        if (Files.exists(pathCuttedPdb)) {
                            FileUtils.cleanDirectory(cuttedPDBdir);
                            Files.delete(pathCuttedPdb);
                        }
                        if(cuttedPDBdir.mkdir()) System.out.println("PDB REFACTORED DIRECTORY CREATED CORRECTLY");

                        if (cmd.hasOption("b")) bondList = new ArrayList<>( Arrays.asList(cmd.getOptionValues("b") ));

                        Path aasDir = Paths.get( outputPath+"/aas");
                        File aasDirectory = new File(aasDir.toString());
                        if (Files.exists(aasDir)) {
                            FileUtils.cleanDirectory(aasDirectory);
                            Files.delete(aasDir);
                        }
                        if(aasDirectory.mkdir()) System.out.println("AAS DIRECTORY CREATED CORRECTLY");

                        Path ringDir = Paths.get( outputPath+"/ringResult");
                        File ringDirectory = new File(ringDir.toString());
                        if (Files.exists(ringDir)) {
                            FileUtils.cleanDirectory(ringDirectory);
                            Files.delete(ringDir);
                        }
                        if(ringDirectory.mkdir()) System.out.println("RING RESULT DIRECTORY CREATED CORRECTLY");

                        File timesDirectory = null;
                        if (cmd.hasOption("t")) {
                            timesDirectory = new File(cmd.getOptionValue("t")+"/execTimes.csv");
                            Path fileTimesPath = Paths.get(cmd.getOptionValue("t"), "execTimes.csv");
                            if (Files.exists(fileTimesPath)) {
                                Files.delete(Paths.get(cmd.getOptionValue("t")+"/execTimes.csv"));
                            }
                            if(timesDirectory.createNewFile()) System.out.println("EXECUTION TIME CSV FILE CREATED CORRECTLY");
                        }


                        FileJsonManager fileReader = new FileJsonManager();
                        ArrayList<String> pdbList = fileReader.getPDBListJSON(new File(fileJson));
                        System.out.println("Analyzing...");
                        for(String singlePDB : pdbList) {
                            long startJsonMs= System.currentTimeMillis();
                            int start = JsonReader.reader(singlePDB,fileJson,String.valueOf(singlePDB.charAt(singlePDB.length()-1)),fileCsvLabel,outputPath,cuttedPDBdir.toString());
                            long endJsonMs = System.currentTimeMillis();

                            long starRingMs= System.currentTimeMillis();

                            Ring.ringManager(singlePDB, ringDirectory.toString(), bondList,cuttedPDBdir.toString());  //passiamo il pdb corrente, il path di destinazione, e i legami da analizzare
                            long endRingMs= System.currentTimeMillis();

                            long startAasMs = System.currentTimeMillis();
                            AasFileGenerator.aasFileGeneratorMain(singlePDB,start,outputPath,bondList,ringDirectory.toString());
                            long endAasMs = System.currentTimeMillis();
                            if( cmd.hasOption("t")) TimeController.executionTimeManager(endJsonMs-startJsonMs,endRingMs-starRingMs,endAasMs-startAasMs,timesDirectory.toString() );
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

        Option timeOption = new Option("t","time",true, "Insert the path where csv file containing execution time will be saved");
        options.addOption(timeOption);

        return options;
    }
}
