package utils;

import core.card.CardColour;
import core.card.CardNumber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CardsLoader {
    private static final CardsLoader cardsLoader = new CardsLoader();
    private static final String CARD_BACK = "card_back";

    private final Map<String, BufferedImage> cardImages = new HashMap<>();

    private CardsLoader() {
    }

    public static CardsLoader getInstance() {
        return cardsLoader;
    }

    public void loadImages() {
        CardNumber[] numbers = CardNumber.values();
        CardColour[] colors = CardColour.values();

        for (CardColour color : colors) {
            for (CardNumber number : numbers) {

                try {
                    String cardName = getCardName(color, number);
                    String path = "/images/cards/" + cardName + ".png";
                    BufferedImage img = ImageIO.read(Objects.requireNonNull(CardsLoader.class.getResource(path)));
                    cardImages.put(cardName, img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        try {
            String path = "/images/cards/" + CARD_BACK + ".png";
            BufferedImage img = ImageIO.read(Objects.requireNonNull(CardsLoader.class.getResource(path)));
            cardImages.put(CARD_BACK, img);
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public BufferedImage getCard(CardColour cardColour, CardNumber cardNumber) {
        try {
            return cardImages.get(getCardName(cardColour, cardNumber));
        } catch (Exception e) {
            return null;
        }
    }

    public BufferedImage getCardBack() {
        return cardImages.get(CARD_BACK);
    }

    private String getCardName(CardColour cardColour, CardNumber cardNumber) {
        return cardColour.name() + "_" + cardNumber.name();
    }
}
