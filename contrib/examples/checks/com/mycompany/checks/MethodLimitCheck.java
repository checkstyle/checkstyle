package com.mycompany.checks;

import com.puppycrawl.tools.checkstyle.api.*;

public class MethodLimitCheck extends Check
{
    /** the maximum number of methods per class/interface */
    private int max = 30;

    /**
     * Give user a chance to configure max in the config file.
     * @param aMax the user specified maximum parsed from configuration property.
     */
    public void setMax(int aMax)
    {
        max = aMax;
    }

    /**
     * We are interested in CLASS_DEF and INTERFACE_DEF Tokens.
     * @see Check
     */
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
    }

    /**
     * @see Check
     */
    public void visitToken(DetailAST ast)
    {
        // the tree below a CLASS_DEF/INTERFACE_DEF looks like this:

        // CLASS_DEF
        //   MODIFIERS
        //   class name (IDENT token type)
        //   EXTENDS_CLAUSE
        //   IMPLEMENTS_CLAUSE
        //   OBJBLOCK
        //     {
        //     some other stuff like variable declarations etc.
        //     METHOD_DEF
        //     more stuff, the users might mix methods, variables, etc.
        //     METHOD_DEF
        //     ...and so on
        //     }

        // We use helper methods to navigate in the syntax tree

        // find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);

        // count the number of direct children of the OBJBLOCK
        // that are METHOD_DEFS
        int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);

        // report error if limit is reached
        if (methodDefs > max) {
            log(ast.getLineNo(), "too.many.methods", new Integer(max));
        }
    }
}
