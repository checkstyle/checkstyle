/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

import java.io.Serializable;

public class InputMissingOverrideBadOverrideFromOtherJava5 implements IFoo2Java5
{
    /**
     * {@inheritDoc}
     */
    public void doFoo() {}      // violation

    public void doFoo2() {}

}

interface IFoo2Java5 {

    void doFoo();
}

interface IBar2Java5 extends IFoo2Java5 {

    /**
     * {@inheritDoc}
     */
    public void doFoo();        // violation
}

class MoreJunk2Java5 extends InputMissingOverrideBadOverrideFromOtherJava5 {

    /**
     * {@inheritDoc}
     */
    public void doFoo() {}      // violation

    /**
     * {@inheritDoc}
     */
    public void doFoo2() {}     // violation

    class EvenMoreJunk2 extends MoreJunk2Java5 implements Serializable {

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

enum Football2Java5 implements IFoo2Java5, IBar2Java5 {
    Detroit_Lions;

    /**
     * {@inheritDoc}
     */
    public void doFoo() {}      // violation
}
