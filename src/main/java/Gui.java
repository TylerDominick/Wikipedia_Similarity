import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Gui extends JFrame{
    private Result result;
    private InputForm inputForm;
    JDialog dialog;
    JLabel label;




    public Gui() {
         inputForm = new InputForm();
         result = new Result();

         Container c = getContentPane();
         c.add(inputForm);
         inputForm.findSimilar.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 Controller.createTables(inputForm.inputUrl.getText());
                 HashTable s = Similarity.cosineSimilarity(Controller.getInputTable(),Controller.getTables());
                 System.out.println(s.url);
                 dialog = new JDialog();
                 label = new JLabel(s.url);
                 dialog.add(label);
                 dialog.setSize(600, 75);
                 dialog.setVisible(true);
             }
         });
    }
}
