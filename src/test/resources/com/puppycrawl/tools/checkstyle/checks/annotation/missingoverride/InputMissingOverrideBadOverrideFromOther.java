/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

import java.io.Serializable;

public class InputMissingOverrideBadOverrideFromOther implements IFoo2
{
    /**
     * {@inheritDoc}
     */
    public void doFoo() {} // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.

    public void doFoo2() {}

}

interface IFoo2 {

    void doFoo();
}

interface IBar2 extends IFoo2 {

    /**
     * {@inheritDoc}
     */
    public void doFoo(); // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
}

class MoreJunk2 extends InputMissingOverrideBadOverrideFromOther {

    /**
     * {@inheritDoc}
     */
    public void doFoo() {} // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.

    /**
     * {@inheritDoc}
     */
    public void doFoo2() {} // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.

    class EvenMoreJunk extends MoreJunk2 implements Serializable {

        /**
         * {@inheritDoc}
         */
        public void doFoo() {} // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.

        /**
         * {@inheritDoc}
         */
        public void doFoo2() {} // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
    }
}

enum Football2 implements IFoo2, IBar2 {
    Detroit_Lions;

    /**
     * {@inheritDoc}
     */
    public void doFoo() {} // violation: Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
}
