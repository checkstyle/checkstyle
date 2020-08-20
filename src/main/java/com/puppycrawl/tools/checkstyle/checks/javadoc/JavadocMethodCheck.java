////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks the Javadoc of a method or constructor.
 * The scope to verify is specified using the {@code Scope} class and defaults
 * to {@code Scope.PRIVATE}. To verify another scope, set property scope to
 * a different <a href="https://checkstyle.org/property_types.html#Scope">scope</a>.
 * </p>
 * <p>
 * Violates parameters and type parameters for which no param tags are present can
 * be suppressed by defining property {@code allowMissingParamTags}.
 * </p>
 * <p>
 * Violates methods which return non-void but for which no return tag is present can
 * be suppressed by defining property {@code allowMissingReturnTag}.
 * </p>
 * <p>
 * Violates exceptions which are declared to be thrown (by {@code throws} in the method
 * signature or by {@code throw new} in the method body), but for which no throws tag is
 * present by activation of property {@code validateThrows}.
 * Note that {@code throw new} is not checked in the following places:
 * </p>
 * <ul>
 * <li>
 * Inside a try block (with catch). It is not possible to determine if the thrown
 * exception can be caught by the catch block as there is no knowledge of the
 * inheritance hierarchy, so the try block is ignored entirely. However, catch
 * and finally blocks, as well as try blocks without catch, are still checked.
 * </li>
 * <li>
 * Local classes, anonymous classes and lambda expressions. It is not known when the
 * throw statements inside such classes are going to be evaluated, so they are ignored.
 * </li>
 * </ul>
 * <p>
 * ATTENTION: Checkstyle does not have information about hierarchy of exception types
 * so usage of base class is considered as separate exception type.
 * As workaround you need to specify both types in javadoc (parent and exact type).
 * </p>
 * <p>
 * Javadoc is not required on a method that is tagged with the {@code @Override}
 * annotation. However under Java 5 it is not possible to mark a method required
 * for an interface (this was <i>corrected</i> under Java 6). Hence Checkstyle
 * supports using the convention of using a single {@code {@inheritDoc}} tag
 * instead of all the other tags.
 * </p>
 * <p>
 * Note that only inheritable items will allow the {@code {@inheritDoc}}
 * tag to be used in place of comments. Static methods at all visibilities,
 * private non-static methods and constructors are not inheritable.
 * </p>
 * <p>
 * For example, if the following method is implementing a method required by
 * an interface, then the Javadoc could be done as:
 * </p>
 * <pre>
 * &#47;** {&#64;inheritDoc} *&#47;
 * public int checkReturnTag(final int aTagIndex,
 *                           JavadocTag[] aTags,
 *                           int aLineNo)
 * </pre>
 * <ul>
 * <li>
 * Property {@code allowedAnnotations} - Specify the list of annotations
 * that allow missed documentation.
 * Type is {@code java.lang.String[]}.
 * Default value is {@code Override}.
 * </li>
 * <li>
 * Property {@code validateThrows} - Control whether to validate {@code throws} tags.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code scope} - Specify the visibility scope where Javadoc comments are checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code private}.
 * </li>
 * <li>
 * Property {@code excludeScope} - Specify the visibility scope where Javadoc comments
 * are not checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.Scope}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code allowMissingParamTags} - Control whether to ignore violations
 * when a method has parameters but does not have matching {@code param} tags in the javadoc.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowMissingReturnTag} - Control whether to ignore violations
 * when a method returns non-void type and does not have a {@code return} tag in the javadoc.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the default check:
 * </p>
 * <pre>
 * &lt;module name="JavadocMethod"/&gt;
 * </pre>
 * <p>
 * To configure the check for {@code public} scope, ignoring any missing param tags is:
 * </p>
 * <pre>
 * &lt;module name="JavadocMethod"&gt;
 *   &lt;property name="scope" value="public"/&gt;
 *   &lt;property name="allowMissingParamTags" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check for methods which are in {@code private},
 * but not in {@code protected} scope:
 * </p>
 * <pre>
 * &lt;module name="JavadocMethod"&gt;
 *   &lt;property name="scope" value="private"/&gt;
 *   &lt;property name="excludeScope" value="protected"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * To configure the check to validate {@code throws} tags, you can use following config.
 * </p>
 * <pre>
 * &lt;module name="JavadocMethod"&gt;
 *   &lt;property name="validateThrows" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <pre>
 * &#47;**
 *  * Actual exception thrown is child class of class that is declared in throws.
 *  * It is limitation of checkstyle (as checkstyle does not know type hierarchy).
 *  * Javadoc is valid not declaring FileNotFoundException
 *  * BUT checkstyle can not distinguish relationship between exceptions.
 *  * &#64;param file some file
 *  * &#64;throws IOException if some problem
 *  *&#47;
 * public void doSomething8(File file) throws IOException {
 *     if (file == null) {
 *         throw new FileNotFoundException(); // violation
 *     }
 * }
 *
 * &#47;**
 *  * Exact throw type referencing in javadoc even first is parent of second type.
 *  * It is a limitation of checkstyle (as checkstyle does not know type hierarchy).
 *  * This javadoc is valid for checkstyle and for javadoc tool.
 *  * &#64;param file some file
 *  * &#64;throws IOException if some problem
 *  * &#64;throws FileNotFoundException if file is not found
 *  *&#47;
 * public void doSomething9(File file) throws IOException {
 *     if (file == null) {
 *         throw new FileNotFoundException();
 *     }
 * }
 *
 * &#47;**
 *  * Ignore try block, but keep catch and finally blocks.
 *  *
 *  * &#64;param s String to parse
 *  * &#64;return A positive integer
 *  *&#47;
 * public int parsePositiveInt(String s) {
 *     try {
 *         int value = Integer.parseInt(s);
 *         if (value &lt;= 0) {
 *             throw new NumberFormatException(value + " is negative/zero"); // ok, try
 *         }
 *         return value;
 *     } catch (NumberFormatException ex) {
 *         throw new IllegalArgumentException("Invalid number", ex); // violation, catch
 *     } finally {
 *         throw new IllegalStateException("Should never reach here"); // violation, finally
 *     }
 * }
 *
 * &#47;**
 *  * Try block without catch is not ignored.
 *  *
 *  * &#64;return a String from standard input, if there is one
 *  *&#47;
 * public String readLine() {
 *     try (Scanner sc = new Scanner(System.in)) {
 *         if (!sc.hasNext()) {
 *             throw new IllegalStateException("Empty input"); // violation, not caught
 *         }
 *         return sc.next();
 *     }
 * }
 *
 * &#47;**
 *  * Lambda expressions are ignored as we do not know when the exception will be thrown.
 *  *
 *  * &#64;param s a String to be printed at some point in the future
 *  * &#64;return a Runnable to be executed when the string is to be printed
 *  *&#47;
 * public Runnable printLater(String s) {
 *     return () -&gt; {
 *         if (s == null) {
 *             throw new NullPointerException(); // ok
 *         }
 *         System.out.println(s);
 *     };
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code javadoc.classInfo}
 * </li>
 * <li>
 * {@code javadoc.duplicateTag}
 * </li>
 * <li>
 * {@code javadoc.expectedTag}
 * </li>
 * <li>
 * {@code javadoc.invalidInheritDoc}
 * </li>
 * <li>
 * {@code javadoc.return.expected}
 * </li>
 * <li>
 * {@code javadoc.unusedTag}
 * </li>
 * <li>
 * {@code javadoc.unusedTagGeneral}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@FileStatefulCheck
public class JavadocMethodCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CLASS_INFO = "javadoc.classInfo";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TAG_GENERAL = "javadoc.unusedTagGeneral";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_INVALID_INHERIT_DOC = "javadoc.invalidInheritDoc";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_UNUSED_TAG = "javadoc.unusedTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EXPECTED_TAG = "javadoc.expectedTag";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_RETURN_EXPECTED = "javadoc.return.expected";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_DUPLICATE_TAG = "javadoc.duplicateTag";

    /** Compiled regexp to match Javadoc tags that take an argument. */
    private static final Pattern MATCH_JAVADOC_ARG = CommonUtil.createPattern(
            "^\\s*(?>\\*|\\/\\*\\*)?\\s*@(throws|exception|param)\\s+(\\S+)\\s+\\S*");
    /** Compiled regexp to match Javadoc tags with argument but with missing description. */
    private static final Pattern MATCH_JAVADOC_ARG_MISSING_DESCRIPTION =
        CommonUtil.createPattern("^\\s*(?>\\*|\\/\\*\\*)?\\s*@(throws|exception|param)\\s+"
            + "(\\S[^*]*)(?:(\\s+|\\*\\/))?");

    /** Compiled regexp to look for a continuation of the comment. */
    private static final Pattern MATCH_JAVADOC_MULTILINE_CONT =
            CommonUtil.createPattern("(\\*\\/|@|[^\\s\\*])");

    /** Multiline finished at end of comment. */
    private static final String END_JAVADOC = "*/";
    /** Multiline finished at next Javadoc. */
    private static final String NEXT_TAG = "@";

    /** Compiled regexp to match Javadoc tags with no argument. */
    private static final Pattern MATCH_JAVADOC_NOARG =
            CommonUtil.createPattern("^\\s*(?>\\*|\\/\\*\\*)?\\s*@(return|see)\\s+\\S");
    /** Compiled regexp to match first part of multilineJavadoc tags. */
    private static final Pattern MATCH_JAVADOC_NOARG_MULTILINE_START =
            CommonUtil.createPattern("^\\s*(?>\\*|\\/\\*\\*)?\\s*@(return|see)\\s*$");
    /** Compiled regexp to match Javadoc tags with no argument and {}. */
    private static final Pattern MATCH_JAVADOC_NOARG_CURLY =
            CommonUtil.createPattern("\\{\\s*@(inheritDoc)\\s*\\}");

    /** Name of current class. */
    private String currentClassName;

    /** Specify the visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;

    /** Specify the visibility scope where Javadoc comments are not checked. */
    private Scope excludeScope;

    /**
     * Control whether to validate {@code throws} tags.
     */
    private boolean validateThrows;

    /**
     * Control whether to ignore violations when a method has parameters but does
     * not have matching {@code param} tags in the javadoc.
     */
    private boolean allowMissingParamTags;

    /**
     * Control whether to ignore violations when a method returns non-void type
     * and does not have a {@code return} tag in the javadoc.
     */
    private boolean allowMissingReturnTag;

    /** Specify the list of annotations that allow missed documentation. */
    private List<String> allowedAnnotations = Collections.singletonList("Override");

    /**
     * Setter to control whether to validate {@code throws} tags.
     *
     * @param value user's value.
     */
    public void setValidateThrows(boolean value) {
        validateThrows = value;
    }

    /**
     * Setter to specify the list of annotations that allow missed documentation.
     *
     * @param userAnnotations user's value.
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Arrays.asList(userAnnotations);
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are checked.
     *
     * @param scope a scope.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Setter to specify the visibility scope where Javadoc comments are not checked.
     *
     * @param excludeScope a scope.
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Setter to control whether to ignore violations when a method has parameters
     * but does not have matching {@code param} tags in the javadoc.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * Setter to control whether to ignore violations when a method returns non-void type
     * and does not have a {@code return} tag in the javadoc.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingReturnTag(boolean flag) {
        allowMissingReturnTag = flag;
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        currentClassName = "";
    }

    @Override
    public final void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF
                 || ast.getType() == TokenTypes.INTERFACE_DEF
                 || ast.getType() == TokenTypes.ENUM_DEF
                 || ast.getType() == TokenTypes.RECORD_DEF) {
            processClass(ast);
        }
        else {
            processAST(ast);
        }
    }

    @Override
    public final void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF
            || ast.getType() == TokenTypes.INTERFACE_DEF
            || ast.getType() == TokenTypes.ENUM_DEF
            || ast.getType() == TokenTypes.RECORD_DEF) {
            // perhaps it was inner class
            final int dotIdx = currentClassName.lastIndexOf('$');
            currentClassName = currentClassName.substring(0, dotIdx);
        }
    }

    /**
     * Called to process an AST when visiting it.
     *
     * @param ast the AST to process. Guaranteed to not be PACKAGE_DEF or
     *             IMPORT tokens.
     */
    private void processAST(DetailAST ast) {
        final Scope theScope = calculateScope(ast);
        if (shouldCheck(ast, theScope)) {
            final FileContents contents = getFileContents();
            final TextBlock textBlock = contents.getJavadocBefore(ast.getLineNo());

            if (textBlock != null) {
                checkComment(ast, textBlock);
            }
        }
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @param nodeScope the scope of the node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast, final Scope nodeScope) {
        final Scope surroundingScope = ScopeUtil.getSurroundingScope(ast);

        return (excludeScope == null
                || nodeScope != excludeScope
                && surroundingScope != excludeScope)
            && nodeScope.isIn(scope)
            && surroundingScope.isIn(scope);
    }

    /**
     * Checks the Javadoc for a method.
     *
     * @param ast the token for the method
     * @param comment the Javadoc comment
     */
    private void checkComment(DetailAST ast, TextBlock comment) {
        final List<JavadocTag> tags = getMethodTags(comment);

        if (!hasShortCircuitTag(ast, tags)) {
            if (ast.getType() == TokenTypes.ANNOTATION_FIELD_DEF) {
                checkReturnTag(tags, ast.getLineNo(), true);
            }
            else {
                final Iterator<JavadocTag> it = tags.iterator();
                // Check for inheritDoc
                boolean hasInheritDocTag = false;
                while (!hasInheritDocTag && it.hasNext()) {
                    hasInheritDocTag = it.next().isInheritDocTag();
                }
                final boolean reportExpectedTags = !hasInheritDocTag
                    && !AnnotationUtil.containsAnnotation(ast, allowedAnnotations);

                // COMPACT_CTOR_DEF has no parameters
                if (ast.getType() != TokenTypes.COMPACT_CTOR_DEF) {
                    checkParamTags(tags, ast, reportExpectedTags);
                }
                final List<ExceptionInfo> throwed =
                    combineExceptionInfo(getThrows(ast), getThrowed(ast));
                checkThrowsTags(tags, throwed, reportExpectedTags);
                if (CheckUtil.isNonVoidMethod(ast)) {
                    checkReturnTag(tags, ast.getLineNo(), reportExpectedTags);
                }

            }

            // Dump out all unused tags
            tags.stream().filter(javadocTag -> !javadocTag.isSeeOrInheritDocTag())
                .forEach(javadocTag -> log(javadocTag.getLineNo(), MSG_UNUSED_TAG_GENERAL));
        }
    }

    /**
     * Validates whether the Javadoc has a short circuit tag. Currently this is
     * the inheritTag. Any violations are logged.
     *
     * @param ast the construct being checked
     * @param tags the list of Javadoc tags associated with the construct
     * @return true if the construct has a short circuit tag.
     */
    private boolean hasShortCircuitTag(final DetailAST ast, final List<JavadocTag> tags) {
        boolean result = true;
        // Check if it contains {@inheritDoc} tag
        if (tags.size() == 1
                && tags.get(0).isInheritDocTag()) {
            // Invalid if private, a constructor, or a static method
            if (!JavadocTagInfo.INHERIT_DOC.isValidOn(ast)) {
                log(ast, MSG_INVALID_INHERIT_DOC);
            }
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Returns the scope for the method/constructor at the specified AST. If
     * the method is in an interface or annotation block, the scope is assumed
     * to be public.
     *
     * @param ast the token of the method/constructor
     * @return the scope of the method/constructor
     */
    private static Scope calculateScope(final DetailAST ast) {
        final Scope scope;

        if (ScopeUtil.isInInterfaceOrAnnotationBlock(ast)) {
            scope = Scope.PUBLIC;
        }
        else {
            final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
            scope = ScopeUtil.getScopeFromMods(mods);
        }
        return scope;
    }

    /**
     * Returns the tags in a javadoc comment. Only finds throws, exception,
     * param, return and see tags.
     *
     * @param comment the Javadoc comment
     * @return the tags found
     */
    private static List<JavadocTag> getMethodTags(TextBlock comment) {
        final String[] lines = comment.getText();
        final List<JavadocTag> tags = new ArrayList<>();
        int currentLine = comment.getStartLineNo() - 1;
        final int startColumnNumber = comment.getStartColNo();

        for (int i = 0; i < lines.length; i++) {
            currentLine++;
            final Matcher javadocArgMatcher =
                MATCH_JAVADOC_ARG.matcher(lines[i]);
            final Matcher javadocArgMissingDescriptionMatcher =
                MATCH_JAVADOC_ARG_MISSING_DESCRIPTION.matcher(lines[i]);
            final Matcher javadocNoargMatcher =
                MATCH_JAVADOC_NOARG.matcher(lines[i]);
            final Matcher noargCurlyMatcher =
                MATCH_JAVADOC_NOARG_CURLY.matcher(lines[i]);
            final Matcher noargMultilineStart =
                MATCH_JAVADOC_NOARG_MULTILINE_START.matcher(lines[i]);

            if (javadocArgMatcher.find()) {
                final int col = calculateTagColumn(javadocArgMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, javadocArgMatcher.group(1),
                        javadocArgMatcher.group(2)));
            }
            else if (javadocArgMissingDescriptionMatcher.find()) {
                final int col = calculateTagColumn(javadocArgMissingDescriptionMatcher, i,
                    startColumnNumber);
                tags.add(new JavadocTag(currentLine, col,
                    javadocArgMissingDescriptionMatcher.group(1),
                    javadocArgMissingDescriptionMatcher.group(2)));
            }
            else if (javadocNoargMatcher.find()) {
                final int col = calculateTagColumn(javadocNoargMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, javadocNoargMatcher.group(1)));
            }
            else if (noargCurlyMatcher.find()) {
                final int col = calculateTagColumn(noargCurlyMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, noargCurlyMatcher.group(1)));
            }
            else if (noargMultilineStart.find()) {
                tags.addAll(getMultilineNoArgTags(noargMultilineStart, lines, i, currentLine));
            }
        }
        return tags;
    }

    /**
     * Calculates column number using Javadoc tag matcher.
     *
     * @param javadocTagMatcher found javadoc tag matcher
     * @param lineNumber line number of Javadoc tag in comment
     * @param startColumnNumber column number of Javadoc comment beginning
     * @return column number
     */
    private static int calculateTagColumn(Matcher javadocTagMatcher,
            int lineNumber, int startColumnNumber) {
        int col = javadocTagMatcher.start(1) - 1;
        if (lineNumber == 0) {
            col += startColumnNumber;
        }
        return col;
    }

    /**
     * Gets multiline Javadoc tags with no arguments.
     *
     * @param noargMultilineStart javadoc tag Matcher
     * @param lines comment text lines
     * @param lineIndex line number that contains the javadoc tag
     * @param tagLine javadoc tag line number in file
     * @return javadoc tags with no arguments
     */
    private static List<JavadocTag> getMultilineNoArgTags(final Matcher noargMultilineStart,
            final String[] lines, final int lineIndex, final int tagLine) {
        int remIndex = lineIndex;
        Matcher multilineCont;

        do {
            remIndex++;
            multilineCont = MATCH_JAVADOC_MULTILINE_CONT.matcher(lines[remIndex]);
        } while (!multilineCont.find());

        final List<JavadocTag> tags = new ArrayList<>();
        final String lFin = multilineCont.group(1);
        if (!lFin.equals(NEXT_TAG)
            && !lFin.equals(END_JAVADOC)) {
            final String param1 = noargMultilineStart.group(1);
            final int col = noargMultilineStart.start(1) - 1;

            tags.add(new JavadocTag(tagLine, col, param1));
        }

        return tags;
    }

    /**
     * Computes the parameter nodes for a method.
     *
     * @param ast the method node.
     * @return the list of parameter nodes for ast.
     */
    private static List<DetailAST> getParameters(DetailAST ast) {
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        final List<DetailAST> returnValue = new ArrayList<>();

        DetailAST child = params.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                if (ident != null) {
                    returnValue.add(ident);
                }
            }
            child = child.getNextSibling();
        }
        return returnValue;
    }

    /**
     * Computes the exception nodes for a method.
     *
     * @param ast the method node.
     * @return the list of exception nodes for ast.
     */
    private static List<ExceptionInfo> getThrows(DetailAST ast) {
        final List<ExceptionInfo> returnValue = new ArrayList<>();
        final DetailAST throwsAST = ast
                .findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = throwsAST.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.IDENT
                        || child.getType() == TokenTypes.DOT) {
                    returnValue.add(getExceptionInfo(child));
                }
                child = child.getNextSibling();
            }
        }
        return returnValue;
    }

    /**
     * Get ExceptionInfo for all exceptions that throws in method code by 'throw new'.
     *
     * @param methodAst method DetailAST object where to find exceptions
     * @return list of ExceptionInfo
     */
    private static List<ExceptionInfo> getThrowed(DetailAST methodAst) {
        final List<ExceptionInfo> returnValue = new ArrayList<>();
        final DetailAST blockAst = methodAst.findFirstToken(TokenTypes.SLIST);
        if (blockAst != null) {
            final List<DetailAST> throwLiterals = findTokensInAstByType(blockAst,
                    TokenTypes.LITERAL_THROW);
            for (DetailAST throwAst : throwLiterals) {
                if (!isInIgnoreBlock(blockAst, throwAst)) {
                    final DetailAST newAst = throwAst.getFirstChild().getFirstChild();
                    if (newAst.getType() == TokenTypes.LITERAL_NEW) {
                        final DetailAST child = newAst.getFirstChild();
                        returnValue.add(getExceptionInfo(child));
                    }
                }
            }
        }
        return returnValue;
    }

    /**
     * Get ExceptionInfo instance.
     *
     * @param ast DetailAST object where to find exceptions node;
     * @return ExceptionInfo
     */
    private static ExceptionInfo getExceptionInfo(DetailAST ast) {
        final FullIdent ident = FullIdent.createFullIdent(ast);
        final DetailAST firstClassNameNode = getFirstClassNameNode(ast);
        return new ExceptionInfo(firstClassNameNode,
                new ClassInfo(new Token(ident)));
    }

    /**
     * Get node where class name of exception starts.
     *
     * @param ast DetailAST object where to find exceptions node;
     * @return exception node where class name starts
     */
    private static DetailAST getFirstClassNameNode(DetailAST ast) {
        DetailAST startNode = ast;
        while (startNode.getType() == TokenTypes.DOT) {
            startNode = startNode.getFirstChild();
        }
        return startNode;
    }

    /**
     * Checks if a 'throw' usage is contained within a block that should be ignored.
     * Such blocks consist of try (with catch) blocks, local classes, anonymous classes,
     * and lambda expressions. Note that a try block without catch is not considered.
     *
     * @param methodBodyAst DetailAST node representing the method body
     * @param throwAst DetailAST node representing the 'throw' literal
     * @return true if throwAst is inside a block that should be ignored
     */
    private static boolean isInIgnoreBlock(DetailAST methodBodyAst, DetailAST throwAst) {
        DetailAST ancestor = throwAst.getParent();
        while (ancestor != methodBodyAst) {
            if (ancestor.getType() == TokenTypes.LITERAL_TRY
                    && ancestor.findFirstToken(TokenTypes.LITERAL_CATCH) != null
                    || ancestor.getType() == TokenTypes.LAMBDA
                    || ancestor.getType() == TokenTypes.OBJBLOCK) {
                // throw is inside a try block, and there is a catch block,
                // or throw is inside a lambda expression/anonymous class/local class
                break;
            }
            if (ancestor.getType() == TokenTypes.LITERAL_CATCH
                    || ancestor.getType() == TokenTypes.LITERAL_FINALLY) {
                // if the throw is inside a catch or finally block,
                // skip the immediate ancestor (try token)
                ancestor = ancestor.getParent();
            }
            ancestor = ancestor.getParent();
        }
        return ancestor != methodBodyAst;
    }

    /**
     * Combine ExceptionInfo lists together by matching names.
     *
     * @param list1 list of ExceptionInfo
     * @param list2 list of ExceptionInfo
     * @return combined list of ExceptionInfo
     */
    private static List<ExceptionInfo> combineExceptionInfo(List<ExceptionInfo> list1,
                                                     List<ExceptionInfo> list2) {
        final List<ExceptionInfo> result = new ArrayList<>(list1);
        for (ExceptionInfo exceptionInfo : list2) {
            if (result.stream().noneMatch(item -> isExceptionInfoSame(item, exceptionInfo))) {
                result.add(exceptionInfo);
            }
        }
        return result;
    }

    /**
     * Finds node of specified type among root children, siblings, siblings children
     * on any deep level.
     *
     * @param root    DetailAST
     * @param astType value of TokenType
     * @return {@link List} of {@link DetailAST} nodes which matches the predicate.
     */
    public static List<DetailAST> findTokensInAstByType(DetailAST root, int astType) {
        final List<DetailAST> result = new ArrayList<>();
        DetailAST curNode = root;
        while (curNode != null) {
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
                if (curNode == root) {
                    toVisit = null;
                    break;
                }
            }
            curNode = toVisit;
            if (curNode != null && curNode.getType() == astType) {
                result.add(curNode);
            }
        }
        return result;
    }

    /**
     * Checks a set of tags for matching parameters.
     *
     * @param tags the tags to check
     * @param parent the node which takes the parameters
     * @param reportExpectedTags whether we should report if do not find
     *            expected tag
     */
    private void checkParamTags(final List<JavadocTag> tags,
            final DetailAST parent, boolean reportExpectedTags) {
        final List<DetailAST> params = getParameters(parent);
        final List<DetailAST> typeParams = CheckUtil
                .getTypeParameters(parent);

        // Loop over the tags, checking to see they exist in the params.
        final ListIterator<JavadocTag> tagIt = tags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = tagIt.next();

            if (!tag.isParamTag()) {
                continue;
            }

            tagIt.remove();

            final String arg1 = tag.getFirstArg();
            boolean found = removeMatchingParam(params, arg1);

            if (CommonUtil.startsWithChar(arg1, '<') && CommonUtil.endsWithChar(arg1, '>')) {
                found = searchMatchingTypeParameter(typeParams,
                        arg1.substring(1, arg1.length() - 1));
            }

            // Handle extra JavadocTag
            if (!found) {
                log(tag.getLineNo(), tag.getColumnNo(), MSG_UNUSED_TAG,
                        "@param", arg1);
            }
        }

        // Now dump out all type parameters/parameters without tags :- unless
        // the user has chosen to suppress these problems
        if (!allowMissingParamTags && reportExpectedTags) {
            for (DetailAST param : params) {
                log(param, MSG_EXPECTED_TAG,
                    JavadocTagInfo.PARAM.getText(), param.getText());
            }

            for (DetailAST typeParam : typeParams) {
                log(typeParam, MSG_EXPECTED_TAG,
                    JavadocTagInfo.PARAM.getText(),
                    "<" + typeParam.findFirstToken(TokenTypes.IDENT).getText()
                    + ">");
            }
        }
    }

    /**
     * Returns true if required type found in type parameters.
     *
     * @param typeParams
     *            list of type parameters
     * @param requiredTypeName
     *            name of required type
     * @return true if required type found in type parameters.
     */
    private static boolean searchMatchingTypeParameter(List<DetailAST> typeParams,
            String requiredTypeName) {
        // Loop looking for matching type param
        final Iterator<DetailAST> typeParamsIt = typeParams.iterator();
        boolean found = false;
        while (typeParamsIt.hasNext()) {
            final DetailAST typeParam = typeParamsIt.next();
            if (typeParam.findFirstToken(TokenTypes.IDENT).getText()
                    .equals(requiredTypeName)) {
                found = true;
                typeParamsIt.remove();
                break;
            }
        }
        return found;
    }

    /**
     * Remove parameter from params collection by name.
     *
     * @param params collection of DetailAST parameters
     * @param paramName name of parameter
     * @return true if parameter found and removed
     */
    private static boolean removeMatchingParam(List<DetailAST> params, String paramName) {
        boolean found = false;
        final Iterator<DetailAST> paramIt = params.iterator();
        while (paramIt.hasNext()) {
            final DetailAST param = paramIt.next();
            if (param.getText().equals(paramName)) {
                found = true;
                paramIt.remove();
                break;
            }
        }
        return found;
    }

    /**
     * Checks for only one return tag. All return tags will be removed from the
     * supplied list.
     *
     * @param tags the tags to check
     * @param lineNo the line number of the expected tag
     * @param reportExpectedTags whether we should report if do not find
     *            expected tag
     */
    private void checkReturnTag(List<JavadocTag> tags, int lineNo,
        boolean reportExpectedTags) {
        // Loop over tags finding return tags. After the first one, report an
        // violation.
        boolean found = false;
        final ListIterator<JavadocTag> it = tags.listIterator();
        while (it.hasNext()) {
            final JavadocTag javadocTag = it.next();
            if (javadocTag.isReturnTag()) {
                if (found) {
                    log(javadocTag.getLineNo(), javadocTag.getColumnNo(),
                            MSG_DUPLICATE_TAG,
                            JavadocTagInfo.RETURN.getText());
                }
                found = true;
                it.remove();
            }
        }

        // Handle there being no @return tags :- unless
        // the user has chosen to suppress these problems
        if (!found && !allowMissingReturnTag && reportExpectedTags) {
            log(lineNo, MSG_RETURN_EXPECTED);
        }
    }

    /**
     * Checks a set of tags for matching throws.
     *
     * @param tags the tags to check
     * @param throwsList the throws to check
     * @param reportExpectedTags whether we should report if do not find
     *            expected tag
     */
    private void checkThrowsTags(List<JavadocTag> tags,
            List<ExceptionInfo> throwsList, boolean reportExpectedTags) {
        // Loop over the tags, checking to see they exist in the throws.
        // The foundThrows used for performance only
        final Set<String> foundThrows = new HashSet<>();
        final ListIterator<JavadocTag> tagIt = tags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = tagIt.next();

            if (!tag.isThrowsTag()) {
                continue;
            }
            tagIt.remove();

            // Loop looking for matching throw
            final Token token = new Token(tag.getFirstArg(), tag.getLineNo(), tag
                    .getColumnNo());
            final ClassInfo documentedClassInfo = new ClassInfo(token);
            processThrows(throwsList, documentedClassInfo, foundThrows);
        }
        // Now dump out all throws without tags :- unless
        // the user has chosen to suppress these problems
        if (validateThrows && reportExpectedTags) {
            throwsList.stream().filter(exceptionInfo -> !exceptionInfo.isFound())
                .forEach(exceptionInfo -> {
                    final Token token = exceptionInfo.getName();
                    log(exceptionInfo.getAst(),
                        MSG_EXPECTED_TAG,
                        JavadocTagInfo.THROWS.getText(), token.getText());
                });
        }
    }

    /**
     * Verifies that documented exception is in throws.
     *
     * @param throwsList list of throws
     * @param documentedClassInfo documented exception class info
     * @param foundThrows previously found throws
     */
    private static void processThrows(List<ExceptionInfo> throwsList,
                                      ClassInfo documentedClassInfo, Set<String> foundThrows) {
        ExceptionInfo foundException = null;

        // First look for matches on the exception name
        for (ExceptionInfo exceptionInfo : throwsList) {
            if (isClassNamesSame(exceptionInfo.getName().getText(),
                    documentedClassInfo.getName().getText())) {
                foundException = exceptionInfo;
                break;
            }
        }

        if (foundException != null) {
            foundException.setFound();
            foundThrows.add(documentedClassInfo.getName().getText());
        }
    }

    /**
     * Check that ExceptionInfo objects are same by name.
     *
     * @param info1 ExceptionInfo object
     * @param info2 ExceptionInfo object
     * @return true is ExceptionInfo object have the same name
     */
    private static boolean isExceptionInfoSame(ExceptionInfo info1, ExceptionInfo info2) {
        return isClassNamesSame(info1.getName().getText(),
                                    info2.getName().getText());
    }

    /**
     * Check that class names are same by short name of class. If some class name is fully
     * qualified it is cut to short name.
     *
     * @param class1 class name
     * @param class2 class name
     * @return true is ExceptionInfo object have the same name
     */
    private static boolean isClassNamesSame(String class1, String class2) {
        boolean result = false;
        if (class1.equals(class2)) {
            result = true;
        }
        else {
            final String separator = ".";
            if (class1.contains(separator) || class2.contains(separator)) {
                final String class1ShortName = class1
                        .substring(class1.lastIndexOf('.') + 1);
                final String class2ShortName = class2
                        .substring(class2.lastIndexOf('.') + 1);
                result = class1ShortName.equals(class2ShortName);
            }
        }
        return result;
    }

    /**
     * Processes class definition.
     *
     * @param ast class definition to process.
     */
    private void processClass(DetailAST ast) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        String innerClass = ident.getText();

        innerClass = "$" + innerClass;
        currentClassName += innerClass;
    }

    /**
     * Contains class's {@code Token}.
     */
    private static class ClassInfo {

        /** {@code FullIdent} associated with this class. */
        private final Token name;

        /**
         * Creates new instance of class information object.
         *
         * @param className token which represents class name.
         * @throws IllegalArgumentException when className is nulls
         */
        protected ClassInfo(final Token className) {
            name = className;
        }

        /**
         * Gets class name.
         *
         * @return class name
         */
        public final Token getName() {
            return name;
        }

    }

    /**
     * Represents text element with location in the text.
     */
    private static class Token {

        /** Token's column number. */
        private final int columnNo;
        /** Token's line number. */
        private final int lineNo;
        /** Token's text. */
        private final String text;

        /**
         * Creates token.
         *
         * @param text token's text
         * @param lineNo token's line number
         * @param columnNo token's column number
         */
        /* package */ Token(String text, int lineNo, int columnNo) {
            this.text = text;
            this.lineNo = lineNo;
            this.columnNo = columnNo;
        }

        /**
         * Converts FullIdent to Token.
         *
         * @param fullIdent full ident to convert.
         */
        /* package */ Token(FullIdent fullIdent) {
            text = fullIdent.getText();
            lineNo = fullIdent.getLineNo();
            columnNo = fullIdent.getColumnNo();
        }

        /**
         * Gets text of the token.
         *
         * @return text of the token
         */
        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "Token[" + text + "(" + lineNo
                + "x" + columnNo + ")]";
        }

    }

    /** Stores useful information about declared exception. */
    private static class ExceptionInfo {

        /** AST node representing this exception. */
        private final DetailAST ast;

        /** Class information associated with this exception. */
        private final ClassInfo classInfo;
        /** Does the exception have throws tag associated with. */
        private boolean found;

        /**
         * Creates new instance for {@code FullIdent}.
         *
         * @param ast AST node representing this exception
         * @param classInfo class info
         */
        /* package */ ExceptionInfo(DetailAST ast, ClassInfo classInfo) {
            this.ast = ast;
            this.classInfo = classInfo;
        }

        /**
         * Gets the AST node representing this exception.
         *
         * @return the AST node representing this exception
         */
        private DetailAST getAst() {
            return ast;
        }

        /** Mark that the exception has associated throws tag. */
        private void setFound() {
            found = true;
        }

        /**
         * Checks that the exception has throws tag associated with it.
         *
         * @return whether the exception has throws tag associated with
         */
        private boolean isFound() {
            return found;
        }

        /**
         * Gets exception name.
         *
         * @return exception's name
         */
        private Token getName() {
            return classInfo.getName();
        }

    }

}
