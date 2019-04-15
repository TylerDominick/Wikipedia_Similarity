import javax.swing.*;

class InputForm extends JPanel {
    JTextField inputUrl;
    JButton findSimilar;


     InputForm(){
        inputUrl = new JTextField("Enter Url" ,45);
        findSimilar = new JButton("Find Similar");

        add(inputUrl);
        add(findSimilar);
    }
}
