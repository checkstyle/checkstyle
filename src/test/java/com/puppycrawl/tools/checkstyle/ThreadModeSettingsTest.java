///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class ThreadModeSettingsTest {

    @Test
    public void testProperties() {
        final ThreadModeSettings config = new ThreadModeSettings(1, 2);
        assertWithMessage("Invalid checker threads number")
                .that(config.getCheckerThreadsNumber())
                .isEqualTo(1);
        assertWithMessage("Invalid treewalker threads number")
                .that(config.getTreeWalkerThreadsNumber())
                .isEqualTo(2);
    }

    @Test
    public void testResolveCheckerInMultiThreadMode() {
        final ThreadModeSettings configuration = new ThreadModeSettings(2, 2);

        try {
            configuration.resolveName(ThreadModeSettings.CHECKER_MODULE_NAME);
            assertWithMessage("An exception is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("Multi thread mode for Checker module is not implemented");
        }
    }

    @Test
    public void testResolveCheckerInSingleThreadMode() {
        final ThreadModeSettings singleThreadMode = ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;

        final String name = singleThreadMode.resolveName(ThreadModeSettings.CHECKER_MODULE_NAME);
        assertWithMessage("Invalid name resolved")
                .that(name)
                .isEqualTo(ThreadModeSettings.CHECKER_MODULE_NAME);
    }

    @Test
    public void testResolveTreeWalker() {
        final ThreadModeSettings configuration = new ThreadModeSettings(2, 2);

        try {
            configuration.resolveName(ThreadModeSettings.TREE_WALKER_MODULE_NAME);
            assertWithMessage("Exception is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("Multi thread mode for TreeWalker module is not implemented");
        }
    }

    @Test
    public void testResolveTreeWalkerInSingleThreadMode() {
        final ThreadModeSettings singleThreadMode = ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
        final String actual =
                singleThreadMode.resolveName(ThreadModeSettings.TREE_WALKER_MODULE_NAME);
        assertWithMessage("Invalid name resolved: " + actual)
                .that(actual)
                .isEqualTo(ThreadModeSettings.TREE_WALKER_MODULE_NAME);
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
            assertWithMessage("Invalid name resolved")
                    .that(singleThreadModeSettings.resolveName(moduleName))
                    .isEqualTo(moduleName);
            assertWithMessage("Invalid name resolved")
                    .that(multiThreadModeSettings.resolveName(moduleName))
                    .isEqualTo(moduleName);
        }
    }

}
