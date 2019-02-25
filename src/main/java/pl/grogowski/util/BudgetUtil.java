package pl.grogowski.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class BudgetUtil {

    public static String DateToString(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.FULL, Locale.UK) + " " + date.getYear();
    }

    public static LocalDate getPresentMonth() {
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
    }

    public static LocalDate getNextMonth() {
        return getPresentMonth().plusMonths(1);
    }

}
