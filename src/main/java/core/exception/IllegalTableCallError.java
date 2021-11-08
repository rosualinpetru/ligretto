package core.exception;

import core.entities.TableState;

public class IllegalTableCallError extends Error {

    private final String message;

    public IllegalTableCallError(TableState expected, TableState actual) {
        message = "In order to call \"" + getStackTrace()[0].getMethodName() + "\", the table state should be " + expected + ", yet it is " + actual + "!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
