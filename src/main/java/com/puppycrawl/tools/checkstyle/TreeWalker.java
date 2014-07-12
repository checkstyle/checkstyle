////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
import java.io.Reader;
import java.io.StringReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import antlr.CommonHiddenStreamToken;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;
import antlr.TokenStreamHiddenTokenFilter;
import antlr.TokenStreamRecognitionException;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaLexer;
import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaRecognizer;

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
     * State of AST.
     * Indicates whether tree contains certain nodes.
     */
    private static enum AstState {
        /**
         * Ordinary tree.
         */
        ORDINARY,

        /**
         * AST contains comment nodes.
         */
        WITH_COMMENTS
    }

    /** default distance between tab stops */
    private static final int DEFAULT_TAB_WIDTH = 8;

    /** maps from token name to ordinary checks */
    private final Multimap<String, Check> mTokenToOrdinaryChecks =
        HashMultimap.create();

    /** maps from token name to comment checks */
    private final Multimap<String, Check> mTokenToCommentChecks =
            HashMultimap.create();

    /** registered ordinary checks, that don't use comment nodes */
    private final Set<Check> mOrdinaryChecks = Sets.newHashSet();

    /** registered comment checks */
    private final Set<Check> mCommentChecks = Sets.newHashSet();

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

    /** logger for debug purpose */
    private static final Log LOG =
        LogFactory.getLog("com.puppycrawl.tools.checkstyle.TreeWalker");

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

    @Override
    public void finishLocalSetup()
    {
        final DefaultContext checkContext = new DefaultContext();
        checkContext.add("classLoader", mClassLoader);
        checkContext.add("messages", getMessageCollector());
        checkContext.add("severity", getSeverity());
        // TODO: hmmm.. this looks less than elegant
        // we have just parsed the string,
        // now we're recreating it only to parse it again a few moments later
        checkContext.add("tabWidth", String.valueOf(mTabWidth));

        mChildContext = checkContext;
    }

    @Override
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

    @Override
    protected void processFiltered(File aFile, List<String> aLines)
    {
        // check if already checked and passed the file
        final String fileName = aFile.getPath();
        final long timestamp = aFile.lastModified();
        if (mCache.alreadyChecked(fileName, timestamp)) {
            return;
        }

        try {
            final FileText text = FileText.fromLines(aFile, aLines);
            final FileContents contents = new FileContents(text);
            final DetailAST rootAST = TreeWalker.parse(contents);

            getMessageCollector().reset();

            walk(rootAST, contents, AstState.ORDINARY);

            final DetailAST astWithComments = appendHiddenCommentNodes(rootAST);

            walk(astWithComments, contents, AstState.WITH_COMMENTS);
        }
        catch (final RecognitionException re) {
            Utils.getExceptionLogger()
                .debug("RecognitionException occured.", re);
            getMessageCollector().add(
                new LocalizedMessage(
                    re.getLine(),
                    re.getColumn(),
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {re.getMessage()},
                    getId(),
                    this.getClass(), null));
        }
        catch (final TokenStreamRecognitionException tre) {
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
                        getId(),
                        this.getClass(), null));
            }
            else {
                getMessageCollector().add(
                    new LocalizedMessage(
                        0,
                        Defn.CHECKSTYLE_BUNDLE,
                        "general.exception",
                        new String[]
                        {"TokenStreamRecognitionException occured."},
                        getId(),
                        this.getClass(), null));
            }
        }
        catch (final TokenStreamException te) {
            Utils.getExceptionLogger()
                .debug("TokenStreamException occured.", te);
            getMessageCollector().add(
                new LocalizedMessage(
                    0,
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {te.getMessage()},
                    getId(),
                    this.getClass(), null));
        }
        catch (final Throwable err) {
            err.printStackTrace();
            Utils.getExceptionLogger().debug("Throwable occured.", err);
            getMessageCollector().add(
                new LocalizedMessage(
                    0,
                    Defn.CHECKSTYLE_BUNDLE,
                    "general.exception",
                    new String[] {"" + err},
                    getId(),
                    this.getClass(), null));
        }

        if (getMessageCollector().size() == 0) {
            mCache.checkedOk(fileName, timestamp);
        }
    }

    /**
     * Register a check for a given configuration.
     * @param aCheck the check to register
     * @throws CheckstyleException if an error occurs
     */
    private void registerCheck(Check aCheck)
        throws CheckstyleException
    {
        final int[] tokens;
        final Set<String> checkTokens = aCheck.getTokenNames();
        if (!checkTokens.isEmpty()) {
            tokens = aCheck.getRequiredTokens();

            //register configured tokens
            final int acceptableTokens[] = aCheck.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            for (String token : checkTokens) {
                try {
                    final int tokenId = TokenTypes.getTokenId(token);
                    if (Arrays.binarySearch(acceptableTokens, tokenId) >= 0) {
                        registerCheck(token, aCheck);
                    }
                    // TODO: else log warning
                }
                catch (final IllegalArgumentException ex) {
                    throw new CheckstyleException("illegal token \""
                        + token + "\" in check " + aCheck, ex);
                }
            }
        }
        else {
            tokens = aCheck.getDefaultTokens();
        }
        for (int element : tokens) {
            registerCheck(element, aCheck);
        }
        if (aCheck.isCommentNodesRequired()) {
            mCommentChecks.add(aCheck);
        }
        else {
            mOrdinaryChecks.add(aCheck);
        }
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
        if (aCheck.isCommentNodesRequired()) {
            mTokenToCommentChecks.put(aToken, aCheck);
        }
        else if (TokenTypes.isCommentType(aToken)) {
            LOG.warn("Check '"
                    + aCheck.getClass().getName()
                    + "' waits for comment type token ('"
                    + aToken
                    + "') and should override 'isCommentNodesRequred()'"
                    + " method to return 'true'");
        }
        else {
            mTokenToOrdinaryChecks.put(aToken, aCheck);
        }
    }

    /**
     * Initiates the walk of an AST.
     * @param aAST the root AST
     * @param aContents the contents of the file the AST was generated from.
     * @param aAstState state of AST.
     */
    private void walk(DetailAST aAST, FileContents aContents
            , AstState aAstState)
    {
        notifyBegin(aAST, aContents, aAstState);

        // empty files are not flagged by javac, will yield aAST == null
        if (aAST != null) {
            processIter(aAST, aAstState);
        }

        notifyEnd(aAST, aAstState);
    }

    /**
     * Notify checks that we are about to begin walking a tree.
     * @param aRootAST the root of the tree.
     * @param aContents the contents of the file the AST was generated from.
     * @param aAstState state of AST.
     */
    private void notifyBegin(DetailAST aRootAST, FileContents aContents
            , AstState aAstState)
    {
        Set<Check> checks;

        if (aAstState == AstState.WITH_COMMENTS) {
            checks = mCommentChecks;
        }
        else {
            checks = mOrdinaryChecks;
        }

        for (Check ch : checks) {
            ch.setFileContents(aContents);
            ch.beginTree(aRootAST);
        }
    }

    /**
     * Notify checks that we have finished walking a tree.
     * @param aRootAST the root of the tree.
     * @param aAstState state of AST.
     */
    private void notifyEnd(DetailAST aRootAST, AstState aAstState)
    {
        Set<Check> checks;

        if (aAstState == AstState.WITH_COMMENTS) {
            checks = mCommentChecks;
        }
        else {
            checks = mOrdinaryChecks;
        }

        for (Check ch : checks) {
            ch.finishTree(aRootAST);
        }
    }

    /**
     * Notify checks that visiting a node.
     * @param aAST the node to notify for.
     * @param aAstState state of AST.
     */
    private void notifyVisit(DetailAST aAST, AstState aAstState)
    {
        Collection<Check> visitors;
        final String tokenType = TokenTypes.getTokenName(aAST.getType());

        if (aAstState == AstState.WITH_COMMENTS) {
            visitors = mTokenToCommentChecks.get(tokenType);
        }
        else {
            visitors = mTokenToOrdinaryChecks.get(tokenType);
        }

        for (Check c : visitors) {
            c.visitToken(aAST);
        }
    }

    /**
     * Notify checks that leaving a node.
     * @param aAST
     *        the node to notify for
     * @param aAstState state of AST.
     */
    private void notifyLeave(DetailAST aAST, AstState aAstState)
    {
        Collection<Check> visitors;
        final String tokenType = TokenTypes.getTokenName(aAST.getType());

        if (aAstState == AstState.WITH_COMMENTS) {
            visitors = mTokenToCommentChecks.get(tokenType);
        }
        else {
            visitors = mTokenToOrdinaryChecks.get(tokenType);
        }

        for (Check ch : visitors) {
            ch.leaveToken(aAST);
        }
    }

    /**
     * Static helper method to parses a Java source file.
     *
     * @param aContents
     *                contains the contents of the file
     * @throws TokenStreamException
     *                 if lexing failed
     * @throws RecognitionException
     *                 if parsing failed
     * @return the root of the AST
     */
    public static DetailAST parse(FileContents aContents)
        throws RecognitionException, TokenStreamException
    {
        final String fullText = aContents.getText().getFullText().toString();
        final Reader sr = new StringReader(fullText);
        final GeneratedJavaLexer lexer = new GeneratedJavaLexer(sr);
        lexer.setFilename(aContents.getFilename());
        lexer.setCommentListener(aContents);
        lexer.setTreatAssertAsKeyword(true);
        lexer.setTreatEnumAsKeyword(true);
        lexer.setTokenObjectClass("antlr.CommonHiddenStreamToken");

        final TokenStreamHiddenTokenFilter filter =
                new TokenStreamHiddenTokenFilter(lexer);
        filter.hide(TokenTypes.SINGLE_LINE_COMMENT);
        filter.hide(TokenTypes.BLOCK_COMMENT_BEGIN);

        final GeneratedJavaRecognizer parser =
            new GeneratedJavaRecognizer(filter);
        parser.setFilename(aContents.getFilename());
        parser.setASTNodeClass(DetailAST.class.getName());
        parser.compilationUnit();

        return (DetailAST) parser.getAST();
    }

    @Override
    public void destroy()
    {
        for (Check c : mOrdinaryChecks) {
            c.destroy();
        }
        for (Check c : mCommentChecks) {
            c.destroy();
        }
        mCache.destroy();
        super.destroy();
    }

    /**
     * Processes a node calling interested checks at each node.
     * Uses iterative algorithm.
     * @param aRoot the root of tree for process
     * @param aAstState state of AST.
     */
    private void processIter(DetailAST aRoot, AstState aAstState)
    {
        DetailAST curNode = aRoot;
        while (curNode != null) {
            notifyVisit(curNode, aAstState);
            DetailAST toVisit = curNode.getFirstChild();
            while ((curNode != null) && (toVisit == null)) {
                notifyLeave(curNode, aAstState);
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }
            curNode = toVisit;
        }
    }

    /**
     * Appends comment nodes to existing AST.
     * It traverses each node in AST, looks for hidden comment tokens
     * and appends found comment tokens as nodes in AST.
     * @param aRoot
     *        root of AST.
     * @return root of AST with comment nodes.
     */
    private static DetailAST appendHiddenCommentNodes(DetailAST aRoot)
    {
        DetailAST result = aRoot;
        DetailAST curNode = aRoot;
        DetailAST lastNode = aRoot;

        while (curNode != null) {
            if (isPositionGreater(curNode, lastNode)) {
                lastNode = curNode;
            }

            CommonHiddenStreamToken tokenBefore = curNode.getHiddenBefore();
            DetailAST currentSibling = curNode;
            while (tokenBefore != null) { // threat multiple comments
                final DetailAST newCommentNode =
                         createCommentAstFromToken(tokenBefore);

                currentSibling.addPreviousSibling(newCommentNode);

                if (currentSibling == result) {
                    result = newCommentNode;
                }

                currentSibling = newCommentNode;
                tokenBefore = tokenBefore.getHiddenBefore();
            }

            DetailAST toVisit = curNode.getFirstChild();
            while ((curNode != null) && (toVisit == null)) {
                toVisit = curNode.getNextSibling();
                if (toVisit == null) {
                    curNode = curNode.getParent();
                }
            }
            curNode = toVisit;
        }
        if (lastNode != null) {
            CommonHiddenStreamToken tokenAfter = lastNode.getHiddenAfter();
            DetailAST currentSibling = lastNode;
            while (tokenAfter != null) {
                final DetailAST newCommentNode =
                        createCommentAstFromToken(tokenAfter);

                currentSibling.addNextSibling(newCommentNode);

                currentSibling = newCommentNode;
                tokenAfter = tokenAfter.getHiddenAfter();
            }
        }
        return result;
    }

    /**
     * Checks if position of first DetailAST is greater than position of
     * second DetailAST. Position is line number and column number in source
     * file.
     * @param aAST1
     *        first DetailAST node.
     * @param aAst2
     *        second DetailAST node.
     * @return true if position of aAst1 is greater than position of aAst2.
     */
    private static boolean isPositionGreater(DetailAST aAST1, DetailAST aAst2)
    {
        if (aAST1.getLineNo() > aAst2.getLineNo()) {
            return true;
        }
        else if (aAST1.getLineNo() < aAst2.getLineNo()) {
            return false;
        }
        else {
            if (aAST1.getColumnNo() > aAst2.getColumnNo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create comment AST from token. Depending on token type
     * SINGLE_LINE_COMMENT or BLOCK_COMMENT_BEGIN is created.
     * @param aToken
     *        Token object.
     * @return DetailAST of comment node.
     */
    private static DetailAST createCommentAstFromToken(Token aToken)
    {
        switch (aToken.getType()) {
        case TokenTypes.SINGLE_LINE_COMMENT:
            return createSlCommentNode(aToken);
        case TokenTypes.BLOCK_COMMENT_BEGIN:
            return createBlockCommentNode(aToken);
        default:
            throw new IllegalArgumentException("Unknown comment type");
        }
    }

    /**
     * Create single-line comment from token.
     * @param aToken
     *        Token object.
     * @return DetailAST with SINGLE_LINE_COMMENT type.
     */
    private static DetailAST createSlCommentNode(Token aToken)
    {
        final DetailAST slComment = new DetailAST();
        slComment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        slComment.setText("//");

        // column counting begins from 0
        slComment.setColumnNo(aToken.getColumn() - 1);
        slComment.setLineNo(aToken.getLine());

        final DetailAST slCommentContent = new DetailAST();
        slCommentContent.initialize(aToken);
        slCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // column counting begins from 0
        // plus length of '//'
        slCommentContent.setColumnNo(aToken.getColumn() - 1 + 2);
        slCommentContent.setLineNo(aToken.getLine());
        slCommentContent.setText(aToken.getText());

        slComment.addChild(slCommentContent);
        return slComment;
    }

    /**
     * Create block comment from token.
     * @param aToken
     *        Token object.
     * @return DetailAST with BLOCK_COMMENT type.
     */
    private static DetailAST createBlockCommentNode(Token aToken)
    {
        final DetailAST blockComment = new DetailAST();
        blockComment.initialize(TokenTypes.BLOCK_COMMENT_BEGIN, "/*");

        // column counting begins from 0
        blockComment.setColumnNo(aToken.getColumn() - 1);
        blockComment.setLineNo(aToken.getLine());

        final DetailAST blockCommentContent = new DetailAST();
        blockCommentContent.initialize(aToken);
        blockCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // column counting begins from 0
        // plus length of '/*'
        blockCommentContent.setColumnNo(aToken.getColumn() - 1 + 2);
        blockCommentContent.setLineNo(aToken.getLine());
        blockCommentContent.setText(aToken.getText());

        final DetailAST blockCommentClose = new DetailAST();
        blockCommentClose.initialize(TokenTypes.BLOCK_COMMENT_END, "*/");

        final Entry<Integer, Integer> linesColumns = countLinesColumns(
                aToken.getText(), aToken.getLine(), aToken.getColumn());
        blockCommentClose.setLineNo(linesColumns.getKey());
        blockCommentClose.setColumnNo(linesColumns.getValue());

        blockComment.addChild(blockCommentContent);
        blockComment.addChild(blockCommentClose);
        return blockComment;
    }

    /**
     * Count lines and columns (in last line) in text.
     * @param aText
     *        String.
     * @param aInitialLinesCnt
     *        initial value of lines counter.
     * @param aInitialColumnsCnt
     *        initial value of columns counter.
     * @return entry(pair), first element is lines counter, second - columns
     *         counter.
     */
    private static Entry<Integer, Integer> countLinesColumns(
            String aText, int aInitialLinesCnt, int aInitialColumnsCnt)
    {
        int lines = aInitialLinesCnt;
        int columns = aInitialColumnsCnt;
        for (char c : aText.toCharArray()) {
            switch (c) {
            case '\n':
                lines++;
                columns = 0;
                break;
            default:
                columns++;
            }
        }
        return new SimpleEntry<Integer, Integer>(lines, columns);
    }
}
