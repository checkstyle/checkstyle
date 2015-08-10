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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for braces around code blocks.
 * </p>
 * <p> By default the check will check the following blocks:
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NeedBraces"/&gt;
 * </pre>
 * <p> An example of how to configure the check for {@code if} and
 * {@code else} blocks is:
 * </p>
 * <pre>
 * &lt;module name="NeedBraces"&gt;
 *     &lt;property name="tokens" value="LITERAL_IF, LITERAL_ELSE"/&gt;
 * &lt;/module&gt;
 * </pre>
 * Check has an option <b>allowSingleLineStatement</b> which allows single-line
 * statements without braces, e.g.:
 * <p>
 * {@code
 * if (obj.isValid()) return true;
 * }
 * </p>
 * <p>
 * {@code
 * while (obj.isValid()) return true;
 * }
 * </p>
 * <p>
 * {@code
 * do this.notify(); while (o != null);
 * }
 * </p>
 * <p>
 * {@code
 * for (int i = 0; ; ) this.notify();
 * }
 * </p>
 * <p>
 * To configure the Check to allow {@code case, default} single-line statements
 * without braces:
 * </p>
 *
 * <pre>
 * &lt;module name=&quot;NeedBraces&quot;&gt;
 *     &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_CASE, LITERAL_DEFAULT&quot;/&gt;
 *     &lt;property name=&quot;allowSingleLineStatement&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * <p>
 * Such statements would be allowed:
 * </p>
 *
 * <pre>
 * {@code
 * switch (num) {
 *     case 1: counter++; break; // OK
 *     case 6: counter += 10; break; // OK
 *     default: counter = 100; break; // OK
 * }
 * }
 * </pre>
 *
 *
 * @author Rick Giles
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class NeedBracesCheck extends Check {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_NEED_BRACES = "needBraces";

    /**
     * Check's option for skipping single-line statements.
     */
    private boolean allowSingleLineStatement;

    /**
     * Setter.
     * @param allowSingleLineStatement Check's option for skipping single-line statements
     */
    public void setAllowSingleLineStatement(boolean allowSingleLineStatement) {
        this.allowSingleLineStatement = allowSingleLineStatement;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_WHILE,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST slistAST = ast.findFirstToken(TokenTypes.SLIST);
        boolean isElseIf = false;
        if (ast.getType() == TokenTypes.LITERAL_ELSE
            && ast.findFirstToken(TokenTypes.LITERAL_IF) != null) {
            isElseIf = true;
        }

        final boolean skipStatement = isSkipStatement(ast);

        if (slistAST == null && !isElseIf && !skipStatement) {
            log(ast.getLineNo(), MSG_KEY_NEED_BRACES, ast.getText());
        }
    }

    /**
     * Checks if current statement can be skipped by "need braces" warning.
     * @param statement if, for, while, do-while, lambda, else, case, default statements.
     * @return true if current statement can be skipped by Check.
     */
    private boolean isSkipStatement(DetailAST statement) {
        return allowSingleLineStatement && isSingleLineStatement(statement);
    }

    /**
     * Checks if current statement is single-line statement, e.g.:
     * <p>
     * <code>
     * if (obj.isValid()) return true;
     * </code>
     * </p>
     * <p>
     * <code>
     * while (obj.isValid()) return true;
     * </code>
     * </p>
     * @param statement if, for, while, do-while, lambda, else, case, default statements.
     * @return true if current statement is single-line statement.
     */
    private static boolean isSingleLineStatement(DetailAST statement) {
        boolean result;
        final int type = statement.getType();

        if (type == TokenTypes.LITERAL_IF) {
            result = isSingleLineIf(statement);
        }
        else if (type == TokenTypes.LITERAL_FOR) {
            result = isSingleLineFor(statement);
        }
        else if (type == TokenTypes.LITERAL_DO) {
            result = isSingleLineDoWhile(statement);
        }
        else if (type == TokenTypes.LITERAL_WHILE) {
            result = isSingleLineWhile(statement);
        }
        else if (type == TokenTypes.LAMBDA) {
            result = isSingleLineLambda(statement);
        }
        else if (type == TokenTypes.LITERAL_CASE) {
            result = isSingleLineCase(statement);
        }
        else if (type == TokenTypes.LITERAL_DEFAULT) {
            result = isSingleLineDefault(statement);
        }
        else {
            result = isSingleLineElse(statement);
        }

        return result;
    }

    /**
     * Checks if current while statement is single-line statement, e.g.:
     * <p>
     * {@code
     * while (obj.isValid()) return true;
     * }
     * </p>
     * @param literalWhile {@link TokenTypes#LITERAL_WHILE while statement}.
     * @return true if current while statement is single-line statement.
     */
    private static boolean isSingleLineWhile(DetailAST literalWhile) {
        boolean result = false;
        if (literalWhile.getParent().getType() == TokenTypes.SLIST
                && literalWhile.getLastChild().getType() != TokenTypes.SLIST) {
            final DetailAST block = literalWhile.getLastChild().getPreviousSibling();
            result = literalWhile.getLineNo() == block.getLineNo();
        }
        return result;
    }

    /**
     * Checks if current do-while statement is single-line statement, e.g.:
     * <p>
     * <code>
     * do this.notify(); while (o != null);
     * </code>
     * </p>
     * @param literalDo {@link TokenTypes#LITERAL_DO do-while statement}.
     * @return true if current do-while statement is single-line statement.
     */
    private static boolean isSingleLineDoWhile(DetailAST literalDo) {
        boolean result = false;
        if (literalDo.getParent().getType() == TokenTypes.SLIST
                && literalDo.getFirstChild().getType() != TokenTypes.SLIST) {
            final DetailAST block = literalDo.getFirstChild();
            result = block.getLineNo() == literalDo.getLineNo();
        }
        return result;
    }

    /**
     * Checks if current for statement is single-line statement, e.g.:
     * <p>
     * <code>
     * for (int i = 0; ; ) this.notify();
     * </code>
     * </p>
     * @param literalFor {@link TokenTypes#LITERAL_FOR for statement}.
     * @return true if current for statement is single-line statement.
     */
    private static boolean isSingleLineFor(DetailAST literalFor) {
        boolean result = false;
        if (literalFor.getLastChild().getType() == TokenTypes.EMPTY_STAT) {
            result = true;
        }
        else if (literalFor.getParent().getType() == TokenTypes.SLIST
                && literalFor.getLastChild().getType() != TokenTypes.SLIST) {
            final DetailAST block = literalFor.findFirstToken(TokenTypes.EXPR);
            result = literalFor.getLineNo() == block.getLineNo();
        }
        return result;
    }

    /**
     * Checks if current if statement is single-line statement, e.g.:
     * <p>
     * <code>
     * if (obj.isValid()) return true;
     * </code>
     * </p>
     * @param literalIf {@link TokenTypes#LITERAL_IF if statement}.
     * @return true if current if statement is single-line statement.
     */
    private static boolean isSingleLineIf(DetailAST literalIf) {
        boolean result = false;
        final DetailAST ifCondition = literalIf.findFirstToken(TokenTypes.EXPR);
        if (literalIf.getParent().getType() == TokenTypes.SLIST) {
            final DetailAST literalIfLastChild = literalIf.getLastChild();
            final DetailAST block;
            if (literalIfLastChild.getType() == TokenTypes.LITERAL_ELSE) {
                block = literalIfLastChild.getPreviousSibling();
            }
            else {
                block = literalIfLastChild;
            }
            result = ifCondition.getLineNo() == block.getLineNo();
        }
        return result;
    }

    /**
     * Checks if current lambda statement is single-line statement, e.g.:
     * <p>
     * <code>
     * Runnable r = () -> System.out.println("Hello, world!");
     * </code>
     * </p>
     * @param lambda {@link TokenTypes#LAMBDA lambda statement}.
     * @return true if current lambda statement is single-line statement.
     */
    private static boolean isSingleLineLambda(DetailAST lambda) {
        boolean result = false;
        final DetailAST block = lambda.getLastChild();
        if (block.getType() != TokenTypes.SLIST) {
            result = lambda.getLineNo() == block.getLineNo();
        }
        return result;
    }

    /**
     * Checks if current case statement is single-line statement, e.g.:
     * <p>
     * {@code
     * case 1: dosomeStuff(); break;
     * case 2: dosomeStuff(); break;
     * }
     * </p>
     * @param literalCase {@link TokenTypes#LITERAL_CASE case statement}.
     * @return true if current case statement is single-line statement.
     */
    private static boolean isSingleLineCase(DetailAST literalCase) {
        boolean result = false;
        final DetailAST slist = literalCase.getNextSibling();
        final DetailAST block = slist.getFirstChild();
        if (block.getType() != TokenTypes.SLIST) {
            final DetailAST caseBreak = slist.findFirstToken(TokenTypes.LITERAL_BREAK);
            final boolean atOneLine = literalCase.getLineNo() == block.getLineNo();
            if (caseBreak != null) {
                result = atOneLine && block.getLineNo() == caseBreak.getLineNo();
            }
        }
        return result;
    }

    /**
     * Checks if current default statement is single-line statement, e.g.:
     * <p>
     * <code>
     * default: doSomeStuff();
     * </code>
     * </p>
     * @param literalDefault {@link TokenTypes#LITERAL_DEFAULT default statement}.
     * @return true if current default statement is single-line statement.
     */
    private static boolean isSingleLineDefault(DetailAST literalDefault) {
        boolean result = false;
        final DetailAST slist = literalDefault.getNextSibling();
        final DetailAST block = slist.getFirstChild();
        if (block.getType() != TokenTypes.SLIST) {
            result = literalDefault.getLineNo() == block.getLineNo();
        }
        return result;
    }

    /**
     * Checks if current else statement is single-line statement, e.g.:
     * <p>
     * <code>
     * else doSomeStuff();
     * </code>
     * </p>
     * @param literalElse {@link TokenTypes#LITERAL_ELSE else statement}.
     * @return true if current else statement is single-line statement.
     */
    private static boolean isSingleLineElse(DetailAST literalElse) {
        boolean result = false;
        final DetailAST block = literalElse.getFirstChild();
        if (block.getType() != TokenTypes.SLIST) {
            result = literalElse.getLineNo() == block.getLineNo();
        }
        return result;
    }
}
