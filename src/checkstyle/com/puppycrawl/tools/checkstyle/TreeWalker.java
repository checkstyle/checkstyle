////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
import antlr.TokenStreamRecognitionException;
import antlr.TokenStream;

import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaRecognizer;
import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaLexer;

/**
 * Responsible for walking an abstract syntax tree and notifying interested
 * checks at each each node.
 *
 * @author Oliver Burn
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
    private static final class SilentJavaRecognizer
        extends GeneratedJavaRecognizer
    {
        /**
         * Creates a new <code>SilentJavaRecognizer</code> instance.
         *
         * @param aLexer the tokenstream the recognizer operates on.
         */
        public SilentJavaRecognizer(TokenStream aLexer)
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

    /** default distance between tab stops */
    private static final int DEFAULT_TAB_WIDTH = 8;

    /** maps from token name to checks */
    private final Map mTokenToChecks = new HashMap();
    /** all the registered checks */
    private final Set mAllChecks = new HashSet();
    /** the distance between tab stops */
    private int mTabWidth = DEFAULT_TAB_WIDTH;
    /** cache file **/
    private PropertyCacheFile mCache = new PropertyCacheFile(null, null);

    /** class loader to resolve classes with. **/
    private ClassLoader mClassLoader;

    /** context of child components */
    private Context mChildContext;

    /** a factory for creating submodules (i.e. the Checks) */
    private ModuleFactory mModuleFactory;

    /**
     * Creates a new <code>TreeWalker</code> instance.
     */
    public TreeWalker()
    {
        setFileExtensions(new String[]{"java"});
    }

    /** @param aTabWidth the distance between tab stops */
    public void setTabWidth(int aTabWidth)
    {
        mTabWidth = aTabWidth;
    }

    /** @param aFileName the cache file */
    public void setCacheFile(String aFileName)
    {
        final Configuration configuration = getConfiguration();
        mCache = new PropertyCacheFile(configuration, aFileName);
    }

    /** @param aClassLoader class loader to resolve classes with. */
    public void setClassLoader(ClassLoader aClassLoader)
    {
        mClassLoader = aClassLoader;
    }

    /**
     * Sets the module factory for creating child modules (Checks).
     * @param aModuleFactory the factory
     */
    public void setModuleFactory(ModuleFactory aModuleFactory)
    {
        mModuleFactory = aModuleFactory;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Configurable */
    public void finishLocalSetup()
    {
        DefaultContext checkContext = new DefaultContext();
        checkContext.add("classLoader", mClassLoader);
        checkContext.add("messages", getMessageCollector());
        checkContext.add("severity", getSeverity());
        // TODO: hmmm.. this looks less than elegant
        // we have just parsed the string,
        // now we're recreating it only to parse it again a few moments later
        checkContext.add("tabWidth", String.valueOf(mTabWidth));

        mChildContext = checkContext;
    }

    /**
     * Instantiates, configures and registers a Check that is specified
     * in the provided configuration.
     * @see com.puppycrawl.tools.checkstyle.api.AutomaticBean
     */
    public void setupChild(Configuration aChildConf)
        throws CheckstyleException
    {
        // TODO: improve the error handing
        final String name = aChildConf.getName();
        final Object module = mModuleFactory.createModule(name);
        if (!(module instanceof Check)) {
            throw new CheckstyleException(
                "TreeWalker is not allowed as a parent of " + name);
        }
        final Check c = (Check) module;
        c.contextualize(mChildContext);
        c.configure(aChildConf);
        c.init();

        registerCheck(c);
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

        try {
            getMessageDispatcher().fireFileStarted(fileName);
            final String[] lines = Utils.getLines(fileName, getCharset());
            final FileContents contents = new FileContents(fileName, lines);
            final DetailAST rootAST =
                TreeWalker.parse(contents);
            walk(rootAST, contents);
        }
        catch (FileNotFoundException fnfe) {
            Utils.getExceptionLogger()
                .debug("FileNotFoundException occured.", fnfe);
            getMessageCollector().add(
                new LocalizedMessage(
                    0,
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.fileNotFound",
                    null,
                    this.getClass()));
        }
        catch (IOException ioe) {
            Utils.getExceptionLogger().debug("IOException occured.", ioe);
            getMessageCollector().add(
                new LocalizedMessage(
                    0,
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {ioe.getMessage()},
                    this.getClass()));
        }
        catch (RecognitionException re) {
            Utils.getExceptionLogger()
                .debug("RecognitionException occured.", re);
            getMessageCollector().add(
                new LocalizedMessage(
                    re.getLine(),
                    re.getColumn(),
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {re.getMessage()},
                    this.getClass()));
        }
        catch (TokenStreamRecognitionException tre) {
            Utils.getExceptionLogger()
                .debug("TokenStreamRecognitionException occured.", tre);
            final RecognitionException re = tre.recog;
            if (re != null) {
                getMessageCollector().add(
                    new LocalizedMessage(
                        re.getLine(),
                        re.getColumn(),
                        Defn.CHECKSTYLE_BUNDLE,
                        "general.exception",
                        new String[] {re.getMessage()},
                        this.getClass()));
            }
            else {
                getMessageCollector().add(
                    new LocalizedMessage(
                        0,
                        Defn.CHECKSTYLE_BUNDLE,
                        "general.exception",
                        new String[]
                        {"TokenStreamRecognitionException occured."},
                        this.getClass()));
            }
        }
        catch (TokenStreamException te) {
            Utils.getExceptionLogger()
                .debug("TokenStreamException occured.", te);
            getMessageCollector().add(
                new LocalizedMessage(
                    0,
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {te.getMessage()},
                    this.getClass()));
        }
        catch (Throwable err) {
            Utils.getExceptionLogger().debug("Throwable occured.", err);
            getMessageCollector().add(
                new LocalizedMessage(
                    0,
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {"" + err},
                    this.getClass()));
        }

        if (getMessageCollector().size() == 0) {
            mCache.checkedOk(fileName, timestamp);
        }
        else {
            fireErrors(fileName);
        }

        getMessageDispatcher().fireFileFinished(fileName);
    }

    /**
     * Register a check for a given configuration.
     * @param aCheck the check to register
     * @throws CheckstyleException if an error occurs
     */
    private void registerCheck(Check aCheck)
        throws CheckstyleException
    {
        int[] tokens = new int[] {}; //safety initialization
        final Set checkTokens = aCheck.getTokenNames();
        if (!checkTokens.isEmpty()) {
            tokens = aCheck.getRequiredTokens();

            //register configured tokens
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
                    // TODO: else log warning
                }
                catch (IllegalArgumentException ex) {
                    throw new CheckstyleException("illegal token \""
                        + token + "\" in check " + aCheck, ex);
                }
            }
        }
        else {
            tokens = aCheck.getDefaultTokens();
        }
        for (int i = 0; i < tokens.length; i++) {
            registerCheck(tokens[i], aCheck);
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
        getMessageCollector().reset();
        notifyBegin(aAST, aContents);

         // empty files are not flagged by javac, will yield aAST == null
        if (aAST != null) {
            process(aAST);
        }

        notifyEnd(aAST);
    }


    /**
     * Notify interested checks that about to begin walking a tree.
     * @param aRootAST the root of the tree
     * @param aContents the contents of the file the AST was generated from
     */
    private void notifyBegin(DetailAST aRootAST, FileContents aContents)
    {
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            check.setFileContents(aContents);
            check.beginTree(aRootAST);
        }
    }

    /**
     * Notify checks that finished walking a tree.
     * @param aRootAST the root of the tree
     */
    private void notifyEnd(DetailAST aRootAST)
    {
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            check.finishTree(aRootAST);
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
            process(child);
        }

        notifyLeave(aAST);

        final DetailAST sibling = (DetailAST) aAST.getNextSibling();
        if (sibling != null) {
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
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
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
                check.leaveToken(aAST);
            }
        }
    }

    /**
     * Static helper method to parses a Java source file.
     * @param aContents contains the contents of the file
     * @throws TokenStreamException if lexing failed
     * @throws RecognitionException if parsing failed
     * @return the root of the AST
     */
    public static DetailAST parse(
        FileContents aContents)
        throws RecognitionException, TokenStreamException
    {
        DetailAST rootAST = null;

        try {
            rootAST = parse(aContents, true, true, true);
        }
        catch (RecognitionException exception) {
            try {
                rootAST = parse(aContents, true, true, false);
            }
            catch (RecognitionException exception2) {
                rootAST = parse(aContents, false, false, false);
            }
        }
        return rootAST;
    }

    /**
     * Static helper method to parses a Java source file with a given
     * lexer class and parser class.
     * @param aContents contains the contents of the file
     * @param aSilentlyConsumeErrors flag to output errors to stdout or not
     * @param aTreatAssertAsKeyword flag to treat 'assert' as a keyowrd
     * @param aTreatEnumAsKeyword flag to treat 'enum' as a keyowrd
     * @throws TokenStreamException if lexing failed
     * @throws RecognitionException if parsing failed
     * @return the root of the AST
     */
    private static DetailAST parse(
        FileContents aContents,
        boolean aSilentlyConsumeErrors,
        boolean aTreatAssertAsKeyword,
        boolean aTreatEnumAsKeyword)
        throws RecognitionException, TokenStreamException
    {
        final Reader sar = new StringArrayReader(aContents.getLines());
        final GeneratedJavaLexer lexer = new GeneratedJavaLexer(sar);
        lexer.setFilename(aContents.getFilename());
        lexer.setCommentListener(aContents);
        lexer.setTreatAssertAsKeyword(aTreatAssertAsKeyword);
        lexer.setTreatEnumAsKeyword(aTreatEnumAsKeyword);

        final GeneratedJavaRecognizer parser =
            aSilentlyConsumeErrors
                ? new SilentJavaRecognizer(lexer)
                : new GeneratedJavaRecognizer(lexer);
        parser.setFilename(aContents.getFilename());
        parser.setASTNodeClass(DetailAST.class.getName());
        parser.compilationUnit();

        return (DetailAST) parser.getAST();
    }

    /** @see com.puppycrawl.tools.checkstyle.api.FileSetCheck */
    public void process(File[] aFiles)
    {
        final File[] javaFiles = filter(aFiles);

        for (int i = 0; i < javaFiles.length; i++) {
            process(javaFiles[i]);
        }
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.FileSetCheck
     */
    public void destroy()
    {
        for (Iterator it = mAllChecks.iterator(); it.hasNext();) {
            final Check c = (Check) it.next();
            c.destroy();
        }
        mCache.destroy();
        super.destroy();
    }

}
