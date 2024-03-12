/*
GenericWhitespace


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

public class InputGenericWhitespaceBeforeRecordHeader {
    record Session<T>() {}
}
