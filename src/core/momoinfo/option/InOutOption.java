package core.momoinfo.option;

import java.util.Arrays;

public enum InOutOption {
    IN_SPOT("In"),
    OUT_SPOT("Out"),
    EXIT_SPOT("EXIT");

    private final String select;

    InOutOption(String select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return select;
    }

    public static InOutOption parseInOutOption(String select) {
        return Arrays.stream(InOutOption.values())
                .filter(historyOption -> historyOption.toString().equalsIgnoreCase(select))
                .findAny()
                .orElse(null);
    }
}
