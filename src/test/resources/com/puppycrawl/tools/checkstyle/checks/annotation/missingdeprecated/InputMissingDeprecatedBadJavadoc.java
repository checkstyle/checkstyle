/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

import java.lang.annotation.Inherited;

@Deprecated
public class InputMissingDeprecatedBadJavadoc
{

    @Deprecated
    @Override
    public String toString() {
        return "";
    }


    @Deprecated
    enum Rock {

        // violation 4 lines below '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
        /**
         *
         */
        @Bleh2(bleh=1)
        @Deprecated
        Metallica
    }
}

// violation 4 lines below '.*@java.lang.Deprecated annotation and @deprecated.*description.'
/**
 *
 */
@Deprecated
interface Foo2 {
    @Deprecated
    interface Bar {

    }
}

// violation 3 lines below '.*@java.lang.Deprecated annotation and @deprecated.*description.'
/**
 */
@Deprecated
@interface Bleh2 {

    // violation 5 lines below '.*@java.lang.Deprecated annotation and @deprecated.*description.'
    /**
     *
     * @return
     */
    @Deprecated
    int bleh();
}
