package core.util;

import core.card.Card;
import core.card.CardColour;
import core.card.CardNumber;
import core.entities.Player;
import core.deck.FacedUpCards;
import core.deck.ShufflingDeck;
import core.deck.TargetDeck;
import org.javatuples.Triplet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Collections.shuffle(deck, new Random(ThreadLocalRandom.current().nextLong()));
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
        AtomicInteger counter = new AtomicInteger();
        Stream.generate(counter::incrementAndGet)
                .takeWhile(it -> it <= FACED_UP_CARDS_COUNT)
                .forEach(it -> facedUpCards.put(it, facedUpCardsList.get(it - 1)));

        var targetDeck = new Stack<Card>();
        targetDeck.addAll(targetDeckList);

        return Triplet.with(new FacedUpCards(facedUpCards), new TargetDeck(targetDeck), new ShufflingDeck(shufflingDeck));
    }


}


