/*
IllegalInstantiation
classes = java.lang.InputTestRecord
tokens = (default)CLASS_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationSameRecordNameJavaLang
{
    InputTestRecord obj = new InputTestRecord();
}
record InputTestRecord() {
}
