package com.puppycrawl.tools.checkstyle.checks.coding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InputIllegalTypeExcludeVariableScope {

	/**
	 * A DateFormat to convert between internal and external date representation.
	 */
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-DD");

	// Calendar used for JPA ORM compliance.
	private Calendar startDate;

	/**
	 * Compound constructor, converting external to internal representation of the startDate.
	 *
	 * @param startDate A non-null String (external representation) which should be parse-able by the
	 */
	public InputIllegalTypeExcludeVariableScope(final String startDate) throws ParseException {

		// Perform an explicit conversion
		final Date parsed = DATE_FORMAT.parse(startDate);

		// Convert to internal representation.
		this.startDate = Calendar.getInstance();
		this.startDate.setTime(parsed);
	}

	/**
	 * This method should really return a JDK 8 ZonedDateTime instance.
	 * However for the scope of the test, we can safely return another external representation.
	 *
	 * @return A String representation of the startTime.
	 */
	public String getStartTime() {
		return DATE_FORMAT.format(startDate.getTime());
	}
}
