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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtilTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CommonUtil.class, CommonUtilTest.class })
public class CommonUtilPowerTest {

    @Test
    public void testLoadSuppressionsUriSyntaxException() throws Exception {
        final URL configUrl = mock(URL.class);

        when(configUrl.toURI()).thenThrow(URISyntaxException.class);
        mockStatic(CommonUtil.class, Mockito.CALLS_REAL_METHODS);
        final String fileName = "/suppressions_none.xml";
        when(CommonUtil.class.getResource(fileName)).thenReturn(configUrl);

        try {
            CommonUtil.getUriByFilename(fileName);
            fail("Exception is expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Invalid exception cause", ex.getCause() instanceof URISyntaxException);
            assertEquals("Invalid exception message",
                "Unable to find: " + fileName, ex.getMessage());
        }
    }

}
