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

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.Utils;
import org.apache.regexp.RESyntaxException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

/**
 * This class provides the functionality to check a set of files.
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 */
public class Checker
    implements Defn
{
    /**
     * Overrides ANTLR error reporting so we completely control
     * checkstyle's output during parsing. This is important because
     * we try parsing with several grammers (with/without support for
     * <code>assert</code>). We must not write any error messages when
     * parsing fails because with the next grammar it might succeed
     * and the user will be confused.
     */
    private class SilentJava14Recognizer extends GeneratedJava14Recognizer
    {
        // TODO: remove
        /**
         * Creates a new <code>SilentJava14Recognizer</code> instance.
         *
         * @param aLexer the tokenstream the recognizer operates on.
         */
        private SilentJava14Recognizer(GeneratedJava14Lexer aLexer)
        {
            super(aLexer);
        }

        /**
         * Parser error-reporting function, does nothing.
         * @param aRex the exception to be reported
         */
        public void reportError(RecognitionException aRex)
        {
        }

        /**
         * Parser error-reporting function, does nothing.
         * @param aMsg the error message
         */
        public void reportError(String aMsg)
        {
        }

        /**
         * Parser warning-reporting function, does nothing.
         * @param aMsg the error message
         */
        public void reportWarning(String aMsg)
        {
        }
    }

    /**
     * Overrides ANTLR error reporting so we completely control
     * checkstyle's output during parsing. This is important because
     * we try parsing with several grammers (with/without support for
     * <code>assert</code>). We must not write any error messages when
     * parsing fails because with the next grammar it might succeed
     * and the user will be confused.
     */
    private static class NEWSilentJava14Recognizer extends Java14Recognizer
    {
        /**
         * Creates a new <code>SilentJava14Recognizer</code> instance.
         *
         * @param aLexer the tokenstream the recognizer operates on.
         */
        private NEWSilentJava14Recognizer(Java14Lexer aLexer)
        {
            super(aLexer);
        }

        /**
         * Parser error-reporting function, does nothing.
         * @param aRex the exception to be reported
         */
        public void reportError(RecognitionException aRex)
        {
        }

        /**
         * Parser error-reporting function, does nothing.
         * @param aMsg the error message
         */
        public void reportError(String aMsg)
        {
        }

        /**
         * Parser warning-reporting function, does nothing.
         * @param aMsg the error message
         */
        public void reportWarning(String aMsg)
        {
        }
    }

    /** configuration */
    private final Configuration mConfig;

    /** cache file **/
    private final PropertyCacheFile mCache;

    /** vector of listeners */
    private final ArrayList mListeners = new ArrayList();

    /** used to collect messages TODO: delete */
    private final LocalizedMessages mMessages;

    /** used to walk an AST and notify the checks */
    private final TreeWalker mWalker;

    /**
     * Creates a new <code>Checker</code> instance.
     *
     * @param aConfig the configuration to use
     * @param aConfigs the configuation of the checks to use
     * @throws ClassNotFoundException if an error occurs
     * @throws InstantiationException if an error occurs
     * @throws IllegalAccessException if an error occurs
     * @throws InvocationTargetException if an error occurs
     * @throws NoSuchMethodException if an error occurs
     */
    public Checker(Configuration aConfig, CheckConfiguration[] aConfigs)
        throws ClassNotFoundException, InstantiationException,
               IllegalAccessException, InvocationTargetException,
               NoSuchMethodException
    {
        mConfig = aConfig;
        mCache = new PropertyCacheFile(aConfig);
        LocalizedMessage.setLocale(new Locale(mConfig.getLocaleLanguage(),
                                              mConfig.getLocaleCountry()));
        mMessages = new LocalizedMessages(mConfig.getTabWidth());
        mWalker = new TreeWalker(mMessages, mConfig.getTabWidth());
        // TODO: improve the error handing
        for (int i = 0; i < aConfigs.length; i++) {
            final CheckConfiguration config = aConfigs[i];
            mWalker.registerCheck(
                config.createInstance(mConfig.getClassLoader()), config);
        }
    }

    /**
     * Constructs the object.
     * @param aConfig contains the configuration to check with
     * @throws RESyntaxException unable to create a regexp object
     * @throws IOException if an error occurs
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     * @throws ClassNotFoundException if an error occurs
     * @throws InstantiationException if an error occurs
     * @throws IllegalAccessException if an error occurs
     * @throws InvocationTargetException if an error occurs
     * @throws NoSuchMethodException if an error occurs
     */
    public Checker(Configuration aConfig)
        throws RESyntaxException, IOException,
        ParserConfigurationException, SAXException,
        ClassNotFoundException, InstantiationException,
        IllegalAccessException, InvocationTargetException,
        NoSuchMethodException
    {
        // TODO: delete this method eventually
        this(aConfig, new CheckConfiguration[0]);
        final Verifier v = new Verifier(aConfig);
        VerifierSingleton.setInstance(v);
    }

    /** Cleans up the object **/
    public void destroy()
    {
        mCache.destroy();
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
     * Processes a set of files.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     * @param aFiles the list of files to be audited.
     * @return the total number of errors found
     * @see #destroy()
     */
    public int process(String[] aFiles)
    {
        // TODO: delete
        int total = 0;
        fireAuditStarted();

        // If you move checkPackageHtml() around beware of the caching
        // functionality of checkstyle. Make sure that package.html
        // checks are not skipped because of caching. Otherwise you
        // might e.g. have a package.html file, check all java files
        // without errors, delete package.html and then recheck without
        // errors because the html file is not covered by the cache.
        total += checkPackageHtml(aFiles);

        for (int i = 0; i < aFiles.length; i++) {
            total += process(aFiles[i]);
        }
        fireAuditFinished();
        return total;
    }

    /**
     * Processes a set of files.
     * Once this is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     * @param aFiles the list of files to be audited.
     * @return the total number of errors found
     * @see #destroy()
     */
    public int processNEW(String[] aFiles)
    {
        // TODO: rename and blow away the old stuff
        int total = 0;
        fireAuditStarted();

        // If you move checkPackageHtml() around beware of the caching
        // functionality of checkstyle. Make sure that package.html
        // checks are not skipped because of caching. Otherwise you
        // might e.g. have a package.html file, check all java files
        // without errors, delete package.html and then recheck without
        // errors because the html file is not covered by the cache.
        total += checkPackageHtml(aFiles);

        for (int i = 0; i < aFiles.length; i++) {
            total += processNEW(aFiles[i]);
        }
        fireAuditFinished();
        return total;
    }

    /**
     * Processes a specified file and prints out all errors found.
     * @return the number of errors found
     * @param aFileName the name of the file to process
     **/
    private int process(String aFileName)
    {
        // check if already checked and passed the file
        final File f = new File(aFileName);
        final long timestamp = f.lastModified();
        if (mCache.alreadyChecked(aFileName, timestamp)) {
            return 0;
        }

        // Create a stripped down version
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

        LocalizedMessage[] errors;
        try {
            fireFileStarted(stripped);
            final String[] lines = Utils.getLines(aFileName);
            try {
                // try the 1.4 grammar first, this will succeed for
                // all code that compiles without any warnings in JDK 1.4,
                // that should cover most cases
                VerifierSingleton.getInstance().reset();
                VerifierSingleton.getInstance().setLines(lines);
                final Reader sar = new StringArrayReader(lines);
                final GeneratedJava14Lexer jl = new GeneratedJava14Lexer(sar);
                jl.setFilename(aFileName);
                final GeneratedJava14Recognizer jr =
                    new SilentJava14Recognizer(jl);
                jr.setFilename(aFileName);
                jr.setASTNodeClass(MyCommonAST.class.getName());
                jr.compilationUnit();
            }
            catch (RecognitionException re) {
                // Parsing might have failed because the checked
                // file contains "assert" as an identifier. Retry with a
                // grammar that treats "assert" as an identifier
                // and not as a keyword

                // Arghh - the pain - duplicate code!
                VerifierSingleton.getInstance().reset();
                VerifierSingleton.getInstance().setLines(lines);
                final Reader sar = new StringArrayReader(lines);
                final GeneratedJavaLexer jl = new GeneratedJavaLexer(sar);
                jl.setFilename(aFileName);
                final GeneratedJavaRecognizer jr =
                    new GeneratedJavaRecognizer(jl);
                jr.setFilename(aFileName);
                jr.setASTNodeClass(MyCommonAST.class.getName());
                jr.compilationUnit();
            }
            errors = VerifierSingleton.getInstance().getMessages();
        }
        catch (FileNotFoundException fnfe) {
            errors = new LocalizedMessage[] {
                new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                     "general.fileNotFound", null)};
        }
        catch (IOException ioe) {
            errors = new LocalizedMessage[] {
                new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                     "general.exception",
                                     new String[] {ioe.getMessage()})};
        }
        catch (RecognitionException re) {
            errors = new LocalizedMessage[] {
                new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                     "general.exception",
                                     new String[] {re.getMessage()})};
        }
        catch (TokenStreamException te) {
            errors = new LocalizedMessage[] {
                new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                     "general.exception",
                                     new String[] {te.getMessage()})};
        }

        if (errors.length == 0) {
            mCache.checkedOk(aFileName, timestamp);
        }
        else {
            fireErrors(stripped, errors);
        }

        fireFileFinished(stripped);
        return errors.length;
    }

    /**
     * Processes a specified file and prints out all errors found.
     * @return the number of errors found
     * @param aFileName the name of the file to process
     **/
    private int processNEW(String aFileName)
    {
        // TODO: blow away the old process and rename this one

        // check if already checked and passed the file
        final File f = new File(aFileName);
        final long timestamp = f.lastModified();
        if (mCache.alreadyChecked(aFileName, timestamp)) {
            return 0;
        }

        // Create a stripped down version
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

        mMessages.reset();
        try {
            fireFileStarted(stripped);
            final String[] lines = Utils.getLines(aFileName);
            final CommentManager cmgr = new CommentManager(lines);
            DetailAST rootAST = parse(lines, aFileName, cmgr);
            // ParseTreeInfoPanel.show(rootAST);
            mWalker.walk(rootAST, lines, aFileName);
        }
        catch (FileNotFoundException fnfe) {
            mMessages.add(new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                               "general.fileNotFound", null));
        }
        catch (IOException ioe) {
            mMessages.add(new LocalizedMessage(
                              0, Defn.CHECKSTYLE_BUNDLE,
                              "general.exception",
                              new String[] {ioe.getMessage()}));
        }
        catch (RecognitionException re) {
            mMessages.add(new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                               "general.exception",
                                               new String[] {re.getMessage()}));
        }
        catch (TokenStreamException te) {
            mMessages.add(new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                               "general.exception",
                                               new String[] {te.getMessage()}));
        }

        if (mMessages.size() == 0) {
            mCache.checkedOk(aFileName, timestamp);
        }
        else {
            fireErrors(stripped, mMessages.getMessages());
        }

        fireFileFinished(stripped);
        return mMessages.size();
    }

    /**
     *
     * @param aLines the individual lines of the java file
     * @param aFileName the filename of the file (used for error messages?)
     * @param aCmgr the comment manager is informed of comments during parsing
     * @return the root of the AST
     * @throws TokenStreamException if lexing failed
     * @throws RecognitionException if parsing failed
     */
    public static DetailAST parse(
            final String[] aLines, String aFileName, final CommentManager aCmgr)
            throws TokenStreamException, RecognitionException
    {
        DetailAST rootAST;
        try {
            // try the 1.4 grammar first, this will succeed for
            // all code that compiles without any warnings in JDK 1.4,
            // that should cover most cases
            final Reader sar = new StringArrayReader(aLines);
            final Java14Lexer jl = new Java14Lexer(sar);
            jl.setFilename(aFileName);
            jl.setCommentManager(aCmgr);

            final Java14Recognizer jr =
                new NEWSilentJava14Recognizer(jl);
            jr.setFilename(aFileName);
            jr.setASTNodeClass(DetailAST.class.getName());
            jr.compilationUnit();
            rootAST = (DetailAST) jr.getAST();
        }
        catch (RecognitionException re) {
            // Parsing might have failed because the checked
            // file contains "assert" as an identifier. Retry with a
            // grammar that treats "assert" as an identifier
            // and not as a keyword

            // Arghh - the pain - duplicate code!
            final Reader sar = new StringArrayReader(aLines);
            final JavaLexer jl = new JavaLexer(sar);
            jl.setFilename(aFileName);
            jl.setCommentManager(aCmgr);

            final JavaRecognizer jr = new JavaRecognizer(jl);
            jr.setFilename(aFileName);
            jr.setASTNodeClass(DetailAST.class.getName());
            jr.compilationUnit();
            rootAST = (DetailAST) jr.getAST();
        }
        return rootAST;
    }

    /**
     * Checks for a package.html file for all java files in parameter list.
     * @param aFiles the filenames of the java files to check
     * @return the number of errors found
     */
    private int checkPackageHtml(String[] aFiles)
    {
        if (!mConfig.isRequirePackageHtml()) {
            return 0;
        }

        int packageHtmlErrors = 0;
        final HashSet checkedPackages = new HashSet();
        for (int i = 0; i < aFiles.length; i++) {
            final File file = new File(aFiles[i]);
            final File packageDir = file.getParentFile();
            if (!checkedPackages.contains(packageDir)) {
                final File packageDoc =
                    new File(packageDir, "package.html");
                final String docFile = packageDoc.toString();
                fireFileStarted(docFile);
                if (!packageDoc.exists()) {
                    final LocalizedMessage error =
                        new LocalizedMessage(0, Defn.CHECKSTYLE_BUNDLE,
                                             "javadoc.packageHtml", null);
                    fireErrors(docFile, new LocalizedMessage[]{error});
                    packageHtmlErrors++;
                }
                fireFileFinished(docFile);
                checkedPackages.add(packageDir);
            }
        }
        return packageHtmlErrors;
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
    protected void fireFileStarted(String aFileName)
    {
        final AuditEvent evt = new AuditEvent(this, aFileName);
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
    protected void fireFileFinished(String aFileName)
    {
        final AuditEvent evt = new AuditEvent(this, aFileName);
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
    protected void fireErrors(String aFileName, LocalizedMessage[] aErrors)
    {
        for (int i = 0; i < aErrors.length; i++) {
            final AuditEvent evt =
                new AuditEvent(this, aFileName, aErrors[i]);
            final Iterator it = mListeners.iterator();
            while (it.hasNext()) {
                final AuditListener listener = (AuditListener) it.next();
                listener.addError(evt);
            }
        }
    }
}
