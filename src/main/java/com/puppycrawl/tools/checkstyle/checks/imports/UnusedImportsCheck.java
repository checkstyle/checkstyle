////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTag;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

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
 * Compatible with Java 1.5 source.
 *
 * @author Oliver Burn
 */
public class UnusedImportsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "import.unused";

    /** Regex to match class names. */
    private static final Pattern CLASS_NAME = CommonUtils.createPattern(
           "((:?[\\p{L}_$][\\p{L}\\p{N}_$]*\\.)*[\\p{L}_$][\\p{L}\\p{N}_$]*)");
    /** Regex to match the first class name. */
    private static final Pattern FIRST_CLASS_NAME = CommonUtils.createPattern(
           "^" + CLASS_NAME);
    /** Regex to match argument names. */
    private static final Pattern ARGUMENT_NAME = CommonUtils.createPattern(
           "[(,]\\s*" + CLASS_NAME.pattern());

    /** Regexp pattern to match java.lang package. */
    private static final Pattern JAVA_LANG_PACKAGE_PATTERN =
        CommonUtils.createPattern("^java\\.lang\\.[a-zA-Z]+$");

    /** Suffix for the star import. */
    private static final String STAR_IMPORT_SUFFIX = ".*";

    /** Set of the imports. */
    private final Set<FullIdent> imports = new HashSet<>();

    /** Set of references - possibly to imports or other things. */
    private final Set<String> referenced = new HashSet<>();

    /** Flag to indicate when time to start collecting references. */
    private boolean collect;
    /** Flag whether to process Javadoc comments. */
    private boolean processJavadoc = true;

    /**
     * Sets whether to process JavaDoc or not.
     *
     * @param value Flag for processing JavaDoc.
     */
    public void setProcessJavadoc(boolean value) {
        processJavadoc = value;
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
        imports.stream()
            .filter(imprt -> isUnusedImport(imprt.getText()))
            .forEach(imprt -> log(imprt.getLineNo(),
                imprt.getColumnNo(),
                MSG_KEY, imprt.getText()));
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
            if (processJavadoc) {
                collectReferencesFromJavadoc(ast);
            }
        }
    }

    /**
     * Checks whether an import is unused.
     * @param imprt an import.
     * @return true if an import is unused.
     */
    private boolean isUnusedImport(String imprt) {
        final Matcher javaLangPackageMatcher = JAVA_LANG_PACKAGE_PATTERN.matcher(imprt);
        return !referenced.contains(CommonUtils.baseClassName(imprt))
            || javaLangPackageMatcher.matches();
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
        if (!name.getText().endsWith(STAR_IMPORT_SUFFIX)) {
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
        if (!name.getText().endsWith(STAR_IMPORT_SUFFIX)) {
            imports.add(name);
        }
    }

    /**
     * Collects references made in Javadoc comments.
     * @param ast node to inspect for Javadoc
     */
    private void collectReferencesFromJavadoc(DetailAST ast) {
        final FileContents contents = getFileContents();
        final int lineNo = ast.getLineNo();
        final TextBlock textBlock = contents.getJavadocBefore(lineNo);
        if (textBlock != null) {
            referenced.addAll(collectReferencesFromJavadoc(textBlock));
        }
    }

    /**
     * Process a javadoc {@link TextBlock} and return the set of classes
     * referenced within.
     * @param textBlock The javadoc block to parse
     * @return a set of classes referenced in the javadoc block
     */
    private static Set<String> collectReferencesFromJavadoc(TextBlock textBlock) {
        final Set<String> references = new HashSet<>();
        final List<JavadocTag> tags = new ArrayList<>();
        // gather all the inline tags, like @link
        // INLINE tags inside BLOCKs get hidden when using ALL
        tags.addAll(getValidTags(textBlock, JavadocUtils.JavadocTagType.INLINE));
        // gather all the block-level tags, like @throws and @see
        tags.addAll(getValidTags(textBlock, JavadocUtils.JavadocTagType.BLOCK));
        tags.stream()
            .filter(JavadocTag::canReferenceImports)
            .forEach(tag -> references.addAll(processJavadocTag(tag)));
        return references;
    }

    /**
     * Returns the list of valid tags found in a javadoc {@link TextBlock}.
     * @param cmt The javadoc block to parse
     * @param tagType The type of tags we're interested in
     * @return the list of tags
     */
    private static List<JavadocTag> getValidTags(TextBlock cmt,
            JavadocUtils.JavadocTagType tagType) {
        return JavadocUtils.getJavadocTags(cmt, tagType).getValidTags();
    }

    /**
     * Returns a list of references found in a javadoc {@link JavadocTag}.
     * @param tag The javadoc tag to parse
     * @return A list of references found in this tag
     */
    private static Set<String> processJavadocTag(JavadocTag tag) {
        final Set<String> references = new HashSet<>();
        final String identifier = tag.getFirstArg().trim();
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
    private static Set<String> matchPattern(String identifier, Pattern pattern) {
        final Set<String> references = new HashSet<>();
        final Matcher matcher = pattern.matcher(identifier);
        while (matcher.find()) {
            references.add(matcher.group(1));
        }
        return references;
    }
}
