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

package com.puppycrawl.tools.checkstyle.filters;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.DetailAstRootHolder;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * <p>
 * A filter that uses annotations to suppress audit events.
 * </p>
 * <p>
 * Structurally based on SupressionCommentFilter.
 * </p>
 *      <p>
 *      Usage: This check only works in conjunction with the DetailASTRootHolder module since that
 *      module holds weak reference on actual AST.
 *      </p>
 * @author attatrol.
 * @see com.puppycrawl.tools.checkstyle.filters.SupressionCommentFilter
 */
public class SuppressionAnnotationFilter extends AutomaticBean implements Filter {

    /**
     * Pattern for checking input annotation names
     */
    private static final Pattern VALID_NAME = Pattern
            .compile("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*");

    /**
     * Default value of excludeAnnotations, if parameter is missing in configuration.
     */
    private static final boolean DEFAULT_MODIFIERS_EXCLUDED = true;

    /** Set containing names of suppressive annotations. */
    private Set<String> annotationNameSet = Sets.newHashSet();

    /** Set containing simple derivatives from fully-qualified input names of annotations */
    private Set<String> annotationSimpleNameSet = Sets.newHashSet();

    /** The check format to suppress. */
    private Set<String> permittedChecksSet = Sets.newHashSet();

    /** Excludes annotations from check if set true. */
    private boolean modifiersExcluded = DEFAULT_MODIFIERS_EXCLUDED;

    /** The parsed check regexp, expanded for the text of this tag. */
    private transient List<Pattern> checkRegexp = Lists.newArrayList();

    /** ranges of tokens being suppressed. */
    private List<SuppressRange> ranges = Lists.newArrayList();

    /**
     * Reference to the root of the current AST. Since this is a weak reference to the AST, it can
     * be reclaimed as soon as the strong references in TreeWalker are reassigned to the next AST,
     * at which time filtering for the current AST is finished.
     */
    private WeakReference<DetailAST> currentASTRootRef = new WeakReference<>(null);

    /**
     * Dummy getter for super.configure.
     * @return not in use.
     */
    public String[] getAnnotationName() {
        return new String[0];
    }

    /**
     * Sets the names of suppressive annotations, handles presence or absence of 'at' starting
     * symbol.
     * @param annotations
     *        , the set of file extensions.
     */
    public void setAnnotationName(String... annotations) {
        for (String s : annotations) {
            if (Utils.startsWithChar(s, '@')) {
                s = s.substring(1);
            }
            annotationNameSet.add(s);
            final Matcher goodName = VALID_NAME.matcher(s);
            if (!goodName.matches()) {
                throw new ConversionException("Bad name for annotation.");
            }
            final int lastIndexOfPoint = s.lastIndexOf('.');
            annotationSimpleNameSet.add(s.substring(lastIndexOfPoint + 1));
        }
    }

    /**
     * Getter for annotationsExcluded.
     * @return excludeAnnotations, excludes annotations from check if set true.
     */
    public boolean isModifiersExcluded() {
        return modifiersExcluded;
    }

    /**
     * Setter for annotationsExcluded.
     * @param format
     *        , set value for excludeAnnotations
     */
    public void setModifiersExcluded(boolean format) {
        modifiersExcluded = format;
    }

    /**
     * Dummy getter for super.configure.
     * @return not in use.
     */
    public String[] getPermittedChecks() {
        return new String[0];

    }

    /**
     * Setter for permittedCheckFormat.
     * @param checks
     *        , contains patterns for non-suppressed checks.
     */
    public void setPermittedChecks(String... checks) {
        for (String s : checks) {
            permittedChecksSet.add(s);
        }
    }

    /**
     * {@inheritDoc} Creates checkRegexp from checkFormat.
     */
    @Override
    protected void finishLocalSetup()
            throws CheckstyleException {
        for (String s : permittedChecksSet) {
            try {
                checkRegexp.add(Pattern.compile(s));
            }
            catch (final PatternSyntaxException e) {
                throw new ConversionException("unable to parse expanded comment " + s, e);
            }
        }
    }

    /**
     * @return checkRegexp, pattern for non-suppressed checks.
     */
    public List<Pattern> getCheckRegexp() {
        return Lists.newArrayList(checkRegexp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(AuditEvent event) {
        if (event.getLocalizedMessage() == null) {
            return true; // An event without message.
        }
        if (isPermittedCheck(event)) {
            return true;
        }
        final DetailAST currentRoot = DetailAstRootHolder.getASTRootReference();
        if (currentRoot == null) {
            return true;
        }
        // Lazy update. If weak reference from DetailASTRootHolder gets update
        // then we reset ranges of suppression according to new AST.
        final DetailAST localRoot = currentASTRootRef.get();
        if (currentRoot != localRoot) {
            currentASTRootRef =
                    new WeakReference<DetailAST>(DetailAstRootHolder.getASTRootReference());
            ranges.clear();
            resetRanges(currentRoot);
        }
        return !isIncludedInSuppressedRanges(event);
    }

    /**
     * Checks if event coordinates belong to some range in the set.
     * @param event
     *        , audited event.
     * @return result of this check.
     */
    private boolean isIncludedInSuppressedRanges(AuditEvent event) {
        final int eventColumn = event.getColumn();
        final int eventLine = event.getLine();
        for (SuppressRange range : ranges) {
            if (range.isInRange(eventLine, eventColumn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates new ranges, where audit events are suppressed.
     * @param currentRoot
     *        , AST tree node, should be the root node.
     */
    private void resetRanges(DetailAST currentRoot) {
        // it was already checked that node != null,
        // in accept method, in case of root AST.
        if (!findSuppressiveAnnotation(currentRoot)) {
            final DetailAST child = currentRoot.getFirstChild();
            if (child != null) {
                resetRanges(child);
            }
        }
        final DetailAST sibling = currentRoot.getNextSibling();
        if (sibling != null) {
            resetRanges(sibling);
        }
    }

    /**
     * Checks if this node has suppressive annotation, if it has, adds range occupied by it into
     * range list.
     * @param node
     *        , AST tree node.
     * @return if node was suppressed.
     */
    private boolean findSuppressiveAnnotation(DetailAST node) {
        final DetailAST possibleModifiers = node.findFirstToken(TokenTypes.MODIFIERS);
        if (possibleModifiers != null) {
            DetailAST possibleAnnotation = possibleModifiers.findFirstToken(TokenTypes.ANNOTATION);
            while (possibleAnnotation != null) {
                if (possibleAnnotation.getType() == TokenTypes.ANNOTATION) {
                    final Pair<String, String> names = getAnnotationName(possibleAnnotation);
                    if (annotationNameSet.contains(names.getLeft())
                            || annotationNameSet.contains(names.getRight())
                            // this check means that any simple name will be processed
                            // if it could be derivative from fully-qualified input name
                            || annotationSimpleNameSet.contains(names.getRight())) {
                        addSuppressRange(node);
                        return true;
                    }
                }
                possibleAnnotation = possibleAnnotation.getNextSibling();
            }
        }
        return false;
    }

    /**
     * Recovers string names of annotation from tree.
     * @param annotation
     *        , node which is annotation.
     * @return annotation strings, pair of strings, left sting is a simple name and right is a
     *         fully-qualified one, if a simple name used within the argument, then both strings
     *         contain the same simple name.
     */
    private Pair<String, String> getAnnotationName(DetailAST annotation) {
        DetailAST node = annotation.findFirstToken(TokenTypes.AT).getNextSibling();
        final DetailAST returnNode = node;
        while (node.getType() != TokenTypes.IDENT) {
            node = node.getFirstChild();
        }
        String simpleName = node.getText();
        final String qualifiedName;
        if (node.getNextSibling() != null) {
            final StringBuilder qualifiedNameBuilder = new StringBuilder();
            qualifiedNameBuilder.append(simpleName);
            while (node != returnNode) {
                simpleName = node.getNextSibling().getText();
                qualifiedNameBuilder.append('.');
                qualifiedNameBuilder.append(simpleName);
                node = node.getParent();
            }
            qualifiedName = qualifiedNameBuilder.toString();
        }
        else {
            qualifiedName = simpleName;
        }
        return Pair.of(simpleName, qualifiedName);
    }

    /**
     * Generates a range occupied by the node in the list of all ranges which are to suppress.
     * @param node
     *        , that is annotated with suppressive annotation.
     */
    private void addSuppressRange(DetailAST node) {
        final DetailAST startNode = getStartNode(node);
        final int startColumn;
        if (doNotRoundStartLineNumber(node)) {
            startColumn = startNode.getColumnNo();
        }
        else {
            startColumn = 0;
        }
        final int startLine = startNode.getLineNo();

        final DetailAST endNode = getEndNode(node);
        final int endLine = endNode.getLineNo();
        final int endColumn = endNode.getColumnNo() + 1; // +1 is reassurance
        final SuppressRange suppressRange = new SuppressRange(startLine, startColumn, endLine,
                endColumn);
        ranges.add(suppressRange);
    }

    /**
     * Get node which starts suppressed range.
     * @param node
     *         , the node to be suppressed.
     * @return start border node.
     */
    DetailAST getStartNode(DetailAST node) {
        if (isModifiersExcluded()) {
            // it was already checked that grand-child exists.
            return node.getFirstChild().getNextSibling();
        }
        else {
            return node;
        }
    }

    /**
     * Get the rightmost lowest node, which is the end border element of the suppressed node.
     * @param node
     *         , the node to be suppressed.
     * @return end border node.
     */
    DetailAST getEndNode(DetailAST node) {
        DetailAST nextNode = node.getFirstChild();
        if (nextNode == null) {
            return node;
        }
        DetailAST probe = nextNode;
        while (true) {
            probe = probe.getNextSibling();
            if (probe == null) {
                break;
            }
            else {
                nextNode = probe;
            }
        }
        return getEndNode(nextNode);
    }

    /**
     * A lot of checks return only number of the line, and number of the column is zeroed. It is
     * supposed, that code is already formatted, and a chance that events which dont address to the
     * annotated element are generated on one string with the annotated element can be neglected.
     * The obvious exception is a parameter's definition.
     * @param node
     *         , the node to be suppressed.
     * @return true
     *          , if the start colunm index should not be set 0.
     */
    private boolean doNotRoundStartLineNumber(DetailAST node) {
        return node.getType() == TokenTypes.PARAMETER_DEF;
    }

    /**
     * Checks if a check that generated the event, is not suppressed.
     * @param event
     *        , the event under audit.
     * @return result of check.
     */
    private boolean isPermittedCheck(AuditEvent event) {
        boolean result = false;
        for (Pattern pat : getCheckRegexp()) {
            final Matcher matcher = pat.matcher(event.getSourceName());
            result |= matcher.matches();
        }
        return result;
    }

    /**
     * A SuppressRange holds start and end ranges of an element annotated with suppressive
     * annotation.
     */
    public static class SuppressRange {
        /** Index of the first line of a suppressed range. */
        private final int startLine;

        /** Index of the first column of a suppressed range. */
        private final int startColumn;

        /** Index of the last line of a suppressed range. */
        private final int endLine;

        /** Index of the last column of a suppressed range. */
        private final int endColumn;

        /**
         * Default constructor.
         * @param startLine
         *        , index of the first line of a suppressed range.
         * @param startColumn
         *        , index of the first column of a suppressed range.
         * @param endLine
         *        , index of the last line of a suppressed range.
         * @param endColumn
         *        , index of the last column of a suppressed range.
         */
        public SuppressRange(int startLine, int startColumn, int endLine, int endColumn) {
            this.startColumn = startColumn;
            this.startLine = startLine;
            this.endColumn = endColumn;
            this.endLine = endLine;
        }

        /**
         * Checks if coordinates of an event fall in the range.
         * @param eventLine
         *        , index line of the event.
         * @param eventColumn
         *        , index of column of the event.
         * @return result of the check.
         */
        public boolean isInRange(int eventLine, int eventColumn) {
            return (eventLine > startLine || eventLine == startLine && eventColumn >= startColumn)
                    && (eventLine < endLine || eventLine == endLine && eventColumn <= endColumn);
        }
    }
}
