package com.puppycrawl.tools.checkstyle.checks;

import java.util.Stack;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

// TODO: need to create a class to represent the constants in JavaTokenTypes.
// Needed to break circular dependencies
public class RedundantModifierCheck extends Check implements JavaTokenTypes
{
    private final Stack mInInterface = new Stack();

    public void beginTree()
    {
        super.beginTree();
        mInInterface.clear();
    }

    public int[] getDefaultTokens()
    {
        return new int[] {MODIFIERS, INTERFACE_DEF, CLASS_DEF};
    }

    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType())
        {
            case INTERFACE_DEF:
                mInInterface.push(Boolean.TRUE);
                break;
            case CLASS_DEF:
                mInInterface.push(Boolean.FALSE);
                break;
            case MODIFIERS:

                // modifiers of the interface itself (public interface X)
                // will be below the INTERFACE_DEF node. Example:

                // public interface X {void y();}

                // INTERFACE_DEF
                // + MODUFIERS
                //   + public
                // + OBJ_BLOCK
                //   + ...

                if (inInterfaceBlock(aAST)) {
                    DetailAST ast = (DetailAST) aAST.getFirstChild();
                    while (ast != null) {
                        String modifier = ast.getText();
                        if ("public".equals(modifier)
                                || "abstract".equals(modifier))
                        {
                            log(ast.getLineNo(),
                                    ast.getColumnNo(),
                                    "redundantModifier",
                                    new String[] {modifier});
                        }
                        ast = (DetailAST) ast.getNextSibling();
                    }
                }
                break;
            default:
                return;
        }
    }

    /** @return whether currently in an interface block,
     *          i.e. in an OBJ_BLOCK of an INTERFACE_DEF
     */
    private boolean inInterfaceBlock(DetailAST aAST)
    {
        if (mInInterface.empty()) {
            return false;
        }
        if (aAST.getParent().getType() == INTERFACE_DEF) {
            int size = mInInterface.size();
            return size > 1 && Boolean.TRUE.equals(mInInterface.get(size - 2));
        }
        else {
            return Boolean.TRUE.equals(mInInterface.peek());
        }
    }

}
