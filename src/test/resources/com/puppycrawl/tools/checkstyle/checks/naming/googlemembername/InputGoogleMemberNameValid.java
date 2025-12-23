/*
GoogleMemberName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemembername;

/** Test valid member names. */
public class InputGoogleMemberNameValid {

    // Standard lowerCamelCase
    int foo;
    int fooBar;
    int fooBarBaz;
    int foo123;
    int foo1Bar2;

    // With valid numbering suffix (underscore between digits only)
    int guava33_4_5;
    int guavaVersion33_4_5;
    int guavaversion33_4_5;
    int foo1_2;
    int foo123_456_789;
    int gradle9_5_1;
    int jdk17_0_1;

    int parseUrl;
    int getUrl;
    int processXmlData;
    int parseJson;
    int loadHttpConfig;

    int ab;
    int abC;
    int a1;
    int a1B2;
    int version1_2_3;

    // Various access modifiers
    public int publicField;
    protected int protectedField;
    private int privateField;
    int packagePrivateField;

    // Two-letter valid names
    int id;
    int db;
    int ui;
    int ok;
    int ip;
    int io;

    // Single letter + digit (valid because not single char alone)
    int x1;
    int y2;
    int a1b;
    int x1y2;

    // Abbreviation patterns
    int i18n;
    int l10n;
    int y2k;

    // Abbreviations in camelCase
    int myHttpServer;
    int xmlParser;
    int jsonData;
    int httpUrl;
    int tcpIpConnection;

    int version2_0;
    int result99_1;
    int myField1_2_3_4_5;
    int test123_456_789_0;
}
