/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import static java.lang.String.format; // violation
import java.util.Objects; // ok
import java.util.Optional; //ok

public class InputUnusedImportsFromStaticMethodRef {

    InputUnusedImportsFromStaticMethodRef() {
    }

    void testMethodRef()
    {
        Optional<String> test = Optional.empty();
        test.map(String::format);
    }

    void testMethodRefWithClass()
    {
        Optional<String> test = Optional.empty();
        test.map(Objects::nonNull);
    }

}
