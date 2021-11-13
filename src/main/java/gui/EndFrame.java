package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EndFrame extends JFrame {
    // frame
    JFrame f;
    //panel
    JPanel p, panelButtons;
    // Table
    JTable j;
    //Buttons
    JButton nextRound, endGame;

    // Constructor
    public EndFrame() {
        // Frame initialization
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image frameIcon = new ImageIcon(getClass().getResource("/images/logo.png")).getImage();
        f.setIconImage(frameIcon);

        f.setResizable(false);

        // Frame Title
        f.setTitle("Ligretto - End Game");

        p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        BoxLayout boxlayout = new BoxLayout(p, BoxLayout.Y_AXIS);
        p.setLayout(boxlayout);

        // Data to be displayed in the JTable
        String[][] data = {
                {"Ana", "11", "39"},
                {"Tania", "14", "30"}
        };

        // Column Names
        String[] columnNames = {"Username", "Current Round", "Total"};

        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);
        j.setFont(new Font("Segoe Print", Font.BOLD, 16));
        j.getTableHeader().setFont(new Font("Segoe Print", Font.BOLD, 24));
        j.getTableHeader().setForeground(new Color(0, 153, 51));
        j.setRowHeight(30);

        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < 3; i++) {
            TableColumn col = j.getColumnModel().getColumn(i);
            col.setCellRenderer(dtcr);
        }

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        p.add(sp);
        // Frame Size

        // Frame Visible = true
        f.setVisible(true);

        panelButtons = new JPanel();
        panelButtons.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        BoxLayout boxlayout2 = new BoxLayout(panelButtons, BoxLayout.X_AXIS);
        panelButtons.setLayout(boxlayout2);

        nextRound = new JButton();
        endGame = new JButton();

        nextRound.setText("Next Round");
        nextRound.setForeground(new Color(0, 153, 51));
        nextRound.setFont(new Font("Segoe Print", Font.BOLD, 20));
        nextRound.setMaximumSize(new Dimension(160, 50));
        nextRound.setMinimumSize(new Dimension(160, 50));
        nextRound.setPreferredSize(new Dimension(160, 50));
        nextRound.addActionListener(this::actionPerformed);

        panelButtons.add(nextRound);
        panelButtons.add(Box.createRigidArea(new Dimension(100, 0)));

        endGame.setText("End Game");
        endGame.setForeground(new Color(0, 153, 51));
        endGame.setFont(new Font("Segoe Print", Font.BOLD, 20));
        endGame.setMaximumSize(new Dimension(160, 50));
        endGame.setMinimumSize(new Dimension(160, 50));
        endGame.setPreferredSize(new Dimension(160, 50));
        endGame.addActionListener(this::actionPerformed);

        panelButtons.add(endGame);

        p.add(panelButtons);
        f.add(p);
        f.setSize(880, 660);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == endGame) {
            f.dispose();
        }
        if (e.getSource() == nextRound) {
            Board board = new Board();
            this.setContentPane(board.getContentPane());
            this.setVisible(true);
            f.dispose();
        }
    }

}