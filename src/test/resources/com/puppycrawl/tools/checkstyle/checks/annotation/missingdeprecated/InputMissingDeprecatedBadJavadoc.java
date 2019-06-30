package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

import java.lang.annotation.Inherited;

@Deprecated
public class InputMissingDeprecatedBadJavadoc
{
    /**
     * @Deprecated this is not the same
     */
    @Deprecated
    protected InputMissingDeprecatedBadJavadoc() {

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
