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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.checks.AbstractFileSetCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Arrays;
import java.io.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import antlr.RecognitionException;
import antlr.TokenStreamException;

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
    /** the tab width for error reporting */
    private final int mTabWidth;
    /** cache file **/
    private final PropertyCacheFile mCache;
    /**
     * the global configuration.
     * TODO: should only know the treewalker part of the config
     */
    private final GlobalProperties mConfig;

    /**
     * Creates a new <code>TreeWalker</code> instance.
     *
     * @param aConfig the configuration to use
     */
    public TreeWalker(GlobalProperties aConfig)
    {
        mMessages = new LocalizedMessages();
        mConfig = aConfig;
        mTabWidth = aConfig.getTabWidth();
        mCache = new PropertyCacheFile(aConfig);
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
            walk(rootAST, contents, mConfig.getClassLoader());
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
     * @param aConfig the configuration to use
     */
    void registerCheck(Check aCheck, CheckConfiguration aConfig)
    {
        aCheck.setMessages(mMessages);
        aCheck.setTabWidth(mTabWidth);
        if (!aConfig.getTokens().isEmpty()) {
            int acceptableTokens[] = aCheck.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            final Iterator it = aConfig.getTokens().iterator();
            while (it.hasNext()) {
                String token = (String) it.next();
                int tokenId = TokenTypes.getTokenId(token);
                if (Arrays.binarySearch(acceptableTokens, tokenId) >= 0) {
                    registerCheck(token, aCheck);
                }
                // TODO: else error message?
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
     * @param aLoader the class loader for resolving classes
     */
    void walk(DetailAST aAST, FileContents aContents, ClassLoader aLoader)
    {
        mMessages.reset();
        notifyBegin(aContents, aLoader);

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
     * @param aLoader the class loader for resolving classes
     */
    private void notifyBegin(FileContents aContents, ClassLoader aLoader)
    {
        // TODO: do not track Context properly for token
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final Check check = (Check) it.next();
            final HashMap treeContext = new HashMap();
            check.setTreeContext(treeContext);
            check.setFileContents(aContents);
            check.setClassLoader(aLoader);
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
            (ArrayList) mTokenToChecks.get(TokenTypes.getTokenName(
                                               aAST.getType()));
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
            (ArrayList) mTokenToChecks.get(TokenTypes.getTokenName(
                                               aAST.getType()));
        if (visitors != null) {
            for (int i = 0; i < visitors.size(); i++) {
                final Check check = (Check) visitors.get(i);
                // TODO: need to setup the token context
                check.leaveToken(aAST);
            }
        }
    }

    /**
     *
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
