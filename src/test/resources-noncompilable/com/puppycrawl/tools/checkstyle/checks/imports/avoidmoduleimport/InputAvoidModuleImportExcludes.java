/*
AvoidModuleImport
excludes = java.sql, java.desktop
maxAllowedModuleImports = (default)0

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.net.http;
// violation above 'Using the 'import module' form of import should be avoided'
import module java.sql;
import module java.desktop;

public class InputAvoidModuleImportExcludes {

    public void test() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("X-Test", "value")
                .build();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:test");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Frame frame = new Frame();
        frame.setTitle("test");

        System.out.println(client + " " + request + " "
                + connection + " " + frame);
    }
}
