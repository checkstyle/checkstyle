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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;

import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.utils.InlineConfigUtils.MatchedDelimiter;

public class InlineConfigUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(InlineConfigUtils.class.getDeclaredConstructor().getModifiers()
                        & Modifier.PRIVATE)
                .isNotEqualTo(0);
    }

    @Test
    public void testMatchDelimiterEmptyLines() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of(), "Example.java");

        assertWithMessage("Result should be null for empty lines")
                .that(result)
                .isNull();
    }

    @Test
    public void testMatchDelimiterJavaXmlStyle() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("/*xml", "content", "*/"), "Example.java");

        assertWithMessage("Should match java xml-style config")
                .that(result)
                .isEqualTo(new MatchedDelimiter(InlineConfigUtils.JAVA_CONFIG_END, true));
    }

    @Test
    public void testMatchDelimiterJavaKeyValueStyle() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("/*", "ModuleName", "*/"), "Example.java");

        assertWithMessage("Should match java key-value style config")
                .that(result)
                .isEqualTo(new MatchedDelimiter(InlineConfigUtils.JAVA_CONFIG_END, false));
    }

    @Test
    public void testMatchDelimiterXmlTargetStyle() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("<!--xml", "content", "-->"), "Example.xml");

        assertWithMessage("Should match xml target style config")
                .that(result)
                .isEqualTo(new MatchedDelimiter(InlineConfigUtils.XML_TARGET_CONFIG_END, true));
    }

    @Test
    public void testMatchDelimiterXmlFileWithoutXmlMarkerFallsThroughToNull() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("<project>", "content"), "Example.xml");

        assertWithMessage("Should not match when first line is neither convention")
                .that(result)
                .isNull();
    }

    @Test
    public void testMatchDelimiterPropertiesTargetStyle() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("#xml", "content", "#/xml"), "Example.properties");

        assertWithMessage("Should match properties target style config")
                .that(result)
                .isEqualTo(new MatchedDelimiter(InlineConfigUtils.PROPERTIES_CONFIG_END, true));
    }

    @Test
    public void testMatchDelimiterPropertiesFileWithoutMarkerFallsThroughToNull() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("key=value"), "Example.properties");

        assertWithMessage("Should not match when first line is neither convention")
                .that(result)
                .isNull();
    }

    @Test
    public void testMatchDelimiterSqlFileUsesJavaConvention() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("/*xml", "content", "*/"), "Example.sql");

        assertWithMessage("Sql files should use the java convention")
                .that(result)
                .isEqualTo(new MatchedDelimiter(InlineConfigUtils.JAVA_CONFIG_END, true));
    }

    @Test
    public void testDescribeExpectedDelimitersJavaFile() {
        final String result = InlineConfigUtils.describeExpectedDelimiters("Example.java");

        assertWithMessage("Description should mention java convention only")
                .that(result)
                .isEqualTo("\"/*xml\" or \"/*\" (Java-comment style)");
    }

    @Test
    public void testDescribeExpectedDelimitersXmlFile() {
        final String result = InlineConfigUtils.describeExpectedDelimiters("Example.xml");

        assertWithMessage("Description should mention xml convention")
                .that(result)
                .isEqualTo("\"/*xml\" or \"/*\" (Java-comment style), or \"<!--xml\""
                    + " (XML-comment style)");
    }

    @Test
    public void testDescribeExpectedDelimitersPropertiesFile() {
        final String result = InlineConfigUtils.describeExpectedDelimiters("Example.properties");

        assertWithMessage("Description should mention properties convention")
                .that(result)
                .isEqualTo("\"/*xml\" or \"/*\" (Java-comment style), or \"#xml\""
                    + " (properties-comment style)");
    }

    @Test
    public void testStripPropertiesCommentPrefixWithHash() {
        final List<String> result = InlineConfigUtils.stripPropertiesCommentPrefix(
                List.of("#<module name=\"Checker\">", "#</module>"));

        assertWithMessage("Leading hash should be stripped")
                .that(result)
                .containsExactly("<module name=\"Checker\">", "</module>")
                .inOrder();
    }

    @Test
    public void testStripPropertiesCommentPrefixWithoutHash() {
        final List<String> result = InlineConfigUtils.stripPropertiesCommentPrefix(
                List.of("", "not a comment"));

        assertWithMessage("Lines without a leading hash should pass through unchanged")
                .that(result)
                .containsExactly("", "not a comment")
                .inOrder();
    }

    @Test
    public void testMatchDelimiterXmlMarkerIgnoredForNonXmlExtension() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("<!--xml", "content", "-->"), "Example.properties");

        assertWithMessage("XML marker should not match when file extension is not .xml")
                .that(result)
                .isNull();
    }

    @Test
    public void testMatchDelimiterPropertiesMarkerIgnoredForNonPropertiesExtension() {
        final MatchedDelimiter result = InlineConfigUtils.matchDelimiter(
                List.of("#xml", "content", "#/xml"), "Example.xml");

        assertWithMessage("Properties marker should not match when file extension"
                + " is not .properties")
                .that(result)
                .isNull();
    }

}
