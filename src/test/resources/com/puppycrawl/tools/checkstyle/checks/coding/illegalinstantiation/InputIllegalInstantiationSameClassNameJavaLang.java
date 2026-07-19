/*
IllegalInstantiation
classes = java.lang.InputTest
tokens = (default)IMPORT,LITERAL_NEW,PACKAGE_DEF,CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationSameClassNameJavaLang
{
    InputTest obj = new InputTest();
}
class InputTest {
}
