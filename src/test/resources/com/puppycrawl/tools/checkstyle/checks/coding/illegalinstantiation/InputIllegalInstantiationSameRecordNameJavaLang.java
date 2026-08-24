/*
IllegalInstantiation
classes = java.lang.InputTest
tokens = (default)CLASS_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationSameRecordNameJavaLang
{
    InputTest obj = new InputTest();
}
record InputTest() {
}
