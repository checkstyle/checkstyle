/*
 * Created by IntelliJ IDEA.
 * User: lk
 * Date: Sep 14, 2002
 * Time: 3:13:55 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import antlr.collections.AST;
import visitor.api.Check;
import visitor.JavaTokenTypes;
import visitor.DetailAST;

public class SimplifyBooleanReturnCheck extends Check
{
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.LITERAL_if};
    }

    public void visitToken(DetailAST aAST)
    {
        // paranoia - what an untrusting sole :-)
        if (aAST.getType() != JavaTokenTypes.LITERAL_if) {
            throw new IllegalArgumentException("not an if");
        }

        // don't bother if this is not if then else
        if (aAST.getChildCount() != 3) {
            return;
        }

        AST condition = aAST.getFirstChild();
        AST thenStatement = condition.getNextSibling();
        AST elseStatement = thenStatement.getNextSibling();

        if (returnsOnlyBooleanLiteral(thenStatement)
                && returnsOnlyBooleanLiteral(elseStatement))
        {
            log(aAST.getLineNo(), "Remove conditional logic.");
        }
    }

    private boolean returnsOnlyBooleanLiteral(AST aAST)
    {
        if (isBooleanLiteralReturnStatement(aAST)) {
            return true;
        }

        AST firstStmnt = aAST.getFirstChild();
        return isBooleanLiteralReturnStatement(firstStmnt);
    }

    private boolean isBooleanLiteralReturnStatement(AST aAST)
    {
        if (aAST.getType() != JavaTokenTypes.LITERAL_return)
            return false;

        AST expr = aAST.getFirstChild();
        AST value = expr.getFirstChild();

        int valueType = value.getType();
        return ( valueType == JavaTokenTypes.LITERAL_true
                || valueType == JavaTokenTypes.LITERAL_false );
    }
}
