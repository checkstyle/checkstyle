////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Filter {@code SuppressWithNearbyCommentFilter} uses nearby comments to suppress audit events.
 * </p>
 * <p>
 * Rationale: Same as {@code SuppressionCommentFilter}.
 * Whereas the SuppressionCommentFilter uses matched pairs of filters to turn
 * on/off comment matching, {@code SuppressWithNearbyCommentFilter} uses single comments.
 * This requires fewer lines to mark a region, and may be aesthetically preferable in some contexts.
 * </p>
 * <p>
 * Attention: This filter may only be specified within the TreeWalker module
 * ({@code &lt;module name="TreeWalker"/&gt;}) and only applies to checks which are also
 * defined within this module. To filter non-TreeWalker checks like {@code RegexpSingleline},
 * a <a href="https://checkstyle.org/config_filters.html#SuppressWithPlainTextCommentFilter">
 * SuppressWithPlainTextCommentFilter</a> or similar filter must be used.
 * </p>
 * <p>
 * SuppressWithNearbyCommentFilter can suppress Checks that have
 * Treewalker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code commentFormat} - Specify comment pattern to trigger filter to begin suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "SUPPRESS CHECKSTYLE (\w+)"}.
 * </li>
 * <li>
 * Property {@code checkFormat} - Specify check pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * <li>
 * Property {@code messageFormat} - Define message pattern to suppress.
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
 * <li>
 * Property {@code checkCPP} - Control whether to check C++ style comments ({@code //}).
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code checkC} - Control whether to check C style comments ({@code &#47;* ... *&#47;}).
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure a filter to suppress audit events for <i>check</i> on any line
 * with a comment {@code SUPPRESS CHECKSTYLE <i>check</i>}:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWithNearbyCommentFilter&quot;/&gt;
 * </pre>
 * <pre>
 * private int [] array; // SUPPRESS CHECKSTYLE
 * </pre>
 * <p>
 * To configure a filter to suppress all audit events on any line containing
 * the comment {@code CHECKSTYLE IGNORE THIS LINE}:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWithNearbyCommentFilter&quot;&gt;
 *   &lt;property name=&quot;commentFormat&quot; value=&quot;CHECKSTYLE IGNORE THIS LINE&quot;/&gt;
 *   &lt;property name=&quot;checkFormat&quot; value=&quot;.*&quot;/&gt;
 *   &lt;property name=&quot;influenceFormat&quot; value=&quot;0&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public static final int lowerCaseConstant; // CHECKSTYLE IGNORE THIS LINE
 * </pre>
 * <p>
 * To configure a filter so that {@code // OK to catch (Throwable|Exception|RuntimeException) here}
 * permits the current and previous line to avoid generating an IllegalCatch audit event:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWithNearbyCommentFilter&quot;&gt;
 *   &lt;property name=&quot;commentFormat&quot; value=&quot;OK to catch (\w+) here&quot;/&gt;
 *   &lt;property name=&quot;checkFormat&quot; value=&quot;IllegalCatchCheck&quot;/&gt;
 *   &lt;property name=&quot;messageFormat&quot; value=&quot;$1&quot;/&gt;
 *   &lt;property name=&quot;influenceFormat&quot; value=&quot;-1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * . . .
 * catch (RuntimeException re) {
 * // OK to catch RuntimeException here
 * }
 * catch (Throwable th) { ... }
 * . . .
 * </pre>
 * <p>
 * To configure a filter so that {@code CHECKSTYLE IGNORE <i>check</i> FOR NEXT
 * <i>var</i> LINES} avoids triggering any audits for the given check for
 * the current line and the next <i>var</i> lines (for a total of <i>var</i>+1 lines):
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWithNearbyCommentFilter&quot;&gt;
 *   &lt;property name=&quot;commentFormat&quot;
 *       value=&quot;CHECKSTYLE IGNORE (\w+) FOR NEXT (\d+) LINES&quot;/&gt;
 *   &lt;property name=&quot;checkFormat&quot; value=&quot;$1&quot;/&gt;
 *   &lt;property name=&quot;influenceFormat&quot; value=&quot;$2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * static final int lowerCaseConstant; // CHECKSTYLE IGNORE ConstantNameCheck FOR NEXT 3 LINES
 * static final int lowerCaseConstant1;
 * static final int lowerCaseConstant2;
 * static final int lowerCaseConstant3;
 * static final int lowerCaseConstant4; // will warn here
 * </pre>
 * <p>
 * To configure a filter to avoid any audits on code like:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressWithNearbyCommentFilter&quot;&gt;
 *   &lt;property name=&quot;commentFormat&quot;
 *     value=&quot;ALLOW (\\w+) ON PREVIOUS LINE&quot;/&gt;
 *   &lt;property name=&quot;checkFormat&quot; value=&quot;$1&quot;/&gt;
 *   &lt;property name=&quot;influenceFormat&quot; value=&quot;-1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * private int D2;
 * // ALLOW MemberName ON PREVIOUS LINE
 * . . .
 * </pre>
 * <p>
 * To configure a filter to allow suppress one or more Checks (separated by "|")
 * and demand comment no less than 14 symbols:
 * </p>
 * <pre>
 * &lt;module name="SuppressWithNearbyCommentFilter"&gt;
 *   &lt;property name="commentFormat"
 *     value="@cs\.suppress \[(\w+(\|\w+)*)\] \w[-\.'`,:;\w ]{14,}"/&gt;
 *   &lt;property name="checkFormat" value="$1"/&gt;
 *   &lt;property name="influenceFormat" value="1"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public static final int [] array; // @cs.suppress [ConstantName|NoWhitespaceAfter] A comment here
 * </pre>
 * <p>
 * It is possible to specify an ID of checks, so that it can be leveraged by
 * the SuppressWithNearbyCommentFilter to skip validations. The following examples show how to skip
 * validations near code that has comment like {@code // @cs-: &lt;ID/&gt; (reason)},
 * where ID is the ID of checks you want to suppress.
 * </p>
 * <p>
 * Examples of Checkstyle checks configuration:
 * </p>
 * <pre>
 * &lt;module name="RegexpSinglelineJava"&gt;
 *   &lt;property name="id" value="ignore"/&gt;
 *   &lt;property name="format" value="^.*@Ignore\s*$"/&gt;
 *   &lt;property name="message" value="@Ignore should have a reason."/&gt;
 * &lt;/module&gt;
 *
 * &lt;module name="RegexpSinglelineJava"&gt;
 *   &lt;property name="id" value="systemout"/&gt;
 *   &lt;property name="format" value="^.*System\.(out|err).*$"/&gt;
 *   &lt;property name="message" value="Don't use System.out/err, use SLF4J instead."/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of SuppressWithNearbyCommentFilter configuration (idFormat which is set to
 * '$1' points that ID of the checks is in the first group of commentFormat regular expressions):
 * </p>
 * <pre>
 * &lt;module name="SuppressWithNearbyCommentFilter"&gt;
 *   &lt;property name="commentFormat" value="@cs-: (\w+) \(.*\)"/&gt;
 *   &lt;property name="idFormat" value="$1"/&gt;
 *   &lt;property name="influenceFormat" value="0"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * &#64;Ignore // @cs-: ignore (test has not been implemented yet)
 * &#64;Test
 * public void testMethod() { }
 *
 * public static void foo() {
 *   System.out.println("Debug info."); // @cs-: systemout (should not fail RegexpSinglelineJava)
 * }
 * </pre>
 * <p>
 * Example of how to configure the check to suppress more than one checks.
 * The influence format is specified in the second regexp group.
 * </p>
 * <pre>
 * &lt;module name="SuppressWithNearbyCommentFilter"&gt;
 *   &lt;property name="commentFormat" value="@cs-\: ([\w\|]+) influence (\d+)"/&gt;
 *   &lt;property name="checkFormat" value="$1"/&gt;
 *   &lt;property name="influenceFormat" value="$2"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * // @cs-: ClassDataAbstractionCoupling influence 2
 * // @cs-: MagicNumber influence 4
 * &#64;Service // no violations from ClassDataAbstractionCoupling here
 * &#64;Transactional
 * public class UserService {
 *   private int value = 10022; // no violations from MagicNumber here
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * @since 5.0
 */
public class SuppressWithNearbyCommentFilter
    extends AutomaticBean
    implements TreeWalkerFilter {

    /** Format to turn checkstyle reporting off. */
    private static final String DEFAULT_COMMENT_FORMAT =
        "SUPPRESS CHECKSTYLE (\\w+)";

    /** Default regex for checks that should be suppressed. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Default regex for lines that should be suppressed. */
    private static final String DEFAULT_INFLUENCE_FORMAT = "0";

    /** Tagged comments. */
    private final List<Tag> tags = new ArrayList<>();

    /** Control whether to check C style comments ({@code &#47;* ... *&#47;}). */
    private boolean checkC = true;

    /** Control whether to check C++ style comments ({@code //}). */
    // -@cs[AbbreviationAsWordInName] We can not change it as,
    // check's property is a part of API (used in configurations).
    private boolean checkCPP = true;

    /** Specify comment pattern to trigger filter to begin suppression. */
    private Pattern commentFormat = Pattern.compile(DEFAULT_COMMENT_FORMAT);

    /** Specify check pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** Define message pattern to suppress. */
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
     * References the current FileContents for this filter.
     * Since this is a weak reference to the FileContents, the FileContents
     * can be reclaimed as soon as the strong references in TreeWalker
     * are reassigned to the next FileContents, at which time filtering for
     * the current FileContents is finished.
     */
    private WeakReference<FileContents> fileContentsReference = new WeakReference<>(null);

    /**
     * Setter to specify comment pattern to trigger filter to begin suppression.
     *
     * @param pattern a pattern.
     */
    public final void setCommentFormat(Pattern pattern) {
        commentFormat = pattern;
    }

    /**
     * Returns FileContents for this filter.
     *
     * @return the FileContents for this filter.
     */
    private FileContents getFileContents() {
        return fileContentsReference.get();
    }

    /**
     * Set the FileContents for this filter.
     *
     * @param fileContents the FileContents for this filter.
     * @noinspection WeakerAccess
     */
    public void setFileContents(FileContents fileContents) {
        fileContentsReference = new WeakReference<>(fileContents);
    }

    /**
     * Setter to specify check pattern to suppress.
     *
     * @param format a {@code String} value
     */
    public final void setCheckFormat(String format) {
        checkFormat = format;
    }

    /**
     * Setter to define message pattern to suppress.
     *
     * @param format a {@code String} value
     */
    public void setMessageFormat(String format) {
        messageFormat = format;
    }

    /**
     * Setter to specify check ID pattern to suppress.
     *
     * @param format a {@code String} value
     */
    public void setIdFormat(String format) {
        idFormat = format;
    }

    /**
     * Setter to specify negative/zero/positive value that defines the number
     * of lines preceding/at/following the suppression comment.
     *
     * @param format a {@code String} value
     */
    public final void setInfluenceFormat(String format) {
        influenceFormat = format;
    }

    /**
     * Setter to control whether to check C++ style comments ({@code //}).
     *
     * @param checkCpp {@code true} if C++ comments are checked.
     */
    // -@cs[AbbreviationAsWordInName] We can not change it as,
    // check's property is a part of API (used in configurations).
    public void setCheckCPP(boolean checkCpp) {
        checkCPP = checkCpp;
    }

    /**
     * Setter to control whether to check C style comments ({@code &#47;* ... *&#47;}).
     *
     * @param checkC {@code true} if C comments are checked.
     */
    public void setCheckC(boolean checkC) {
        this.checkC = checkC;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent event) {
        boolean accepted = true;

        if (event.getViolation() != null) {
            // Lazy update. If the first event for the current file, update file
            // contents and tag suppressions
            final FileContents currentContents = event.getFileContents();

            if (getFileContents() != currentContents) {
                setFileContents(currentContents);
                tagSuppressions();
            }
            if (matchesTag(event)) {
                accepted = false;
            }
        }
        return accepted;
    }

    /**
     * Whether current event matches any tag from {@link #tags}.
     *
     * @param event TreeWalkerAuditEvent to test match on {@link #tags}.
     * @return true if event matches any tag from {@link #tags}, false otherwise.
     */
    private boolean matchesTag(TreeWalkerAuditEvent event) {
        boolean result = false;
        for (final Tag tag : tags) {
            if (tag.isMatch(event)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Collects all the suppression tags for all comments into a list and
     * sorts the list.
     */
    private void tagSuppressions() {
        tags.clear();
        final FileContents contents = getFileContents();
        if (checkCPP) {
            tagSuppressions(contents.getSingleLineComments().values());
        }
        if (checkC) {
            final Collection<List<TextBlock>> cComments =
                contents.getBlockComments().values();
            cComments.forEach(this::tagSuppressions);
        }
    }

    /**
     * Appends the suppressions in a collection of comments to the full
     * set of suppression tags.
     *
     * @param comments the set of comments.
     */
    private void tagSuppressions(Collection<TextBlock> comments) {
        for (final TextBlock comment : comments) {
            final int startLineNo = comment.getStartLineNo();
            final String[] text = comment.getText();
            tagCommentLine(text[0], startLineNo);
            for (int i = 1; i < text.length; i++) {
                tagCommentLine(text[i], startLineNo + i);
            }
        }
    }

    /**
     * Tags a string if it matches the format for turning
     * checkstyle reporting on or the format for turning reporting off.
     *
     * @param text the string to tag.
     * @param line the line number of text.
     */
    private void tagCommentLine(String text, int line) {
        final Matcher matcher = commentFormat.matcher(text);
        if (matcher.find()) {
            addTag(matcher.group(0), line);
        }
    }

    /**
     * Adds a comment suppression {@code Tag} to the list of all tags.
     *
     * @param text the text of the tag.
     * @param line the line number of the tag.
     */
    private void addTag(String text, int line) {
        final Tag tag = new Tag(text, line, this);
        tags.add(tag);
    }

    /**
     * A Tag holds a suppression comment and its location.
     */
    private static final class Tag {

        /** The text of the tag. */
        private final String text;

        /** The first line where warnings may be suppressed. */
        private final int firstLine;

        /** The last line where warnings may be suppressed. */
        private final int lastLine;

        /** The parsed check regexp, expanded for the text of this tag. */
        private final Pattern tagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private final Pattern tagMessageRegexp;

        /** The parsed check ID regexp, expanded for the text of this tag. */
        private final Pattern tagIdRegexp;

        /**
         * Constructs a tag.
         *
         * @param text the text of the suppression.
         * @param line the line number.
         * @param filter the {@code SuppressWithNearbyCommentFilter} with the context
         * @throws IllegalArgumentException if unable to parse expanded text.
         */
        /* package */ Tag(String text, int line, SuppressWithNearbyCommentFilter filter) {
            this.text = text;

            // Expand regexp for check and message
            // Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.checkFormat, text, filter.commentFormat);
                tagCheckRegexp = Pattern.compile(format);
                if (filter.messageFormat == null) {
                    tagMessageRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.messageFormat, text, filter.commentFormat);
                    tagMessageRegexp = Pattern.compile(format);
                }
                if (filter.idFormat == null) {
                    tagIdRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.idFormat, text, filter.commentFormat);
                    tagIdRegexp = Pattern.compile(format);
                }
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.influenceFormat, text, filter.commentFormat);

                final int influence = parseInfluence(format, filter.influenceFormat, text);

                if (influence >= 1) {
                    firstLine = line;
                    lastLine = line + influence;
                }
                else {
                    firstLine = line + influence;
                    lastLine = line;
                }
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

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Tag tag = (Tag) other;
            return Objects.equals(firstLine, tag.firstLine)
                    && Objects.equals(lastLine, tag.lastLine)
                    && Objects.equals(text, tag.text)
                    && Objects.equals(tagCheckRegexp, tag.tagCheckRegexp)
                    && Objects.equals(tagMessageRegexp, tag.tagMessageRegexp)
                    && Objects.equals(tagIdRegexp, tag.tagIdRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, firstLine, lastLine, tagCheckRegexp, tagMessageRegexp,
                    tagIdRegexp);
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this tag.
         *
         * @param event the {@code TreeWalkerAuditEvent} to check.
         * @return true if the source of event matches the text of this tag.
         */
        public boolean isMatch(TreeWalkerAuditEvent event) {
            return isInScopeOfSuppression(event)
                    && isCheckMatch(event)
                    && isIdMatch(event)
                    && isMessageMatch(event);
        }

        /**
         * Checks whether the {@link TreeWalkerAuditEvent} is in the scope of the suppression.
         *
         * @param event {@link TreeWalkerAuditEvent} instance.
         * @return true if the {@link TreeWalkerAuditEvent} is in the scope of the suppression.
         */
        private boolean isInScopeOfSuppression(TreeWalkerAuditEvent event) {
            final int line = event.getLine();
            return line >= firstLine && line <= lastLine;
        }

        /**
         * Checks whether {@link TreeWalkerAuditEvent} source name matches the check format.
         *
         * @param event {@link TreeWalkerAuditEvent} instance.
         * @return true if the {@link TreeWalkerAuditEvent} source name matches the check format.
         */
        private boolean isCheckMatch(TreeWalkerAuditEvent event) {
            final Matcher checkMatcher = tagCheckRegexp.matcher(event.getSourceName());
            return checkMatcher.find();
        }

        /**
         * Checks whether the {@link TreeWalkerAuditEvent} module ID matches the ID format.
         *
         * @param event {@link TreeWalkerAuditEvent} instance.
         * @return true if the {@link TreeWalkerAuditEvent} module ID matches the ID format.
         */
        private boolean isIdMatch(TreeWalkerAuditEvent event) {
            boolean match = true;
            if (tagIdRegexp != null) {
                if (event.getModuleId() == null) {
                    match = false;
                }
                else {
                    final Matcher idMatcher = tagIdRegexp.matcher(event.getModuleId());
                    match = idMatcher.find();
                }
            }
            return match;
        }

        /**
         * Checks whether the {@link TreeWalkerAuditEvent} message matches the message format.
         *
         * @param event {@link TreeWalkerAuditEvent} instance.
         * @return true if the {@link TreeWalkerAuditEvent} message matches the message format.
         */
        private boolean isMessageMatch(TreeWalkerAuditEvent event) {
            boolean match = true;
            if (tagMessageRegexp != null) {
                final Matcher messageMatcher = tagMessageRegexp.matcher(event.getMessage());
                match = messageMatcher.find();
            }
            return match;
        }

        @Override
        public String toString() {
            return "Tag[text='" + text + '\''
                    + ", firstLine=" + firstLine
                    + ", lastLine=" + lastLine
                    + ", tagCheckRegexp=" + tagCheckRegexp
                    + ", tagMessageRegexp=" + tagMessageRegexp
                    + ", tagIdRegexp=" + tagIdRegexp
                    + ']';
        }

    }

}
