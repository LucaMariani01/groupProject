import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String singlePDB ="";
        Integer[] startEndPdb = new Integer[2];

        AasGeneretor aasGeneretor = new AasGeneretor();
        ArrayList<String[]> app = aasGeneretor.readerEdges(new File("/home/filippo/Scrivania/molecola"+singlePDB+".pdb_ringEdges"), startEndPdb[0]);

    }
}