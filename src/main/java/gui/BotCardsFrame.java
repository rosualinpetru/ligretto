package gui;

import core.entities.Bot;

import javax.swing.*;
import java.awt.*;

public class BotCardsFrame extends JFrame {

    private static final int BOT_CARDS_WIDTH = 600;
    private static final int BOT_CARDS_HEIGHT = 680;

    private final JPanel botCardsHolderPanel;

    public BotCardsFrame() throws HeadlessException {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("Bot Cards");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.png")).getImage());

        botCardsHolderPanel = new JPanel();
        botCardsHolderPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        botCardsHolderPanel.setLayout(new BoxLayout(botCardsHolderPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(botCardsHolderPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setMinimumSize(new Dimension(200, 400));
        setPreferredSize(new Dimension(BOT_CARDS_WIDTH, BOT_CARDS_HEIGHT));

        add(scrollPane);
        pack();
        setVisible(true);
    }

    public void addBotCard(Bot bot) {
        BotCardsPanel botCardsPanel = new BotCardsPanel(bot.name);
        bot.linkToCard(botCardsPanel);

        botCardsHolderPanel.add(botCardsPanel);

        pack();
    }
}
