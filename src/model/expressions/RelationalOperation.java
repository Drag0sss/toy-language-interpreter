package model.expressions;

public enum RelationalOperation {
    LESS_THAN("<"),
    LESS_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    GREATER_EQUAL(">=");

    private final String symbol;

    RelationalOperation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
