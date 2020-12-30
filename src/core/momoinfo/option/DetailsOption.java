package core.momoinfo.option;

import java.util.Arrays;

public enum DetailsOption {
    IN_DETAILS("In", "Incoming Details"),
    OUT_DETAILS("Out", "Outgoing Details"),
    ALL_DETAILS("All", "In Out Details"),
    EXIT_DETAILS("EXIT", "EXIT");

    private final String select;
    private final String header;

    DetailsOption(String select, String header) {
        this.select = select;
        this.header = header;
    }

    @Override
    public String toString() {
        return select;
    }

    public String getHeader() {
        return header;
    }

    public static DetailsOption parseHistoryOption(String select) {
        return Arrays.stream(DetailsOption.values())
                .filter(historyOption -> historyOption.toString().equalsIgnoreCase(select))
                .findAny()
                .orElse(null);
    }
}
