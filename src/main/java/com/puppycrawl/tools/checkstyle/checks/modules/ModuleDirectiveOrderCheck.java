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

package com.puppycrawl.tools.checkstyle.checks.modules;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.annotation.PreserveOrder;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks the ordering, grouping and separation of directives in a module
 * declaration. Directives of each kind must form a single block, the blocks
 * must appear in a configurable order, and each block must be separated from
 * the previous one by exactly one blank line.
 * </div>
 *
 * <p>
 * The default configuration enforces
 * <a href="https://google.github.io/styleguide/javaguide.html#s3.5-module-declaration">
 * Google Java Style Guide, Section 3.5.1</a>: all {@code requires} directives
 * first, then {@code exports}, {@code opens}, {@code uses} and {@code provides},
 * each kind in a single block, with a single blank line between blocks. Blank
 * lines are what delimit blocks, so blank lines between directives of the same
 * kind are also violations.
 * </p>
 *
 * <p>
 * All forms of {@code requires} (plain, {@code transitive}, {@code static})
 * belong to a single block, and the order of directives inside a block is not
 * validated.
 * </p>
 *
 * <p>
 * Directive kinds that are not listed in the {@code order} property are not
 * validated.
 * </p>
 *
 * @since 14.1.0
 */
@StatelessCheck
public class ModuleDirectiveOrderCheck extends AbstractCheck {

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when a directive block appears after a block that it should precede.
     */
    public static final String MSG_ORDER = "module.directive.order";

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when directives of one kind are interleaved with directives of
     * another kind.
     */
    public static final String MSG_GROUPING = "module.directive.grouping";

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when directives of the same kind are separated by blank lines.
     */
    public static final String MSG_SEPARATED_INTERNALLY =
            "module.directive.separated.internally";

    /**
     * A key pointing to the warning message text in "messages.properties" file.
     * Emitted when a directive block is not separated from the previous block
     * by exactly one blank line.
     */
    public static final String MSG_SEPARATION = "module.directive.separation";

    /** Default order of directive kinds. */
    private static final List<String> DEFAULT_ORDER = List.of(
        "requires",
        "exports",
        "opens",
        "uses",
        "provides"
    );

    /** Valid values for entries of the {@code order} property. */
    private static final Set<String> VALID_KINDS = Set.copyOf(DEFAULT_ORDER);

    /**
     * Specify directive kinds in the order their blocks must appear inside
     * the module declaration.
     */
    @PreserveOrder
    private List<String> order = DEFAULT_ORDER;

    /**
     * Control whether blank line separation is validated: exactly one blank
     * line between directive blocks and no blank lines inside a block.
     */
    private boolean validateBlockSeparation = true;

    /**
     * Creates a new {@code ModuleDirectiveOrderCheck} instance.
     */
    public ModuleDirectiveOrderCheck() {
        // no code by default
    }

    /**
     * Setter to specify directive kinds in the order their blocks must appear
     * inside the module declaration.
     *
     * @param order the order of directive kinds.
     * @throws IllegalArgumentException when an element of order is not a
     *     directive kind.
     * @since 14.1.0
     */
    public void setOrder(String... order) {
        for (final String kind : order) {
            if (!VALID_KINDS.contains(kind)) {
                throw new IllegalArgumentException("unable to parse " + kind);
            }
        }
        this.order = List.of(order);
    }

    /**
     * Setter to control whether blank line separation is validated: exactly
     * one blank line between directive blocks and no blank lines inside a block.
     *
     * @param validateBlockSeparation the value to set.
     * @since 14.1.0
     */
    public void setValidateBlockSeparation(boolean validateBlockSeparation) {
        this.validateBlockSeparation = validateBlockSeparation;
    }

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
        return new int[] {TokenTypes.MODULE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST directiveBlock = ast.findFirstToken(TokenTypes.DIRECTIVE_BLOCK);
        final Set<String> seenKinds = new HashSet<>();
        DetailAST previous = null;
        for (DetailAST child = directiveBlock.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            if (order.contains(child.getText())) {
                if (previous != null) {
                    validateDirectivePlacement(child, previous, seenKinds);
                }
                seenKinds.add(child.getText());
                previous = child;
            }
        }
    }

    /**
     * Validates the placement of a directive relative to the previous directive
     * of the module.
     *
     * <p>
     * A directive of the same kind as the previous one continues the current
     * block and must not be separated from it by blank lines. Otherwise the
     * directive starts a new block, which must not repeat an earlier kind,
     * must not belong before the previous block, and must be separated from
     * it by exactly one blank line. Blank line requirements are validated
     * only when {@code validateBlockSeparation} is enabled.
     * </p>
     *
     * @param directive the directive to validate.
     * @param previous the directive before the given one.
     * @param seenKinds kinds of all directives seen before the given one.
     */
    private void validateDirectivePlacement(DetailAST directive, DetailAST previous,
                                            Set<String> seenKinds) {
        final String kind = directive.getText();
        final String previousKind = previous.getText();
        final int blankLines = countBlankLinesBetweenDirectives(previous, directive);
        if (kind.equals(previousKind)) {
            if (validateBlockSeparation && blankLines > 0) {
                log(directive, MSG_SEPARATED_INTERNALLY, kind);
            }
        }
        else if (seenKinds.contains(kind)) {
            log(directive, MSG_GROUPING, kind);
        }
        else if (precedesInOrder(kind, previousKind)) {
            log(directive, MSG_ORDER, kind, previousKind);
        }
        else if (validateBlockSeparation && blankLines != 1) {
            log(directive, MSG_SEPARATION, kind);
        }
    }

    /**
     * Checks whether the given kind precedes the other kind in the {@code order} property.
     *
     * @param kind the kind of the directive being validated.
     * @param previousKind the kind of the previous directive.
     * @return true if {@code kind} precedes {@code previousKind} in the configured order.
     */
    private boolean precedesInOrder(String kind, String previousKind) {
        return order.stream()
                .takeWhile(entry -> !entry.equals(previousKind))
                .anyMatch(kind::equals);
    }

    /**
     * Counts the blank lines between the end of the previous directive and
     * the start of the given directive.
     *
     * @param previous the directive before the given one.
     * @param directive the directive to count blank lines before.
     * @return the number of blank lines between the two directives.
     */
    private int countBlankLinesBetweenDirectives(DetailAST previous, DetailAST directive) {
        final int previousEnd = previous.getLastChild().getLineNo();
        final int directiveStart = directive.getLineNo();
        int result = 0;
        for (int lineIndex = previousEnd; lineIndex <= directiveStart - 2; lineIndex++) {
            if (CommonUtil.isBlank(getLine(lineIndex))) {
                result++;
            }
        }
        return result;
    }

}
