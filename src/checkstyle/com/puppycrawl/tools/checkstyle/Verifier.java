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

/**
 * Interface for a verifier of Java rules. Each rule verifier takes the form of
 * <code>void verifyXXX(args)</code>. The implementation must not throw any
 * exceptions.
 * <P>
 * Line numbers start from 1, column numbers start for 0.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 **/
interface Verifier
{
    /**
     * Returns the ordered list of error messages.
     * @return the list of messages
     **/
    LineText[] getMessages();

    /**
     * Clears the list of error messages. Use before processing a file.
     **/
    void clearMessages();

    /**
     * Sets the lines for the file being checked.
     * @param aLines the lines of the file
     **/
    void setLines(String[] aLines);

    /**
     * Verify that a valid Javadoc comment exists for the method.
     * @param aMods the set of modifiers for the method
     * @param aReturnType the AST node representing the return type
     * @param aSig the method signature
     **/
    void verifyMethodJavadoc(MyModifierSet aMods,
                             MyCommonAST aReturnType,
                             MethodSignature aSig);

    /**
     * Verify that a type conforms to the style.
     * @param aMods the set of modifiers for the type
     * @param aType the type details
     **/
    void verifyType(MyModifierSet aMods, MyCommonAST aType);

    /**
     * Verify that a variable conforms to the style.
     * @param aVar the variable details
     **/
    void verifyVariable(MyVariable aVar);

    /**
     * Verify that a parameter conforms to the style.
     * @param aParam the parameter details
     **/
    void verifyParameter(LineText aParam);

    /**
     * Verify that a left curly brace follows.
     * @param aText the text to check
     * @param aAllowIf indicates whether an "if" statement is valid instead
     * @param aConstruct the type of construct being checked. For example "for".
     * @param aLineNo the line number of the construct
     **/
    void verifyLeftCurly(String aText,
                         boolean aAllowIf,
                         String aConstruct,
                         int aLineNo);

    /**
     * Verify that whitespace surrounds an AST.
     * @param aAST the AST to check
     **/
    void verifySurroundingWS(MyCommonAST aAST);

    /**
     * Verify that whitespace IS around the specified text.
     * @param aLineNo number of line to check
     * @param aColNo column where the text ends
     * @param aText the text to check
     */
    void verifyWSAroundEnd(int aLineNo, int aColNo, String aText);

    /**
     * Verify that whitespace IS around the specified text.
     * @param aLineNo number of line to check
     * @param aColNo column where the text starts
     * @param aText the text to check
     */
    void verifyWSAroundBegin(int aLineNo, int aColNo, String aText);

    /**
     * Verify that no whitespace after an AST.
     * @param aAST the AST to check
     **/
    void verifyNoWSAfter(MyCommonAST aAST);

    /**
     * Verify that no whitespace before an AST.
     * @param aAST the AST to check
     **/
    void verifyNoWSBefore(MyCommonAST aAST);

    /**
     * Verify that whitespace IS after a specified column.
     * @param aLineNo number of line to check
     * @param aColNo column where the cast ends
     * @param aToken the token being checked
     */
    void verifyWSAfter(int aLineNo, int aColNo, MyToken aToken);

    /**
     * Verify that a method length is ok.
     * @param aLineNo line the method block starts at
     * @param aLength the length of the method block
     */
    void verifyMethodLength(int aLineNo, int aLength);

    /**
     * Verify that a constructor length is ok.
     * @param aLineNo line the constructor block starts at
     * @param aLength the length of the constructor block
     */
    void verifyConstructorLength(int aLineNo, int aLength);

    /**
     * Report the location of a C++ comment.
     * @param aLineNo the line number
     * @param aColNo the column number
     **/
    void reportCppComment(int aLineNo, int aColNo);

    /**
     * Report the location of a C-style comment.
     * @param aStartLineNo the starting line number
     * @param aStartColNo the starting column number
     * @param aEndLineNo the ending line number
     * @param aEndColNo the ending column number
     **/
    void reportCComment(int aStartLineNo, int aStartColNo,
                        int aEndLineNo, int aEndColNo);

    /**
     * Report that the parser is entering a block associated with method or
     * constructor.
     **/
    void reportStartMethodBlock();

    /**
     * Report that the parser is leaving a block associated with method or
     * constructor.
     **/
    void reportEndMethodBlock();

    /**
     * Report a reference to a type.
     * @param aType the type referenced
     */
    void reportReference(String aType);

    /**
     * Report the name of the package the file is in.
     * @param aName the name of the package
     **/
    void reportPackageName(String aName);

    /**
     * Report the location of an import.
     * @param aLineNo the line number
     * @param aType the type imported
     **/
    void reportImport(int aLineNo, String aType);

    /**
     * Report the location of an import using a ".*".
     * @param aLineNo the line number
     * @param aPkg the package imported
     **/
    void reportStarImport(int aLineNo, String aPkg);

    /**
     * Report that the parser is entering a block that is associated with a
     * class or interface. Must match up the call to this method with a call
     * to the reportEndBlock().
     * @param aScope the Scope of the type block
     * @param aIsInterface indicates if the block is for an interface
     */
    void reportStartTypeBlock(Scope aScope, boolean aIsInterface);

    /** Report that the parser is leaving a type block. **/
    void reportEndTypeBlock();
}
