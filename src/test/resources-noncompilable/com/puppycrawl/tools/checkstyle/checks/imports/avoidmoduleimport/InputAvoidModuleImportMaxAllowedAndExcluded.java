/*
AvoidModuleImport
excludes = java.desktop
maxAllowedModuleImports = 1

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.base;
import module java.xml;
// violation above 'Only '1' module import is allowed per file.'
import module java.desktop;

public class InputAvoidModuleImportMaxAllowedAndExcluded {

    public void doSomething() {
        Set<String> tags = new TreeSet<>();
        tags.add("one");
        tags.add("two");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            System.out.println(document);
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }

        Button button = new Button("Submit");
        Panel panel = new Panel();
        panel.add(button);

        System.out.println(tags + " " + panel);
    }
}
