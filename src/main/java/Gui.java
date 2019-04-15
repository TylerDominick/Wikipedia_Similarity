import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Gui extends JFrame{
    private InputForm inputForm;
    JDialog dialog;
    JOptionPane optionPane;




     Gui() {
         inputForm = new InputForm();
            Container c = getContentPane();
            c.add(inputForm);
            inputForm.findSimilar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Controller.run(inputForm.inputUrl.getText());
                    optionPane = new JOptionPane();
                    optionPane.setMessage("Most similar: " + Controller.bestPage +"\nClosest Mediod: " + Controller.medioid);
                    optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                    dialog = optionPane.createDialog(null,"Wikipedia Similarity");
                    dialog.setVisible(true);
                }
            });

    }

}
