////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocPackageCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocPackageCheckPowerTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocpackage";
    }

    /**
     * Test require readable file with no parent to be used.
     * Usage of Mockito.spy() is the only way to satisfy these requirements
     * without the need to create new file in current working directory.
     *
     * @throws Exception if error occurs
     */
    @Test
    public void testWithFileWithoutParent() throws Exception {
        final DefaultConfiguration moduleConfig = createModuleConfig(JavadocPackageCheck.class);
        final File fileWithoutParent = spy(new File(getPath("noparentfile"
                    + File.separator + "package-info.java")));
        when(fileWithoutParent.getParent()).thenReturn(null);
        when(fileWithoutParent.getParentFile()).thenReturn(null);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(moduleConfig),
                new File[] {fileWithoutParent},
                getPath("annotation"
                    + File.separator + "package-info.java"), expected);
    }

}
