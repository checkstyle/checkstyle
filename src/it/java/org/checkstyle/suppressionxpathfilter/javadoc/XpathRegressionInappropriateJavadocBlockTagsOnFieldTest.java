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

package org.checkstyle.suppressionxpathfilter.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnFieldCheck.MSG_KEY;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnFieldCheck;

public class XpathRegressionInappropriateJavadocBlockTagsOnFieldTest
        extends AbstractXpathTestSupport {

    private final String checkName = InappropriateJavadocBlockTagsOnFieldCheck.class
            .getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/"
                + "inappropriatejavadocblocktagsonfield";
    }

    @Test
    public void testInappropriateClassFieldTag() throws Exception {
        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(InappropriateJavadocBlockTagsOnFieldCheck.class,
                MSG_KEY, "return", "field"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='field']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='field']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='field']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnFieldClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInappropriateInterfaceFieldTag() throws Exception {
        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(InappropriateJavadocBlockTagsOnFieldCheck.class,
                MSG_KEY, "return", "FIELD"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldInterface']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='FIELD']]",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldInterface']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='FIELD']]/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldInterface']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='FIELD']]/TYPE",
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldInterface']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='FIELD']]/TYPE/LITERAL_INT");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnFieldInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInappropriateEnumFieldTag() throws Exception {
        final String[] expectedViolation = {
            "16:5: " + getCheckMessage(InappropriateJavadocBlockTagsOnFieldCheck.class,
                MSG_KEY, "return", "field"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldEnum']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='field']]",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldEnum']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='field']]/MODIFIERS",
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text="
                        + "'InputXpathInappropriateJavadocBlockTagsOnFieldEnum']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='field']]"
                        + "/MODIFIERS/LITERAL_PRIVATE");

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInappropriateJavadocBlockTagsOnFieldEnum.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
