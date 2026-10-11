/*
IllegalInstantiation
classes = java.lang.InputTestRecord
tokens = (default)IMPORT,LITERAL_NEW,PACKAGE_DEF,CLASS_DEF,RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

public class InputIllegalInstantiationSameRecordNameJavaLang
{
    InputTestRecord obj = new InputTestRecord();
}
record InputTestRecord() {
}
