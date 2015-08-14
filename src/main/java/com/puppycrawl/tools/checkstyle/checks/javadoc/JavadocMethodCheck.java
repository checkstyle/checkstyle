////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import antlr.collections.AST;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.ScopeUtils;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

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
    public static final String MSG_EXCPECTED_TAG = "javadoc.expectedTag";

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

    /** compiled regexp to match Javadoc tags that take an argument * */
    private static final Pattern MATCH_JAVADOC_ARG =
        Utils.createPattern("@(throws|exception|param)\\s+(\\S+)\\s+\\S*");

    /** compiled regexp to match first part of multilineJavadoc tags * */
    private static final Pattern MATCH_JAVADOC_ARG_MULTILINE_START =
        Utils.createPattern("@(throws|exception|param)\\s+(\\S+)\\s*$");

    /** compiled regexp to look for a continuation of the comment * */
    private static final Pattern MATCH_JAVADOC_MULTILINE_CONT =
        Utils.createPattern("(\\*/|@|[^\\s\\*])");

    /** Multiline finished at end of comment * */
    private static final String END_JAVADOC = "*/";
    /** Multiline finished at next Javadoc * */
    private static final String NEXT_TAG = "@";

    /** compiled regexp to match Javadoc tags with no argument * */
    private static final Pattern MATCH_JAVADOC_NOARG =
        Utils.createPattern("@(return|see)\\s+\\S");
    /** compiled regexp to match first part of multilineJavadoc tags * */
    private static final Pattern MATCH_JAVADOC_NOARG_MULTILINE_START =
        Utils.createPattern("@(return|see)\\s*$");
    /** compiled regexp to match Javadoc tags with no argument and {} * */
    private static final Pattern MATCH_JAVADOC_NOARG_CURLY =
        Utils.createPattern("\\{\\s*@(inheritDoc)\\s*\\}");

    /** Maximum children allowed * */
    private static final int MAX_CHILDREN = 7;

    /** Maximum children allowed * */
    private static final int BODY_SIZE = 3;

    /** Default value of minimal amount of lines in method to demand documentation presence.*/
    private static final int DEFAULT_MIN_LINE_COUNT = -1;

    /** the visibility scope where Javadoc comments are checked * */
    private Scope scope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked * */
    private Scope excludeScope;

    /** Minimal amount of lines in method to demand documentation presence.*/
    private int minLineCount = DEFAULT_MIN_LINE_COUNT;

    /**
     * controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     */
    private boolean allowUndeclaredRTE;

    /**
     * Allows validating throws tags.
     */
    private boolean validateThrows;

    /**
     * controls whether to allow documented exceptions that are subclass of one
     * of declared exception. Defaults to false (backward compatibility).
     */
    private boolean allowThrowsTagsForSubclasses;

    /**
     * controls whether to ignore errors when a method has parameters but does
     * not have matching param tags in the javadoc. Defaults to false.
     */
    private boolean allowMissingParamTags;

    /**
     * controls whether to ignore errors when a method declares that it throws
     * exceptions but does not have matching throws tags in the javadoc.
     * Defaults to false.
     */
    private boolean allowMissingThrowsTags;

    /**
     * controls whether to ignore errors when a method returns non-void type
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
        ignoreMethodNamesRegex = Utils.createPattern(regex);
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
    public void setAllowedAnnotations(String userAnnotations) {
        final List<String> annotations = new ArrayList<>();
        final String[] sAnnotations = userAnnotations.split(",");
        for (int i = 0; i < sAnnotations.length; i++) {
            sAnnotations[i] = sAnnotations[i].trim();
        }

        Collections.addAll(annotations, sAnnotations);
        allowedAnnotations = annotations;
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
     * controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowUndeclaredRTE(boolean flag) {
        allowUndeclaredRTE = flag;
    }

    /**
     * controls whether to allow documented exception that are subclass of one
     * of declared exceptions.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowThrowsTagsForSubclasses(boolean flag) {
        allowThrowsTagsForSubclasses = flag;
    }

    /**
     * controls whether to allow a method which has parameters to omit matching
     * param tags in the javadoc. Defaults to false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingParamTags(boolean flag) {
        allowMissingParamTags = flag;
    }

    /**
     * controls whether to allow a method which declares that it throws
     * exceptions to omit matching throws tags in the javadoc. Defaults to
     * false.
     *
     * @param flag a {@code Boolean} value
     */
    public void setAllowMissingThrowsTags(boolean flag) {
        allowMissingThrowsTags = flag;
    }

    /**
     * controls whether to allow a method which returns non-void type to omit
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
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT,
                          TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF,
                          TokenTypes.INTERFACE_DEF,
                          TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                          TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                          TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    protected final void processAST(DetailAST ast) {
        if ((ast.getType() == TokenTypes.METHOD_DEF || ast.getType() == TokenTypes.CTOR_DEF)
            && getMethodsNumberOfLine(ast) <= minLineCount
            || hasAllowedAnnotations(ast)) {
            return;
        }
        final Scope theScope = calculateScope(ast);
        if (shouldCheck(ast, theScope)) {
            final FileContents contents = getFileContents();
            final TextBlock cmt = contents.getJavadocBefore(ast.getLineNo());

            if (cmt == null) {
                if (!isMissingJavadocAllowed(ast)) {
                    log(ast, MSG_JAVADOC_MISSING);
                }
            }
            else {
                checkComment(ast, cmt);
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
        int numberOfLines;
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
                && (isSetterMethod(ast) || isGetterMethod(ast))
            || matchesSkipRegex(ast);
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

        return nodeScope.isIn(scope)
                && surroundingScope.isIn(scope)
                && (excludeScope == null || !nodeScope.isIn(excludeScope)
                    || !surroundingScope.isIn(excludeScope));
    }

    /**
     * Checks the Javadoc for a method.
     *
     * @param ast the token for the method
     * @param comment the Javadoc comment
     */
    private void checkComment(DetailAST ast, TextBlock comment) {
        final List<JavadocTag> tags = getMethodTags(comment);

        if (hasShortCircuitTag(ast, tags)) {
            return;
        }

        Iterator<JavadocTag> it = tags.iterator();
        if (ast.getType() != TokenTypes.ANNOTATION_FIELD_DEF) {
            // Check for inheritDoc
            boolean hasInheritDocTag = false;
            while (it.hasNext() && !hasInheritDocTag) {
                hasInheritDocTag |= it.next().isInheritDocTag();
            }

            checkParamTags(tags, ast, !hasInheritDocTag);
            checkThrowsTags(tags, getThrows(ast), !hasInheritDocTag);
            if (isFunction(ast)) {
                checkReturnTag(tags, ast.getLineNo(), !hasInheritDocTag);
            }
        }

        // Dump out all unused tags
        it = tags.iterator();
        while (it.hasNext()) {
            final JavadocTag jt = it.next();
            if (!jt.isSeeOrInheritDocTag()) {
                log(jt.getLineNo(), MSG_UNUSED_TAG_GENERAL);
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
        return ScopeUtils.inInterfaceOrAnnotationBlock(ast) ? Scope.PUBLIC
                : declaredScope;
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
                int col = javadocArgMatcher.start(1) - 1;
                if (i == 0) {
                    col += comment.getStartColNo();
                }
                tags.add(new JavadocTag(currentLine, col, javadocArgMatcher
                        .group(1), javadocArgMatcher.group(2)));
            }
            else if (javadocNoargMatcher.find()) {
                int col = javadocNoargMatcher.start(1) - 1;
                if (i == 0) {
                    col += comment.getStartColNo();
                }
                tags.add(new JavadocTag(currentLine, col, javadocNoargMatcher
                        .group(1)));
            }
            else if (noargCurlyMatcher.find()) {
                int col = noargCurlyMatcher.start(1) - 1;
                if (i == 0) {
                    col += comment.getStartColNo();
                }
                tags.add(new JavadocTag(currentLine, col, noargCurlyMatcher
                        .group(1)));
            }
            else if (argMultilineStart.find()) {
                final String p1 = argMultilineStart.group(1);
                final String p2 = argMultilineStart.group(2);
                int col = argMultilineStart.start(1) - 1;
                if (i == 0) {
                    col += comment.getStartColNo();
                }

                // Look for the rest of the comment if all we saw was
                // the tag and the name. Stop when we see '*/' (end of
                // Javadoc), '@' (start of next tag), or anything that's
                // not whitespace or '*' characters.
                int remIndex = i + 1;
                while (remIndex < lines.length) {
                    final Matcher multilineCont = MATCH_JAVADOC_MULTILINE_CONT
                            .matcher(lines[remIndex]);
                    if (multilineCont.find()) {
                        remIndex = lines.length;
                        final String lFin = multilineCont.group(1);
                        if (!lFin.equals(NEXT_TAG)
                            && !lFin.equals(END_JAVADOC)) {
                            tags.add(new JavadocTag(currentLine, col, p1, p2));
                        }
                    }
                    remIndex++;
                }
            }
            else if (noargMultilineStart.find()) {
                final String p1 = noargMultilineStart.group(1);
                final int col = noargMultilineStart.start(1) - 1;

                // Look for the rest of the comment if all we saw was
                // the tag and the name. Stop when we see '*/' (end of
                // Javadoc), '@' (start of next tag), or anything that's
                // not whitespace or '*' characters.
                int remIndex = i + 1;
                while (remIndex < lines.length) {
                    final Matcher multilineCont = MATCH_JAVADOC_MULTILINE_CONT
                            .matcher(lines[remIndex]);
                    multilineCont.find();
                    remIndex = lines.length;
                    final String lFin = multilineCont.group(1);
                    if (!lFin.equals(NEXT_TAG)
                        && !lFin.equals(END_JAVADOC)) {
                        tags.add(new JavadocTag(currentLine, col, p1));
                    }
                    remIndex++;
                }
            }
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
        final List<DetailAST> retVal = Lists.newArrayList();

        DetailAST child = params.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                retVal.add(ident);
            }
            child = child.getNextSibling();
        }
        return retVal;
    }

    /**
     * Computes the exception nodes for a method.
     *
     * @param ast the method node.
     * @return the list of exception nodes for ast.
     */
    private List<ExceptionInfo> getThrows(DetailAST ast) {
        final List<ExceptionInfo> retVal = Lists.newArrayList();
        final DetailAST throwsAST = ast
                .findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = throwsAST.getFirstChild();
            while (child != null) {
                if (child.getType() == TokenTypes.IDENT
                        || child.getType() == TokenTypes.DOT) {
                    final FullIdent fi = FullIdent.createFullIdent(child);
                    final ExceptionInfo ei = new ExceptionInfo(createClassInfo(new Token(fi),
                            getCurrentClassName()));
                    retVal.add(ei);
                }
                child = child.getNextSibling();
            }
        }
        return retVal;
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

            boolean found = false;

            // Loop looking for matching param
            final Iterator<DetailAST> paramIt = params.iterator();
            final String arg1 = tag.getFirstArg();
            while (paramIt.hasNext()) {
                final DetailAST param = paramIt.next();
                if (param.getText().equals(arg1)) {
                    found = true;
                    paramIt.remove();
                    break;
                }
            }

            if (Utils.startsWithChar(arg1, '<') && Utils.endsWithChar(arg1, '>')) {
                // Loop looking for matching type param
                final Iterator<DetailAST> typeParamsIt = typeParams.iterator();
                while (typeParamsIt.hasNext()) {
                    final DetailAST typeParam = typeParamsIt.next();
                    if (typeParam.findFirstToken(TokenTypes.IDENT).getText()
                            .equals(
                                    arg1.substring(1,
                                        arg1.length() - 1))) {
                        found = true;
                        typeParamsIt.remove();
                        break;
                    }
                }

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
                log(param, MSG_EXCPECTED_TAG,
                    JavadocTagInfo.PARAM.getText(), param.getText());
            }

            for (DetailAST typeParam : typeParams) {
                log(typeParam, MSG_EXCPECTED_TAG,
                    JavadocTagInfo.PARAM.getText(),
                    "<" + typeParam.findFirstToken(TokenTypes.IDENT).getText()
                    + ">");
            }
        }
    }

    /**
     * Checks whether a method is a function.
     *
     * @param ast the method node.
     * @return whether the method is a function.
     */
    private static boolean isFunction(DetailAST ast) {
        boolean retVal = false;
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST typeAST = ast.findFirstToken(TokenTypes.TYPE);
            if (typeAST.findFirstToken(TokenTypes.LITERAL_VOID) == null) {
                retVal = true;
            }
        }
        return retVal;
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
            final JavadocTag jt = it.next();
            if (jt.isReturnTag()) {
                if (found) {
                    log(jt.getLineNo(), jt.getColumnNo(),
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
            final AbstractClassInfo documentedCI = createClassInfo(token,
                    getCurrentClassName());
            boolean found = foundThrows.contains(documentedEx);

            // First look for matches on the exception name
            ListIterator<ExceptionInfo> throwIt = throwsList.listIterator();
            while (!found && throwIt.hasNext()) {
                final ExceptionInfo ei = throwIt.next();

                if (ei.getName().getText().equals(
                        documentedCI.getName().getText())) {
                    found = true;
                    ei.setFound();
                    foundThrows.add(documentedEx);
                }
            }

            // Now match on the exception type
            throwIt = throwsList.listIterator();
            while (!found && throwIt.hasNext()) {
                final ExceptionInfo ei = throwIt.next();

                if (documentedCI.getClazz() == ei.getClazz()) {
                    found = true;
                    ei.setFound();
                    foundThrows.add(documentedEx);
                }
                else if (allowThrowsTagsForSubclasses) {
                    found = isSubclass(documentedCI.getClazz(), ei.getClazz());
                }
            }

            // Handle extra JavadocTag.
            if (!found) {
                boolean reqd = true;
                if (allowUndeclaredRTE) {
                    reqd = !isUnchecked(documentedCI.getClazz());
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
            for (ExceptionInfo ei : throwsList) {
                if (!ei.isFound()) {
                    final Token fi = ei.getName();
                    log(fi.getLineNo(), fi.getColumnNo(),
                        MSG_EXCPECTED_TAG,
                        JavadocTagInfo.THROWS.getText(), fi.getText());
                }
            }
        }
    }

    /**
     * Returns whether an AST represents a setter method.
     * @param ast the AST to check with
     * @return whether the AST represents a setter method
     */
    private static boolean isSetterMethod(final DetailAST ast) {
        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper setter method which does not throw any
        // exceptions.
        if (ast.getType() != TokenTypes.METHOD_DEF
                || ast.getChildCount() != MAX_CHILDREN) {
            return false;
        }

        // Should I handle only being in a class????

        // Check the name matches format setX...
        final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
        final String name = type.getNextSibling().getText();
        if (!name.matches("^set[A-Z].*")) { // Depends on JDK 1.4
            return false;
        }

        // Check the return type is void
        if (type.getChildCount(TokenTypes.LITERAL_VOID) == 0) {
            return false;
        }

        // Check that is had only one parameter
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        if (params.getChildCount(TokenTypes.PARAMETER_DEF) != 1) {
            return false;
        }

        // Now verify that the body consists of:
        // SLIST -> EXPR -> ASSIGN
        // SEMI
        // RCURLY
        final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);
        if (slist == null || slist.getChildCount() != BODY_SIZE) {
            return false;
        }

        final AST expr = slist.getFirstChild();
        return expr.getFirstChild().getType() == TokenTypes.ASSIGN;
    }

    /**
     * Returns whether an AST represents a getter method.
     * @param ast the AST to check with
     * @return whether the AST represents a getter method
     */
    private static boolean isGetterMethod(final DetailAST ast) {
        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper getter method which does not throw any
        // exceptions.
        if (ast.getType() != TokenTypes.METHOD_DEF
                || ast.getChildCount() != MAX_CHILDREN) {
            return false;
        }

        // Check the name matches format of getX or isX. Technically I should
        // check that the format isX is only used with a boolean type.
        final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
        final String name = type.getNextSibling().getText();
        if (!name.matches("^(is|get)[A-Z].*")) { // Depends on JDK 1.4
            return false;
        }

        // Check the return type is void
        if (type.getChildCount(TokenTypes.LITERAL_VOID) > 0) {
            return false;
        }

        // Check that is had only one parameter
        final DetailAST params = ast.findFirstToken(TokenTypes.PARAMETERS);
        if (params.getChildCount(TokenTypes.PARAMETER_DEF) > 0) {
            return false;
        }

        // Now verify that the body consists of:
        // SLIST -> RETURN
        // RCURLY
        final DetailAST slist = ast.findFirstToken(TokenTypes.SLIST);
        if (slist == null || slist.getChildCount() != 2) {
            return false;
        }

        final AST expr = slist.getFirstChild();
        return expr.getType() == TokenTypes.LITERAL_RETURN;

    }

    /** Stores useful information about declared exception. */
    private static class ExceptionInfo {
        /** does the exception have throws tag associated with. */
        private boolean found;
        /** class information associated with this exception. */
        private final AbstractClassInfo classInfo;

        /**
         * Creates new instance for {@code FullIdent}.
         *
         * @param classInfo clas info
         */
        ExceptionInfo(AbstractClassInfo classInfo) {
            this.classInfo = classInfo;
        }

        /** Mark that the exception has associated throws tag */
        final void setFound() {
            found = true;
        }

        /** @return whether the exception has throws tag associated with */
        final boolean isFound() {
            return found;
        }

        /** @return exception's name */
        final Token getName() {
            return classInfo.getName();
        }

        /** @return class for this exception */
        final Class<?> getClazz() {
            return classInfo.getClazz();
        }
    }
}
