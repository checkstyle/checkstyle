////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.apache.regexp.RE;

/**
 * <p>
 * Checks the Javadoc of a method or constructor.
 * By default, does not check for unused throws.
 * To allow documented <code>java.lang.RuntimeException</code>s
 * that are not declared, set property allowUndeclaredRTE to true.
 * The scope to verify is specified using the {@link Scope} class and
 * defaults to {@link Scope#PRIVATE}. To verify another scope,
 * set property scope to one of the {@link Scope} constants.
 * </p>
 * <p>
 * Error messages about parameters for which no param tags are
 * present can be suppressed by defining property
 * <code>allowMissingParamTags</code>.
 * Error messages about exceptions which are declared to be thrown,
 * but for which no throws tag is present can be suppressed by
 * defining property <code>allowMissingThrowsTags</code>.
 * Error messages about methods which return non-void but for
 * which no return tag is present can be suppressed by defining
 * property <code>allowMissingReturnTag</code>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="JavadocMethod"/&gt;
 * </pre>
 * <p> An example of how to configure the check to check to allow
 * documentation of undeclared RuntimeExceptions
 * and for the {@link Scope#PUBLIC} scope, while ignoring any missing
 * param tags is:
 *</p>
 * <pre>
 * &lt;module name="JavadocMethod"&gt;
 *    &lt;property name="scope" value="public"/&gt;
 *    &lt;property name="allowUndeclaredRTE" value="true"/&gt;
 *    &lt;property name="allowMissingParamTags" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author Rick Giles
 * @author o_sukhodoslky
 * @version 1.1
 */
public class JavadocMethodCheck
    extends AbstractImportCheck
{

       // {{{ Data declarations
    /** the pattern to match Javadoc tags that take an argument **/
    private static final String MATCH_JAVADOC_ARG_PAT =
        "@(throws|exception|param)\\s+(\\S+)\\s+\\S";
    /** compiled regexp to match Javadoc tags that take an argument **/
    private static final RE MATCH_JAVADOC_ARG =
        Utils.createRE(MATCH_JAVADOC_ARG_PAT);

   /**
    * the pattern to match the first line of a multi-line Javadoc
    * tag that takes an argument.
    **/
    private static final String MATCH_JAVADOC_ARG_MULTILINE_START_PAT =
        "@(throws|exception|param)\\s+(\\S+)\\s*$";
    /** compiled regexp to match first part of multilineJavadoc tags **/
    private static final RE MATCH_JAVADOC_ARG_MULTILINE_START =
        Utils.createRE(MATCH_JAVADOC_ARG_MULTILINE_START_PAT);

    /** the pattern that looks for a continuation of the comment **/
    private static final String MATCH_JAVADOC_MULTILINE_CONT_PAT =
        "(\\*/|@|[^\\s\\*])";
    /** compiled regexp to look for a continuation of the comment **/
    private static final RE MATCH_JAVADOC_MULTILINE_CONT =
        Utils.createRE(MATCH_JAVADOC_MULTILINE_CONT_PAT);
    /** Multiline finished at end of comment **/
    private static final String END_JAVADOC = "*/";
    /** Multiline finished at next Javadoc **/
    private static final String NEXT_TAG = "@";

    /** the pattern to match Javadoc tags with no argument **/
    private static final String MATCH_JAVADOC_NOARG_PAT =
        "@(return|see)\\s+\\S";
    /** compiled regexp to match Javadoc tags with no argument **/
    private static final RE MATCH_JAVADOC_NOARG =
        Utils.createRE(MATCH_JAVADOC_NOARG_PAT);
   /**
    * the pattern to match the first line of a multi-line Javadoc
    * tag that takes no argument.
    **/
    private static final String MATCH_JAVADOC_NOARG_MULTILINE_START_PAT =
        "@(return|see)\\s*$";
    /** compiled regexp to match first part of multilineJavadoc tags **/
    private static final RE MATCH_JAVADOC_NOARG_MULTILINE_START =
        Utils.createRE(MATCH_JAVADOC_NOARG_MULTILINE_START_PAT);

    /** the pattern to match Javadoc tags with no argument and {} **/
    private static final String MATCH_JAVADOC_NOARG_CURLY_PAT =
        "\\{\\s*@(inheritDoc)\\s*\\}";
    /** compiled regexp to match Javadoc tags with no argument and {} **/
    private static final RE MATCH_JAVADOC_NOARG_CURLY =
        Utils.createRE(MATCH_JAVADOC_NOARG_CURLY_PAT);

    /** full identifier for package of the method **/
    private FullIdent mPackageFullIdent = null;

    /** imports details **/
    private Set mImports = new HashSet();

    /** the visibility scope where Javadoc comments are checked **/
    private Scope mScope = Scope.PRIVATE;

    /**
     * controls whether to allow documented exceptions that
     * are not declared if they are a subclass of
     * java.lang.RuntimeException.
     **/
    private boolean mAllowUndeclaredRTE = false;

    /**
     * controls whether to allow documented exceptions that
     * are subclass of one of declared exception.
     * Defaults to false (backward compatibility).
     **/
    private boolean mAllowThrowsTagsForSubclasses = false;

    /**
     * controls whether to ignore errors when a method has parameters
     * but does not have matching param tags in the javadoc.
     * Defaults to false.
     **/
    private boolean mAllowMissingParamTags = false;

    /**
     * controls whether to ignore errors when a method declares that
     * it throws exceptions but does not have matching throws tags
     * in the javadoc. Defaults to false.
     **/
    private boolean mAllowMissingThrowsTags = false;

    /**
     * controls whether to ignore errors when a method returns
     * non-void type but does not have a return tag in the javadoc.
     * Defaults to false.
     **/
    private boolean mAllowMissingReturnTag = false;

    /**
     * Set the scope.
     * @param aFrom a <code>String</code> value
     */
    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /**
     * controls whether to allow documented exceptions that
     * are not declared if they are a subclass of
     * java.lang.RuntimeException.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowUndeclaredRTE(boolean aFlag)
    {
        mAllowUndeclaredRTE = aFlag;
    }

    /**
     * controls whether to allow documented exception that
     * are subclass of one of declared exceptions.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowThrowsTagsForSubclasses(boolean aFlag)
    {
        mAllowThrowsTagsForSubclasses = aFlag;
    }

    /**
     * controls whether to allow a method which has parameters
     * to omit matching param tags in the javadoc.
     * Defaults to false.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingParamTags(boolean aFlag)
    {
        mAllowMissingParamTags = aFlag;
    }

    /**
     * controls whether to allow a method which declares that
     * it throws exceptions to omit matching throws tags
     * in the javadoc. Defaults to false.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingThrowsTags(boolean aFlag)
    {
        mAllowMissingThrowsTags = aFlag;
    }

    /**
     * controls whether to allow a method which returns
     * non-void type to omit the return tag in the javadoc.
     * Defaults to false.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setAllowMissingReturnTag(boolean aFlag)
    {
        mAllowMissingReturnTag = aFlag;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree(DetailAST aRootAST)
    {
        mPackageFullIdent = FullIdent.createFullIdent(null);
        mImports.clear();
        mClassResolver = null;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            if (mAllowUndeclaredRTE) {
                processPackage(aAST);
            }
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            processImport(aAST);
        }
        else {
            //TokenTypes.METHOD_DEF or TokenTypes.CTOR_DEF
            processMethod(aAST);
        }
    }



    /**
     * Collects the details of a package.
     * @param aAST node containing the package details
     */
    private void processPackage(DetailAST aAST)
    {
        final DetailAST nameAST = (DetailAST) aAST.getFirstChild();
        mPackageFullIdent = FullIdent.createFullIdent(nameAST);
    }

    /**
     * Collects the details of imports.
     * @param aAST node containing the import details
     */
    private void processImport(DetailAST aAST)
    {
        final FullIdent name = getImportText(aAST);
        if (name != null) {
            mImports.add(name.getText());
        }
    }

    /**
     * Checks Javadoc comments for a method or constructor.
     * @param aAST the tree node for the method or constructor.
     */
    private void processMethod(DetailAST aAST)
    {
        final DetailAST mods = aAST.findFirstToken(TokenTypes.MODIFIERS);
        final Scope declaredScope = ScopeUtils.getScopeFromMods(mods);
        final Scope targetScope =
            ScopeUtils.inInterfaceBlock(aAST)
                ? Scope.PUBLIC
                : declaredScope;

        if (targetScope.isIn(mScope)) {
            final Scope surroundingScope =
                ScopeUtils.getSurroundingScope(aAST);

            if (surroundingScope.isIn(mScope)) {
                final FileContents contents = getFileContents();
                final String[] cmt =
                    contents.getJavadocBefore(aAST.getLineNo());

                if (cmt == null) {
                    log(aAST.getLineNo(),
                        aAST.getColumnNo(),
                        "javadoc.missing");
                }
                else {
                    checkComment(aAST, cmt);
                }
            }
        }
    }

    /**
     * Checks the Javadoc for a method.
     * @param aAST the token for the method
     * @param aComment the Javadoc comment
     */
    private void checkComment(DetailAST aAST, String[] aComment)
    {
        final List tags = getMethodTags(aComment, aAST.getLineNo() - 1);
        // Check for only one @see tag
        if ((tags.size() != 1)
            || !((JavadocTag) tags.get(0)).isSeeOrInheritDocTag())
        {
            checkParamTags(tags, getParameters(aAST));
            checkThrowsTags(tags, getThrows(aAST));
            if (isFunction(aAST)) {
                checkReturnTag(tags, aAST.getLineNo());
            }

            // Dump out all unused tags
            final Iterator it = tags.iterator();
            while (it.hasNext()) {
                final JavadocTag jt = (JavadocTag) it.next();
                if (!jt.isSeeOrInheritDocTag()) {
                    log(jt.getLineNo(), "javadoc.unusedTagGeneral");
                }
            }
        }
    }
    /**
     * Returns the tags in a javadoc comment. Only finds throws, exception,
     * param, return and see tags.
     * @return the tags found
     * @param aLines the Javadoc comment
     * @param aLastLineNo the line number of the last line in the Javadoc
     *                    comment
     **/
    private List getMethodTags(String[] aLines, int aLastLineNo)
    {
        final List tags = new ArrayList();
        int currentLine = aLastLineNo - aLines.length;
        for (int i = 0; i < aLines.length; i++) {
            currentLine++;
            if (MATCH_JAVADOC_ARG.match(aLines[i])) {
                tags.add(new JavadocTag(currentLine,
                                        MATCH_JAVADOC_ARG.getParen(1),
                                        MATCH_JAVADOC_ARG.getParen(2)));
            }
            else if (MATCH_JAVADOC_NOARG.match(aLines[i])) {
                tags.add(new JavadocTag(currentLine,
                                        MATCH_JAVADOC_NOARG.getParen(1)));
            }
            else if (MATCH_JAVADOC_NOARG_CURLY.match(aLines[i])) {
                tags.add(new JavadocTag(currentLine,
                                        MATCH_JAVADOC_NOARG_CURLY.getParen(1)));
            }
            else if (MATCH_JAVADOC_ARG_MULTILINE_START.match(aLines[i])) {
                final String p1 = MATCH_JAVADOC_ARG_MULTILINE_START.getParen(1);
                final String p2 = MATCH_JAVADOC_ARG_MULTILINE_START.getParen(2);

                // Look for the rest of the comment if all we saw was
                // the tag and the name. Stop when we see '*/' (end of
                // Javadoc, '@' (start of next tag), or anything that's
                // not whitespace or '*' characters.
                int remIndex = i + 1;
                while (remIndex < aLines.length) {
                    if (MATCH_JAVADOC_MULTILINE_CONT.match(aLines[remIndex])) {
                        remIndex = aLines.length;
                        String lFin = MATCH_JAVADOC_MULTILINE_CONT.getParen(1);
                        if (!lFin.equals(NEXT_TAG)
                            && !lFin.equals(END_JAVADOC))
                        {
                            tags.add(new JavadocTag(currentLine, p1, p2));
                        }
                    }
                    remIndex++;
                }
            }
            else if (MATCH_JAVADOC_NOARG_MULTILINE_START.match(aLines[i])) {
                final String p1 =
                    MATCH_JAVADOC_NOARG_MULTILINE_START.getParen(1);

                // Look for the rest of the comment if all we saw was
                // the tag and the name. Stop when we see '*/' (end of
                // Javadoc, '@' (start of next tag), or anything that's
                // not whitespace or '*' characters.
                int remIndex = i + 1;
                while (remIndex < aLines.length) {
                    if (MATCH_JAVADOC_MULTILINE_CONT.match(aLines[remIndex])) {
                        remIndex = aLines.length;
                        String lFin = MATCH_JAVADOC_MULTILINE_CONT.getParen(1);
                        if (!lFin.equals(NEXT_TAG)
                            && !lFin.equals(END_JAVADOC))
                        {
                            tags.add(new JavadocTag(currentLine, p1));
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
     * @param aAST the method node.
     * @return the list of parameter nodes for aAST.
     **/
    private List getParameters(DetailAST aAST)
    {
        final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
        final List retVal = new ArrayList();

        DetailAST child = (DetailAST) params.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                retVal.add(ident);
            }
            child = (DetailAST) child.getNextSibling();
        }
        return retVal;
    }

     /**
     * Computes the exception nodes for a method.
     * @param aAST the method node.
     * @return the list of exception nodes for aAST.
     **/
    private List getThrows(DetailAST aAST)
    {
        final List retVal = new ArrayList();
        final DetailAST throwsAST =
            aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = (DetailAST) throwsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                    || (child.getType() == TokenTypes.DOT))
                {
                    final ExceptionInfo ei =
                        new ExceptionInfo(FullIdent.createFullIdent(child));
                    retVal.add(ei);
                }
                child = (DetailAST) child.getNextSibling();
            }
        }
        return retVal;
    }


    /**
     * Checks a set of tags for matching parameters.
     * @param aTags the tags to check
     * @param aParams the list of parameters to check
     **/
    private void checkParamTags(List aTags, List aParams)
    {
        // Loop over the tags, checking to see they exist in the params.
        final ListIterator tagIt = aTags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = (JavadocTag) tagIt.next();

            if (!tag.isParamTag()) {
                continue;
            }

            tagIt.remove();

            // Loop looking for matching param
            boolean found = false;
            final Iterator paramIt = aParams.iterator();
            while (paramIt.hasNext()) {
                final DetailAST param = (DetailAST) paramIt.next();
                if (param.getText().equals(tag.getArg1())) {
                    found = true;
                    paramIt.remove();
                    break;
                }
            }

            // Handle extra JavadocTag
            if (!found) {
                log(tag.getLineNo(), "javadoc.unusedTag",
                              "@param", tag.getArg1());
            }
        }

        // Now dump out all parameters without tags :- unless
        // the user has chosen to suppress these problems
        if (!mAllowMissingParamTags) {
            final Iterator paramIt = aParams.iterator();
            while (paramIt.hasNext()) {
                final DetailAST param = (DetailAST) paramIt.next();
                log(param.getLineNo(), param.getColumnNo(),
                    "javadoc.expectedTag", "@param", param.getText());
            }
        }
    }

    /**
     * Return if two Strings represent the same type, inspecting the
     * import statements if necessary
     *
     * @param aDeclared type declared in throws clause
     * @param aDocumented type declared in javadoc throws tag
     * @return true iff type names represent the same type
     */
    private boolean isSameType(String aDeclared, String aDocumented)
    {
        return aDeclared.equals(aDocumented)
                || isShortName(aDeclared, aDocumented)
                || isShortName(aDocumented, aDeclared);
    }

    /**
     * Calculate if one type name is a shortname for another type name.
     * @param aShortName a shorthand, such as <code>IOException</code>
     * @param aFullName a full name, such as <code>java.io.IOException</code>
     * @return true iff aShortName represents the same type as aFullName
     */
    private boolean isShortName(String aShortName, String aFullName)
    {
        if (aShortName.length() >= aFullName.length()) {
            return false;
        }

        final String base = Utils.baseClassname(aFullName);
        if (aShortName.length() >= aFullName.length()
                || !base.equals(aShortName))
        {
            return false;
        }

        // check fully qualified import
        if (mImports.contains(aFullName)) {
            return true;
        }

        // check .* import
        final int endIndex = aFullName.length() - base.length() - 1;
        final String packageName = aFullName.substring(0, endIndex);
        final String starImport = packageName + ".*";
        if (mImports.contains(starImport)) {
            return true;
        }

        // check fully qualified class from same package
        return packageName.equals(mPackageFullIdent.getText());
    }

    /**
     * Checks whether a method is a function.
     * @param aAST the method node.
     * @return whether the method is a function.
     **/
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
     * @param aTags the tags to check
     * @param aLineNo the line number of the expected tag
     **/
    private void checkReturnTag(List aTags, int aLineNo)
    {
        // Loop over tags finding return tags. After the first one, report an
        // error.
        boolean found = false;
        final ListIterator it = aTags.listIterator();
        while (it.hasNext()) {
            final JavadocTag jt = (JavadocTag) it.next();
            if (jt.isReturnTag()) {
                if (found) {
                    log(jt.getLineNo(), "javadoc.return.duplicate");
                }
                found = true;
                it.remove();
            }
        }

        // Handle there being no @return tags :- unless
        // the user has chosen to suppress these problems
        if (!found && !mAllowMissingReturnTag) {
            log(aLineNo, "javadoc.return.expected");
        }
    }


    /**
     * Checks a set of tags for matching throws.
     * @param aTags the tags to check
     * @param aThrows the throws to check
     **/
    private void checkThrowsTags(List aTags, List aThrows)
    {
        // Loop over the tags, checking to see they exist in the throws.
        final Set foundThrows = new HashSet(); //used for performance only
        final ListIterator tagIt = aTags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = (JavadocTag) tagIt.next();

            if (!tag.isThrowsTag()) {
                continue;
            }

            tagIt.remove();

            // Loop looking for matching throw
            final String documentedEx = tag.getArg1();
            boolean found = foundThrows.contains(documentedEx);
            final ListIterator throwIt = aThrows.listIterator();
            while (!found && throwIt.hasNext()) {
                final ExceptionInfo ei = (ExceptionInfo) throwIt.next();
                final FullIdent fi = ei.getFullIdent();
                final String declaredEx = fi.getText();
                if (isSameType(declaredEx, documentedEx)) {
                    found = true;
                    ei.setFound();
                    foundThrows.add(documentedEx);
                }
                else if (mAllowThrowsTagsForSubclasses) {
                    final ClassResolver cr = getClassResolver();
                    try {
                        final Class documentedClass = cr.resolve(documentedEx);
                        try {
                            final Class declaredClass = cr.resolve(declaredEx);
                            found =
                                declaredClass.isAssignableFrom(documentedClass);
                            if (found) {
                                ei.setFound();
                            }
                        }
                        catch (ClassNotFoundException e) {
                            log(tag.getLineNo(), "javadoc.classInfo",
                                "@throws", declaredEx);
                        }
                    }
                    catch (ClassNotFoundException e) {
                        log(tag.getLineNo(), "javadoc.classInfo",
                                      "@throws", documentedEx);
                    }
                }
            }

            // Handle extra JavadocTag.
            if (!found) {
                boolean reqd = true;
                if (mAllowUndeclaredRTE) {
                    final ClassResolver cr = getClassResolver();
                    try {
                        final Class clazz = cr.resolve(tag.getArg1());
                        reqd =
                            !RuntimeException.class.isAssignableFrom(clazz)
                                && !Error.class.isAssignableFrom(clazz);
                    }
                    catch (ClassNotFoundException e) {
                        log(tag.getLineNo(), "javadoc.classInfo",
                                      "@throws", tag.getArg1());
                    }
                }

                if (reqd) {
                    log(tag.getLineNo(), "javadoc.unusedTag",
                                  "@throws", tag.getArg1());
                }
            }
        }

        // Now dump out all throws without tags :- unless
        // the user has chosen to suppress these problems
        if (!mAllowMissingThrowsTags) {
            final ListIterator throwIt = aThrows.listIterator();
            while (throwIt.hasNext()) {
                final ExceptionInfo ei = (ExceptionInfo) throwIt.next();
                if (!ei.isFound()) {
                    final FullIdent fi = ei.getFullIdent();
                    log(fi.getLineNo(), fi.getColumnNo(),
                        "javadoc.expectedTag", "@throws", fi.getText());
                }
            }
        }
    }

    /** @return <code>ClassResolver</code> for current tree. */
    final ClassResolver getClassResolver()
    {
        if (mClassResolver == null) {
            mClassResolver = new ClassResolver(getClassLoader(),
                                               mPackageFullIdent.getText(),
                                               mImports);

        }
        return mClassResolver;
    }

    /** <code>ClassResolver</code> instance for current tree. */
    private ClassResolver mClassResolver;
}

/**
 * Stores useful information about declared exception.
 * @author o_sukhodoslky
 */
final class ExceptionInfo
{
    /** <code>FullIdent</code> of the exception. */
    private final FullIdent mIdent;

    /** does the exception have throws tag associated with. */
    private boolean mFound;

    /**
     * Creates new instance for <code>FullIdent</code>.
     * @param aIdent <code>FullIdent</code> of the exception
     */
    ExceptionInfo(FullIdent aIdent)
    {
        mIdent = aIdent;
    }

    /** @return <code>FullIdent</code> of the exception. */
    final FullIdent getFullIdent()
    {
        return mIdent;
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
}
