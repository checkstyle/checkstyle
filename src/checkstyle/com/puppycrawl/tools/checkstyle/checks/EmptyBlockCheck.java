/*
 * Created by IntelliJ IDEA.
 * User: lk
 * Date: Sep 15, 2002
 * Time: 8:32:39 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import java.util.Set;
import java.util.HashSet;

import visitor.api.Check;
import visitor.DetailAST;
import visitor.JavaTokenTypes;

public class EmptyBlockCheck extends Check
{
    private final Set mCheckFor = new HashSet();

    public EmptyBlockCheck()
    {
        mCheckFor.add("if");
        mCheckFor.add("try");
        mCheckFor.add("catch");
        mCheckFor.add("finally");
        // TODO: currently there is no way to differenciate between if and else
        // else is not available as a parent token, instead if has two statement children
        // needs grammar change or workaround here to make config simple
    }

    // TODO: overwrite mCheckFor based on user settings in config file

    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.SLIST};
    }

    public void visitToken(DetailAST aAST)
    {
        // defend against users that change the token set in the config file.
        if (aAST.getType() != JavaTokenTypes.SLIST) {
            return;
        }

        if (aAST.getChildCount() == 0) {
            DetailAST parent = aAST.getParent();
            String parentText = parent.getText();
            if (mCheckFor.contains(parentText)) {
                log(aAST.getLineNo(), "empty " + parentText + " block");
            }
        }

    }
}
