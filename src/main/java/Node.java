import java.io.Serializable;

public class Node implements Serializable {
        String word;
        int freq;
        Node next = null;
        int index;

        Node(String word){
            this.word = word;
            this.freq = 1;
        }

        int getFreq(){
            return this.freq;
        }

        boolean hasNext(){
            return next != null;
        }

        void setIndex(int index){
            this.index = index;
        }
        int getIndex(){
            return this.index;
        }
    }


