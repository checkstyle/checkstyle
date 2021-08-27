/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Comparator;



public class InputLambda16 {

    static final Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>> DATE_TIME_ORDER =
         (Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>>) (dateTime1, dateTime2) -> {
             int cmp = Long.compare(dateTime1.toLocalDate().toEpochDay(),
                dateTime2.toLocalDate().toEpochDay());
             if (cmp == 0) {
                 cmp = Long.compare(dateTime1.toLocalTime().toNanoOfDay(),
                    dateTime2.toLocalTime().toNanoOfDay());
             }
             return cmp;
         };

    public static void main(String args[]) {

    }
}
