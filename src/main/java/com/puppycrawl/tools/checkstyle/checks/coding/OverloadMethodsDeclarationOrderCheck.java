////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks that overload methods are grouped together. Example:
 * </p>
 * <pre>
 * {@code
 * public void foo(int i) {}
 * public void foo(String s) {}
 * public void notFoo() {} // Have to be after foo(int i, String s)
 * public void foo(int i, String s) {}
 * }
 * </pre>
 * <p>
 * An example of how to configure the check is:
 * <code><pre>
 * &lt;module name="OverloadMethodsDeclarationOrder"&gt;
 *     &lt;property name="modifierGroups" value="static, .*"/&gt;
 * &lt;/module&gt;
 * </pre></code>
 * </p>
 *
 * <pre>
 * &lt;module name="OverloadMethodsDeclarationOrder"/&gt;
 * </pre>
 * @author maxvetrenko
 * @author ilankeshet
 */
@StatelessCheck
public class OverloadMethodsDeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "overload.methods.declaration";

    /**
     * The default catch all overload group.
     * Immutable singleton.
     */
    public static final OverloadGroup DEFAULT_OVERLOAD_GROUP = pattern -> true;

    /**
     * A match all pattern for the DEFAULT_OVERLOAD_GROUP.
     */
    private static final String DEFAULT_OVERLOAD_GROUP_PATTERN = ".*";

    /**
     * An ordered by insertion set of <code>OverloadGroup</code>.
     */
    private final Set<OverloadGroup> overloadGroups;

    /**
     * The default constructor.
     */
    public OverloadMethodsDeclarationOrderCheck() {
        overloadGroups = new LinkedHashSet<>();
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
        return new int[] {
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();
        if (parentType == TokenTypes.CLASS_DEF
                || parentType == TokenTypes.ENUM_DEF
                || parentType == TokenTypes.INTERFACE_DEF
                || parentType == TokenTypes.LITERAL_NEW) {
            checkOverloadMethodsGrouping(ast);
        }
    }

    /**
     * @return an ordered by insertion set of <code>OverloadGroup</code>.
     */
    public String getModifierGroups() {
        return Objects.toString(overloadGroups, null);
    }

    /**
     * Set static methods to be grouped separately from instance methods.
     * @param modifierGroupRegex true means static method overloads are
     *                       evaluated separately from instance methods.
     */
    public void setModifierGroups(String modifierGroupRegex) {
        Arrays.stream(modifierGroupRegex.split(",")).forEach(modifierGroup -> {
            final String trimmed = modifierGroup.trim();
            if (CommonUtils.isBlank(trimmed) || DEFAULT_OVERLOAD_GROUP_PATTERN.equals(trimmed)) {
                overloadGroups.add(DEFAULT_OVERLOAD_GROUP);
            }
            else {
                overloadGroups.add(new RegexOverloadGroup(trimmed));
            }
        });
    }

    /**
     * @return an ordered by insertion set of <code>OverloadGroup</code>.
     */
    private Set<OverloadGroup> getOverloadGroups() {
        return overloadGroups;
    }

    /**
     * @return true if at least one custom group is defined.
     */
    private boolean isGroupsDefined() {
        return !overloadGroups.isEmpty();
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     * @param objectBlock
     *        is a class, interface or enum object block.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        final int allowedDistance = 1;
        final LastInStore<OverloadDescriptor> knownMethods = new LastInStore<>();
        DetailAST currentToken = objectBlock.getFirstChild();

        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final OverloadDescriptor methodKey =
                    extractMethodDescription(currentToken, currentIndex);
                final OverloadDescriptor previous = knownMethods.set(methodKey);
                if (previous != null) {
                    final int previousIndex = previous.getIndex();
                    if (currentIndex - previousIndex > allowedDistance) {
                        final int previousLineWithOverloadMethod = previous.getLineNo();
                        log(methodKey.getLineNo(), MSG_KEY, previousLineWithOverloadMethod);
                    }
                }
            }
            currentToken = currentToken.getNextSibling();
        }
    }

    /**
     * Get the currentToken text and if needed prefixes
     * it according to if the method is static or instance.
     * @param currentToken assumed to be a method
     * @param index the index of the method in the file relative to
     *              its own modifier group
     * @return MethodKey instance to which the method belongs
     */
    private OverloadDescriptor extractMethodDescription(DetailAST currentToken, int index) {
        final DetailAST method = currentToken.findFirstToken(TokenTypes.IDENT);
        final String methodName = method.getText();
        final int lineNo = currentToken.getLineNo();

        OverloadDescriptor overloadDescriptor = null;
        if (!isGroupsDefined()) {
            // fast path
            overloadDescriptor =
                new OverloadDescriptor(DEFAULT_OVERLOAD_GROUP, methodName, lineNo, index);
        }

        if (overloadDescriptor == null) {
            final String methodModifiers = extractModifiersString(currentToken);

            for (OverloadGroup overloadGroup : getOverloadGroups()) {
                if (overloadGroup.matches(methodModifiers)) {
                    // method belongs to the first overload group which matches it's modifiers
                    overloadDescriptor =
                        new OverloadDescriptor(overloadGroup, methodName, lineNo, index);
                    break;
                }
            }
        }

        if (overloadDescriptor == null) {
            // no custom group found, overloaded method added to the default, match all, group
            overloadDescriptor =
                new OverloadDescriptor(DEFAULT_OVERLOAD_GROUP, methodName, lineNo, index);
        }

        return overloadDescriptor;
    }

    /**
     * @param token expected to be TokenType.METHOD_DEF .
     * @return modifier string for <code>token</code> with 'package' scope added if needed
     */
    private static String extractModifiersString(final DetailAST token) {
        final DetailAST modifiersAst = token.findFirstToken(TokenTypes.MODIFIERS);
        final List<String> collectedModifiers = new LinkedList<>();
        DetailAST modifier = modifiersAst.getFirstChild();
        boolean foundScopeModifier = false;
        while (modifier != null) {
            if (modifier.getType() != TokenTypes.ANNOTATION) {
                // skipping annotations

                final String currModifierText = modifier.getText();
                if (!foundScopeModifier) {
                    // no need to test again if scope modifier already found
                    foundScopeModifier = isScopeModifier(currModifierText);
                }

                collectedModifiers.add(currModifierText);
            }

            // advance element in loop
            modifier = modifier.getNextSibling();
        }

        if (!foundScopeModifier) {
            // prepend string 'package' to mark default scope.
            // the package scope modifier is placed at the beginning to conform
            // with modifiers order as suggested in sections 8.1.1, 8.3.1 and 8.4.3 of the JLS
            collectedModifiers.add(0, "package");
        }

        return String.join(" ", collectedModifiers);
    }

    /**
     * @param modifier the modifier to test
     * @return true of matches either of the strings 'public', 'protected', 'private'.
     */
    private static boolean isScopeModifier(String modifier) {
        return "public".equals(modifier)
                || "protected".equals(modifier)
                || "private".equals(modifier);
    }

    /**
     * Serves as an identifier and predicate for testing group membership..
     */
    public interface OverloadGroup {
        /**
         * @param modifiers raw modifier text of candidate overloaded method to be tested.
         * @return true if belong to {@code OverloadGroup}
         */
        boolean matches(String modifiers);
    }

    /**
     * A Regex customizable Overload group.
     */
    public static final class RegexOverloadGroup implements OverloadGroup {
        /**
         * Regular expression by which group is defined.
         */
        private final Pattern regex;

        /**
         * @param regexPattern None <code>null</code> {@link String} to be converted
         *                     to {@link Pattern} with which this
         *                     <code>RegexOverloadGroup}</code> is defined.
         */
        RegexOverloadGroup(String regexPattern) {
            Objects.requireNonNull(regexPattern, "regexPattern may not be null");
            regex = Pattern.compile(regexPattern);
        }

        /**
         * @return pattern string by which the {@code OverloadGroup} is matched.
         */
        public String pattern() {
            return regex.pattern();
        }

        @Override
        public boolean matches(String modifiers) {
            final Matcher matcher = regex.matcher(modifiers);
            return matcher.find();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final RegexOverloadGroup other = (RegexOverloadGroup) o;
            return Objects.equals(pattern(), other.pattern());
        }

        @Override
        public int hashCode() {
            final String pattern = pattern();
            return pattern.hashCode();
        }

        @Override
        public String toString() {
            return pattern();
        }
    }

    /**
     * Holds information about an overload method, the group it belongs to
     * and where it was found.
     * <br> Note: <code>index</code> and <code>lineNo</code> do not participate
     * in {@link #equals(Object)} or {@link #hashCode()}.
     */
    public static final class OverloadDescriptor {
        /**
         * Overload group definition.
         */
        private final OverloadGroup overloadGroup;

        /**
         * Method name.
         */
        private final String methodName;

        /** Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}. */
        private final int index;
        /** Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}. */
        private final int lineNo;

        /**
         * @param overloadGroup to which method belongs. may not be null.
         * @param methodName name of method, mey not be null.
         * @param lineNo line in which method is defined in file.
         * @param index order of appearance in overload group.
         */
        OverloadDescriptor(OverloadGroup overloadGroup,
            String methodName, int lineNo, int index) {
            this.overloadGroup =
                Objects.requireNonNull(overloadGroup, "overloadGroup may not be null");
            this.methodName = Objects.requireNonNull(methodName, "methodName may not be null");
            this.lineNo = lineNo;
            this.index = index;
        }

        /**
         * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
         * @return method line number
         */
        public int getLineNo() {
            return lineNo;
        }

        /**
         * Note: does not participate in {@link #equals(Object)} or {@link #hashCode()}.
         * @return method index in block
         */
        public int getIndex() {
            return index;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final OverloadDescriptor other = (OverloadDescriptor) o;
            return Objects.equals(overloadGroup, other.overloadGroup)
                && Objects.equals(methodName, other.methodName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(overloadGroup.hashCode(), methodName.hashCode());
        }
    }

    /**
     * Functions like a {@link Set} except that it keeps the last equal element inserted
     * in contrition to a {@link Set} which keeps the first.
     * @param <T> Type of value to be stored
     */
    private static final class LastInStore<T> {
        /**
         * A map who's key and value are of the same type.
         */
        private final Map<T, T> map = new HashMap<>();

        /**
         * @param value The to be stores.
         * @return Previous equals value equals if exits, or <code>null</code>.
         */
        public T set(T value) {
            return map.put(value, value);
        }
    }
}
