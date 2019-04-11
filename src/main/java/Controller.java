import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

     private static ArrayList<HashTable> tables;
    private static HashTable inputTable;
    private static BTree tree;

    public static void createTables(String inputURl){
        FileOutputStream fo = null;
        ObjectOutputStream os = null;
        tables = new ArrayList<HashTable>();
        File file = new File("src/main/webpage-urls.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String urlFromFile;
            while((urlFromFile = in.readLine()) != null){
                HashTable table = new HashTable(urlFromFile);
                List<String> words = ReadTextFromURL.getWords(table.url);
                for(String word:words){
                    table.put(word);
                }
                tables.add(table);
                fo = new FileOutputStream("src/main/files/" + urlFromFile.hashCode());
                os = new ObjectOutputStream(fo);
                os.writeObject(table);
                os.close();
                fo.close();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        createInputTable(inputURl);
    }
    public static void createInputTable(String inputURL){
         inputTable = new HashTable(inputURL);
        try{
            List<String> words =  ReadTextFromURL.getWords(inputURL);
            for(String word : words){
                inputTable.put(word);
            }
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        createTree();
    }
    public static void createTree() {
        try{
            File tmp = new File("src/main/files/btree");
            RandomAccessFile treeFile = new RandomAccessFile(tmp,"rw");
            tree = new BTree(treeFile);
            String [] list = new File("src/main/files/").list();
            for(String file: list){
                if(!file.equalsIgnoreCase("btree")){
                    tree.insert(tree, Integer.parseInt(file));
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void calcMediods(int k){
        ArrayList<Long> medioids = new ArrayList<Long>();
        long bestMed = 0;
        Similarity s = new Similarity();
        boolean finished = false;
        int runs = 0;
        HashTable current, test;
        while(!finished){
            int start = (100/k * runs);
            int finish = (100/k * runs) * (runs+1);
            double bestScore = 0;
            double currentScore = 0;
            for(int i = start; i<finish; i++){
                current = tables.get(i);
                for(int j = start; j <finish; j++){
                    test = tables.get(j);
                    currentScore += s.
                }
            }
        }
    }

    public static ArrayList<HashTable> getTables(){
        return tables;
    }
    public static HashTable getInputTable(){
        return inputTable;
    }

}
