import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BTree implements Serializable {

    RandomAccessFile BTree_file;
    Btree_Node root;
    int height;
    int nodeSize = 4056;
    final static int k = 8;

    public BTree(RandomAccessFile file) throws Exception{
        root = null;
        height = 0;
        this.BTree_file = file;
        Btree_Node x = createNode();
        x.leaf = 1;
        x.count = 0;
        writeToDisk(x);
        this.root=x;
    }

    public void insert(BTree tree, int key) throws Exception{
        Btree_Node root = tree.root;
        if(root.count == 2*k-1){
            Btree_Node s = createNode();
            tree.root = s;
            s.leaf = 0;
            s.count = 0;
            s.children[0] = root.nodeID;
            split(s,0,root);
            readyToInsert(s,key);
        }
        readyToInsert(root,key);
    }

    public void readyToInsert(Btree_Node tree, int key) throws Exception{
        int count = tree.count;
        if(tree.leaf ==1){
            while(count >= 1 && key < tree.keys[count - 1]){
                tree.keys[count] = tree.keys[count - 1];
                count--;
            }
            tree.keys[count]=key;
            tree.count++;
            writeToDisk(tree);
        }
        else{
            int i = 0;
            while (i<tree.count && key > tree.keys[i]){
                i++;
            }
            Btree_Node child = readFromDisk(tree.children[i]);
            if(child.count == k*2-1){
                split(tree,i,child);
                if(key>tree.keys[i]){
                    i++;
                }
            }
            child = readFromDisk(tree.children[i]);
            readyToInsert(child,key);
        }
    }

    public void split(Btree_Node parent, int index, Btree_Node child) throws Exception{
        Btree_Node newChild = createNode();
        newChild.leaf = child.leaf;
        newChild.count = k-1;

        for(int i = 0; i<k-1; i++){
            newChild.keys[i] = child.keys[i+k];
        }

        if(child.leaf == 0){
            for(int i =0; i < k; i++){
                newChild.children[i] = child.children[i+k];
            }
        }
        child.count = k - 1;

        for (int i = parent.count; i > index; i--){
            parent.children[i+1] = parent.children[i];
            parent.keys[i] = parent.keys[i-1];
        }

        parent.children[index+1] = newChild.nodeID;
        parent.keys[index] = child.keys[k-1];
        newChild.keys[k-1] = 0;
        parent.leaf = 0;

        for(int i = 0; i < k-1; i++){
            parent.keys[i + k] = 0;
        }

        parent.count++;

        writeToDisk(parent);
        writeToDisk(newChild);
        writeToDisk(parent);
    }

    public Btree_Node createNode() throws Exception{
        BTree_file.seek(BTree_file.length());
        Btree_Node temp = new Btree_Node(BTree_file.getFilePointer());
        writeToDisk(temp);
        return temp;
    }

    public void writeToDisk(Btree_Node node) throws Exception{
        BTree_file.seek(node.nodeID);
        FileChannel f = BTree_file.getChannel();

        ByteBuffer b = ByteBuffer.allocate(nodeSize);
        b.putInt(node.leaf);
        b.putLong(node.nodeID);
        b.putInt(node.count);

        for(long k: node.keys){
            b.putLong(k);
        }

        for(long c: node.children){
            b.putLong(c);
        }

        b.flip();
        f.write(b);
        b.clear();
    }

    public Btree_Node readFromDisk(long position)throws Exception{
        BTree_file.seek(position);
        FileChannel f = BTree_file.getChannel();
        ByteBuffer b = ByteBuffer.allocate(nodeSize);
        f.read(b);
        b.flip();
        Btree_Node temp = new Btree_Node(-1);
        temp.leaf = b.getInt();
        temp.nodeID = b.getLong();
        temp.count = b.getInt();
        for(int i = 0; i < (2 * k)-1; i++){
            temp.keys[i] = b.getLong();
        }
        for(int i = 0; i < 2*k;i++){
            temp.children[i] = b.getLong();
        }
        b.clear();
        return temp;
    }

    public boolean contians(Btree_Node node, int key, String url) throws Exception{
        int i = 0;
        while(i < node.count && key>node.keys[i]){
            i++;
        }
        if(i < node.count && key == node.keys[i]){
            try{
                FileInputStream fin = new FileInputStream("src/main/files/" + node.keys[i]);
                ObjectInputStream oin = new ObjectInputStream(fin);
                HashTable ht = (HashTable) oin.readObject();
                if(ht.url.equalsIgnoreCase(url)){
                    return true;
                }
                return false;
            }catch(Exception e){
                e.printStackTrace();
            }
        }else if(node.leaf == 1){
            return false;
        }else{
            Btree_Node tmp = readFromDisk(node.children[i]);
            return contians(tmp,key,url);

        }return false;
    }

}
