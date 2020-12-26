package core.momoinfo.option;

import java.util.Arrays;

public enum HistoryOption {
    IN_HISTORY("입고내역"),
    OUT_HISTORY("출고내역"),
    ALL_HISTORY("전체보기"),
    EXIT_HISTORY("종료");

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
                .filter(historyOption -> historyOption.toString().equals(select))
                .findAny()
                .orElse(null);
    }
}
