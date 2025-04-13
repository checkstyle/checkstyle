///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that the order of modifiers conforms to the suggestions in the
 * <a href="https://docs.oracle.com/javase/specs/jls/se16/preview/specs/sealed-classes-jls.html">
 * Java Language specification, &#167; 8.1.1, 8.3.1, 8.4.3</a> and
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-9.html">9.4</a>.
 * The correct order is:
 * </div>
 *
 * <ol>
 * <li> {@code public} </li>
 * <li> {@code protected} </li>
 * <li> {@code private} </li>
 * <li> {@code abstract} </li>
 * <li> {@code default} </li>
 * <li> {@code static} </li>
 * <li> {@code sealed} </li>
 * <li> {@code non-sealed} </li>
 * <li> {@code final} </li>
 * <li> {@code transient} </li>
 * <li> {@code volatile} </li>
 * <li> {@code synchronized} </li>
 * <li> {@code native} </li>
 * <li> {@code strictfp} </li>
 * </ol>
 *
 * <p>
 * In additional, modifiers are checked to ensure all annotations
 * are declared before all other modifiers.
 * </p>
 *
 * <p>
 * Rationale: Code is easier to read if everybody follows
 * a standard.
 * </p>
 *
 * <p>
 * ATTENTION: We skip
 * <a href="https://www.oracle.com/technical-resources/articles/java/ma14-architect-annotations.html">
 * type annotations</a> from validation.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code annotation.order}
 * </li>
 * <li>
 * {@code mod.order}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class ModifierOrderCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ANNOTATION_ORDER = "annotation.order";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MODIFIER_ORDER = "mod.order";

    /**
     * The order of modifiers as suggested in sections 8.1.1,
     * 8.3.1 and 8.4.3 of the JLS.
     */
    private static final String[] JLS_ORDER = {
        "public", "protected", "private", "abstract", "default", "static",
        "sealed", "non-sealed", "final", "transient", "volatile",
        "synchronized", "native", "strictfp",
    };

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
        return new int[] {TokenTypes.MODIFIERS};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<DetailAST> mods = new ArrayList<>();
        DetailAST modifier = ast.getFirstChild();
        while (modifier != null) {
            mods.add(modifier);
            modifier = modifier.getNextSibling();
        }

        if (!mods.isEmpty()) {
            final DetailAST error = checkOrderSuggestedByJls(mods);
            if (error != null) {
                if (error.getType() == TokenTypes.ANNOTATION) {
                    log(error,
                            MSG_ANNOTATION_ORDER,
                             error.getFirstChild().getText()
                             + error.getFirstChild().getNextSibling()
                                .getText());
                }
                else {
                    log(error, MSG_MODIFIER_ORDER, error.getText());
                }
            }
        }
    }

    /**
     * Checks if the modifiers were added in the order suggested
     * in the Java language specification.
     *
     * @param modifiers list of modifier AST tokens
     * @return null if the order is correct, otherwise returns the offending
     *     modifier AST.
     */
    private static DetailAST checkOrderSuggestedByJls(List<DetailAST> modifiers) {
        final Iterator<DetailAST> iterator = modifiers.iterator();

        // Speed past all initial annotations
        DetailAST modifier = skipAnnotations(iterator);

        DetailAST offendingModifier = null;

        // All modifiers are annotations, no problem
        if (modifier.getType() != TokenTypes.ANNOTATION) {
            int index = 0;

            while (modifier != null
                    && offendingModifier == null) {
                if (modifier.getType() == TokenTypes.ANNOTATION) {
                    if (!isAnnotationOnType(modifier)) {
                        // Annotation not at start of modifiers, bad
                        offendingModifier = modifier;
                    }
                    break;
                }

                while (index < JLS_ORDER.length
                       && !JLS_ORDER[index].equals(modifier.getText())) {
                    index++;
                }

                if (index == JLS_ORDER.length) {
                    // Current modifier is out of JLS order
                    offendingModifier = modifier;
                }
                else if (iterator.hasNext()) {
                    modifier = iterator.next();
                }
                else {
                    // Reached end of modifiers without problem
                    modifier = null;
                }
            }
        }
        return offendingModifier;
    }

    /**
     * Skip all annotations in modifier block.
     *
     * @param modifierIterator iterator for collection of modifiers
     * @return modifier next to last annotation
     */
    private static DetailAST skipAnnotations(Iterator<DetailAST> modifierIterator) {
        DetailAST modifier;
        do {
            modifier = modifierIterator.next();
        } while (modifierIterator.hasNext() && modifier.getType() == TokenTypes.ANNOTATION);
        return modifier;
    }

    /**
     * Checks whether annotation on type takes place.
     *
     * @param modifier modifier token.
     * @return true if annotation on type takes place.
     */
    private static boolean isAnnotationOnType(DetailAST modifier) {
        boolean annotationOnType = false;
        final DetailAST modifiers = modifier.getParent();
        final DetailAST definition = modifiers.getParent();
        final int definitionType = definition.getType();
        if (definitionType == TokenTypes.VARIABLE_DEF
                || definitionType == TokenTypes.PARAMETER_DEF
                || definitionType == TokenTypes.CTOR_DEF) {
            annotationOnType = true;
        }
        else if (definitionType == TokenTypes.METHOD_DEF) {
            final DetailAST typeToken = definition.findFirstToken(TokenTypes.TYPE);
            final int methodReturnType = typeToken.getLastChild().getType();
            if (methodReturnType != TokenTypes.LITERAL_VOID) {
                annotationOnType = true;
            }
        }
        return annotationOnType;
    }

}
