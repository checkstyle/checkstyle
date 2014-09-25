package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Comparator;


public class InputLambdaTest16 {

	static final Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>> DATE_TIME_ORDER =
         (Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>>) (dateTime1, dateTime2) -> {
             int cmp = Long.compare(dateTime1.toLocalDate().toEpochDay(), dateTime2.toLocalDate().toEpochDay());
             if (cmp == 0) {
                 cmp = Long.compare(dateTime1.toLocalTime().toNanoOfDay(), dateTime2.toLocalTime().toNanoOfDay());
             }
             return cmp;
         };
	
	public static void main(String args[]) {
		
	}
}