package main.java;
import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        Option helpOption = new Option("h", "help", false, "Show the help");
        options.addOption(helpOption);

        Option inputOption = new Option("i","input",true,"Insert the JSON file path to fetch PDB information.");
        inputOption.setRequired(true);
        options.addOption(inputOption);

        Option outputOption = new Option("o","output",true,"Insert the path where RING and AAS files will be saved.");
        inputOption.setRequired(true);
        options.addOption(outputOption);


        Option bondOption = new Option("b","bond",true,"Insert the bond list that you want to analyze. EX: HBOND, IONIC, VDW.");
        options.addOption(bondOption);

        // Crea il parser delle opzioni
        CommandLineParser parser = new DefaultParser();

        try {
            // Analizza gli argomenti della riga di comando
            CommandLine cmd = null;

            cmd = parser.parse(options, args);

            // Gestisci le opzioni
            if (cmd.hasOption("h")) {
                // Mostra l'aiuto
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("CommandLineExample", options);
            } else {
                // Leggi l'opzione del file se presente
                if (cmd.hasOption("f")) {
                    String filePath = cmd.getOptionValue("f");
                    System.out.println("Percorso del file: " + filePath);
                } else {
                    System.out.println("Opzione non riconosciuta. Usa -h per l'aiuto.");
                }
            }
        } catch (Exception e) {
            System.err.println("Errore durante l'analisi degli argomenti: " + e.getMessage());
        }
        /*
        //TODO IMPLEMENTARE CLASSE PER GESTIRE COMANDI
        ChemCompGroupFactory.setChemCompProvider(new ReducedChemCompProvider());
        String fileJson = "RepeatsDB-table (2).json";
        FileManagerTSV fileReader = new FileManagerTSV();
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

            Ring.ringManager(new String[]{singlePDB,args[0],args[1]}); //todo args[1] andrà modificato, è li solo per testing
            long endRing= System.currentTimeMillis();
            long startAas= System.currentTimeMillis();
            AasFileGenerator.AasGenerator(new String[]{singlePDB,String.valueOf(start),args[0],args[1]});//todo args[1] andrà modificato,è li solo per testing
            long endAas= System.currentTimeMillis();

            TimeController.saveCalculator(endTSV-startTSV,endRing-starRing,endAas-startAas);
        }

         */
    }
}
