///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <p>
 * Checks that all package annotations are in the package-info.java file.
 * </p>
 * <p>
 * For Java SE8 and above, placement of package annotations in the package-info.java
 * file is enforced by the compiler and this check is not necessary.
 * </p>
 * <p>
 * For Java SE7 and below, the Java Language Specification highly recommends
 * but doesn't require that annotations are placed in the package-info.java file,
 * and this check can help to enforce that placement.
 * </p>
 * <p>
 * See <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-7.html#jls-7.4.1">
 * Java Language Specification, &#167;7.4.1</a> for more info.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;PackageAnnotation&quot;/&gt;
 * </pre>
 * <p>Example of validating MyClass.java:</p>
 * <pre>
 * &#64;Deprecated
 * package com.example.annotations.packageannotation; //violation
 * </pre>
 * <p>Example of fixing violation in MyClass.java:</p>
 * <pre>
 * package com.example.annotations.packageannotation; //ok
 * </pre>
 * <p>Example of validating package-info.java:</p>
 * <pre>
 * &#64;Deprecated
 * package com.example.annotations.packageannotation; //ok
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code annotation.package.location}
 * </li>
 * </ul>
 *
 * @since 5.0
 */
@StatelessCheck
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
            AnnotationUtil.containsAnnotation(ast);

        if (containsAnnotation && !CheckUtil.isPackageInfo(getFilePath())) {
            log(ast, MSG_KEY);
        }
    }

}
