package com.puppycrawl.tools.checkstyle.api;

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.Scope;

public class ScopeUtils {
    public static Scope getScopeFromMods(DetailAST aMods)
    {
        Scope retVal = Scope.PACKAGE; // default scope
        for (AST token = aMods.getFirstChild();
            token != null;
            token = token.getNextSibling())
        {
            if ("public".equals(token.getText())) {
                retVal = Scope.PUBLIC;
                break;
            }
            else if ("protected".equals(token.getText())) {
                retVal = Scope.PROTECTED;
                break;
            }
            else if ("private".equals(token.getText())) {
                retVal = Scope.PRIVATE;
                break;
            }
        }
        return retVal;
    }

    /**
     * Returns the scope of the surrounding "block".
     * @param aAST the node to return the scope for
     * @return the Scope of the surrounding block
     */
    public static Scope getSurroundingScope(DetailAST aAST)
    {
        Scope retVal = null;
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent())
        {
            final int type = token.getType();
            if ((type == TokenTypes.CLASS_DEF)
                || (type == TokenTypes.INTERFACE_DEF))
            {
                final DetailAST mods =
                    Utils.findFirstToken(token.getFirstChild(),
                                         TokenTypes.MODIFIERS);
                final Scope modScope = ScopeUtils.getScopeFromMods(mods);
                if ((retVal == null) || (retVal.isIn(modScope))) {
                    retVal = modScope;
                }
            }
        }
    
        return retVal;
    }

    public static boolean inInterfaceBlock(DetailAST aAST)
    {
        boolean retVal = false;
    
        // Loop up looking for a containing interface block
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent())
        {
            final int type = token.getType();
            if (type == TokenTypes.CLASS_DEF) {
                break; // in a class
            }
            else if (type == TokenTypes.INTERFACE_DEF) {
                retVal = true;
                break;
            }
        }
    
        return retVal;
    }

    public static boolean inCodeBlock(DetailAST aAST)
    {
        boolean retVal = false;
    
        // Loop up looking for a containing code block
        for (DetailAST token = aAST.getParent();
             token != null;
             token = token.getParent())
        {
            final int type = token.getType();
            if ((type == TokenTypes.METHOD_DEF)
                || (type == TokenTypes.CTOR_DEF)
                || (type == TokenTypes.INSTANCE_INIT)
                || (type == TokenTypes.STATIC_INIT))
            {
                retVal = true;
                break;
            }
        }
    
        return retVal;
    }
}
