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
    * the pattern to match a single line comment containing only the comment
    * itself -- no code.
    **/
    private static final String MATCH_SINGLELINE_COMMENT_PAT
      = "^\\s*//.*$";
   /** compiled regexp to match a single-line comment line **/
    private static final RE MATCH_SINGLELINE_COMMENT =
      createRE(MATCH_SINGLELINE_COMMENT_PAT);

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
    private final LocalizedMessages mMessages;

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
        mMessages = new LocalizedMessages(mConfig.getTabWidth());
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
    LocalizedMessage[] getMessages()
    {
        checkImports();
        return mMessages.getMessages();
    }

    /** Resets the verifier. Use before processing a file. **/
    void reset()
    {
        mLines = null;
        mPkgName = null;
        mInInterface.clear();
        mInScope.clear();
        mMessages.reset();
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
        mMessages.setLines(mLines);

        checkHeader();

        // Iterate over the lines looking for:
        //    - long lines
        //    - tabs
        for (int i = 0; i < mLines.length; i++) {
            // check for long line, but possibly allow imports
            final String line = mLines[i];
            final int realLength = Utils.lengthExpandedTabs(
                line, line.length(), mConfig.getTabWidth());
            if ((realLength > mConfig.getMaxLineLength())
                && !(mConfig.getIgnoreLineLengthRegexp().match(line))
                && !(mConfig.isIgnoreImportLength()
                     && line.trim().startsWith("import")))
            {
                mMessages.add(i + 1, "maxLineLen",
                              new Integer(mConfig.getMaxLineLength()));
            }

            // Check for tabs
            if (!mConfig.isAllowTabs()) {
                final int tabPosition = mLines[i].indexOf('\t');
                if (tabPosition != -1) {
                    mMessages.add(i + 1, tabPosition, "containsTab");
                }
            }
        }

        // Check excessive number of lines
        if (mLines.length > mConfig.getMaxFileLength()) {
            mMessages.add(1, "maxLen.file",
                          new Integer(mLines.length),
                          new Integer(mConfig.getMaxFileLength()));
        }
    }

    /**
     * Verify that a valid Javadoc comment exists for the method.
     * @param aSig the method signature
     **/
    void verifyMethod(MethodSignature aSig)
    {
        // no need to check constructor names
        if (!aSig.isConstructor()
            && !mConfig.getMethodRegexp().match(aSig.getName().getText()))
        {
            mMessages.add(aSig.getName().getLineNo(),
                          aSig.getName().getColumnNo(),
                          "name.invalidPattern",
                          "method",
                          aSig.getName().getText(),
                          mConfig.getMethodPat());
        }

        // Always verify the parameters are ok
        for (Iterator it = aSig.getParams().iterator(); it.hasNext();) {
            checkParameter((LineText) it.next());
        }

        // Always check that the order of modifiers follows the JLS suggestion
        checkModOrder(aSig.getModSet());

        // Check for to many parameters
        if (aSig.getParams().size() > mConfig.getMaxParameters()) {
            mMessages.add(aSig.getFirstLineNo(),
                          aSig.getFirstColNo(),
                          "maxParam", new Integer(mConfig.getMaxParameters()));
        }
        // JLS, chapter 9.4 - public in interface is strongly discouraged
        if (!mConfig.isIgnorePublicInInterface() && inInterfaceBlock()
            && aSig.getModSet().containsPublic())
        {
            mMessages.add(aSig.getModSet().getFirstLineNo(),
                          aSig.getModSet().getFirstColNo(),
                          "redundantModifier", "public");
        }

        // Check for redunant abstract
        if (inInterfaceBlock() && aSig.getModSet().containsAbstract()) {
            mMessages.add(aSig.getModSet().getFirstLineNo(),
                          aSig.getModSet().getFirstColNo(),
                          "redundantModifier", "abstract");
        }
        // now check the javadoc
        final Scope methodScope = inInterfaceBlock()
            ? Scope.PUBLIC
            : aSig.getModSet().getVisibilityScope();

        if (!inCheckScope(methodScope)) {
            return; // no need to really check anything
        }

        final String[] jd = getJavadocBefore(aSig.getFirstLineNo() - 1);
        if (jd == null) {
            mMessages.add(aSig.getFirstLineNo(),
                          aSig.getFirstColNo(),
                          "javadoc.missing", "method");
        }
        else {
            final List tags = getMethodTags(jd, aSig.getFirstLineNo() - 1);
            // Check for only one @see tag
            if ((tags.size() != 1)
                || !((JavadocTag) tags.get(0)).isSeeTag())
            {
                checkParamTags(tags, aSig.getParams());
                checkThrowsTags(tags, aSig.getThrows());
                if (aSig.isFunction()) {
                    checkReturnTag(tags, aSig.getFirstLineNo());
                }

                // Dump out all unused tags
                final Iterator it = tags.iterator();
                while (it.hasNext()) {
                    final JavadocTag jt = (JavadocTag) it.next();
                    if (!jt.isSeeTag()) {
                        mMessages.add(jt.getLineNo(),
                                      "javadoc.unusedTagGeneral");
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
            mMessages.add(aType.getLineNo(), aType.getColumnNo(),
                          "name.invalidPattern",
                          "type", aType.getText(), mConfig.getTypePat());
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
            mMessages.add(lineNo, "javadoc.missing", "type");
        }
        else if (!mConfig.isAllowNoAuthor()
                 && (mInScope.size() == 0)
                 // don't check author for inner classes
                 && (MATCH_JAVADOC_AUTHOR.grep(jd).length == 0))
        {
            mMessages.add(lineNo, "type.missingAuthorTag");
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

        if (inCheckScope(variableScope)
            && (getJavadocBefore(aVar.getStartLineNo() - 1) == null))
        {
            mMessages.add(aVar.getLineNo(), aVar.getColumnNo() - 1,
                          "variable.missingJavadoc", aVar.getText());
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
                    if (Scope.PRIVATE.equals(variableScope)
                        || (mConfig.isAllowPackage() && isPckg)
                        || (mConfig.isAllowProtected() && isProt))
                    {
                        checkVariable(aVar,
                                      mConfig.getStaticRegexp(),
                                      mConfig.getStaticPat());
                    }
                    else {
                        mMessages.add(aVar.getLineNo(), aVar.getColumnNo() - 1,
                                      "variable.notPrivate", aVar.getText());
                    }
                }
            }
            else {
                // These are the non-static variables
                if (Scope.PRIVATE.equals(variableScope)
                    || (mConfig.isAllowPackage() && isPckg)
                    || (mConfig.isAllowProtected() && isProt))
                {
                    checkVariable(aVar,
                                  mConfig.getMemberRegexp(),
                                  mConfig.getMemberPat());
                }
                else if (mods.containsPublic()
                         && mConfig.getPublicMemberRegexp()
                               .match(aVar.getText()))
                {
                    // silently allow
                }
                else {
                    mMessages.add(aVar.getLineNo(), aVar.getColumnNo() - 1,
                                  "variable.notPrivate", aVar.getText());
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
            mMessages.add(aStmt.getLine(), "needBraces", aStmt.getText());
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
            mMessages.add(aLineNo, before + 1, "ws.notPreceeded", aText);
        }

        if ((after < line.length())
            && !Character.isWhitespace(line.charAt(after)))
        {
            mMessages.add(aLineNo, after, "ws.notFollowed", aText);
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
        if ((after >= line.length())
            || Character.isWhitespace(line.charAt(after)))
        {
            mMessages.add(aAST.getLineNo(), after,
                          "ws.followed", aAST.getText());
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
            mMessages.add(aAST.getLineNo(), before,
                          "ws.preceeded", aAST.getText());
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
                    mMessages.add(aAST.getLineNo(), aAST.getColumnNo() - 1,
                                  "ws.preceeded", ".");
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
                    mMessages.add(aAST.getLineNo(), after, "ws.followed", ".");
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
            mMessages.add(aLineNo, "maxLen.method",
                          new Integer(aLength),
                          new Integer(mConfig.getMaxMethodLength()));
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
        // check surrounded by whitespace
        verifyWSAroundBegin(aBrace.getLineNo(),
                            aBrace.getColumnNo() + 1,
                            aBrace.getText());

        // Check the actual brace
        if ((mConfig.getRCurly() == RightCurlyOption.SAME)
            && (aBrace.getLineNo() != aStartLine))
        {
            mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                          "line.same", "}");
        }
        else if ((mConfig.getRCurly() == RightCurlyOption.ALONE)
                   && (aBrace.getLineNo() == aStartLine))
        {
            mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                          "line.alone", "}");
        }
    }

    /**
     * Verifies that a left paren conforms to formatting rules.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     */
    void verifyLParen(int aLineNo, int aColNo)
    {
        if (mConfig.isIgnoreWhitespace()
            || (PadOption.IGNORE == mConfig.getParenPadOption()))
        {
            return;
        }

        final String line = mLines[aLineNo - 1];
        final int after = aColNo - 1;
        if (after < line.length()) {
            if (Character.isWhitespace(line.charAt(after))) {
                mMessages.add(aLineNo, after, "ws.followed", "(");
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
        if (mConfig.isIgnoreWhitespace()
            || (PadOption.IGNORE == mConfig.getParenPadOption()))
        {
            return;
        }

        final String line = mLines[aLineNo - 1];
        final int before = aColNo - 3;
        if (before >= 0) {
            if (Character.isWhitespace(line.charAt(before))
                && !Utils.whitespaceBefore(before, line))
            {
                mMessages.add(aLineNo, before, "ws.preceeded", ")");
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
            mMessages.add(aLineNo, "maxLen.constructor",
                          new Integer(aLength),
                          new Integer(mConfig.getMaxConstructorLength()));
        }
    }


    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     **/
    void reportCPPComment(int aStartLineNo, int aStartColNo)
    {
        final String cmt = mLines[aStartLineNo - 1].substring(aStartColNo);
        if (mConfig.getTodoRegexp().match(cmt)) {
            mMessages.add(aStartLineNo, "todo.match", mConfig.getTodoPat());
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
        final String[] cc = extractCComment(aStartLineNo, aStartColNo,
                                            aEndLineNo, aEndColNo);

        // Remember if possible Javadoc comment
        if (mLines[aStartLineNo - 1].indexOf("/**", aStartColNo) != -1) {
            mComments.put(new Integer(aEndLineNo - 1), cc);
        }

        // Check for to-do comments
        for (int i = 0; i < cc.length; i++) {
            if (mConfig.getTodoRegexp().match(cc[i])) {
                mMessages.add(aStartLineNo + i, "todo.match",
                              mConfig.getTodoPat());
            }
        }
    }


    /**
     * Report a reference to a type.
     * @param aType the type referenced
     */
    void reportReference(String aType)
    {
        mReferenced.add(aType);

        // we might have multiple levels of inner classes,
        // all of them have to be marked as referenced

        // as an unwanted side effect we also add package names like
        // "com", "java", etc., but that probably doesn't hurt
        // and could be fixed by getting more info using the classloader
        int lastDot = aType.lastIndexOf('.');
        while (lastDot != -1) {
            mReferenced.add(aType.substring(0, lastDot));
            lastDot = aType.lastIndexOf('.', lastDot - 1);
        }
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
                    mMessages.add(aLineNo, "import.duplicate",
                                  new Integer(lt.getLineNo()));
                }
            }
        }
        // Add to list to check for duplicates, usage and instantiation checks
        mImports.add(new LineText(aLineNo, aType));
    }


    /**
     * Report the location of an import using a ".*".
     * @param aLineNo the line number
     * @param aPkg the package imported
     **/
    void reportStarImport(int aLineNo, String aPkg)
    {
        if (!mConfig.isIgnoreImports()) {
            mMessages.add(aLineNo, "import.avoidStar");
        }
        mImports.add(new LineText(aLineNo, aPkg));
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

    /**
     * Report that the parser has found a try block.
     * @param aBraces the start and end braces from the try block
     * @param aNoStmt whether there are any statements in the block
     */
    void reportTryBlock(MyCommonAST[] aBraces, boolean aNoStmt)
    {
        checkBlock("try", mConfig.getTryBlock(), aBraces, aNoStmt);
    }

    /**
     * Report that the parser has found a catch block.
     * @param aBraces the start and end braces from the catch block
     * @param aNoStmt whether there are any statements in the block
     */
    void reportCatchBlock(MyCommonAST[] aBraces, boolean aNoStmt)
    {
        checkBlock("catch", mConfig.getCatchBlock(), aBraces, aNoStmt);
    }

    /**
     * Report that the parser has found a finally block.
     * @param aBraces the start and end braces from the finally block
     * @param aNoStmt whether there are any statements in the block
     */
    void reportFinallyBlock(MyCommonAST[] aBraces, boolean aNoStmt)
    {
        checkBlock("finally", mConfig.getFinallyBlock(), aBraces, aNoStmt);
    }

    /**
     * Report an object instantiation.
     *
     * @param aNewAST the AST of 'new', used for line/column number reporting
     * @param aTypeName the typename, may or may not be qualified
     */
    void reportInstantiation(MyCommonAST aNewAST, LineText aTypeName)
    {
        final String typeName = aTypeName.getText();
        final int lineNo = aNewAST.getLineNo();
        final int colNo = aNewAST.getColumnNo();
        final String fqClassName = getIllegalInstantiation(typeName);
        if (fqClassName != null) {
            mMessages.add(lineNo, colNo, "instantiation.avoid", fqClassName);
        }
    }

    /**
     * Verify an operator. Checks include that the operator is surrounded by
     * whitespace, and that the operator follows the rules about whether to
     * be at the end of a line.
     * @param aLineNo number of line to check
     * @param aColNo column where the text ends
     * @param aText the text to check
     */
    void verifyOpEnd(int aLineNo, int aColNo, String aText)
    {
        verifyOpBegin(aLineNo, aColNo - aText.length(), aText);
    }

    /**
     * Verify an operator. Checks include that the operator is surrounded by
     * whitespace, and that the operator follows the rules about whether to
     * be at the end of a line.
     * @param aLineNo number of line to check
     * @param aColNo column where the text starts
     * @param aText the text to check
     */
    void verifyOpBegin(int aLineNo, int aColNo, String aText)
    {
        verifyWSAroundBegin(aLineNo, aColNo, aText);

        // Check if rest of line is whitespace, and not just the operator by
        // itself. This last bit is to handle the operator on a line by itself
        if ((mConfig.getWrapOpOption() != WrapOpOption.IGNORE)
            && !aText.equals(mLines[aLineNo - 1].trim())
            && (mLines[aLineNo - 1].substring(aColNo + aText.length() - 1)
                .trim().length() == 0))
        {
            mMessages.add(aLineNo, aColNo - 1, "line.new", aText);
        }
    }

    /**
     * Verify that the 'L' on a long is uppercase. E.g. 40L, not 40l.
     * @param aLineNo number of line to check
     * @param aColNo column where the 'ell' is
     */
    void verifyLongEll(int aLineNo, int aColNo)
    {
        if (!mConfig.isIgnoreLongEll()
            && (mLines[aLineNo - 1].charAt(aColNo) == 'l'))
        {
            mMessages.add(aLineNo, aColNo, "upperEll");
        }
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
            retVal = Utils.getRE(aPattern);
        }
        catch (RESyntaxException e) {
            System.out.println("Failed to initialise regexp expression "
                               + aPattern);
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return retVal;
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
            mMessages.add(aVar.getLineNo(), aVar.getColumnNo() - 1,
                          "name.invalidPattern",
                          "variable", aVar.getText(), aPattern);
        }
    }


    /**
     * Verify that a parameter conforms to the style.
     * @param aParam the parameter details
     **/
    private void checkParameter(LineText aParam)
    {
        if (!mConfig.getParamRegexp().match(aParam.getText())) {
            mMessages.add(aParam.getLineNo(), aParam.getColumnNo(),
                          "name.invalidPattern",
                          "parameter", aParam.getText(), mConfig.getParamPat());
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
        while ((lineNo > 0) && (lineIsBlank(lineNo) || lineIsComment(lineNo))) {
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
     * Checks if the specified line is a single-line comment without code.
     * @param aLineNo  the line number to check
     * @return if the specified line consists of only a single line comment
     *         without code.
     **/
    private boolean lineIsComment(int aLineNo)
    {
      return MATCH_SINGLELINE_COMMENT.match(mLines[aLineNo]);
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
                mMessages.add(tag.getLineNo(), "javadoc.unusedTag",
                              "@param", tag.getArg1());
            }
        }

        // Now dump out all parameters without tags
        final ListIterator paramIt = aParams.listIterator();
        while (paramIt.hasNext()) {
            final LineText param = (LineText) paramIt.next();
            mMessages.add(param.getLineNo(), param.getColumnNo(),
                          "javadoc.expectedTag", "@param", param.getText());
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
                    mMessages.add(jt.getLineNo(), "javadoc.return.duplicate");
                }
                found = true;
                it.remove();
            }
        }

        // Handle there being no @return tags
        if (!found) {
            mMessages.add(aLineNo, "javadoc.return.expected");
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
                final LineText t = (LineText) throwIt.next();
                if (t.getText().equals(documentedEx)) {
                    found = true;
                    throwIt.remove();
                    foundThrows.add(documentedEx);
                }
            }

            // Handle extra JavadocTag.
            if (!found) {
                boolean reqd = true;
                if (mConfig.isCheckUnusedThrows()) {
                    final ClassResolver cr = new ClassResolver(
                        mConfig.getClassLoader(), mPkgName, mImports);
                    try {
                        final Class clazz = cr.resolve(tag.getArg1());
                        reqd = !RuntimeException.class.isAssignableFrom(clazz)
                            && !Error.class.isAssignableFrom(clazz);
                    }
                    catch (ClassNotFoundException e) {
                        mMessages.add(tag.getLineNo(), "javadoc.classInfo",
                                      "@throws", tag.getArg1());
                    }
                }

                if (reqd) {
                    mMessages.add(tag.getLineNo(), "javadoc.unusedTag",
                                  "@throws", tag.getArg1());
                }
            }
        }

        // Now dump out all throws without tags
        final ListIterator throwIt = aThrows.listIterator();
        while (throwIt.hasNext()) {
            final LineText t = (LineText) throwIt.next();
            mMessages.add(t.getLineNo(), t.getColumnNo() - 1,
                          "javadoc.expectedTag", "@throws", t.getText());
        }
    }


    /** checks that a file contains a valid header **/
    private void checkHeader()
    {
        if (mConfig.getHeaderLines().length > mLines.length) {
            mMessages.add(1, "header.missing");
        }
        else {
            for (int i = 0; i < mConfig.getHeaderLines().length; i++) {
                // skip lines we are meant to ignore
                if (mConfig.isHeaderIgnoreLineNo(i + 1)) {
                    continue;
                }

                final String headerLine = mConfig.getHeaderLines()[i];
                try {
                    final boolean match =
                        mConfig.getHeaderLinesRegexp()
                        ? Utils.getRE(headerLine).match(mLines[i])
                        : headerLine.equals(mLines[i]);

                    if (!match) {
                        mMessages.add(i + 1, "header.mismatch",
                                      mConfig.getHeaderLines()[i]);
                        break; // stop checking
                    }
                }
                catch (RESyntaxException e) {
                    mMessages.add(i + 1, "regexp.parseError", headerLine);
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
        final MyCommonAST error = aModSet.checkOrderSuggestedByJLS();
        if (error != null) {
            mMessages.add(error.getLineNo(), error.getColumnNo(),
                          "mod.order", error.getText());
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
                mMessages.add(imp.getLineNo(), "import.lang");
            }
            else if (fromPackage(imp.getText(), mPkgName)) {
                mMessages.add(imp.getLineNo(), "import.same");
            }
            else if (!isReferencedImport(imp)) {
                mMessages.add(imp.getLineNo(), "import.unused", imp.getText());
            }
            else if (isIllegalImport(imp.getText())) {
                mMessages.add(imp.getLineNo(), "import.illegal", imp.getText());
            }
        }
    }

    /**
     * Checks is an import statement is referenced.
     * @param aImp the import parameter, e.g. "javax.swing.JButton".
     * @return if aImp is used by one of the entries in mReferenced.
     */
    private boolean isReferencedImport(LineText aImp)
    {
        if (aImp.getText().endsWith(".*")) {
            // we should try to figure out the used classes via classloader
            return true;
        }
        String impText = aImp.getText();

        return
            mReferenced.contains(basename(impText))
            || mReferenced.contains(impText);
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

    /**
     * Checks illegal instantiations.
     * @param aClassName instantiated class, may or may not be qualified
     * @return the fully qualified class name of aClassName
     * or null if instantiation of aClassName is OK
     */
    private String getIllegalInstantiation(String aClassName)
    {
        final Set illegalInsts = mConfig.getIllegalInstantiations();
        final String javaLang = "java.lang.";

        if (illegalInsts.contains(aClassName)) {
            return aClassName;
        }

        final int clsNameLen = aClassName.length();
        final int pkgNameLen = (mPkgName == null) ? 0 : mPkgName.length();

        final Iterator illIter = illegalInsts.iterator();
        while (illIter.hasNext()) {
            final String illegal = (String) illIter.next();
            final int illegalLen = illegal.length();

            // class from java.lang
            if (((illegalLen - javaLang.length()) == clsNameLen)
                && illegal.endsWith(aClassName)
                && illegal.startsWith(javaLang))
            {
                return illegal;
            }

            // class from same package

            // the toplevel package (mPkgName == null) is covered by the
            // "illegalInsts.contains(aClassName)" check above

            // the test is the "no garbage" version of
            // illegal.equals(mPkgName + "." + aClassName)
            if (mPkgName != null
                && clsNameLen == illegalLen - pkgNameLen - 1
                && illegal.charAt(pkgNameLen) == '.'
                && illegal.endsWith(aClassName)
                && illegal.startsWith(mPkgName))
            {
                return illegal;
            }

            // import statements
            final Iterator importIter = mImports.iterator();
            while (importIter.hasNext()) {
                final LineText importLineText = (LineText) importIter.next();
                final String importArg = importLineText.getText();
                if (importArg.endsWith(".*")) {
                    final String fqClass =
                        importArg.substring(0, importArg.length() - 1)
                        + aClassName;

                    // assume that illegalInsts only contain existing classes
                    // or else we might create a false alarm here
                    if (illegalInsts.contains(fqClass)) {
                        return fqClass;
                    }
                }
                else {
                    if (basename(importArg).equals(aClassName)
                        && illegalInsts.contains(importArg))
                    {
                        return importArg;
                    }
                }
            }
        }
        return null;
    }

    /** @return whether currently in an interface block **/
    private boolean inInterfaceBlock()
    {
        return (!mInInterface.empty()
                && Boolean.TRUE.equals(mInInterface.peek()));
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
        verifyWSAroundBegin(aBrace.getLineNo(),
                            aBrace.getColumnNo() + 1,
                            aBrace.getText());
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
                mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                              "line.new", "{");
            }
        }
        else if (aOption == LeftCurlyOption.EOL) {
            if (Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)
                && ((prevLineLen + 2) <= mConfig.getMaxLineLength()))
            {
                mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                              "line.previous", "{");
            }
        }
        else if (aOption == LeftCurlyOption.NLOW) {
            if (aStartLine == aBrace.getLineNo()) {
                // all ok as on the same line
            }
            else if ((aStartLine + 1) == aBrace.getLineNo()) {
                if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                    mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                                  "line.new", "{");
                }
                else if ((prevLineLen + 2) <= mConfig.getMaxLineLength()) {
                    mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                                  "line.previous", "{");
                }
            }
            else if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                mMessages.add(aBrace.getLineNo(), aBrace.getColumnNo(),
                              "line.new", "{");
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
        if (mConfig.isIgnoreWhitespace()
            || ((MyToken.CAST == aConstruct)
                && mConfig.isIgnoreCastWhitespace()))
        {
            return;
        }

        final String line = mLines[aLineNo - 1];
        if ((aColNo < line.length())
            && !Character.isWhitespace(line.charAt(aColNo))
            && (aAllow.indexOf(line.charAt(aColNo)) == -1))
        {
            mMessages.add(aLineNo, aColNo,
                          "ws.notFollowed", aConstruct.getText());
        }
    }

    /**
     * Check that a block conforms to the specified rule.
     * @param aType the type of block to be used in error messages
     * @param aOption the option to check the block against
     * @param aBraces the start and end braces from the block
     * @param aNoStmt whether there are any statements in the block
     */
    void checkBlock(String aType, BlockOption aOption,
                    MyCommonAST[] aBraces, boolean aNoStmt)
    {
        if (aNoStmt && (aOption == BlockOption.STMT)) {
            mMessages.add(aBraces[0].getLineNo(), aBraces[0].getColumnNo(),
                          "block.noStmt");
        }
        else if (aOption == BlockOption.TEXT) {
            if (aBraces[0].getLineNo() == aBraces[1].getLineNo()) {
                // Handle braces on the same line
                final String txt = mLines[aBraces[0].getLineNo() - 1]
                    .substring(aBraces[0].getColumnNo() + 1,
                               aBraces[1].getColumnNo());
                if (txt.trim().length() == 0) {
                    mMessages.add(aBraces[0].getLineNo(),
                                  aBraces[0].getColumnNo(),
                                  "block.empty", aType);
                }
            }
            else {
                // check only whitespace of first & last lines
                if ((mLines[aBraces[0].getLineNo() - 1]
                     .substring(aBraces[0].getColumnNo() + 1).trim().length()
                     == 0)
                    && (mLines[aBraces[1].getLineNo() - 1]
                        .substring(0, aBraces[1].getColumnNo()).trim().length()
                        == 0))
                {

                    // Need to check if all lines are also only whitespace
                    boolean isBlank = true;
                    for (int i = aBraces[0].getLineNo();
                         i < (aBraces[1].getLineNo() - 1);
                         i++)
                    {
                        if (mLines[i].trim().length() > 0) {
                            isBlank = false;
                            break;
                        }
                    }

                    if (isBlank) {
                        mMessages.add(aBraces[0].getLineNo(),
                                      aBraces[0].getColumnNo(),
                                      "block.empty", aType);
                    }
                }
            }
        }
    }
    // }}}
}
