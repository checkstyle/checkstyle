/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 13/09/2002
 * Time: 20:26:30
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import visitor.api.Check;
import visitor.DetailAST;
import visitor.JavaTokenTypes;

public abstract class ImportCheck
    extends Check
{
    private static final String TEXT_KEY = "name";

    protected String getImportText(DetailAST aAST)
    {
        String text = (String) getTokenContext().get(TEXT_KEY);
        if (text != null) {
            return text;
        }

        final StringBuffer buf = new StringBuffer();
        extractIdent(buf, (DetailAST) aAST.getFirstChild());
        text = buf.toString();
        getTokenContext().put(TEXT_KEY, text);
        return text;
    }

    private static void extractIdent(StringBuffer aBuf, DetailAST aAST)
    {
        if (aAST == null) {
            System.out.println("CALLED WITH NULL");
            return;
        }

        if (aAST.getType() == JavaTokenTypes.DOT) {
            extractIdent(aBuf, (DetailAST) aAST.getFirstChild());
            aBuf.append(".");
            extractIdent(aBuf,
                         (DetailAST) aAST.getFirstChild().getNextSibling());
        }
        else if ((aAST.getType() == JavaTokenTypes.IDENT)
            || (aAST.getType() == JavaTokenTypes.STAR)) {
            aBuf.append(aAST.getText());
        }
        else {
            System.out.println("********* Got the string " + aAST.getText());
            aBuf.append(aAST.getText());
        }
    }
}
