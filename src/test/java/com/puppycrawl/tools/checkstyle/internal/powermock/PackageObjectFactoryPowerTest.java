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

import static com.puppycrawl.tools.checkstyle.PackageObjectFactory.ModuleLoadOption.TRY_IN_ALL_REGISTERED_PACKAGES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.utils.ModuleReflectionUtil;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore(value = "com.puppycrawl.tools.checkstyle.api.*", globalIgnore = false)
@PrepareForTest(ModuleReflectionUtil.class)
public class PackageObjectFactoryPowerTest {

    /**
     * This method is for testing the case of an exception caught inside
     * {@code PackageObjectFactory.generateThirdPartyNameToFullModuleName}, a private method used
     * to initialize private field {@code PackageObjectFactory.thirdPartyNameToFullModuleNames}.
     * Since the method and the field both are private, the {@link Whitebox} is required to ensure
     * that the field is changed. Also, the expected exception should be thrown from the static
     * method {@link ModuleReflectionUtil#getCheckstyleModules}, so {@link PowerMockito#mockStatic}
     * is required to mock this exception.
     *
     * @throws Exception when the code tested throws an exception
     */
    @Test
    public void testGenerateThirdPartyNameToFullModuleNameWithException() throws Exception {
        final String name = "String";
        final String packageName = "java.lang";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Set<String> packages = Collections.singleton(packageName);
        final PackageObjectFactory objectFactory = new PackageObjectFactory(packages, classLoader,
                TRY_IN_ALL_REGISTERED_PACKAGES);

        mockStatic(ModuleReflectionUtil.class);
        doThrow(new IOException("mock exception")).when(ModuleReflectionUtil.class);
        ModuleReflectionUtil.getCheckstyleModules(packages, classLoader);

        final String internalFieldName = "thirdPartyNameToFullModuleNames";
        final Map<String, String> nullMap = Whitebox.getInternalState(objectFactory,
                internalFieldName);
        assertNull("Expected uninitialized field", nullMap);

        final Object instance = objectFactory.createModule(name);
        assertEquals("Expected empty string", "", instance);

        final Map<String, String> emptyMap = Whitebox.getInternalState(objectFactory,
                internalFieldName);
        assertEquals("Expected empty map", Collections.emptyMap(), emptyMap);
    }

}
