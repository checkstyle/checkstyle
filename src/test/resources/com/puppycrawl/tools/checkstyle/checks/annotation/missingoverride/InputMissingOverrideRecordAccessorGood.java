/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

import java.util.Locale;

/** some javadoc. */
public record InputMissingOverrideRecordAccessorGood(String name, int age) {

    /** some javadoc. */
    @Override
    public String name() {
        return name.toUpperCase(Locale.ROOT);
    }

    /** some javadoc. */
    @Override
    public int age() {
        return age;
    }
}

record GoodContainer(String fileName, int port) {

    @Override
    public int port() {
        return port;
    }

    @Override
    public String fileName() {
        return fileName;
    }
}
