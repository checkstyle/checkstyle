/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

import java.io.Serializable;

public class InputMissingOverrideGoodOverrideFromOtherJava5 implements IFoo1Java5
{
    public void doFoo() {}

    public void doFoo2() {}

}

interface IFoo1Java5 {

    void doFoo();
}

interface IBar1Java5 extends IFoo1Java5 {

    public void doFoo();
}

class MoreJunk1Java5 extends InputMissingOverrideGoodOverrideFromOtherJava5 {

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

    class EvenMoreJunk extends MoreJunk1Java5 implements Serializable {

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

    class EvenMoreMoreJunk extends MoreJunk1Java5 implements Serializable {

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

enum Football1Java5 implements IFoo1Java5, IBar1Java5 {
    Detroit_Lions;

    public void doFoo() {}
}
