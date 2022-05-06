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
import java.util.Collections;
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
 * Filter {@code SuppressionCommentFilter} uses pairs of comments to suppress audit events.
 * </p>
 * <p>
 * Rationale:
 * Sometimes there are legitimate reasons for violating a check. When
 * this is a matter of the code in question and not personal
 * preference, the best place to override the policy is in the code
 * itself. Semi-structured comments can be associated with the check.
 * This is sometimes superior to a separate suppressions file, which
 * must be kept up-to-date as the source file is edited.
 * </p>
 * <p>
 * Note that the suppression comment should be put before the violation.
 * You can use more than one suppression comment each on separate line.
 * </p>
 * <p>
 * Attention: This filter may only be specified within the TreeWalker module
 * ({@code &lt;module name="TreeWalker"/&gt;}) and only applies to checks which are also
 * defined within this module. To filter non-TreeWalker checks like {@code RegexpSingleline}, a
 * <a href="https://checkstyle.org/config_filters.html#SuppressWithPlainTextCommentFilter">
 * SuppressWithPlainTextCommentFilter</a> or similar filter must be used.
 * </p>
 * <p>
 * {@code offCommentFormat} and {@code onCommentFormat} must have equal
 * <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/regex/Matcher.html#groupCount()">
 * paren counts</a>.
 * </p>
 * <p>
 * SuppressionCommentFilter can suppress Checks that have Treewalker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code offCommentFormat} - Specify comment pattern to
 * trigger filter to begin suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "CHECKSTYLE:OFF"}.
 * </li>
 * <li>
 * Property {@code onCommentFormat} - Specify comment pattern to trigger filter to end suppression.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "CHECKSTYLE:ON"}.
 * </li>
 * <li>
 * Property {@code checkFormat} - Specify check pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code ".*"}.
 * </li>
 * <li>
 * Property {@code messageFormat} - Specify message pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code idFormat} - Specify check ID pattern to suppress.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
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
 * To configure a filter to suppress audit events between a comment containing
 * {@code CHECKSTYLE:OFF} and a comment containing {@code CHECKSTYLE:ON}:
 * </p>
 * <pre>
 * &lt;module name="TreeWalker"&gt;
 *               ...
 *   &lt;module name="SuppressionCommentFilter"/&gt;
 *               ...
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure a filter to suppress audit events between a comment containing line
 * {@code BEGIN GENERATED CODE} and a comment containing line {@code END GENERATED CODE}:
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="BEGIN GENERATED CODE"/&gt;
 *   &lt;property name="onCommentFormat" value="END GENERATED CODE"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * //BEGIN GENERATED CODE
 * &#64;Override
 * public boolean equals(Object obj) { ... } // No violation events will be reported
 *
 * &#64;Override
 * public int hashCode() { ... } // No violation events will be reported
 * //END GENERATED CODE
 * . . .
 * </pre>
 * <p>
 * To configure a filter so that {@code // stop constant check} and
 * {@code // resume constant check} marks legitimate constant names:
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="stop constant check"/&gt;
 *   &lt;property name="onCommentFormat" value="resume constant check"/&gt;
 *   &lt;property name="checkFormat" value="ConstantNameCheck"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * //stop constant check
 * public static final int someConstant; // won't warn here
 * //resume constant check
 * public static final int someConstant; // will warn here as constant's name doesn't match the
 * // pattern "^[A-Z][A-Z0-9]*$"
 * </pre>
 * <p>
 * To configure a filter so that {@code UNUSED OFF: <i>var</i>} and
 * {@code UNUSED ON: <i>var</i>} marks a variable or parameter known not to be
 * used by the code by matching the variable name in the message:
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="UNUSED OFF\: (\w+)"/&gt;
 *   &lt;property name="onCommentFormat" value="UNUSED ON\: (\w+)"/&gt;
 *   &lt;property name="checkFormat" value="Unused"/&gt;
 *   &lt;property name="messageFormat" value="^Unused \w+ '$1'.$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * private static void foo(int a, int b) // UNUSED OFF: b
 * {
 * System.out.println(a);
 * }
 *
 * private static void foo1(int a, int b) // UNUSED ON: b
 * {
 * System.out.println(a);
 * }
 * </pre>
 * <p>
 * To configure a filter so that name of suppressed check mentioned in comment
 * {@code CSOFF: <i>regexp</i>} and {@code CSON: <i>regexp</i>} mark a matching check:
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="CSOFF\: ([\w\|]+)"/&gt;
 *   &lt;property name="onCommentFormat" value="CSON\: ([\w\|]+)"/&gt;
 *   &lt;property name="checkFormat" value="$1"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public static final int lowerCaseConstant; // CSOFF: ConstantNameCheck
 * public static final int lowerCaseConstant1; // CSON: ConstantNameCheck
 * </pre>
 * <p>
 * To configure a filter to suppress all audit events between a comment containing
 * {@code CHECKSTYLE_OFF: ALMOST_ALL} and a comment containing
 * {@code CHECKSTYLE_OFF: ALMOST_ALL} except for the <em>EqualsHashCode</em> check:
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="CHECKSTYLE_OFF: ALMOST_ALL"/&gt;
 *   &lt;property name="onCommentFormat" value="CHECKSTYLE_ON: ALMOST_ALL"/&gt;
 *   &lt;property name="checkFormat" value="^((?!(EqualsHashCode)).)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * public static final int array []; // CHECKSTYLE_OFF: ALMOST_ALL
 * private String [] strArray;
 * private int array1 []; // CHECKSTYLE_ON: ALMOST_ALL
 * </pre>
 * <p>
 * To configure a filter to suppress Check's violation message
 * <b>which matches specified message in messageFormat</b>
 * (so suppression will be not only by Check's name, but by message text
 * additionally, as the same Check could report different by message format violations)
 * between a comment containing {@code stop} and comment containing {@code resume}:
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="stop"/&gt;
 *   &lt;property name="onCommentFormat" value="resume"/&gt;
 *   &lt;property name="checkFormat" value="IllegalTypeCheck"/&gt;
 *   &lt;property name="messageFormat"
 *       value="^Declaring variables, return values or parameters of type 'GregorianCalendar'
 *         is not allowed.$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code before filter above is applied with Check's audit events:
 * </p>
 * <pre>
 * ...
 * // Warning below: Declaring variables, return values or parameters of type 'GregorianCalendar'
 * // is not allowed.
 * GregorianCalendar calendar;
 * // Warning below here: Declaring variables, return values or parameters of type 'HashSet'
 * // is not allowed.
 * HashSet hashSet;
 * ...
 * </pre>
 * <p>
 * Code after filter is applied:
 * </p>
 * <pre>
 * ...
 * //stop
 * GregorianCalendar calendar; // No warning here as it is suppressed by filter.
 * HashSet hashSet;
 * // Warning above here: Declaring variables, return values or parameters of type 'HashSet'
 * //is not allowed.
 *
 * //resume
 * ...
 * </pre>
 * <p>
 * It is possible to specify an ID of checks, so that it can be leveraged by the
 * SuppressionCommentFilter to skip validations. The following examples show how
 * to skip validations near code that is surrounded with {@code // CSOFF &lt;ID&gt; (reason)}
 * and {@code // CSON &lt;ID&gt;}, where ID is the ID of checks you want to suppress.
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
 * Example of SuppressionCommentFilter configuration (checkFormat which is set
 * to '$1' points that ID of the checks is in the first group of offCommentFormat
 * and onCommentFormat regular expressions):
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="CSOFF (\w+) \(\w+\)"/&gt;
 *   &lt;property name="onCommentFormat" value="CSON (\w+)"/&gt;
 *   &lt;property name="idFormat" value="$1"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * // CSOFF ignore (test has not been implemented yet)
 * &#64;Ignore // should NOT fail RegexpSinglelineJava
 * &#64;Test
 * public void testMethod() { }
 * // CSON ignore
 *
 * // CSOFF systemout (debug)
 * public static void foo() {
 *   System.out.println("Debug info."); // should NOT fail RegexpSinglelineJava
 * }
 * // CSON systemout
 * </pre>
 * <p>
 * Example of how to configure the check to suppress more than one checks.
 * </p>
 * <pre>
 * &lt;module name="SuppressionCommentFilter"&gt;
 *   &lt;property name="offCommentFormat" value="@cs-\: ([\w\|]+)"/&gt;
 *   &lt;property name="checkFormat" value="$1"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * // @cs-: ClassDataAbstractionCoupling
 * // @cs-: MagicNumber
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
 * @since 3.5
 */
public class SuppressionCommentFilter
    extends AutomaticBean
    implements TreeWalkerFilter {

    /**
     * Enum to be used for switching checkstyle reporting for tags.
     */
    public enum TagType {

        /**
         * Switch reporting on.
         */
        ON,
        /**
         * Switch reporting off.
         */
        OFF,

    }

    /** Turns checkstyle reporting off. */
    private static final String DEFAULT_OFF_FORMAT = "CHECKSTYLE:OFF";

    /** Turns checkstyle reporting on. */
    private static final String DEFAULT_ON_FORMAT = "CHECKSTYLE:ON";

    /** Control all checks. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Tagged comments. */
    private final List<Tag> tags = new ArrayList<>();

    /** Control whether to check C style comments ({@code &#47;* ... *&#47;}). */
    private boolean checkC = true;

    /** Control whether to check C++ style comments ({@code //}). */
    // -@cs[AbbreviationAsWordInName] we can not change it as,
    // Check property is a part of API (used in configurations)
    private boolean checkCPP = true;

    /** Specify comment pattern to trigger filter to begin suppression. */
    private Pattern offCommentFormat = Pattern.compile(DEFAULT_OFF_FORMAT);

    /** Specify comment pattern to trigger filter to end suppression. */
    private Pattern onCommentFormat = Pattern.compile(DEFAULT_ON_FORMAT);

    /** Specify check pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** Specify message pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String messageFormat;

    /** Specify check ID pattern to suppress. */
    @XdocsPropertyType(PropertyType.PATTERN)
    private String idFormat;

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
    public final void setOffCommentFormat(Pattern pattern) {
        offCommentFormat = pattern;
    }

    /**
     * Setter to specify comment pattern to trigger filter to end suppression.
     *
     * @param pattern a pattern.
     */
    public final void setOnCommentFormat(Pattern pattern) {
        onCommentFormat = pattern;
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
     * Setter to specify message pattern to suppress.
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
            final Tag matchTag = findNearestMatch(event);
            accepted = matchTag == null || matchTag.getTagType() == TagType.ON;
        }
        return accepted;
    }

    /**
     * Finds the nearest comment text tag that matches an audit event.
     * The nearest tag is before the line and column of the event.
     *
     * @param event the {@code TreeWalkerAuditEvent} to match.
     * @return The {@code Tag} nearest event.
     */
    private Tag findNearestMatch(TreeWalkerAuditEvent event) {
        Tag result = null;
        for (Tag tag : tags) {
            final int eventLine = event.getLine();
            if (tag.getLine() > eventLine
                || tag.getLine() == eventLine
                    && tag.getColumn() > event.getColumn()) {
                break;
            }
            if (tag.isMatch(event)) {
                result = tag;
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
            final Collection<List<TextBlock>> cComments = contents
                    .getBlockComments().values();
            cComments.forEach(this::tagSuppressions);
        }
        Collections.sort(tags);
    }

    /**
     * Appends the suppressions in a collection of comments to the full
     * set of suppression tags.
     *
     * @param comments the set of comments.
     */
    private void tagSuppressions(Collection<TextBlock> comments) {
        for (TextBlock comment : comments) {
            final int startLineNo = comment.getStartLineNo();
            final String[] text = comment.getText();
            tagCommentLine(text[0], startLineNo, comment.getStartColNo());
            for (int i = 1; i < text.length; i++) {
                tagCommentLine(text[i], startLineNo + i, 0);
            }
        }
    }

    /**
     * Tags a string if it matches the format for turning
     * checkstyle reporting on or the format for turning reporting off.
     *
     * @param text the string to tag.
     * @param line the line number of text.
     * @param column the column number of text.
     */
    private void tagCommentLine(String text, int line, int column) {
        final Matcher offMatcher = offCommentFormat.matcher(text);
        if (offMatcher.find()) {
            addTag(offMatcher.group(0), line, column, TagType.OFF);
        }
        else {
            final Matcher onMatcher = onCommentFormat.matcher(text);
            if (onMatcher.find()) {
                addTag(onMatcher.group(0), line, column, TagType.ON);
            }
        }
    }

    /**
     * Adds a {@code Tag} to the list of all tags.
     *
     * @param text the text of the tag.
     * @param line the line number of the tag.
     * @param column the column number of the tag.
     * @param reportingOn {@code true} if the tag turns checkstyle reporting on.
     */
    private void addTag(String text, int line, int column, TagType reportingOn) {
        final Tag tag = new Tag(line, column, text, reportingOn, this);
        tags.add(tag);
    }

    /**
     * A Tag holds a suppression comment and its location, and determines
     * whether the suppression turns checkstyle reporting on or off.
     */
    private static final class Tag
        implements Comparable<Tag> {

        /** The text of the tag. */
        private final String text;

        /** The line number of the tag. */
        private final int line;

        /** The column number of the tag. */
        private final int column;

        /** Determines whether the suppression turns checkstyle reporting on. */
        private final TagType tagType;

        /** The parsed check regexp, expanded for the text of this tag. */
        private final Pattern tagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private final Pattern tagMessageRegexp;

        /** The parsed check ID regexp, expanded for the text of this tag. */
        private final Pattern tagIdRegexp;

        /**
         * Constructs a tag.
         *
         * @param line the line number.
         * @param column the column number.
         * @param text the text of the suppression.
         * @param tagType {@code ON} if the tag turns checkstyle reporting.
         * @param filter the {@code SuppressionCommentFilter} with the context
         * @throws IllegalArgumentException if unable to parse expanded text.
         */
        /* package */ Tag(int line, int column, String text, TagType tagType,
                   SuppressionCommentFilter filter) {
            this.line = line;
            this.column = column;
            this.text = text;
            this.tagType = tagType;

            final Pattern commentFormat;
            if (this.tagType == TagType.ON) {
                commentFormat = filter.onCommentFormat;
            }
            else {
                commentFormat = filter.offCommentFormat;
            }

            // Expand regexp for check and message
            // Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.checkFormat, text, commentFormat);
                tagCheckRegexp = Pattern.compile(format);

                if (filter.messageFormat == null) {
                    tagMessageRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.messageFormat, text, commentFormat);
                    tagMessageRegexp = Pattern.compile(format);
                }

                if (filter.idFormat == null) {
                    tagIdRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.idFormat, text, commentFormat);
                    tagIdRegexp = Pattern.compile(format);
                }
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException(
                    "unable to parse expanded comment " + format, ex);
            }
        }

        /**
         * Returns line number of the tag in the source file.
         *
         * @return the line number of the tag in the source file.
         */
        public int getLine() {
            return line;
        }

        /**
         * Determines the column number of the tag in the source file.
         * Will be 0 for all lines of multiline comment, except the
         * first line.
         *
         * @return the column number of the tag in the source file.
         */
        public int getColumn() {
            return column;
        }

        /**
         * Determines whether the suppression turns checkstyle reporting on or
         * off.
         *
         * @return {@code ON} if the suppression turns reporting on.
         */
        public TagType getTagType() {
            return tagType;
        }

        /**
         * Compares the position of this tag in the file
         * with the position of another tag.
         *
         * @param object the tag to compare with this one.
         * @return a negative number if this tag is before the other tag,
         *     0 if they are at the same position, and a positive number if this
         *     tag is after the other tag.
         */
        @Override
        public int compareTo(Tag object) {
            final int result;
            if (line == object.line) {
                result = Integer.compare(column, object.column);
            }
            else {
                result = Integer.compare(line, object.line);
            }
            return result;
        }

        /**
         * Indicates whether some other object is "equal to" this one.
         * Suppression on enumeration is needed so code stays consistent.
         *
         * @noinspection EqualsCalledOnEnumConstant
         */
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Tag tag = (Tag) other;
            return Objects.equals(line, tag.line)
                    && Objects.equals(column, tag.column)
                    && Objects.equals(tagType, tag.tagType)
                    && Objects.equals(text, tag.text)
                    && Objects.equals(tagCheckRegexp, tag.tagCheckRegexp)
                    && Objects.equals(tagMessageRegexp, tag.tagMessageRegexp)
                    && Objects.equals(tagIdRegexp, tag.tagIdRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, line, column, tagType, tagCheckRegexp, tagMessageRegexp,
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
            return isCheckMatch(event) && isIdMatch(event) && isMessageMatch(event);
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
                    + ", line=" + line
                    + ", column=" + column
                    + ", type=" + tagType
                    + ", tagCheckRegexp=" + tagCheckRegexp
                    + ", tagMessageRegexp=" + tagMessageRegexp
                    + ", tagIdRegexp=" + tagIdRegexp + ']';
        }

    }

}
