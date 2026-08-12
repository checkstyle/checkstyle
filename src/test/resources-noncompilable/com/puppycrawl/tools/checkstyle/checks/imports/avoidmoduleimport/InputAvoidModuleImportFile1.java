/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = 2

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.sql;

import java.util.ArrayList;
import java.util.List;

public class InputAvoidModuleImportFile1 {

    public void method() {
        List<String> names = new ArrayList<>();
        names.add("foo");
        names.add("boo");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:test");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(names + " " + connection);
    }
}
