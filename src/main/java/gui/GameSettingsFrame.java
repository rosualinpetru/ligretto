/*
 * Created by JFormDesigner on Tue Nov 09 15:36:52 EET 2021
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Tania Topciov
 */
public class GameSettingsFrame extends JFrame {
    public GameSettingsFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label7 = new JLabel();
        label19 = new JLabel();
        label13 = new JLabel();
        label25 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        label12 = new JLabel();
        label14 = new JLabel();
        panel3 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label16 = new JLabel();
        label17 = new JLabel();
        label18 = new JLabel();
        panel4 = new JPanel();
        label2 = new JLabel();
        JComboBox comboBox1 = new JComboBox();
        label21 = new JLabel();
        label22 = new JLabel();
        label23 = new JLabel();
        panel2 = new JPanel();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        radioButton1 = new JRadioButton();
        radioButton4 = new JRadioButton();
        label26 = new JLabel();
        label27 = new JLabel();
        label28 = new JLabel();
        label29 = new JLabel();
        label30 = new JLabel();
        buttonBar = new JPanel();
        startGameButton = new JButton();
        cancelButton = new JButton();
        label3 = new JLabel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setTitle("Ligretto - Game Configuration");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(6, 4));
                contentPanel.add(label7);

                //---- label19 ----
                label19.setText("Game ");
                label19.setFont(new Font("Segoe Print", Font.BOLD, 48));
                label19.setForeground(new Color(0, 153, 51));
                label19.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label19);

                //---- label13 ----
                label13.setText("Settings");
                label13.setForeground(new Color(0, 153, 51));
                label13.setFont(new Font("Segoe Print", Font.BOLD, 48));
                contentPanel.add(label13);
                contentPanel.add(label25);
                contentPanel.add(label8);
                contentPanel.add(label9);
                contentPanel.add(label10);
                contentPanel.add(label11);
                contentPanel.add(label12);

                //---- label14 ----
                label14.setText("Introduce name:");
                label14.setFont(new Font("Segoe Print", Font.BOLD, 20));
                label14.setForeground(new Color(0, 153, 51));
                contentPanel.add(label14);

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayout(3, 0));
                    panel3.add(label1);

                    //---- textField1 ----
                    textField1.setFont(new Font("Segoe Print", Font.PLAIN, 16));
                    panel3.add(textField1);
                }
                contentPanel.add(panel3);
                contentPanel.add(label16);
                contentPanel.add(label17);

                //---- label18 ----
                label18.setText("Select shape:");
                label18.setForeground(new Color(0, 153, 51));
                label18.setFont(new Font("Segoe Print", Font.BOLD, 20));
                contentPanel.add(label18);

                //======== panel4 ========
                {
                    panel4.setLayout(new GridLayout(3, 0));

                    //---- label2 ----
                    label2.setFont(new Font("Segoe Print", Font.PLAIN, 16));
                    panel4.add(label2);

                    //---- comboBox1 ----
                    comboBox1.setFont(new Font("Segoe Print", Font.PLAIN, 16));
                    comboBox1.addItem("circle");
                    comboBox1.addItem("square");
                    comboBox1.addItem("triangle");
                    comboBox1.addItem("diamond");
                    comboBox1.addItem("star");
                    comboBox1.addItem("spiral");
                    panel4.add(comboBox1);
                }
                contentPanel.add(panel4);
                contentPanel.add(label21);
                contentPanel.add(label22);

                //---- label23 ----
                label23.setText("Players number:");
                label23.setFont(new Font("Segoe Print", Font.BOLD, 20));
                label23.setForeground(new Color(0, 153, 51));
                contentPanel.add(label23);

                //======== panel2 ========
                {
                    panel2.setLayout(new GridLayout(1, 4));

                    //---- radioButton2 ----
                    radioButton2.setText("3");
                    panel2.add(radioButton2);

                    //---- radioButton3 ----
                    radioButton3.setText("4");
                    panel2.add(radioButton3);

                    //---- radioButton1 ----
                    radioButton1.setText("5");
                    panel2.add(radioButton1);

                    //---- radioButton4 ----
                    radioButton4.setText("6");
                    panel2.add(radioButton4);
                }
                contentPanel.add(panel2);
                contentPanel.add(label26);
                contentPanel.add(label27);
                contentPanel.add(label28);
                contentPanel.add(label29);
                contentPanel.add(label30);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 0, 85, 85, 0};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0, 0.0};

                //---- startGameButton ----
                startGameButton.setText("Start Game");
                startGameButton.setForeground(new Color(0, 153, 51));
                startGameButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                startGameButton.setMaximumSize(new Dimension(130, 35));
                startGameButton.setMinimumSize(new Dimension(130, 35));
                startGameButton.setPreferredSize(new Dimension(130, 35));
                buttonBar.add(startGameButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setForeground(new Color(0, 153, 51));
                cancelButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                cancelButton.setMaximumSize(new Dimension(130, 35));
                cancelButton.setMinimumSize(new Dimension(130, 35));
                cancelButton.setPreferredSize(new Dimension(130, 35));
                buttonBar.add(cancelButton, new GridBagConstraints(4, 0, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label7;
    private JLabel label19;
    private JLabel label13;
    private JLabel label25;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JLabel label12;
    private JLabel label14;
    private JPanel panel3;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label16;
    private JLabel label17;
    private JLabel label18;
    private JPanel panel4;
    private JLabel label2;
    private JLabel label21;
    private JLabel label22;
    private JLabel label23;
    private JPanel panel2;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton1;
    private JRadioButton radioButton4;
    private JLabel label26;
    private JLabel label27;
    private JLabel label28;
    private JLabel label29;
    private JLabel label30;
    private JPanel buttonBar;
    private JButton startGameButton;
    private JButton cancelButton;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
