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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.SAXParseException;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlLoader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImportControlLoader.class, URI.class})
public class ImportControlLoaderPowerTest {

    @Test
    public void testInputStreamFailsOnRead() throws Exception {
        final InputStream inputStream = PowerMockito.mock(InputStream.class);
        final int available = Mockito.doThrow(IOException.class).when(inputStream).available();

        final URL url = PowerMockito.mock(URL.class);
        BDDMockito.given(url.openStream()).willReturn(inputStream);

        final URI uri = PowerMockito.mock(URI.class);
        BDDMockito.given(uri.toURL()).willReturn(url);

        try {
            ImportControlLoader.load(uri);
            // Using available to bypass 'ignored result' warning
            fail("exception expected " + available);
        }
        catch (CheckstyleException ex) {
            assertSame("Invalid exception class",
                    SAXParseException.class, ex.getCause().getClass());
        }
    }

}
