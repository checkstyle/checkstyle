////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import java.lang.reflect.Method;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.filters.SuppressionsLoader;
import com.puppycrawl.tools.checkstyle.filters.SuppressionsLoaderTest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SuppressionsLoader.class, SuppressionsLoaderTest.class })
public class SuppressionsLoaderPowerTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testUnableToFindSuppressions() throws Exception {
        final Class<SuppressionsLoader> loaderClass = SuppressionsLoader.class;
        final Method loadSuppressions =
            loaderClass.getDeclaredMethod("loadSuppressions", InputSource.class, String.class);
        loadSuppressions.setAccessible(true);

        final String sourceName = "InputSuppressionsLoaderNone.xml";
        final InputSource inputSource = new InputSource(sourceName);

        thrown.expect(CheckstyleException.class);
        thrown.expectMessage("Unable to find: " + sourceName);

        loadSuppressions.invoke(loaderClass, inputSource, sourceName);
    }

    @Test
    public void testUnableToReadSuppressions() throws Exception {
        final Class<SuppressionsLoader> loaderClass = SuppressionsLoader.class;
        final Method loadSuppressions =
            loaderClass.getDeclaredMethod("loadSuppressions", InputSource.class, String.class);
        loadSuppressions.setAccessible(true);

        final InputSource inputSource = new InputSource();

        thrown.expect(CheckstyleException.class);
        final String sourceName = "InputSuppressionsLoaderNone.xml";
        thrown.expectMessage("Unable to read " + sourceName);

        loadSuppressions.invoke(loaderClass, inputSource, sourceName);
    }

}
