package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicolonintrywithresources;

import java.io.PipedReader;
import java.io.Reader;

public class InputXpathUnnecessarySemicolonInTryWithResourcesInnerClass {
    class Inner {
        void test() throws Exception {
            try(Reader reader = new PipedReader();){} // warn
        }
    }
}
