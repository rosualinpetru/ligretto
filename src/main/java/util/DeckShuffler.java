package util;

import model.card.Card;
import model.card.CardColour;
import model.card.CardNumber;
import model.player.Player;
import model.deck.FacedUpCards;
import model.deck.ShufflingDeck;
import model.deck.TargetDeck;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;

public class DeckShuffler {

    private static final int FACED_UP_CARDS_COUNT = 3;
    private static final int TARGET_DECK_SIZE = 10;
    private static final int SHUFFLING_DECK_SIZE = 27;

    private DeckShuffler() {

    }

    public static Triplet<FacedUpCards, TargetDeck, ShufflingDeck> shuffleStartingDecks(Player player) {
        var deck = Arrays.stream(CardNumber.values())
                .flatMap(number ->
                        Arrays.stream(CardColour.values()).map(colour -> new Card(colour, number, player)))
                .collect(Collectors.toList());
        Collections.shuffle(deck);
        return divideInSmallerDecks(deck);
    }

    private static Triplet<FacedUpCards, TargetDeck, ShufflingDeck> divideInSmallerDecks(List<Card> fullDeck) {
        var facedUpCardsList = fullDeck.stream().limit(FACED_UP_CARDS_COUNT).collect(Collectors.toList());
        fullDeck.removeAll(facedUpCardsList);

        var targetDeckList = fullDeck.stream().limit(TARGET_DECK_SIZE).collect(Collectors.toList());
        fullDeck.removeAll(targetDeckList);

        var shufflingDeck = fullDeck.stream().limit(SHUFFLING_DECK_SIZE).collect(Collectors.toList());
        fullDeck.removeAll(shufflingDeck);

        var facedUpCards = new HashMap<Integer, Card>();
        for (int i = 1; i <= FACED_UP_CARDS_COUNT; i++) {
            facedUpCards.put(i, facedUpCardsList.get(i - 1));
        }

        var targetDeck = new Stack<Card>();
        targetDeck.addAll(targetDeckList);

        return Triplet.with(new FacedUpCards(facedUpCards), new TargetDeck(targetDeck), new ShufflingDeck(shufflingDeck));
    }


}


