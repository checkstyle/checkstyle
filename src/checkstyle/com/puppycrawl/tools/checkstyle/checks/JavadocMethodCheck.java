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

import com.puppycrawl.tools.checkstyle.Scope;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
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
 * Checks the Javadoc of a method or constructor.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author Rick Giles
 * @version 1.0
 */
public class JavadocMethodCheck
    extends ImportCheck
{

       // {{{ Data declarations
    /** the pattern to match Javadoc tags that take an argument **/
    private static final String MATCH_JAVADOC_ARG_PAT
        = "@(throws|exception|param)\\s+(\\S+)\\s+\\S";
    /** compiled regexp to match Javadoc tags that take an argument **/
    private static final RE MATCH_JAVADOC_ARG =
        Utils.createRE(MATCH_JAVADOC_ARG_PAT);

   /**
    * the pattern to match a single line comment containing only the comment
    * itself -- no code.
    **/
    private static final String MATCH_SINGLELINE_COMMENT_PAT
      = "^\\s*//.*$";
   /** compiled regexp to match a single-line comment line **/
    private static final RE MATCH_SINGLELINE_COMMENT =
      Utils.createRE(MATCH_SINGLELINE_COMMENT_PAT);

   /**
    * the pattern to match the first line of a multi-line Javadoc
    * tag that takes an argument. Javadoc with no arguments isn't
    * allowed to go over multiple lines.
    **/
    private static final String MATCH_JAVADOC_MULTILINE_START_PAT
        = "@(throws|exception|param)\\s+(\\S+)\\s*$";
    /** compiled regexp to match first part of multilineJavadoc tags **/
    private static final RE MATCH_JAVADOC_MULTILINE_START =
       Utils.createRE(MATCH_JAVADOC_MULTILINE_START_PAT);

    /** the pattern that looks for a continuation of the comment **/
    private static final String MATCH_JAVADOC_MULTILINE_CONT_PAT
        = "(\\*/|@|[^\\s\\*])";
    /** compiled regexp to look for a continuation of the comment **/
    private static final RE MATCH_JAVADOC_MULTILINE_CONT =
       Utils.createRE(MATCH_JAVADOC_MULTILINE_CONT_PAT);
    /** Multiline finished at end of comment **/
    private static final String END_JAVADOC = "*/";
    /** Multiline finished at next Javadoc **/
    private static final String NEXT_TAG = "@";

    /** the pattern to match Javadoc tags with no argument **/
    private static final String MATCH_JAVADOC_NOARG_PAT
        = "@(return|see|author)\\s+\\S";
    /** compiled regexp to match Javadoc tags with no argument **/
    private static final RE MATCH_JAVADOC_NOARG
        = Utils.createRE(MATCH_JAVADOC_NOARG_PAT);

    /** full identifier for package of the method **/
    private FullIdent mPackageFullIdent = null;

    /** imports details **/
    private Set mImports = new HashSet();

    /** the visibility scope where Javadoc comments are checked **/
    private Scope mScope = Scope.PRIVATE;

    /** check for unused throws **/
    private boolean mCheckUnusedThrows = false;

    /**
     * Set the scope.
     * @param aFrom a <code>String</code> value
     */
    public void setScope(String aFrom)
    {
        mScope = Scope.getInstance(aFrom);
    }

    /**
     * Set whether to check for unused throws.
     * @param aFlag a <code>Boolean</code> value
     */
    public void setCheckUnusedThrows(boolean aFlag)
    {
        mCheckUnusedThrows = aFlag;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void beginTree()
    {
        mPackageFullIdent = new FullIdent();
        mImports.clear();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.PACKAGE_DEF) {
            if (mCheckUnusedThrows) {
                processPackage(aAST);
            }
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            if (mCheckUnusedThrows) {
                processImport(aAST);
            }
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
            mImports.add(name);
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
            || !((JavadocTag) tags.get(0)).isSeeTag())
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
                if (!jt.isSeeTag()) {
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
            else if (MATCH_JAVADOC_MULTILINE_START.match(aLines[i])) {
                final String p1 = MATCH_JAVADOC_MULTILINE_START.getParen(1);
                final String p2 = MATCH_JAVADOC_MULTILINE_START.getParen(2);

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
                final DetailAST ident
                    = child.findFirstToken(TokenTypes.IDENT);
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
        final DetailAST throwsAST
            = aAST.findFirstToken(TokenTypes.LITERAL_THROWS);
        if (throwsAST != null) {
            DetailAST child = (DetailAST) throwsAST.getFirstChild();
            while (child != null) {
                if ((child.getType() == TokenTypes.IDENT)
                    || (child.getType() == TokenTypes.DOT))
                {
                    final FullIdent fi = FullIdent.createFullIdent(child);
                    retVal.add(fi);
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

        // Now dump out all parameters without tags
        final Iterator paramIt = aParams.iterator();
        while (paramIt.hasNext()) {
            final DetailAST param = (DetailAST) paramIt.next();
            log(param.getLineNo(), param.getColumnNo(),
                "javadoc.expectedTag", "@param", param.getText());
        }
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

        // Handle there being no @return tags
        if (!found) {
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
        final Set foundThrows = new HashSet();
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
                final FullIdent fi = (FullIdent) throwIt.next();
                if (fi.getText().equals(documentedEx)) {
                    found = true;
                    throwIt.remove();
                    foundThrows.add(documentedEx);
                }
            }

            // Handle extra JavadocTag.
            if (!found) {
                boolean reqd = true;
                if (mCheckUnusedThrows) {
                    final ClassResolver cr = new ClassResolver(
                        Thread.currentThread().getContextClassLoader(),
                        mPackageFullIdent.getText(), mImports);
                    try {
                        final Class clazz = cr.resolve(tag.getArg1());
                        reqd = !RuntimeException.class.isAssignableFrom(clazz)
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

        // Now dump out all throws without tags
        final ListIterator throwIt = aThrows.listIterator();
        while (throwIt.hasNext()) {
            final FullIdent fi = (FullIdent) throwIt.next();
            log(fi.getLineNo(), fi.getColumnNo(),
                "javadoc.expectedTag", "@throws", fi.getText());
        }
    }
}
