/*
 * Created by IntelliJ IDEA.
 * User: oliver.burn
 * Date: 13/09/2002
 * Time: 23:04:45
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package visitor.checks;

import visitor.checks.ImportCheck;
import visitor.JavaTokenTypes;
import visitor.DetailAST;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class UnusedImportsCheck extends ImportCheck
{
    private boolean mCollect;
    private final Set mImports = new HashSet();
    private final Set mReferenced = new HashSet();

    public void beginTree()
    {
        mCollect = false;
        mImports.clear();
        mReferenced.clear();
    }

    public void finishTree()
    {
        // loop over all the imports to see if referenced.
        final Iterator it = mImports.iterator();
        while (it.hasNext()) {
            final String imp = (String) it.next();
            if (!mReferenced.contains(basename(imp))) {
                log(666, "unused import " + imp);
            }
        }
    }

    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.IMPORT,
                          JavaTokenTypes.CLASS_DEF,
                          JavaTokenTypes.INTERFACE_DEF,
                          JavaTokenTypes.IDENT};
    }

    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == JavaTokenTypes.IDENT) {
            if (mCollect) {
                processIdent(aAST);
            }
        }
        else if (aAST.getType() == JavaTokenTypes.IMPORT) {
            processImport(aAST);
        }
        else if ((aAST.getType() == JavaTokenTypes.CLASS_DEF)
            || (aAST.getType() == JavaTokenTypes.INTERFACE_DEF))
        {
            mCollect = true;
        }
    }

    private void processIdent(DetailAST aAST)
    {
        // TODO: should be a lot smarter in selection. Currently use
        // same algorithm as real checkstyle
        final DetailAST parent = aAST.getParent();
        if (parent.getType() == JavaTokenTypes.DOT) {
            if (aAST.getNextSibling() != null) {
                mReferenced.add(aAST.getText());
            }
        }
        else {
            mReferenced.add(aAST.getText());
        }
    }

    private void processImport(DetailAST aAST)
    {
        final String name = getImportText(aAST);
        if ((name != null) && !name.endsWith(".*")) {
            mImports.add(name);
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
}
