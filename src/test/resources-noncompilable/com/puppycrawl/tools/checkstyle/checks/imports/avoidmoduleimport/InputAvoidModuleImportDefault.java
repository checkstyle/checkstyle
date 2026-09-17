/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = (default)0

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.base;
// violation above 'Using the 'import module' form of import should be avoided'
import module java.xml;
// violation above 'Using the 'import module' form of import should be avoided'
import module java.logging;
// violation above 'Using the 'import module' form of import should be avoided'

public class InputAvoidModuleImportDefault {

    public void log() {
        List<String> names = new ArrayList<>();
        names.add("foo");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            System.out.println(document);
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }

        Logger logger =
                Logger.getLogger("InputAvoidModuleImportDefault");
        logger.info("done: " + names);
    }
}
