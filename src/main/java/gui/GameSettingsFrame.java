/*
 * Created by JFormDesigner on Tue Nov 09 15:36:52 EET 2021
 */

package gui;

import main.Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.function.Consumer;

/**
 * @author Tania Topciov
 */
public class GameSettingsFrame extends JFrame {
    public GameSettingsFrame() {
        initComponents();
        addItemsToShapeComboBox();
        addItemsToGameModeComboBox();
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
        nameField = new JTextField();
        label16 = new JLabel();
        label17 = new JLabel();
        label18 = new JLabel();
        panel4 = new JPanel();
        panel6 = new JPanel();
        label2 = new JLabel();
        gameModeComboBox = new JComboBox();
        difficultyComboBox = new JComboBox();
        label21 = new JLabel();
        label22 = new JLabel();
        label23 = new JLabel();
        panel2 = new JPanel();
        radioButton3 = new JRadioButton();
        radioButton4 = new JRadioButton();
        radioButton5 = new JRadioButton();
        radioButton6 = new JRadioButton();
        label26 = new JLabel();
        label27 = new JLabel();
        label28 = new JLabel();
        label29 = new JLabel();
        label30 = new JLabel();
        label34 = new JLabel();
        label58 = new JLabel();
        label220 = new JLabel();
        label210 = new JLabel();
        buttonBar = new JPanel();
        startButton = new JButton();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setTitle("Ligretto - Game Configuration");
        setMinimumSize(new Dimension(880, 660));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setMaximumSize(new Dimension(880, 640));
            dialogPane.setMinimumSize(new Dimension(880, 640));
            dialogPane.setPreferredSize(new Dimension(880, 640));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(7, 4));
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

                    //---- nameField ----
                    nameField.setFont(new Font("Segoe Print", Font.PLAIN, 14));
                    panel3.add(nameField);
                }
                contentPanel.add(panel3);
                contentPanel.add(label16);
                contentPanel.add(label17);

                //---- label18 ----
                label18.setText("Game mode:");
                label18.setForeground(new Color(0, 153, 51));
                label18.setFont(new Font("Segoe Print", Font.BOLD, 20));
                contentPanel.add(label18);

                //======== panel4 ========
                {
                    panel4.setLayout(new GridLayout(3, 0));

                    //---- label2 ----
                    label2.setFont(new Font("Segoe Print", Font.PLAIN, 16));
                    panel4.add(label2);

                    //---- gameModeComboBox ----
                    gameModeComboBox.setFont(new Font("Segoe Print", Font.PLAIN, 14));
                    panel4.add(gameModeComboBox);
                }
                contentPanel.add(panel4);
                contentPanel.add(label21);
                contentPanel.add(label22);

                label58.setText("Difficulty:");
                label58.setForeground(new Color(0, 153, 51));
                label58.setFont(new Font("Segoe Print", Font.BOLD, 20));
                contentPanel.add(label58);

                {
                    panel6.setLayout(new GridLayout(3, 0));

                    label34.setFont(new Font("Segoe Print", Font.PLAIN, 16));
                    panel6.add(label34);

                    difficultyComboBox.setFont(new Font("Segoe Print", Font.PLAIN, 14));
                    panel6.add(difficultyComboBox);
                }
                contentPanel.add(panel6);
                contentPanel.add(label210);
                contentPanel.add(label220);

                //---- label23 ----
                label23.setText("Players number:");
                label23.setFont(new Font("Segoe Print", Font.BOLD, 20));
                label23.setForeground(new Color(0, 153, 51));
                contentPanel.add(label23);

                //======== panel2 ========
                {
                    panel2.setLayout(new GridLayout(1, 4));

                    //---- radioButton3 ----
                    radioButton3.setText("3");
                    radioButton3.setSelected(true);
                    panel2.add(radioButton3);

                    //---- radioButton4 ----
                    radioButton4.setText("4");
                    panel2.add(radioButton4);

                    //---- radioButton5 ----
                    radioButton5.setText("5");
                    panel2.add(radioButton5);

                    //---- radioButton6 ----
                    radioButton6.setText("6");
                    panel2.add(radioButton6);

                    buttonGroup = new ButtonGroup();
                    buttonGroup.add(radioButton3);
                    buttonGroup.add(radioButton4);
                    buttonGroup.add(radioButton5);
                    buttonGroup.add(radioButton6);
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
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 0, 0, 85, 85, 0};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0};

                //---- startButton ----
                startButton.setText("Start");
                startButton.setForeground(new Color(0, 153, 51));
                startButton.setFont(new Font("Segoe Print", Font.BOLD, 16));
                startButton.setMaximumSize(new Dimension(100, 35));
                startButton.setMinimumSize(new Dimension(100, 35));
                startButton.setPreferredSize(new Dimension(100, 35));
                buttonBar.add(startButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));
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
    private JLabel label34;
    private JPanel panel3;
    private JPanel panel6;
    private JLabel label1;
    private JTextField nameField;
    private JLabel label16;
    private JLabel label17;
    private JLabel label18;
    private JPanel panel4;
    private JLabel label2;
    private JComboBox gameModeComboBox;
    private JComboBox difficultyComboBox;
    private JLabel label21;
    private JLabel label210;
    private JLabel label22;
    private JLabel label220;
    private JLabel label23;
    private JLabel label58;
    private JPanel panel2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JLabel label26;
    private JLabel label27;
    private JLabel label28;
    private JLabel label29;
    private JLabel label30;
    private JPanel buttonBar;
    private JButton startButton;
    private ButtonGroup buttonGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setStartButtonClickEventListener(Consumer<MouseEvent> consumer) {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public String getGameModeComboBoxSelectedItem() {
        return gameModeComboBox.getSelectedItem().toString();
    }

    public String getDifficultyComboBoxSelectedItem() {
        return difficultyComboBox.getSelectedItem().toString();
    }

    public String getNameFieldContent() {
        return nameField.getText();
    }

    public void addItemsToShapeComboBox() {
        gameModeComboBox.addItem(Main.ONLY_BOTS_PLAYING);
        gameModeComboBox.addItem(Main.PLAY_WITH_BOTS);
    }

    public void addItemsToGameModeComboBox() {
        difficultyComboBox.addItem(Main.EASY);
        difficultyComboBox.addItem(Main.MEDIUM);
        difficultyComboBox.addItem(Main.HARD);
    }

    public int getNumberOfSelectedBots() {
        Enumeration<AbstractButton> elements = buttonGroup.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton element = elements.nextElement();
            if (element.isSelected()) {
                return Integer.parseInt(element.getText());
            }
        }
        return 3;
    }
}
