/**
 * Module definition for Checkstyle.
 * <p>
 * This module is defined as {@code open} to allow reflective access to all
 * classes. This may be reduced to specific packages later.
 * </p>
 * <p>
 * All dependencies are defined as {@code transitive} because it is assumed
 * their types are used in the public API of this module. This may be reduced to
 * those dependencies used in the actual public API later.
 * </p>
 * <p>
 * It is assumed all packages are public API that shall be exported. This may be
 * reduced to those packages that actually contain public API.
 * </p>
 */
open module com.puppycrawl.tools.checkstyle {

    // JDK dependencies
    requires transitive java.logging;
    requires transitive java.xml;
    requires transitive java.desktop;

    // JDK dependencies for test classes (change project setup!)
    requires java.management;
    requires java.naming;
    requires java.compiler;
    requires java.xml.crypto;
    requires java.scripting;

    /*
     * Third party dependencies.
     * 
     * There are three types of dependencies:
     * 1. They define a module-info.class with a module name.
     * 2. They define an 'Automatic-Module-Name' in the 'MANIFEST.MF'.
     * 3. The module name is derived from their file name by JDK rules.
     * 
     * Find the name with: 'java --list-modules -p <directory-of-jar>'
     */
    requires transitive commons.beanutils;
    requires transitive commons.logging;
    requires transitive org.antlr.antlr4.runtime;
    requires transitive Saxon.HE;
    requires transitive org.reflections;
    requires transitive info.picocli;
    requires transitive doxia.core;
    requires transitive doxia.module.xdoc;
    requires transitive doxia.sink.api;
    requires transitive plexus.component.annotations;
    requires transitive plexus.utils;
    requires transitive ant;
    requires transitive com.google.common;
    requires transitive jsr305;

    // export public API
    exports com.puppycrawl.tools.checkstyle;
    exports com.puppycrawl.tools.checkstyle.ant;
    exports com.puppycrawl.tools.checkstyle.api;
    exports com.puppycrawl.tools.checkstyle.checks;
    exports com.puppycrawl.tools.checkstyle.checks.annotation;
    exports com.puppycrawl.tools.checkstyle.checks.blocks;
    exports com.puppycrawl.tools.checkstyle.checks.coding;
    exports com.puppycrawl.tools.checkstyle.checks.design;
    exports com.puppycrawl.tools.checkstyle.checks.header;
    exports com.puppycrawl.tools.checkstyle.checks.imports;
    exports com.puppycrawl.tools.checkstyle.checks.indentation;
    exports com.puppycrawl.tools.checkstyle.checks.javadoc;
    exports com.puppycrawl.tools.checkstyle.checks.javadoc.utils;
    exports com.puppycrawl.tools.checkstyle.checks.metrics;
    exports com.puppycrawl.tools.checkstyle.checks.modifier;
    exports com.puppycrawl.tools.checkstyle.checks.naming;
    exports com.puppycrawl.tools.checkstyle.checks.regexp;
    exports com.puppycrawl.tools.checkstyle.checks.sizes;
    exports com.puppycrawl.tools.checkstyle.checks.whitespace;
    exports com.puppycrawl.tools.checkstyle.filefilters;
    exports com.puppycrawl.tools.checkstyle.filters;
    exports com.puppycrawl.tools.checkstyle.grammar;
    exports com.puppycrawl.tools.checkstyle.gui;
    exports com.puppycrawl.tools.checkstyle.meta;
    exports com.puppycrawl.tools.checkstyle.site;
    exports com.puppycrawl.tools.checkstyle.utils;
    exports com.puppycrawl.tools.checkstyle.xpath;
    exports com.puppycrawl.tools.checkstyle.xpath.iterators;
}
