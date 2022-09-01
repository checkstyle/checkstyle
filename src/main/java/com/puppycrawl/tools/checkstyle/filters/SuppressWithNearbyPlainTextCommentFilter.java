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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Filter {@code SuppressWithNearbyPlainTextCommentFilter} uses plain text to suppress
 * nearby audit events. The filter can only suppress checks which have Checker as a
 * parent module and knows nothing about AST. It supports only singe-line comments.
 * </p>
 * <p>
 * Rationale: Sometimes there are legitimate reasons for violating a check.
 * When this is a matter of the code in question and not personal preference,
 * the best place to override the policy is in the code itself. Semi-structured
 * comments can be associated with the check. This is sometimes superior to
 * a separate suppressions file, which must be kept up-to-date as the source
 * file is edited.
 * </p>
 * <ul>
 * <li>
 * Property {@code commentFormat} - Specify comment pattern to trigger filter to begin suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "SUPPRESS CHECKSTYLE (\w+)"}.
 * </li>
 * <li>
 * Property {@code checkFormat} - Specify check name pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * <li>
 * Property {@code messageFormat} - Specify check violation message pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code idFormat} - Specify check ID pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code influenceFormat} - Specify negative/zero/positive value that
 * defines the number of lines preceding/at/following the suppression comment.
 * Type is {@code java.lang.String}.
 * Default value is {@code "0"}.
 * </li>
 * </ul>
 * <p>
 * To configure the filter to suppress audit events on the same line:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;/&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant; // SUPPRESS CHECKSTYLE the reason for this is..
 * static final int lowerCaseConstant1; // violation
 * static final int lowerCaseConstant2; // violation
 * static final int lowerCaseConstant3; // violation
 * static final int lowerCaseConstant4; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events on any line that contains
 * {@code DO NOT CHECK THIS LINE}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;commentFormat&quot; value=&quot;DO NOT CHECK THIS LINE&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant; // DO NOT CHECK THIS LINE
 * static final int lowerCaseConstant1; // violation
 * static final int lowerCaseConstant2; // violation
 * static final int lowerCaseConstant3; // violation
 * static final int lowerCaseConstant4; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events only on {@code LineLengthCheck}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;checkFormat&quot; value=&quot;LineLengthCheck&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;LineLength&quot;&gt;
 *     &lt;property name=&quot;max&quot; value=&quot;40&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant;
 * static final int UPPER_CASE_CONSTANT1234567; // violation
 * static final int lowerCaseConstant2;
 * static final int lowerCaseConstant3;
 * static final int UPPER_CASE_CONSTANT4567891011; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events whose check message contains
 * the word {@code Line}. In this case, {@code LineLengthCheck}'s violation message
 * contains it:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;messageFormat&quot; value=&quot;.*Line.*&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;LineLength&quot;&gt;
 *     &lt;property name=&quot;max&quot; value=&quot;40&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant; // violation
 * static final int UPPER_CASE_CONSTANT1234567;
 * static final int lowerCaseConstant2; // violation
 * static final int lowerCaseConstant3; // violation
 * static final int UPPER_CASE_CONSTANT4567891011;
 * </pre>
 * <p>
 * To configure the filter to suppress audit events only on a check whose name is
 * {@code ignoreMe}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;idFormat&quot; value=&quot;ignoreMe&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;LineLength&quot;&gt;
 *     &lt;property name=&quot;max&quot; value=&quot;40&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantName&quot;&gt;
 *       &lt;property name=&quot;id&quot; value=&quot;ignoreMe&quot;/&gt;
 *     &lt;/module&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant;
 * static final int UPPER_CASE_CONSTANT1234567; // violation
 * static final int lowerCaseConstant2;
 * static final int lowerCaseConstant3;
 * static final int UPPER_CASE_CONSTANT4567891011; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events for the current and next 3 lines:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;influenceFormat&quot; value=&quot;3&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant; // violation
 * static final int lowerCaseConstant1; // SUPPRESS CHECKSTYLE the reason for this is..
 * static final int lowerCaseConstant2;
 * static final int lowerCaseConstant3;
 * static final int lowerCaseConstant4;
 * static final int lowerCaseConstant5; // violation
 * </pre>
 * <p>
 * To configure the filter to suppress audit events for the current and previous 2 lines:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;influenceFormat&quot; value=&quot;-2&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant; // violation
 * static final int lowerCaseConstant1;
 * static final int lowerCaseConstant2;
 * static final int lowerCaseConstant3; // SUPPRESS CHECKSTYLE the reason for this is..
 * static final int lowerCaseConstant4; // violation
 * static final int lowerCaseConstant5; // violation
 * </pre>
 * <p>
 * To configure the filter to accept variable {@code checkFormat} and {@code influenceFormat}:
 * </p>
 * <pre>
 * &lt;module name=&quot;Checker&quot;&gt;
 *   &lt;module name=&quot;SuppressWithNearbyPlainTextCommentFilter&quot;&gt;
 *     &lt;property name=&quot;commentFormat&quot;
 *       value=&quot;SUPPRESS CHECKSTYLE (\w+) - ([+-]\d+) Lines&quot;/&gt;
 *     &lt;property name=&quot;commentFormat&quot; value=&quot;$1&quot;/&gt;
 *     &lt;property name=&quot;influenceFormat&quot; value=&quot;$2&quot;/&gt;
 *   &lt;/module&gt;
 *   &lt;module name=&quot;TreeWalker&quot;&gt;
 *     &lt;module name=&quot;ConstantNameCheck&quot;/&gt;
 *   &lt;/module&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * static final int lowerCaseConstant; // violation
 * static final int lowerCaseConstant1; // SUPPRESS CHECKSTYLE FileTabCharacterCheck - +3 Lines
 * static final int lowerCaseConstant2;
 * static final int lowerCaseConstant3;
 * static final int lowerCaseConstant4;
 * static final int lowerCaseConstant5; // violation
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 10.4
 */
public class SuppressWithNearbyPlainTextCommentFilter
    extends AutomaticBean
    implements Filter {

    /** Default comment format to turn check reporting off. */
    private static final String DEFAULT_COMMENT_FORMAT = "SUPPRESS CHECKSTYLE (\\w+)";

    /** Default regex for checks that should be suppressed. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Default number of lines that should be suppressed. */
    private static final String DEFAULT_INFLUENCE_FORMAT = "0";

    /** Stores all suppressions for a given file name. */
    private final Map<String, List<Suppression>> fileSuppressions = new HashMap<>();

    /** Specify comment pattern to trigger filter to begin suppression. */
    private Pattern commentFormat = Pattern.compile(DEFAULT_COMMENT_FORMAT);

    /** Specify check name pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** Specify check violation message pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String messageFormat;

    /** Specify check ID pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String idFormat;

    /**
     * Specify negative/zero/positive value that defines the number of lines
     * preceding/at/following the suppression comment.
     */
    private String influenceFormat = DEFAULT_INFLUENCE_FORMAT;

    /**
     * Setter to specify comment pattern to trigger filter to begin suppression.
     *
     * @param pattern a {@code Pattern} value.
     */
    public final void setCommentFormat(Pattern pattern) {
        commentFormat = pattern;
    }

    /**
     * Setter to specify check name pattern to suppress.
     *
     * @param format a {@code String} value.
     */
    public final void setCheckFormat(String format) {
        checkFormat = format;
    }

    /**
     * Setter to specify check violation message pattern to suppress.
     *
     * @param format a {@code String} value.
     */
    public void setMessageFormat(String format) {
        messageFormat = format;
    }

    /**
     * Setter to specify check ID pattern to suppress.
     *
     * @param format a {@code String} value.
     */
    public void setIdFormat(String format) {
        idFormat = format;
    }

    /**
     * Setter to specify negative/zero/positive value that defines the number
     * of lines preceding/at/following the suppression comment.
     *
     * @param format a {@code String} value.
     */
    public final void setInfluenceFormat(String format) {
        influenceFormat = format;
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
    protected void finishLocalSetup() throws CheckstyleException {
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
        final String fullFileName = fileText.getFile().getPath();
        final List<Suppression> suppressions =
                fileSuppressions.getOrDefault(fullFileName, new ArrayList<>());

        if (suppressions.isEmpty()) {
            for (int lineNo = 0; lineNo < fileText.size(); lineNo++) {
                final Optional<Suppression> suppression = getSuppression(fileText, lineNo);
                suppression.ifPresent(suppressions::add);
            }
            fileSuppressions.putIfAbsent(fullFileName, suppressions);
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
        final Matcher commentMatcher = commentFormat.matcher(line);

        Suppression suppression = null;
        if (commentMatcher.find()) {
            suppression = new Suppression(commentMatcher.group(0), lineNo + 1, this);
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
    private static final class Suppression {

        /** Suppression text.*/
        private final String text;

        /** The first line where warnings may be suppressed. */
        private final int firstLine;

        /** The last line where warnings may be suppressed. */
        private final int lastLine;

        /** The regexp which is used to match the event source.*/
        private final Pattern eventSourceRegexp;

        /** The regexp which is used to match the event message.*/
        private final Pattern eventMessageRegexp;

        /** The regexp which is used to match the event ID.*/
        private final Pattern eventIdRegexp;

        /**
         * Constructs new {@code Suppression} instance.
         *
         * @param text suppression text.
         * @param lineNo suppression line number.
         * @param filter the {@code SuppressWithNearbyPlainTextCommentFilter} with the context.
         * @throws IllegalArgumentException if there is an error in the filter regex syntax.
         */
        /* package */ Suppression(
                String text,
                int lineNo,
                SuppressWithNearbyPlainTextCommentFilter filter
        ) {
            this.text = text;

            final Pattern commentFormat = filter.commentFormat;
            final String influenceFormat = filter.influenceFormat;
            String format = "";
            try {
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.checkFormat, text, commentFormat);
                eventSourceRegexp = Pattern.compile(format);
                if (filter.messageFormat == null) {
                    eventMessageRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.messageFormat, text, commentFormat);
                    eventMessageRegexp = Pattern.compile(format);
                }
                if (filter.idFormat == null) {
                    eventIdRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.idFormat, text, commentFormat);
                    eventIdRegexp = Pattern.compile(format);
                }
                format = CommonUtil.fillTemplateWithStringsByRegexp(influenceFormat,
                                                                    text, commentFormat);

                final int influence = parseInfluence(format, influenceFormat, text);

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
         * @param format          influence format to parse
         * @param influenceFormat raw influence format
         * @param text            text of the suppression
         * @return parsed influence
         * @throws IllegalArgumentException when unable to parse int in format
         */
        private static int parseInfluence(String format, String influenceFormat, String text) {
            try {
                return Integer.parseInt(format);
            }
            catch (final NumberFormatException ex) {
                throw new IllegalArgumentException("unable to parse influence from '" + text
                        + "' using " + influenceFormat, ex);
            }
        }

        /**
         * Indicates whether some other object is "equal to" this one.
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Suppression suppression = (Suppression) other;
            return Objects.equals(firstLine, suppression.firstLine)
                    && Objects.equals(lastLine, suppression.lastLine)
                    && Objects.equals(text, suppression.text)
                    && Objects.equals(eventSourceRegexp, suppression.eventSourceRegexp)
                    && Objects.equals(eventMessageRegexp, suppression.eventMessageRegexp)
                    && Objects.equals(eventIdRegexp, suppression.eventIdRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, firstLine, lastLine, eventSourceRegexp,
                                eventMessageRegexp, eventIdRegexp);
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
         * Checks whether {@link AuditEvent} source name matches the check format.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} source name matches the check format.
         */
        private boolean isCheckMatch(AuditEvent event) {
            final Matcher checkMatcher = eventSourceRegexp.matcher(event.getSourceName());
            return checkMatcher.find();
        }

        /**
         * Checks whether the {@link AuditEvent} module ID matches the ID format.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} module ID matches the ID format.
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
         * Checks whether the {@link AuditEvent} message matches the message format.
         *
         * @param event {@link AuditEvent} instance.
         * @return true if the {@link AuditEvent} message matches the message format.
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
