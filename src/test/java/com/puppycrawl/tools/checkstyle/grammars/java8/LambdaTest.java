////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammars.java8;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class LambdaTest extends BaseCheckTestSupport {

    @Test
    public void testLambdaInVariableInitialization()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest1.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithoutArgsOneLineLambdaBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest2.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithoutArgsFullLambdaBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest3.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithOneArgWithOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest4.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithOneArgWithFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest5.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithOneArgWIthoutTypeOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest6.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithOneArgWIthoutTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest7.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithFewArgsWithoutTypeOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest8.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithFewArgsWithoutTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest9.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithOneArgWIthoutParenthesesWithoutTypeOneLineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest10.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithOneArgWIthoutParenthesesWithoutTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest11.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithFewArgWIthTypeOneLine()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest12.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithFewArgWithTypeFullBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest13.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWIthMultilineBody()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest14.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testCasesFromSpec()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest15.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testWithTypecast()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest16.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testInAssignment()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest17.java").getCanonicalPath(),
                expected);

    }

    @Test
    public void testInTernary()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/"
                + "tools/checkstyle/grammars/java8/InputLambdaTest18.java").getCanonicalPath(),
                expected);

    }

}
