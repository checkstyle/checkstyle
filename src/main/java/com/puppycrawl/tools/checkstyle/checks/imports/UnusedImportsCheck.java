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
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
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
public class UnusedImportsCheck extends Check
{
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
    private boolean mCollect;
    /** flag whether to process Javadoc comments. */
    private boolean mProcessJavadoc;

    /** set of the imports. */
    private final Set<FullIdent> mImports = Sets.newHashSet();

    /** set of references - possibly to imports or other things. */
    private final Set<String> mReferenced = Sets.newHashSet();

    /** Default constructor. */
    public UnusedImportsCheck()
    {
    }

    public void setProcessJavadoc(boolean aValue)
    {
        mProcessJavadoc = aValue;
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mCollect = false;
        mImports.clear();
        mReferenced.clear();
    }

    @Override
    public void finishTree(DetailAST aRootAST)
    {
        // loop over all the imports to see if referenced.
        for (final FullIdent imp : mImports) {
            if (!mReferenced.contains(Utils.baseClassname(imp.getText()))) {
                log(imp.getLineNo(),
                    imp.getColumnNo(),
                    "import.unused", imp.getText());
            }
        }
    }

    @Override
    public int[] getDefaultTokens()
    {
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
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.IDENT) {
            if (mCollect) {
                processIdent(aAST);
            }
        }
        else if (aAST.getType() == TokenTypes.IMPORT) {
            processImport(aAST);
        }
        else if (aAST.getType() == TokenTypes.STATIC_IMPORT) {
            processStaticImport(aAST);
        }
        else {
            mCollect = true;
            if (mProcessJavadoc) {
                processJavadoc(aAST);
            }
        }
    }

    /**
     * Collects references made by IDENT.
     * @param aAST the IDENT node to process
     */
    private void processIdent(DetailAST aAST)
    {
        final DetailAST parent = aAST.getParent();
        final int parentType = parent.getType();
        if (((parentType != TokenTypes.DOT)
            && (parentType != TokenTypes.METHOD_DEF))
            || ((parentType == TokenTypes.DOT)
                && (aAST.getNextSibling() != null)))
        {
            mReferenced.add(aAST.getText());
        }
    }

    /**
     * Collects the details of imports.
     * @param aAST node containing the import details
     */
    private void processImport(DetailAST aAST)
    {
        final FullIdent name = FullIdent.createFullIdentBelow(aAST);
        if ((name != null) && !name.getText().endsWith(".*")) {
            mImports.add(name);
        }
    }

    /**
     * Collects the details of static imports.
     * @param aAST node containing the static import details
     */
    private void processStaticImport(DetailAST aAST)
    {
        final FullIdent name =
            FullIdent.createFullIdent(
                aAST.getFirstChild().getNextSibling());
        if ((name != null) && !name.getText().endsWith(".*")) {
            mImports.add(name);
        }
    }

    /**
     * Collects references made in JavaDoc comments.
     * @param aAST node to inspect for JavaDoc
     */
    private void processJavadoc(DetailAST aAST)
    {
        final FileContents contents = getFileContents();
        final int lineNo = aAST.getLineNo();
        final TextBlock cmt = contents.getJavadocBefore(lineNo);
        if (cmt != null) {
            mReferenced.addAll(processJavadoc(cmt));
        }
    }

    /**
     * Process a javadoc {@link TextBlock} and return the set of classes
     * referenced within.
     * @param aCmt The javadoc block to parse
     * @return a set of classes referenced in the javadoc block
     */
    private Set<String> processJavadoc(TextBlock aCmt)
    {
        final Set<String> references = new HashSet<String>();
        // process all the @link type tags
        // INLINEs inside BLOCKs get hidden when using ALL
        for (final JavadocTag tag
                : getValidTags(aCmt, JavadocUtils.JavadocTagType.INLINE))
        {
            if (tag.canReferenceImports()) {
                references.addAll(processJavadocTag(tag));
            }
        }
        // process all the @throws type tags
        for (final JavadocTag tag
                : getValidTags(aCmt, JavadocUtils.JavadocTagType.BLOCK))
        {
            if (tag.canReferenceImports()) {
                references.addAll(
                        matchPattern(tag.getArg1(), FIRST_CLASS_NAME));
            }
        }
        return references;
    }

    /**
     * Returns the list of valid tags found in a javadoc {@link TextBlock}
     * @param aCmt The javadoc block to parse
     * @param aTagType The type of tags we're interested in
     * @return the list of tags
     */
    private List<JavadocTag> getValidTags(TextBlock aCmt,
            JavadocUtils.JavadocTagType aTagType)
    {
        return JavadocUtils.getJavadocTags(aCmt, aTagType).getValidTags();
    }

    /**
     * Returns a list of references found in a javadoc {@link JavadocTag}
     * @param aTag The javadoc tag to parse
     * @return A list of references found in this tag
     */
    private Set<String> processJavadocTag(JavadocTag aTag)
    {
        final Set<String> references = new HashSet<String>();
        final String identifier = aTag.getArg1().trim();
        for (Pattern pattern : new Pattern[]
        {FIRST_CLASS_NAME, ARGUMENT_NAME})
        {
            references.addAll(matchPattern(identifier, pattern));
        }
        return references;
    }

    /**
     * Extracts a list of texts matching a {@link Pattern} from a
     * {@link String}.
     * @param aIdentifier The String to match the pattern against
     * @param aPattern The Pattern used to extract the texts
     * @return A list of texts which matched the pattern
     */
    private Set<String> matchPattern(String aIdentifier, Pattern aPattern)
    {
        final Set<String> references = new HashSet<String>();
        final Matcher matcher = aPattern.matcher(aIdentifier);
        while (matcher.find()) {
            references.add(matcher.group(1));
        }
        return references;
    }
}
