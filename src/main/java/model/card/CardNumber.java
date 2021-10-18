package model.card;

import java.util.Optional;

public enum CardNumber {
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN;

    public boolean isFirst() {
        return this == ONE;
    }

    public boolean isLast() {
        return this == TEN;
    }

    public Optional<CardNumber> next() {
        return ofInt(ordinal() + 2);
    }

    private Optional<CardNumber> ofInt(int value) {
        return switch (value) {
            case 1 -> Optional.of(ONE);
            case 2 -> Optional.of(TWO);
            case 3 -> Optional.of(THREE);
            case 4 -> Optional.of(FOUR);
            case 5 -> Optional.of(FIVE);
            case 6 -> Optional.of(SIX);
            case 7 -> Optional.of(SEVEN);
            case 8 -> Optional.of(EIGHT);
            case 9 -> Optional.of(NINE);
            case 10 -> Optional.of(TEN);
            default -> Optional.empty();
        };
    }
}
