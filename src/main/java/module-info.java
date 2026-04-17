module com.puppycrawl.tools.checkstyle {

    requires java.xml;
    requires java.desktop;
    requires java.logging;
    requires java.naming;
    requires java.xml.crypto;

    requires info.picocli;
    requires org.antlr.antlr4.runtime;
    requires org.apache.commons.beanutils;
    requires commons.logging;
    requires com.google.common;
    requires org.jspecify;
    requires org.reflections;
    requires Saxon.HE;

    requires static ant;
    opens com.puppycrawl.tools.checkstyle to org.apache.commons.beanutils;
    opens com.puppycrawl.tools.checkstyle.checks to org.apache.commons.beanutils;
    opens com.puppycrawl.tools.checkstyle.api to org.apache.commons.beanutils;

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
