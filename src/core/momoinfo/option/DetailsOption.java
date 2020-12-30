package core.momoinfo.option;

import java.util.Arrays;

public enum DetailsOption {
    IN_DETAILS("In"),
    OUT_DETAILS("Out"),
    ALL_DETAILS("All"),
    EXIT_DETAILS("EXIT");

    private final String select;

    DetailsOption(String select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return select;
    }

    public static DetailsOption parseHistoryOption(String select) {
        return Arrays.stream(DetailsOption.values())
                .filter(historyOption -> historyOption.toString().equalsIgnoreCase(select))
                .findAny()
                .orElse(null);
    }
}
