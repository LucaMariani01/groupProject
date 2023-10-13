package main.java;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Group;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.chem.ChemCompGroupFactory;
import org.biojava.nbio.structure.chem.ReducedChemCompProvider;
import org.biojava.nbio.structure.io.FileParsingParameters;
import org.biojava.nbio.structure.io.PDBFileReader;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

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
    }

}
