/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

import java.util.Locale;

/** some javadoc. */
public record InputMissingOverrideRecordAccessor(String name, int age) {

    /** some javadoc. */
    public String name() {
// violation above, 'Method is a record accessor and must include @java.lang.Override annotation.'
        return name.toUpperCase(Locale.ROOT);
    }

    /** some javadoc. */
    public int age() {
// violation above, 'Method is a record accessor and must include @java.lang.Override annotation.'
        return age;
    }

    /** Not an accessor - different name. */
    public String getName() {
        return name;
    }

    /** Not an accessor - has parameters. */
    public String name(String prefix) {
        return prefix + name;
    }
}

record Container(String fileName, int port) {

    public int port() {
// violation above, 'Method is a record accessor and must include @java.lang.Override annotation.'
        return port;
    }

    public String fileName() {
// violation above, 'Method is a record accessor and must include @java.lang.Override annotation.'
        return fileName;
    }
}
