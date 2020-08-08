//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation

/* Config:
 * allowNoEmptyLineBetweenFields = false
 * allowMultipleEmptyLines = true
 * allowMultipleEmptyLinesInsideClassMembers = false
 * tokens = {PACKAGE_DEF , IMPORT , STATIC_IMPORT , CLASS_DEF , INTERFACE_DEF , ENUM_DEF ,
 *  STATIC_INIT , INSTANCE_INIT , METHOD_DEF , CTOR_DEF , VARIABLE_DEF,
 *  RECORD_DEF, COMPACT_CTOR_DEF}
 *
 */
public class InputEmptyLineSeparatorRecordsAndCompactCtorsNoEmptyLines {
    public void foo() {


    } // ^ violation

    public record MyRecord1(){
        public MyRecord1{


        } // ^ violation
    }
}
