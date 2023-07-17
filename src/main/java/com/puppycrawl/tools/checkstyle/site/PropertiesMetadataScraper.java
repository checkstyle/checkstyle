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

package com.puppycrawl.tools.checkstyle.site;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import org.apache.tools.ant.taskdefs.Java;

/**
 * Class for scraping module properties from the corresponding class' fields, setter methods
 * and javadoc comments.
 */
@FileStatefulCheck
public class PropertiesMetadataScraper extends AbstractJavadocCheck {
    /** ModulePropertyDetails instance for each module AST traversal. */
    private static ModulePropertyDetails modulePropertyDetails;

    /**
     * Getter method for {@code modulePropertyDetails}.
     *
     * @return the module property details.
     */
    public static ModulePropertyDetails getModulePropertyDetails() {
        return modulePropertyDetails;
    }

    /** Reset the module property details of any previous information. */
    public static void resetModulePropertyDetails() {
        modulePropertyDetails = null;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        modulePropertyDetails = new ModulePropertyDetails();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        String javadocParamName = ast.getText();
        DetailAST blockCommentAst = getBlockCommentAst();

        if (BlockCommentPosition.isOnMethod(blockCommentAst)) {

            DetailAST methodModifier = blockCommentAst.getParent();
            DetailAST methodDef = methodModifier.getParent();
            String methodName = methodDef.findFirstToken(TokenTypes.IDENT).getText();
            if (methodName.startsWith("set")) {
                String propertyName = methodName.substring(3);
                System.out.println("property name: " + Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1));
            }
        }
        // get all
    }

    @Override
    public void finishJavadocTree(DetailNode rootAst) {
        // nothing yet
    }

}
