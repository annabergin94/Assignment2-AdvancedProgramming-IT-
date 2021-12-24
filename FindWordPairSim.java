package Lab2Final;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class FindWordPairSim {

    //shylock bassanio
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Properties p = new Properties();
        p.load(new FileReader("dataConfig.properties"));
        ArrayList<SparseVector> sparseVectors;  //AL to SparseVector
        sparseVectors = loadFile(p.getProperty("wvec.out.file"));

        String word1 = args[0]; // shylock
        String word2 = args[1]; // bassanio
        SparseVector vector1 = null;
        SparseVector vector2 = null;

        // use the words to find the related sparse vector
        // added getter to SparseVector class
        for(SparseVector simVector: sparseVectors) {
            // if word1 is equal to the vector word then the sparse vector
            if (simVector.getWord().equals(word1)) { // if the word attribute of the vector equals word1
                vector1 = simVector;
            }
            if (simVector.getWord().equals(word2)) { // if the word attribute of the vector equals word1
                vector2 = simVector;
            }
        }
        System.out.println(vector1.sim(vector2));
    }

    // dataConfig.properties venice
    // 0.5954158
    public static ArrayList<SparseVector> loadFile(String loadFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadFile))) {
            ArrayList<SparseVector> inputFile = (ArrayList<SparseVector>) ois.readObject();
            return inputFile;
        }
    }
}