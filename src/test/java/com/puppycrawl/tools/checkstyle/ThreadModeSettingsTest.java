////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class ThreadModeSettingsTest {

    @Test
    public void testProperties() {
        final ThreadModeSettings config = new ThreadModeSettings(1, 2);
        assertEquals("Invalid checker threads number", 1, config.getCheckerThreadsNumber());
        assertEquals("Invalid treewalker threads number", 2, config.getTreeWalkerThreadsNumber());
    }

    @Test
    public void testResolveCheckerInMultiThreadMode() {
        final ThreadModeSettings configuration = new ThreadModeSettings(2, 2);

        try {
            configuration.resolveName(ThreadModeSettings.CHECKER_MODULE_NAME);
            fail("An exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "Multi thread mode for Checker module is not implemented",
                    ex.getMessage());
        }
    }

    @Test
    public void testResolveCheckerInSingleThreadMode() {
        final ThreadModeSettings singleThreadMode = ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;

        assertEquals("Invalid name resolved", ThreadModeSettings.CHECKER_MODULE_NAME,
                singleThreadMode.resolveName(ThreadModeSettings.CHECKER_MODULE_NAME));
    }

    @Test
    public void testResolveTreeWalker() {
        final ThreadModeSettings configuration = new ThreadModeSettings(2, 2);

        try {
            configuration.resolveName(ThreadModeSettings.TREE_WALKER_MODULE_NAME);
            fail("Exception is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Invalid exception message",
                    "Multi thread mode for TreeWalker module is not implemented",
                    ex.getMessage());
        }
    }

    @Test
    public void testResolveTreeWalkerInSingleThreadMode() {
        final ThreadModeSettings singleThreadMode = ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
        final String actual =
                singleThreadMode.resolveName(ThreadModeSettings.TREE_WALKER_MODULE_NAME);
        assertThat("Invalid name resolved: " + actual,
                actual, is(ThreadModeSettings.TREE_WALKER_MODULE_NAME));
    }

    @Test
    public void testResolveAnyOtherModule() throws Exception {
        final Set<Class<?>> allModules = CheckUtil.getCheckstyleModules();
        final ThreadModeSettings multiThreadModeSettings = new ThreadModeSettings(2, 2);
        final ThreadModeSettings singleThreadModeSettings =
                ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;

        for (Class<?> module : allModules) {
            if (Checker.class.isAssignableFrom(module)
                    || TreeWalker.class.isAssignableFrom(module)) {
                // they're handled in other tests
                continue;
            }

            final String moduleName = module.getSimpleName();
            assertThat("Invalid name resolved",
                    singleThreadModeSettings.resolveName(moduleName), is(moduleName));
            assertThat("Invalid name resolved",
                    multiThreadModeSettings.resolveName(moduleName), is(moduleName));
        }
    }

}
