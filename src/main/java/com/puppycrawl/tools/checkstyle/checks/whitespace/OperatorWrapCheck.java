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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import org.apache.commons.lang3.StringUtils;

import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 * <p>
 * Checks line wrapping for operators.
 * The policy to verify is specified using the {@link WrapOption} class
 * and defaults to {@link WrapOption#NL}.
 * </p>
 * <p> By default the check will check the following operators:
 *  {@link TokenTypes#BAND BAND},
 *  {@link TokenTypes#BOR BOR},
 *  {@link TokenTypes#BSR BSR},
 *  {@link TokenTypes#BXOR BXOR},
 *  {@link TokenTypes#COLON COLON},
 *  {@link TokenTypes#DIV DIV},
 *  {@link TokenTypes#EQUAL EQUAL},
 *  {@link TokenTypes#GE GE},
 *  {@link TokenTypes#GT GT},
 *  {@link TokenTypes#LAND LAND},
 *  {@link TokenTypes#LE LE},
 *  {@link TokenTypes#LITERAL_INSTANCEOF LITERAL_INSTANCEOF},
 *  {@link TokenTypes#LOR LOR},
 *  {@link TokenTypes#LT LT},
 *  {@link TokenTypes#MINUS MINUS},
 *  {@link TokenTypes#MOD MOD},
 *  {@link TokenTypes#NOT_EQUAL NOT_EQUAL},
 *  {@link TokenTypes#PLUS PLUS},
 *  {@link TokenTypes#QUESTION QUESTION},
 *  {@link TokenTypes#SL SL},
 *  {@link TokenTypes#SR SR},
 *  {@link TokenTypes#STAR STAR}.
 * Other acceptable tokens are
 *  {@link TokenTypes#ASSIGN ASSIGN},
 *  {@link TokenTypes#BAND_ASSIGN BAND_ASSIGN},
 *  {@link TokenTypes#BOR_ASSIGN BOR_ASSIGN},
 *  {@link TokenTypes#BSR_ASSIGN BSR_ASSIGN},
 *  {@link TokenTypes#BXOR_ASSIGN BXOR_ASSIGN},
 *  {@link TokenTypes#DIV_ASSIGN DIV_ASSIGN},
 *  {@link TokenTypes#MINUS_ASSIGN MINUS_ASSIGN},
 *  {@link TokenTypes#MOD_ASSIGN MOD_ASSIGN},
 *  {@link TokenTypes#PLUS_ASSIGN PLUS_ASSIGN},
 *  {@link TokenTypes#SL_ASSIGN SL_ASSIGN},
 *  {@link TokenTypes#SR_ASSIGN SR_ASSIGN},
 *  {@link TokenTypes#STAR_ASSIGN STAR_ASSIGN}.
 * </p>
 *  <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="OperatorWrap"/&gt;
 * </pre>
 * <p> An example of how to configure the check for assignment operators at the
 * end of a line is:
 * </p>
 * <pre>
 * &lt;module name="OperatorWrap"&gt;
 *     &lt;property name="tokens"
 *               value="ASSIGN,DIV_ASSIGN,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,MOD_ASSIGN,SR_ASSIGN,BSR_ASSIGN,SL_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,BAND_ASSIGN"/&gt;
 *     &lt;property name="option" value="eol"/&gt;
  * &lt;/module&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class OperatorWrapCheck
    extends AbstractOptionCheck<WrapOption> {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String LINE_NEW = "line.new";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String LINE_PREVIOUS = "line.previous";

    /**
     * Sets the operator wrap option to new line.
     */
    public OperatorWrapCheck() {
        super(WrapOption.NL, WrapOption.class);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              //' +' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.LITERAL_INSTANCEOF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              //' +' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      //"-="
            TokenTypes.STAR_ASSIGN,       // "*="
            TokenTypes.MOD_ASSIGN,        // "%="
            TokenTypes.SR_ASSIGN,         // ">>="
            TokenTypes.BSR_ASSIGN,        // ">>>="
            TokenTypes.SL_ASSIGN,         // "<<="
            TokenTypes.BXOR_ASSIGN,       // "^="
            TokenTypes.BOR_ASSIGN,        // "|="
            TokenTypes.BAND_ASSIGN,       // "&="

        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.COLON) {
            final DetailAST parent = ast.getParent();
            if (parent.getType() == TokenTypes.LITERAL_DEFAULT
                || parent.getType() == TokenTypes.LITERAL_CASE) {
                //we do not want to check colon for cases and defaults
                return;
            }
        }
        final WrapOption wOp = getAbstractOption();

        final String text = ast.getText();
        final int colNo = ast.getColumnNo();
        final int lineNo = ast.getLineNo();
        final String currentLine = getLine(lineNo - 1);

        // Check if rest of line is whitespace, and not just the operator
        // by itself. This last bit is to handle the operator on a line by
        // itself.
        if (wOp == WrapOption.NL
                && !text.equals(currentLine.trim())
                && StringUtils.isBlank(currentLine.substring(colNo + text.length()))) {
            log(lineNo, colNo, LINE_NEW, text);
        }
        else if (wOp == WrapOption.EOL
                  && Utils.whitespaceBefore(colNo - 1, currentLine)) {
            log(lineNo, colNo, LINE_PREVIOUS, text);
        }
    }
}
