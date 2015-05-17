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

package com.puppycrawl.tools.checkstyle.checks.imports;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Checks for unused import statements.
 * </p>
 *  <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="UnusedImports"/&gt;
 * </pre>
 *
 * Compatible with Java 1.5 source.
 *
 * @author Oliver Burn
 */
public class UnusedImportsCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.unused";

    /** regex to match class names. */
    private static final Pattern CLASS_NAME = Pattern.compile(
           "((:?[\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*)");
    /** regex to match the first class name. */
    private static final Pattern FIRST_CLASS_NAME = Pattern.compile(
           "^" + CLASS_NAME);
    /** regex to match argument names. */
    private static final Pattern ARGUMENT_NAME = Pattern.compile(
           "[(,]\\s*" + CLASS_NAME.pattern());

    /** flag to indicate when time to start collecting references. */
    private boolean collect;
    /** flag whether to process Javdoc comments. */
    private boolean processingJavadoc;

    /** set of the imports. */
    private final Set<FullIdent> imports = Sets.newHashSet();

    /** set of references - possibly to imports or other things. */
    private final Set<String> referenced = Sets.newHashSet();

    public void setProcessJavadoc(boolean value) {
        processingJavadoc = value;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        collect = false;
        imports.clear();
        referenced.clear();
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // loop over all the imports to see if referenced.
        for (final FullIdent imp : imports) {
            if (!referenced.contains(Utils.baseClassname(imp.getText()))) {
                log(imp.getLineNo(),
                    imp.getColumnNo(),
                    MSG_KEY, imp.getText());
            }
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            // Definitions that may contain Javadoc...
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            // Definitions that may contain Javadoc...
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.IDENT) {
            if (collect) {
                processIdent(ast);
            }
        }
        else if (ast.getType() == TokenTypes.IMPORT) {
            processImport(ast);
        }
        else if (ast.getType() == TokenTypes.STATIC_IMPORT) {
            processStaticImport(ast);
        }
        else {
            collect = true;
            if (processingJavadoc) {
                processJavadoc(ast);
            }
        }
    }

    /**
     * Collects references made by IDENT.
     * @param ast the IDENT node to process
     */
    private void processIdent(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        final int parentType = parent.getType();
        if (parentType != TokenTypes.DOT
            && parentType != TokenTypes.METHOD_DEF
            || parentType == TokenTypes.DOT
                && ast.getNextSibling() != null) {
            referenced.add(ast.getText());
        }
    }

    /**
     * Collects the details of imports.
     * @param ast node containing the import details
     */
    private void processImport(DetailAST ast) {
        final FullIdent name = FullIdent.createFullIdentBelow(ast);
        if (!name.getText().endsWith(".*")) {
            imports.add(name);
        }
    }

    /**
     * Collects the details of static imports.
     * @param ast node containing the static import details
     */
    private void processStaticImport(DetailAST ast) {
        final FullIdent name =
            FullIdent.createFullIdent(
                ast.getFirstChild().getNextSibling());
        if (!name.getText().endsWith(".*")) {
            imports.add(name);
        }
    }

    /**
     * Collects references made in Javadoc comments.
     * @param ast node to inspect for Javadoc
     */
    private void processJavadoc(DetailAST ast) {
        final FileContents contents = getFileContents();
        final int lineNo = ast.getLineNo();
        final TextBlock cmt = contents.getJavadocBefore(lineNo);
        if (cmt != null) {
            referenced.addAll(processJavadoc(cmt));
        }
    }

    /**
     * Process a javadoc {@link TextBlock} and return the set of classes
     * referenced within.
     * @param cmt The javadoc block to parse
     * @return a set of classes referenced in the javadoc block
     */
    private Set<String> processJavadoc(TextBlock cmt) {
        final Set<String> references = new HashSet<>();
        // process all the @link type tags
        // INLINEs inside BLOCKs get hidden when using ALL
        for (final JavadocTag tag
                : getValidTags(cmt, JavadocUtils.JavadocTagType.INLINE)) {
            if (tag.canReferenceImports()) {
                references.addAll(processJavadocTag(tag));
            }
        }
        // process all the @throws type tags
        for (final JavadocTag tag
                : getValidTags(cmt, JavadocUtils.JavadocTagType.BLOCK)) {
            if (tag.canReferenceImports()) {
                references.addAll(
                        matchPattern(tag.getArg1(), FIRST_CLASS_NAME));
            }
        }
        return references;
    }

    /**
     * Returns the list of valid tags found in a javadoc {@link TextBlock}
     * @param cmt The javadoc block to parse
     * @param tagType The type of tags we're interested in
     * @return the list of tags
     */
    private List<JavadocTag> getValidTags(TextBlock cmt,
            JavadocUtils.JavadocTagType tagType) {
        return JavadocUtils.getJavadocTags(cmt, tagType).getValidTags();
    }

    /**
     * Returns a list of references found in a javadoc {@link JavadocTag}
     * @param tag The javadoc tag to parse
     * @return A list of references found in this tag
     */
    private Set<String> processJavadocTag(JavadocTag tag) {
        final Set<String> references = new HashSet<>();
        final String identifier = tag.getArg1().trim();
        for (Pattern pattern : new Pattern[]
        {FIRST_CLASS_NAME, ARGUMENT_NAME}) {
            references.addAll(matchPattern(identifier, pattern));
        }
        return references;
    }

    /**
     * Extracts a list of texts matching a {@link Pattern} from a
     * {@link String}.
     * @param identifier The String to match the pattern against
     * @param pattern The Pattern used to extract the texts
     * @return A list of texts which matched the pattern
     */
    private Set<String> matchPattern(String identifier, Pattern pattern) {
        final Set<String> references = new HashSet<>();
        final Matcher matcher = pattern.matcher(identifier);
        while (matcher.find()) {
            references.add(matcher.group(1));
        }
        return references;
    }
}
