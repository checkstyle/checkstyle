/*
IllegalInstantiation
classes = java.lang.InputTest
tokens = (default)CLASS_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationSameClassNameJavaLang // ok
{
    InputTest obj = new InputTest();
}
class InputTest {
}
