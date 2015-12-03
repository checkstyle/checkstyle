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
import java.io.Reader;
import java.io.StringReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;

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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaLexer;
import com.puppycrawl.tools.checkstyle.grammars.GeneratedJavaRecognizer;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.TokenUtils;

/**
 * Responsible for walking an abstract syntax tree and notifying interested
 * checks at each each node.
 *
 * @author Oliver Burn
 */
public final class TreeWalker
    extends AbstractFileSetCheck {
    /**
     * State of AST.
     * Indicates whether tree contains certain nodes.
     */
    private enum AstState {
        /**
         * Ordinary tree.
         */
        ORDINARY,

        /**
         * AST contains comment nodes.
         */
        WITH_COMMENTS
    }

    /** Default distance between tab stops. */
    private static final int DEFAULT_TAB_WIDTH = 8;

    /** Maps from token name to ordinary checks. */
    private final Multimap<String, Check> tokenToOrdinaryChecks =
        HashMultimap.create();

    /** Maps from token name to comment checks. */
    private final Multimap<String, Check> tokenToCommentChecks =
            HashMultimap.create();

    /** Registered ordinary checks, that don't use comment nodes. */
    private final Set<Check> ordinaryChecks = Sets.newHashSet();

    /** Registered comment checks. */
    private final Set<Check> commentChecks = Sets.newHashSet();

    /** The distance between tab stops. */
    private int tabWidth = DEFAULT_TAB_WIDTH;

    /** Cache file. **/
    private PropertyCacheFile cache;

    /** Class loader to resolve classes with. **/
    private ClassLoader classLoader;

    /** Context of child components. */
    private Context childContext;

    /** A factory for creating submodules (i.e. the Checks) */
    private ModuleFactory moduleFactory;

    /**
     * Creates a new {@code TreeWalker} instance.
     */
    public TreeWalker() {
        setFileExtensions("java");
    }

    /**
     * Sets tab width.
     * @param tabWidth the distance between tab stops
     */
    public void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    /**
     * Sets cache file.
     * @param fileName the cache file
     * @throws IOException if there are some problems with file loading
     */
    public void setCacheFile(String fileName) throws IOException {
        final Configuration configuration = getConfiguration();
        cache = new PropertyCacheFile(configuration, fileName);

        cache.load();
    }

    /**
     * @param classLoader class loader to resolve classes with.
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Sets the module factory for creating child modules (Checks).
     * @param moduleFactory the factory
     */
    public void setModuleFactory(ModuleFactory moduleFactory) {
        this.moduleFactory = moduleFactory;
    }

    @Override
    public void finishLocalSetup() {
        final DefaultContext checkContext = new DefaultContext();
        checkContext.add("classLoader", classLoader);
        checkContext.add("messages", getMessageCollector());
        checkContext.add("severity", getSeverity());
        checkContext.add("tabWidth", String.valueOf(tabWidth));

        childContext = checkContext;
    }

    @Override
    public void setupChild(Configuration childConf)
        throws CheckstyleException {
        final String name = childConf.getName();
        final Object module = moduleFactory.createModule(name);
        if (!(module instanceof Check)) {
            throw new CheckstyleException(
                "TreeWalker is not allowed as a parent of " + name);
        }
        final Check check = (Check) module;
        check.contextualize(childContext);
        check.configure(childConf);
        check.init();

        registerCheck(check);
    }

    @Override
    protected void processFiltered(File file, List<String> lines) throws CheckstyleException {
        // check if already checked and passed the file
        final String fileName = file.getPath();
        final long timestamp = file.lastModified();
        if (cache != null
                && (cache.isInCache(fileName, timestamp)
                    || !CommonUtils.matchesFileExtension(file, getFileExtensions()))) {
            return;
        }

        final String msg = "%s occurred during the analysis of file %s.";

        try {
            final FileText text = FileText.fromLines(file, lines);
            final FileContents contents = new FileContents(text);
            final DetailAST rootAST = parse(contents);

            getMessageCollector().reset();

            walk(rootAST, contents, AstState.ORDINARY);

            final DetailAST astWithComments = appendHiddenCommentNodes(rootAST);

            walk(astWithComments, contents, AstState.WITH_COMMENTS);
        }
        catch (final TokenStreamRecognitionException tre) {
            final String exceptionMsg = String.format(Locale.ROOT, msg,
                    "TokenStreamRecognitionException", fileName);
            throw new CheckstyleException(exceptionMsg, tre);
        }
        catch (RecognitionException | TokenStreamException ex) {
            final String exceptionMsg = String.format(Locale.ROOT, msg,
                    ex.getClass().getSimpleName(), fileName);
            throw new CheckstyleException(exceptionMsg, ex);
        }

        if (cache != null && getMessageCollector().size() == 0) {
            cache.put(fileName, timestamp);
        }
    }

    /**
     * Register a check for a given configuration.
     * @param check the check to register
     * @throws CheckstyleException if an error occurs
     */
    private void registerCheck(Check check)
        throws CheckstyleException {
        validateDefaultTokens(check);
        final int[] tokens;
        final Set<String> checkTokens = check.getTokenNames();
        if (checkTokens.isEmpty()) {
            tokens = check.getDefaultTokens();
        }
        else {
            tokens = check.getRequiredTokens();

            //register configured tokens
            final int[] acceptableTokens = check.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            for (String token : checkTokens) {
                final int tokenId = TokenUtils.getTokenId(token);
                if (Arrays.binarySearch(acceptableTokens, tokenId) >= 0) {
                    registerCheck(token, check);
                }
                else {
                    final String message = String.format(Locale.ROOT, "Token \"%s\" was "
                            + "not found in Acceptable tokens list in check %s",
                            token, check.getClass().getName());
                    throw new CheckstyleException(message);
                }
            }
        }
        for (int element : tokens) {
            registerCheck(element, check);
        }
        if (check.isCommentNodesRequired()) {
            commentChecks.add(check);
        }
        else {
            ordinaryChecks.add(check);
        }
    }

    /**
     * Register a check for a specified token id.
     * @param tokenId the id of the token
     * @param check the check to register
     * @throws CheckstyleException if Check is misconfigured
     */
    private void registerCheck(int tokenId, Check check) throws CheckstyleException {
        registerCheck(TokenUtils.getTokenName(tokenId), check);
    }

    /**
     * Register a check for a specified token name.
     * @param token the name of the token
     * @param check the check to register
     * @throws CheckstyleException if Check is misconfigured
     */
    private void registerCheck(String token, Check check) throws CheckstyleException {
        if (check.isCommentNodesRequired()) {
            tokenToCommentChecks.put(token, check);
        }
        else if (TokenUtils.isCommentType(token)) {
            final String message = String.format(Locale.ROOT, "Check '%s' waits for comment type "
                    + "token ('%s') and should override 'isCommentNodesRequired()' "
                    + "method to return 'true'", check.getClass().getName(), token);
            throw new CheckstyleException(message);
        }
        else {
            tokenToOrdinaryChecks.put(token, check);
        }
    }

    /**
     * Validates that check's required tokens are subset of default tokens.
     * @param check to validate
     * @throws CheckstyleException when validation of default tokens fails
     */
    private static void validateDefaultTokens(Check check) throws CheckstyleException {
        if (check.getRequiredTokens().length != 0) {
            final int[] defaultTokens = check.getDefaultTokens();
            Arrays.sort(defaultTokens);
            for (final int token : check.getRequiredTokens()) {
                if (Arrays.binarySearch(defaultTokens, token) < 0) {
                    final String message = String.format(Locale.ROOT, "Token \"%s\" from required "
                            + "tokens was not found in default tokens list in check %s",
                            token, check.getClass().getName());
                    throw new CheckstyleException(message);
                }
            }
        }
    }

    /**
     * Initiates the walk of an AST.
     * @param ast the root AST
     * @param contents the contents of the file the AST was generated from.
     * @param astState state of AST.
     */
    private void walk(DetailAST ast, FileContents contents,
            AstState astState) {
        notifyBegin(ast, contents, astState);

        // empty files are not flagged by javac, will yield ast == null
        if (ast != null) {
            processIter(ast, astState);
        }
        notifyEnd(ast, astState);
    }

    /**
     * Notify checks that we are about to begin walking a tree.
     * @param rootAST the root of the tree.
     * @param contents the contents of the file the AST was generated from.
     * @param astState state of AST.
     */
    private void notifyBegin(DetailAST rootAST, FileContents contents,
            AstState astState) {
        Set<Check> checks;

        if (astState == AstState.WITH_COMMENTS) {
            checks = commentChecks;
        }
        else {
            checks = ordinaryChecks;
        }

        for (Check check : checks) {
            check.setFileContents(contents);
            check.beginTree(rootAST);
        }
    }

    /**
     * Notify checks that we have finished walking a tree.
     * @param rootAST the root of the tree.
     * @param astState state of AST.
     */
    private void notifyEnd(DetailAST rootAST, AstState astState) {
        Set<Check> checks;

        if (astState == AstState.WITH_COMMENTS) {
            checks = commentChecks;
        }
        else {
            checks = ordinaryChecks;
        }

        for (Check check : checks) {
            check.finishTree(rootAST);
        }
    }

    /**
     * Notify checks that visiting a node.
     * @param ast the node to notify for.
     * @param astState state of AST.
     */
    private void notifyVisit(DetailAST ast, AstState astState) {
        final Collection<Check> visitors = getListOfChecks(ast, astState);

        if (visitors != null) {
            for (Check check : visitors) {
                check.visitToken(ast);
            }
        }
    }

    /**
     * Notify checks that leaving a node.
     * @param ast
     *        the node to notify for
     * @param astState state of AST.
     */
    private void notifyLeave(DetailAST ast, AstState astState) {
        final Collection<Check> visitors = getListOfChecks(ast, astState);

        if (visitors != null) {
            for (Check check : visitors) {
                check.leaveToken(ast);
            }
        }
    }

    /**
     * Method returns list of checks
     *
     * @param ast
     *            the node to notify for
     * @param astState
     *            state of AST.
     * @return list of visitors
     */
    private Collection<Check> getListOfChecks(DetailAST ast, AstState astState) {
        Collection<Check> visitors = null;
        final String tokenType = TokenUtils.getTokenName(ast.getType());

        if (astState == AstState.WITH_COMMENTS) {
            if (tokenToCommentChecks.containsKey(tokenType)) {
                visitors = tokenToCommentChecks.get(tokenType);
            }
        }
        else {
            if (tokenToOrdinaryChecks.containsKey(tokenType)) {
                visitors = tokenToOrdinaryChecks.get(tokenType);
            }
        }
        return visitors;
    }

    /**
     * Static helper method to parses a Java source file.
     *
     * @param contents
     *                contains the contents of the file
     * @return the root of the AST
     * @throws TokenStreamException
     *                 if lexing failed
     * @throws RecognitionException
     *                 if parsing failed
     */
    public static DetailAST parse(FileContents contents)
        throws RecognitionException, TokenStreamException {
        final String fullText = contents.getText().getFullText().toString();
        final Reader reader = new StringReader(fullText);
        final GeneratedJavaLexer lexer = new GeneratedJavaLexer(reader);
        lexer.setFilename(contents.getFileName());
        lexer.setCommentListener(contents);
        lexer.setTreatAssertAsKeyword(true);
        lexer.setTreatEnumAsKeyword(true);
        lexer.setTokenObjectClass("antlr.CommonHiddenStreamToken");

        final TokenStreamHiddenTokenFilter filter =
                new TokenStreamHiddenTokenFilter(lexer);
        filter.hide(TokenTypes.SINGLE_LINE_COMMENT);
        filter.hide(TokenTypes.BLOCK_COMMENT_BEGIN);

        final GeneratedJavaRecognizer parser =
            new GeneratedJavaRecognizer(filter);
        parser.setFilename(contents.getFileName());
        parser.setASTNodeClass(DetailAST.class.getName());
        parser.compilationUnit();

        return (DetailAST) parser.getAST();
    }

    @Override
    public void destroy() {
        for (Check check : ordinaryChecks) {
            check.destroy();
        }
        for (Check check : commentChecks) {
            check.destroy();
        }
        if (cache != null) {
            try {
                cache.persist();
            }
            catch (IOException ex) {
                throw new IllegalStateException("Unable to persist cache file", ex);
            }
        }
        super.destroy();
    }

    /**
     * Processes a node calling interested checks at each node.
     * Uses iterative algorithm.
     * @param root the root of tree for process
     * @param astState state of AST.
     */
    private void processIter(DetailAST root, AstState astState) {
        DetailAST curNode = root;
        while (curNode != null) {
            notifyVisit(curNode, astState);
            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                notifyLeave(curNode, astState);
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
     * @param root
     *        root of AST.
     * @return root of AST with comment nodes.
     */
    private static DetailAST appendHiddenCommentNodes(DetailAST root) {
        DetailAST result = root;
        DetailAST curNode = root;
        DetailAST lastNode = root;

        while (curNode != null) {
            if (isPositionGreater(curNode, lastNode)) {
                lastNode = curNode;
            }

            CommonHiddenStreamToken tokenBefore = curNode.getHiddenBefore();
            DetailAST currentSibling = curNode;
            while (tokenBefore != null) {
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
            while (curNode != null && toVisit == null) {
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
     * @param ast1
     *        first DetailAST node.
     * @param ast2
     *        second DetailAST node.
     * @return true if position of ast1 is greater than position of ast2.
     */
    private static boolean isPositionGreater(DetailAST ast1, DetailAST ast2) {
        if (ast1.getLineNo() == ast2.getLineNo()) {
            return ast1.getColumnNo() > ast2.getColumnNo();
        }
        else {
            return ast1.getLineNo() > ast2.getLineNo();
        }
    }

    /**
     * Create comment AST from token. Depending on token type
     * SINGLE_LINE_COMMENT or BLOCK_COMMENT_BEGIN is created.
     * @param token
     *        Token object.
     * @return DetailAST of comment node.
     */
    private static DetailAST createCommentAstFromToken(Token token) {
        if (token.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            return createSlCommentNode(token);
        }
        else {
            return createBlockCommentNode(token);
        }
    }

    /**
     * Create single-line comment from token.
     * @param token
     *        Token object.
     * @return DetailAST with SINGLE_LINE_COMMENT type.
     */
    private static DetailAST createSlCommentNode(Token token) {
        final DetailAST slComment = new DetailAST();
        slComment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        slComment.setText("//");

        // column counting begins from 0
        slComment.setColumnNo(token.getColumn() - 1);
        slComment.setLineNo(token.getLine());

        final DetailAST slCommentContent = new DetailAST();
        slCommentContent.initialize(token);
        slCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // column counting begins from 0
        // plus length of '//'
        slCommentContent.setColumnNo(token.getColumn() - 1 + 2);
        slCommentContent.setLineNo(token.getLine());
        slCommentContent.setText(token.getText());

        slComment.addChild(slCommentContent);
        return slComment;
    }

    /**
     * Create block comment from token.
     * @param token
     *        Token object.
     * @return DetailAST with BLOCK_COMMENT type.
     */
    private static DetailAST createBlockCommentNode(Token token) {
        final DetailAST blockComment = new DetailAST();
        blockComment.initialize(TokenTypes.BLOCK_COMMENT_BEGIN, "/*");

        // column counting begins from 0
        blockComment.setColumnNo(token.getColumn() - 1);
        blockComment.setLineNo(token.getLine());

        final DetailAST blockCommentContent = new DetailAST();
        blockCommentContent.initialize(token);
        blockCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // column counting begins from 0
        // plus length of '/*'
        blockCommentContent.setColumnNo(token.getColumn() - 1 + 2);
        blockCommentContent.setLineNo(token.getLine());
        blockCommentContent.setText(token.getText());

        final DetailAST blockCommentClose = new DetailAST();
        blockCommentClose.initialize(TokenTypes.BLOCK_COMMENT_END, "*/");

        final Entry<Integer, Integer> linesColumns = countLinesColumns(
                token.getText(), token.getLine(), token.getColumn());
        blockCommentClose.setLineNo(linesColumns.getKey());
        blockCommentClose.setColumnNo(linesColumns.getValue());

        blockComment.addChild(blockCommentContent);
        blockComment.addChild(blockCommentClose);
        return blockComment;
    }

    /**
     * Count lines and columns (in last line) in text.
     * @param text
     *        String.
     * @param initialLinesCnt
     *        initial value of lines counter.
     * @param initialColumnsCnt
     *        initial value of columns counter.
     * @return entry(pair), first element is lines counter, second - columns
     *         counter.
     */
    private static Entry<Integer, Integer> countLinesColumns(
            String text, int initialLinesCnt, int initialColumnsCnt) {
        int lines = initialLinesCnt;
        int columns = initialColumnsCnt;
        for (char c : text.toCharArray()) {
            if (c == '\n') {
                lines++;
                columns = 0;
            }
            else {
                columns++;
            }
        }
        return new SimpleEntry<>(lines, columns);
    }

}
