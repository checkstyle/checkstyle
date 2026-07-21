/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = (default)
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

import org.junit.Before;

abstract class InputAbbreviationAsWordInNameOverridableMethod2 extends Class1 {
    // violation below 'Abbreviation in name 'serialNUMBER''
    public int serialNUMBER = 6;
    public final int s1erialNUMBER = 6;
    private static int s2erialNUMBER = 6;
    private static final int s3erialNUMBER = 6;

    // violation 4 lines below 'Abbreviation in name 'overRIDDENMethod''
    @Override
    @SuppressWarnings(value = { "" })
    @Before
    protected void overRIDDENMethod(){
        int a = 0;
        // blah-blah
    }
}

class Class12 {
    // violation 2 lines below 'Abbreviation in name 'overRIDDENMethod''
    @SuppressWarnings(value = { "" })
    protected void overRIDDENMethod(){
        int a = 0;
        // blah-blah
    }

}

class Class22 extends Class1 {

    // violation 4 lines below 'Abbreviation in name 'overRIDDENMethod''
    @Override
    @SuppressWarnings(value = { "" })
    @Before
    protected void overRIDDENMethod(){
        int a = 0;
        // blah-blah
    }

}
