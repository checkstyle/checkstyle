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

package com.puppycrawl.tools.checkstyle.filters;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.blocks.LeftCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck;
import com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck;
import com.puppycrawl.tools.checkstyle.checks.modifier.ModifierOrderCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck;

public class SuppressionXpathFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "41:3: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 4, 3),
        };

        final String[] expectedWithFilter = {
            "41:3: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 4, 3),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
                + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {

        final String[] expectedWithoutFilter = {
            "41:3: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 4, 3),
        };

        final String[] expectedWithFilter = {
            "41:3: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 4, 3),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {

        final String[] expectedWithoutFilter = {
            "40:3: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 4, 3),
        };

        final String[] expectedWithFilter = {
            "40:3: " + getCheckMessage(CyclomaticComplexityCheck.class,
                    CyclomaticComplexityCheck.MSG_KEY, 4, 3),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
                + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase4() throws Exception {

        final String[] expectedWithoutFilter = {
            "13:1: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "package"),
            "21:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "22:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "23:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        final String[] expectedWithFilter = {
            "21:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "22:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "23:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase4.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase5() throws Exception {

        final String[] expectedWithoutFilter = {
            "19:23: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 23),
            "27:36: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 36),
            "31:27: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 27),
            "35:28: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 28),
            "40:31: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 31),
            "41:35: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 35),
            "44:23: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 23),
        };

        final String[] expectedWithFilter = {
            "27:36: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 36),
            "31:27: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 27),
            "35:28: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 28),
            "40:31: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 31),
            "41:35: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 35),
            "44:23: " + getCheckMessage(LeftCurlyCheck.class,
                    LeftCurlyCheck.MSG_KEY_LINE_NEW, "{", 23),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase5.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase6() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "21:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "22:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        final String[] expectedWithFilter = {
            "20:3: " + getCheckMessage(EmptyLineSeparatorCheck.class,
                    EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase6.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase7() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "20:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "21:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
            "48:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
            "51:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test2", pattern),
        };

        final String[] expectedWithFilter = {
            "21:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
            "48:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
            "51:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test2", pattern),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase7.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase8() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expectedWithoutFilter = {
            "34:9: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN,
                    "TestVariable", pattern),
            "35:9: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN,
                    "WeirdName", pattern),
        };

        final String[] expectedWithFilter = {
            "35:9: " + getCheckMessage(LocalVariableNameCheck.class, MSG_INVALID_PATTERN,
                    "WeirdName", pattern),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase8.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase9() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "19:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "23"),
            "20:27: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "11"),
            "21:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "22:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
            "31:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "24"),
            "49:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
            "52:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test2", pattern),
            "56:19: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "11"),
            "57:8: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "FOO", pattern),
        };

        final String[] expectedWithFilter = {
            "19:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "23"),
            "20:27: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "11"),
            "21:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "31:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "24"),
            "49:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
            "52:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test2", pattern),
            "56:19: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "11"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase9.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase10() throws Exception {

        final String[] expectedWithoutFilter = {
            "32:5: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "age", ""),
            "41:9: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "age", ""),
            "41:20: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "wordCount", ""),
            "44:14: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "age", ""),
        };

        final String[] expectedWithFilter = {
            "41:9: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "age", ""),
            "41:20: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "wordCount", ""),
            "44:14: " + getCheckMessage(RequireThisCheck.class, RequireThisCheck.MSG_VARIABLE,
                    "age", ""),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase10.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase11() throws Exception {

        final String[] expectedWithoutFilter = {
            "23:37: " + getCheckMessage(IllegalThrowsCheck.class, IllegalThrowsCheck.MSG_KEY,
                    "RuntimeException"),
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase11.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase12() throws Exception {

        final String[] expectedWithoutFilter = {
            "25:9: " + getCheckMessage(ModifierOrderCheck.class,
                    ModifierOrderCheck.MSG_MODIFIER_ORDER, "public"),
            "26:14: " + getCheckMessage(ModifierOrderCheck.class,
                    ModifierOrderCheck.MSG_MODIFIER_ORDER, "abstract"),
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase12.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase13() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "23"),
            "19:27: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "11"),
            "30:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "24"),
        };

        final String[] expectedWithFilter = {
            "18:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "23"),
            "30:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "24"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase13.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "23"),
            "19:27: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "11"),
            "30:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "24"),
        };

        final String[] expectedWithFilter = {
            "18:13: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "23"),
            "30:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "24"),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "19:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "20:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
            "47:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
            "50:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test2", pattern),
        };

        final String[] expectedWithFilter = {
            "19:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "20:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase3() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedWithoutFilter = {
            "19:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "20:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
            "47:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
            "50:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test2", pattern),
        };

        final String[] expectedWithFilter = {
            "19:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "SetSomeVar", pattern),
            "20:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "DoMATH", pattern),
            "47:15: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Test1", pattern),
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

}
