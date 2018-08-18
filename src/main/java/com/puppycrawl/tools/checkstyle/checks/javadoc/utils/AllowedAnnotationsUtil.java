////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Utility class that is used by JavadocTypeCheck and JavadocMethodCheck to
 * permit certain types and methods to omit documentation.
 */
public final class AllowedAnnotationsUtil {
    /**
     * Private constructor to indicate the class is a static utility class.
     */
    private AllowedAnnotationsUtil() {
    }

    /**
     * Checks if the given AST element is marked with an annotation that allows
     * it to omit javadoc documentation.
     * @param ast The type or method definition.
     * @param allowedAnnotations A collection of annotations that allow omitting documentation.
     * @return true if the type or method is exempt from comments, false otherwise.
     */
    public static boolean hasAllowedAnnotations(DetailAST ast, List<String> allowedAnnotations) {
        boolean result = false;
        final DetailAST modifiersNode = ast.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST annotationNode = modifiersNode.findFirstToken(TokenTypes.ANNOTATION);
        while (annotationNode != null && annotationNode.getType() == TokenTypes.ANNOTATION) {
            DetailAST identNode = annotationNode.findFirstToken(TokenTypes.IDENT);
            if (identNode == null) {
                identNode = annotationNode.findFirstToken(TokenTypes.DOT)
                        .findFirstToken(TokenTypes.IDENT);
            }
            if (allowedAnnotations.contains(identNode.getText())) {
                result = true;
                break;
            }
            annotationNode = annotationNode.getNextSibling();
        }

        return result;
    }
}
