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
import java.util.Set;

import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.PackageObjectFactory;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.coding.AbstractSuperCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractAccessControlNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck;
import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.doxia.sink.impl.AbstractXmlSink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that inserts an unordered list of the violation messages.
 */
@Component(role = Macro.class, hint = "violation-messages")
public class ViolationMessagesMacro extends AbstractMacro {
    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String moduleName = (String) request.getParameter("moduleName");
        final Class<?> clss = getModuleClass(moduleName);
        final Set<Field> checkstyleMessages = getCheckstyleMessages(clss);
        createListOfMessages(sink, clss, checkstyleMessages);
    }

    /**
     * Constructs a URL to GitHub that searches for the message key.
     *
     * @param clss the class of the module.
     * @param messageKey the message key.
     * @return the URL to GitHub.
     */
    private static String constructMessageKeyUrl(Class<?> clss, String messageKey) {
        return "https://github.com/search?q="
                + "path%3Asrc%2Fmain%2Fresources%2F"
                + clss.getPackage().getName().replace(".", "%2F")
                + "%20path%3A**%2Fmessages*.properties+repo%3Acheckstyle%2F"
                + "checkstyle+%22" + messageKey + "%22";
    }

    /**
     * Returns the class of the module with the given name.
     *
     * @param moduleName the name of the module.
     * @return the class of the module.
     * @throws MacroExecutionException if the module could not be created.
     */
    private Class<?> getModuleClass(String moduleName) throws MacroExecutionException {
        final ModuleFactory moduleFactory = getModuleFactory();
        final Object instance = getModuleInstance(moduleName, moduleFactory);
        return instance.getClass();
    }

    /**
     * Returns the default PackageObjectFactory with the default package names.
     *
     * @return the default PackageObjectFactory.
     * @throws MacroExecutionException if the PackageObjectFactory could not be created.
     */
    private static ModuleFactory getModuleFactory() throws MacroExecutionException {
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
     * Returns the instance of the module with the given name.
     *
     * @param moduleName the name of the module.
     * @param moduleFactory the factory to create the module.
     * @return the instance of the module.
     * @throws MacroExecutionException if the module could not be created.
     */
    private static Object getModuleInstance(String moduleName, ModuleFactory moduleFactory)
            throws MacroExecutionException {
        final Object instance;
        try {
            instance = moduleFactory.createModule(moduleName);
        }
        catch (CheckstyleException ex) {
            throw new MacroExecutionException("Couldn't find class: " + moduleName, ex);
        }
        return instance;
    }

    /**
     * Returns the value of the given field.
     *
     * @param field the field.
     * @return the value of the field.
     * @throws MacroExecutionException if the value could not be retrieved.
     */
    private static String getFieldValue(Field field) throws MacroExecutionException {
        try {
            field.trySetAccessible();
            return field.get(null).toString();
        }
        catch (IllegalAccessException ex) {
            throw new MacroExecutionException("Couldn't get field value", ex);
        }
    }

    /**
     * Returns the checkstyle messages for the given class.
     *
     * @param clss the class.
     * @return the checkstyle messages.
     * @throws MacroExecutionException if the messages could not be retrieved.
     */
    private static Set<Field> getCheckstyleMessages(Class<?> clss) throws MacroExecutionException {
        final Set<Field> checkstyleMessages = new HashSet<>();

        final Field[] fields = clss.getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().startsWith("MSG_")) {
                checkstyleMessages.add(field);
            }
        }

        // deep scan class through hierarchy
        final Class<?> superModule = clss.getSuperclass();

        if (superModule != null && shouldScanDeepClassForMessages(superModule)) {
            checkstyleMessages.addAll(getCheckstyleMessages(superModule));
        }

        // special cases that require additional classes
        try {
            if (clss == RegexpMultilineCheck.class) {
                checkstyleMessages.addAll(getCheckstyleMessages(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector")));
            }
            else if (clss == RegexpSinglelineCheck.class
                    || clss == RegexpSinglelineJavaCheck.class) {
                checkstyleMessages.addAll(getCheckstyleMessages(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector")));
            }
        }
        catch (ClassNotFoundException ex) {
            throw new MacroExecutionException("Couldn't find class", ex);
        }

        return checkstyleMessages;
    }

    /**
     * Should the class be deep scanned for messages.
     *
     * @param superModule The class to examine.
     * @return {@code true} if the class should be deeply scanned.
     */
    private static boolean shouldScanDeepClassForMessages(Class<?> superModule) {
        return superModule == AbstractNameCheck.class
                || superModule == AbstractAccessControlNameCheck.class
                || superModule == AbstractParenPadCheck.class
                || superModule == AbstractSuperCheck.class;
    }

    /**
     * Iterates through the fields of the class and creates an unordered list.
     *
     * @param sink the sink to write to.
     * @param clss the class of the fields.
     * @param checkstyleMessages the set of fields to iterate through.
     * @throws MacroExecutionException if an exception occurs.
     */
    private static void createListOfMessages(
            Sink sink, Class<?> clss, Set<Field> checkstyleMessages)
            throws MacroExecutionException {
        sink.list();
        for (Field field : checkstyleMessages) {
            final String messageKey = getFieldValue(field);
            final String messageKeyUrl = constructMessageKeyUrl(clss, messageKey);
            sink.rawText("\n          ");

            // This is a hack to prevent a newline from being inserted.
            // Once we get rid of the custom parser, we can remove this.
            final AbstractXmlSink xmlSink = (AbstractXmlSink) sink;
            xmlSink.setInsertNewline(false);
            sink.listItem();
            xmlSink.setInsertNewline(true);

            sink.rawText("\n            ");
            sink.link(messageKeyUrl);
            sink.rawText("\n              ");
            sink.rawText(messageKey);
            sink.rawText("\n            ");
            sink.link_();
            sink.rawText("\n          ");
            sink.listItem_();
        }
        sink.rawText("\n        ");
        sink.list_();
    }
}
