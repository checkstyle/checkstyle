////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.Configuration;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyCacheFile.class, PropertyCacheFileTest.class })
public class PropertyCacheFileTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testNonAccessibleFile() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final File file = temporaryFolder.newFile("file.output");
        file.setReadable(true, false);
        file.setWritable(false, false);

        new PropertyCacheFile(config, file.getAbsolutePath());
    }

    @Test
    public void testCtor() {
        try {
            new PropertyCacheFile(null, "");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("config can not be null", ex.getMessage());
        }
        try {
            final Configuration config = new DefaultConfiguration("myName");
            new PropertyCacheFile(config, null);
        }
        catch (IllegalArgumentException ex) {
            assertEquals("fileName can not be null", ex.getMessage());
        }
    }

    @Test
    public void testInCache() throws IOException {
        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        cache.put("myFile", 1);
        assertTrue(cache.isInCache("myFile", 1));
        assertFalse(cache.isInCache("myFile", 2));
        assertFalse(cache.isInCache("myFile1", 1));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testExceptionNoSuchAlgorithmException() throws Exception {

        final Configuration config = new DefaultConfiguration("myName");
        final String filePath = temporaryFolder.newFile().getPath();
        final PropertyCacheFile cache = new PropertyCacheFile(config, filePath);
        cache.put("myFile", 1);
        mockStatic(MessageDigest.class);

        when(MessageDigest.getInstance("SHA-1"))
                .thenThrow(NoSuchAlgorithmException.class);

        final Class<?>[] param = new Class<?>[1];
        param[0] = Serializable.class;
        final Method method = PropertyCacheFile.class.getDeclaredMethod("getConfigHashCode", param);
        method.setAccessible(true);
        try {
            method.invoke(cache, config);
            fail();
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause().getCause() instanceof NoSuchAlgorithmException);
            assertEquals("Unable to calculate hashcode.", ex.getCause().getMessage());
        }
    }
}
