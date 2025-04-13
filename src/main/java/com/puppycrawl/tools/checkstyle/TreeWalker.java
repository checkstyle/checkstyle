///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.*;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Responsible for walking an abstract syntax tree and notifying interested
 * checks at each node.
 *
 */
@FileStatefulCheck
public final class TreeWalker extends AbstractFileSetCheck implements ExternalResourceHolder {

    /** Message to use when an exception occurs and should be printed as a violation. */
    public static final String PARSE_EXCEPTION_MSG = "parse.exception";

    /** Maps from token name to ordinary checks. */
    private final Map<Integer, Set<AbstractCheck>> tokenToOrdinaryChecks =
        new HashMap<>();

    /** Maps from token name to comment checks. */
    private final Map<Integer, Set<AbstractCheck>> tokenToCommentChecks =
            new HashMap<>();

    /** Registered ordinary checks, that don't use comment nodes. */
    private final Set<AbstractCheck> ordinaryChecks = createNewCheckSortedSet();

    /** Registered comment checks. */
    private final Set<AbstractCheck> commentChecks = createNewCheckSortedSet();

    /** The ast filters. */
    private final Set<TreeWalkerFilter> filters = new HashSet<>();

    /** The sorted set of violations. */
    private final SortedSet<Violation> violations = new TreeSet<>();

    /** Context of child components. */
    private Context childContext;

    /** A factory for creating submodules (i.e. the Checks) */
    private ModuleFactory moduleFactory;

    /** Control whether to skip files with Java parsing exceptions. */
    private boolean skipFileOnJavaParseException;

    /** Specify severity Level to log Java parsing exceptions when they are skipped. */
    private SeverityLevel javaParseExceptionSeverity = SeverityLevel.ERROR;

    /**
     * Creates a new {@code TreeWalker} instance.
     */
    public TreeWalker() {
        setFileExtensions("java");
    }

    /**
     * Sets the module factory for creating child modules (Checks).
     *
     * @param moduleFactory the factory
     */
    public void setModuleFactory(ModuleFactory moduleFactory) {
        this.moduleFactory = moduleFactory;
    }

    /**
     * Setter to control whether to skip files with Java parsing exceptions.
     *
     *  @param skipFileOnJavaParseException whether to skip files with Java parsing errors.
     *  @since 10.18.0
     */
    public void setSkipFileOnJavaParseException(boolean skipFileOnJavaParseException) {
        this.skipFileOnJavaParseException = skipFileOnJavaParseException;
    }

    /**
     * Setter to specify the severity level
     * to log Java parsing exceptions when they are skipped.
     *
     *  @param javaParseExceptionSeverity severity level to log parsing exceptions
     *      when they are skipped.
     *  @since 10.18.0
     */
    public void setJavaParseExceptionSeverity(SeverityLevel javaParseExceptionSeverity) {
        this.javaParseExceptionSeverity = javaParseExceptionSeverity;
    }

    @Override
    public void finishLocalSetup() {
        final DefaultContext checkContext = new DefaultContext();
        checkContext.add("severity", getSeverity());
        checkContext.add("tabWidth", String.valueOf(getTabWidth()));
        childContext = checkContext;
    }

    /**
     * {@inheritDoc} Creates child module.
     *
     * @noinspection ChainOfInstanceofChecks
     * @noinspectionreason ChainOfInstanceofChecks - we treat checks and filters differently
     */
    @Override
    public void setupChild(Configuration childConf)
            throws CheckstyleException {
        final Object module = moduleDiscovery(childConf);
        if (module instanceof AbstractCheck) {
            final AbstractCheck check = (AbstractCheck) module;
            check.init();
            registerCheck(check);
        }
        else if (module instanceof TreeWalkerFilter) {
            final TreeWalkerFilter filter = (TreeWalkerFilter) module;
            filters.add(filter);
        } else {
            throw new CheckstyleException("TreeWalker is not allowed as a parent of "
                    + childConf.getName() + ". Please review 'Parent Module' section " +
                    "(parent=\"com.puppycrawl.tools.checkstyle.TreeWalker\") " +
                    "(parent=\"com.puppycrawl.tools.checkstyle.Checker\") " +
                    "for this Check in web documentation if Check is standard.");
        }
    }

    private Object moduleDiscovery(Configuration childConf) throws CheckstyleException {
        final Object module;
        try {
            module = moduleFactory.createModule(childConf.getName());
            if (module instanceof AbstractAutomaticBean) {
                final AbstractAutomaticBean bean = (AbstractAutomaticBean) module;
                bean.contextualize(childContext);
                bean.configure(childConf);
            }
        }
        catch (final CheckstyleException ex) {
            throw new CheckstyleException("cannot initialize module " + childConf.getName()
                    + " - " + ex.getMessage(), ex);
        }
        return module;
    }

    /**
     * {@inheritDoc} Processes the file.
     *
     * @noinspection ProhibitedExceptionThrown
     * @noinspectionreason ProhibitedExceptionThrown - there is no other way to obey
     *     skipFileOnJavaParseException field
     */
    @Override
    protected void processFiltered(File file, FileText fileText) throws CheckstyleException {
        // check if already checked and passed the file
        if (!ordinaryChecks.isEmpty() || !commentChecks.isEmpty()) {
            final FileContents contents = getFileContents();
            DetailAST rootAST = null;
            // whether skip the procedure after parsing Java files.
            boolean skip = false;
            try {
                rootAST = JavaParser.parse(contents);
            }
            // -@cs[IllegalCatch] There is no other way to obey skipFileOnJavaParseException field
            catch (Exception ex) {
                if (!skipFileOnJavaParseException) {
                    throw ex;
                }
                skip = true;
                violations.add(new Violation(1, Definitions.CHECKSTYLE_BUNDLE, PARSE_EXCEPTION_MSG,
                            new Object[] {ex.getMessage()}, javaParseExceptionSeverity, null,
                            getClass(), null));
                addViolations(violations);
            }

            if (!skip) {
                if (!ordinaryChecks.isEmpty()) {
                    walk(rootAST, contents, AstState.ORDINARY);
                }
                if (!commentChecks.isEmpty()) {
                    final DetailAST astWithComments = JavaParser.appendHiddenCommentNodes(rootAST);
                    walk(astWithComments, contents, AstState.WITH_COMMENTS);
                }
                if (filters.isEmpty()) {
                    addViolations(violations);
                }
                else {
                    final SortedSet<Violation> filteredViolations =
                            getFilteredViolations(file.getAbsolutePath(), contents, rootAST);
                    addViolations(filteredViolations);
                }
            }
            violations.clear();
        }
    }

    /**
     * Returns filtered set of {@link Violation}.
     *
     * @param fileName path to the file
     * @param fileContents the contents of the file
     * @param rootAST root AST element {@link DetailAST} of the file
     * @return filtered set of violations
     */
    private SortedSet<Violation> getFilteredViolations(
            String fileName, FileContents fileContents, DetailAST rootAST) {
        final SortedSet<Violation> result = new TreeSet<>(violations);
        for (Violation element : violations) {
            final TreeWalkerAuditEvent event =
                    new TreeWalkerAuditEvent(fileContents, fileName, element, rootAST);
            for (TreeWalkerFilter filter : filters) {
                if (!filter.accept(event)) {
                    result.remove(element);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Register a check for a given configuration.
     *
     * @param check the check to register
     * @throws CheckstyleException if an error occurs
     */
    private void registerCheck(AbstractCheck check) throws CheckstyleException {
        final int[] tokens;
        final Set<String> checkTokens = check.getTokenNames();
        if (checkTokens.isEmpty()) {
            tokens = check.getDefaultTokens();
        }
        else {
            tokens = check.getRequiredTokens();

            // register configured tokens
            final int[] acceptableTokens = check.getAcceptableTokens();
            Arrays.sort(acceptableTokens);
            for (String token : checkTokens) {
                final int tokenId = TokenUtil.getTokenId(token);
                if (Arrays.binarySearch(acceptableTokens, tokenId) >= 0) {
                    registerCheck(tokenId, check);
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
     *
     * @param tokenId the id of the token
     * @param check the check to register
     * @throws CheckstyleException if Check is misconfigured
     */
    private void registerCheck(int tokenId, AbstractCheck check) throws CheckstyleException {
        if (check.isCommentNodesRequired()) {
            tokenToCommentChecks.computeIfAbsent(tokenId, empty -> createNewCheckSortedSet())
                    .add(check);
        }
        else if (TokenUtil.isCommentType(tokenId)) {
            final String message = String.format(Locale.ROOT, "Check '%s' waits for comment type "
                    + "token ('%s') and should override 'isCommentNodesRequired()' "
                    + "method to return 'true'", check.getClass().getName(),
                    TokenUtil.getTokenName(tokenId));
            throw new CheckstyleException(message);
        }
        else {
            tokenToOrdinaryChecks.computeIfAbsent(tokenId, empty -> createNewCheckSortedSet())
                    .add(check);
        }
    }

    /**
     * Initiates the walk of an AST.
     *
     * @param ast the root AST
     * @param contents the contents of the file the AST was generated from.
     * @param astState state of AST.
     */
    private void walk(DetailAST ast, FileContents contents,
            AstState astState) {
        notifyBegin(ast, contents, astState);
        processIter(ast, astState);
        notifyEnd(ast, astState);
    }

    /**
     * Notify checks that we are about to begin walking a tree.
     *
     * @param rootAST the root of the tree.
     * @param contents the contents of the file the AST was generated from.
     * @param astState state of AST.
     */
    private void notifyBegin(DetailAST rootAST, FileContents contents,
            AstState astState) {
        final Set<AbstractCheck> checks;

        if (astState == AstState.WITH_COMMENTS) {
            checks = commentChecks;
        }
        else {
            checks = ordinaryChecks;
        }

        for (AbstractCheck check : checks) {
            check.setFileContents(contents);
            check.clearViolations();
            check.beginTree(rootAST);
        }
    }

    /**
     * Notify checks that we have finished walking a tree.
     *
     * @param rootAST the root of the tree.
     * @param astState state of AST.
     */
    private void notifyEnd(DetailAST rootAST, AstState astState) {
        final Set<AbstractCheck> checks;

        if (astState == AstState.WITH_COMMENTS) {
            checks = commentChecks;
        }
        else {
            checks = ordinaryChecks;
        }

        for (AbstractCheck check : checks) {
            check.finishTree(rootAST);
            violations.addAll(check.getViolations());
        }
    }

    /**
     * Notify checks that visiting a node.
     *
     * @param ast the node to notify for.
     * @param astState state of AST.
     */
    private void notifyVisit(DetailAST ast, AstState astState) {
        final Collection<AbstractCheck> visitors = getListOfChecks(ast, astState);

        if (visitors != null) {
            for (AbstractCheck check : visitors) {
                check.visitToken(ast);
            }
        }
    }

    /**
     * Notify checks that leaving a node.
     *
     * @param ast
     *        the node to notify for
     * @param astState state of AST.
     */
    private void notifyLeave(DetailAST ast, AstState astState) {
        final Collection<AbstractCheck> visitors = getListOfChecks(ast, astState);

        if (visitors != null) {
            for (AbstractCheck check : visitors) {
                check.leaveToken(ast);
            }
        }
    }

    /**
     * Method returns list of checks.
     *
     * @param ast
     *            the node to notify for
     * @param astState
     *            state of AST.
     * @return list of visitors
     */
    private Collection<AbstractCheck> getListOfChecks(DetailAST ast, AstState astState) {
        final Collection<AbstractCheck> visitors;
        final int tokenId = ast.getType();

        if (astState == AstState.WITH_COMMENTS) {
            visitors = tokenToCommentChecks.get(tokenId);
        }
        else {
            visitors = tokenToOrdinaryChecks.get(tokenId);
        }
        return visitors;
    }

    @Override
    public void destroy() {
        ordinaryChecks.forEach(AbstractCheck::destroy);
        commentChecks.forEach(AbstractCheck::destroy);
        super.destroy();
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return Stream.concat(filters.stream(),
                Stream.concat(ordinaryChecks.stream(), commentChecks.stream()))
            .filter(ExternalResourceHolder.class::isInstance)
            .flatMap(resource -> {
                return ((ExternalResourceHolder) resource)
                        .getExternalResourceLocations().stream();
            })
            .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Processes a node calling interested checks at each node.
     * Uses iterative algorithm.
     *
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
                curNode = curNode.getParent();
            }
            curNode = toVisit;
        }
    }

    /**
     * Creates a new {@link SortedSet} with a deterministic order based on the
     * Check's name before the default ordering.
     *
     * @return The new {@link SortedSet}.
     */
    private static SortedSet<AbstractCheck> createNewCheckSortedSet() {
        return new TreeSet<>(
                Comparator.<AbstractCheck, String>comparing(check -> check.getClass().getName())
                        .thenComparing(AbstractCheck::getId,
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparingInt(AbstractCheck::hashCode));
    }

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
        WITH_COMMENTS,

    }

}
