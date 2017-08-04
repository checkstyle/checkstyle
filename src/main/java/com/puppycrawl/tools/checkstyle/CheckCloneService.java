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

package com.puppycrawl.tools.checkstyle;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configurable;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Contextualizable;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * A service for cloning check instances.
 */
public final class CheckCloneService {
    /** Logger for CheckCloneService. */
    private static final Log LOG = LogFactory.getLog(CheckCloneService.class);

    /** A log pattern for case when a check does not implement appropriate interface. */
    private static final String MODULE_MUST_IMPLEMENT_MT_INTERFACE =
            "CheckCloneService.moduleMustImplementAtLeastOneMtInterface";

    /** A key pointing to "unable to clone module" message in the "messages.properties" file */
    private static final String UNABLE_TO_CLONE_MODULE = "CheckCloneService.unableToCloneModule";

    /** A private constructor is required for utility class. */
    private CheckCloneService() {
    }

    /**
     * Clones the given check.
     * @param check A check to be cloned.
     * @return Cloned check.
     */
    public static AbstractCheck cloneCheck(AbstractCheck check) {
        final AbstractCheck clone;

        if (check.getClass().isAnnotationPresent(FileStatefulCheck.class)) {
            clone = doCloneCheck(check);
        }
        else if (check.getClass().isAnnotationPresent(StatelessCheck.class)
                || check.getClass().isAnnotationPresent(GlobalStatefulCheck.class)) {
            clone = check;
        }
        else {
            final String checkClassName = check.getClass().getSimpleName();
            final LocalizedMessage message = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE,
                    MODULE_MUST_IMPLEMENT_MT_INTERFACE,
                    new String[] {checkClassName}, null, CheckCloneService.class, null);
            LOG.debug(message.getMessage());

            clone = doCloneCheck(check);
        }

        return clone;
    }

    /**
     * Clones a list of fileset checks.
     * @param fileSetChecks A list of fileset checks to be cloned.
     * @return A list of cloned fileset checks.
     */
    public static List<FileSetCheck> cloneFileSetChecks(List<FileSetCheck> fileSetChecks) {
        return fileSetChecks.stream().map(CheckCloneService::cloneFileSetCheck)
                .collect(Collectors.toList());
    }

    /**
     * Clones the given fileset check.
     * @param fileSetCheck A fileset check to clone.
     * @return Cloned fileset check.
     */
    private static FileSetCheck cloneFileSetCheck(FileSetCheck fileSetCheck) {
        final FileSetCheck clone;

        if (fileSetCheck.getClass().isAnnotationPresent(FileStatefulCheck.class)) {
            clone = doCloneFileSetCheck(fileSetCheck);
        }
        else if (fileSetCheck.getClass().isAnnotationPresent(StatelessCheck.class)
                || fileSetCheck.getClass().isAnnotationPresent(GlobalStatefulCheck.class)) {
            clone = fileSetCheck;
        }
        else {
            final String checkClassName = fileSetCheck.getClass().getSimpleName();
            final LocalizedMessage message = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE,
                    MODULE_MUST_IMPLEMENT_MT_INTERFACE,
                    new String[] {checkClassName}, null, CheckCloneService.class, null);
            LOG.debug(message.getMessage());

            clone = doCloneFileSetCheck(fileSetCheck);
        }

        return clone;
    }

    /**
     * Performs actual check cloning.
     * @param check The check to clone
     * @return The check clone.
     */
    private static AbstractCheck doCloneCheck(AbstractCheck check) {
        final AbstractCheck clone = doCloneModule(check, AbstractCheck.class);
        clone.init();
        return clone;
    }

    /**
     * Performs actual fileset check cloning.
     * @param originalCheck The check to clone
     * @return The check clone.
     */
    private static FileSetCheck doCloneFileSetCheck(FileSetCheck originalCheck) {
        final FileSetCheck clone = doCloneModule(originalCheck, FileSetCheck.class);
        clone.init();
        clone.finishCloning(originalCheck);
        return clone;
    }

    /**
     * Clones the module using the default module factory.
     * @param module A module to clone.
     * @param clazz The module class.
     * @param <T> The generic module type.
     * @return The module clone.
     */
    private static <T extends Configurable & Contextualizable> T doCloneModule(
            T module, Class<T> clazz) {
        final ClassLoader classLoader = CheckCloneService.class.getClassLoader();
        final ModuleFactory factory = new PackageObjectFactory(
                Checker.class.getPackage().getName(), classLoader);
        return doCloneModule(module, clazz, factory);
    }

    /**
     * Clones the module using the given module factory.
     * @param originalModule A module to clone.
     * @param clazz The module class.
     * @param factory A factory for creating new modules.
     * @param <T> The generic module type.
     * @return The module clone.
     */
    private static <T extends Configurable & Contextualizable> T doCloneModule(
            T originalModule, Class<T> clazz, ModuleFactory factory) {
        final Configuration config = originalModule.getConfiguration();
        final String moduleName = config.getName();
        try {
            final Object module = factory.createModule(moduleName);
            final T clone = clazz.cast(module);
            clone.contextualize(originalModule.getContext());
            clone.configure(config);
            return clone;
        }
        catch (CheckstyleException ex) {
            final LocalizedMessage unableToCloneModuleMessage = new LocalizedMessage(0,
                    Definitions.CHECKSTYLE_BUNDLE, UNABLE_TO_CLONE_MODULE,
                    new String[] {moduleName}, null, CheckCloneService.class, null);
            throw new IllegalStateException(unableToCloneModuleMessage.getMessage(), ex);
        }
    }
}
