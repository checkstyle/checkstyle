////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Ensures that annotations have no blank lines between their sibling annotations and targets.
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="AdjacentAnnotation"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 *
 * </pre>
 * <p>
 * To configure the check to check for annotations applied on interfaces, variables and constructors:
 * </p>
 * <pre>
 * &lt;module name="AdjacentAnnotation"&gt; &lt;property name="tokens" value="INTERFACE_DEF, VARIABLE_DEF, CTOR_DEF"/&gt; &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 *
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code annotation.not.adjacent}
 * </li>
 * </ul> @since null
 */
@StatelessCheck
public class AdjacentAnnotationCheck extends AbstractAnnotationModifiersCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_ANNOTATION_NOT_ADJACENT = "annotation.not.adjacent";

    @Override
    public void processModifiersNode( DetailAST modifiersNode ) {
        for ( DetailAST annotationNode = modifiersNode.getFirstChild();
                annotationNode != null;
                annotationNode = annotationNode.getNextSibling() ) {
            if ( annotationNode.getType() == TokenTypes.ANNOTATION ) {
                DetailAST sibling = getNextNode( annotationNode );
                if ( annotationNode.getLastChild().getLineNo() + 1 < sibling.getLineNo() ) {
                    log( annotationNode, MSG_KEY_ANNOTATION_NOT_ADJACENT, getAnnotationName( annotationNode ),
                            sibling.getLineNo() );
                }
            }
        }
    }

}
