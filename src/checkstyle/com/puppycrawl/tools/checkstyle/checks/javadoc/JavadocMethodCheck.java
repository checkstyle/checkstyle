////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import antlr.collections.AST;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.AbstractTypeAwareCheck;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks the Javadoc of a method or constructor.
 *
 * @author Oliver Burn
 * @author Rick Giles
 * @author o_sukhodoslky
 */
public class JavadocMethodCheck extends AbstractTypeAwareCheck
{
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

    /** the visibility scope where Javadoc comments are checked * */
    private Scope mScope = Scope.PRIVATE;

    /** the visibility scope where Javadoc comments shouldn't be checked * */
    private Scope mExcludeScope;

    /**
     * controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     */
    private boolean mAllowUndeclaredRTE;

    /**
     * controls whether to allow documented exceptions that are subclass of one
     * of declared exception. Defaults to false (backward compatibility).
     */
    private boolean mAllowThrowsTagsForSubclasses;

    /**
     * controls whether to ignore errors when a method has parameters but does
     * not have matching param tags in the javadoc. Defaults to false.
     */
    private boolean mAllowMissingParamTags;

    /**
     * controls whether to ignore errors when a method declares that it throws
     * exceptions but does not have matching throws tags in the javadoc.
     * Defaults to false.
     */
    private boolean mAllowMissingThrowsTags;

    /**
     * controls whether to ignore errors when a method returns non-void type
     * but does not have a return tag in the javadoc. Defaults to false.
     */
    private boolean mAllowMissingReturnTag;

    /**
     * Controls whether to ignore errors when there is no javadoc. Defaults to
     * false.
     */
    private boolean mAllowMissingJavadoc;

    /**
     * Controls whether to allow missing Javadoc on accessor methods for
     * properties (setters and getters).
     */
    private boolean mAllowMissingPropertyJavadoc;

    /**
     * Set the scope.
     *
     * @param aFrom a <code>String</code> value
     */
    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /**
     * Set the excludeScope.
     *
     * @param aScope a <code>String</code> value
     */
    public void setExcludeScope(String aScope)
    {
        mExcludeScope = Scope.getInstance(aScope);
    }

    /**
     * controls whether to allow documented exceptions that are not declared if
     * they are a subclass of java.lang.RuntimeException.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowUndeclaredRTE(boolean aFlag)
    {
        mAllowUndeclaredRTE = aFlag;
    }

    /**
     * controls whether to allow documented exception that are subclass of one
     * of declared exceptions.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowThrowsTagsForSubclasses(boolean aFlag)
    {
        mAllowThrowsTagsForSubclasses = aFlag;
    }

    /**
     * controls whether to allow a method which has parameters to omit matching
     * param tags in the javadoc. Defaults to false.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingParamTags(boolean aFlag)
    {
        mAllowMissingParamTags = aFlag;
    }

    /**
     * controls whether to allow a method which declares that it throws
     * exceptions to omit matching throws tags in the javadoc. Defaults to
     * false.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingThrowsTags(boolean aFlag)
    {
        mAllowMissingThrowsTags = aFlag;
    }

    /**
     * controls whether to allow a method which returns non-void type to omit
     * the return tag in the javadoc. Defaults to false.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingReturnTag(boolean aFlag)
    {
        mAllowMissingReturnTag = aFlag;
    }

    /**
     * Controls whether to ignore errors when there is no javadoc. Defaults to
     * false.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingJavadoc(boolean aFlag)
    {
        mAllowMissingJavadoc = aFlag;
    }

    /**
     * Controls whether to ignore errors when there is no javadoc for a
     * property accessor (setter/getter methods). Defaults to false.
     *
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingPropertyJavadoc(final boolean aFlag)
    {
        mAllowMissingPropertyJavadoc = aFlag;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.PACKAGE_DEF, TokenTypes.IMPORT,
                          TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF,
                          TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                          TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF,
                          TokenTypes.ANNOTATION_FIELD_DEF,
        };
    }

    @Override
    protected final void processAST(DetailAST aAST)
    {
        final Scope theScope = calculateScope(aAST);
        if (shouldCheck(aAST, theScope)) {
            final FileContents contents = getFileContents();
            final TextBlock cmt = contents.getJavadocBefore(aAST.getLineNo());

            if (cmt == null) {
                if (!isMissingJavadocAllowed(aAST)) {
                    log(aAST, "javadoc.missing");
                }
            }
            else {
                checkComment(aAST, cmt, theScope);
            }
        }
    }

    @Override
    protected final void logLoadError(Token aIdent)
    {
        logLoadErrorImpl(aIdent.getLineNo(), aIdent.getColumnNo(),
            "javadoc.classInfo",
            JavadocTagInfo.THROWS.getText(), aIdent.getText());
    }

    /**
     * The JavadocMethodCheck is about to report a missing Javadoc.
     * This hook can be used by derived classes to allow a missing javadoc
     * in some situations.  The default implementation checks
     * <code>allowMissingJavadoc</code> and
     * <code>allowMissingPropertyJavadoc</code> properties, do not forget
     * to call <code>super.isMissingJavadocAllowed(aAST)</code> in case
     * you want to keep this logic.
     * @param aAST the tree node for the method or constructor.
     * @return True if this method or constructor doesn't need Javadoc.
     */
    protected boolean isMissingJavadocAllowed(final DetailAST aAST)
    {
        return mAllowMissingJavadoc || isOverrideMethod(aAST)
            || (mAllowMissingPropertyJavadoc
                && (isSetterMethod(aAST) || isGetterMethod(aAST)));
    }

    /**
     * Whether we should check this node.
     *
     * @param aAST a given node.
     * @param aScope the scope of the node.
     * @return whether we should check a given node.
     */
    private boolean shouldCheck(final DetailAST aAST, final Scope aScope)
    {
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(aAST);

        return aScope.isIn(mScope)
                && surroundingScope.isIn(mScope)
                && ((mExcludeScope == null) || !aScope.isIn(mExcludeScope)
                    || !surroundingScope.isIn(mExcludeScope));
    }

    /**
     * Checks the Javadoc for a method.
     *
     * @param aAST the token for the method
     * @param aComment the Javadoc comment
     * @param aScope the scope of the method.
     */
    private void checkComment(DetailAST aAST, TextBlock aComment, Scope aScope)
    {
        final List<JavadocTag> tags = getMethodTags(aComment);

        if (hasShortCircuitTag(aAST, tags, aScope)) {
            return;
        }

        Iterator<JavadocTag> it = tags.iterator();
        if (aAST.getType() != TokenTypes.ANNOTATION_FIELD_DEF) {
            // Check for inheritDoc
            boolean hasInheritDocTag = false;
            while (it.hasNext() && !hasInheritDocTag) {
                hasInheritDocTag |= (it.next()).isInheritDocTag();
            }

            checkParamTags(tags, aAST, !hasInheritDocTag);
            checkThrowsTags(tags, getThrows(aAST), !hasInheritDocTag);
            if (isFunction(aAST)) {
                checkReturnTag(tags, aAST.getLineNo(), !hasInheritDocTag);
            }
        }

        // Dump out all unused tags
        it = tags.iterator();
        while (it.hasNext()) {
            final JavadocTag jt = it.next();
            if (!jt.isSeeOrInheritDocTag()) {
                log(jt.getLineNo(), "javadoc.unusedTagGeneral");
            }
        }
    }

    /**
     * Validates whether the Javadoc has a short circuit tag. Currently this is
     * the inheritTag. Any errors are logged.
     *
     * @param aAST the construct being checked
     * @param aTags the list of Javadoc tags associated with the construct
     * @param aScope the scope of the construct
     * @return true if the construct has a short circuit tag.
     */
    private boolean hasShortCircuitTag(final DetailAST aAST,
            final List<JavadocTag> aTags, final Scope aScope)
    {
        // Check if it contains {@inheritDoc} tag
        if ((aTags.size() != 1)
                || !(aTags.get(0)).isInheritDocTag())
        {
            return false;
        }

        // Invalid if private, a constructor, or a static method
        if (!JavadocTagInfo.INHERIT_DOC.isValidOn(aAST)) {
            log(aAST, "javadoc.invalidInheritDoc");
        }

        return true;
    }

    /**
     * Returns the scope for the method/constructor at the specified AST. If
     * the method is in an interface or annotation block, the scope is assumed
     * to be public.
     *
     * @param aAST the token of the method/constructor
     * @return the scope of the method/constructor
     */
    private Scope calculateScope(final DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        return ScopeUtils.inInterfaceOrAnnotationBlock(aAST) ? Scope.PUBLIC
                : declaredScope;
    }

    /**
     * Returns the tags in a javadoc comment. Only finds throws, exception,
     * param, return and see tags.
     *
     * @return the tags found
     * @param aComment the Javadoc comment
     */
    private List<JavadocTag> getMethodTags(TextBlock aComment)
    {
        final String[] lines = aComment.getText();
        final List<JavadocTag> tags = Lists.newArrayList();
        int currentLine = aComment.getStartLineNo() - 1;

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
                    col += aComment.getStartColNo();
                }
                tags.add(new JavadocTag(currentLine, col, javadocArgMatcher
                        .group(1), javadocArgMatcher.group(2)));
            }
            else if (javadocNoargMatcher.find()) {
                int col = javadocNoargMatcher.start(1) - 1;
                if (i == 0) {
                    col += aComment.getStartColNo();
                }
                tags.add(new JavadocTag(currentLine, col, javadocNoargMatcher
                        .group(1)));
            }
            else if (noargCurlyMatcher.find()) {
                int col = noargCurlyMatcher.start(1) - 1;
                if (i == 0) {
                    col += aComment.getStartColNo();
                }
                tags.add(new JavadocTag(currentLine, col, noargCurlyMatcher
                        .group(1)));
            }
            else if (argMultilineStart.find()) {
                final String p1 = argMultilineStart.group(1);
                final String p2 = argMultilineStart.group(2);
                int col = argMultilineStart.start(1) - 1;
                if (i == 0) {
                    col += aComment.getStartColNo();
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
                            && !lFin.equals(END_JAVADOC))
                        {
                            tags.add(new JavadocTag(currentLine, col, p1, p2));
                        }
                    }
                    remIndex++;
                }
            }
            else if (noargMultilineStart.find()) {
                final String p1 = noargMultilineStart.group(1);
                int col = noargMultilineStart.start(1) - 1;
                if (i == 0) {
                    col += aComment.getStartColNo();
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
                            && !lFin.equals(END_JAVADOC))
                        {
                            tags.add(new JavadocTag(currentLine, col, p1));
                        }
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
     * @param aAST the method node.
     * @return the list of parameter nodes for aAST.
     */
    private List<DetailAST> getParameters(DetailAST aAST)
    {
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
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
     * @param aAST the method node.
     * @return the list of exception nodes for aAST.
     */
    private List<ExceptionInfo> getThrows(DetailAST aAST)
    {
        final List<ExceptionInfo> retVal = Lists.newArrayList();
        final DetailAST throwsAST = aAST
                .findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = throwsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                        || (child.getType() == TokenTypes.DOT))
                {
                    final FullIdent fi = FullIdent.createFullIdent(child);
                    final ExceptionInfo ei = new ExceptionInfo(new Token(fi),
                            getCurrentClassName());
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
     * @param aTags the tags to check
     * @param aParent the node which takes the parameters
     * @param aReportExpectedTags whether we should report if do not find
     *            expected tag
     */
    private void checkParamTags(final List<JavadocTag> aTags,
            final DetailAST aParent, boolean aReportExpectedTags)
    {
        final List<DetailAST> params = getParameters(aParent);
        final List<DetailAST> typeParams = CheckUtils
                .getTypeParameters(aParent);

        // Loop over the tags, checking to see they exist in the params.
        final ListIterator<JavadocTag> tagIt = aTags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = tagIt.next();

            if (!tag.isParamTag()) {
                continue;
            }

            tagIt.remove();

            boolean found = false;

            // Loop looking for matching param
            final Iterator<DetailAST> paramIt = params.iterator();
            while (paramIt.hasNext()) {
                final DetailAST param = paramIt.next();
                if (param.getText().equals(tag.getArg1())) {
                    found = true;
                    paramIt.remove();
                    break;
                }
            }

            if (tag.getArg1().startsWith("<") && tag.getArg1().endsWith(">")) {
                // Loop looking for matching type param
                final Iterator<DetailAST> typeParamsIt = typeParams.iterator();
                while (typeParamsIt.hasNext()) {
                    final DetailAST typeParam = typeParamsIt.next();
                    if (typeParam.findFirstToken(TokenTypes.IDENT).getText()
                            .equals(
                                    tag.getArg1().substring(1,
                                            tag.getArg1().length() - 1)))
                    {
                        found = true;
                        typeParamsIt.remove();
                        break;
                    }
                }

            }

            // Handle extra JavadocTag
            if (!found) {
                log(tag.getLineNo(), tag.getColumnNo(), "javadoc.unusedTag",
                        "@param", tag.getArg1());
            }
        }

        // Now dump out all type parameters/parameters without tags :- unless
        // the user has chosen to suppress these problems
        if (!mAllowMissingParamTags && aReportExpectedTags) {
            for (DetailAST param : params) {
                log(param, "javadoc.expectedTag",
                    JavadocTagInfo.PARAM.getText(), param.getText());
            }

            for (DetailAST typeParam : typeParams) {
                log(typeParam, "javadoc.expectedTag",
                    JavadocTagInfo.PARAM.getText(),
                    "<" + typeParam.findFirstToken(TokenTypes.IDENT).getText()
                    + ">");
            }
        }
    }

    /**
     * Checks whether a method is a function.
     *
     * @param aAST the method node.
     * @return whether the method is a function.
     */
    private boolean isFunction(DetailAST aAST)
    {
        boolean retVal = false;
        if (aAST.getType() == TokenTypes.METHOD_DEF) {
            final DetailAST typeAST = aAST.findFirstToken(TokenTypes.TYPE);
            if ((typeAST != null)
                && (typeAST.findFirstToken(TokenTypes.LITERAL_VOID) == null))
            {
                retVal = true;
            }
        }
        return retVal;
    }

    /**
     * Checks for only one return tag. All return tags will be removed from the
     * supplied list.
     *
     * @param aTags the tags to check
     * @param aLineNo the line number of the expected tag
     * @param aReportExpectedTags whether we should report if do not find
     *            expected tag
     */
    private void checkReturnTag(List<JavadocTag> aTags, int aLineNo,
        boolean aReportExpectedTags)
    {
        // Loop over tags finding return tags. After the first one, report an
        // error.
        boolean found = false;
        final ListIterator<JavadocTag> it = aTags.listIterator();
        while (it.hasNext()) {
            final JavadocTag jt = it.next();
            if (jt.isReturnTag()) {
                if (found) {
                    log(jt.getLineNo(), jt.getColumnNo(),
                        "javadoc.duplicateTag",
                        JavadocTagInfo.RETURN.getText());
                }
                found = true;
                it.remove();
            }
        }

        // Handle there being no @return tags :- unless
        // the user has chosen to suppress these problems
        if (!found && !mAllowMissingReturnTag && aReportExpectedTags) {
            log(aLineNo, "javadoc.return.expected");
        }
    }

    /**
     * Checks a set of tags for matching throws.
     *
     * @param aTags the tags to check
     * @param aThrows the throws to check
     * @param aReportExpectedTags whether we should report if do not find
     *            expected tag
     */
    private void checkThrowsTags(List<JavadocTag> aTags,
            List<ExceptionInfo> aThrows, boolean aReportExpectedTags)
    {
        // Loop over the tags, checking to see they exist in the throws.
        // The foundThrows used for performance only
        final Set<String> foundThrows = Sets.newHashSet();
        final ListIterator<JavadocTag> tagIt = aTags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = tagIt.next();

            if (!tag.isThrowsTag()) {
                continue;
            }

            tagIt.remove();

            // Loop looking for matching throw
            final String documentedEx = tag.getArg1();
            final Token token = new Token(tag.getArg1(), tag.getLineNo(), tag
                    .getColumnNo());
            final ClassInfo documentedCI = createClassInfo(token,
                    getCurrentClassName());
            boolean found = foundThrows.contains(documentedEx);

            // First look for matches on the exception name
            ListIterator<ExceptionInfo> throwIt = aThrows.listIterator();
            while (!found && throwIt.hasNext()) {
                final ExceptionInfo ei = throwIt.next();

                if (ei.getName().getText().equals(
                        documentedCI.getName().getText()))
                {
                    found = true;
                    ei.setFound();
                    foundThrows.add(documentedEx);
                }
            }

            // Now match on the exception type
            throwIt = aThrows.listIterator();
            while (!found && throwIt.hasNext()) {
                final ExceptionInfo ei = throwIt.next();

                if (documentedCI.getClazz() == ei.getClazz()) {
                    found = true;
                    ei.setFound();
                    foundThrows.add(documentedEx);
                }
                else if (mAllowThrowsTagsForSubclasses) {
                    found = isSubclass(documentedCI.getClazz(), ei.getClazz());
                }
            }

            // Handle extra JavadocTag.
            if (!found) {
                boolean reqd = true;
                if (mAllowUndeclaredRTE) {
                    reqd = !isUnchecked(documentedCI.getClazz());
                }

                if (reqd) {
                    log(tag.getLineNo(), tag.getColumnNo(),
                        "javadoc.unusedTag",
                        JavadocTagInfo.THROWS.getText(), tag.getArg1());

                }
            }
        }

        // Now dump out all throws without tags :- unless
        // the user has chosen to suppress these problems
        if (!mAllowMissingThrowsTags && aReportExpectedTags) {
            for (ExceptionInfo ei : aThrows) {
                if (!ei.isFound()) {
                    final Token fi = ei.getName();
                    log(fi.getLineNo(), fi.getColumnNo(),
                        "javadoc.expectedTag",
                        JavadocTagInfo.THROWS.getText(), fi.getText());
                }
            }
        }
    }

    /**
     * Returns whether an AST represents a setter method.
     * @param aAST the AST to check with
     * @return whether the AST represents a setter method
     */
    private boolean isSetterMethod(final DetailAST aAST)
    {
        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper setter method which does not throw any
        // exceptions.
        if ((aAST.getType() != TokenTypes.METHOD_DEF)
                || (aAST.getChildCount() != MAX_CHILDREN))
        {
            return false;
        }

        // Should I handle only being in a class????

        // Check the name matches format setX...
        final DetailAST type = aAST.findFirstToken(TokenTypes.TYPE);
        final String name = type.getNextSibling().getText();
        if (!name.matches("^set[A-Z].*")) { // Depends on JDK 1.4
            return false;
        }

        // Check the return type is void
        if (type.getChildCount(TokenTypes.LITERAL_VOID) == 0) {
            return false;
        }

        // Check that is had only one parameter
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        if ((params == null)
                || (params.getChildCount(TokenTypes.PARAMETER_DEF) != 1))
        {
            return false;
        }

        // Now verify that the body consists of:
        // SLIST -> EXPR -> ASSIGN
        // SEMI
        // RCURLY
        final DetailAST slist = aAST.findFirstToken(TokenTypes.SLIST);
        if ((slist == null) || (slist.getChildCount() != BODY_SIZE)) {
            return false;
        }

        final AST expr = slist.getFirstChild();
        if ((expr.getType() != TokenTypes.EXPR)
                || (expr.getFirstChild().getType() != TokenTypes.ASSIGN))
        {
            return false;
        }

        return true;
    }

    /**
     * Returns whether an AST represents a getter method.
     * @param aAST the AST to check with
     * @return whether the AST represents a getter method
     */
    private boolean isGetterMethod(final DetailAST aAST)
    {
        // Check have a method with exactly 7 children which are all that
        // is allowed in a proper getter method which does not throw any
        // exceptions.
        if ((aAST.getType() != TokenTypes.METHOD_DEF)
                || (aAST.getChildCount() != MAX_CHILDREN))
        {
            return false;
        }

        // Check the name matches format of getX or isX. Technically I should
        // check that the format isX is only used with a boolean type.
        final DetailAST type = aAST.findFirstToken(TokenTypes.TYPE);
        final String name = type.getNextSibling().getText();
        if (!name.matches("^(is|get)[A-Z].*")) { // Depends on JDK 1.4
            return false;
        }

        // Check the return type is void
        if (type.getChildCount(TokenTypes.LITERAL_VOID) > 0) {
            return false;
        }

        // Check that is had only one parameter
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        if ((params == null)
                || (params.getChildCount(TokenTypes.PARAMETER_DEF) > 0))
        {
            return false;
        }

        // Now verify that the body consists of:
        // SLIST -> RETURN
        // RCURLY
        final DetailAST slist = aAST.findFirstToken(TokenTypes.SLIST);
        if ((slist == null) || (slist.getChildCount() != 2)) {
            return false;
        }

        final AST expr = slist.getFirstChild();
        if ((expr.getType() != TokenTypes.LITERAL_RETURN)
                || (expr.getFirstChild().getType() != TokenTypes.EXPR))
        {
            return false;
        }

        return true;
    }

    /**
     * Returns is a method has the "@Override" annotation.
     * @param aAST the AST to check with
     * @return whether the AST represents a method that has the annotation.
     */
    private boolean isOverrideMethod(DetailAST aAST)
    {
        // Need it to be a method, cannot have an override on anything else.
        // Must also have MODIFIERS token to hold the @Override
        if ((TokenTypes.METHOD_DEF != aAST.getType())
            || (TokenTypes.MODIFIERS != aAST.getFirstChild().getType()))
        {
            return false;
        }

        // Now loop over all nodes while they are annotations looking for
        // an "@Override".
        DetailAST node = aAST.getFirstChild().getFirstChild();
        while ((null != node) && (TokenTypes.ANNOTATION == node.getType())) {
            if ((node.getFirstChild().getType() == TokenTypes.AT)
                && (node.getFirstChild().getNextSibling().getType()
                    == TokenTypes.IDENT)
                && ("Override".equals(
                        node.getFirstChild().getNextSibling().getText())))
            {
                return true;
            }
            node = node.getNextSibling();
        }
        return false;
    }

    /** Stores useful information about declared exception. */
    private class ExceptionInfo
    {
        /** does the exception have throws tag associated with. */
        private boolean mFound;
        /** class information associated with this exception. */
        private final ClassInfo mClassInfo;

        /**
         * Creates new instance for <code>FullIdent</code>.
         *
         * @param aIdent the exception
         * @param aCurrentClass name of current class.
         */
        ExceptionInfo(Token aIdent, String aCurrentClass)
        {
            mClassInfo = createClassInfo(aIdent, aCurrentClass);
        }

        /** Mark that the exception has associated throws tag */
        final void setFound()
        {
            mFound = true;
        }

        /** @return whether the exception has throws tag associated with */
        final boolean isFound()
        {
            return mFound;
        }

        /** @return exception's name */
        final Token getName()
        {
            return mClassInfo.getName();
        }

        /** @return class for this exception */
        final Class<?> getClazz()
        {
            return mClassInfo.getClazz();
        }
    }
}
