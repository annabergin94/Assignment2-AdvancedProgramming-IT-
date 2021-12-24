package Lab2Final;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

class Word2Vec {

    // venice
    public static void main(String[] args) throws IOException {

        // load properties file
        Properties p = new Properties();
        p.load(new FileReader("dataConfig.properties"));

        // use Java 8's built in Stream to read the Shakespeare text
        // remove junk, stop words and store in an ArrayList
        final List<String> updatedWords = inputData();
        //   System.out.print(updatedWords + "\t");

        String keyWordSearched = args[0];
        //  int k = Integer.parseInt(p.getProperty("wvec.k"));
        //System.out.println(k);
        //System.out.println(keyWordSearched);

        // A HashMap that stores a single word and its context
        ArrayList<SparseVector> finalResult = topic(updatedWords);
        saveToFile(p.getProperty("wvec.out.file"), finalResult); // saving the map to the file using the getProperty method
        for(SparseVector s: finalResult) {
            if(s.getWord().equals(keyWordSearched)){
                System.out.println(s.top(3));
            }
        }
    }

    public static List<String> inputData() throws IOException {
        String file = "\\Users\\annab\\OneDrive\\Desktop\\shakespeare.txt";
        Path myPath = Paths.get(file);
        List<String> allWords = Files.readAllLines(myPath);
        List<String> updatedWords;
        updatedWords = new ArrayList<>();

        // ` '"{}[].;,! whitespace
        for (String line : allWords) {
            for (String word : line.replaceAll("\\s|\\\"|\\`|\\'|\\{|\\}|\\[|\\]|\\.|\\;|\\,|\\!|\"|\\|\\’|\\\"|\\\\", " ").toLowerCase().split(" ")) {
                if (!word.equals("")) {
                    updatedWords.add(word);
                }
            }
        }
        String stopFile = "\\Users\\annab\\OneDrive\\Desktop\\stop.txt";
        Path stopPath = Paths.get(stopFile);
        List<String> stopWords = Files.readAllLines(stopPath);
        for (String s : stopWords) {
            updatedWords.removeIf(e -> e.equals(s));
        }
        return updatedWords;
    }

    public static ArrayList<SparseVector> topic(List<String> updatedWords) {
        ArrayList<SparseVector> listOfSparse = new ArrayList<SparseVector>(); // List for sparse vector objects

        for (String s : new HashSet<>(updatedWords)) { //HashSet of vocabulary
            SparseVector svContext = new SparseVector(s); // string vocab
            for (int j = 0; j < updatedWords.size(); j++) {
                if (updatedWords.get(j).equals(s)) { // if the word from the vocabulary is in all text
                    // j becomes the keyword we are looking for and we go through each position 5 left, 5 right
                    // create sparse vector objects, adding the word at the position

                    //LHS of keyword, k hardcoded
                    if (j - 5 >= 0) {
                        svContext.update(updatedWords.get(j - 5));
                    }
                    if (j - 4 >= 0 && j - 4 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j - 4));
                    }
                    if (j - 3 >= 0 && j - 3 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j - 3));
                    }
                    if (j - 2 >= 0 && j - 2 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j - 2));
                    }

                    // RHS of keyword
                    if (j - 1 >= 0 && j - 1 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j - 1));
                    }
                    if (j + 5 >= 0 && j + 5 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j + 5));
                    }
                    if (j + 4 >= 0 && j + 4 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j + 4));
                    }
                    if (j + 3 >= 0 && j + 3 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j + 3));
                    }
                    if (j + 2 >= 0 && j + 2 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j + 2));
                    }
                    if (j + 1 < updatedWords.size()) {
                        svContext.update(updatedWords.get(j + 1));
                        // System.out.println(svContext);
                        // System.out.println(updatedWords);
                    }
                }
            }
            listOfSparse.add(svContext); // the vectors to the List<Sparse>
        }
        return listOfSparse;
    }

    public static void saveToFile(String outFile, ArrayList<SparseVector> s) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outFile))) {
            oos.writeObject(s);
        }
    }
}





