import java.util.*;
import java.util.logging.FileHandler;

public class Similarity {
   public static double cosineSimilarity(HashTable table1, HashTable table2){
            Set<String> keySets = new TreeSet<String>(table1.getKeySet());
            keySets.addAll(table2.getKeySet());

            double dotProduct = 0.0;
            double magnitude1 = 0.0;
            double magnitude2 = 0.0;
            for (String key : keySets) {
                if(!key.equals("")){
                    dotProduct += table1.get(key) * table2.get(key);
                    magnitude1 += Math.pow(table1.get(key), 2);
                    magnitude2 += Math.pow(table1.get(key), 2);
                }

            }

            magnitude1 = Math.sqrt(magnitude1);
            magnitude2 = Math.sqrt(magnitude2);
            return dotProduct / (magnitude1 * magnitude2);
    }






}
