/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 14/09/2002
 * Time: 09:51:28
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import antlr.collections.AST;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import visitor.api.Check;
import visitor.JavaTokenTypes;
import visitor.DetailAST;

public class ModifierCheck extends Check
{
    /**
     * The order of modifiers as suggested in sections 8.1.1,
     * 8.3.1 and 8.4.3 of the JLS.
     */
    private static final String[] JLS_ORDER =
    {
        "public", "protected", "private", "abstract", "static", "final",
        "transient", "volatile", "synchronized", "native", "strictfp"
    };

    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.MODIFIERS};
    }

    public void visitToken(DetailAST aAST)
    {
        final List mods = new ArrayList();
        AST modifier = aAST.getFirstChild();
        while (modifier != null) {
            mods.add(modifier);
            modifier = modifier.getNextSibling();
        }

        if (!mods.isEmpty()) {
            final DetailAST error = checkOrderSuggestedByJLS(mods);
            if (error != null) {
                log(error.getLineNo(), "OUT OF ORDER " + error.getText());
            }
        }
    }


    /**
     * Checks if the modifiers were added in the order suggested
     * in the Java language specification.
     *
     * @return null if the order is correct, otherwise returns the offending
     * *       modifier AST.
     */
    DetailAST checkOrderSuggestedByJLS(List aModifiers)
    {
        int i = 0;
        DetailAST modifier;
        final Iterator it = aModifiers.iterator();
        do {
            if (!it.hasNext()) {
                return null;
            }

            modifier = (DetailAST) it.next();
            while ((i < JLS_ORDER.length)
                   && !JLS_ORDER[i].equals(modifier.getText()))
            {
                i++;
            }
        } while (i < JLS_ORDER.length);

        return modifier;
    }
}
