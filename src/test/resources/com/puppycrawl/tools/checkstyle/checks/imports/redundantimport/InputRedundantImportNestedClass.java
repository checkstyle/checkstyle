/*
RedundantImport


*/

package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.InputRedundantImportNestedClassOuter.Inner;
// violation above 'Redundant import from the same package'
import com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.InputRedundantImportNestedClassOuter.Inner.Deep;
// violation above 'Redundant import from the same package'
import java.lang.Thread.State; // violation 'Redundant import from the java.lang package'
import java.lang.invoke.MethodHandles.Lookup;

public class InputRedundantImportNestedClass {

    private Inner inner;

    private Inner.Deep deep;

    private State state;

    private Lookup lookup;

}

class InputRedundantImportNestedClassOuter {

    static class Inner {

        static class Deep {
        }

    }

}
