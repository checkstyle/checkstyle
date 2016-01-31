////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtility;

/**
 * This check makes sure that all package annotations are in the
 * package-info.java file.
 *
 * <p>
 * According to the Java Language Specification.
 * </p>
 *
 * <p>
 * The JLS does not enforce the placement of package annotations.
 * This placement may vary based on implementation. The JLS
 * does highly recommend that all package annotations are
 * placed in the package-info.java file.
 *
 * See <a
 * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-7.html#jls-7.4.1">
 * Java Language Specification, section 7.4.1</a>.
 * </p>
 * @author Travis Schneeberger
 */
public class PackageAnnotationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "annotation.package.location";

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(final DetailAST ast) {
        final boolean containsAnnotation =
            AnnotationUtility.containsAnnotation(ast);
        final boolean inPackageInfo =
            getFileContents().inPackageInfo();

        if (containsAnnotation && !inPackageInfo) {
            log(ast.getLine(), MSG_KEY);
        }
    }
}
