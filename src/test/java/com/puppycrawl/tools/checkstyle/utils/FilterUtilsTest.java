////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class FilterUtilsTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(FilterUtils.class, true));
    }

    @Test
    public void testExistingFile() throws Exception {
        final File file = temporaryFolder.newFile();
        assertTrue("Suppression file exists",
                FilterUtils.isFileExists(file.getPath()));
    }

    @Test
    public void testNonExistingFile() {
        assertFalse("Suppression file does not exist",
                FilterUtils.isFileExists("non-existing.xml"));
    }

    @Test
    @PrepareForTest({FilterUtils.class, CommonUtils.class})
    public void testExceptionOnClosing() throws Exception {
        final File file = temporaryFolder.newFile("existing.xml");
        final InputStream inputStream = PowerMockito.mock(InputStream.class);
        Mockito.doThrow(IOException.class).when(inputStream).close();

        final URL url = PowerMockito.mock(URL.class);
        BDDMockito.given(url.openStream()).willReturn(inputStream);

        final URI uri = PowerMockito.mock(URI.class);
        BDDMockito.given(uri.toURL()).willReturn(url);

        PowerMockito.mockStatic(CommonUtils.class);

        final String fileName = file.getPath();
        BDDMockito.given(CommonUtils.getUriByFilename(fileName)).willReturn(uri);
        assertFalse("Should be false, because error on close",
                FilterUtils.isFileExists(fileName));
    }
}
