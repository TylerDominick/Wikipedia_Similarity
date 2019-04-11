import java.io.Serializable;

public class Btree_Node implements Serializable {
    static final int numberOfKeys = 8;
    long[] keys = new long[(2*numberOfKeys)-1];
    long[] children = new long[(2*numberOfKeys)];
    int leaf;
    long nodeID;
    int count;

    public Btree_Node(long id){
        this.nodeID = id;
        leaf = 1;
        count = 0;
        for(int i = 0; i < keys.length; i++){
            keys[i] = Long.MIN_VALUE;
        }
        for(int i = 0; i<children.length; i++){
            children[i] = Long.MIN_VALUE;
        }
    }
}
