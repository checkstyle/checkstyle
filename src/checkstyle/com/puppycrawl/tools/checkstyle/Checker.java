////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * This class provides the functionality to check a set of files.
 * @author Oliver Burn
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @author lkuehne
 */
public class Checker extends AutomaticBean implements MessageDispatcher
{
    /** maintains error count */
    private final SeverityLevelCounter mCounter = new SeverityLevelCounter(
            SeverityLevel.ERROR);

    /** vector of listeners */
    private final List<AuditListener> mListeners = Lists.newArrayList();

    /** vector of fileset checks */
    private final List<FileSetCheck> mFileSetChecks = Lists.newArrayList();

    /** class loader to resolve classes with. **/
    private ClassLoader mLoader = Thread.currentThread()
            .getContextClassLoader();

    /** the basedir to strip off in filenames */
    private String mBasedir;

    /** locale country to report messages  **/
    private String mLocaleCountry = Locale.getDefault().getCountry();
    /** locale language to report messages  **/
    private String mLocaleLanguage = Locale.getDefault().getLanguage();

    /** The factory for instantiating submodules */
    private ModuleFactory mModuleFactory;

    /** The classloader used for loading Checkstyle module classes. */
    private ClassLoader mModuleClassLoader;

    /** the context of all child components */
    private Context mChildContext;

    /** The audit event filters */
    private final FilterSet mFilters = new FilterSet();

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
    private SeverityLevel mSeverityLevel = SeverityLevel.ERROR;

    /** Name of a charset */
    private String mCharset = System.getProperty("file.encoding", "UTF-8");

    /**
     * Creates a new <code>Checker</code> instance.
     * The instance needs to be contextualized and configured.
     *
     * @throws CheckstyleException if an error occurs
     */
    public Checker() throws CheckstyleException
    {
        addListener(mCounter);
    }

    @Override
    public void finishLocalSetup() throws CheckstyleException
    {
        final Locale locale = new Locale(mLocaleLanguage, mLocaleCountry);
        LocalizedMessage.setLocale(locale);

        if (mModuleFactory == null) {

            if (mModuleClassLoader == null) {
                throw new CheckstyleException(
                        "if no custom moduleFactory is set, "
                                + "moduleClassLoader must be specified");
            }

            final Set<String> packageNames = PackageNamesLoader
                    .getPackageNames(mModuleClassLoader);
            mModuleFactory = new PackageObjectFactory(packageNames,
                    mModuleClassLoader);
        }

        final DefaultContext context = new DefaultContext();
        context.add("charset", mCharset);
        context.add("classLoader", mLoader);
        context.add("moduleFactory", mModuleFactory);
        context.add("severity", mSeverityLevel.getName());
        context.add("basedir", mBasedir);
        mChildContext = context;
    }

    @Override
    protected void setupChild(Configuration aChildConf)
        throws CheckstyleException
    {
        final String name = aChildConf.getName();
        try {
            final Object child = mModuleFactory.createModule(name);
            if (child instanceof AutomaticBean) {
                final AutomaticBean bean = (AutomaticBean) child;
                bean.contextualize(mChildContext);
                bean.configure(aChildConf);
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
     * @param aFileSetCheck the additional FileSetCheck
     */
    public void addFileSetCheck(FileSetCheck aFileSetCheck)
    {
        aFileSetCheck.setMessageDispatcher(this);
        mFileSetChecks.add(aFileSetCheck);
    }

    /**
     * Adds a filter to the end of the audit event filter chain.
     * @param aFilter the additional filter
     */
    public void addFilter(Filter aFilter)
    {
        mFilters.addFilter(aFilter);
    }

    /**
     * Removes filter.
     * @param aFilter filter to remove.
     */
    public void removeFilter(Filter aFilter)
    {
        mFilters.removeFilter(aFilter);
    }

    /** Cleans up the object. **/
    public void destroy()
    {
        mListeners.clear();
        mFilters.clear();
    }

    /**
     * Add the listener that will be used to receive events from the audit.
     * @param aListener the nosy thing
     */
    public final void addListener(AuditListener aListener)
    {
        mListeners.add(aListener);
    }

    /**
     * Removes a given listener.
     * @param aListener a listener to remove
     */
    public void removeListener(AuditListener aListener)
    {
        mListeners.remove(aListener);
    }

    /**
     * Processes a set of files with all FileSetChecks.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     * @param aFiles the list of files to be audited.
     * @return the total number of errors found
     * @see #destroy()
     */
    public int process(List<File> aFiles)
    {
        // Prepare to start
        fireAuditStarted();
        for (final FileSetCheck fsc : mFileSetChecks) {
            fsc.beginProcessing(mCharset);
        }

        // Process each file
        for (final File f : aFiles) {
            final String fileName = f.getAbsolutePath();
            fireFileStarted(fileName);
            final TreeSet<LocalizedMessage> fileMessages = Sets.newTreeSet();
            try {
                final FileText theText = new FileText(f.getAbsoluteFile(),
                        mCharset);
                for (final FileSetCheck fsc : mFileSetChecks) {
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
        for (final FileSetCheck fsc : mFileSetChecks) {
            // They may also log!!!
            fsc.finishProcessing();
            fsc.destroy();
        }

        final int errorCount = mCounter.getCount();
        fireAuditFinished();
        return errorCount;
    }

    /**
     * Create a stripped down version of a filename.
     * @param aFileName the original filename
     * @return the filename where an initial prefix of basedir is stripped
     */
    private String getStrippedFileName(final String aFileName)
    {
        return Utils.getStrippedFileName(mBasedir, aFileName);
    }

    /** @param aBasedir the base directory to strip off in filenames */
    public void setBasedir(String aBasedir)
    {
        // we use getAbsolutePath() instead of getCanonicalPath()
        // because normalize() removes all . and .. so path
        // will be canonical by default.
        mBasedir = normalize(aBasedir);
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
     *
     * @param aPath a path for &quot;normalizing&quot;
     * @return &quot;normalized&quot; file name
     * @throws java.lang.NullPointerException if the file path is
     * equal to null.
     */
    public String normalize(String aPath)
    {
        final String osName = System.getProperty("os.name").toLowerCase(
                Locale.US);
        final boolean onNetWare = (osName.indexOf("netware") > -1);

        String path = aPath.replace('/', File.separatorChar).replace('\\',
            File.separatorChar);

        // make sure we are dealing with an absolute path
        final int colon = path.indexOf(":");

        if (!onNetWare) {
            if (!path.startsWith(File.separator)
                && !((path.length() >= 2)
                     && Character.isLetter(path.charAt(0)) && (colon == 1)))
            {
                final String msg = path + " is not an absolute path";
                throw new IllegalArgumentException(msg);
            }
        }
        else {
            if (!path.startsWith(File.separator) && (colon == -1)) {
                final String msg = path + " is not an absolute path";
                throw new IllegalArgumentException(msg);
            }
        }

        boolean dosWithDrive = false;
        String root = null;
        // Eliminate consecutive slashes after the drive spec
        if ((!onNetWare && (path.length() >= 2)
             && Character.isLetter(path.charAt(0)) && (path.charAt(1) == ':'))
            || (onNetWare && (colon > -1)))
        {

            dosWithDrive = true;

            final char[] ca = path.replace('/', '\\').toCharArray();
            final StringBuffer sbRoot = new StringBuffer();
            for (int i = 0; i < colon; i++) {
                sbRoot.append(Character.toUpperCase(ca[i]));
            }
            sbRoot.append(':');
            if (colon + 1 < path.length()) {
                sbRoot.append(File.separatorChar);
            }
            root = sbRoot.toString();

            // Eliminate consecutive slashes after the drive spec
            final StringBuffer sbPath = new StringBuffer();
            for (int i = colon + 1; i < ca.length; i++) {
                if ((ca[i] != '\\') || ((ca[i] == '\\') && (ca[i - 1] != '\\')))
                {
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
                            + aPath);
                }
                s.pop();
            }
            else { // plain component
                s.push(thisToken);
            }
        }

        final StringBuffer sb = new StringBuffer();
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
        return mBasedir;
    }

    /** notify all listeners about the audit start */
    protected void fireAuditStarted()
    {
        final AuditEvent evt = new AuditEvent(this);
        for (final AuditListener listener : mListeners) {
            listener.auditStarted(evt);
        }
    }

    /** notify all listeners about the audit end */
    protected void fireAuditFinished()
    {
        final AuditEvent evt = new AuditEvent(this);
        for (final AuditListener listener : mListeners) {
            listener.auditFinished(evt);
        }
    }

    /**
     * Notify all listeners about the beginning of a file audit.
     *
     * @param aFileName
     *            the file to be audited
     */
    public void fireFileStarted(String aFileName)
    {
        final String stripped = getStrippedFileName(aFileName);
        final AuditEvent evt = new AuditEvent(this, stripped);
        for (final AuditListener listener : mListeners) {
            listener.fileStarted(evt);
        }
    }

    /**
     * Notify all listeners about the end of a file audit.
     *
     * @param aFileName
     *            the audited file
     */
    public void fireFileFinished(String aFileName)
    {
        final String stripped = getStrippedFileName(aFileName);
        final AuditEvent evt = new AuditEvent(this, stripped);
        for (final AuditListener listener : mListeners) {
            listener.fileFinished(evt);
        }
    }

    /**
     * notify all listeners about the errors in a file.
     *
     * @param aFileName the audited file
     * @param aErrors the audit errors from the file
     */
    public void fireErrors(String aFileName, TreeSet<LocalizedMessage> aErrors)
    {
        final String stripped = getStrippedFileName(aFileName);
        for (final LocalizedMessage element : aErrors) {
            final AuditEvent evt = new AuditEvent(this, stripped, element);
            if (mFilters.accept(evt)) {
                for (final AuditListener listener : mListeners) {
                    listener.addError(evt);
                }
            }
        }
    }

    /**
     * Sets the factory for creating submodules.
     *
     * @param aModuleFactory the factory for creating FileSetChecks
     */
    public void setModuleFactory(ModuleFactory aModuleFactory)
    {
        mModuleFactory = aModuleFactory;
    }

    /** @param aLocaleCountry the country to report messages  **/
    public void setLocaleCountry(String aLocaleCountry)
    {
        mLocaleCountry = aLocaleCountry;
    }

    /** @param aLocaleLanguage the language to report messages  **/
    public void setLocaleLanguage(String aLocaleLanguage)
    {
        mLocaleLanguage = aLocaleLanguage;
    }

    /**
     * Sets the severity level.  The string should be one of the names
     * defined in the <code>SeverityLevel</code> class.
     *
     * @param aSeverity  The new severity level
     * @see SeverityLevel
     */
    public final void setSeverity(String aSeverity)
    {
        mSeverityLevel = SeverityLevel.getInstance(aSeverity);
    }

    /**
     * Sets the classloader that is used to contextualize filesetchecks.
     * Some Check implementations will use that classloader to improve the
     * quality of their reports, e.g. to load a class and then analyze it via
     * reflection.
     * @param aLoader the new classloader
     */
    public final void setClassloader(ClassLoader aLoader)
    {
        mLoader = aLoader;
    }

    /**
     * Sets the classloader used to load Checkstyle core and custom module
     * classes when the module tree is being built up.
     * If no custom ModuleFactory is being set for the Checker module then
     * this module classloader must be specified.
     * @param aModuleClassLoader the classloader used to load module classes
     */
    public final void setModuleClassLoader(ClassLoader aModuleClassLoader)
    {
        mModuleClassLoader = aModuleClassLoader;
    }

    /**
     * Sets a named charset.
     * @param aCharset the name of a charset
     * @throws UnsupportedEncodingException if aCharset is unsupported.
     */
    public void setCharset(String aCharset)
        throws UnsupportedEncodingException
    {
        if (!Charset.isSupported(aCharset)) {
            final String message = "unsupported charset: '" + aCharset + "'";
            throw new UnsupportedEncodingException(message);
        }
        mCharset = aCharset;
    }
}
