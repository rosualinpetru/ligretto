package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPanel extends JPanel implements ActionListener {
    private JLabel label;
    private JTextField textField;
    private JButton submit;
    public String username;
    public String symbol;
    private JComboBox comboBox;

    public ConfigPanel(){
        initComponents();
    }

    public void initComponents(){
        setBackground(Color.WHITE);
        setVisible(true);
        setLayout(new FlowLayout());

        label = new JLabel("Username: ");
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.ORANGE);
        label.setFont(new Font("MV Boli", Font.BOLD, 56));
        add(label, BorderLayout.PAGE_START);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(250,40));
        textField.setFont(new Font("MV Boli", Font.PLAIN, 25));
        textField.setForeground(Color.GRAY);
        textField.setCaretColor(Color.WHITE);

        submit = new JButton("Submit");
        submit.setBackground(Color.ORANGE);
        submit.addActionListener(this);

        String[] symbols = {"sqare", "circle", "triangle", "diamond", "star", "trapeze"};
        comboBox = new JComboBox(symbols);

        this.add(textField);
        this.add(submit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            username = textField.getText();
            System.out.println(username);
        }
    }
}
