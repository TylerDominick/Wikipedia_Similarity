
import javax.swing.*;


public class Main {

    public static void main(String[] args) {
        // write your code here
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                JFrame frame = new Gui();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800,100);
                frame.setVisible(true);
            }
        });

    }

}
