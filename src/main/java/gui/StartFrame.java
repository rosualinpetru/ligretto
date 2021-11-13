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

    JButton startButton;
    JPanel buttonPanel;
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
        buttonPanel = new JPanel();
        startButton = new JButton();

        //======== this ========
        setVisible(true);
        setResizable(false);
        setMinimumSize(new Dimension(880, 660));
        setMaximumSize(new Dimension(880, 660));
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        backgroundPanel1.setMaximumSize(new Dimension(880, 640));
        backgroundPanel1.setMinimumSize(new Dimension(880, 640));
        contentPane.add(backgroundPanel1, BorderLayout.CENTER);

        setTitle("Ligretto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonPanel.setBackground(Color.white);
        buttonPanel.setMaximumSize(new Dimension(880, 20));
        buttonPanel.setMinimumSize(new Dimension(880, 20));
        buttonPanel.setLayout(new BorderLayout(2, 2));

        //---- startButton ----
        startButton.setText("Start Game");
        startButton.setForeground(Color.white);
        startButton.setBackground(Color.decode("0x009933"));
//        startButton.addActionListener(this::actionPerformed);
        buttonPanel.add(startButton, BorderLayout.CENTER);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        Image frameIcon = new ImageIcon(getClass().getResource("/images/logo.png")).getImage();
        setIconImage(frameIcon);

        pack();
        setLocationRelativeTo(getOwner());

        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private BackgroundPanel backgroundPanel1;

    public void setStartButtonClickEventListener(Consumer<MouseEvent> consumer) {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
