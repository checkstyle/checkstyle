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
package com.puppycrawl.tools.checkstyle;

import antlr.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

/**
 * Verifier of Java rules. Each rule verifier takes the form of
 * <code>void verifyXXX(args)</code>. The implementation must not throw any
 * exceptions.
 * <P>
 * Line numbers start from 1, column numbers start for 0.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class Verifier
{
    // {{{ Data declarations
    /** the pattern to match Javadoc tags that take an argument **/
    private static final String MATCH_JAVADOC_ARG_PAT
        = "@(throws|exception|param)\\s+(\\S+)\\s+\\S";
    /** compiled regexp to match Javadoc tags that take an argument **/
    private static final RE MATCH_JAVADOC_ARG = createRE(MATCH_JAVADOC_ARG_PAT);

   /**
    * the pattern to match the first line of a multi-line Javadoc
    * tag that takes an argument. Javadoc with no arguments isn't
    * allowed to go over multiple lines.
    **/
    private static final String MATCH_JAVADOC_MULTILINE_START_PAT
        = "@(throws|exception|param)\\s+(\\S+)\\s*$";
    /** compiled regexp to match first part of multilineJavadoc tags **/
    private static final RE MATCH_JAVADOC_MULTILINE_START =
       createRE(MATCH_JAVADOC_MULTILINE_START_PAT);

    /** the pattern that looks for a continuation of the comment **/
    private static final String MATCH_JAVADOC_MULTILINE_CONT_PAT
        = "(\\*/|@|[^\\s\\*])";
    /** compiled regexp to look for a continuation of the comment **/
    private static final RE MATCH_JAVADOC_MULTILINE_CONT =
       createRE(MATCH_JAVADOC_MULTILINE_CONT_PAT);
    /** Multiline finished at end of comment **/
    private static final String END_JAVADOC = "*/";
    /** Multiline finished at next Javadoc **/
    private static final String NEXT_TAG = "@";

    /** the pattern to match Javadoc tags with no argument **/
    private static final String MATCH_JAVADOC_NOARG_PAT
        = "@(return|see|author)\\s+\\S";
    /** compiled regexp to match Javadoc tags with no argument **/
    private static final RE MATCH_JAVADOC_NOARG
        = createRE(MATCH_JAVADOC_NOARG_PAT);

    /** the pattern to match author tag **/
    private static final String MATCH_JAVADOC_AUTHOR_PAT = "@author\\s+\\S";
    /** compiled regexp to match author tag **/
    private static final RE MATCH_JAVADOC_AUTHOR
        = createRE(MATCH_JAVADOC_AUTHOR_PAT);


    ////////////////////////////////////////////////////////////////////////////
    // Member variables
    ////////////////////////////////////////////////////////////////////////////

    /** stack tracking the type of block currently in **/
    private final Stack mInInterface = new Stack();

    /** stack tracking the visibility scope currently in **/
    private final Stack mInScope = new Stack();

    /** tracks the level of block definitions for methods **/
    private int mMethodBlockLevel = 0;

    /** the messages being logged **/
    private final List mMessages = new ArrayList();

    /** the lines of the file being checked **/
    private String[] mLines;

    /** name of the package the file is in **/
    private String mPkgName;

    /** map of the Javadoc comments indexed on the last line of the comment.
     * The hack is it assumes that there is only one Javadoc comment per line.
     **/
    private final Map mComments = new HashMap();

    /** the set of imports (no line number) **/
    private final Set mImports = new HashSet();

    /** the identifiers used **/
    private final Set mReferenced = new HashSet();

    /** configuration for checking **/
    private final Configuration mConfig;

    // }}}

    // {{{ Constructors
    ////////////////////////////////////////////////////////////////////////////
    // Constructor methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs the object.
     * @param aConfig the configuration to use for checking
     **/
    Verifier(Configuration aConfig)
    {
        mConfig = aConfig;
    }

    // }}}

    // {{{ Interface verifier methods
    ////////////////////////////////////////////////////////////////////////////
    // Interface Verifier methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns the ordered list of error messages.
     * @return the list of messages
     **/
    LineText[] getMessages()
    {
        checkImports();
        Collections.sort(mMessages);
        return (LineText[]) mMessages.toArray(new LineText[mMessages.size()]);
    }

    /** Clears the list of error messages. Use before processing a file. **/
    void clearMessages()
    {
        mLines = null;
        mPkgName = null;
        mInInterface.clear();
        mInScope.clear();
        mMessages.clear();
        mComments.clear();
        mImports.clear();
        mReferenced.clear();
        mMethodBlockLevel = 0;
    }

    /**
     * Sets the lines for the file being checked.
     * @param aLines the lines of the file
     **/
    void setLines(String[] aLines)
    {
        mLines = aLines;

        checkHeader();

        // Iterate over the lines looking for long lines and tabs.
        for (int i = 0; i < mLines.length; i++) {
            // check for long line, but possibly allow imports
            if ((mLines[i].length() > mConfig.getMaxLineLength()) &&
                !(mConfig.getIgnoreLineLengthRegexp().match(mLines[i])) &&
                !(mConfig.isIgnoreImportLength() &&
                  mLines[i].trim().startsWith("import")))
            {
                log(i + 1,
                    "line longer than " + mConfig.getMaxLineLength() +
                    " characters");
            }

            if (!mConfig.isAllowTabs() && (mLines[i].indexOf('\t') != -1)) {
                log(i + 1, "line contains a tab character");
            }
        }

        // Check excessive number of lines
        if (mLines.length > mConfig.getMaxFileLength()) {
            log(1,
                "file length is " + mLines.length + " lines (max allowed is " +
                mConfig.getMaxFileLength() + ").");
        }
    }


    /**
     * Verify that a valid Javadoc comment exists for the method.
     * @param aSig the method signature
     **/
    void verifyMethod(MethodSignature aSig)
    {
        // no need to check constructor names
        if (!aSig.isConstructor() &&
            !mConfig.getMethodRegexp().match(aSig.getName()))
        {
            log(aSig.getLineNo(),
                "method name '" + aSig.getName() +
                "' must match pattern '" + mConfig.getMethodPat() + "'.");
        }

        // Always verify the parameters are ok
        for (Iterator it = aSig.getParams().iterator(); it.hasNext();) {
            checkParameter((LineText) it.next());
        }

        // Always check that the order of modifiers follows the JLS suggestion
        checkModOrder(aSig.getModSet());

        // now check the javadoc
        final Scope methodScope = inInterfaceBlock()
            ? Scope.PUBLIC
            : aSig.getModSet().getVisibilityScope();

        if (!inCheckScope(methodScope)) {
            return; // no need to really check anything
        }

        final String[] jd = getJavadocBefore(aSig.getLineNo() - 1);
        if (jd == null) {
            log(aSig.getLineNo(), "method is missing a Javadoc comment.");
        }
        else {
            final List tags = getMethodTags(jd, aSig.getLineNo() - 1);
            // Check for only one @see tag
            if ((tags.size() != 1) ||
                !((JavadocTag) tags.get(0)).isSeeTag())
            {
                checkParamTags(tags, aSig.getParams());
                checkThrowsTags(tags, aSig.getThrows());
                if (aSig.isFunction()) {
                    checkReturnTag(tags, aSig.getLineNo());
                }

                // Dump out all unused tags
                final Iterator it = tags.iterator();
                while (it.hasNext()) {
                    final JavadocTag jt = (JavadocTag) it.next();
                    if (!jt.isSeeTag()) {
                        log(jt.getLineNo(), "Unused Javadoc tag.");
                    }
                }
            }
        }
    }


    /**
     * Verify that a type conforms to the style.
     * @param aMods the set of modifiers for the type
     * @param aType the type details
     **/
    void verifyType(MyModifierSet aMods, MyCommonAST aType)
    {
        if (!mConfig.getTypeRegexp().match(aType.getText())) {
            log(aType.getLineNo(),
                "type name '" + aType.getText() +
                "' must match pattern '" + mConfig.getTypePat() + "'.");
        }

        // Always check that the order of modifiers follows the JLS suggestion
        checkModOrder(aMods);

        //
        // Only Javadoc testing below
        //
        final Scope typeScope =
            inInterfaceBlock() ? Scope.PUBLIC : aMods.getVisibilityScope();

        if (!inCheckScope(typeScope)) {
            return; // no need to really check anything
        }

        final int lineNo = (aMods.size() > 0)
            ? aMods.getFirstLineNo()
            : aType.getLineNo();

        final String[] jd = getJavadocBefore(lineNo - 1);
        if (jd == null) {
            log(lineNo, "type is missing a Javadoc comment.");
        }
        else if (!mConfig.isAllowNoAuthor() &&
                 mInScope.size() == 0 && // don't check author for inner classes
                 (MATCH_JAVADOC_AUTHOR.grep(jd).length == 0))
        {
            log(lineNo, "type Javadoc comment is missing an @author tag.");
        }
    }


    /**
     * Verify that a variable conforms to the style.
     * @param aVar the variable details
     **/
    void verifyVariable(MyVariable aVar)
    {
        if (inMethodBlock()) {
            checkVariable(aVar,
                          mConfig.getLocalVarRegexp(),
                          mConfig.getLocalVarPat());
            return;
        }

        final MyModifierSet mods = aVar.getModifierSet();
        final Scope declaredScope = mods.getVisibilityScope();
        final Scope variableScope =
            inInterfaceBlock() ? Scope.PUBLIC : declaredScope;

        // Always check that the order of modifiers follows the JLS suggestion
        checkModOrder(mods);

        if (inCheckScope(variableScope) &&
            getJavadocBefore(aVar.getLineNo() - 1) == null)
        {
            log(aVar.getLineNo(),
                "variable '" + aVar.getText() + "' missing Javadoc.");
        }

        // Check correct format
        if (inInterfaceBlock()) {
            // The only declarations allowed in interfaces are all static final,
            // even if not declared that way.
            checkVariable(aVar,
                          mConfig.getStaticFinalRegexp(),
                          mConfig.getStaticFinalPat());
        }
        else {
            ///////////////////////////////////////////////////////////////////
            // THIS BLOCK NEEDS REFACTORING!!
            ///////////////////////////////////////////////////////////////////
            final boolean isPckg = Scope.PACKAGE.equals(variableScope);
            final boolean isProt = Scope.PROTECTED.equals(variableScope);

            if (mods.containsStatic()) {
                if (mods.containsFinal()) {
                    // Handle the serialVersionUID constant which is used for
                    // Serialization. Cannot enforce rules on it. :-)
                    if (!"serialVersionUID".equals(aVar.getText())) {
                        checkVariable(aVar,
                                      mConfig.getStaticFinalRegexp(),
                                      mConfig.getStaticFinalPat());
                    }
                }
                else {
                    if (Scope.PRIVATE.equals(variableScope) ||
                        (mConfig.isAllowPackage() && isPckg) ||
                        (mConfig.isAllowProtected() && isProt))
                    {
                        checkVariable(aVar,
                                      mConfig.getStaticRegexp(),
                                      mConfig.getStaticPat());
                    }
                    else {
                        log(aVar.getLineNo(),
                            "variable '" + aVar.getText() +
                            "' must be private and have accessor methods.");
                    }
                }
            }
            else {
                // These are the non-static variables
                if (Scope.PRIVATE.equals(variableScope) ||
                    (mConfig.isAllowPackage() && isPckg) ||
                    (mConfig.isAllowProtected() && isProt))
                {
                    checkVariable(aVar,
                                  mConfig.getMemberRegexp(),
                                  mConfig.getMemberPat());
                }
                else if (mods.containsPublic() &&
                         mConfig.getPublicMemberRegexp().match(aVar.getText()))
                {
                    // silently allow
                }
                else {
                    log(aVar.getLineNo(),
                        "variable '" + aVar.getText() +
                        "' must be private and have accessor methods.");
                }
            }
        }
    }


    /**
     * Report that a statement should be using a compound statement
     * (that is, {}'s).
     * @param aStmt the token for the statement
     */
    void reportNeedBraces(Token aStmt)
    {
        if (!mConfig.isIgnoreBraces()) {
            log(aStmt.getLine(),
                "'" + aStmt.getText() + "' construct must use '{}'s.");
        }
    }


    /**
     * Verify that whitespace IS around the specified text.
     * @param aLineNo number of line to check
     * @param aColNo column where the text ends
     * @param aText the text to check
     */
    void verifyWSAroundEnd(int aLineNo, int aColNo, String aText)
    {
        verifyWSAroundBegin(aLineNo, aColNo - aText.length(), aText);
    }


    /**
     * Verify that whitespace IS around the specified text.
     * @param aLineNo number of line to check
     * @param aColNo column where the text starts
     * @param aText the text to check
     */
    void verifyWSAroundBegin(int aLineNo, int aColNo, String aText)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aLineNo - 1];
        final int before = aColNo - 2;
        final int after = aColNo + aText.length() - 1;

        if ((before >= 0) && !Character.isWhitespace(line.charAt(before))) {
            log(aLineNo, "'" + aText + "' is not preceeded with whitespace.");
        }

        if ((after < line.length()) &&
            !Character.isWhitespace(line.charAt(after)))
        {
            log(aLineNo, "'" + aText + "' is not followed by whitespace.");
        }
    }


    /**
     * Verify that no whitespace after an AST.
     * @param aAST the AST to check
     **/
    void verifyNoWSAfter(MyCommonAST aAST)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aAST.getLineNo() - 1];
        final int after = aAST.getColumnNo() + aAST.getText().length();
        if ((after >= line.length()) ||
            Character.isWhitespace(line.charAt(after)))
        {
            log(aAST.getLineNo(),
                "'" + aAST.getText() + "' is followed by whitespace.");
        }
    }


    /**
     * Verify that no whitespace before an AST.
     * @param aAST the AST to check
     **/
    void verifyNoWSBefore(MyCommonAST aAST)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        if ((before < 0) || Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(),
                "'" + aAST.getText() + "' is preceeded with whitespace.");
        }
    }

    /**
     * Verifies that the '.' as the location specified by aAST follows the
     * following rules:
     * 1. It is not preceeded with whitespace, or all characters on the line
     *    before are whitespace;
     * 2. It is not followed by whitespace, or all characters on the line
     *    after are whitespace;
     * @param aAST specified the location of the dot.
     **/
    void verifyDot(MyCommonAST aAST)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aAST.getLineNo() - 1];

        // check before
        final int before = aAST.getColumnNo() - 1;
        if ((before >= 0) && Character.isWhitespace(line.charAt(before))) {
            // verify all characters before '.' are whitespace
            for (int i = 0; i < before; i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    log(aAST.getLineNo(), "'.' is preceeded with whitespace.");
                    break;
                }
            }
        }

        // check after
        final int after = aAST.getColumnNo() + 1;
        if ((after < line.length())
            && Character.isWhitespace(line.charAt(after)))
        {
            for (int i = after + 1; i < line.length(); i++) {
                if (!Character.isWhitespace(line.charAt(i))) {
                    log(aAST.getLineNo(), "'.' is followed by whitespace.");
                    break;
                }
            }
        }
    }

    /**
     * Verify that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aConstruct the construct being checked
     */
    void verifyWSAfter(int aLineNo, int aColNo, MyToken aConstruct)
    {
        verifyWSAfter(aLineNo, aColNo, aConstruct, "");
    }

    /**
     * Verify that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aConstruct the construct being checked
     * @param aAllow other character to allow apart from whitespace
     */
    void verifyWSAfter(int aLineNo, int aColNo,
                       MyToken aConstruct, String aAllow)
    {
        checkWSAfter(aLineNo, aColNo, aConstruct, aAllow);
    }


    /**
     * Verify that a method length is ok.
     * @param aLineNo line the method block starts at
     * @param aLength the length of the method block
     */
    void verifyMethodLength(int aLineNo, int aLength)
    {
        if (aLength > mConfig.getMaxMethodLength()) {
            log(aLineNo,
                "method length is " + aLength + " lines (max allowed is " +
                mConfig.getMaxMethodLength() + ").");
        }
    }


    /**
     * Verify that a method has correct placement of the left curly brace.
     * @param aMethodLine line the method starts on
     * @param aBrace location of the brace
     */
    void verifyLCurlyMethod(int aMethodLine, MyCommonAST aBrace)
    {
        checkLCurly(aMethodLine, aBrace, mConfig.getLCurlyMethod());
    }


    /**
     * Verify that a type has correct placement of the left curly brace.
     * @param aTypeLine line the type starts on
     * @param aBrace location of the brace
     */
    void verifyLCurlyType(int aTypeLine, MyCommonAST aBrace)
    {
        checkLCurly(aTypeLine, aBrace, mConfig.getLCurlyType());
    }


    /**
     * Verify that a other has correct placement of the left curly brace.
     * @param aOtherLine line the other starts on
     * @param aBrace location of the brace
     */
    void verifyLCurlyOther(int aOtherLine, MyCommonAST aBrace)
    {
        checkLCurly(aOtherLine, aBrace, mConfig.getLCurlyOther());
    }


    /**
     * Verify the correct placement of the right curly brace.
     * @param aBrace location of the brace
     * @param aStartLine line the next statement starts on
     */
    void verifyRCurly(MyCommonAST aBrace, int aStartLine)
    {
        final RightCurlyOption option = mConfig.getRCurly();
        if ((mConfig.getRCurly() == RightCurlyOption.SAME)
            && (aBrace.getLineNo() != aStartLine))
        {
            log(aBrace.getLineNo(), "'}' should be on the same line.");
        }
        else if ((mConfig.getRCurly() == RightCurlyOption.ALONE)
                   && (aBrace.getLineNo() == aStartLine))
        {
            log(aBrace.getLineNo(), "'}' should be alone on a line.");
        }
    }

    /**
     * Verifies that a left paren conforms to formatting rules.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     */
    void verifyLParen(int aLineNo, int aColNo)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aLineNo - 1];
        final int after = aColNo - 1;
        if (after < line.length()) {
            if (Character.isWhitespace(line.charAt(after))) {
                log(aLineNo, "'(' is followed by whitespace.");
            }
        }
    }

    /**
     * Verifies that a right paren conforms to formatting rules.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     */
    void verifyRParen(int aLineNo, int aColNo)
    {
        if (mConfig.isIgnoreWhitespace()) {
            return;
        }

        final String line = mLines[aLineNo - 1];
        final int before = aColNo - 3;
        if (before >= 0) {
            if (Character.isWhitespace(line.charAt(before))) {
                log(aLineNo, "')' is preceeded by whitespace.");
            }
        }
    }


    /**
     * Verify that a constructor length is ok.
     * @param aLineNo line the constructor block starts at
     * @param aLength the length of the constructor block
     */
    void verifyConstructorLength(int aLineNo, int aLength)
    {
        if (aLength > mConfig.getMaxConstructorLength()) {
            log(aLineNo,
                "constructor length is " + aLength + " lines (max allowed is " +
                mConfig.getMaxConstructorLength() + ").");
        }
    }


    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    void reportCComment(int aStartLineNo, int aStartColNo,
                               int aEndLineNo, int aEndColNo)
    {
        if (mLines[aStartLineNo - 1].indexOf("/**", aStartColNo) != -1) {
            final String[] cc =
                extractCComment(aStartLineNo, aStartColNo,
                                aEndLineNo, aEndColNo);
            mComments.put(new Integer(aEndLineNo - 1), cc);
        }
    }


    /**
     * Report a reference to a type.
     * @param aType the type referenced
     */
    void reportReference(String aType)
    {
        mReferenced.add(aType);
    }


    /**
     * Report the name of the package the file is in.
     * @param aName the name of the package
     **/
    void reportPackageName(String aName)
    {
        mPkgName = aName;
    }


    /**
     * Report the location of an import.
     * @param aLineNo the line number
     * @param aType the type imported
     **/
    void reportImport(int aLineNo, String aType)
    {
        if (!mConfig.isIgnoreImports()) {
            // Check for a duplicate import
            final Iterator it = mImports.iterator();
            while (it.hasNext()) {
                final LineText lt = (LineText) it.next();
                if (aType.equals(lt.getText())) {
                    log(aLineNo,
                        "Duplicate import to line " + lt.getLineNo() + ".");
                }
            }
            // Add to list to check for duplicates and usage
            mImports.add(new LineText(aLineNo, aType));
        }
    }


    /**
     * Report the location of an import using a ".*".
     * @param aLineNo the line number
     * @param aPkg the package imported
     **/
    void reportStarImport(int aLineNo, String aPkg)
    {
        if (!mConfig.isIgnoreImports()) {
            log(aLineNo, "Avoid using the '.*' form of import.");
            mImports.add(new LineText(aLineNo, aPkg));
        }
    }


    /**
     * Report that the parser is entering a block that is associated with a
     * class or interface. Must match up the call to this method with a call
     * to the reportEndBlock().
     * @param aScope the Scope of the type block
     * @param aIsInterface indicates if the block is for an interface
     */
    void reportStartTypeBlock(Scope aScope, boolean aIsInterface)
    {
        mInScope.push(aScope);
        mInInterface.push(aIsInterface ? Boolean.TRUE : Boolean.FALSE);
    }


    /** Report that the parser is leaving a type block. **/
    void reportEndTypeBlock()
    {
        mInScope.pop();
        mInInterface.pop();
    }


    /**
     * Report that the parser is entering a block associated with method or
     * constructor.
     **/
    void reportStartMethodBlock()
    {
        mMethodBlockLevel++;
    }


    /**
     * Report that the parser is leaving a block associated with method or
     * constructor.
     **/
    void reportEndMethodBlock()
    {
        mMethodBlockLevel--;
    }

    // }}}

    // {{{ Private methods
    ////////////////////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Checks if aScope is a part of the Source code where we have
     * to verify correct javadoc.
     * @param aScope a <code>Scope</code> value
     * @return if a Scope is a part of the Source code where we have
     * to verify correct javadoc.
     */
    private boolean inCheckScope(Scope aScope)
    {
        final Scope configScope = mConfig.getJavadocScope();
        boolean retVal = aScope.isIn(configScope);

        // Need to handle where the scope of the enclosing type is not
        // in the scope to be checked. For example:
        // class Outer {
        //     public class Inner {
        //     }
        // }
        //
        // If the scope we are checking is "protected", then even though
        // Inner is public, we do not require Javadoc because Outer does
        // not require it.

        // to implement this we search up the scope stack
        // that all stack elements are also in configScope

        final Iterator scopeIterator = mInScope.iterator();
        while (retVal && scopeIterator.hasNext()) {
            final Scope stackScope = (Scope) scopeIterator.next();
            retVal = stackScope.isIn(configScope);
        }
        return retVal;
    }



    /**
     * Helper method to create a regular expression. Will exit if unable to
     * create the object.
     * @param aPattern the pattern to match
     * @return a created regexp object
     **/
    private static RE createRE(String aPattern)
    {
        RE retVal = null;
        try {
            retVal = new RE(aPattern);
        }
        catch (RESyntaxException e) {
            System.out.println("Failed to initialise regexp expression " +
                               aPattern);
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return retVal;
    }

    /**
     * Logs a message to be reported.
     * @param aLineNo the line number associated with the message
     * @param aMsg the message to log
     **/
    private void log(int aLineNo, String aMsg)
    {
        mMessages.add(new LineText(aLineNo, aMsg));
    }


    /**
     * Checks that a variable confirms to a specified regular expression. Logs
     * a message if it does not.
     * @param aVar the variable to check
     * @param aRegexp the regexp to match against
     * @param aPattern text representation of regexp
     **/
    private void checkVariable(MyVariable aVar, RE aRegexp, String aPattern)
    {
        if (!aRegexp.match(aVar.getText())) {
            log(aVar.getLineNo(),
                "variable '" + aVar.getText() +
                "' must match pattern '" + aPattern + "'.");
        }
    }


    /**
     * Verify that a parameter conforms to the style.
     * @param aParam the parameter details
     **/
    private void checkParameter(LineText aParam)
    {
        if (!mConfig.getParamRegexp().match(aParam.getText())) {
            log(aParam.getLineNo(),
                "parameter '" + aParam.getText() +
                "' must match pattern '" + mConfig.getParamPat() + "'.");
        }
    }


    /**
     * Returns the specified C comment as a String array.
     * @return C comment as a array
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    private String[] extractCComment(int aStartLineNo, int aStartColNo,
                                     int aEndLineNo, int aEndColNo)
    {
        String[] retVal;
        if (aStartLineNo == aEndLineNo) {
            retVal = new String[1];
            retVal[0] = mLines[aStartLineNo - 1].substring(aStartColNo,
                                                           aEndColNo + 1);
        }
        else {
            retVal = new String[aEndLineNo - aStartLineNo + 1];
            retVal[0] = mLines[aStartLineNo - 1].substring(aStartColNo);
            for (int i = aStartLineNo; i < aEndLineNo; i++) {
                retVal[i - aStartLineNo + 1] = mLines[i];
            }
            retVal[retVal.length - 1] =
                mLines[aEndLineNo - 1].substring(0, aEndColNo + 1);
        }
        return retVal;
    }

    /**
     * Returns the Javadoc comment before the specified line. null is none.
     * @return the Javadoc comment, or <code>null</code> if none
     * @param aLineNo the line number to check before
     **/
    private String[] getJavadocBefore(int aLineNo)
    {
        int lineNo = aLineNo - 1;

        // skip blank lines
        while ((lineNo > 0) && lineIsBlank(lineNo)) {
            lineNo--;
        }

        return (String[]) mComments.get(new Integer(lineNo));
    }

    /**
     * Checks if the specified line is blank.
     * @param aLineNo the line number to check
     * @return if the specified line consists only of tabs and spaces.
     **/
    private boolean lineIsBlank(int aLineNo)
    {
        // possible improvement: avoid garbage creation in trim()
        return "".equals(mLines[aLineNo].trim());
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
                        if (!lFin.equals(NEXT_TAG) && !lFin.equals(END_JAVADOC))
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
     * Checks a set of tags for matching parameters.
     * @param aTags the tags to check
     * @param aParams the parameters to check
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
            final ListIterator paramIt = aParams.listIterator();
            while (paramIt.hasNext()) {
                final LineText param = (LineText) paramIt.next();
                if (param.getText().equals(tag.getArg1())) {
                    found = true;
                    paramIt.remove();
                    break;
                }
            }

            // Handle extra JavadocTag
            if (!found) {
                log(tag.getLineNo(),
                    "Unused @param tag for '" + tag.getArg1() + "'.");
            }
        }

        // Now dump out all parameters without tags
        final ListIterator paramIt = aParams.listIterator();
        while (paramIt.hasNext()) {
            final LineText param = (LineText) paramIt.next();
            log(param.getLineNo(),
                "Expected @param tag for '" + param.getText() + "'.");
        }
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
                    log(jt.getLineNo(), "Duplicate @return tag.");
                }
                found = true;
                it.remove();
            }
        }

        // Handle there being no @return tags
        if (!found) {
            log(aLineNo, "Expected an @return tag.");
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
        final ListIterator tagIt = aTags.listIterator();
        while (tagIt.hasNext()) {
            final JavadocTag tag = (JavadocTag) tagIt.next();

            if (!tag.isThrowsTag()) {
                continue;
            }

            tagIt.remove();

            // Loop looking for matching throw
            boolean found = false;
            final ListIterator throwIt = aThrows.listIterator();
            while (throwIt.hasNext()) {
                final LineText t = (LineText) throwIt.next();
                if (t.getText().equals(tag.getArg1())) {
                    found = true;
                    throwIt.remove();
                    break;
                }
            }

            // Handle extra JavadocTag
            if (!found) {
                log(tag.getLineNo(),
                    "Unused @throws tag for '" + tag.getArg1() + "'.");
            }
        }

        // Now dump out all throws without tags
        final ListIterator throwIt = aThrows.listIterator();
        while (throwIt.hasNext()) {
            final LineText t = (LineText) throwIt.next();
            log(t.getLineNo(),
                "Expected @throws tag for '" + t.getText() + "'.");
        }
    }


    /** checks that a file contains a valid header **/
    private void checkHeader()
    {
        if (mConfig.getHeaderLines().length > mLines.length) {
            log(1, "Missing a header - not enough lines in file.");
        }
        else {
            for (int i = 0; i < mConfig.getHeaderLines().length; i++) {
                // skip lines we are meant to ignore
                if (mConfig.isHeaderIgnoreLineNo(i + 1)) {
                    continue;
                }

                final String headerLine = mConfig.getHeaderLines()[i];

                // TODO: RE creation should be cached to avoid
                // re-compilation when multiple files are checked. Will wait
                // until this is shown to be a performance problem.
                final boolean match =
                    mConfig.getHeaderLinesRegexp() ?
                    createRE(headerLine).match(mLines[i]) :
                    headerLine.equals(mLines[i]);

                if (!match) {
                    log(i + 1,
                        "Line does not match expected header line of '" +
                        mConfig.getHeaderLines()[i] + "'.");
                    break; // stop checking
                }
            }
        }
    }

    /**
     * checks if the order of modifiers follows the suggestions
     * in the JLS and logs an error message accordingly.
     *
     * @param aModSet the set of modifiers
     */
    private void checkModOrder(MyModifierSet aModSet)
    {
        final String error = aModSet.checkOrderSuggestedByJLS();
        if (error != null) {
            log(aModSet.getFirstLineNo(), error);
        }
    }

    /**
     * @return the class name from a fully qualified name
     * @param aType the fully qualified name
     */
    private String basename(String aType)
    {
        final int i = aType.lastIndexOf(".");
        return (i == -1) ? aType : aType.substring(i + 1);
    }

    /** Check the imports that are unused or unrequired. **/
    private void checkImports()
    {
        if (mConfig.isIgnoreImports()) {
            return;
        }

        // Loop checking imports
        final Iterator it = mImports.iterator();
        while (it.hasNext()) {
            final LineText imp = (LineText) it.next();

            if (fromPackage(imp.getText(), "java.lang")) {
                log(imp.getLineNo(),
                    "Redundant import from the java.lang package.");
            }
            else if (fromPackage(imp.getText(), mPkgName)) {
                log(imp.getLineNo(), "Redundant import from the same package.");
            }
            else if (!imp.getText().endsWith(".*") &&
                     !mReferenced.contains(basename(imp.getText())))
            {
                log(imp.getLineNo(), "Unused import - " + imp.getText());
            }
            else if (isIllegalImport(imp.getText())) {
                log(imp.getLineNo(),
                    "Import from illegal package - " + imp.getText());
            }
        }
    }

    /**
     * Checks if an import is from a package that must not be used.
     * @param aImportText the argument of the import keyword
     * @return if <code>aImportText</code> contains an illegal package prefix
     */
    private boolean isIllegalImport(String aImportText)
    {
        final Iterator it = mConfig.getIllegalImports().iterator();
        while (it.hasNext()) {
            final String illegalPkgName = (String) it.next();
            if (aImportText.startsWith(illegalPkgName + ".")) {
                return true;
            }
        }
        return false;
    }

    /** @return whether currently in an interface block **/
    private boolean inInterfaceBlock()
    {
        return (!mInInterface.empty() &&
                Boolean.TRUE.equals(mInInterface.peek()));
    }

    /** @return whether currently in a method block **/
    private boolean inMethodBlock()
    {
        return (mMethodBlockLevel > 0);
    }

    /**
     * Determines in an import statement is for types from a specified package.
     * @param aImport the import name
     * @param aPkg the package name
     * @return whether from the package
     */
    private static boolean fromPackage(String aImport, String aPkg)
    {
        boolean retVal = false;
        if (aPkg == null) {
            // If not package, then check for no package in the import.
            retVal = (aImport.indexOf('.') == -1);
        }
        else {
            final int index = aImport.lastIndexOf('.');
            if (index != -1) {
                final String front = aImport.substring(0, index);
                retVal = front.equals(aPkg);
            }
        }
        return retVal;
    }

    /**
     * Verify the correct placement of the left curly brace.
     * @param aStartLine line the construct starts on
     * @param aBrace location of the brace
     * @param aOption specifies where the brace should be
     */
    private void checkLCurly(int aStartLine,
                             MyCommonAST aBrace,
                             LeftCurlyOption aOption)
    {
        final String braceLine = mLines[aBrace.getLineNo() - 1];

        // calculate the previous line length without trailing whitespace. Need
        // to handle the case where there is no previous line, cause the line
        // being check is the first line in the file.
        final int prevLineLen = (aBrace.getLineNo() == 1)
            ? mConfig.getMaxLineLength()
            : Utils.lengthMinusTrailingWhitespace(
                mLines[aBrace.getLineNo() - 2]);

        // Check for being told to ignore, or have '{}' which is a special case
        if ((aOption == LeftCurlyOption.IGNORE)
            || ((braceLine.length() > (aBrace.getColumnNo() + 1))
                && (braceLine.charAt(aBrace.getColumnNo() + 1) == '}')))
        {
            // ignore
        }
        else if (aOption == LeftCurlyOption.NL) {
            if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), "'{' should be on a new line.");
            }
        }
        else if (aOption == LeftCurlyOption.EOL) {
            if (Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)
                && ((prevLineLen + 2) <= mConfig.getMaxLineLength()))
            {
                log(aBrace.getLineNo(), "'{' should be on the previous line.");
            }
        }
        else if (aOption == LeftCurlyOption.NLOW) {
            if (aStartLine == aBrace.getLineNo()) {
                // all ok as on the same line
            }
            else if ((aStartLine + 1) == aBrace.getLineNo()) {
                if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                    log(aBrace.getLineNo(), "'{' should be on a new line.");
                }
                else if ((prevLineLen + 2) <= mConfig.getMaxLineLength()) {
                    log(aBrace.getLineNo(),
                        "'{' should be on the previous line.");
                }
            }
            else if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), "'{' should be on a new line.");
            }
        }
    }

    /**
     * Checks that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aConstruct the construct being checked
     * @param aAllow characters to allow as well as whitespace
     */
    void checkWSAfter(int aLineNo, int aColNo,
                      MyToken aConstruct, String aAllow)
    {
        if (mConfig.isIgnoreWhitespace() ||
            ((MyToken.CAST == aConstruct) && mConfig.isIgnoreCastWhitespace()))
        {
            return;
        }

        final String line = mLines[aLineNo - 1];
        if ((aColNo < line.length())
            && !Character.isWhitespace(line.charAt(aColNo))
            && (aAllow.indexOf(line.charAt(aColNo)) == -1))
        {
            log(aLineNo,
                aConstruct.getText() + " needs to be followed by whitespace.");
        }
    }

    // }}}
}
