/*
UnnecessarySemicolonInTryWithResources
allowWhenNoBraceAfterSemicolon = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonintrywithresources;

import java.io.IOException;
import java.io.PipedReader;
import java.io.Reader;

public class InputUnnecessarySemicolonInTryWithResourcesDefault {

    void method() throws IOException {
        try(Reader r1 = new PipedReader();){} // violation
        try(Reader r1 = new PipedReader();Reader r2 = new PipedReader();){} // violation
        try(Reader r2 = new PipedReader();Reader r3 = new PipedReader()){}
        try(Reader r4 = new PipedReader();
            Reader r5 = new PipedReader();
            ){}
        try(Reader r6 = new PipedReader();
            Reader r7
                = new PipedReader();
            ){}

    }
}
