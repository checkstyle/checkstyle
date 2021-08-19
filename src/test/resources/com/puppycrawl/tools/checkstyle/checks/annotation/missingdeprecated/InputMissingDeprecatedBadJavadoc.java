/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

import java.lang.annotation.Inherited;

@Deprecated
public class InputMissingDeprecatedBadJavadoc
{
    /**
     * @Deprecated this is not the same
     */
    @Deprecated
    protected InputMissingDeprecatedBadJavadoc() { // violation above

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
        @Bleh2(bleh=1) // violation
        @Deprecated
        Metallica
    }
}

/**
 *
 */
@Deprecated
interface Foo2 { // violation above
    @Deprecated
    interface Bar {

    }
}

/**
 */
@Deprecated
@interface Bleh2 { // violation above

    /**
     *
     * @return
     */
    @Deprecated
    int bleh(); // violation above
}
