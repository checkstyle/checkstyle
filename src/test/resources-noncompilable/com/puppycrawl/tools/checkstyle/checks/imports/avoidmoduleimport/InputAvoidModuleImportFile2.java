/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = 2

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.net.http;
import module java.logging;

public class InputAvoidModuleImportFile2 {

    public void doSomething() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://example.org/foo"))
                .build();

        Logger logger = Logger.getLogger("Bar2");
        logger.info("request built: " + request);

        System.out.println(client + " " + request);
    }
}
