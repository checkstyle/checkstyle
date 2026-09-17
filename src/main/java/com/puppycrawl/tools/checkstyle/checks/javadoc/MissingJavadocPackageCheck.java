///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

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
 * @since 8.22
 */
@StatelessCheck
public class MissingJavadocPackageCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PKG_JAVADOC_MISSING = "package.javadoc.missing";

    /**
     * Creates a new {@code MissingJavadocPackageCheck} instance.
     */
    public MissingJavadocPackageCheck() {
        // no code by default
    }

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
        if (CheckUtil.isPackageInfo(getFilePath())
                && JavadocUtil.getAttachedJavadocCommentForPackage(ast) == null) {
            log(ast, MSG_PKG_JAVADOC_MISSING);
        }
    }

}
