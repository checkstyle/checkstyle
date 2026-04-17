open module com.puppycrawl.tools.checkstyle {

    requires java.xml;
    requires java.desktop;
    requires java.logging;

    requires info.picocli;
    requires org.antlr.antlr4.runtime;
    requires org.apache.commons.beanutils;
    requires com.google.common;
    requires org.reflections;
    requires Saxon.HE;

    requires static ant;

    exports com.puppycrawl.tools.checkstyle;
    exports com.puppycrawl.tools.checkstyle.api;
    exports com.puppycrawl.tools.checkstyle.ant;
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
    exports com.puppycrawl.tools.checkstyle.gui;
    exports com.puppycrawl.tools.checkstyle.utils;
    exports com.puppycrawl.tools.checkstyle.xpath;
    exports com.puppycrawl.tools.checkstyle.xpath.iterators;

}
