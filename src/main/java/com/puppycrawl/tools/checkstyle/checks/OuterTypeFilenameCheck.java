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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that the outer type name and the file name match.
 * For example, the class {@code Foo} must be in a file named {@code Foo.java}.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;OuterTypeFilename&quot;/&gt;
 * </pre>
 * <p>Example of class Test in a file named Test.java</p>
 * <pre>
 * public class Test { // OK
 *
 * }
 * </pre>
 * <p>Example of class Foo in a file named Test.java</p>
 * <pre>
 * class Foo { // violation
 *
 * }
 * </pre>
 * <p>Example of interface Foo in a file named Test.java</p>
 * <pre>
 * interface Foo { // violation
 *
 * }
 * </pre>
 * <p>Example of enum Foo in a file named Test.java</p>
 * <pre>
 * enum Foo { // violation
 *
 * }
 * </pre>
 * <p>Example of record Foo in a file named Test.java</p>
 * <pre>
 * record Foo { // violation
 *
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code type.file.mismatch}
 * </li>
 * </ul>
 *
 * @since 5.3
 */
@FileStatefulCheck
public class OuterTypeFilenameCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "type.file.mismatch";

    /** Pattern matching any file extension with dot included. */
    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("\\.[^.]*$");

    /** Indicates whether the first token has been seen in the file. */
    private boolean seenFirstToken;

    /** Current file name. */
    private String fileName;

    /** If file has public type. */
    private boolean hasPublic;

    /** Outer type with mismatched file name. */
    private DetailAST wrongType;

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
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        fileName = getSourceFileName();
        seenFirstToken = false;
        hasPublic = false;
        wrongType = null;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (seenFirstToken) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
                    && TokenUtil.isRootNode(ast.getParent())) {
                hasPublic = true;
            }
        }
        else {
            final String outerTypeName = ast.findFirstToken(TokenTypes.IDENT).getText();

            if (!fileName.equals(outerTypeName)) {
                wrongType = ast;
            }
        }
        seenFirstToken = true;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (!hasPublic && wrongType != null) {
            log(wrongType, MSG_KEY);
        }
    }

    /**
     * Get source file name.
     *
     * @return source file name.
     */
    private String getSourceFileName() {
        String name = getFilePath();
        name = name.substring(name.lastIndexOf(File.separatorChar) + 1);
        return FILE_EXTENSION_PATTERN.matcher(name).replaceAll("");
    }

}
