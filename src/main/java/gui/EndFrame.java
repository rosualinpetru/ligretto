package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EndFrame extends JFrame{
    // frame
    JFrame f;
    //panel
    JPanel p, panelButtons;
    // Table
    JTable j;
    //Buttons
    JButton nextRound, endGame;

    // Constructor
    public EndFrame()
    {
        // Frame initialization
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image frameIcon = new ImageIcon(getClass().getResource("/images/logo.png")).getImage();
        f.setIconImage(frameIcon);

        f.setResizable(false);

        // Frame Title
        f.setTitle("Ligretto - End Game");

        p = new JPanel();
        BoxLayout boxlayout = new BoxLayout(p, BoxLayout.Y_AXIS);
        p.setLayout(boxlayout);

        // Data to be displayed in the JTable
        String[][] data = {
                { "Ana", "11", "39" },
                { "Tania", "14", "30" }
        };

        // Column Names
        String[] columnNames = { "Username", "Last Round", "Total" };

        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        p.add(sp);
        // Frame Size

        // Frame Visible = true
        f.setVisible(true);

        panelButtons = new JPanel();
        BoxLayout boxlayout2 = new BoxLayout(panelButtons, BoxLayout.X_AXIS);
        panelButtons.setLayout(boxlayout2);

        nextRound = new JButton();
        endGame = new JButton();

        nextRound.setText("Next Round");
        nextRound.setForeground(new Color(0, 153, 51));
        nextRound.setFont(new Font("Segoe Print", Font.BOLD, 10));
        nextRound.setMaximumSize(new Dimension(100, 35));
        nextRound.setMinimumSize(new Dimension(100, 35));
        nextRound.setPreferredSize(new Dimension(100, 35));
        nextRound.addActionListener(this::actionPerformed);

        panelButtons.add(nextRound);
        panelButtons.add(Box.createRigidArea(new Dimension(100, 0)));

        endGame.setText("End Game");
        endGame.setForeground(new Color(0, 153, 51));
        endGame.setFont(new Font("Segoe Print", Font.BOLD, 10));
        endGame.setMaximumSize(new Dimension(100, 35));
        endGame.setMinimumSize(new Dimension(100, 35));
        endGame.setPreferredSize(new Dimension(100, 35));
        endGame.addActionListener(this::actionPerformed);

        panelButtons.add(endGame);

        p.add(panelButtons);
        f.add(p);
        f.setSize(600, 240);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == endGame)
        {
            f.dispose();
        }
        if(e.getSource() == nextRound)
        {
            Board board = new Board();
            this.setContentPane(board.getContentPane());
            this.setVisible(true);
            f.dispose();
        }
    }

}