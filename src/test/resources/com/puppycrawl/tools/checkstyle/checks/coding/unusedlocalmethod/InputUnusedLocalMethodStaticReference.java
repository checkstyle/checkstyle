/*
UnusedLocalMethod

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalmethod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class InputUnusedLocalMethodStaticReference {

    public void test() throws Exception {
        try (Stream<Path> fileStream = Files.walk(Path.of(""))) {
            fileStream.map(InputUnusedLocalMethodStaticReference::getMetaFileName).sorted();
        }
    }

    private static String getMetaFileName(Path file) {
        return "null";
    }
}
