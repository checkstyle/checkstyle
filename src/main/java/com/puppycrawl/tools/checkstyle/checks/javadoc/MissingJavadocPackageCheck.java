////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks for missing Javadoc comments in package-info.java files.
 * </p>
 * <p>
 * Rationale: description and other related documentation for a package can be written up
 * in the package-info.java file and it gets used in the production of the Javadocs.
 * See <a
 * href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#packagecomment"
 * >link</a> for more info.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="MissingJavadocPackage"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * /**
 * * Provides API classes
 * *&#47;
 * package com.checkstyle.api; // no violation
 * /*
 * * Block comment is not a javadoc
 * *&#47;
 * package com.checkstyle.api; // violation
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
            for (DetailAST child = firstAnnotationChild.get(); child != null;
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
