package core.momoinfo.option;

import java.util.Arrays;

public enum HistoryOption {
    IN_HISTORY("In"),
    OUT_HISTORY("Out"),
    ALL_HISTORY("All"),
    TOTAL_PAYMENT("Total Payment"),
    EXIT_HISTORY("EXIT");

    private final String select;

    HistoryOption(String select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return select;
    }

    public static HistoryOption parseHistoryOption(String select) {
        return Arrays.stream(HistoryOption.values())
                .filter(historyOption -> historyOption.toString().equalsIgnoreCase(select))
                .findAny()
                .orElse(null);
    }
}
