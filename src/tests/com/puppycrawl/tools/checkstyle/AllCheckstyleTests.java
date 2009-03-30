package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.annotation.AllAnnotationTests;

import com.puppycrawl.tools.checkstyle.api.AllApiTests;
import com.puppycrawl.tools.checkstyle.checks.AllChecksTests;
import com.puppycrawl.tools.checkstyle.checks.blocks.AllBlocksTests;
import com.puppycrawl.tools.checkstyle.checks.coding.AllCodingTests;
import com.puppycrawl.tools.checkstyle.checks.design.AllDesignTests;
import com.puppycrawl.tools.checkstyle.checks.duplicates.AllDuplicatesTests;
import com.puppycrawl.tools.checkstyle.checks.header.AllHeaderTests;
import com.puppycrawl.tools.checkstyle.checks.imports.AllImportsTests;
import com.puppycrawl.tools.checkstyle.checks.indentation.AllIndentationTests;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AllJavadocTests;
import com.puppycrawl.tools.checkstyle.checks.metrics.AllMetricsTests;
import com.puppycrawl.tools.checkstyle.checks.modifier.AllModifierTests;
import com.puppycrawl.tools.checkstyle.checks.naming.AllNamingTests;
import com.puppycrawl.tools.checkstyle.checks.regexp.AllRegexpTests;
import com.puppycrawl.tools.checkstyle.checks.sizes.AllSizesTests;
import com.puppycrawl.tools.checkstyle.checks.whitespace.AllWhitespaceTests;
import com.puppycrawl.tools.checkstyle.filters.AllFilterTests;
import com.puppycrawl.tools.checkstyle.grammars.AllGrammarTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {CheckerTest.class, ConfigurationLoaderTest.class,
    PackageNamesLoaderTest.class,
    PackageObjectFactoryTest.class, StringArrayReaderTest.class,
    UtilsTest.class, XMLLoggerTest.class, AllApiTests.class,
    AllChecksTests.class, AllBlocksTests.class, AllCodingTests.class,
    AllDesignTests.class, AllDuplicatesTests.class, AllHeaderTests.class,
    AllImportsTests.class, AllIndentationTests.class, AllJavadocTests.class,
    AllMetricsTests.class, AllModifierTests.class, AllNamingTests.class,
    AllSizesTests.class, AllWhitespaceTests.class, AllFilterTests.class,
    AllGrammarTests.class, AllRegexpTests.class, AllAnnotationTests.class})
public class AllCheckstyleTests
{
}
