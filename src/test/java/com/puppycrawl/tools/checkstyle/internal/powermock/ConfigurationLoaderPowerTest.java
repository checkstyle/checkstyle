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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.Attributes;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.PropertyResolver;
import com.puppycrawl.tools.checkstyle.ThreadModeSettings;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DefaultConfiguration.class, ConfigurationLoader.class})
public class ConfigurationLoaderPowerTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/configurationloader";
    }

    @Test
    public void testIncorrectTag() throws Exception {
        try {
            final Class<?> aClassParent = ConfigurationLoader.class;
            final Constructor<?> ctorParent = aClassParent.getDeclaredConstructor(
                    PropertyResolver.class, boolean.class, ThreadModeSettings.class);
            ctorParent.setAccessible(true);
            final Object objParent = ctorParent.newInstance(null, true, null);

            final Class<?> aClass = Class.forName("com.puppycrawl.tools.checkstyle."
                    + "ConfigurationLoader$InternalLoader");
            final Constructor<?> constructor = aClass.getConstructor(objParent.getClass());
            constructor.setAccessible(true);

            final Object obj = constructor.newInstance(objParent);

            final Class<?>[] param = new Class<?>[] {String.class, String.class,
                String.class, Attributes.class, };
            final Method method = aClass.getDeclaredMethod("startElement", param);

            method.setAccessible(true);
            method.invoke(obj, "", "", "hello", null);

            fail("Exception is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Invalid exception cause",
                ex.getCause() instanceof IllegalStateException);
            assertEquals("Invalid exception cause message",
                "Unknown name:" + "hello" + ".", ex.getCause().getMessage());
        }
    }

    @Test
    public void testConfigWithIgnoreExceptionalAttributes() throws Exception {
        // emulate exception from unrelated code, but that is same try-catch
        final DefaultConfiguration tested = PowerMockito.mock(DefaultConfiguration.class);
        when(tested.getAttributeNames()).thenReturn(new String[] {"severity"});
        when(tested.getName()).thenReturn("MemberName");
        when(tested.getAttribute("severity")).thenThrow(CheckstyleException.class);
        // to void creation of 2 other mocks for now reason, only one moc is used for all cases
        PowerMockito.whenNew(DefaultConfiguration.class)
                .withArguments("MemberName", ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE)
                .thenReturn(tested);
        PowerMockito.whenNew(DefaultConfiguration.class)
                .withArguments("Checker", ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE)
                .thenReturn(tested);
        PowerMockito.whenNew(DefaultConfiguration.class)
                .withArguments("TreeWalker", ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE)
                .thenReturn(tested);

        try {
            ConfigurationLoader.loadConfiguration(
                    getPath("InputConfigurationLoaderModuleIgnoreSeverity.xml"),
                    new PropertiesExpander(new Properties()), true);
            fail("Exception is expected");
        }
        catch (CheckstyleException expected) {
            assertEquals("Invalid exception cause message",
                "Problem during accessing 'severity' attribute for MemberName",
                    expected.getCause().getMessage());
        }
    }

}
