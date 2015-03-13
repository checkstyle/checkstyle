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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.FilterSet;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.SeverityLevelCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;

import static com.puppycrawl.tools.checkstyle.Utils.fileExtensionMatches;

/**
 * This class provides the functionality to check a set of files.
 * @author Oliver Burn
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @author lkuehne
 */
public class Checker extends AutomaticBean implements MessageDispatcher
{
    /** maintains error count */
    private final SeverityLevelCounter counter = new SeverityLevelCounter(
            SeverityLevel.ERROR);

    /** vector of listeners */
    private final List<AuditListener> listeners = Lists.newArrayList();

    /** vector of fileset checks */
    private final List<FileSetCheck> fileSetChecks = Lists.newArrayList();

    /** class loader to resolve classes with. **/
    private ClassLoader loader = Thread.currentThread()
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
     * Creates a new <code>Checker</code> instance.
     * The instance needs to be contextualized and configured.
     *
     * @throws CheckstyleException if an error occurs
     */
    public Checker() throws CheckstyleException
    {
        addListener(counter);
    }

    @Override
    public void finishLocalSetup() throws CheckstyleException
    {
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
        context.add("classLoader", loader);
        context.add("moduleFactory", moduleFactory);
        context.add("severity", severityLevel.getName());
        context.add("basedir", basedir);
        childContext = context;
    }

    @Override
    protected void setupChild(Configuration childConf)
        throws CheckstyleException
    {
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
            // TODO i18n
            throw new CheckstyleException("cannot initialize module " + name
                    + " - " + ex.getMessage(), ex);
        }
    }

    /**
     * Adds a FileSetCheck to the list of FileSetChecks
     * that is executed in process().
     * @param fileSetCheck the additional FileSetCheck
     */
    public void addFileSetCheck(FileSetCheck fileSetCheck)
    {
        fileSetCheck.setMessageDispatcher(this);
        fileSetChecks.add(fileSetCheck);
    }

    /**
     * Adds a filter to the end of the audit event filter chain.
     * @param filter the additional filter
     */
    public void addFilter(Filter filter)
    {
        filters.addFilter(filter);
    }

    /**
     * Removes filter.
     * @param filter filter to remove.
     */
    public void removeFilter(Filter filter)
    {
        filters.removeFilter(filter);
    }

    /** Cleans up the object. **/
    public void destroy()
    {
        listeners.clear();
        filters.clear();
    }

    /**
     * Add the listener that will be used to receive events from the audit.
     * @param listener the nosy thing
     */
    public final void addListener(AuditListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Removes a given listener.
     * @param listener a listener to remove
     */
    public void removeListener(AuditListener listener)
    {
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
    public int process(List<File> files)
    {
        // Prepare to start
        fireAuditStarted();
        for (final FileSetCheck fsc : fileSetChecks) {
            fsc.beginProcessing(charset);
        }

        // Process each file
        for (final File f : files) {
            if (!fileExtensionMatches(f, fileExtensions)) {
                continue;
            }
            final String fileName = f.getAbsolutePath();
            fireFileStarted(fileName);
            final SortedSet<LocalizedMessage> fileMessages = Sets.newTreeSet();
            try {
                final FileText theText = new FileText(f.getAbsoluteFile(),
                        charset);
                for (final FileSetCheck fsc : fileSetChecks) {
                    fileMessages.addAll(fsc.process(f, theText));
                }
            }
            catch (final FileNotFoundException fnfe) {
                Utils.getExceptionLogger().debug(
                        "FileNotFoundException occured.", fnfe);
                fileMessages.add(new LocalizedMessage(0,
                        Defn.CHECKSTYLE_BUNDLE, "general.fileNotFound", null,
                        null, this.getClass(), null));
            }
            catch (final IOException ioe) {
                Utils.getExceptionLogger().debug("IOException occured.", ioe);
                fileMessages.add(new LocalizedMessage(0,
                        Defn.CHECKSTYLE_BUNDLE, "general.exception",
                        new String[] {ioe.getMessage()}, null, this.getClass(),
                        null));
            }
            fireErrors(fileName, fileMessages);
            fireFileFinished(fileName);
        }

        // Finish up
        for (final FileSetCheck fsc : fileSetChecks) {
            // They may also log!!!
            fsc.finishProcessing();
            fsc.destroy();
        }

        final int errorCount = counter.getCount();
        fireAuditFinished();
        return errorCount;
    }

    /**
     * Create a stripped down version of a filename.
     * @param fileName the original filename
     * @return the filename where an initial prefix of basedir is stripped
     */
    private String getStrippedFileName(final String fileName)
    {
        return Utils.getStrippedFileName(basedir, fileName);
    }

    /** @param basedir the base directory to strip off in filenames */
    public void setBasedir(String basedir)
    {
        // we use getAbsolutePath() instead of getCanonicalPath()
        // because normalize() removes all . and .. so path
        // will be canonical by default.
        this.basedir = normalize(basedir);
    }

    /**
     * &quot;normalize&quot; the given absolute path.
     *
     * <p>This includes:
     * <ul>
     *   <li>Uppercase the drive letter if there is one.</li>
     *   <li>Remove redundant slashes after the drive spec.</li>
     *   <li>resolve all ./, .\, ../ and ..\ sequences.</li>
     *   <li>DOS style paths that start with a drive letter will have
     *     \ as the separator.</li>
     * </ul>
     * <p>
     *
     * @param normalizingPath a path for &quot;normalizing&quot;
     * @return &quot;normalized&quot; file name
     * @throws java.lang.NullPointerException if the file path is
     * equal to null.
     */
    public String normalize(String normalizingPath)
    {

        if (normalizingPath == null) {
            return normalizingPath;
        }

        final String osName = System.getProperty("os.name").toLowerCase(
                Locale.US);
        final boolean onNetWare = osName.indexOf("netware") > -1;

        String path = normalizingPath.replace('/', File.separatorChar).replace('\\',
            File.separatorChar);

        // make sure we are dealing with an absolute path
        final int colon = path.indexOf(":");

        if (!onNetWare) {
            if (!path.startsWith(File.separator)
                && !(path.length() >= 2
                     && Character.isLetter(path.charAt(0)) && colon == 1))
            {
                final String msg = path + " is not an absolute path";
                throw new IllegalArgumentException(msg);
            }
        }
        else {
            if (!path.startsWith(File.separator) && colon == -1) {
                final String msg = path + " is not an absolute path";
                throw new IllegalArgumentException(msg);
            }
        }

        boolean dosWithDrive = false;
        String root = null;
        // Eliminate consecutive slashes after the drive spec
        if (!onNetWare && path.length() >= 2
             && Character.isLetter(path.charAt(0)) && path.charAt(1) == ':'
            || onNetWare && colon > -1)
        {

            dosWithDrive = true;

            final char[] ca = path.replace('/', '\\').toCharArray();
            final StringBuilder sbRoot = new StringBuilder();
            for (int i = 0; i < colon; i++) {
                sbRoot.append(Character.toUpperCase(ca[i]));
            }
            sbRoot.append(':');
            if (colon + 1 < path.length()) {
                sbRoot.append(File.separatorChar);
            }
            root = sbRoot.toString();

            // Eliminate consecutive slashes after the drive spec
            final StringBuilder sbPath = new StringBuilder();
            for (int i = colon + 1; i < ca.length; i++) {
                if (ca[i] != '\\' || ca[i] == '\\' && ca[i - 1] != '\\') {
                    sbPath.append(ca[i]);
                }
            }
            path = sbPath.toString().replace('\\', File.separatorChar);

        }
        else {
            if (path.length() == 1) {
                root = File.separator;
                path = "";
            }
            else if (path.charAt(1) == File.separatorChar) {
                // UNC drive
                root = File.separator + File.separator;
                path = path.substring(2);
            }
            else {
                root = File.separator;
                path = path.substring(1);
            }
        }

        final FastStack<String> s = FastStack.newInstance();
        s.push(root);
        final StringTokenizer tok = new StringTokenizer(path, File.separator);
        while (tok.hasMoreTokens()) {
            final String thisToken = tok.nextToken();
            if (".".equals(thisToken)) {
                continue;
            }
            else if ("..".equals(thisToken)) {
                if (s.size() < 2) {
                    throw new IllegalArgumentException("Cannot resolve path "
                            + path);
                }
                s.pop();
            }
            else { // plain component
                s.push(thisToken);
            }
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.size(); i++) {
            if (i > 1) {
                // not before the filesystem root and not after it, since root
                // already contains one
                sb.append(File.separatorChar);
            }
            sb.append(s.peek(i));
        }

        path = sb.toString();
        if (dosWithDrive) {
            path = path.replace('/', '\\');
        }
        return path;
    }

    /** @return the base directory property used in unit-test. */
    public final String getBasedir()
    {
        return basedir;
    }

    /** notify all listeners about the audit start */
    protected void fireAuditStarted()
    {
        final AuditEvent evt = new AuditEvent(this);
        for (final AuditListener listener : listeners) {
            listener.auditStarted(evt);
        }
    }

    /** notify all listeners about the audit end */
    protected void fireAuditFinished()
    {
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
    public void fireFileStarted(String fileName)
    {
        final String stripped = getStrippedFileName(fileName);
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
    public void fireFileFinished(String fileName)
    {
        final String stripped = getStrippedFileName(fileName);
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
    public void fireErrors(String fileName,
        SortedSet<LocalizedMessage> errors)
    {
        final String stripped = getStrippedFileName(fileName);
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
    public final void setFileExtensions(String[] extensions)
    {
        if (extensions == null) {
            fileExtensions = null;
            return;
        }

        fileExtensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            final String extension = extensions[i];
            if (extension.startsWith(".")) {
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
    public void setModuleFactory(ModuleFactory moduleFactory)
    {
        this.moduleFactory = moduleFactory;
    }

    /** @param localeCountry the country to report messages  **/
    public void setLocaleCountry(String localeCountry)
    {
        this.localeCountry = localeCountry;
    }

    /** @param localeLanguage the language to report messages  **/
    public void setLocaleLanguage(String localeLanguage)
    {
        this.localeLanguage = localeLanguage;
    }

    /**
     * Sets the severity level.  The string should be one of the names
     * defined in the <code>SeverityLevel</code> class.
     *
     * @param severity  The new severity level
     * @see SeverityLevel
     */
    public final void setSeverity(String severity)
    {
        severityLevel = SeverityLevel.getInstance(severity);
    }

    /**
     * Sets the classloader that is used to contextualize filesetchecks.
     * Some Check implementations will use that classloader to improve the
     * quality of their reports, e.g. to load a class and then analyze it via
     * reflection.
     * @param loader the new classloader
     */
    public final void setClassloader(ClassLoader loader)
    {
        this.loader = loader;
    }

    /**
     * Sets the classloader used to load Checkstyle core and custom module
     * classes when the module tree is being built up.
     * If no custom ModuleFactory is being set for the Checker module then
     * this module classloader must be specified.
     * @param moduleClassLoader the classloader used to load module classes
     */
    public final void setModuleClassLoader(ClassLoader moduleClassLoader)
    {
        this.moduleClassLoader = moduleClassLoader;
    }

    /**
     * Sets a named charset.
     * @param charset the name of a charset
     * @throws UnsupportedEncodingException if charset is unsupported.
     */
    public void setCharset(String charset)
        throws UnsupportedEncodingException
    {
        if (!Charset.isSupported(charset)) {
            final String message = "unsupported charset: '" + charset + "'";
            throw new UnsupportedEncodingException(message);
        }
        this.charset = charset;
    }
}
