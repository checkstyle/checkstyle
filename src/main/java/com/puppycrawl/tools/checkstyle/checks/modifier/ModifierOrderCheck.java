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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.UnmodifiableCollectionUtil;

/**
 * <div>
 * Checks that the order of modifiers conforms to the configured order.
 * By default, checks that the order conforms to the suggestions in the
 * <a href="https://docs.oracle.com/javase/specs/jls/se16/preview/specs/sealed-classes-jls.html">
 * Java Language specification, &#167; 8.1.1, 8.3.1, 8.4.3</a> and
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-9.html">9.4</a>.
 * The default order is:
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
 * To configure the check to follow the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-modifiers">
 * OpenJDK style</a>, set the property {@code modifierOrder} to {@code openjdk}.
 * The OpenJDK order is:
 * </p>
 *
 * <ol>
 * <li> {@code public} </li>
 * <li> {@code protected} </li>
 * <li> {@code private} </li>
 * <li> {@code abstract} </li>
 * <li> {@code static} </li>
 * <li> {@code final} </li>
 * <li> {@code transient} </li>
 * <li> {@code volatile} </li>
 * <li> {@code default} </li>
 * <li> {@code synchronized} </li>
 * <li> {@code native} </li>
 * <li> {@code strictfp} </li>
 * </ol>
 *
 * <p>
 * In addition, modifiers are checked to ensure all annotations
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
 * @since 3.0
 */
@FileStatefulCheck
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

    /** Modifier keyword. */
    private static final String MODIFIER_PUBLIC = "public";
    /** Modifier keyword. */
    private static final String MODIFIER_PROTECTED = "protected";
    /** Modifier keyword. */
    private static final String MODIFIER_PRIVATE = "private";
    /** Modifier keyword. */
    private static final String MODIFIER_ABSTRACT = "abstract";
    /** Modifier keyword (also used as the name of the default style). */
    private static final String MODIFIER_DEFAULT = "default";
    /** Modifier keyword. */
    private static final String MODIFIER_STATIC = "static";
    /** Modifier keyword. */
    private static final String MODIFIER_SEALED = "sealed";
    /** Modifier keyword. */
    private static final String MODIFIER_NON_SEALED = "non-sealed";
    /** Modifier keyword. */
    private static final String MODIFIER_FINAL = "final";
    /** Modifier keyword. */
    private static final String MODIFIER_TRANSIENT = "transient";
    /** Modifier keyword. */
    private static final String MODIFIER_VOLATILE = "volatile";
    /** Modifier keyword. */
    private static final String MODIFIER_SYNCHRONIZED = "synchronized";
    /** Modifier keyword. */
    private static final String MODIFIER_NATIVE = "native";
    /** Modifier keyword. */
    private static final String MODIFIER_STRICTFP = "strictfp";

    /** Style name for OpenJDK modifier order. */
    private static final String STYLE_OPENJDK = "openjdk";

    /**
     * The order of modifiers as suggested in sections 8.1.1,
     * 8.3.1 and 8.4.3 of the JLS.
     */
    private static final String[] DEFAULT_ORDER = UnmodifiableCollectionUtil.copyOfArray(
        new String[] {
            MODIFIER_PUBLIC, MODIFIER_PROTECTED, MODIFIER_PRIVATE,
            MODIFIER_ABSTRACT, MODIFIER_DEFAULT, MODIFIER_STATIC,
            MODIFIER_SEALED, MODIFIER_NON_SEALED, MODIFIER_FINAL,
            MODIFIER_TRANSIENT, MODIFIER_VOLATILE,
            MODIFIER_SYNCHRONIZED, MODIFIER_NATIVE, MODIFIER_STRICTFP,
        }, 14);

    /**
     * The order of modifiers following OpenJDK style.
     * Based on: https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-modifiers
     */
    private static final String[] OPENJDK_ORDER = UnmodifiableCollectionUtil.copyOfArray(
        new String[] {
            MODIFIER_PUBLIC, MODIFIER_PROTECTED, MODIFIER_PRIVATE,
            MODIFIER_ABSTRACT, MODIFIER_STATIC, MODIFIER_FINAL,
            MODIFIER_TRANSIENT, MODIFIER_VOLATILE, MODIFIER_DEFAULT,
            MODIFIER_SYNCHRONIZED, MODIFIER_NATIVE, MODIFIER_STRICTFP,
        }, 12);

    /**
     * Set of all recognised Java modifier keywords, used for fast validation.
     * Built once from {@link #DEFAULT_ORDER} which is the superset of all modifiers.
     */
    private static final Set<String> ALL_VALID_MODIFIERS =
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(DEFAULT_ORDER)));

    /**
     * Specify the order of modifiers.
     * Use "default" for JLS suggested order, "openjdk" for OpenJDK style.
     * Or provide a custom comma-separated list of modifiers.
     */
    private String modifierOrder = MODIFIER_DEFAULT;

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
            final DetailAST error = checkOrder(mods);
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
     * Setter to specify the order of modifiers.
     * Use "default" for JLS suggested order, "openjdk" for OpenJDK style.
     * Or provide a custom comma-separated list of modifiers.
     *
     * @param modifierOrder the modifier order style or custom list
     * @since 13.4.0
     */
    public void setModifierOrder(String modifierOrder) {
        // validate early by resolving (throws if invalid), then store raw string
        resolveModifierOrder(modifierOrder);
        this.modifierOrder = modifierOrder.trim();
    }

    /**
     * Resolves the modifier order array based on the given style name or custom list.
     *
     * @param style the style name ("default", "openjdk") or custom comma-separated list
     * @return the resolved modifier order array
     * @throws IllegalArgumentException if the style is invalid or contains invalid modifiers
     */
    private static String[] resolveModifierOrder(String style) {
        final String trimmedStyle = style.trim().toLowerCase(Locale.ENGLISH);
        final String[] result;

        if (MODIFIER_DEFAULT.equals(trimmedStyle)) {
            result = DEFAULT_ORDER;
        }
        else if (STYLE_OPENJDK.equals(trimmedStyle)) {
            result = OPENJDK_ORDER;
        }
        else {
            final String[] customOrder = style.split(",");
            for (int idx = 0; idx < customOrder.length; idx++) {
                customOrder[idx] = customOrder[idx].trim();
            }
            validateCustomOrder(customOrder);
            result = customOrder;
        }

        return result;
    }

    /**
     * Validates the custom modifier order array.
     *
     * @param customOrder the custom modifier order array to validate
     * @throws IllegalArgumentException if the array contains invalid modifier names
     */
    private static void validateCustomOrder(String... customOrder) {
        for (String modifier : customOrder) {
            if (!isValidModifier(modifier)) {
                throw new IllegalArgumentException(
                    "Invalid modifier in custom order: " + modifier);
            }
        }
    }

    /**
     * Checks if the given string is a valid Java modifier.
     * Uses a pre-built {@link Set} lookup for O(1) performance and low complexity.
     *
     * @param modifier the modifier string to check
     * @return true if the modifier is valid, false otherwise
     */
    private static boolean isValidModifier(String modifier) {
        return ALL_VALID_MODIFIERS.contains(modifier);
    }

    /**
     * Checks if the modifiers were added in the configured order.
     *
     * @param modifiers list of modifier AST tokens
     * @return null if the order is correct, otherwise returns the offending
     *     modifier AST.
     */
    private DetailAST checkOrder(List<DetailAST> modifiers) {
        final Iterator<DetailAST> iterator = modifiers.iterator();

        DetailAST modifier = skipAnnotations(iterator);

        DetailAST offendingModifier = null;

        final String[] orderArray = resolveModifierOrder(modifierOrder);

        if (modifier.getType() != TokenTypes.ANNOTATION) {
            int index = 0;

            while (modifier != null
                    && offendingModifier == null) {
                if (modifier.getType() == TokenTypes.ANNOTATION) {
                    if (!isAnnotationOnType(modifier)) {
                        offendingModifier = modifier;
                    }
                    break;
                }

                while (index < orderArray.length
                       && !orderArray[index].equals(modifier.getText())) {
                    index++;
                }

                if (index == orderArray.length) {
                    offendingModifier = modifier;
                }
                else if (iterator.hasNext()) {
                    modifier = iterator.next();
                }
                else {
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
