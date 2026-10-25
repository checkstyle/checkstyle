/*
UnnecessarySemicolonInTryWithResources
allowWhenNoBraceAfterSemicolon = false


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicolonintrywithresources;

import java.io.PipedReader;
import java.io.Reader;

public class InputXpathUnnecessarySemicolonInTryWithResourcesNoBrace {
    void test() throws Exception {
        try(Reader good = new PipedReader();){} // violation 'Unnecessary semicolon\.'
    }
}
