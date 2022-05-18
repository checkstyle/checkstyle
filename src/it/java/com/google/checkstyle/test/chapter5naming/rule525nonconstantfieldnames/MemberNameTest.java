///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.google.checkstyle.test.chapter5naming.rule525nonconstantfieldnames;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class MemberNameTest extends AbstractGoogleModuleTestSupport {

    private static final String MSG = "Member name ''{0}'' must match pattern ''{1}''.";

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming/rule525nonconstantfieldnames";
    }

    @Test
    public void testMemberName() throws Exception {
        final Configuration checkConfig = getModuleConfig("MemberName");
        final String format = "^[a-z][a-z0-9][a-zA-Z0-9]*$";
        final String[] expected = {
            "5:16: " + getCheckMessage(MSG, "mPublic", format),
            "6:19: " + getCheckMessage(MSG, "mProtected", format),
            "7:9: " + getCheckMessage(MSG, "mPackage", format),
            "8:17: " + getCheckMessage(MSG, "mPrivate", format),
            "10:16: " + getCheckMessage(MSG, "_public", format),
            "11:19: " + getCheckMessage(MSG, "prot_ected", format),
            "12:9: " + getCheckMessage(MSG, "package_", format),
            "13:17: " + getCheckMessage(MSG, "priva$te", format),
            "20:9: " + getCheckMessage(MSG, "ABC", format),
            "21:15: " + getCheckMessage(MSG, "C_D_E", format),
            "23:16: " + getCheckMessage(MSG, "$mPublic", format),
            "24:19: " + getCheckMessage(MSG, "mPro$tected", format),
            "25:9: " + getCheckMessage(MSG, "mPackage$", format),
        };

        final String filePath = getPath("InputMemberNameBasic.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSimple() throws Exception {
        final Configuration checkConfig = getModuleConfig("MemberName");
        final String format = "^[a-z][a-z0-9][a-zA-Z0-9]*$";
        final String[] expected = {
            "12:17: " + getCheckMessage(MSG, "bad$Static", format),
            "17:17: " + getCheckMessage(MSG, "bad_Member", format),
            "19:17: " + getCheckMessage(MSG, "m", format),
            "21:19: " + getCheckMessage(MSG, "m_M", format),
            "24:19: " + getCheckMessage(MSG, "m$nts", format),
            "35:9: " + getCheckMessage(MSG, "mTest1", format),
            "37:16: " + getCheckMessage(MSG, "mTest2", format),
            "39:16: " + getCheckMessage(MSG, "$mTest2", format),
            "41:16: " + getCheckMessage(MSG, "mTes$t2", format),
            "43:16: " + getCheckMessage(MSG, "mTest2$", format),
            "77:21: " + getCheckMessage(MSG, "bad$Static", format),
            "79:22: " + getCheckMessage(MSG, "sum_Created", format),
            "82:21: " + getCheckMessage(MSG, "bad_Member", format),
            "84:21: " + getCheckMessage(MSG, "m", format),
            "86:23: " + getCheckMessage(MSG, "m_M", format),
            "89:23: " + getCheckMessage(MSG, "m$nts", format),
            "93:13: " + getCheckMessage(MSG, "mTest1", format),
            "95:20: " + getCheckMessage(MSG, "mTest2", format),
            "97:20: " + getCheckMessage(MSG, "$mTest2", format),
            "99:20: " + getCheckMessage(MSG, "mTes$t2", format),
            "101:20: " + getCheckMessage(MSG, "mTest2$", format),
            "107:25: " + getCheckMessage(MSG, "bad$Static", format),
            "109:25: " + getCheckMessage(MSG, "sum_Created", format),
            "112:25: " + getCheckMessage(MSG, "bad_Member", format),
            "114:25: " + getCheckMessage(MSG, "m", format),
            "116:25: " + getCheckMessage(MSG, "m_M", format),
            "119:27: " + getCheckMessage(MSG, "m$nts", format),
            "123:25: " + getCheckMessage(MSG, "mTest1", format),
            "125:25: " + getCheckMessage(MSG, "mTest2", format),
            "127:25: " + getCheckMessage(MSG, "$mTest2", format),
            "129:25: " + getCheckMessage(MSG, "mTes$t2", format),
            "131:25: " + getCheckMessage(MSG, "mTest2$", format),
        };

        final String filePath = getPath("InputMemberNameSimple.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
