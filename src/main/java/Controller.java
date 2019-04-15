
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Controller {

     private static ArrayList<HashTable> tables;
    private static HashTable inputTable;
    private static BTree tree;
    private static String closest;
    private static FileInputStream fin;
    private static ObjectInputStream oin;
    static String medioid;
    private static double compareResult = 0;
    static String bestPage = "";
    private static String medDistance;

    static void createTables(){
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

    }
    private static void createInputTable(String inputURL){
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
    }
    private static void createTree() {
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

    private static ArrayList<Long> calcMediods(int k) throws Exception{
        ArrayList<Long> mediods = new ArrayList<Long>();
        long bestMed = 0;
        //Similarity s = new Similarity();
        boolean finished = false;
        int runs = 0;
        HashTable current, test;
        while(!finished){
            int start = (100/k) * runs;
            int finish = (100/k)  * (runs+1);
            double bestScore = 0;
            double currentScore = 0;
            for(int i = start; i < finish; i++){
                current = tables.get(i);
                for(int j = start; j < finish; j++){
                    test = tables.get(j);
                    currentScore += Similarity.cosineSimilarity(current,test);
                }
                if(currentScore>bestScore){
                    bestScore = currentScore;
                    bestMed = current.url.hashCode();
                }
            }
            mediods.add(bestMed);
            runs++;
            if(runs == k){
                finished = true;
            }
        }
        for(int i =0; i < k; i++){
            System.out.println(mediods.get(i));
        }
        FileOutputStream fo = new FileOutputStream("src/main/files/mediods");
        ObjectOutputStream os = new ObjectOutputStream(fo);
        File f = new File("src/main/files/mediods");
        os.writeObject(mediods);
        fo.close();
        os.close();
        return mediods;
    }

    private static void compare(String inputUrl) throws Exception{
        File[] files = new File("src/main/files").listFiles();
        HashTable t1 = new HashTable("");
        if(tree.contains(tree.root, inputUrl.hashCode(), inputUrl)){
            closest = inputUrl + " is in the tree";
            if(compareMeds(inputTable).equalsIgnoreCase(inputUrl)) {
               // System.out.println("This website is one of the mediods");
                medioid = "this is one of the mediods";
                medDistance = "0";
                compareResult = 0;
            }
        }
        else{
            for(int i =0 ; i<10; i++){
                try{
                    fin = new FileInputStream(files[i]);
                    oin = new ObjectInputStream(fin);
                    t1 = (HashTable) oin.readObject();
                }catch(Exception e){
                    e.printStackTrace();
                }
                fin.close();
                oin.close();
                double tempResults = Similarity.cosineSimilarity(inputTable,t1);
                if(tempResults > compareResult){
                    compareResult = tempResults;
                    bestPage = t1.url;
                }
            }
            System.out.println(compareResult);
            compareMeds(inputTable);
        }
    }

    private static String compareMeds(HashTable table){
        FileInputStream fin;
        ObjectInputStream oin;
        HashTable test;
        ArrayList<Long> mediods = null;
        try{
            fin = new FileInputStream("src/main/files/mediods");
            oin = new ObjectInputStream(fin);
            mediods = (ArrayList<Long>) oin.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        double best = 0;
        double tmp;
        String bestFile = "";
        for(int i = 0; i < 5; i++){
            try{
                long fileName = mediods.get(i);
                fin = new FileInputStream(new File("src/main/files/"+fileName));
                oin = new ObjectInputStream(fin);
                test = (HashTable) oin.readObject();
                if(test.url.equalsIgnoreCase(table.url)){
                    return test.url;
                }else{
                    tmp = Similarity.cosineSimilarity(table,test);
                    if(tmp>best){
                        best = tmp;
                        bestFile = test.url;
                    }
                    System.out.println(test.url);
                }
                fin.close();
                oin.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        medioid = (bestFile);
        medDistance = ("" + best);
        return bestFile;

    }

    static void run(String inputUrl){
        try{
            createTables();
            createInputTable(inputUrl);
            createTree();
            calcMediods(5);
            compare(inputUrl);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
