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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import org.apache.regexp.RESyntaxException;

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
     * checkstyle's output during parsing.
     *
     * @see SilentJava14Recognizer
     */
    private class SilentJavaRecognizer extends GeneratedJavaRecognizer
    {
        /**
         * Creates a new <code>SilentJavaRecognizer</code> instance.
         *
         * @param aLexer the tokenstream the recognizer operates on.
         */
        private SilentJavaRecognizer(GeneratedJavaLexer aLexer)
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

    /**
     * Constructs the object.
     * @param aConfig contains the configuration to check with
     * @throws RESyntaxException unable to create a regexp object
     **/
    public Checker(Configuration aConfig)
        throws RESyntaxException
    {
        mConfig = aConfig;
        mCache = new PropertyCacheFile(aConfig);
        final Verifier v = new Verifier(aConfig);
        VerifierSingleton.setInstance(v);
    }

    /** Cleans up the object **/
    public void destroy()
    {
        mCache.destroy();

        // close all listeners
        final Iterator it = mListeners.iterator();
        while (it.hasNext()) {
            final AuditListener listener = (AuditListener) it.next();
            final OutputStream os = listener.getOutputStream();
            // close only those that can be closed...
            if ((os != System.out) && (os != System.err) && (os != null)) {
                try  {
                    os.flush();
                    os.close();
                }
                catch (IOException ignored) {
                }
            }
        }
        it.remove();
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
     * Processes a specified file and prints out all errors found.
     * @return the number of errors found
     * @param aFileName the name of the file to process
     **/
    private int process(String aFileName)
    {
        final File f = new File(aFileName);
        final long timestamp = f.lastModified();
        if (mCache.alreadyChecked(aFileName, timestamp)) {
            return 0;
        }

        LineText[] errors;
        try {
            fireFileStarted(aFileName);
            final String[] lines = getLines(aFileName);
            final Reader sar = new StringArrayReader(lines);
            VerifierSingleton.getInstance().clearMessages();
            VerifierSingleton.getInstance().setLines(lines);
            try {
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
                // filecontains "assert" statement. Retry with a
                // grammar that treats "assert" as an identifier
                // and not as a keyword

                // Arghh - the pain - duplicate code!
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
            errors = new LineText[] {new LineText(0, "File not found!")};
        }
        catch (IOException ioe) {
            errors = new LineText[] {
                new LineText(0, "Got an IOException -" + ioe.getMessage())};
        }
        catch (RecognitionException re) {
            errors = new LineText[] {
                new LineText(0,
                             "Got a RecognitionException -" + re.getMessage())};
        }
        catch (TokenStreamException te) {
            errors = new LineText[] {
                new LineText(0,
                             "Got a TokenStreamException -" + te.getMessage())};
        }

        if (errors.length == 0) {
            mCache.checkedOk(aFileName, timestamp);
        }
        else {
            fireErrors(aFileName, errors);
        }

        fireFileFinished(aFileName);
        return errors.length;
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
                    final LineText error =
                        new LineText(0, "missing package documentation file.");
                    fireErrors(docFile, new LineText[]{error} );
                    packageHtmlErrors++;
                }
                fireFileFinished(docFile);
                checkedPackages.add(packageDir);
            }
        }
        return packageHtmlErrors;
    }


    /**
     * Loads the contents of a file in a String array.
     * @return the lines in the file
     * @param aFileName the name of the file to load
     * @throws IOException error occurred
     **/
    private String[] getLines(String aFileName)
        throws IOException
    {
        final LineNumberReader lnr =
            new LineNumberReader(new FileReader(aFileName));
        final ArrayList lines = new ArrayList();
        while (true) {
            final String l = lnr.readLine();
            if (l == null) {
                break;
            }
            lines.add(l);
        }

        return (String[]) lines.toArray(new String[0]);
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
    protected void fireErrors(String aFileName, LineText[] aErrors)
    {
        for (int i = 0; i < aErrors.length; i++) {
            final AuditEvent evt =
                new AuditEvent(this, aFileName, aErrors[i].getLineNo(),
                               aErrors[i].getText());
            final Iterator it = mListeners.iterator();
            while (it.hasNext()) {
                final AuditListener listener = (AuditListener) it.next();
                listener.addError(evt);
            }
        }
    }
}
