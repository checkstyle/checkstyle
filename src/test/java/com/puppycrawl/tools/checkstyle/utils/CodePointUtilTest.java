////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck;

public class CodePointUtilTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/codepointutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(CommonUtil.class, true))
                .isTrue();
    }

    @Test
    public void testIsBlank() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();

        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertFalse(CodePointUtil.isBlank(check.getLineCodePoints(0)),
                "Should return false when array is empty");
    }

    @Test
    public void testIsBlankAheadWhitespace() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();
        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertFalse(CodePointUtil.isBlank(check.getLineCodePoints(1)),
                "Should return false when array is empty");
    }

    @Test
    public void testIsBlankBehindWhitespace() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();
        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertFalse(CodePointUtil.isBlank(check.getLineCodePoints(2)),
                "Should return false when array is empty");
    }

    @Test
    public void testIsBlankWithWhitespacesAround() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();
        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertFalse(CodePointUtil.isBlank(check.getLineCodePoints(3)),
                "Should return false when array is empty");
    }

    @Test
    public void testIsBlankWithWhitespaceInside() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();
        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertFalse(CodePointUtil.isBlank(check.getLineCodePoints(4)),
                "Should return false when array is empty");
    }

    @Test
    public void testIsBlankWithEmptyLine() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();
        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertWithMessage("Should return true when array is empty")
                .that(CodePointUtil.isBlank(check.getLineCodePoints(6)))
                .isTrue();
    }

    @Test
    public void testIsBlankWithWhitespacesOnly() throws Exception {
        final AvoidEscapedUnicodeCharactersCheck check =
                new AvoidEscapedUnicodeCharactersCheck();
        check.setFileContents(new FileContents(new FileText(
                new File(getPath("InputCodePointUtil_resource.txt")),
                Charset.defaultCharset().name())));
        assertWithMessage("Should return true when array contains only spaces")
                .that(CodePointUtil.isBlank(check.getLineCodePoints(8)))
                .isTrue();
    }

}
