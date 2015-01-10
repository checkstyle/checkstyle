////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

public class AnnotationTest extends BaseCheckTestSupport
{

    @Test
    public void testSimpleTypeAnnotation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                getPath("grammars/java8/InputAnnotationsTest1.java"), expected);

    }

    @Test
    public void testAnnotationOnClass()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest2.java").getCanonicalPath(), expected);

    }

    @Test
    public void testClassCastTypeAnnotation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest3.java").getCanonicalPath(), expected);

    }

    @Test
    public void testMethodParametersTypeAnnotation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest4.java").getCanonicalPath(), expected);

    }

    @Test
    public void testAnnotationInThrows()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest5.java").getCanonicalPath(), expected);

    }

    @Test
    public void testAnnotationInGeneric()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest6.java").getCanonicalPath(), expected);

    }

    @Test
    public void testAnnotationOnConstructorCall()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest7.java").getCanonicalPath(), expected);

    }

    @Test
    public void testAnnotationNestedCall()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest8.java").getCanonicalPath(), expected);

    }

    @Test
    public void testAnnotationOnWildcards()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest9.java").getCanonicalPath(), expected);

    }

    @Test
    public void testAnnotationInCatchParameters()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(MemberNameCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                  new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/grammars/java8/"
                        + "InputAnnotationsTest10.java").getCanonicalPath(), expected);

    }

}
