////
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
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks for missing package definition Javadoc comments in package-info.java files.
 * </div>
 *
 * <p>
 * Rationale: description and other related documentation for a package can be written up
 * in the package-info.java file and it gets used in the production of the Javadocs.
 * See <a
 * href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#packagecomment"
 * >link</a> for more info.
 * </p>
 *
 * <p>
 * This check specifically only validates package definitions. It will not validate any methods or
 * interfaces declared in the package-info.java file.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code package.javadoc.missing}
 * </li>
 * </ul>
 *
 * @since 8.22
 */
@StatelessCheck
public class MissingJavadocPackageCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PKG_JAVADOC_MISSING = "package.javadoc.missing";

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.PACKAGE_DEF};
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (CheckUtil.isPackageInfo(getFilePath()) && !hasJavadoc(ast)) {
            log(ast, MSG_PKG_JAVADOC_MISSING);
        }
    }

    /**
     * Checks that there is javadoc before ast.
     * Because of <a href="https://github.com/checkstyle/checkstyle/issues/4392">parser bug</a>
     * parser can place javadoc comment either as previous sibling of package definition
     * or (if there is annotation between package def and javadoc) inside package definition tree.
     * So we should look for javadoc in both places.
     *
     * @param ast {@link TokenTypes#PACKAGE_DEF} token to check
     * @return true if there is javadoc, false otherwise
     */
    private static boolean hasJavadoc(DetailAST ast) {
        final boolean hasJavadocBefore = ast.getPreviousSibling() != null
            && isJavadoc(ast.getPreviousSibling());
        return hasJavadocBefore || hasJavadocAboveAnnotation(ast);
    }

    /**
     * Checks javadoc existence in annotations list.
     *
     * @param ast package def
     * @return true if there is a javadoc, false otherwise
     */
    private static boolean hasJavadocAboveAnnotation(DetailAST ast) {
        final Optional<DetailAST> firstAnnotationChild = Optional.of(ast.getFirstChild())
            .map(DetailAST::getFirstChild)
            .map(DetailAST::getFirstChild);
        boolean result = false;
        if (firstAnnotationChild.isPresent()) {
            for (DetailAST child = firstAnnotationChild.orElseThrow(); child != null;
                 child = child.getNextSibling()) {
                if (isJavadoc(child)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Checks that ast is a javadoc comment.
     *
     * @param ast token to check
     * @return true if ast is a javadoc comment, false otherwise
     */
    private static boolean isJavadoc(DetailAST ast) {
        return ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN && JavadocUtil.isJavadocComment(ast);
    }
}
