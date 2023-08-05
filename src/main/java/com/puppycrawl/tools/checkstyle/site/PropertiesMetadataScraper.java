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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.meta.ModulePropertyDetails;
import com.puppycrawl.tools.checkstyle.utils.BlockCommentPosition;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * Class for scraping module properties from the corresponding class' fields, setter methods
 * and javadoc comments.
 */
@FileStatefulCheck
public class PropertiesMetadataScraper extends AbstractJavadocCheck {

    /** List of the module properties details - name, type, default value, etc.. */
    private static List<ModulePropertyDetails> modulePropertyDetailsList = new ArrayList<>();

    /** Clear the module property details of any previous information. */
    public static void clearModulePropertyDetails() {
        modulePropertyDetailsList.clear();
    }

    /**
     * Get the module property details list.
     *
     * @return the module property details list.
     */
    public static List<ModulePropertyDetails> getModulePropertyDetailsList() {
        return modulePropertyDetailsList;
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        // do nothing
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final String javadocParamName = ast.getText();
        final DetailAST blockCommentAst = getBlockCommentAst();
        if (BlockCommentPosition.isOnMethod(blockCommentAst)) {

            final DetailAST methodModifier = blockCommentAst.getParent();
            final DetailAST methodDef = methodModifier.getParent();
            final String methodName = methodDef.findFirstToken(TokenTypes.IDENT).getText();

            if (CheckUtil.isSetterMethod(methodDef)) {
                final String propertyName = methodName.substring(3);
                final ModulePropertyDetails modulePropertyDetails = new ModulePropertyDetails();
                final String text = ((DetailAstImpl) blockCommentAst).getFirstChild().getText();
                final Pattern descriptionPattern = Pattern.compile(".*\\* (Setter to.+\\.)\n\\s+\\*\\n.*", Pattern.DOTALL);
                final Matcher matcher = descriptionPattern.matcher(text);
                if (matcher.find()) {
                    System.out.println(matcher.group(1));
//                    modulePropertyDetails.setDescription();
                }


                // Use block comment to get description
                // BLOCK_COMMENT_BEGIN -> COMMENT_CONTENT -> JAVADOC -> N children
                // Skip 2 first children - NEWLINE and LEADING_ASTERISK
                // Start collecting children
                // stop when we see that next 4 children are NEWLINE, LEADING_ASTERISK, NEWLINE, LEADING_ASTERISK
                // Perform DFS on the collected children to get the description

                modulePropertyDetails.setName(Character.toLowerCase(propertyName.charAt(0)) + propertyName.substring(1));
                modulePropertyDetails.setSince(getSinceVersion(blockCommentAst));
                modulePropertyDetailsList.add(modulePropertyDetails);
            }
        }
    }

    private String getSinceVersion(DetailAST blockCommentAst) {
        // TODO: JavadocUtil::getJavadocTags is probably better here but I haven't figured out how
        // to get a TextBlock yet.
        final String text = ((DetailAstImpl) blockCommentAst).getFirstChild().getText();
        final Pattern sinceVersionPattern = Pattern.compile("@since ((\\d+\\.)+\\d+)\n");
        final Matcher matcher = sinceVersionPattern.matcher(text);
        String sinceVersion = "undefined";
        if (matcher.find()) {
            sinceVersion = matcher.group(1);
        }
        return sinceVersion;
    }
}
