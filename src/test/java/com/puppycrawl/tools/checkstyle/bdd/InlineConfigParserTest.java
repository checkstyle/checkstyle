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

package com.puppycrawl.tools.checkstyle.bdd;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InlineConfigParserTest {

    private static final Path INPUT_FILE = Path.of(
        "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/nolinewrap/"
            + "InputNoLineWrapBad.java");

    private static final Path ILLEGAL_SYMBOL_INPUT_FILE = Path.of(
        "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/illegalsymbol/"
            + "InputIllegalSymbolDefault.java");

    private static final Path IMPORT_ORDER_INPUT_FILE = Path.of(
        "src/test/resources/com/puppycrawl/tools/checkstyle/checks/imports/importorder/"
            + "InputImportOrderCaseInsensitive.java");

    private static final Path ATCLAUSE_ORDER_INPUT_FILE = Path.of(
        "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder/"
            + "InputAtclauseOrderIncorrectCustom1.java");

    @TempDir
    public Path temporaryFolder;

    @Test
    public void testAddMissingDefaultTag() throws Exception {
        final Path input = copyInput("tokens = PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT");

        InlineConfigParser.parse(input.toString());

        assertWithMessage("missing default tag was not added")
                .that(Files.readString(input))
                .contains("tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT");
    }

    @Test
    public void testAddMissingDefaultProperty() throws Exception {
        final Path input = copyInput("");

        final TestInputConfiguration configuration = InlineConfigParser.parse(input.toString());

        assertWithMessage("missing default property was not added")
                .that(Files.readString(input))
                .contains("tokens = (default)PACKAGE_DEF,IMPORT,STATIC_IMPORT,MODULE_IMPORT");
        assertWithMessage("violation line was not updated after adding a property")
                .that(configuration.getViolations().getFirst().getLineNo())
                .isEqualTo(9);
    }

    @Test
    public void testKeepNonDefaultProperty() throws Exception {
        final Path input = copyInput("tokens = IMPORT");

        InlineConfigParser.parse(input.toString());

        assertWithMessage("non-default property was changed")
                .that(Files.readString(input))
                .contains("tokens = IMPORT");
    }

    @Test
    public void testUpdateOutdatedDefaultProperty() throws Exception {
        final Path input = copyInput("tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT");

        InlineConfigParser.parse(input.toString());

        assertWithMessage("outdated default property was not updated")
                .that(Files.readString(input))
                .contains("tokens = (default)PACKAGE_DEF,IMPORT,STATIC_IMPORT,MODULE_IMPORT");
    }

    @Test
    public void testUpdateTokensMissingFromMetadata() throws Exception {
        final Path input = copyImportOrderInput();

        InlineConfigParser.parse(input.toString());

        assertWithMessage("tokens omitted from metadata were not updated")
                .that(Files.readString(input))
                .contains("tokens = (default)IMPORT,STATIC_IMPORT");
    }

    @Test
    public void testUpdateOutdatedMultilineDefaultProperty() throws Exception {
        final Path input = copyInput("tokens = (default)PACKAGE_DEF, IMPORT, \\\n"
                + "         STATIC_IMPORT");

        InlineConfigParser.parse(input.toString());

        assertWithMessage("outdated multiline default property was not updated")
                .that(Files.readString(input))
                .contains("tokens = (default)PACKAGE_DEF, IMPORT, \\\n"
                    + "         STATIC_IMPORT, MODULE_IMPORT\n\n");
        assertWithMessage("updating a multiline property changed the input body line")
                .that(Files.readAllLines(input).indexOf("package com.puppycrawl.tools. // violation "
                    + "'package statement should not be line-wrapped.'"))
                .isEqualTo(8);
    }

    @Test
    public void testKeepReorderedCommaSeparatedStringProperty() throws Exception {
        final Path input = copyIllegalSymbolInput();

        InlineConfigParser.parse(input.toString());

        assertWithMessage("reordered String property was marked as default")
                .that(Files.readString(input))
                .contains("symbolCodes = 0x1F700-0x10FFFF, 0x1F680-0x1F6FF, "
                    + "0x1F600-0x1F64F, 0x2190-0x27BF");
    }

    @Test
    public void testKeepOrderSensitiveCollectionProperty() throws Exception {
        final Path input = copyAtclauseOrderInput();

        InlineConfigParser.parse(input.toString());

        assertWithMessage("order-sensitive collection was marked as default")
                .that(Files.readString(input))
                .contains("tagOrder = @since, @version, @param, @return");
    }

    @Test
    public void testDoNotDuplicateDefaultTag() throws Exception {
        final Path input = copyInput(
                "tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT");

        InlineConfigParser.parse(input.toString());
        final String contentAfterFirstParse = Files.readString(input);
        InlineConfigParser.parse(input.toString());

        assertWithMessage("second parse changed the input")
                .that(Files.readString(input))
                .isEqualTo(contentAfterFirstParse);
    }

    @Test
    public void testAddMissingDefaultTagToMultilineProperty() throws Exception {
        final Path input = copyInput("tokens = PACKAGE_DEF, IMPORT, \\\n"
                + "         STATIC_IMPORT, MODULE_IMPORT");

        InlineConfigParser.parse(input.toString());

        assertWithMessage("missing default tag was not added to multiline property")
                .that(Files.readString(input))
                .contains("tokens = (default)PACKAGE_DEF, IMPORT, \\\n"
                    + "         STATIC_IMPORT, MODULE_IMPORT");
    }

    @Test
    public void testKeepUnknownProperty() throws Exception {
        final Path input = copyInput("tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, "
                + "MODULE_IMPORT\nunknown = value");

        InlineConfigParser.parse(input.toString());

        assertWithMessage("unknown property was changed")
                .that(Files.readString(input))
                .contains("unknown = value");
    }

    @Test
    public void testKeepInputBody() throws Exception {
        final Path input = copyInput("");
        final String contentBeforeParse = Files.readString(input);

        InlineConfigParser.parse(input.toString());

        final String contentAfterParse = Files.readString(input);
        assertWithMessage("input body was changed")
                .that(getInputBody(contentAfterParse))
                .isEqualTo(getInputBody(contentBeforeParse));
    }

    private static String getInputBody(String content) {
        return content.substring(content.indexOf("*/"));
    }

    private Path copyInput(String tokensProperty) throws IOException {
        final Path input = temporaryFolder.resolve(
            "com/puppycrawl/tools/checkstyle/checks/whitespace/nolinewrap/InputNoLineWrapBad.java");
        Files.createDirectories(input.getParent());
        final String content = Files.readString(INPUT_FILE)
                .replace("tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT",
                    tokensProperty);
        Files.writeString(input, content);
        return input;
    }

    private Path copyIllegalSymbolInput() throws IOException {
        final Path input = temporaryFolder.resolve(
            "com/puppycrawl/tools/checkstyle/checks/coding/illegalsymbol/"
                + "InputIllegalSymbolDefault.java");
        Files.createDirectories(input.getParent());
        final String content = Files.readString(ILLEGAL_SYMBOL_INPUT_FILE)
                .replace("symbolCodes = (default)0x2190-0x27BF, 0x1F600-0x1F64F, "
                        + "0x1F680-0x1F6FF, 0x1F700-0x10FFFF",
                    "symbolCodes = 0x1F700-0x10FFFF, 0x1F680-0x1F6FF, "
                        + "0x1F600-0x1F64F, 0x2190-0x27BF");
        Files.writeString(input, content);
        return input;
    }

    private Path copyImportOrderInput() throws IOException {
        final Path input = temporaryFolder.resolve(
            "com/puppycrawl/tools/checkstyle/checks/imports/importorder/"
                + "InputImportOrderCaseInsensitive.java");
        Files.createDirectories(input.getParent());
        Files.copy(IMPORT_ORDER_INPUT_FILE, input);
        return input;
    }

    private Path copyAtclauseOrderInput() throws IOException {
        final Path input = temporaryFolder.resolve(
            "com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder/"
                + "InputAtclauseOrderIncorrectCustom1.java");
        Files.createDirectories(input.getParent());
        Files.copy(ATCLAUSE_ORDER_INPUT_FILE, input);
        return input;
    }

}
