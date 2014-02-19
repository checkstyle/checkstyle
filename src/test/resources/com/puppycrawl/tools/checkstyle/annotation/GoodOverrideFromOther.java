package com.puppycrawl.tools.checkstyle.annotation;

import java.io.Serializable;

public class GoodOverrideFromOther implements IFoo
{
    public void doFoo() { }

    public void doFoo2() { }

}

interface IFoo {

    void doFoo();
}

interface IBar extends IFoo {

    public void doFoo();
}

class MoreJunk extends GoodOverrideFromOther {

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFoo() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFoo2() { }

    class EvenMoreJunk extends MoreJunk implements Serializable {

        /**
         * {@inheritDoc}
         */
        @Override
        public void doFoo() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void doFoo2() { }
    }
    
    class EvenMoreMoreJunk extends MoreJunk implements Serializable {

        /**
         * {@inheritDoc}
         */
        @java.lang.Override
        public void doFoo() {
        }

        /**
         * {@inheritDoc}
         */
        @java.lang.Override
        public void doFoo2() { }
    }
}

enum Football implements IFoo, IBar {
    Detroit_Lions;

    public void doFoo()
    {

    }
}
