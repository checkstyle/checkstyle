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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;

/**
 * Responsible for walking an abstract syntax tree and notifying interested
 * checks at each each node.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public final class TreeWalker
    extends AbstractFileSetCheck
{
    /**
     * Overrides ANTLR error reporting so we completely control
     * checkstyle's output during parsing. This is important because
     * we try parsing with several grammers (with/without support for
     * <code>assert</code>). We must not write any error messages when
     * parsing fails because with the next grammar it might succeed
     * and the user will be confused.
     */
    private static class SilentJava14Recognizer
        extends GeneratedJava14Recognizer
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
    // TODO: really need to optimise the performance of this class.

    /** maps from token name to checks */
    private final Map mTokenToChecks = new HashMap();
    /** all the registered checks */
    private final Set mAllChecks = new HashSet();
    /** collects the error messages */
    private final LocalizedMessages mMessages;
    /** the distance between tab stops */
    private int mTabWidth = 8;
    /** cache file **/
    private PropertyCacheFile mCache = new PropertyCacheFile(null, null);

    /**
     * the global configuration.
     * TODO: should only know the treewalker part of the config
     */
    private Configuration mConfig;

    /** class loader to resolve classes with. **/
    private ClassLoader mClassLoader;

    /**
     * Creates a new <code>TreeWalker</code> instance.
     */
    public TreeWalker()
    {
        mMessages = new LocalizedMessages();
    }

    /** @param aTabWidth the distance between tab stops */
    public void setTabWidth(int aTabWidth)
    {
        mTabWidth = aTabWidth;
    }

    /** @param aFileName the cache file */
    public void setCacheFile(String aFileName)
    {
        mCache = new PropertyCacheFile(mConfig, aFileName);
    }

    // TODO: Call from contextualize
    /** @param aClassLoader class loader to resolve classes with. */
    public void setClassLoader(ClassLoader aClassLoader)
    {
        mClassLoader = aClassLoader;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Configurable */
    public void configure(Configuration aConfiguration)
            throws CheckstyleException
    {
        super.configure(aConfiguration);
        mConfig = aConfiguration;

        DefaultContext checkContext = new DefaultContext();
        checkContext.add("classLoader", mClassLoader);
        checkContext.add("messages", mMessages);
        // TODO: hmmm.. this looks less than elegant
        checkContext.add("tabWidth", String.valueOf(mTabWidth));

        // TODO: improve the error handing
        Configuration[] checkConfigs = aConfiguration.getChildren();
        for (int i = 0; i < checkConfigs.length; i++) {
            final Configuration config = checkConfigs[i];
            // IMPORTANT! Need to use the same class loader that created this
            // class. Otherwise can get ClassCastException problems.
            final String name = config.getName();
            final String[] packageNames = getPackageNames();
            final Check check =
                    (Check) PackageObjectFactory.makeObject(
                        packageNames, this.getClass().getClassLoader(), name);

            check.contextualize(checkContext);
            check.configure(config);

            registerCheck(check);
        }
    }

    /**
     * Processes a specified file and reports all errors found.
     * @param aFile the file to process
     **/
    private void process(File aFile)
    {
        // check if already checked and passed the file
        final String fileName = aFile.getPath();
        final long timestamp = aFile.lastModified();
        if (mCache.alreadyChecked(fileName, timestamp)) {
            return;
        }

        mMessages.reset();
        try {
            getMessageDispatcher().fireFileStarted(fileName);
            final String[] lines = Utils.getLines(fileName);
            final FileContents contents = new FileContents(fileName, lines);
            final DetailAST rootAST = TreeWalker.parse(contents);
            walk(rootAST, contents);
        }
        catch (FileNotFoundException fnfe) {
            // TODO: this dependency on the checkstyle package is not good. It
            // introduces a circular dependency between packages.
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
            mCache.checkedOk(fileName, timestamp);
        }
        else {
            getMessageDispatcher().fireErrors(
                fileName, mMessages.getMessages());
        }

        getMessageDispatcher().fireFileFinished(fileName);
    }

    /**
     * Register a check for a given configuration.
     * @param aCheck the check to register
     * @throws CheckstyleException if an error occurs
     */
    void registerCheck(Check aCheck)
           throws CheckstyleException
    {
        final Set checkTokens = aCheck.getTokenNames();
        if (!checkTokens.isEmpty()) {
            final int acceptableTokens[] = aCheck.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final Iterator it = checkTokens.iterator();
            while (it.hasNext()) {
                final String token = (String) it.next();
                try {
                    final int tokenId = TokenTypes.getTokenId(token);
                    if (Arrays.binarySearch(acceptableTokens, tokenId) >= 0) {
                        registerCheck(token, aCheck);
                    }
                    // TODO: else error message?
                }
                catch (IllegalArgumentException ex) {
                    throw new CheckstyleException("illegal token \""
                        + token + "\" in check " + aCheck);
                }
            }
        }
        else {
            final int[] tokens = aCheck.getDefaultTokens();
            for (int i = 0; i < tokens.length; i++) {
                registerCheck(tokens[i], aCheck);
            }
        }
        mAllChecks.add(aCheck);
    }

    /**
     * Register a check for a specified token id.
     * @param aTokenID the id of the token
     * @param aCheck the check to register
     */
    private void registerCheck(int aTokenID, Check aCheck)
    {
        registerCheck(TokenTypes.getTokenName(aTokenID), aCheck);
    }

    /**
     * Register a check for a specified token name
     * @param aToken the name of the token
     * @param aCheck the check to register
     */
    private void registerCheck(String aToken, Check aCheck)
    {
        ArrayList visitors = (ArrayList) mTokenToChecks.get(aToken);
        if (visitors == null) {
            visitors = new ArrayList();
            mTokenToChecks.put(aToken, visitors);
        }

        visitors.add(aCheck);
    }

    /**
     * Initiates the walk of an AST.
     * @param aAST the root AST
     * @param aContents the contents of the file the AST was generated from
     */
    private void walk(DetailAST aAST, FileContents aContents)
    {
        mMessages.reset();
        notifyBegin(aContents);

         // empty files are not flagged by javac, will yield aAST == null
        if (aAST != null) {
            aAST.setParent(null);
            process(aAST);
        }

        notifyEnd();
    }

    /**
     * Notify interested checks that about to begin walking a tree.
     * @param aContents the contents of the file the AST was generated from
     */
    private void notifyBegin(FileContents aContents)
    {
        // TODO: do not track Context properly for token
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            final HashMap treeContext = new HashMap();
            check.setTreeContext(treeContext);
            check.setFileContents(aContents);
            check.beginTree();
        }
    }

    /**
     * Notify checks that finished walking a tree.
     */
    private void notifyEnd()
    {
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            check.finishTree();
        }
    }

    /**
     * Recursively processes a node calling interested checks at each node.
     * @param aAST the node to start from
     */
    private void process(DetailAST aAST)
    {
        if (aAST == null) {
            return;
        }

        notifyVisit(aAST);

        final DetailAST child = (DetailAST) aAST.getFirstChild();
        if (child != null) {
            child.setParent(aAST);
            process(child);
        }

        notifyLeave(aAST);

        final DetailAST sibling = (DetailAST) aAST.getNextSibling();
        if (sibling != null) {
            sibling.setParent(aAST.getParent());
            process(sibling);
        }

    }

    /**
     * Notify interested checks that visiting a node.
     * @param aAST the node to notify for
     */
    private void notifyVisit(DetailAST aAST)
    {
        final ArrayList visitors =
            (ArrayList) mTokenToChecks.get(
                TokenTypes.getTokenName(aAST.getType()));
        if (visitors != null) {
            final Map ctx = new HashMap();
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
                check.setTokenContext(ctx);
                check.visitToken(aAST);
            }
        }
    }

    /**
     * Notify interested checks that leaving a node.
     * @param aAST the node to notify for
     */
    private void notifyLeave(DetailAST aAST)
    {
        final ArrayList visitors =
            (ArrayList) mTokenToChecks.get(
                TokenTypes.getTokenName(aAST.getType()));
        if (visitors != null) {
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
                // TODO: need to setup the token context
                check.leaveToken(aAST);
            }
        }
    }

    /**
     * Static helper method to parses a Java source file.
     * @param aContents contains the contents of the file
     * @return the root of the AST
     * @throws TokenStreamException if lexing failed
     * @throws RecognitionException if parsing failed
     */
    public static DetailAST parse(FileContents aContents)
        throws TokenStreamException, RecognitionException
    {
        DetailAST rootAST;
        try {
            // try the 1.4 grammar first, this will succeed for
            // all code that compiles without any warnings in JDK 1.4,
            // that should cover most cases
            final Reader sar = new StringArrayReader(aContents.getLines());
            final GeneratedJava14Lexer jl = new GeneratedJava14Lexer(sar);
            jl.setFilename(aContents.getFilename());
            jl.setFileContents(aContents);

            final GeneratedJava14Recognizer jr =
                new SilentJava14Recognizer(jl);
            jr.setFilename(aContents.getFilename());
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
            final Reader sar = new StringArrayReader(aContents.getLines());
            final GeneratedJavaLexer jl = new GeneratedJavaLexer(sar);
            jl.setFilename(aContents.getFilename());
            jl.setFileContents(aContents);

            final GeneratedJavaRecognizer jr = new GeneratedJavaRecognizer(jl);
            jr.setFilename(aContents.getFilename());
            jr.setASTNodeClass(DetailAST.class.getName());
            jr.compilationUnit();
            rootAST = (DetailAST) jr.getAST();
        }
        return rootAST;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.FileSetCheck */
    public void process(File[] aFiles)
    {
        for (int i = 0; i < aFiles.length; i++) {
            process(aFiles[i]);
        }
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.FileSetCheck
     */
    public void destroy()
    {
        super.destroy();
        mCache.destroy();
    }
}
