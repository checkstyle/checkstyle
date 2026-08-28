///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Checkstyle module descriptor.
 *
 * <p>The module is declared {@code open} because Checkstyle is a reflection-driven tool:
 * modules are instantiated by name from user configuration, properties are set through
 * commons-beanutils, {@code checkstyle_packages.xml} is discovered via classloader resource
 * lookup and {@code messages*.properties} bundles are loaded from many packages. Per-package
 * {@code opens} clauses would have to enumerate every package that holds a resource and be
 * kept in sync forever; {@code open} covers all of them. Compile-time API visibility is still
 * governed by the {@code exports} below.
 */
open module com.puppycrawl.tools.checkstyle {

    requires java.desktop;
    requires java.logging;
    // XmlLoader / ConfigurationLoader expose org.xml.sax types in their public API.
    requires transitive java.xml;

    requires info.picocli;
    // Token / Parser appear in public signatures (CheckstyleParserErrorStrategy, JavadocNodeImpl).
    requires transitive org.antlr.antlr4.runtime;
    requires org.apache.commons.beanutils;
    requires org.apache.commons.logging;
    requires com.google.common;
    // org.reflections is an automatic module and cannot declare its own runtime dependency
    // on slf4j; without this the JVM never resolves org.slf4j and metadata scanning
    // (XmlMetaReader, used for SARIF output) fails with NoClassDefFoundError.
    requires org.reflections;
    requires org.slf4j;

    // AbstractNode in the exported xpath package implements Saxon's NodeInfo,
    // so Saxon types are part of Checkstyle's public API and must be readable by consumers.
    // Published Saxon-HE releases (up to and including 12.10 and 13.0) ship no
    // Automatic-Module-Name, so this is the filename-derived name. Saxonica has added
    // Automatic-Module-Name "net.sf.saxon" on their 12 and 13 branches,
    // see https://saxonica.plan.io/issues/7193 (rename here once a fixed release is adopted).
    requires transitive Saxon.HE;

    // Compile-time only annotations, not needed at runtime:
    // javax.annotation.Nullable (jsr305) and org.jspecify.annotations (via guava).
    requires static jsr305;
    requires static org.jspecify;

    // Only needed by the Ant task; the ant dependency is "provided" scope.
    requires static ant;

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
    exports com.puppycrawl.tools.checkstyle.gui;
    exports com.puppycrawl.tools.checkstyle.meta;
    exports com.puppycrawl.tools.checkstyle.utils;
    exports com.puppycrawl.tools.checkstyle.xpath;
    exports com.puppycrawl.tools.checkstyle.xpath.iterators;

}
