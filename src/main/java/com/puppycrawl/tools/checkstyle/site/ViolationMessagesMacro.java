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
import org.apache.maven.doxia.macro.AbstractMacro;
import org.apache.maven.doxia.macro.Macro;
import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;

/**
 * A macro that inserts an unordered list of the violation messages.
 */
@Component(role = Macro.class, hint = "violation-messages")
public class ViolationMessagesMacro extends AbstractMacro {
    @Override
    public void execute(Sink sink, MacroRequest request) throws MacroExecutionException {
        final String moduleName = "AnnotationLocation";
        final ModuleFactory moduleFactory = getModuleFactory();
        final Object instance = getModuleInstance(moduleName, moduleFactory);
        final Class<?> clss = instance.getClass();
        final Set<Field> checkstyleMessages = new HashSet<>();

        final Field[] fields = clss.getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().startsWith("MSG_")) {
                checkstyleMessages.add(field);
            }
        }

        sink.list();
        for (Field field : checkstyleMessages) {
            field.trySetAccessible();
            final String messageKey = getFieldValue(field);
            final String messageKeyUrl = "https://github.com/search?q="
                            + "path%3Asrc%2Fmain%2Fresources%2F"
                            + clss.getPackage().getName().replace(".", "%2F")
                            + "%20path%3A**%2Fmessages*.properties+repo%3Acheckstyle%2F"
                            + "checkstyle+%22" + messageKey + "%22";
            sink.rawText("\n          ");
            final XdocsTemplateSink xdocsTemplateSink = (XdocsTemplateSink) sink;
            xdocsTemplateSink.setInsertNewline(false);
            sink.listItem();
            xdocsTemplateSink.setInsertNewline(true);
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

    /**
     * Returns the value of the given field.
     *
     * @param field the field.
     * @return the value of the field.
     * @throws MacroExecutionException if the value could not be retrieved.
     */
    private static String getFieldValue(Field field) throws MacroExecutionException {
        try {
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
}
