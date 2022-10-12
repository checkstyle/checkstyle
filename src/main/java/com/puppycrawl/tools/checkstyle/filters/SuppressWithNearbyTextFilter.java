///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Filter {@code SuppressWithNearbyTextFilter} uses plain text to suppress
 * nearby audit events. The filter can suppress all checks which have Checker as a parent module.
 * </p>
 * <p>
 * Rationale: Sometimes there are legitimate reasons for suppressing a check.
 * When this is a matter of the code in question and not personal preference,
 * the best place to override the policy is in the code itself. Semi-structured
 * comments can be associated with the check. This is sometimes superior to
 * a separate suppressions file, which must be kept up-to-date as the source
 * file is edited.
 * </p>
 * <ul>
 * <li>
 * Property {@code nearbyTextPattern} - Specify nearby text
 * pattern to trigger filter to begin suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "SUPPRESS CHECKSTYLE (\w+)"}.
 * </li>
 * <li>
 * Property {@code checkPattern} - Specify check name pattern to suppress.
 * Property can also be a RegExp group index at {@code nearbyTextPattern} in
 * format of {@code $x} and be picked from target file.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * <li>
 * Property {@code messagePattern} - Specify check violation message pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code idPattern} - Specify check ID pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code lineRange} - Specify negative/zero/positive value that
 * defines the number of lines preceding/at/following the suppressing nearby text.
 * Property can also be a RegExp group index at {@code nearbyTextPattern} in
 * format of {@code $x} and be picked from target file.
 * Type is {@code java.lang.String}.
 * Default value is {@code "0"}.
 * </li>
 * </ul>
 * <p>
 * To configure the filter to suppress audit events on the same line:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;/&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;MagicNumber&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * int a = 1; // SUPPRESS CHECKSTYLE the reason for this is..
 * int b = 1; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events on any line that contains
 * {@code DO NOT CHECK THIS LINE}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;nearbyTextPattern&quot; value=&quot;DO NOT CHECK THIS LINE&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;MagicNumber&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * int a = 1; // DO NOT CHECK THIS LINE
 * int b = 1; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events only on {@code FileTabCharacter}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;checkPattern&quot; value=&quot;FileTabCharacter&quot;/&gt;
 *     &lt;property name=&quot;nearbyTextPattern&quot; value=&quot;.*&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;FileTabCharacter&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * CREATE TABLE Persons (
 *    LastName varchar(255), -- ok
 *    FirstName varchar(255), -- ok
 * );
 * </pre>
 * <p>
 * To configure the filter to suppress audit events whose check message contains
 * the word {@code Line}. In this case, {@code LineLengthCheck}'s violation message
 * contains it:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;messagePattern&quot; value=&quot;.*Line.*&quot;/&gt;
 *     &lt;property name=&quot;nearbyTextPattern&quot; value=&quot;.*&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;LineLength&quot;&gt;
 *     &lt;property name=&quot;max&quot; value=&quot;10&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * export FOO=BAR # ok, even though line is 14 chars
 * </pre>
 * <p>
 * To configure the filter to suppress audit events only on a check whose id is
 * {@code ignoreMe}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;idPattern&quot; value=&quot;ignoreMe&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;LineLength&quot;&gt;
 *     &lt;property name=&quot;max&quot; value=&quot;15&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;MagicNumber&quot;&gt;
 *       &lt;property name=&quot;id&quot; value=&quot;ignoreMe&quot;/&gt;
 *     &lt;/module&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * int a = 1; // ok
 * int longVarName = 2; // LineLength violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events for the current and next 2 lines:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;lineRange&quot; value=&quot;2&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;FileTabCharacter&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * &lt;root&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- SUPPRESS CHECKSTYLE the reason for this is.. --&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- ok --&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- ok --&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- violation --&gt;
 * &lt;/root&gt;
 * </pre>
 * <p>
 * To configure the filter to suppress audit events for the current and previous line:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;lineRange&quot; value=&quot;-1&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;FileTabCharacter&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * &lt;root&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- violation --&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- ok --&gt;
 *   &lt;child&gt;&lt;/child&gt; &lt;!-- SUPPRESS CHECKSTYLE the reason for this is.. --&gt;
 * &lt;/root&gt;
 * </pre>
 * <p>
 * To configure the filter to accept variable {@code checkPattern} and {@code lineRange}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyTextFilter&quot;&gt;
 *     &lt;property name=&quot;nearbyTextPattern&quot;
 *       value=&quot;SUPPRESS CHECKSTYLE (\w+) - ([+-]\d+) Lines&quot;/&gt;
 *     &lt;property name=&quot;nearbyTextPattern&quot; value=&quot;$1&quot;/&gt;
 *     &lt;property name=&quot;lineRange&quot; value=&quot;$2&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;MagicNumber&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * int a = 1; // SUPPRESS CHECKSTYLE MagicNumber - +3 Lines
 * int b = 1;
 * int c = 1;
 * int d = 1;
 * int e = 1; // violation
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 10.3.5
 */
public class SuppressWithNearbyTextFilter
    extends AutomaticBean
    implements Filter {

    /** Default nearby text pattern to turn check reporting off. */
    private static final String DEFAULT_NEARBY_TEXT_PATTERN = "SUPPRESS CHECKSTYLE (\\w+)";

    /** Default regex for checks that should be suppressed. */
    private static final String DEFAULT_CHECK_PATTERN = ".*";

    /** Default number of lines that should be suppressed. */
    private static final String DEFAULT_LINE_RANGE = "0";

    /** Specify nearby text pattern to trigger filter to begin suppression. */
    private Pattern nearbyTextPattern = Pattern.compile(DEFAULT_NEARBY_TEXT_PATTERN);

    /**
     * Specify check name pattern to suppress. Property can also be a RegExp group index
     * at {@code nearbyTextPattern} in format of {@code $x} and be picked from target file.
     */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String checkPattern = DEFAULT_CHECK_PATTERN;

    /** Specify check violation message pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String messagePattern;

    /** Specify check ID pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String idPattern;

    /**
     * Specify negative/zero/positive value that defines the number of lines
     * preceding/at/following the suppressing nearby text. Property can also be a RegExp group
     * index at {@code nearbyTextPattern} in format of {@code $x} and be picked from target file.
     */
    private String lineRange = DEFAULT_LINE_RANGE;

    /**
     * Setter to specify nearby text pattern to trigger filter to begin suppression.
     *
     * @param pattern a {@code Pattern} value.
     */
    public final void setNearbyTextPattern(Pattern pattern) {
        nearbyTextPattern = pattern;
    }

    /**
     * Setter to specify check name pattern to suppress. Property can also
     * be a RegExp group index at {@code nearbyTextPattern} in
     * format of {@code $x} and be picked from target file.
     *
     * @param pattern a {@code String} value.
     */
    public final void setCheckPattern(String pattern) {
        checkPattern = pattern;
    }

    /**
     * Setter to specify check violation message pattern to suppress.
     *
     * @param pattern a {@code String} value.
     */
    public void setMessagePattern(String pattern) {
        messagePattern = pattern;
    }

    /**
     * Setter to specify check ID pattern to suppress.
     *
     * @param pattern a {@code String} value.
     */
    public void setIdPattern(String pattern) {
        idPattern = pattern;
    }

    /**
     * Setter to specify negative/zero/positive value that defines the number
     * of lines preceding/at/following the suppressing nearby text. Property can also
     * be a RegExp group index at {@code nearbyTextPattern} in
     * format of {@code $x} and be picked from target file.
     *
     * @param format a {@code String} value.
     */
    public final void setLineRange(String format) {
        lineRange = format;
    }

    @Override
    public boolean accept(AuditEvent event) {
        boolean accepted = true;
        if (event.getViolation() != null) {
            final FileText fileText = getFileText(event.getFileName());
            if (fileText != null) {
                final List<Suppression> suppressions = getSuppressions(fileText);
                accepted = getNearestSuppression(suppressions, event) == null;
            }
        }
        return accepted;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    /**
     * Returns {@link FileText} instance created based on the given file name.
     *
     * @param fileName the name of the file.
     * @return {@link FileText} instance.
     * @throws IllegalStateException if the file could not be read.
     */

    private static FileText getFileText(String fileName) {
        final File file = new File(fileName);
        FileText result = null;

        if (!file.isDirectory()) {
            try {
                result = new FileText(file, StandardCharsets.UTF_8.name());
            }
            catch (IOException ex) {
                throw new IllegalStateException("Cannot read source file: " + fileName, ex);
            }
        }

        return result;
    }

    /**
     * Returns the list of {@link Suppression} instances retrieved from the given {@link FileText}.
     *
     * @param fileText {@link FileText} instance.
     * @return list of {@link Suppression} instances.
     */
    private List<Suppression> getSuppressions(FileText fileText) {
        final List<Suppression> suppressions = new ArrayList<>();

        for (int lineNo = 0; lineNo < fileText.size(); lineNo++) {
            final Optional<Suppression> suppression = getSuppression(fileText, lineNo);
            suppression.ifPresent(suppressions::add);
        }

        return suppressions;
    }

    /**
     * Tries to extract the suppression from the given line.
     *
     * @param fileText {@link FileText} instance.
     * @param lineNo line number.
     * @return {@link Optional} of {@link Suppression}.
     */
    private Optional<Suppression> getSuppression(FileText fileText, int lineNo) {
        final String line = fileText.get(lineNo);
        final Matcher nearbyTextMatcher = nearbyTextPattern.matcher(line);

        Suppression suppression = null;
        if (nearbyTextMatcher.find()) {
            suppression = new Suppression(nearbyTextMatcher.group(0), lineNo + 1, this);
        }

        return Optional.ofNullable(suppression);
    }

    /**
     * Finds the nearest {@link Suppression} instance which can suppress
     * the given {@link AuditEvent}. The nearest suppression is the suppression which scope
     * is before the line and column of the event.
     *
     * @param suppressions collection of {@link Suppression} instances.
     * @param event {@link AuditEvent} instance.
     * @return {@link Suppression} instance.
     */
    private static Suppression getNearestSuppression(Collection<Suppression> suppressions,
                                                    AuditEvent event) {
        return suppressions
            .stream()
            .filter(suppression -> suppression.isMatch(event))
            .findFirst()
            .orElse(null);
    }

    /** The class which represents the suppression. */
    private static class Suppression {

        /** The first line where warnings may be suppressed. */
        private final int firstLine;

        /** The last line where warnings may be suppressed. */
        private final int lastLine;

        /** The regexp which is used to match the event source.*/
        private final Pattern eventSourceRegexp;

        /** The regexp which is used to match the event message.*/
        private Pattern eventMessageRegexp;

        /** The regexp which is used to match the event ID.*/
        private Pattern eventIdRegexp;

        /**
         * Constructs new {@code Suppression} instance.
         *
         * @param text suppression text.
         * @param lineNo suppression line number.
         * @param filter the {@code SuppressWithNearbyTextFilter} with the context.
         * @throws IllegalArgumentException if there is an error in the filter regex syntax.
         */
        /* package */ Suppression(
                String text,
                int lineNo,
                SuppressWithNearbyTextFilter filter
        ) {
            final Pattern nearbyTextPattern = filter.nearbyTextPattern;
            final String lineRange = filter.lineRange;
            String format = "";
            try {
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.checkPattern, text, nearbyTextPattern);
                eventSourceRegexp = Pattern.compile(format);
                if (filter.messagePattern != null) {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.messagePattern, text, nearbyTextPattern);
                    eventMessageRegexp = Pattern.compile(format);
                }
                if (filter.idPattern != null) {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.idPattern, text, nearbyTextPattern);
                    eventIdRegexp = Pattern.compile(format);
                }
                format = CommonUtil.fillTemplateWithStringsByRegexp(lineRange,
                                                                    text, nearbyTextPattern);

                final int influence = parseInfluence(format, lineRange, text);

                firstLine = Math.min(lineNo, lineNo + influence);
                lastLine = Math.max(lineNo, lineNo + influence);
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException(
                    "unable to parse expanded comment " + format, ex);
            }
        }

        /**
         * Gets influence from suppress filter influence format param.
         *
         * @param format influence format to parse
         * @param lineRange raw line range
         * @param text text of the suppression
         * @return parsed influence
         * @throws IllegalArgumentException when unable to parse int in format
         */
        private static int parseInfluence(String format, String lineRange, String text) {
            try {
                return Integer.parseInt(format);
            }
            catch (final NumberFormatException ex) {
                throw new IllegalArgumentException("unable to parse line range from '" + text
                        + "' using " + lineRange, ex);
            }
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this suppression.
         *
         * @param event the {@code AuditEvent} to check.
         * @return true if the source of event matches the text of this suppression.
         */
        public boolean isMatch(AuditEvent event) {
            return isInScopeOfSuppression(event)
                    && isCheckMatch(event)
                    && isIdMatch(event)
                    && isMessageMatch(event);
        }

        /**
         * Checks whether the {@link AuditEvent} is in the scope of the suppression.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} is in the scope of the suppression.
         */
        private boolean isInScopeOfSuppression(AuditEvent event) {
            final int eventLine = event.getLine();
            return eventLine >= firstLine && eventLine <= lastLine;
        }

        /**
         * Checks whether {@link AuditEvent} source name matches the check pattern.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} source name matches the check pattern.
         */
        private boolean isCheckMatch(AuditEvent event) {
            final Matcher checkMatcher = eventSourceRegexp.matcher(event.getSourceName());
            return checkMatcher.find();
        }

        /**
         * Checks whether the {@link AuditEvent} module ID matches the ID pattern.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} module ID matches the ID pattern.
         */
        private boolean isIdMatch(AuditEvent event) {
            boolean match = true;
            if (eventIdRegexp != null) {
                if (event.getModuleId() == null) {
                    match = false;
                }
                else {
                    final Matcher idMatcher = eventIdRegexp.matcher(event.getModuleId());
                    match = idMatcher.find();
                }
            }
            return match;
        }

        /**
         * Checks whether the {@link AuditEvent} message matches the message pattern.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} message matches the message pattern.
         */
        private boolean isMessageMatch(AuditEvent event) {
            boolean match = true;
            if (eventMessageRegexp != null) {
                final Matcher messageMatcher = eventMessageRegexp.matcher(event.getMessage());
                match = messageMatcher.find();
            }
            return match;
        }
    }
}
