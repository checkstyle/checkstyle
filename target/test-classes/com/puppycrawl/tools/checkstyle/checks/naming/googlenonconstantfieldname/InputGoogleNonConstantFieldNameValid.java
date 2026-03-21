/*
GoogleNonConstantFieldName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlenonconstantfieldname;

/** Test valid non-constant field names. */
public class InputGoogleNonConstantFieldNameValid {

    int foo;
    public int fooBar;
    protected int fooBarBaz;
    private int foo123;
    int foo1Bar2;

    // With valid numbering suffix (underscore between digits only)
    final int guava33_4_5 = 0;
    public final int guavaVersion33_4_5 = 0;
    private final int guavaversion33_4_5 = 0;
    protected final int foo1_2 = 0;
    int foo123_456_789;
    int gradle9_5_1;
    int jdk17_0_1;

    int parseUrl;
    int getUrl;
    final int processXmlData = 0;
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

    int i18n;
    int l10n;
    int y2k;

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
