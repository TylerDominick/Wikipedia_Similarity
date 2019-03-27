import java.util.*;

public class Similarity {
   public static HashTable cosineSimilarity(HashTable inputURL, ArrayList<HashTable> tables){
        double max = 0.0;
        HashTable mostSimilar = inputURL;
        for(HashTable table: tables) {
            Set<String> keySets = new TreeSet<String>(inputURL.getKeySet());
            keySets.addAll(table.getKeySet());

            double dotProduct = 0.0;
            double magnitude1 = 0.0;
            double magnitude2 = 0.0;
            for (String key : keySets) {
                if(!key.equals("")){
                    dotProduct += inputURL.get(key) * table.get(key);
                    magnitude1 += Math.pow(inputURL.get(key), 2);
                    magnitude2 += Math.pow(table.get(key), 2);
                }

            }

            magnitude1 = Math.sqrt(magnitude1);
            magnitude2 = Math.sqrt(magnitude2);
            double score = 1 / Math.acos(dotProduct / (magnitude1 * magnitude2));
            if (score > max) {
                max = score;
                mostSimilar = table;
            }
        }
        return mostSimilar;
    }





}
