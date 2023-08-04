///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.site;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.apache.maven.doxia.macro.MacroExecutionException;

import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck;

/**
 * Utility class for site generation.
 */
public final class SiteUtil {

    /**
     * Private utility constructor.
     */
    private SiteUtil() {
    }

    /**
     * Get string values of the message keys from the given check class.
     *
     * @param module class to examine.
     * @return a set of checkstyle's module message keys.
     * @throws MacroExecutionException if extraction of message keys fails.
     */
    public static Set<String> getMessageKeys(Class<?> module)
            throws MacroExecutionException {
        final Set<Field> messageKeyFields = getCheckMessageKeys(module);
        // We use a TreeSet to sort the message keys alphabetically
        final Set<String> messageKeys = new TreeSet<>();
        for (Field field : messageKeyFields) {
            messageKeys.add(getFieldValue(field, module));
        }
        return messageKeys;
    }

    /**
     * Gets the check's messages keys.
     *
     * @param module class to examine.
     * @return a set of checkstyle's module message fields.
     * @throws MacroExecutionException if the attempt to read a protected class fails.
     * @noinspection ChainOfInstanceofChecks
     * @noinspectionreason ChainOfInstanceofChecks - We will deal with this at
     *                     <a href="https://github.com/checkstyle/checkstyle/issues/13500">13500</a>
     *
     */
    private static Set<Field> getCheckMessageKeys(Class<?> module)
            throws MacroExecutionException {
        try {
            final Set<Field> checkstyleMessages = new HashSet<>();

            // get all fields from current class
            final Field[] fields = module.getDeclaredFields();

            for (Field field : fields) {
                if (field.getName().startsWith("MSG_")) {
                    checkstyleMessages.add(field);
                }
            }

            // deep scan class through hierarchy
            final Class<?> superModule = module.getSuperclass();

            if (superModule != null) {
                checkstyleMessages.addAll(getCheckMessageKeys(superModule));
            }

            // special cases that require additional classes
            if (module == RegexpMultilineCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeys(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector")));
            }
            else if (module == RegexpSinglelineCheck.class
                    || module == RegexpSinglelineJavaCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeys(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector")));
            }

            return checkstyleMessages;
        }
        catch (ClassNotFoundException ex) {
            final String message = String.format(Locale.ROOT, "Couldn't find class: %s",
                    module.getName());
            throw new MacroExecutionException(message, ex);
        }
    }

    /**
     * Returns the value of the given field.
     *
     * @param field the field.
     * @param instance the instance of the module.
     * @return the value of the field.
     * @throws MacroExecutionException if the value could not be retrieved.
     */
    private static String getFieldValue(Field field, Object instance)
            throws MacroExecutionException {
        try {
            // required for package/private classes
            field.trySetAccessible();
            return field.get(instance).toString();
        }
        catch (IllegalAccessException ex) {
            throw new MacroExecutionException("Couldn't get field value", ex);
        }
    }

    /**
     * Returns the instance of the module with the given name.
     *
     * @param moduleName the name of the module.
     * @return the instance of the module.
     * @throws MacroExecutionException if the module could not be created.
     */
    public static Object getModuleInstance(String moduleName) throws MacroExecutionException {
        final ModuleFactory factory = getPackageObjectFactory();
        try {
            return factory.createModule(moduleName);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't find class: " + moduleName, ex);
        }
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     *
     * @return the default PackageObjectFactory.
     * @throws MacroExecutionException if the PackageObjectFactory cannot be created.
     */
    private static PackageObjectFactory getPackageObjectFactory() throws MacroExecutionException {
        try {
            final ClassLoader cl = ViolationMessagesMacro.class.getClassLoader();
            final Set<String> packageNames = PackageNamesLoader.getPackageNames(cl);
            return new PackageObjectFactory(packageNames, cl);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't load checkstyle modules", ex);
        }
    }

    /**
     * Construct a string with a leading newline character and followed by
     * the given amount of spaces. We use this method only to match indentation in
     * regular xdocs and have minimal diff when parsing the templates.
     * This method exists until
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">13426</a>
     *
     * @param amountOfSpaces the amount of spaces to add after the newline.
     * @return the constructed string.
     */
    public static String getNewlineAndIndentSpaces(int amountOfSpaces) {
        return System.lineSeparator() + " ".repeat(amountOfSpaces);
    }
}
