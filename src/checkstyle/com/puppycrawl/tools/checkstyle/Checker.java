////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.MessageDispatcher;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * This class provides the functionality to check a set of files.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @author lkuehne
 */
public class Checker
    implements Defn, MessageDispatcher
{
    /**
     * An AuditListener that maintains the number of errors.
     */
    private class ErrorCounter implements AuditListener
    {
        /** keeps track of the number of errors */
        private int mCount = 0;

        /** @see AuditListener */
        public void addError(AuditEvent aEvt)
        {
            mCount++;
        }

        /** @see AuditListener */
        public void addException(AuditEvent aEvt, Throwable aThrowable)
        {
            mCount++;
        }

        /** @see AuditListener */
        public void auditStarted(AuditEvent aEvt)
        {
            mCount = 0;
        }

        /** @see AuditListener */
        public void fileStarted(AuditEvent aEvt)
        {
        }

        /** @see AuditListener */
        public void auditFinished(AuditEvent aEvt)
        {
        }

        /** @see AuditListener */
        public void fileFinished(AuditEvent aEvt)
        {
        }

        /**
         * @return the number of errors since audit started.
         */
        private int getCount()
        {
            return mCount;
        }
    }

    /** maintains error count */
    private final ErrorCounter mCounter = new ErrorCounter();

    /** configuration */
    private final GlobalProperties mConfig;

    /** vector of listeners */
    private final ArrayList mListeners = new ArrayList();

    /** vector of fileset checks */
    private final ArrayList mFileSetChecks = new ArrayList();

    /**
     * Creates a new <code>Checker</code> instance.
     *
     * @param aConfig the configuration to use
     * @throws CheckstyleException if an error occurs
     */
    public Checker(Configuration aConfig)
        throws CheckstyleException
    {
        this(aConfig.getGlobalProperties(), aConfig.getCheckConfigurations());
    }

    /**
     * Creates a new <code>Checker</code> instance.
     *
     * @param aConfig the configuration to use
     * @param aConfigs the configuation of the checks to use
     * @throws CheckstyleException if an error occurs
     */
    public Checker(GlobalProperties aConfig, CheckConfiguration[] aConfigs)
        throws CheckstyleException
    {
        mConfig = aConfig;
        LocalizedMessage.setLocale(new Locale(mConfig.getLocaleLanguage(),
                                              mConfig.getLocaleCountry()));

        // TODO: create, configure and register the FileSetChecks from config
        // file instead of hardcoding it here in the Checker constructor.
        // Probably the addFileSetCheck mthod must be called from outside
        // the checker, just like the TreeWalker is not concerned with
        // finding all the checks it has to execute (IOC principle).

        // TODO: uncommenting the addFileSetCheck calls breaks the tests
        // because the packageHtml check is always executed and yields
        // additional errors that are not expected in the current test code
        // (which should stay like it currently is!)

        //FileSetCheck translationCheck = new TranslationCheck();
        // addFileSetCheck(translationCheck);

        //FileSetCheck packageHtmlCheck = new PackageHtmlCheck();
        // addFileSetCheck(packageHtmlCheck);

        final TreeWalker walker = new TreeWalker(mConfig);
        // TODO: improve the error handing
        for (int i = 0; i < aConfigs.length; i++) {
            final CheckConfiguration config = aConfigs[i];
            // IMPORTANT! Need to use the same class loader that created this
            // class. Otherwise can get ClassCastException problems.
            walker.registerCheck(
                config.createInstance(this.getClass().getClassLoader()),
                config);
        }
        addFileSetCheck(walker);

        this.addListener(mCounter);
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

    /** Cleans up the object **/
    public void destroy()
    {
        mListeners.clear();
    }

    /**
     * Add the listener that will be used to receive events from the audit
     * @param aListener the nosy thing
     */
    public void addListener(AuditListener aListener)
    {
        mListeners.add(aListener);
    }

    /**
     * Processes a set of files with all FileSetChecks.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     * @param aFiles the list of files to be audited.
     * @return the total number of errors found
     * @see #destroy()
     */
    public int process(File[] aFiles)
    {
        fireAuditStarted();
        for (int i = 0; i < mFileSetChecks.size(); i++) {
            FileSetCheck fileSetCheck = (FileSetCheck) mFileSetChecks.get(i);
            fileSetCheck.process(aFiles);
        }
        int errorCount = mCounter.getCount();
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
        final String stripped;
        final String basedir = mConfig.getBasedir();
        if ((basedir == null) || !aFileName.startsWith(basedir)) {
            stripped = aFileName;
        }
        else {
            // making the assumption that there is text after basedir
            final int skipSep = basedir.endsWith(File.separator) ? 0 : 1;
            stripped = aFileName.substring(basedir.length() + skipSep);
        }
        return stripped;
    }


    /** notify all listeners about the audit start */
    protected void fireAuditStarted()
    {
        final AuditEvent evt = new AuditEvent(this);
        final Iterator it = mListeners.iterator();
        while (it.hasNext()) {
            final AuditListener listener = (AuditListener) it.next();
            listener.auditStarted(evt);
        }
    }

    /** notify all listeners about the audit end */
    protected void fireAuditFinished()
    {
        final AuditEvent evt = new AuditEvent(this);
        final Iterator it = mListeners.iterator();
        while (it.hasNext()) {
            final AuditListener listener = (AuditListener) it.next();
            listener.auditFinished(evt);
        }
    }

    /**
     * notify all listeners about the beginning of a file audit
     * @param aFileName the file to be audited
     */
    public void fireFileStarted(String aFileName)
    {
        final String stripped = getStrippedFileName(aFileName);
        final AuditEvent evt = new AuditEvent(this, stripped);
        final Iterator it = mListeners.iterator();
        while (it.hasNext()) {
            final AuditListener listener = (AuditListener) it.next();
            listener.fileStarted(evt);
        }
    }

    /**
     * notify all listeners about the end of a file audit
     * @param aFileName the audited file
     */
    public void fireFileFinished(String aFileName)
    {
        final String stripped = getStrippedFileName(aFileName);
        final AuditEvent evt = new AuditEvent(this, stripped);
        final Iterator it = mListeners.iterator();
        while (it.hasNext()) {
            final AuditListener listener = (AuditListener) it.next();
            listener.fileFinished(evt);
        }
    }

    /**
     * notify all listeners about the errors in a file.
     * @param aFileName the audited file
     * @param aErrors the audit errors from the file
     */
    public void fireErrors(String aFileName, LocalizedMessage[] aErrors)
    {
        final String stripped = getStrippedFileName(aFileName);
        for (int i = 0; i < aErrors.length; i++) {
            final AuditEvent evt = new AuditEvent(this, stripped, aErrors[i]);
            final Iterator it = mListeners.iterator();
            while (it.hasNext()) {
                final AuditListener listener = (AuditListener) it.next();
                listener.addError(evt);
            }
        }
    }
}
