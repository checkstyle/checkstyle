///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class HorizontalWhitespaceTest extends AbstractGoogleModuleTestSupport {

    private static final String[] MODULES = {
        "WhitespaceAround",
        "WhitespaceAfter",
        "GenericWhitespace",
        "MethodParamPad",
        "ParenPad",
        "NoWhitespaceBefore",
        "NoWhitespaceBeforeCaseDefaultColon",
    };

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule462horizontalwhitespace";
    }

    @Test
    public void testWhitespaceAroundBasic() throws Exception {
        final String filePath = getPath("InputWhitespaceAroundBasic.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testWhitespaceAroundEmptyTypesCycles() throws Exception {
        final String filePath = getPath("InputWhitespaceAroundEmptyTypesAndCycles.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testWhitespaceAfterBad() throws Exception {
        final String filePath = getPath("InputWhitespaceAfterBad.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testWhitespaceAfterGood() throws Exception {
        final String filePath = getPath("InputWhitespaceAfterGood.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testParenPad() throws Exception {
        final String filePath = getPath("InputParenPad.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testNoWhitespaceBeforeEmptyForLoop() throws Exception {
        final String filePath = getPath("InputNoWhitespaceBeforeEmptyForLoop.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testNoWhitespaceBeforeColonOfLabel() throws Exception {
        final String filePath = getPath("InputNoWhitespaceBeforeColonOfLabel.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testNoWhitespaceBeforeAnnotations() throws Exception {
        final String filePath = getPath("InputNoWhitespaceBeforeAnnotations.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testNoWhitespaceBeforeCaseDefaultColon() throws Exception {
        final String filePath = getPath("InputNoWhitespaceBeforeCaseDefaultColon.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testMethodParamPad() throws Exception {
        final String filePath = getPath("InputMethodParamPad.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testWhitespaceAroundGenerics() throws Exception {
        final String filePath = getPath("InputWhitespaceAroundGenerics.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void testGenericWhitespace() throws Exception {
        final String filePath = getPath("InputGenericWhitespace.java");
        verifyWithConfigParser(MODULES, filePath);
    }

    @Test
    public void genericEndsTheLine() throws Exception {
        verifyWithConfigParser(MODULES, getPath("InputGenericWhitespaceEndsTheLine.java"));
    }

    @Test
    public void testWhitespaceAroundWhen() throws Exception {
        final String filePath = getNonCompilablePath(("InputWhitespaceAroundWhen.java"));
        verifyWithConfigParser(MODULES, filePath);
    }
}
