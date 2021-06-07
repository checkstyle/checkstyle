/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

import java.io.Serializable;

public class InputMissingOverrideGoodOverrideFromOther implements IFoo1
{
    public void doFoo() {}

    public void doFoo2() {}

}

interface IFoo1 {

    void doFoo();
}

interface IBar1 extends IFoo1 {

    public void doFoo();
}

class MoreJunk1 extends InputMissingOverrideGoodOverrideFromOther {

    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public void doFoo() {}

    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public void doFoo2() {}

    class EvenMoreJunk extends MoreJunk1 implements Serializable {

        /**
         * {@inheritDoc}
         */
        @Override       // ok
        public void doFoo() {}

        /**
         * {@inheritDoc}
         */
        @Override       // ok
        public void doFoo2() {}
    }

    class EvenMoreMoreJunk extends MoreJunk1 implements Serializable {

        /**
         * {@inheritDoc}
         */
        @java.lang.Override     // ok
        public void doFoo() {}

        /**
         * {@inheritDoc}
         */
        @java.lang.Override     // ok
        public void doFoo2() {}
    }
}

enum Football1 implements IFoo1, IBar1 {
    Detroit_Lions;

    public void doFoo() {}
}
