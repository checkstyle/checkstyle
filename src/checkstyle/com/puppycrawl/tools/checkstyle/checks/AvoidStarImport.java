/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 13/09/2002
 * Time: 22:23:58
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import visitor.checks.ImportCheck;
import visitor.JavaTokenTypes;
import visitor.DetailAST;

public class AvoidStarImport extends ImportCheck
{
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.IMPORT};
    }

    public void visitToken(DetailAST aAST)
    {
        final String name = getImportText(aAST);
        if ((name != null) && name.endsWith(".*")) {
            log(aAST.getLineNo(), "Avoid STAR import - " + name);
        }
    }
}
