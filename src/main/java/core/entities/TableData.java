package core.entities;

import core.card.Card;
import core.deck.OnTableDeck;

import java.util.List;

public record TableData(List<Card> visibleCards, List<OnTableDeck> placedDecks) {

}
