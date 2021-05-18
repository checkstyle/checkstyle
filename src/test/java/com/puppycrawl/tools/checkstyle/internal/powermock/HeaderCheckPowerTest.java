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

package com.puppycrawl.tools.checkstyle.internal.powermock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.header.AbstractHeaderCheck;
import com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck;
import com.puppycrawl.tools.checkstyle.checks.header.HeaderCheckTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HeaderCheck.class, HeaderCheckTest.class, AbstractHeaderCheck.class })
public class HeaderCheckPowerTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/header";
    }

    /**
     * This test needs powermock because {@code StringReader} can't throw an exception to trigger
     * the catch otherwise unless the reader is mishandled.
     */
    @Test
    public void testIoExceptionWhenLoadingHeader() {
        final HeaderCheck check = PowerMockito.spy(new HeaderCheck());

        assertDoesNotThrow(() -> check.setHeader("header"));
    }

}
