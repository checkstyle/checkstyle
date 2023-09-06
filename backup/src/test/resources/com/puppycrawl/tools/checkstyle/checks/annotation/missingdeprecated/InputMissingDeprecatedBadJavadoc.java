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
    @Deprecated // violation '.*@java.lang.Deprecated annotation and @deprecated.*description.'
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
        @Bleh2(bleh=1) // violation '.*@java.lang.Deprecated annotation.*@deprecated.*description.'
        @Deprecated
        Metallica
    }
}

/**
 *
 */
@Deprecated // violation '.*@java.lang.Deprecated annotation and @deprecated.*description.'
interface Foo2 {
    @Deprecated
    interface Bar {

    }
}

/**
 */
@Deprecated // violation '.*@java.lang.Deprecated annotation and @deprecated.*description.'
@interface Bleh2 {

    /**
     *
     * @return
     */
    @Deprecated // violation '.*@java.lang.Deprecated annotation and @deprecated.*description.'
    int bleh();
}
