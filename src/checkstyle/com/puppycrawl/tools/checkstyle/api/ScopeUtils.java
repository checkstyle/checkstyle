package com.puppycrawl.tools.checkstyle.api;

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.Scope;

/**
 * Contains utility methods for working on scope.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class ScopeUtils
{
    /**
     * Returns the Scope specified by the modifier set.
     *
     * @param aMods root node of a modifier set
     * @return a <code>Scope</code> value
     */
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
                    token.findFirstToken(TokenTypes.MODIFIERS);
                final Scope modScope = ScopeUtils.getScopeFromMods(mods);
                if ((retVal == null) || (retVal.isIn(modScope))) {
                    retVal = modScope;
                }
            }
        }

        return retVal;
    }

    /**
     * Returns whether a node is directly contained within an interface block.
     *
     * @param aAST the node to check if directly contained within an interface
     * block
     * @return a <code>boolean</code> value
     */
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

    /**
     * Returns whether a node is contained within a code block. A code block
     * is a method or constructor body, or a initialiser block.
     *
     * @param aAST the node to check
     * @return a <code>boolean</code> value
     */
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

    /**
     * Returns whether a node is contained in the outer most type block.
     *
     * @param aAST the node to check
     * @return a <code>boolean</code> value
     */
    public static boolean isOuterMostType(DetailAST aAST)
    {
        boolean retVal = true;
        for (DetailAST parent = aAST.getParent();
             parent != null;
             parent = parent.getParent())
        {
            if ((parent.getType() == TokenTypes.CLASS_DEF)
                || (parent.getType() == TokenTypes.INTERFACE_DEF))
            {
                retVal = false;
                break;
            }
        }

        return retVal;
    }
}
