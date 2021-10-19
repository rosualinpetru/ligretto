package core.event;

/**
 * The model for the event which all players subscribe to, representing
 * the placement of a card on the table.
 */
public record CardPlacedEvent(int position) {
}
