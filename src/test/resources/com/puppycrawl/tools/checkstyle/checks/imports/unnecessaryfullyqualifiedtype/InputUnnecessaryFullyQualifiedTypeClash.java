/*
UnnecessaryFullyQualifiedType


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unnecessaryfullyqualifiedtype;

import java.util.Map;

public class InputUnnecessaryFullyQualifiedTypeClash {

    private java.sql.Date sqlDate;

    private java.util.Date utilDate;

    private Map<String, String> importedMap;

    private com.example.Map customMap;

    private java.util.List list;

    static class List {
    }
}
