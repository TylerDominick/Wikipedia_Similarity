import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

     private static ArrayList<HashTable> tables;
    private static HashTable inputTable;

    public static void createTables(String inputURl){
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
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
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
    }

    public static ArrayList<HashTable> getTables(){
        return tables;
    }
    public static HashTable getInputTable(){
        return inputTable;
    }

}
