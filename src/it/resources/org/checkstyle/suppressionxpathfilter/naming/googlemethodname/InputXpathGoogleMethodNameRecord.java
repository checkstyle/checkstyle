/*
GoogleMethodName


*/

package org.checkstyle.suppressionxpathfilter.naming.googlemethodname;

public record InputXpathGoogleMethodNameRecord(String name) {

    void f() {} // violation "Method name 'f' must be more than a character"
}
