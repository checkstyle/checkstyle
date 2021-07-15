package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.Serializable;

public class InputAntlr4AstRegressionBadOverride {
   /**
     * {@inheritDoc}
     */
    public void doFoo() {}      // violation

    public void doFoo2() {}

}

interface IFoo2 {

    void doFoo();
}

interface IBar2 extends IFoo2 {

    /**
     * {@inheritDoc}
     */
    public void doFoo();        // violation
}

class MoreJunk2 extends InputAntlr4AstRegressionBadOverride {

    /**
     * {@inheritDoc}
     */
    public void doFoo() {}      // violation

    /**
     * {@inheritDoc}
     */
    public void doFoo2() {}     // violation

    class EvenMoreJunk extends MoreJunk2 implements Serializable {

        /**
         * {@inheritDoc}
         */
        public void doFoo() {}      // violation

        /**
         * {@inheritDoc}
         */
        public void doFoo2() {}     // violation
    }
}

enum Football2 implements IFoo2, IBar2 {
    Detroit_Lions;

    /**
     * {@inheritDoc}
     */
    public void doFoo() {}      // violation
}
