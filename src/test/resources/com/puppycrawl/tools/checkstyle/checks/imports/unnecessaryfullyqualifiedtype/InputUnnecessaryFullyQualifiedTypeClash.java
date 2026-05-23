/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

public class InputUnnecessaryFullyQualifiedTypeClash {

    // two different 'Date' types are used fully qualified, so both must stay qualified
    private java.sql.Date sqlDate;

    private java.util.Date utilDate;

    // 'java.util.List' is kept because a local type named 'List' is also declared
    private java.util.List list;

    static class List {
    }
}
