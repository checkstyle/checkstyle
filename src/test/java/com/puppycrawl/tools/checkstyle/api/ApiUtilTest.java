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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.io.File;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ApiUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor must be private")
                .that(isUtilsClassHasPrivateConstructor(ApiUtil.class))
                .isTrue();
    }

    @MethodSource("fileExtensionCases")
    @ParameterizedTest
    public final void testMatchesFileExtension(String fileName, String[] extensions,
                                               boolean expected) {
        final File file = new File(fileName);
        assertWithMessage("File extension match for %s", fileName)
                .that(ApiUtil.matchesFileExtension(file, extensions))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> fileExtensionCases() {
        return Stream.of(
                Arguments.of("file.txt", null, true),
                Arguments.of("file.txt", new String[0], true),
                Arguments.of("file.java", new String[] {"java"}, true),
                Arguments.of("file.java", new String[] {".java"}, true),
                Arguments.of("file.txt", new String[] {"java"}, false),
                Arguments.of("file.java", new String[] {"txt", "java"}, true),
                Arguments.of("file.java", new String[] {"java", "txt"}, true),
                Arguments.of("file.java", new String[] {"JAVA"}, false),
                Arguments.of("archive.tar.gz", new String[] {"tar.gz"}, true),
                Arguments.of("archive.gz", new String[] {"tar.gz"}, false),
                Arguments.of("file.", new String[] {""}, true),
                Arguments.of("file", new String[] {""}, false),
                Arguments.of("filejava", new String[] {"java"}, false),
                Arguments.of("parent.java/file.txt", new String[] {"java"}, false),
                Arguments.of("parent.txt/file.java", new String[] {"java"}, true));
    }

    @Test
    public void testUnrestrictedExtensionsDoNotReadFile() {
        assertWithMessage("Null filter must accept without reading the file")
                .that(ApiUtil.matchesFileExtension(null, (String[]) null))
                .isTrue();
        assertWithMessage("Empty filter must accept without reading the file")
                .that(ApiUtil.matchesFileExtension(null))
                .isTrue();
    }

    @Test
    public void testExtensionsAreNotModified() {
        final String[] extensions = {"txt", ".java"};
        ApiUtil.matchesFileExtension(new File("file.java"), extensions);
        assertWithMessage("Normalization must leave the supplied array unchanged")
                .that(extensions)
                .asList()
                .containsExactly("txt", ".java")
                .inOrder();
    }

    @Test
    public void testAllExtensionsAreNormalizedBeforeMatching() {
        final NullPointerException exception = getExpectedThrowable(NullPointerException.class,
                () -> ApiUtil.matchesFileExtension(new File("file.java"), "java", null));
        assertWithMessage("Normalization must reject null even after a matching extension")
                .that(exception.getClass())
                .isEqualTo(NullPointerException.class);
    }

    @MethodSource("expandedTabCases")
    @ParameterizedTest
    public final void testLengthExpandedTabs(String line, int toIndex, int tabWidth,
                                             int expected) {
        assertWithMessage("Expanded length for prefix %s and tab width %s", toIndex, tabWidth)
                .that(ApiUtil.lengthExpandedTabs(line, toIndex, tabWidth))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> expandedTabCases() {
        return Stream.of(
                Arguments.of("", 0, 8, 0),
                Arguments.of("abc", 3, 8, 3),
                Arguments.of("\t", 1, 8, 8),
                Arguments.of("  \t", 3, 8, 8),
                Arguments.of("\t\t", 2, 8, 16),
                Arguments.of(" \t ", 3, 8, 9),
                Arguments.of("abcd\t", 5, 4, 8),
                Arguments.of("a\tb", 2, 4, 4),
                Arguments.of("a\tb", 3, 4, 5),
                Arguments.of("a\tb", 1, 4, 1),
                Arguments.of("\t", 1, 1, 1),
                // Preserve the historical UTF-16 index semantics for supplementary characters.
                Arguments.of("\ud83d\ude00\t", 3, 4, 4),
                Arguments.of("\ud83d\ude00", 2, 8, 2));
    }

}
