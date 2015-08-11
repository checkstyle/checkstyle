////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.SeverityLevelCounter;

/**
 * This class provides the functionality to check a set of files.
 * @author Oliver Burn
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @author lkuehne
 */
public class Checker extends AutomaticBean implements MessageDispatcher {
    /** Logger for Checker */
    private static final Log LOG = LogFactory.getLog(Checker.class);

    /** maintains error count */
    private final SeverityLevelCounter counter = new SeverityLevelCounter(
            SeverityLevel.ERROR);

    /** vector of listeners */
    private final List<AuditListener> listeners = Lists.newArrayList();

    /** vector of fileset checks */
    private final List<FileSetCheck> fileSetChecks = Lists.newArrayList();

    /** class loader to resolve classes with. **/
    private ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();

    /** the basedir to strip off in filenames */
    private String basedir;

    /** locale country to report messages  **/
    private String localeCountry = Locale.getDefault().getCountry();
    /** locale language to report messages  **/
    private String localeLanguage = Locale.getDefault().getLanguage();

    /** The factory for instantiating submodules */
    private ModuleFactory moduleFactory;

    /** The classloader used for loading Checkstyle module classes. */
    private ClassLoader moduleClassLoader;

    /** the context of all child components */
    private Context childContext;

    /** The audit event filters */
    private final FilterSet filters = new FilterSet();

    /** the file extensions that are accepted */
    private String[] fileExtensions = {};

    /**
     * The severity level of any violations found by submodules.
     * The value of this property is passed to submodules via
     * contextualize().
     *
     * Note: Since the Checker is merely a container for modules
     * it does not make sense to implement logging functionality
     * here. Consequently Checker does not extend AbstractViolationReporter,
     * leading to a bit of duplicated code for severity level setting.
     */
    private SeverityLevel severityLevel = SeverityLevel.ERROR;

    /** Name of a charset */
    private String charset = System.getProperty("file.encoding", "UTF-8");

    /**
     * Creates a new {@code Checker} instance.
     * The instance needs to be contextualized and configured.
     */
    public Checker() {
        addListener(counter);
    }

    @Override
    public void finishLocalSetup() throws CheckstyleException {
        final Locale locale = new Locale(localeLanguage, localeCountry);
        LocalizedMessage.setLocale(locale);

        if (moduleFactory == null) {

            if (moduleClassLoader == null) {
                throw new CheckstyleException(
                        "if no custom moduleFactory is set, "
                                + "moduleClassLoader must be specified");
            }

            final Set<String> packageNames = PackageNamesLoader
                    .getPackageNames(moduleClassLoader);
            moduleFactory = new PackageObjectFactory(packageNames,
                    moduleClassLoader);
        }

        final DefaultContext context = new DefaultContext();
        context.add("charset", charset);
        context.add("classLoader", classLoader);
        context.add("moduleFactory", moduleFactory);
        context.add("severity", severityLevel.getName());
        context.add("basedir", basedir);
        childContext = context;
    }

    @Override
    protected void setupChild(Configuration childConf)
        throws CheckstyleException {
        final String name = childConf.getName();
        try {
            final Object child = moduleFactory.createModule(name);
            if (child instanceof AutomaticBean) {
                final AutomaticBean bean = (AutomaticBean) child;
                bean.contextualize(childContext);
                bean.configure(childConf);
            }
            if (child instanceof FileSetCheck) {
                final FileSetCheck fsc = (FileSetCheck) child;
                fsc.init();
                addFileSetCheck(fsc);
            }
            else if (child instanceof Filter) {
                final Filter filter = (Filter) child;
                addFilter(filter);
            }
            else if (child instanceof AuditListener) {
                final AuditListener listener = (AuditListener) child;
                addListener(listener);
            }
            else {
                throw new CheckstyleException(name
                        + " is not allowed as a child in Checker");
            }
        }
        catch (final Exception ex) {
            throw new CheckstyleException("cannot initialize module " + name
                    + " - " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a FileSetCheck to the list of FileSetChecks
     * that is executed in process().
     * @param fileSetCheck the additional FileSetCheck
     */
    public void addFileSetCheck(FileSetCheck fileSetCheck) {
        fileSetCheck.setMessageDispatcher(this);
        fileSetChecks.add(fileSetCheck);
    }

    /**
     * Adds a filter to the end of the audit event filter chain.
     * @param filter the additional filter
     */
    public void addFilter(Filter filter) {
        filters.addFilter(filter);
    }

    /**
     * Removes filter.
     * @param filter filter to remove.
     */
    public void removeFilter(Filter filter) {
        filters.removeFilter(filter);
    }

    /** Cleans up the object. **/
    public void destroy() {
        listeners.clear();
        filters.clear();
    }

    /**
     * Add the listener that will be used to receive events from the audit.
     * @param listener the nosy thing
     */
    public final void addListener(AuditListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a given listener.
     * @param listener a listener to remove
     */
    public void removeListener(AuditListener listener) {
        listeners.remove(listener);
    }

    /**
     * Processes a set of files with all FileSetChecks.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     * @param files the list of files to be audited.
     * @return the total number of errors found
     * @see #destroy()
     */
    public int process(List<File> files) {
        // Prepare to start
        fireAuditStarted();
        for (final FileSetCheck fsc : fileSetChecks) {
            fsc.beginProcessing(charset);
        }

        // Process each file
        for (final File file : files) {
            if (!Utils.fileExtensionMatches(file, fileExtensions)) {
                continue;
            }
            final String fileName = file.getAbsolutePath();
            fireFileStarted(fileName);
            final SortedSet<LocalizedMessage> fileMessages = Sets.newTreeSet();
            try {
                final FileText theText = new FileText(file.getAbsoluteFile(),
                        charset);
                for (final FileSetCheck fsc : fileSetChecks) {
                    fileMessages.addAll(fsc.process(file, theText));
                }
            }
            catch (final IOException ioe) {
                LOG.debug("IOException occured.", ioe);
                fileMessages.add(new LocalizedMessage(0,
                        Definitions.CHECKSTYLE_BUNDLE, "general.exception",
                        new String[] {ioe.getMessage()}, null, getClass(),
                        null));
            }
            fireErrors(fileName, fileMessages);
            fireFileFinished(fileName);
        }

        // Finish up
        for (final FileSetCheck fsc : fileSetChecks) {
            // It may also log!!!
            fsc.finishProcessing();
        }

        for (final FileSetCheck fsc : fileSetChecks) {
            // It may also log!!!
            fsc.destroy();
        }

        final int errorCount = counter.getCount();
        fireAuditFinished();
        return errorCount;
    }

    /** @param basedir the base directory to strip off in filenames */
    public void setBasedir(String basedir) {
        this.basedir = basedir;
    }

    /** notify all listeners about the audit start */
    protected void fireAuditStarted() {
        final AuditEvent evt = new AuditEvent(this);
        for (final AuditListener listener : listeners) {
            listener.auditStarted(evt);
        }
    }

    /** notify all listeners about the audit end */
    protected void fireAuditFinished() {
        final AuditEvent evt = new AuditEvent(this);
        for (final AuditListener listener : listeners) {
            listener.auditFinished(evt);
        }
    }

    /**
     * Notify all listeners about the beginning of a file audit.
     *
     * @param fileName
     *            the file to be audited
     */
    @Override
    public void fireFileStarted(String fileName) {
        final String stripped = Utils.relativizeAndNormalizePath(basedir, fileName);
        final AuditEvent evt = new AuditEvent(this, stripped);
        for (final AuditListener listener : listeners) {
            listener.fileStarted(evt);
        }
    }

    /**
     * Notify all listeners about the end of a file audit.
     *
     * @param fileName
     *            the audited file
     */
    @Override
    public void fireFileFinished(String fileName) {
        final String stripped = Utils.relativizeAndNormalizePath(basedir, fileName);
        final AuditEvent evt = new AuditEvent(this, stripped);
        for (final AuditListener listener : listeners) {
            listener.fileFinished(evt);
        }
    }

    /**
     * notify all listeners about the errors in a file.
     *
     * @param fileName the audited file
     * @param errors the audit errors from the file
     */
    @Override
    public void fireErrors(String fileName, SortedSet<LocalizedMessage> errors) {
        final String stripped = Utils.relativizeAndNormalizePath(basedir, fileName);
        for (final LocalizedMessage element : errors) {
            final AuditEvent evt = new AuditEvent(this, stripped, element);
            if (filters.accept(evt)) {
                for (final AuditListener listener : listeners) {
                    listener.addError(evt);
                }
            }
        }
    }

    /**
     * Sets the file extensions that identify the files that pass the
     * filter of this FileSetCheck.
     * @param extensions the set of file extensions. A missing
     * initial '.' character of an extension is automatically added.
     */
    public final void setFileExtensions(String... extensions) {
        if (extensions == null) {
            fileExtensions = null;
            return;
        }

        fileExtensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            final String extension = extensions[i];
            if (Utils.startsWithChar(extension, '.')) {
                fileExtensions[i] = extension;
            }
            else {
                fileExtensions[i] = "." + extension;
            }
        }
    }

    /**
     * Sets the factory for creating submodules.
     *
     * @param moduleFactory the factory for creating FileSetChecks
     */
    public void setModuleFactory(ModuleFactory moduleFactory) {
        this.moduleFactory = moduleFactory;
    }

    /** @param localeCountry the country to report messages  **/
    public void setLocaleCountry(String localeCountry) {
        this.localeCountry = localeCountry;
    }

    /** @param localeLanguage the language to report messages  **/
    public void setLocaleLanguage(String localeLanguage) {
        this.localeLanguage = localeLanguage;
    }

    /**
     * Sets the severity level.  The string should be one of the names
     * defined in the {@code SeverityLevel} class.
     *
     * @param severity  The new severity level
     * @see SeverityLevel
     */
    public final void setSeverity(String severity) {
        severityLevel = SeverityLevel.getInstance(severity);
    }

    /**
     * Sets the classloader that is used to contextualize filesetchecks.
     * Some Check implementations will use that classloader to improve the
     * quality of their reports, e.g. to load a class and then analyze it via
     * reflection.
     * @param classLoader the new classloader
     */
    public final void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Sets the classloader that is used to contextualize filesetchecks.
     * Some Check implementations will use that classloader to improve the
     * quality of their reports, e.g. to load a class and then analyze it via
     * reflection.
     * @param loader the new classloader
     * @deprecated use {@link #setClassLoader(ClassLoader loader)} instead.
     */
    @Deprecated
    public final void setClassloader(ClassLoader loader) {
        setClassLoader(loader);
    }

    /**
     * Sets the classloader used to load Checkstyle core and custom module
     * classes when the module tree is being built up.
     * If no custom ModuleFactory is being set for the Checker module then
     * this module classloader must be specified.
     * @param moduleClassLoader the classloader used to load module classes
     */
    public final void setModuleClassLoader(ClassLoader moduleClassLoader) {
        this.moduleClassLoader = moduleClassLoader;
    }

    /**
     * Sets a named charset.
     * @param charset the name of a charset
     * @throws UnsupportedEncodingException if charset is unsupported.
     */
    public void setCharset(String charset)
        throws UnsupportedEncodingException {
        if (!Charset.isSupported(charset)) {
            final String message = "unsupported charset: '" + charset + "'";
            throw new UnsupportedEncodingException(message);
        }
        this.charset = charset;
    }
}
