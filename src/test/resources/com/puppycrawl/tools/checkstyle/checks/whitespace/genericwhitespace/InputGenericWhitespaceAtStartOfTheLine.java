/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.util.Collections;

class InputGenericWhitespaceAtStartOfTheLine {

    public String getConstructor(Class<?>... parameterTypes)
    {
        Collections.<Object
 >emptySet(); // violation ''>' is preceded with whitespace.'
        Collections.
 <Object>emptySet(); // violation ''<' is preceded with whitespace.'
        return "pitest makes me cry";
    }
}
