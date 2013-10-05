package com.puppycrawl.tools.checkstyle.annotation;

import java.lang.annotation.Inherited;

@Deprecated
public class BadDeprecatedJavadoc
{
    /**
     * @Deprecated this is not the same
     */
    @Deprecated
    protected BadDeprecatedJavadoc() {

    }

    @Deprecated
    @Override
    public String toString() {
        return "";
    }


    @Deprecated
    enum Rock {

        /**
         * 
         */
        @Bleh2(bleh=1)
        @Deprecated
        Metallica
    }
}

/**
 *
 */
@Deprecated
interface Foo2 {
    @Deprecated
    interface Bar {

    }
}

/**
 */
@Deprecated
@interface Bleh2 {

    /**
     * 
     * @return
     */
    @Deprecated
    int bleh();
}
