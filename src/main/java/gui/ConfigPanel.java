package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPanel extends JPanel implements ActionListener {
    private JLabel label;
    private JTextField textField;
    private JButton submit;
    private JComboBox comboBox;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private JRadioButton radio3;
    private ButtonGroup buttonGroup;

    public String username;
    public String symbol;
    public int bots;

    public ConfigPanel(){
        initComponents();
    }

    public void initComponents(){
        setBackground(Color.WHITE);
        setVisible(true);
        setLayout(new FlowLayout());

        label = new JLabel("Symbol and Username: ");
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

        String[] symbols = {"square", "circle", "triangle", "diamond", "star", "trapeze"};
        comboBox = new JComboBox(symbols);
        comboBox.addActionListener(this);
        add(comboBox);

        radio1 = new JRadioButton("3 bots");
        radio2 = new JRadioButton("4 bots");
        radio3 = new JRadioButton("5 bots");
        radio1.addActionListener(this);
        radio2.addActionListener(this);
        radio3.addActionListener(this);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(radio1);
        buttonGroup.add(radio2);
        buttonGroup.add(radio3);

        this.add(textField);
        this.add(submit);
        this.add(radio1);
        this.add(radio2);
        this.add(radio3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){
            username = textField.getText();
            System.out.println(username);
        }
        if(e.getSource() == comboBox){
            symbol = (String) comboBox.getSelectedItem();
            System.out.println(symbol);
        }
        if(e.getSource() == radio1){
            bots = 3;
            System.out.println(bots);
        }
        if(e.getSource() == radio2){
            bots = 4;
        }
        if(e.getSource() == radio3){
            bots = 5;
        }
    }
}
