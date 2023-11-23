package main.java;

import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.io.PDBFileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static int reader(PDB currentPDB, String fileJson,File fileNameCsvLabel, String PDBDirectory, String PDBcuttedDir, int unitNumber) throws Exception {
        int start;
        int end;

        LabelCsvGenerator.generator(currentPDB,fileNameCsvLabel,unitNumber);

        start = currentPDB.getStart();
        end = currentPDB.getEnd();

        biojavaGenPdb(PDBDirectory,PDBcuttedDir ,currentPDB, unitNumber, start,end);
        return start;
    }


    private static void biojavaGenPdb(String PDBDirectory,String PDBcuttedDir, PDB currentPDB, int unitNumber, int start, int end)throws Exception{
        FileJsonManager fileReader = new FileJsonManager();
        PDBFileReader pdbReader = new PDBFileReader();
        String biojavaDirectory = PDBDirectory+"/path_to_pdb_cache_directory";
        pdbReader.setPath(biojavaDirectory); //setting path where pdb generated with bioJava will be stored

        Structure structure = pdbReader.getStructureById(currentPDB.getPdbId());
        structure.toPDB(); //generating pdb files

        String pdbPath =biojavaDirectory+"/data/structures/divided/pdb/"+
                currentPDB.getRepeatsdbId().substring(1, 3) + "/pdb" + (currentPDB.getPdbId()) + ".ent.gz"; //is the pdb file complete path

        List<String> comando = new ArrayList<>();
        comando.add("gunzip"); //unzipping pdb files
        comando.add(pdbPath);
        ProcessBuilder processBuilder = new ProcessBuilder(comando);
        Process processo = processBuilder.start();
        processo.waitFor();
        pdbPath = biojavaDirectory+"/data/structures/divided/pdb/"+
                currentPDB.getRepeatsdbId().substring(1, 3)+"/pdb"+(currentPDB.getPdbId())+".ent"; //path where we are going to unzip pdb files
        if(unitNumber!=-1) fileReader.pdbReaderRefactor(new File(pdbPath), start, end, currentPDB,unitNumber,PDBcuttedDir); //method used to cut pdb file
        else fileReader.pdbReaderRefactor(new File(pdbPath), start, end, currentPDB,unitNumber,PDBcuttedDir);
    }
}