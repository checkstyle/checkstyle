///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck;

public class XpathRegressionCatchParameterNameTest extends AbstractXpathTestSupport {
    private final String checkName = CatchParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSimple() throws Exception {
        final String pattern = "^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameSimple.java"));

        final String[] expectedViolation = {
            "6:28: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "e1", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                + "/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF/IDENT[@text='e1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testNested() throws Exception {
        final String pattern = "^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameNested.java"));

        final String[] expectedViolation = {
            "9:40: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "i", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameNested']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='NestedClass']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                + "/SLIST/LITERAL_IF/SLIST"
                + "/LITERAL_TRY/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF/IDENT[@text='i']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testStaticInit() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]+$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameStaticInit.java"));

        final String[] expectedViolation = {
            "7:32: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "Ex", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameStaticInit']]"
                + "/OBJBLOCK/STATIC_INIT/SLIST"
                + "/LITERAL_DO/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF/IDENT[@text='Ex']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testAnonymous() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]+$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameAnonymous.java"));

        final String[] expectedViolation = {
            "12:40: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "E1", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT[@text='InnerClass']]"
                + "/SLIST/EXPR/LITERAL_NEW[./IDENT[@text='Runnable']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]"
                + "/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF/IDENT[@text='E1']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testLambda() throws Exception {
        final String pattern = "^[A-Z][a-z]+$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameLambda.java"));

        final String[] expectedViolation = {
            "12:32: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "e", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameLambda']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='lambdaFunction']]"
                + "/ASSIGN/LAMBDA[./IDENT[@text='a']]"
                + "/SLIST/LITERAL_FOR/SLIST/LITERAL_TRY/LITERAL_CATCH"
                + "/PARAMETER_DEF/IDENT[@text='e']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final String pattern = "^[A-Z][a-z]+$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameEnum.java"));

        final String[] expectedViolation = {
            "10:40: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "eX", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/ENUM_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameEnum']]"
                + "/OBJBLOCK/ENUM_CONSTANT_DEF[./IDENT[@text='VALUE']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP/SLIST/LITERAL_TRY/LITERAL_CATCH/"
                + "PARAMETER_DEF/IDENT[@text='eX']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final String pattern = "^[A-Z][a-z]+$";

        final DefaultConfiguration moduleConfig =
            createModuleConfig(CatchParameterNameCheck.class);
        moduleConfig.addProperty("format", pattern);

        final File fileToProcess =
            new File(getPath("InputXpathCatchParameterNameInterface.java"));

        final String[] expectedViolation = {
            "7:32: " + getCheckMessage(CatchParameterNameCheck.class,
                MSG_INVALID_PATTERN, "EX", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='InputXpathCatchParameterNameInterface']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='InnerInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                + "/SLIST/LITERAL_TRY/LITERAL_CATCH/PARAMETER_DEF/IDENT[@text='EX']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
