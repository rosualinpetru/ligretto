/*
 * Created by JFormDesigner on Sat Oct 23 21:42:52 EEST 2021
 */

package gui;

import gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * @author Tania Topciov
 */
public class StartFrame extends JFrame {

    private FrameManager frameManager;

    public StartFrame() {
        initComponents();

        frameManager = FrameManager.getInstance();
        frameManager.setCurrentFrame(this);

        setStartButtonClickEventListener(e -> {
            frameManager.switchToGameSettingsFrame();
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        backgroundPanel1 = new BackgroundPanel();
        panel1 = new JPanel();
        startButton = new JButton();

        //======== this ========
        setVisible(true);
        setMinimumSize(new Dimension(880, 620));
        setResizable(false);
        setTitle("Ligretto");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //---- backgroundPanel1 ----
        backgroundPanel1.setMaximumSize(new Dimension(880, 600));
        backgroundPanel1.setMinimumSize(new Dimension(880, 600));
        backgroundPanel1.setPreferredSize(new Dimension(880, 600));
        contentPane.add(backgroundPanel1, BorderLayout.CENTER);

        //======== panel1 ========
        {
            panel1.setLayout(new BorderLayout(2, 2));

            //---- startButton ----
            startButton.setText("Let's play!");
            startButton.setFont(new Font("Segoe Print", Font.BOLD, 18));
            startButton.setForeground(new Color(0, 153, 51));
            startButton.setBackground(Color.white);
            startButton.setMaximumSize(new Dimension(880, 40));
            startButton.setMinimumSize(new Dimension(880, 40));
            startButton.setPreferredSize(new Dimension(880, 40));
            panel1.add(startButton, BorderLayout.CENTER);
        }
        contentPane.add(panel1, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private BackgroundPanel backgroundPanel1;
    private JPanel panel1;
    private JButton startButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void setStartButtonClickEventListener(Consumer<MouseEvent> consumer) {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }
}
