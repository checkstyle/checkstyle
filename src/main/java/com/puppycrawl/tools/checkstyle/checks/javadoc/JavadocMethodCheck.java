////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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
 * Checks the Javadoc of a method or constructor.
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

    /** Compiled regexp to match first part of multilineJavadoc tags. */
    private static final Pattern MATCH_JAVADOC_ARG_MULTILINE_START = CommonUtil.createPattern(
            "^\\s*(?>\\*|\\/\\*\\*)?\\s*@(throws|exception|param)\\s+(\\S+)\\s*$");

    /** Compiled regexp to look for a continuation of the comment. */
    private static final Pattern MATCH_JAVADOC_MULTILINE_CONT =
            CommonUtil.createPattern("(\\*/|@|[^\\s\\*])");

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

    /** Stack of maps for type params. */
    private final Deque<Map<String, AbstractClassInfo>> currentTypeParams = new ArrayDeque<>();

    /** Imports details. **/
    private final Set<String> imports = new HashSet<>();

    /** Full identifier for package of the method. **/
    private FullIdent packageFullIdent;

    /** Name of current class. */
    private String currentClassName;

    /** {@code ClassResolver} instance for current tree. */
    private ClassResolver classResolver;

    /** The visibility scope where Javadoc comments are checked. */
    private Scope scope = Scope.PRIVATE;

    /** The visibility scope where Javadoc comments shouldn't be checked. */
    private Scope excludeScope;

    /**
     * Controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     */
    // -@cs[AbbreviationAsWordInName] We can not change it as,
    // check's property is part of API (used in configurations).
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
     * Controls whether to ignore violations when a method has parameters but does
     * not have matching param tags in the javadoc. Defaults to false.
     */
    private boolean allowMissingParamTags;

    /**
     * Controls whether to ignore violations when a method declares that it throws
     * exceptions but does not have matching throws tags in the javadoc.
     * Defaults to false.
     */
    private boolean allowMissingThrowsTags;

    /**
     * Controls whether to ignore violations when a method returns non-void type
     * but does not have a return tag in the javadoc. Defaults to false.
     */
    private boolean allowMissingReturnTag;

    /** List of annotations that allow missed documentation. */
    private List<String> allowedAnnotations = Collections.singletonList("Override");

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
     * @param scope a scope.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Set the excludeScope.
     *
     * @param excludeScope a scope.
     */
    public void setExcludeScope(Scope excludeScope) {
        this.excludeScope = excludeScope;
    }

    /**
     * Controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     *
     * @param flag a {@code Boolean} value
     */
    // -@cs[AbbreviationAsWordInName] We can not change it as,
    // check's property is part of API (used in configurations).
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
     * Controls whether to log class loading errors to the checkstyle report
     * instead of throwing a RTE.
     *
     * @param logLoadErrors true if errors should be logged
     * @deprecated No substitute.
     */
    @Deprecated
    public final void setLogLoadErrors(boolean logLoadErrors) {
        // no code
    }

    /**
     * Controls whether to show class loading errors in the checkstyle report.
     *
     * @param suppressLoadErrors true if errors shouldn't be shown
     * @deprecated No substitute.
     */
    @Deprecated
    public final void setSuppressLoadErrors(boolean suppressLoadErrors) {
        // no code
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
        };
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
    public void beginTree(DetailAST rootAST) {
        packageFullIdent = FullIdent.createFullIdent(null);
        imports.clear();
        // add java.lang.* since it's always imported
        imports.add("java.lang.*");
        classResolver = null;
        currentClassName = "";
        currentTypeParams.clear();
    }

    @Override
    public final void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.PACKAGE_DEF) {
            processPackage(ast);
        }
        else if (ast.getType() == TokenTypes.IMPORT) {
            processImport(ast);
        }
        else if (ast.getType() == TokenTypes.CLASS_DEF
                 || ast.getType() == TokenTypes.INTERFACE_DEF
                 || ast.getType() == TokenTypes.ENUM_DEF) {
            processClass(ast);
        }
        else {
            if (ast.getType() == TokenTypes.METHOD_DEF) {
                processTypeParams(ast);
            }
            processAST(ast);
        }
    }

    @Override
    public final void leaveToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.CLASS_DEF
            || ast.getType() == TokenTypes.INTERFACE_DEF
            || ast.getType() == TokenTypes.ENUM_DEF) {
            // perhaps it was inner class
            int dotIdx = currentClassName.lastIndexOf('$');
            if (dotIdx == -1) {
                // perhaps just a class
                dotIdx = currentClassName.lastIndexOf('.');
            }
            if (dotIdx == -1) {
                // looks like a topmost class
                currentClassName = "";
            }
            else {
                currentClassName = currentClassName.substring(0, dotIdx);
            }
            currentTypeParams.pop();
        }
        else if (ast.getType() == TokenTypes.METHOD_DEF) {
            currentTypeParams.pop();
        }
    }

    /**
     * Called to process an AST when visiting it.
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

                checkParamTags(tags, ast, reportExpectedTags);
                checkThrowsTags(tags, getThrows(ast), reportExpectedTags);
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
        for (int remIndex = lineIndex + 1; remIndex < lines.length; remIndex++) {
            final Matcher multilineCont = MATCH_JAVADOC_MULTILINE_CONT.matcher(lines[remIndex]);
            if (multilineCont.find()) {
                final String lFin = multilineCont.group(1);
                if (!lFin.equals(NEXT_TAG)
                    && !lFin.equals(END_JAVADOC)) {
                    tags.add(new JavadocTag(tagLine, column, param1, param2));
                }
                break;
            }
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
    private List<ExceptionInfo> getThrows(DetailAST ast) {
        final List<ExceptionInfo> returnValue = new ArrayList<>();
        final DetailAST throwsAST = ast
                .findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = throwsAST.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.IDENT
                        || child.getType() == TokenTypes.DOT) {
                    final FullIdent ident = FullIdent.createFullIdent(child);
                    final ExceptionInfo exceptionInfo = new ExceptionInfo(
                            createClassInfo(new Token(ident), currentClassName));
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
            final String documentedEx = tag.getFirstArg();
            final Token token = new Token(tag.getFirstArg(), tag.getLineNo(), tag
                    .getColumnNo());
            final AbstractClassInfo documentedClassInfo = createClassInfo(token,
                    currentClassName);
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
            throwsList.stream().filter(exceptionInfo -> !exceptionInfo.isFound())
                .forEach(exceptionInfo -> {
                    final Token token = exceptionInfo.getName();
                    log(token.getLineNo(), token.getColumnNo(),
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
     * @return true if documented exception is in throws.
     */
    private boolean isInThrows(List<ExceptionInfo> throwsList,
            AbstractClassInfo documentedClassInfo, Set<String> foundThrows) {
        boolean found = false;
        ExceptionInfo foundException = null;

        // First look for matches on the exception name
        for (ExceptionInfo exceptionInfo : throwsList) {
            if (exceptionInfo.getName().getText().equals(
                    documentedClassInfo.getName().getText())) {
                found = true;
                foundException = exceptionInfo;
                break;
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

    /**
     * Is exception is unchecked (subclass of {@code RuntimeException}
     * or {@code Error}.
     *
     * @param exception {@code Class} of exception to check
     * @return true  if exception is unchecked
     *         false if exception is checked
     */
    private static boolean isUnchecked(Class<?> exception) {
        return isSubclass(exception, RuntimeException.class)
            || isSubclass(exception, Error.class);
    }

    /**
     * Checks if one class is subclass of another.
     *
     * @param child {@code Class} of class
     *               which should be child
     * @param parent {@code Class} of class
     *                which should be parent
     * @return true  if aChild is subclass of aParent
     *         false otherwise
     */
    private static boolean isSubclass(Class<?> child, Class<?> parent) {
        return parent != null && child != null
            && parent.isAssignableFrom(child);
    }

    /**
     * Returns the current tree's ClassResolver.
     * @return {@code ClassResolver} for current tree.
     */
    private ClassResolver getClassResolver() {
        if (classResolver == null) {
            classResolver =
                new ClassResolver(getClass().getClassLoader(),
                                  packageFullIdent.getText(),
                                  imports);
        }
        return classResolver;
    }

    /**
     * Attempts to resolve the Class for a specified name.
     * @param resolvableClassName name of the class to resolve
     * @param className name of surrounding class.
     * @return the resolved class or {@code null}
     *          if unable to resolve the class.
     * @noinspection WeakerAccess
     */
    // -@cs[ForbidWildcardAsReturnType] The class is deprecated and will be removed soon.
    private Class<?> resolveClass(String resolvableClassName,
                                          String className) {
        Class<?> clazz;
        try {
            clazz = getClassResolver().resolve(resolvableClassName, className);
        }
        // -@cs[IllegalCatch] Exception type is not predictable.
        catch (final Exception ignored) {
            clazz = null;
        }
        return clazz;
    }

    /**
     * Tries to load class. Logs error if unable.
     * @param ident name of class which we try to load.
     * @param className name of surrounding class.
     * @return {@code Class} for a ident.
     * @noinspection WeakerAccess, MethodOnlyUsedFromInnerClass
     */
    // -@cs[ForbidWildcardAsReturnType] The class is deprecated and will be removed soon.
    private Class<?> tryLoadClass(Token ident, String className) {
        return resolveClass(ident.getText(), className);
    }

    /**
     * Collects the details of a package.
     * @param ast node containing the package details
     */
    private void processPackage(DetailAST ast) {
        final DetailAST nameAST = ast.getLastChild().getPreviousSibling();
        packageFullIdent = FullIdent.createFullIdent(nameAST);
    }

    /**
     * Collects the details of imports.
     * @param ast node containing the import details
     */
    private void processImport(DetailAST ast) {
        final FullIdent name = FullIdent.createFullIdentBelow(ast);
        imports.add(name.getText());
    }

    /**
     * Process type params (if any) for given class, enum or method.
     * @param ast class, enum or method to process.
     */
    private void processTypeParams(DetailAST ast) {
        final DetailAST params =
            ast.findFirstToken(TokenTypes.TYPE_PARAMETERS);

        final Map<String, AbstractClassInfo> paramsMap = new HashMap<>();
        currentTypeParams.push(paramsMap);

        if (params != null) {
            for (DetailAST child = params.getFirstChild();
                 child != null;
                 child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.TYPE_PARAMETER) {
                    final DetailAST bounds =
                        child.findFirstToken(TokenTypes.TYPE_UPPER_BOUNDS);
                    if (bounds != null) {
                        final FullIdent name =
                            FullIdent.createFullIdentBelow(bounds);
                        final AbstractClassInfo classInfo =
                            createClassInfo(new Token(name), currentClassName);
                        final String alias =
                                child.findFirstToken(TokenTypes.IDENT).getText();
                        paramsMap.put(alias, classInfo);
                    }
                }
            }
        }
    }

    /**
     * Processes class definition.
     * @param ast class definition to process.
     */
    private void processClass(DetailAST ast) {
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        String innerClass = ident.getText();

        if (!currentClassName.isEmpty()) {
            innerClass = "$" + innerClass;
        }
        currentClassName += innerClass;
        processTypeParams(ast);
    }

    /**
     * Creates class info for given name.
     * @param name name of type.
     * @param surroundingClass name of surrounding class.
     * @return class info for given name.
     */
    private AbstractClassInfo createClassInfo(final Token name,
                                              final String surroundingClass) {
        final AbstractClassInfo result;
        final AbstractClassInfo classInfo = findClassAlias(name.getText());
        if (classInfo == null) {
            result = new RegularClass(name, surroundingClass, this);
        }
        else {
            result = new ClassAlias(name, classInfo);
        }
        return result;
    }

    /**
     * Looking if a given name is alias.
     * @param name given name
     * @return ClassInfo for alias if it exists, null otherwise
     * @noinspection WeakerAccess
     */
    private AbstractClassInfo findClassAlias(final String name) {
        AbstractClassInfo classInfo = null;
        final Iterator<Map<String, AbstractClassInfo>> iterator = currentTypeParams
                .descendingIterator();
        while (iterator.hasNext()) {
            final Map<String, AbstractClassInfo> paramMap = iterator.next();
            classInfo = paramMap.get(name);
            if (classInfo != null) {
                break;
            }
        }
        return classInfo;
    }

    /**
     * Contains class's {@code Token}.
     */
    private abstract static class AbstractClassInfo {

        /** {@code FullIdent} associated with this class. */
        private final Token name;

        /**
         * Creates new instance of class information object.
         * @param className token which represents class name.
         */
        protected AbstractClassInfo(final Token className) {
            if (className == null) {
                throw new IllegalArgumentException(
                    "ClassInfo's name should be non-null");
            }
            name = className;
        }

        /**
         * Returns class associated with that object.
         * @return {@code Class} associated with an object.
         */
        // -@cs[ForbidWildcardAsReturnType] The class is deprecated and will be removed soon.
        public abstract Class<?> getClazz();

        /**
         * Gets class name.
         * @return class name
         */
        public final Token getName() {
            return name;
        }

    }

    /** Represents regular classes/enums. */
    private static final class RegularClass extends AbstractClassInfo {

        /** Name of surrounding class. */
        private final String surroundingClass;
        /** The check we use to resolve classes. */
        private final JavadocMethodCheck check;
        /** Is class loadable. */
        private boolean loadable = true;
        /** {@code Class} object of this class if it's loadable. */
        private Class<?> classObj;

        /**
         * Creates new instance of of class information object.
         * @param name {@code FullIdent} associated with new object.
         * @param surroundingClass name of current surrounding class.
         * @param check the check we use to load class.
         */
        /* package */ RegularClass(final Token name,
                             final String surroundingClass,
                             final JavadocMethodCheck check) {
            super(name);
            this.surroundingClass = surroundingClass;
            this.check = check;
        }

        @Override
        public Class<?> getClazz() {
            if (loadable && classObj == null) {
                setClazz(check.tryLoadClass(getName(), surroundingClass));
            }
            return classObj;
        }

        /**
         * Associates {@code Class} with an object.
         * @param clazz {@code Class} to associate with.
         */
        private void setClazz(Class<?> clazz) {
            classObj = clazz;
            loadable = clazz != null;
        }

        @Override
        public String toString() {
            return "RegularClass[name=" + getName()
                    + ", in class='" + surroundingClass + '\''
                    + ", check=" + check.hashCode()
                    + ", loadable=" + loadable
                    + ", class=" + classObj
                    + ']';
        }

    }

    /** Represents type param which is "alias" for real type. */
    private static class ClassAlias extends AbstractClassInfo {

        /** Class information associated with the alias. */
        private final AbstractClassInfo classInfo;

        /**
         * Creates new instance of the class.
         * @param name token which represents name of class alias.
         * @param classInfo class information associated with the alias.
         */
        /* package */ ClassAlias(final Token name, AbstractClassInfo classInfo) {
            super(name);
            this.classInfo = classInfo;
        }

        @Override
        public final Class<?> getClazz() {
            return classInfo.getClazz();
        }

        @Override
        public String toString() {
            return "ClassAlias[alias " + getName() + " for " + classInfo.getName() + "]";
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
         * @param text token's text
         * @param lineNo token's line number
         * @param columnNo token's column number
         */
        /* default */ Token(String text, int lineNo, int columnNo) {
            this.text = text;
            this.lineNo = lineNo;
            this.columnNo = columnNo;
        }

        /**
         * Converts FullIdent to Token.
         * @param fullIdent full ident to convert.
         */
        /* default */ Token(FullIdent fullIdent) {
            text = fullIdent.getText();
            lineNo = fullIdent.getLineNo();
            columnNo = fullIdent.getColumnNo();
        }

        /**
         * Gets line number of the token.
         * @return line number of the token
         */
        public int getLineNo() {
            return lineNo;
        }

        /**
         * Gets column number of the token.
         * @return column number of the token
         */
        public int getColumnNo() {
            return columnNo;
        }

        /**
         * Gets text of the token.
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

        /** Class information associated with this exception. */
        private final AbstractClassInfo classInfo;
        /** Does the exception have throws tag associated with. */
        private boolean found;

        /**
         * Creates new instance for {@code FullIdent}.
         *
         * @param classInfo class info
         */
        /* package */ ExceptionInfo(AbstractClassInfo classInfo) {
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
