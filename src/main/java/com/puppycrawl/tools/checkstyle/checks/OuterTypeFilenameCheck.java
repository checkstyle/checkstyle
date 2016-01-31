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

package com.puppycrawl.tools.checkstyle.checks;

import java.io.File;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that the outer type name and the file name match.
 * @author Oliver Burn
 * @author maxvetrenko
 */
public class OuterTypeFilenameCheck extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "type.file.mismatch";

    /** Pattern matching any file extension with dot included. */
    private static final Pattern FILE_EXTENSION_PATTERN = Pattern.compile("\\.[^\\.]*$");

    /** Indicates whether the first token has been seen in the file. */
    private boolean seenFirstToken;

    /** Current file name. */
    private String fileName;

    /** If file has public type. */
    private boolean hasPublic;

    /** If first type has has same name as file. */
    private boolean validFirst;

    /** Outer type with mismatched file name. */
    private DetailAST wrongType;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF, TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        fileName = getFileName();
        seenFirstToken = false;
        validFirst = false;
        hasPublic = false;
        wrongType = null;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String outerTypeName = ast.findFirstToken(TokenTypes.IDENT).getText();
        if (seenFirstToken) {
            final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) != null
                    && ast.getParent() == null) {
                hasPublic = true;
            }
        }
        else {

            if (fileName.equals(outerTypeName)) {
                validFirst = true;
            }
            else {
                wrongType = ast;
            }
        }
        seenFirstToken = true;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        if (!validFirst && !hasPublic && wrongType != null) {
            log(wrongType.getLineNo(), MSG_KEY);
        }
    }

    /**
     * Get source file name.
     * @return source file name.
     */
    private String getFileName() {
        String name = getFileContents().getFileName();
        name = name.substring(name.lastIndexOf(File.separatorChar) + 1);
        name = FILE_EXTENSION_PATTERN.matcher(name).replaceAll("");
        return name;
    }
}
