/*
WhitespaceAfter
tokens = ANNOTATIONS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputWhitespaceAfterUnicodeAnnotations {
    String @Ascii [] asciiSpace;
    String @Ascii[] asciiNoSpace; // violation ''Ascii' is not followed by whitespace'
    String @É [] bmpSpace;
    String @É[] bmpNoSpace; // violation ''É' is not followed by whitespace'
    String @𝒜 [] supplementarySpace;
    String @𝒜[] supplementaryNoSpace; // violation ''𝒜' is not followed by whitespace'
    String @𝒜𝒜 [] multipleSpace;
    String @𝒜𝒜[] multipleNoSpace; // violation ''𝒜𝒜' is not followed by whitespace'

    String @InputWhitespaceAfterUnicodeAnnotations.𝒜 [] qualifiedSpace;
    String @InputWhitespaceAfterUnicodeAnnotations.𝒜𝒜[] qualifiedNoSpace;
    // violation above ''𝒜𝒜' is not followed by whitespace'

    void varargsWithSpace(Object @𝒜 ... values) {}

    void varargsWithoutSpace(Object @𝒜... values) {}
    // violation above ''𝒜' is not followed by whitespace'

    @Target(ElementType.TYPE_USE)
    @interface Ascii {}

    @Target(ElementType.TYPE_USE)
    @interface É {}

    @Target(ElementType.TYPE_USE)
    @interface 𝒜 {}

    @Target(ElementType.TYPE_USE)
    @interface 𝒜𝒜 {}
}
