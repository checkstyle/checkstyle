/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 14/09/2002
 * Time: 11:55:01
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import visitor.api.Check;
import visitor.JavaTokenTypes;
import visitor.DetailAST;

import java.util.Map;

public class WhitespaceAroundCheck extends Check
{
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.ASSIGN};
    }

    public void visitToken(DetailAST aAST)
    {
        final String[] lines = getLines();
        final String line = lines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + aAST.getText().length();

        if ((before >= 0) && !Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(), "NO LEADING SPACE for " + aAST.getText());
        }

        if ((after < line.length())
            && !Character.isWhitespace(line.charAt(after)))
        {
            log(aAST.getLineNo(), "NO TRAILING SPACE for " + aAST.getText());
        }
    }
}
