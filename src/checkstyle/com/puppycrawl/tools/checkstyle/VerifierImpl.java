////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

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
 * Simple implementation of the Verifier interface. Should really get details
 * of the rules from a configuration file, rather than being hard coded.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
class VerifierImpl
    implements Verifier
{
    /** the pattern to match Javadoc tags that take an argument **/
    private static final String MATCH_JAVADOC_ARG_PAT
        = "@(throws|exception|param)\\s+(\\S+)\\s+\\S";
    /** compiled regexp to match Javadoc tags that take an argument **/
    private static final RE MATCH_JAVADOC_ARG = createRE(MATCH_JAVADOC_ARG_PAT);

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

    /** tracks the level of block definitions for methods **/
    private int mMethodBlockLevel = 0;
    
    /** the messages being logged **/
    private final List mMessages = new ArrayList();

    /** the lines of the file being checked **/
    private String[] mLines;

    /** map of the Javadoc comments indexed on the last line of the comment.
     * The hack is it assumes that there is only one Javadoc comment per line.
     **/
    private final Map mComments = new HashMap();

    /** the set of imports (no line number) **/
    private final Set mImports = new HashSet();

    /** the identifiers used **/
    private final Set mReferenced = new HashSet();

    /** pattern to match parameters **/
    private final String mParamPat;
    /** regexp to match parameters **/
    private final RE mParamRegexp;

    /** pattern to match static variables **/
    private final String mStaticPat;
    /** regexp to match static variables **/
    private final RE mStaticRegexp;

    /** pattern to match static final variables **/
    private final String mStaticFinalPat;
    /** regexp to match static final variables **/
    private final RE mStaticFinalRegexp;

    /** pattern to match member variables **/
    private final String mMemberPat;
    /** regexp to match member variables **/
    private final RE mMemberRegexp;

    /** pattern to match type names **/
    private final String mTypePat;
    /** regexp to match type names **/
    private final RE mTypeRegexp;

    /** the maximum line length **/
    private final int mMaxLineLength;
    /** whether to allow tabs **/
    private final boolean mAllowTabs;
    /** whether to allow protected data **/
    private final boolean mAllowProtected;
    /** whether to allow having no author tag **/
    private final boolean mAllowNoAuthor;
    /** whether to relax javadoc checking **/
    private final boolean mRelaxJavadoc;
    /** whether to process imports **/
    private final boolean mCheckImports;
    
    /** the header lines to check for **/
    private final String[] mHeaderLines;
    /** line number to ignore in header **/
    private final int mHeaderIgnoreLineNo;

    ////////////////////////////////////////////////////////////////////////////
    // Constructor methods
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs the object.
     * @param aParamPat pattern to match against parameters
     * @param aStaticPat pattern to match against static variables
     * @param aStaticFinalPat pattern to match against static final variables
     * @param aMemberPat pattern to match against member variables
     * @param aTypePat pattern to match against type names
     * @param aMaxLineLength max line length allowed
     * @param aAllowTabs specifies if tabs are allowed
     * @param aAllowProtected specifies if protected data is allowed
     * @param aAllowNoAuthor specifies if allowed to have no author tag
     * @param aRelaxJavadoc specifies if to relax Javadoc checking
     * @param aCheckImports specifies whether to check import statements
     * @param aHeaderLines the header lines to check for
     * @param aHeaderIgnoreLineNo the line to ignore in the header
     * @throws RESyntaxException error creating a regexp object
     **/
    VerifierImpl(String aParamPat,
                 String aStaticPat,
                 String aStaticFinalPat,
                 String aMemberPat,
                 String aTypePat,
                 int aMaxLineLength,
                 boolean aAllowTabs,
                 boolean aAllowProtected,
                 boolean aAllowNoAuthor,
                 boolean aRelaxJavadoc,
                 boolean aCheckImports,
                 String[] aHeaderLines,
                 int aHeaderIgnoreLineNo)
        throws RESyntaxException
    {
        mParamPat = aParamPat;
        mParamRegexp = new RE(aParamPat);
        mStaticPat = aStaticPat;
        mStaticRegexp = new RE(aStaticPat);
        mStaticFinalPat = aStaticFinalPat;
        mStaticFinalRegexp = new RE(aStaticFinalPat);
        mMemberPat = aMemberPat;
        mMemberRegexp = new RE(aMemberPat);
        mTypePat = aTypePat;
        mTypeRegexp = new RE(aTypePat);
        mMaxLineLength = aMaxLineLength;
        mAllowTabs = aAllowTabs;
        mAllowProtected = aAllowProtected;
        mAllowNoAuthor = aAllowNoAuthor;
        mRelaxJavadoc = aRelaxJavadoc;
        mCheckImports = aCheckImports;
        mHeaderLines = aHeaderLines;
        mHeaderIgnoreLineNo = aHeaderIgnoreLineNo;
    }


    ////////////////////////////////////////////////////////////////////////////
    // Interface Verifier methods
    ////////////////////////////////////////////////////////////////////////////

    /** @see Verifier **/
    public LineText[] getMessages()
    {
        checkImports();
        Collections.sort(mMessages);
        return (LineText[]) mMessages.toArray(new LineText[] {});
    }

    /** @see Verifier **/
    public void clearMessages()
    {
        mInInterface.clear();
        mMethodBlockLevel = 0;
        mMessages.clear();
        mComments.clear();
        mImports.clear();
        mReferenced.clear();
    }

    /** @see Verifier **/
    public void setLines(String[] aLines)
    {
        mLines = aLines;

        // Iterate over the lines looking for long lines and tabs
        for (int i = 0; i < mLines.length; i++) {
            if (mLines[i].length() > mMaxLineLength) {
                log(i + 1,
                    "line longer than " + mMaxLineLength + " characters");
            }
            if (!mAllowTabs && (mLines[i].indexOf('\t') != -1)) {
                log(i + 1, "line contains a tab character");
            }
        }

        checkHeader();
    }


    /** @see Verifier **/
    public void verifyMethodJavadoc(MyModifierSet aMods,
                                    MyCommonAST aReturnType,
                                    MethodSignature aSig)
    {
        // Calculate line number. Unfortunately aReturnType does not contain a
        // valid line number
        final int lineNo = (aMods.size() > 0)
            ? aMods.getFirstLineNo()
            : aSig.getLineNo();

        final boolean isFunction = (aReturnType == null)
            ? false
            : !"void".equals(aReturnType.getFirstChild().getText());

        final String[] jd = getJavadocBefore(lineNo - 1);
        if (jd == null) {
            // logic below is:
            // - if in not in a method block (cause if we are, then this is an
            // anonymous class); AND
            // - one of:
            //    o javadoc checking not relaxed; OR
            //    o in an interface block (all methods must have javadoc); OR
            //    o method is protected or public.
            if (!inMethodBlock() &&
                (!mRelaxJavadoc || inInterfaceBlock() ||
                 (aMods.containsProtected() || aMods.containsPublic())))
            {
                log(lineNo, "method is missing a Javadoc comment.");
            }
        }
        else {
            final List tags = getMethodTags(jd, lineNo - 1);
            // Check for only one @see tag
            if ((tags.size() != 1) ||
                !((JavadocTag) tags.get(0)).isSeeTag())
            {
                checkParamTags(tags, aSig.getParams());
                checkThrowsTags(tags, aSig.getThrows());
                if (isFunction) {
                    checkReturnTag(tags, lineNo);
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


    /** @see Verifier **/
    public void verifyType(MyModifierSet aMods, MyCommonAST aType)
    {
        final int lineNo = (aMods.size() > 0)
            ? aMods.getFirstLineNo()
            : aType.getLineNo();

        final String[] jd = getJavadocBefore(lineNo - 1);
        if (jd == null) {
            // demand that types have Javadoc, so ignore mRelaxJavadoc
            log(lineNo, "type is missing a Javadoc comment.");
        }
        else if (!mAllowNoAuthor && (MATCH_JAVADOC_AUTHOR.grep(jd).length == 0))
        {
            log(lineNo, "type Javadoc comment is missing an @author tag.");
        }

        if (!mTypeRegexp.match(aType.getText())) {
            log(aType.getLineNo(),
                "type name '" + aType.getText() +
                "' must match pattern '" + mTypePat + "'.");
        }
    }


    /** @see Verifier **/
    public void verifyVariable(MyVariable aVar, boolean aInInterface)
    {
        if (getJavadocBefore(aVar.getLineNo() - 1) == null) {
            if (!mRelaxJavadoc || inInterfaceBlock() ||
                (aVar.getModifierSet().containsProtected() ||
                 aVar.getModifierSet().containsPublic()))
            {
                log(aVar.getLineNo(),
                    "variable '" + aVar.getText() + "' missing Javadoc.");
            }
        }

        // Check correct format
        if (aInInterface) {
            // The only declarations allowed in interfaces are all static final,
            // even if not declared that way.
            checkVariable(aVar,
                          mStaticFinalRegexp,
                          mStaticFinalPat);
        }
        else {
            final MyModifierSet mods = aVar.getModifierSet();
            if (mods.containsStatic()) {
                if (mods.containsFinal()) {
                    // Handle the serialVersionUID constant which is used for
                    // Serialization. Cannot enforce rules on it. :-)
                    if (!"serialVersionUID".equals(aVar.getText())) {
                        checkVariable(aVar,
                                      mStaticFinalRegexp,
                                      mStaticFinalPat);
                    }
                }
                else {
                    if (mods.containsPrivate()) {
                        checkVariable(aVar, mStaticRegexp, mStaticPat);
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
                if (mods.containsPrivate() ||
                    (mAllowProtected && mods.containsProtected()))
                {
                    checkVariable(aVar, mMemberRegexp, mMemberPat);
                }
                else {
                    log(aVar.getLineNo(),
                        "variable '" + aVar.getText() +
                        "' must be private and have accessor methods.");
                }
            }
        }
    }

    /** @see Verifier **/
    public void verifyParameter(LineText aParam)
    {
        if (!mParamRegexp.match(aParam.getText())) {
            log(aParam.getLineNo(),
                "parameter '" + aParam.getText() +
                "' must match pattern '" + mParamPat + "'.");
        }
    }

    /** @see Verifier **/
    public void verifyLeftCurly(String aText,
                                boolean aAllowIf,
                                String aConstruct,
                                int aLineNo)
    {
        if (!"{".equals(aText) && !(aAllowIf && "if".equals(aText))) {
            log(aLineNo, "'" + aConstruct + "' construct must use '{}'s.");
        }
    }

    /** @see Verifier **/
    public void verifySurroundingWS(MyCommonAST aAST)
    {
        // Guard to handle an unusable AST
        if (aAST.getLineNo() == 0) {
            return;
        }

        final String line = mLines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + aAST.getText().length();
        if ((before >= 0) && !Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(), "'" + aAST.getText() +
                "' is not preceeded with whitespace.");
        }
        else if ((after < line.length()) &&
                 !Character.isWhitespace(line.charAt(after))) {
            log(aAST.getLineNo(), "'" + aAST.getText() +
                "' is not proceeded with whitespace.");
        }
    }

    /** @see Verifier **/
    public void verifyNoWSAfter(MyCommonAST aAST)
    {
        final String line = mLines[aAST.getLineNo() - 1];
        final int after = aAST.getColumnNo() + aAST.getText().length();
        if ((after >= line.length()) ||
            Character.isWhitespace(line.charAt(after))) {
            log(aAST.getLineNo(),
                "'" + aAST.getText() + "' is proceeded with whitespace.");
        }
    }

    /** @see Verifier **/
    public void verifyNoWSBefore(MyCommonAST aAST)
    {
        final String line = mLines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        if ((before < 0) || Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(),
                "'" + aAST.getText() + "' is preceeded with whitespace.");
        }
    }

    /** @see Verifier **/
    public void reportCppComment(int aLineNo, int aColNo)
    {
        // nop
    }

    /** @see Verifier **/
    public void reportCComment(int aStartLineNo, int aStartColNo,
                               int aEndLineNo, int aEndColNo)
    {
        if (mLines[aStartLineNo - 1].indexOf("/**", aStartColNo) != -1) {
            final String[] cc =
                extractCComment(aStartLineNo, aStartColNo,
                                aEndLineNo, aEndColNo);
            mComments.put(new Integer(aEndLineNo - 1), cc);
        }
    }

    /** @see Verifier **/
    public void reportReference(String aType)
    {
        mReferenced.add(aType);
    }
    
    /** @see Verifier **/
    public void reportImport(int aLineNo, String aType)
    {
        if (mCheckImports) {
            // Check for a duplicate import
            final Iterator it = mImports.iterator();
            while (it.hasNext()) {
                final LineText lt = (LineText) it.next();
                if (aType.equals(lt.getText())) {
                    log(aLineNo,
                        "Duplicate import to line " + lt.getLineNo() + ".");
                }
            }
        }

        mImports.add(new LineText(aLineNo, aType));
    }
    
    /** @see Verifier **/
    public void reportStarImport(int aLineNo, String aPkg)
    {
        if (mCheckImports) {
            log(aLineNo, "Avoid using the '.*' form of import.");
        }
    }
    
    /** @see Verifier **/
    public void reportStartTypeBlock(boolean aIsInterface)
    {
        mInInterface.push(new Boolean(aIsInterface));
    }
    
    /** @see Verifier **/
    public void reportEndTypeBlock()
    {
        mInInterface.pop();
    }
    
    /** @see Verifier **/
    public void reportStartMethodBlock()
    {
        mMethodBlockLevel++;
    }
    
    /** @see Verifier **/
    public void reportEndMethodBlock()
    {
        mMethodBlockLevel--;
    }
    

    ////////////////////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////////////////////

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
            System.err.println("Failed to initialise regexp expression " +
                               aPattern);
            e.printStackTrace(System.err);
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
        // #HACK#: should be improved to skip blank lines.
        return (String[]) mComments.get(new Integer(aLineNo - 1));
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
        if (mHeaderLines.length > mLines.length) {
            log(1, "Missing a header - not enough lines in file.");
        }
        else {
            for (int i = 0; i < mHeaderLines.length; i++) {
                if ((i != (mHeaderIgnoreLineNo - 1)) &&
                    !mHeaderLines[i].equals(mLines[i]))
                {
                    log(i + 1,
                        "Line does not match expected header line of '" +
                        mHeaderLines[i] + "'.");
                    break; // stop checking
                }
            }
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

    /** Check for imports that are unused. **/
    private void checkImports()
    {
        if (!mCheckImports) {
            return;
        }

        final Iterator it = mImports.iterator();
        while (it.hasNext()) {
            final LineText imp = (LineText) it.next();
            if (!mReferenced.contains(basename(imp.getText()))) {
                log(imp.getLineNo(), "Unused import - " + imp.getText());
            }
        }
    }

    /** @return whether currently in an interface block **/
    private boolean inInterfaceBlock()
    {
        return ((Boolean) mInInterface.peek()).booleanValue();
    }

    /** @return whether currently in a method block **/
    private boolean inMethodBlock()
    {
        return (mMethodBlockLevel > 0);
    }
}
