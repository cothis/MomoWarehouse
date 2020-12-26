package core.momoinfo.option;

import java.util.Arrays;

public enum InOutOption {
    IN_SPOT("입고"),
    OUT_SPOT("출고"),
    EXIT_SPOT("종료");

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
                .filter(historyOption -> historyOption.toString().equals(select))
                .findAny()
                .orElse(null);
    }
}
