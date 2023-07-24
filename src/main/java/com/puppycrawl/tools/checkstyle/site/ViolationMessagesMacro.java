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
import java.util.TreeSet;

import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.module.xdoc.XdocSink;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

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

/**
 * A macro that inserts an unordered list of the violation messages.
 */
@Component(role = Macro.class, hint = "violation-messages")
public class ViolationMessagesMacro extends AbstractMacro {
    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String moduleName = (String) request.getParameter("moduleName");
        final Class<?> clss = getModuleClass(moduleName);
        final Set<Field> messageKeyFields = getCheckMessageKeyFields(clss);
        final Set<String> messageKeys = getMessageKeys(messageKeyFields);
        createListOfMessages(sink, clss, messageKeys);
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
     * Returns the checkstyle messages for the given class.
     *
     * @param clss the class.
     * @return the checkstyle messages.
     * @throws MacroExecutionException if the messages could not be retrieved.
     */
    private static Set<Field> getCheckMessageKeyFields(Class<?> clss)
            throws MacroExecutionException {
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
            checkstyleMessages.addAll(getCheckMessageKeyFields(superModule));
        }

        // special cases that require additional classes
        try {
            if (clss == RegexpMultilineCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeyFields(Class
                    .forName("com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector")));
            }
            else if (clss == RegexpSinglelineCheck.class
                    || clss == RegexpSinglelineJavaCheck.class) {
                checkstyleMessages.addAll(getCheckMessageKeyFields(Class
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
     * Returns the message keys for the given fields.
     *
     * @param messageKeyFields the fields.
     * @return the message keys.
     * @throws MacroExecutionException if the message keys could not be retrieved.
     */
    private static Set<String> getMessageKeys(Set<Field> messageKeyFields)
            throws MacroExecutionException {
        final Set<String> messageKeys = new TreeSet<>();
        for (Field field : messageKeyFields) {
            messageKeys.add(getFieldValue(field));
        }
        return messageKeys;
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
            // required for package/private classes
            field.trySetAccessible();
            return field.get(null).toString();
        }
        catch (IllegalAccessException ex) {
            throw new MacroExecutionException("Couldn't get field value", ex);
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
     * Iterates through the fields of the class and creates an unordered list.
     *
     * @param sink the sink to write to.
     * @param clss the class of the fields.
     * @param messageKeys the set of message keys to iterate through.
     */
    private static void createListOfMessages(
            Sink sink, Class<?> clss, Set<String> messageKeys) {
        sink.list();

        for (String messageKey : messageKeys) {
            createListItem(sink, clss, messageKey);
        }

        sink.rawText(getNewlineAndIndentSpaces(8));
        sink.list_();
    }

    /**
     * Creates a list item for the given field.
     *
     * @param sink the sink to write to.
     * @param clss the class of the field.
     * @param messageKey the message key.
     */
    private static void createListItem(Sink sink, Class<?> clss, String messageKey) {
        final String messageKeyUrl = constructMessageKeyUrl(clss, messageKey);

        // Place the <li>.
        sink.rawText(getNewlineAndIndentSpaces(10));
        // This is a hack to prevent a newline from being inserted by the default sink.
        // Once we get rid of the custom parser, we can remove this.
        final XdocSink xdocSink = (XdocSink) sink;
        xdocSink.setInsertNewline(false);
        sink.listItem();
        xdocSink.setInsertNewline(true);

        // Place an <a>.
        sink.rawText(getNewlineAndIndentSpaces(12));
        sink.link(messageKeyUrl);
        // Further indent the text.
        sink.rawText(getNewlineAndIndentSpaces(14));
        sink.rawText(messageKey);

        // Place closing </a> and </li> tags.
        sink.rawText(getNewlineAndIndentSpaces(12));
        sink.link_();
        sink.rawText(getNewlineAndIndentSpaces(10));
        sink.listItem_();
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
     * Construct a string with a leading newline character and followed by
     * the given amount of spaces.
     * This method exists until
     * <a href="https://github.com/checkstyle/checkstyle/issues/13426">13426</a>
     *
     * @param amountOfSpaces the amount of spaces to add after the newline.
     * @return the constructed string.
     */
    private static String getNewlineAndIndentSpaces(int amountOfSpaces) {
        return System.lineSeparator() + " ".repeat(amountOfSpaces);
    }
}
