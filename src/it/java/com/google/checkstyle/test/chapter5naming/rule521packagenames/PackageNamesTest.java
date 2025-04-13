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

package com.google.checkstyle.test.chapter5naming.rule521packagenames;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;

public class PackageNamesTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming";
    }

    private String getPath(String packageName, String fileName) throws IOException {
        return getPath("rule521" + packageName + File.separator + fileName);
    }

    @Test
    public void testGoodPackageName() throws Exception {
        verifyWithWholeConfig(getPath("packagenames", "InputPackageNameGood.java"));
    }

    @Test
    public void testBadPackageName() throws Exception {
        verifyWithWholeConfig(getPath("packageNamesCamelCase", "InputPackageNameBad.java"));
    }

    @Test
    public void testBadPackageName2() throws Exception {
        verifyWithWholeConfig(getPath("_packagenames", "InputBadPackageName2.java"));
    }

    @Test
    public void testBadPackageName3() throws Exception {
        verifyWithWholeConfig(getPath("$packagenames", "InputPackageBadName3.java"));
    }

}
