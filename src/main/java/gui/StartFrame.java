/*
 * Created by JFormDesigner on Sat Oct 23 21:42:52 EEST 2021
 */

package gui;

import java.awt.*;
import javax.swing.*;

/**
 * @author Tania Topciov
 */
public class StartFrame extends JFrame {
    public StartFrame() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        backgroundPanel1 = new BackgroundPanel();

        //======== this ========
        setVisible(true);
        setResizable(false);
        setMinimumSize(new Dimension(880, 640));
        setMaximumSize(new Dimension(880, 640));

        Image frameIcon = new ImageIcon(getClass().getResource("/images/logo.png")).getImage();
        setIconImage(frameIcon);

        var contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(backgroundPanel1);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private BackgroundPanel backgroundPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
