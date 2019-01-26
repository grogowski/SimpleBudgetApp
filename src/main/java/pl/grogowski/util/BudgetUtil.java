package pl.grogowski.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class BudgetUtil {

    public static String convertDate(LocalDate date) {
        return date.getMonth().getDisplayName(TextStyle.FULL, Locale.UK) + " " + date.getYear();
    }

}
