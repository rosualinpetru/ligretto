package core.card;

public enum CardColour {
    RED, GREEN, BLUE, YELLOW;

    @Override
    public String toString() {
        return "" + super.toString().charAt(0);
    }
}
