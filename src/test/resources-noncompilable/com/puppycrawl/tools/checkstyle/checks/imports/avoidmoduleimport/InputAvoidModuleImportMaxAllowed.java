/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = 2

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.base;
import module java.net.http;
import module java.desktop;
// violation above 'Only '2' module import is allowed per file.'

public class InputAvoidModuleImportMaxAllowed {

    public void doSomething() {
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("foo", 42);
        inventory.put("bar", 17);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.org/foo"))
                .build();

        Frame frame = new Frame();
        frame.setTitle("Foo Tracker");
        Label label = new Label("foo: " + inventory.get("foo"));
        frame.add(label);

        System.out.println(client + " " + request + " " + frame);
    }
}
