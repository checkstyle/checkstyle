package org.checkstyle.suppressionxpathfilter.naming.googlemethodname;

public record InputXpathGoogleMethodNameRecord(String name) {

    void f() {} // warn 'Method name 'f' must start with a lowercase letter, min 2 chars'
}
