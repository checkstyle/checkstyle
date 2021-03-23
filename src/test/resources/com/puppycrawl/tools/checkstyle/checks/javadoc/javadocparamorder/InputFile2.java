package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Config: default
 * Some Javadoc.
 *
 * @since 8.36 // ok
 */
class InputFile2 {

    /**
     * Description.
     *
     * @version Some javadoc. // OK
     * @param p2
     *            Parameter 2 desc. // violation
     * @param p1
     *            Parameter 1 desc.
     * @param <T>
     *            Parameter T desc. // violation
     * @see Some javadoc. // OK
     * @param p3
     *            Parameter 3 desc.
     * @return Some number. // OK
     */
    public <T> int test(String p1, String p2, T p3) {
        return 2021;
    }

    /**
     * xyz
     */
    public void test2() {
    }

}
