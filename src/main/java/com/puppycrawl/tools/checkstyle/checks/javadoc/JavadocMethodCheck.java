////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * Checks the Javadoc of a method or constructor.
 *
 * @author Oliver Burn
 * @author Rick Giles
 * @author o_sukhodoslky
 */
@SuppressWarnings("deprecation")
public class JavadocMethodCheck extends AbstractTypeAwareCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_MISSING = "javadoc.missing";

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
    private static final Pattern MATCH_JAVADOC_ARG =
            CommonUtils.createPattern("@(throws|exception|param)\\s+(\\S+)\\s+\\S*");

    /** Compiled regexp to match first part of multilineJavadoc tags. */
    private static final Pattern MATCH_JAVADOC_ARG_MULTILINE_START =
            CommonUtils.createPattern("@(throws|exception|param)\\s+(\\S+)\\s*$");

    /** Compiled regexp to look for a continuation of the comment. */
    private static final Pattern MATCH_JAVADOC_MULTILINE_CONT =
            CommonUtils.createPattern("(\\*/|@|[^\\s\\*])");

    /** Multiline finished at end of comment. */
    private static final String END_JAVADOC = "*/";
    /** Multiline finished at next Javadoc. */
    private static final String NEXT_TAG = "@";

    /** Compiled regexp to match Javadoc tags with no argument. */
    private static final Pattern MATCH_JAVADOC_NOARG =
            CommonUtils.createPattern("@(return|see)\\s+\\S");
    /** Compiled regexp to match first part of multilineJavadoc tags. */
    private static final Pattern MATCH_JAVADOC_NOARG_MULTILINE_START =
            CommonUtils.createPattern("@(return|see)\\s*$");
    /** Compiled regexp to match Javadoc tags with no argument and {}. */
    private static final Pattern MATCH_JAVADOC_NOARG_CURLY =
            CommonUtils.createPattern("\\{\\s*@(inheritDoc)\\s*\\}");

    /** Default value of minimal amount of lines in method to demand documentation presence.*/
    private static final int DEFAULT_MIN_LINE_COUNT = -1;

    /** The visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;

    /** The visibility scope where Javadoc comments shouldn't be checked. */
    private Scope excludeScope;

    /** Minimal amount of lines in method to demand documentation presence.*/
    private int minLineCount = DEFAULT_MIN_LINE_COUNT;

    /**
     * Controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     */
    private boolean allowUndeclaredRTE;

    /**
     * Allows validating throws tags.
     */
    private boolean validateThrows;

    /**
     * Controls whether to allow documented exceptions that are subclass of one
     * of declared exception. Defaults to false (backward compatibility).
     */
    private boolean allowThrowsTagsForSubclasses;

    /**
     * Controls whether to ignore errors when a method has parameters but does
     * not have matching param tags in the javadoc. Defaults to false.
     */
    private boolean allowMissingParamTags;

    /**
     * Controls whether to ignore errors when a method declares that it throws
     * exceptions but does not have matching throws tags in the javadoc.
     * Defaults to false.
     */
    private boolean allowMissingThrowsTags;

    /**
     * Controls whether to ignore errors when a method returns non-void type
     * but does not have a return tag in the javadoc. Defaults to false.
     */
    private boolean allowMissingReturnTag;

    /**
     * Controls whether to ignore errors when there is no javadoc. Defaults to
     * false.
     */
    private boolean allowMissingJavadoc;

    /**
     * Controls whether to allow missing Javadoc on accessor methods for
     * properties (setters and getters).
     */
    private boolean allowMissingPropertyJavadoc;

    /** List of annotations that could allow missed documentation. */
    private List<String> allowedAnnotations = Collections.singletonList("Override");

    /** Method names that match this pattern do not require javadoc blocks. */
    private Pattern ignoreMethodNamesRegex;

    /**
     * Set regex for matching method names to ignore.
     * @param regex regex for matching method names.
     */
    public void setIgnoreMethodNamesRegex(String regex) {
        ignoreMethodNamesRegex = CommonUtils.createPattern(regex);
    }

    /**
     * Sets minimal amount of lines in method.
     * @param value user's value.
     */
    public void setMinLineCount(int value) {
        minLineCount = value;
    }

    /**
     * Allow validating throws tag.
     * @param value user's value.
     */
    public void setValidateThrows(boolean value) {
        validateThrows = value;
    }

    /**
     * Sets list of annotations.
     * @param userAnnotations user's value.
     */
    public void setAllowedAnnotations(String... userAnnotations) {
        allowedAnnotations = Arrays.asList(userAnnotations);
    }

    /**
     * Set the scope.
     *
     * @param from a {@code String} value
     */
    public void setScope(String from) {
        scope = Scope.getInstance(from);
    }

    /**
     * Set the excludeScope.
     *
     * @param excludeScope a {@code String} value
     */
    public void setExcludeScope(String excludeScope) {
        this.excludeScope = Scope.getInstance(excludeScope);
    }

    /**
     * Controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowUndeclaredRTE(boolean flag) {
        allowUndeclaredRTE = flag;
    }

    /**
     * Controls whether to allow documented exception that are subclass of one
     * of declared exceptions.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowThrowsTagsForSubclasses(boolean flag) {
        allowThrowsTagsForSubclasses = flag;
    }

    /**
     * Controls whether to allow a method which has parameters to omit matching
     * param tags in the javadoc. Defaults to false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * Controls whether to allow a method which declares that it throws
     * exceptions to omit matching throws tags in the javadoc. Defaults to
     * false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingThrowsTags(boolean flag) {
        allowMissingThrowsTags = flag;
    }

    /**
     * Controls whether to allow a method which returns non-void type to omit
     * the return tag in the javadoc. Defaults to false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingReturnTag(boolean flag) {
        allowMissingReturnTag = flag;
    }

    /**
     * Controls whether to ignore errors when there is no javadoc. Defaults to
     * false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingJavadoc(boolean flag) {
        allowMissingJavadoc = flag;
    }

    /**
     * Controls whether to ignore errors when there is no javadoc for a
     * property accessor (setter/getter methods). Defaults to false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingPropertyJavadoc(final boolean flag) {
        allowMissingPropertyJavadoc = flag;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    protected final void processAST(DetailAST ast) {
        final Scope theScope = calculateScope(ast);
        if (shouldCheck(ast, theScope)) {
            final FileContents contents = getFileContents();
            final TextBlock textBlock = contents.getJavadocBefore(ast.getLineNo());

            if (textBlock == null) {
                if (!isMissingJavadocAllowed(ast)) {
                    log(ast, MSG_JAVADOC_MISSING);
                }
            }
            else {
                checkComment(ast, textBlock);
            }
        }
    }

    /**
     * Some javadoc.
     * @param methodDef Some javadoc.
     * @return Some javadoc.
     */
    private boolean hasAllowedAnnotations(DetailAST methodDef) {
        final DetailAST modifiersNode = methodDef.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST annotationNode = modifiersNode.findFirstToken(TokenTypes.ANNOTATION);
        while (annotationNode != null && annotationNode.getType() == TokenTypes.ANNOTATION) {
            DetailAST identNode = annotationNode.findFirstToken(TokenTypes.IDENT);
            if (identNode == null) {
                identNode = annotationNode.findFirstToken(TokenTypes.DOT)
                    .findFirstToken(TokenTypes.IDENT);
            }
            if (allowedAnnotations.contains(identNode.getText())) {
                return true;
            }
            annotationNode = annotationNode.getNextSibling();
        }
        return false;
    }

    /**
     * Some javadoc.
     * @param methodDef Some javadoc.
     * @return Some javadoc.
     */
    private static int getMethodsNumberOfLine(DetailAST methodDef) {
        final int numberOfLines;
        final DetailAST lcurly = methodDef.getLastChild();
        final DetailAST rcurly = lcurly.getLastChild();

        if (lcurly.getFirstChild() == rcurly) {
            numberOfLines = 1;
        }
        else {
            numberOfLines = rcurly.getLineNo() - lcurly.getLineNo() - 1;
        }
        return numberOfLines;
    }

    @Override
    protected final void logLoadError(Token ident) {
        logLoadErrorImpl(ident.getLineNo(), ident.getColumnNo(),
            MSG_CLASS_INFO,
            JavadocTagInfo.THROWS.getText(), ident.getText());
    }

    /**
     * The JavadocMethodCheck is about to report a missing Javadoc.
     * This hook can be used by derived classes to allow a missing javadoc
     * in some situations.  The default implementation checks
     * {@code allowMissingJavadoc} and
     * {@code allowMissingPropertyJavadoc} properties, do not forget
     * to call {@code super.isMissingJavadocAllowed(ast)} in case
     * you want to keep this logic.
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    protected boolean isMissingJavadocAllowed(final DetailAST ast) {
        return allowMissingJavadoc
            || allowMissingPropertyJavadoc
                && (CheckUtils.isSetterMethod(ast) || CheckUtils.isGetterMethod(ast))
            || matchesSkipRegex(ast)
            || isContentsAllowMissingJavadoc(ast);
    }

    /**
     * Checks if the Javadoc can be missing if the method or constructor is
     * below the minimum line count or has a special annotation.
     *
     * @param ast the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    private boolean isContentsAllowMissingJavadoc(DetailAST ast) {
        return (ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF)
                && (getMethodsNumberOfLine(ast) <= minLineCount || hasAllowedAnnotations(ast));
    }

    /**
     * Checks if the given method name matches the regex. In that case
     * we skip enforcement of javadoc for this method
     * @param methodDef {@link TokenTypes#METHOD_DEF METHOD_DEF}
     * @return true if given method name matches the regex.
     */
    private boolean matchesSkipRegex(DetailAST methodDef) {
        if (ignoreMethodNamesRegex != null) {
            final DetailAST ident = methodDef.findFirstToken(TokenTypes.IDENT);
            final String methodName = ident.getText();

            final Matcher matcher = ignoreMethodNamesRegex.matcher(methodName);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether we should check this node.
     *
     * @param ast a given node.
     * @param nodeScope the scope of the node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST ast, final Scope nodeScope) {
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(ast);

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
            final Iterator<JavadocTag> it = tags.iterator();
            if (ast.getType() == TokenTypes.ANNOTATION_FIELD_DEF) {
                checkReturnTag(tags, ast.getLineNo(), true);
            }
            else {
                // Check for inheritDoc
                boolean hasInheritDocTag = false;
                while (!hasInheritDocTag && it.hasNext()) {
                    hasInheritDocTag = it.next().isInheritDocTag();
                }
                final boolean reportExpectedTags = !hasInheritDocTag && !hasAllowedAnnotations(ast);

                checkParamTags(tags, ast, reportExpectedTags);
                checkThrowsTags(tags, getThrows(ast), reportExpectedTags);
                if (CheckUtils.isNonVoidMethod(ast)) {
                    checkReturnTag(tags, ast.getLineNo(), reportExpectedTags);
                }
            }

            // Dump out all unused tags
            for (JavadocTag javadocTag : tags) {
                if (!javadocTag.isSeeOrInheritDocTag()) {
                    log(javadocTag.getLineNo(), MSG_UNUSED_TAG_GENERAL);
                }
            }
        }
    }

    /**
     * Validates whether the Javadoc has a short circuit tag. Currently this is
     * the inheritTag. Any errors are logged.
     *
     * @param ast the construct being checked
     * @param tags the list of Javadoc tags associated with the construct
     * @return true if the construct has a short circuit tag.
     */
    private boolean hasShortCircuitTag(final DetailAST ast,
            final List<JavadocTag> tags) {
        // Check if it contains {@inheritDoc} tag
        if (tags.size() != 1
                || !tags.get(0).isInheritDocTag()) {
            return false;
        }

        // Invalid if private, a constructor, or a static method
        if (!JavadocTagInfo.INHERIT_DOC.isValidOn(ast)) {
            log(ast, MSG_INVALID_INHERIT_DOC);
        }

        return true;
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
        final DetailAST mods = ast.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);

        if (ScopeUtils.isInInterfaceOrAnnotationBlock(ast)) {
            return Scope.PUBLIC;
        }
        else {
            return declaredScope;
        }
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
        final List<JavadocTag> tags = Lists.newArrayList();
        int currentLine = comment.getStartLineNo() - 1;
        final int startColumnNumber = comment.getStartColNo();

        for (int i = 0; i < lines.length; i++) {
            currentLine++;
            final Matcher javadocArgMatcher =
                MATCH_JAVADOC_ARG.matcher(lines[i]);
            final Matcher javadocNoargMatcher =
                MATCH_JAVADOC_NOARG.matcher(lines[i]);
            final Matcher noargCurlyMatcher =
                MATCH_JAVADOC_NOARG_CURLY.matcher(lines[i]);
            final Matcher argMultilineStart =
                MATCH_JAVADOC_ARG_MULTILINE_START.matcher(lines[i]);
            final Matcher noargMultilineStart =
                MATCH_JAVADOC_NOARG_MULTILINE_START.matcher(lines[i]);

            if (javadocArgMatcher.find()) {
                final int col = calculateTagColumn(javadocArgMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, javadocArgMatcher.group(1),
                        javadocArgMatcher.group(2)));
            }
            else if (javadocNoargMatcher.find()) {
                final int col = calculateTagColumn(javadocNoargMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, javadocNoargMatcher.group(1)));
            }
            else if (noargCurlyMatcher.find()) {
                final int col = calculateTagColumn(noargCurlyMatcher, i, startColumnNumber);
                tags.add(new JavadocTag(currentLine, col, noargCurlyMatcher.group(1)));
            }
            else if (argMultilineStart.find()) {
                final int col = calculateTagColumn(argMultilineStart, i, startColumnNumber);
                tags.addAll(getMultilineArgTags(argMultilineStart, col, lines, i, currentLine));
            }
            else if (noargMultilineStart.find()) {
                tags.addAll(getMultilineNoArgTags(noargMultilineStart, lines, i, currentLine));
            }
        }
        return tags;
    }

    /**
     * Calculates column number using Javadoc tag matcher.
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
     * Gets multiline Javadoc tags with arguments.
     * @param argMultilineStart javadoc tag Matcher
     * @param column column number of Javadoc tag
     * @param lines comment text lines
     * @param lineIndex line number that contains the javadoc tag
     * @param tagLine javadoc tag line number in file
     * @return javadoc tags with arguments
     */
    private static List<JavadocTag> getMultilineArgTags(final Matcher argMultilineStart,
            final int column, final String[] lines, final int lineIndex, final int tagLine) {
        final List<JavadocTag> tags = new ArrayList<>();
        final String param1 = argMultilineStart.group(1);
        final String param2 = argMultilineStart.group(2);
        int remIndex = lineIndex + 1;
        while (remIndex < lines.length) {
            final Matcher multilineCont = MATCH_JAVADOC_MULTILINE_CONT.matcher(lines[remIndex]);
            if (multilineCont.find()) {
                remIndex = lines.length;
                final String lFin = multilineCont.group(1);
                if (!lFin.equals(NEXT_TAG)
                    && !lFin.equals(END_JAVADOC)) {
                    tags.add(new JavadocTag(tagLine, column, param1, param2));
                }
            }
            remIndex++;
        }
        return tags;
    }

    /**
     * Gets multiline Javadoc tags with no arguments.
     * @param noargMultilineStart javadoc tag Matcher
     * @param lines comment text lines
     * @param lineIndex line number that contains the javadoc tag
     * @param tagLine javadoc tag line number in file
     * @return javadoc tags with no arguments
     */
    private static List<JavadocTag> getMultilineNoArgTags(final Matcher noargMultilineStart,
            final String[] lines, final int lineIndex, final int tagLine) {
        final String param1 = noargMultilineStart.group(1);
        final int col = noargMultilineStart.start(1) - 1;
        final List<JavadocTag> tags = new ArrayList<>();
        int remIndex = lineIndex + 1;
        while (remIndex < lines.length) {
            final Matcher multilineCont = MATCH_JAVADOC_MULTILINE_CONT
                    .matcher(lines[remIndex]);
            if (multilineCont.find()) {
                remIndex = lines.length;
                final String lFin = multilineCont.group(1);
                if (!lFin.equals(NEXT_TAG)
                    && !lFin.equals(END_JAVADOC)) {
                    tags.add(new JavadocTag(tagLine, col, param1));
                }
            }
            remIndex++;
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
        final List<DetailAST> returnValue = Lists.newArrayList();

        DetailAST child = params.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                returnValue.add(ident);
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
    private List<ExceptionInfo> getThrows(DetailAST ast) {
        final List<ExceptionInfo> returnValue = Lists.newArrayList();
        final DetailAST throwsAST = ast
                .findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = throwsAST.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.IDENT
                        || child.getType() == TokenTypes.DOT) {
                    final FullIdent ident = FullIdent.createFullIdent(child);
                    final ExceptionInfo exceptionInfo = new ExceptionInfo(
                            createClassInfo(new Token(ident), getCurrentClassName()));
                    returnValue.add(exceptionInfo);
                }
                child = child.getNextSibling();
            }
        }
        return returnValue;
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
        final List<DetailAST> typeParams = CheckUtils
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

            if (CommonUtils.startsWithChar(arg1, '<') && CommonUtils.endsWithChar(arg1, '>')) {
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
        // error.
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
        final Set<String> foundThrows = Sets.newHashSet();
        final ListIterator<JavadocTag> tagIt = tags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = tagIt.next();

            if (!tag.isThrowsTag()) {
                continue;
            }
            tagIt.remove();

            // Loop looking for matching throw
            final String documentedEx = tag.getFirstArg();
            final Token token = new Token(tag.getFirstArg(), tag.getLineNo(), tag
                    .getColumnNo());
            final AbstractClassInfo documentedClassInfo = createClassInfo(token,
                    getCurrentClassName());
            final boolean found = foundThrows.contains(documentedEx)
                    || isInThrows(throwsList, documentedClassInfo, foundThrows);

            // Handle extra JavadocTag.
            if (!found) {
                boolean reqd = true;
                if (allowUndeclaredRTE) {
                    reqd = !isUnchecked(documentedClassInfo.getClazz());
                }

                if (reqd && validateThrows) {
                    log(tag.getLineNo(), tag.getColumnNo(),
                        MSG_UNUSED_TAG,
                        JavadocTagInfo.THROWS.getText(), tag.getFirstArg());

                }
            }
        }
        // Now dump out all throws without tags :- unless
        // the user has chosen to suppress these problems
        if (!allowMissingThrowsTags && reportExpectedTags) {
            for (ExceptionInfo exceptionInfo : throwsList) {
                if (!exceptionInfo.isFound()) {
                    final Token token = exceptionInfo.getName();
                    log(token.getLineNo(), token.getColumnNo(),
                            MSG_EXPECTED_TAG,
                            JavadocTagInfo.THROWS.getText(), token.getText());
                }
            }
        }
    }

    /**
     * Verifies that documented exception is in throws.
     *
     * @param throwsList list of throws
     * @param documentedClassInfo documented exception class info
     * @param foundThrows previously found throws
     * @return true if documented exception is in throws.
     */
    private boolean isInThrows(List<ExceptionInfo> throwsList,
            AbstractClassInfo documentedClassInfo, Set<String> foundThrows) {
        boolean found = false;
        ExceptionInfo foundException = null;

        // First look for matches on the exception name
        final ListIterator<ExceptionInfo> throwIt = throwsList.listIterator();
        while (!found && throwIt.hasNext()) {
            final ExceptionInfo exceptionInfo = throwIt.next();

            if (exceptionInfo.getName().getText().equals(
                    documentedClassInfo.getName().getText())) {
                found = true;
                foundException = exceptionInfo;
            }
        }

        // Now match on the exception type
        final ListIterator<ExceptionInfo> exceptionInfoIt = throwsList.listIterator();
        while (!found && exceptionInfoIt.hasNext()) {
            final ExceptionInfo exceptionInfo = exceptionInfoIt.next();

            if (documentedClassInfo.getClazz() == exceptionInfo.getClazz()) {
                found = true;
                foundException = exceptionInfo;
            }
            else if (allowThrowsTagsForSubclasses) {
                found = isSubclass(documentedClassInfo.getClazz(), exceptionInfo.getClazz());
            }
        }

        if (foundException != null) {
            foundException.setFound();
            foundThrows.add(documentedClassInfo.getName().getText());
        }

        return found;
    }

    /** Stores useful information about declared exception. */
    private static class ExceptionInfo {
        /** Class information associated with this exception. */
        private final AbstractClassInfo classInfo;
        /** Does the exception have throws tag associated with. */
        private boolean found;

        /**
         * Creates new instance for {@code FullIdent}.
         *
         * @param classInfo class info
         */
        ExceptionInfo(AbstractClassInfo classInfo) {
            this.classInfo = classInfo;
        }

        /** Mark that the exception has associated throws tag. */
        private void setFound() {
            found = true;
        }

        /**
         * Checks that the exception has throws tag associated with it.
         * @return whether the exception has throws tag associated with
         */
        private boolean isFound() {
            return found;
        }

        /**
         * Gets exception name.
         * @return exception's name
         */
        private Token getName() {
            return classInfo.getName();
        }

        /**
         * Gets exception class.
         * @return class for this exception
         */
        private Class<?> getClazz() {
            return classInfo.getClazz();
        }
    }
}
