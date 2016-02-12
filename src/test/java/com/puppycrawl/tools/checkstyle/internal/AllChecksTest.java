////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportControlCheck;

public class AllChecksTest extends BaseCheckTestSupport {
    @Test
    public void testAllChecksWithDefaultConfiguration() throws Exception {
        final String inputFilePath = getPath("InputDefaultConfig.java");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            final DefaultConfiguration checkConfig = createCheckConfig(check);
            final Checker checker;
            if (AbstractCheck.class.isAssignableFrom(check)) {
                // Checks which have Check as a parent.
                if (check.equals(ImportControlCheck.class)) {
                    // ImportControlCheck must have the import control configuration file to avoid
                    // violation.
                    checkConfig.addAttribute("file", getPath("import-control_complete.xml"));
                }
                checker = createChecker(checkConfig);
            }
            else {
                // Checks which have TreeWalker as a parent.
                BaseCheckTestSupport testSupport = new BaseCheckTestSupport() {
                    @Override
                    protected DefaultConfiguration createCheckerConfig(Configuration config) {
                        final DefaultConfiguration dc = new DefaultConfiguration("root");
                        dc.addChild(checkConfig);
                        return dc;
                    }
                };
                checker = testSupport.createChecker(checkConfig);
            }
            verify(checker, inputFilePath, expected);
        }
    }

    @Test
    public void testDefaultTokensAreSubsetOfAcceptableTokens() throws Exception {
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (AbstractCheck.class.isAssignableFrom(check)) {
                final AbstractCheck testedCheck = (AbstractCheck) check.getDeclaredConstructor()
                        .newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                if (!isSubset(defaultTokens, acceptableTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's default tokens must be a subset"
                            + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfAcceptableTokens() throws Exception {
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (AbstractCheck.class.isAssignableFrom(check)) {
                final AbstractCheck testedCheck = (AbstractCheck) check.getDeclaredConstructor()
                        .newInstance();
                final int[] requiredTokens = testedCheck.getRequiredTokens();
                final int[] acceptableTokens = testedCheck.getAcceptableTokens();

                if (!isSubset(requiredTokens, acceptableTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's required tokens must be a subset"
                            + " of acceptable tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testRequiredTokensAreSubsetOfDefaultTokens() throws Exception {
        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (AbstractCheck.class.isAssignableFrom(check)) {
                final AbstractCheck testedCheck = (AbstractCheck) check.getDeclaredConstructor()
                        .newInstance();
                final int[] defaultTokens = testedCheck.getDefaultTokens();
                final int[] requiredTokens = testedCheck.getRequiredTokens();

                if (!isSubset(requiredTokens, defaultTokens)) {
                    final String errorMessage = String.format(Locale.ROOT,
                            "%s's required tokens must be a subset"
                            + " of default tokens.", check.getName());
                    Assert.fail(errorMessage);
                }
            }
        }
    }

    @Test
    public void testAllChecksAreReferencedInConfigFile() throws Exception {
        final Set<String> checksReferencedInConfig = CheckUtil.getConfigCheckStyleChecks();
        final Set<String> checksNames = getSimpleNames(CheckUtil.getCheckstyleChecks());

        for (String check : checksNames) {
            if (!checksReferencedInConfig.contains(check)) {
                final String errorMessage = String.format(Locale.ROOT,
                        "%s is not referenced in checkstyle_checks.xml", check);
                Assert.fail(errorMessage);
            }
        }

    }

    @Test
    public void testAllCheckstyleModulesHaveXdocDocumentation() throws Exception {
        final Set<String> checkstyleModulesNames = getSimpleNames(CheckUtil.getCheckstyleModules());
        final Set<String> modulesNamesWhichHaveXdocs = XDocUtil.getModulesNamesWhichHaveXdoc();

        for (String moduleName : checkstyleModulesNames) {
            if (!modulesNamesWhichHaveXdocs.contains(moduleName)) {
                final String missingModuleMessage = String.format(Locale.ROOT,
                    "Module %s does not have xdoc documentation.",
                    moduleName);
                Assert.fail(missingModuleMessage);
            }
        }
    }

    @Test
    public void testAllCheckstyleModulesInCheckstyleConfig() throws Exception {
        final Set<String> configChecks = CheckUtil.getConfigCheckStyleChecks();

        for (String moduleName : getSimpleNames(CheckUtil.getCheckstyleModules())) {
            Assert.assertTrue("checkstyle_checks.xml is missing module: " + moduleName,
                    configChecks.contains(moduleName));
        }
    }

    @Test
    public void testAllCheckstyleModulesHaveMessage() throws Exception {
        for (Class<?> module : CheckUtil.getCheckstyleChecks()) {
            Assert.assertFalse(module.getSimpleName()
                    + " should have atleast one 'MSG_*' field for error messages", CheckUtil
                    .getCheckMessages(module).isEmpty());
        }
    }

    @Test
    public void testAllCheckstyleMessages() throws Exception {
        for (Class<?> module : CheckUtil.getCheckstyleChecks()) {
            for (Field message : CheckUtil.getCheckMessages(module)) {
                Assert.assertEquals(module.getSimpleName() + "." + message.getName()
                        + " should be 'public static final'", Modifier.PUBLIC | Modifier.STATIC
                        | Modifier.FINAL, message.getModifiers());

                // below is required for package/private classes
                if (!message.isAccessible()) {
                    message.setAccessible(true);
                }

                final String result = CheckUtil.getCheckMessage(module, message.get(null)
                        .toString());

                Assert.assertNotNull(module.getSimpleName() + " should have text for the message '"
                        + message.getName() + "'", result);
                Assert.assertFalse(
                        module.getSimpleName() + " should have non-empty text for the message '"
                                + message.getName() + "'", result.trim().isEmpty());
                Assert.assertFalse(module.getSimpleName()
                        + " should have non-TODO text for the message '" + message.getName() + "'",
                        result.trim().startsWith("TODO"));
            }
        }
    }

    /**
     * Checks that an array is a subset of other array.
     * @param array to check whether it is a subset.
     * @param arrayToCheckIn array to check in.
     */
    private static boolean isSubset(int[] array, int... arrayToCheckIn) {
        Arrays.sort(arrayToCheckIn);
        for (final int element : array) {
            if (Arrays.binarySearch(arrayToCheckIn, element) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes 'Check' suffix from each class name in the set.
     * @param checks class instances.
     * @return a set of simple names.
     */
    private static Set<String> getSimpleNames(Set<Class<?>> checks) {
        final Set<String> checksNames = new HashSet<>();
        for (Class<?> check : checks) {
            checksNames.add(check.getSimpleName().replace("Check", ""));
        }
        return checksNames;
    }
}
