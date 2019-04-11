import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class HashTable implements Serializable {

    String url;
    private Node[] data;
    int initialSize = 64;
    private int count;



    public HashTable( String url){ data = new Node[initialSize]; this.url = url;}

    //creates a hashcode based on the chars
    int hashCode(String word){
        return Math.abs(word.hashCode() % data.length);
    }

    //if index is empty, creates new node. Else, check to see if value at current index matches and increment
    //as necessary or create new Node chaining for collisions
    void put(String key){
            int index = hashCode(key);
            Node current = new Node(key);
            if (data[index] == null) {
                current.setIndex(index);
                data[index] = current;
                count++;
            } else {
                if (!searchAndInc(data[index], key)) {
                    current.setIndex(index);
                    current.next = data[index];
                    data[index] = current;
                    count++;
                }
            }
            if (++count > data.length * 0.65){
                resize();
            }


    }

    Set<String> getKeySet(){
        Set<String> keySet = new HashSet<String>();
        for(int i = 0; i < data.length; i++){
            if(data[i] != null){
                for(Node node = data[i]; node != null; node = node.next){
                    keySet.add(node.word);
                }
            }
        }
        return keySet;
    }

    int get(String key){
        int index = hashCode(key);

        for(Node n = data[index]; n!= null; n = n.next){
            if (index == hashCode(n.word) && n.word.equals(key)){
                return n.getFreq();
            }
        }
         return 0;

    }

    void resize(){
        Node[] largerTable = new Node[data.length*2];
        for(int i = 0; i<data.length;i++){
            Node nodes = data[i];
            while(nodes != null){
                Node next = nodes.next;
                int index = hashCode(nodes.word);
                nodes.next = largerTable[index];
                largerTable[index] = nodes;
                nodes = next;

            }
        }
        data = largerTable;
    }


    void printTable(){
        for(int i = 0; i < data.length; i++){
            if(data[i] != null){
                Node current = data[i];
                System.out.println(current.word + " ---> " + current.getFreq());
                while(current.hasNext()){
                    current = current.next;
                    System.out.println(current.word + " ---> " + current.getFreq());
                }
            }

        }
    }

    boolean searchAndInc(Node n, String key){
        if(n == null){
            return false;
        }
        if(n.word.equalsIgnoreCase(key)){
            n.freq++;
            return true;
        }
        return searchAndInc(n.next,key);

    }


}
