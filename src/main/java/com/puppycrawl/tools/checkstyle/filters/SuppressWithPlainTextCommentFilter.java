////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 *     A filter that uses comments to suppress audit events.
 *     The filter can be used only to suppress audit events received from
 *     {@link com.puppycrawl.tools.checkstyle.api.FileSetCheck} checks.
 *     SuppressWithPlainTextCommentFilter knows nothing about AST,
 *     it treats only plain text comments and extracts the information required for suppression from
 *     the plain text comments. Currently the filter supports only single line comments.
 * </p>
 * <p>
 *     Rationale:
 *     Sometimes there are legitimate reasons for violating a check. When
 *     this is a matter of the code in question and not personal
 *     preference, the best place to override the policy is in the code
 *     itself.  Semi-structured comments can be associated with the check.
 *     This is sometimes superior to a separate suppressions file, which
 *     must be kept up-to-date as the source file is edited.
 * </p>
 */
public class SuppressWithPlainTextCommentFilter extends AutomaticBean implements Filter {

    /** Comment format which turns checkstyle reporting off. */
    private static final String DEFAULT_OFF_FORMAT = "// CHECKSTYLE:OFF";

    /** Comment format which turns checkstyle reporting on. */
    private static final String DEFAULT_ON_FORMAT = "// CHECKSTYLE:ON";

    /** Default check format to suppress. By default the filter suppress all checks. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Regexp which turns checkstyle reporting off. */
    private Pattern offCommentFormat = CommonUtil.createPattern(DEFAULT_OFF_FORMAT);

    /** Regexp which turns checkstyle reporting on. */
    private Pattern onCommentFormat = CommonUtil.createPattern(DEFAULT_ON_FORMAT);

    /** The check format to suppress. */
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** The message format to suppress.*/
    private String messageFormat;

    /**
     * Sets an off comment format pattern.
     * @param pattern off comment format pattern.
     */
    public final void setOffCommentFormat(Pattern pattern) {
        offCommentFormat = pattern;
    }

    /**
     * Sets an on comment format pattern.
     * @param pattern  on comment format pattern.
     */
    public final void setOnCommentFormat(Pattern pattern) {
        onCommentFormat = pattern;
    }

    /**
     * Sets a pattern for check format.
     * @param format pattern for check format.
     */
    public final void setCheckFormat(String format) {
        checkFormat = format;
    }

    /**
     * Sets a pattern for message format.
     * @param format pattern for message format.
     */
    public final void setMessageFormat(String format) {
        messageFormat = format;
    }

    @Override
    public boolean accept(AuditEvent event) {
        boolean accepted = true;
        if (event.getLocalizedMessage() != null) {
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
     * @param fileName the name of the file.
     * @return {@link FileText} instance.
     */
    private static FileText getFileText(String fileName) {
        final File file = new File(fileName);
        FileText result = null;

        // some violations can be on a directory, instead of a file
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
     * @param fileText {@link FileText} instance.
     * @param lineNo line number.
     * @return {@link Optional} of {@link Suppression}.
     */
    private Optional<Suppression> getSuppression(FileText fileText, int lineNo) {
        final String line = fileText.get(lineNo);
        final Matcher onCommentMatcher = onCommentFormat.matcher(line);
        final Matcher offCommentMatcher = offCommentFormat.matcher(line);

        Suppression suppression = null;
        if (onCommentMatcher.find()) {
            suppression = new Suppression(onCommentMatcher.group(0),
                lineNo + 1, onCommentMatcher.start(), SuppressionType.ON, this);
        }
        if (offCommentMatcher.find()) {
            suppression = new Suppression(offCommentMatcher.group(0),
                lineNo + 1, offCommentMatcher.start(), SuppressionType.OFF, this);
        }

        return Optional.ofNullable(suppression);
    }

    /**
     * Finds the nearest {@link Suppression} instance which can suppress
     * the given {@link AuditEvent}. The nearest suppression is the suppression which scope
     * is before the line and column of the event.
     * @param suppressions {@link Suppression} instance.
     * @param event {@link AuditEvent} instance.
     * @return {@link Suppression} instance.
     */
    private static Suppression getNearestSuppression(List<Suppression> suppressions,
                                                     AuditEvent event) {
        return suppressions
            .stream()
            .filter(suppression -> suppression.isMatch(event))
            .reduce((first, second) -> second)
            .filter(suppression -> suppression.suppressionType != SuppressionType.ON)
            .orElse(null);
    }

    /** Enum which represents the type of the suppression. */
    private enum SuppressionType {

        /** On suppression type. */
        ON,
        /** Off suppression type. */
        OFF,

    }

    /** The class which represents the suppression. */
    public static class Suppression {

        /** The regexp which is used to match the event source.*/
        private final Pattern eventSourceRegexp;
        /** The regexp which is used to match the event message.*/
        private final Pattern eventMessageRegexp;

        /** Suppression text.*/
        private final String text;
        /** Suppression line.*/
        private final int lineNo;
        /** Suppression column number.*/
        private final int columnNo;
        /** Suppression type. */
        private final SuppressionType suppressionType;

        /**
         * Creates new suppression instance.
         * @param text suppression text.
         * @param lineNo suppression line number.
         * @param columnNo suppression column number.
         * @param suppressionType suppression type.
         * @param filter the {@link SuppressWithPlainTextCommentFilter} with the context.
         */
        protected Suppression(
            String text,
            int lineNo,
            int columnNo,
            SuppressionType suppressionType,
            SuppressWithPlainTextCommentFilter filter
        ) {
            this.text = text;
            this.lineNo = lineNo;
            this.columnNo = columnNo;
            this.suppressionType = suppressionType;

            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                if (this.suppressionType == SuppressionType.ON) {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.checkFormat, text, filter.onCommentFormat);
                    eventSourceRegexp = Pattern.compile(format);
                    if (filter.messageFormat == null) {
                        eventMessageRegexp = null;
                    }
                    else {
                        format = CommonUtil.fillTemplateWithStringsByRegexp(
                                filter.messageFormat, text, filter.onCommentFormat);
                        eventMessageRegexp = Pattern.compile(format);
                    }
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.checkFormat, text, filter.offCommentFormat);
                    eventSourceRegexp = Pattern.compile(format);
                    if (filter.messageFormat == null) {
                        eventMessageRegexp = null;
                    }
                    else {
                        format = CommonUtil.fillTemplateWithStringsByRegexp(
                                filter.messageFormat, text, filter.offCommentFormat);
                        eventMessageRegexp = Pattern.compile(format);
                    }
                }
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException(
                    "unable to parse expanded comment " + format, ex);
            }
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Suppression suppression = (Suppression) other;
            return Objects.equals(lineNo, suppression.lineNo)
                    && Objects.equals(columnNo, suppression.columnNo)
                    && Objects.equals(suppressionType, suppression.suppressionType)
                    && Objects.equals(text, suppression.text)
                    && Objects.equals(eventSourceRegexp, suppression.eventSourceRegexp)
                    && Objects.equals(eventMessageRegexp, suppression.eventMessageRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                text, lineNo, columnNo, suppressionType, eventSourceRegexp, eventMessageRegexp);
        }

        /**
         * Checks whether the suppression matches the given {@link AuditEvent}.
         * @param event {@link AuditEvent} instance.
         * @return true if the suppression matches {@link AuditEvent}.
         */
        private boolean isMatch(AuditEvent event) {
            boolean match = false;
            if (isInScopeOfSuppression(event)) {
                final Matcher sourceNameMatcher = eventSourceRegexp.matcher(event.getSourceName());
                if (sourceNameMatcher.find()) {
                    match = eventMessageRegexp == null
                        || eventMessageRegexp.matcher(event.getMessage()).find();
                }
                else {
                    match = event.getModuleId() != null
                        && eventSourceRegexp.matcher(event.getModuleId()).find();
                }
            }
            return match;
        }

        /**
         * Checks whether {@link AuditEvent} is in the scope of the suppression.
         * @param event {@link AuditEvent} instance.
         * @return true if {@link AuditEvent} is in the scope of the suppression.
         */
        private boolean isInScopeOfSuppression(AuditEvent event) {
            return lineNo <= event.getLine();
        }

    }

}
