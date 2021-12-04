package gui;

import gui.managers.ScoreManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class EndFrame extends JFrame {
    //panel
    JPanel p, panelButtons;
    // Table
    JTable j;
    //Buttons
    JButton nextRound, endGame;

    // Constructor
    public EndFrame(ArrayList<String> data) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image frameIcon = new ImageIcon(getClass().getResource("/images/logo.png")).getImage();
        this.setIconImage(frameIcon);

        this.setResizable(false);

        // Frame Title
        this.setTitle("Ligretto - End Game");

        p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        BoxLayout boxlayout = new BoxLayout(p, BoxLayout.Y_AXIS);
        p.setLayout(boxlayout);

        // Data to be displayed in the JTable
        /*String[][] data = {
                {"Ana", "11", "39"},
                {"Tania", "14", "30"}
        };*/
        HashMap<String, Long> roundScores = new HashMap<>(data.size());

        for (int i = 0; i < data.size() - 1; i += 2) {
            roundScores.put(data.get(i), Long.parseLong(data.get(i + 1)));
        }

        ScoreManager scoreManager = ScoreManager.getInstance();
        scoreManager.updateScores(roundScores);

        String[][] d = new String[data.size() / 2][3];
        int index = 0;
        for (int i = 0; i < data.size() - 1; i += 2) {
            d[index][0] = data.get(i);
            d[index][1] = data.get(i + 1);
            d[index][2] = scoreManager.getValueFromKey(data.get(i));
            index++;
        }

        // Column Names
        String[] columnNames = {"Username", "Current Round", "Total"};

        // Initializing the JTable
        j = new JTable(d, columnNames);
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

        // Frame Visible = true
        this.setVisible(true);

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

        panelButtons.add(nextRound);
        panelButtons.add(Box.createRigidArea(new Dimension(100, 0)));

        endGame.setText("End Game");
        endGame.setForeground(new Color(0, 153, 51));
        endGame.setFont(new Font("Segoe Print", Font.BOLD, 20));
        endGame.setMaximumSize(new Dimension(160, 50));
        endGame.setMinimumSize(new Dimension(160, 50));
        endGame.setPreferredSize(new Dimension(160, 50));

        panelButtons.add(endGame);

        p.add(panelButtons);
        this.add(p);
        this.setSize(880, 680);
    }

    public void setNextRoundButtonClickEventListener(Consumer<MouseEvent> consumer) {
        nextRound.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    public void setEndGameButtonClickEventListener(Consumer<MouseEvent> consumer) {
        endGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

}