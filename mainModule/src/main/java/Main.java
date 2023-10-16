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
        Option helpOption = new Option("h", "help", false, "Show the help");
        options.addOption(helpOption);

        Option inputOption = new Option("i","input",true,"Insert the JSON file path to fetch PDB information.");
        options.addOption(inputOption);

        Option outputOption = new Option("o","output",true,"Insert the path where RING and AAS files will be saved.");
        options.addOption(outputOption);

        Option bondOption = new Option("b","bond",true,"Insert the bond list that you want to analyze. EX: HBOND, IONIC, VDW.");
        options.addOption(bondOption);

        // Crea il parser delle opzioni
        CommandLineParser parser = new DefaultParser();

        try {
            // Analizza gli argomenti della riga di comando
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {  // Mostra l'aiuto
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

                        System.out.println("NUMERO PDB :["+pdbList.size()+"]");
                        int cont = 0;
                        for(String singlePDB : pdbList) {
                            System.out.println("STO ANALIZZANDO LA NUMERO :["+cont+"], "+singlePDB);
                            cont++;
                            long startTSV= System.currentTimeMillis();
                            int start = JsonReader.reader(new String[]{singlePDB,fileJson,String.valueOf(singlePDB.charAt(singlePDB.length()-1))});
                            System.out.println("START"+start);
                            long endTSV= System.currentTimeMillis();

                            long starRing= System.currentTimeMillis();
                            Ring.ringManager(singlePDB, outputPath, bondList);  //passiamo il pdb corrente, il path di destinazione, e i legami da analizzare
                            long endRing= System.currentTimeMillis();

                            long startAas= System.currentTimeMillis();
                            AasFileGenerator.aasFileGeneratorMain(singlePDB,start,outputPath,bondList);
                            long endAas= System.currentTimeMillis();
                            TimeController.saveCalculator(endTSV-startTSV,endRing-starRing,endAas-startAas);
                        }
                    }
                } else System.out.println("Opzione non riconosciuta. Usa -h per l'aiuto.");
            }
        } catch (Exception e) {
            System.err.println("Errore durante l'analisi degli argomenti: " + e.getMessage());
        }

    }
}
