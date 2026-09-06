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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <div>
 * Checks that multiple {@code @see} tags are ordered in a predictable way,
 * roughly following the order in which their arguments are searched for by javadoc,
 * from nearest to farthest access, from least-qualified to fully-qualified.
 * </div>
 *
 * <p>The order of {@code @see} tags should be:</p>
 * <ol>
 *   <li>local members first</li>
 *   <li>simple class references after local members</li>
 *   <li>simple class member references after simple class references</li>
 *   <li>qualified class references after simple class member references</li>
 *   <li>qualified class member references after qualified class references</li>
 *   <li>package references last</li>
 * </ol>
 *
 * <p>Inside each member group, fields come first and members with parameters
 * (constructors and methods) come after. Overloaded constructors and methods
 * with the same name must be grouped together and ordered by the number of
 * parameters, with the fewest parameters first.</p>
 *
 * <p>References that are not in a recognizable structured form (for example
 * {@code @see String}, or an HTML anchor) are ignored for ordering purposes,
 * so the check only reports violations when it is confident about the correct
 * order.</p>
 *
 * @since 14.1.0
 */
@FileStatefulCheck
public class JavadocSeeTagOrderCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "javadoc.seeTagOrder";

    /** Dot. */
    private static final char DOT = '.';

    /** Hash sign used to separate a type name from its member. */
    private static final char HASH = '#';

    /** The most distant structural position seen so far in the current Javadoc tree. */
    private SeeReference maxReference;

    /** The reference that immediately precedes the current one. */
    private SeeReference previousReference;

    /** The most recent reference for each member name within the current Javadoc tree. */
    private Map<String, SeeReference> lastByName;

    /**
     * Creates a new {@code JavadocSeeTagOrderCheck} instance.
     */
    public JavadocSeeTagOrderCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultJavadocTokens() {
        return getRequiredJavadocTokens();
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return new int[] {
            JavadocCommentsTokenTypes.SEE_BLOCK_TAG,
        };
    }

    @Override
    public void beginJavadocTree(DetailNode rootAst) {
        maxReference = null;
        lastByName = new HashMap<>();
    }

    @Override
    public void visitJavadocToken(DetailNode ast) {
        final SeeReference current = SeeReference.from(ast);
        if (current != null) {
            if (maxReference == null) {
                maxReference = current;
            }
            else if (isStructuralViolation(current, maxReference)) {
                log(ast, MSG_KEY, current.getText(), maxReference.getText());
            }
            else if (isTelescopingViolation(current)) {
                log(ast, MSG_KEY, current.getText(), lastByName.get(lastByKey(current)).getText());
            }
            else if (isGroupingViolation(current)) {
                log(ast, MSG_KEY, current.getText(), previousReference.getText());
            }
            if (current.structurallyAfterThan(maxReference)) {
                maxReference = current;
            }
            previousReference = current;
            lastByName.put(lastByKey(current), current);
        }
    }

    /**
     * Checks whether the current reference breaks the structural order
     * (category and field-before-callable).
     *
     * @param current the current reference
     * @param maximum the greatest structural reference seen so far
     * @return {@code true} if the structural order is violated
     */
    private static boolean isStructuralViolation(SeeReference current, SeeReference maximum) {
        return current.structuralKey() < maximum.structuralKey();
    }

    /**
     * Checks whether the current reference breaks the telescoping order of an
     * overloaded constructor or method with the same name.
     *
     * @param current the current reference
     * @return {@code true} if the telescoping order is violated
     */
    private boolean isTelescopingViolation(SeeReference current) {
        final SeeReference sameName = lastByName.get(lastByKey(current));
        return sameName != null
                && current.parameterCount < sameName.parameterCount;
    }

    /**
     * Checks whether the current reference breaks the grouping of overloaded
     * members with the same name.
     *
     * @param current the current reference
     * @return {@code true} if the grouping is violated
     */
    private boolean isGroupingViolation(SeeReference current) {
        return lastByName.containsKey(lastByKey(current))
                && previousReference.isField() == current.isField()
                && !previousReference.name().equals(current.name());
    }

    /**
     * Returns the map key that scopes a member name to its owning type and category
     * group, so that grouping and telescoping checks only apply to overloads of the
     * same member on the same type.
     *
     * @param reference the reference
     * @return the map key
     */
    private static String lastByKey(SeeReference reference) {
        return reference.category() + ":" + reference.qualifier() + "#" + reference.name();
    }

    /**
     * Category of a {@code @see} reference, ordered from the closest to the most
     * distant access.
     */
    private enum Category {
        /** Local member such as {@code #field} or {@code #method()}. */
        LOCAL(0),
        /** Simple type reference such as {@code OtherClass}. */
        SIMPLE_TYPE(1),
        /** Simple type member such as {@code OtherClass#field}. */
        SIMPLE_MEMBER(2),
        /** Qualified type reference such as {@code java.util.List}. */
        QUALIFIED_TYPE(3),
        /** Qualified type member such as {@code java.util.List#size()}. */
        QUALIFIED_MEMBER(4),
        /** Package reference such as {@code java.util}. */
        PACKAGE(5);

        /** Explicit structural order, independent of the enum's declaration order. */
        private final int structuralOrder;

        /**
         * Creates a new {@code Category} instance.
         *
         * @param order the explicit structural order
         */
        Category(int order) {
            structuralOrder = order;
        }

        /**
         * Returns the explicit structural order.
         *
         * @return the structural order
         */
        /* package */ int order() {
            return structuralOrder;
        }

    }

    /**
     * Represents a parsed {@code @see} reference together with the ordering
     * information needed to validate the order.
     */
    private static final class SeeReference {

        /** Category that defines the primary structural ordering. */
        private final Category referenceCategory;

        /** Whether the reference points to a field (has no parameters). */
        private final boolean field;

        /** Owning type name, or an empty string for local and non-member references. */
        private final String referenceQualifier;

        /** Simple member or type name used for grouping. */
        private final String identifier;

        /** Number of parameters, used to order overloaded methods. */
        private final int parameterCount;

        /** Full reference text used for messages. */
        private final String text;

        /**
         * Creates a new {@code SeeReference} instance.
         *
         * @param category category of the reference
         * @param isField whether the reference points to a field
         * @param qualifier owning type name, or an empty string
         * @param identifier simple name
         * @param parameterCount number of parameters
         * @param originalText full reference text
         */
        private SeeReference(Category category, boolean isField, String qualifier,
                String identifier, int parameterCount, String originalText) {
            referenceCategory = category;
            field = isField;
            referenceQualifier = qualifier;
            this.identifier = identifier;
            this.parameterCount = parameterCount;
            text = originalText;
        }

        /**
         * Parses a {@code @see} block tag into a {@code SeeReference}, or returns
         * {@code null} if the reference is not in a form that can be confidently ordered.
         *
         * @param seeBlock the {@code @see} block tag
         * @return the parsed reference, or {@code null} if it cannot be classified
         */
        private static SeeReference from(DetailNode seeBlock) {
            final DetailNode reference = JavadocUtil.findFirstToken(
                    seeBlock, JavadocCommentsTokenTypes.REFERENCE);
            SeeReference result = null;
            if (reference != null) {
                final DetailNode firstChild = reference.getFirstChild();
                final int firstType = firstChild.getType();

                if (firstType == JavadocCommentsTokenTypes.HASH) {
                    final DetailNode member = JavadocUtil.findFirstToken(
                            reference, JavadocCommentsTokenTypes.MEMBER_REFERENCE);
                    result = parseMember(member, "", Category.LOCAL);
                }
                else {
                    result = parseIdentifierReference(reference, firstChild);
                }
            }
            return result;
        }

        /**
         * Parses an identifier-based reference (simple or qualified type, member,
         * or package reference).
         *
         * @param reference the reference node
         * @param identifierNode the identifier node
         * @return the parsed reference, or {@code null} if it cannot be classified
         */
        private static SeeReference parseIdentifierReference(DetailNode reference,
                DetailNode identifierNode) {
            final String typeName = identifierNode.getText();
            final DetailNode member = JavadocUtil.findFirstToken(
                    reference, JavadocCommentsTokenTypes.MEMBER_REFERENCE);
            SeeReference result = null;
            if (member != null) {
                final Category category;
                if (typeName.indexOf(DOT) == -1) {
                    category = Category.SIMPLE_MEMBER;
                }
                else {
                    category = Category.QUALIFIED_MEMBER;
                }
                result = parseMember(member, typeName, category);
            }
            else if (isTypeReference(typeName)) {
                final Category category;
                if (typeName.indexOf(DOT) == -1) {
                    category = Category.SIMPLE_TYPE;
                }
                else {
                    category = Category.QUALIFIED_TYPE;
                }
                result = new SeeReference(category, false, "",
                        lastIdentifier(typeName), 0, typeName);
            }
            else if (typeName.indexOf(DOT) != -1 && isPackageReference(typeName)) {
                result = new SeeReference(Category.PACKAGE, false, "",
                        typeName, 0, typeName);
            }
            return result;
        }

        /**
         * Parses a member reference (local member or class member).
         *
         * @param member the member reference node
         * @param qualifier the owning type name, or an empty string for local members
         * @param category category of the reference
         * @return the parsed member reference
         */
        private static SeeReference parseMember(DetailNode member, String qualifier,
                Category category) {
            final DetailNode identifier = JavadocUtil.findFirstToken(
                    member, JavadocCommentsTokenTypes.IDENTIFIER);
            final boolean callable = JavadocUtil.findFirstToken(
                    member, JavadocCommentsTokenTypes.LPAREN) != null;
            final String memberName = identifier.getText();
            final List<String> parameterTypes = parameterTypes(member);
            final StringBuilder text = new StringBuilder(qualifier)
                    .append(HASH);
            if (callable) {
                text.append(memberName).append('(');
                for (int ind = 0; ind < parameterTypes.size(); ind++) {
                    if (ind > 0) {
                        text.append(", ");
                    }
                    text.append(parameterTypes.get(ind));
                }
                text.append(')');
            }
            else {
                text.append(memberName);
            }
            return new SeeReference(category, !callable, qualifier, memberName,
                    parameterTypes.size(), text.toString());
        }

        /**
         * Returns the parameter type texts of a member reference.
         *
         * @param member the member reference node
         * @return the list of parameter type texts
         */
        private static List<String> parameterTypes(DetailNode member) {
            final DetailNode parameterList = JavadocUtil.findFirstToken(
                    member, JavadocCommentsTokenTypes.PARAMETER_TYPE_LIST);
            final List<String> parameterTypes = new ArrayList<>();
            if (parameterList != null) {
                DetailNode node = parameterList.getFirstChild();
                while (node != null) {
                    if (node.getType() == JavadocCommentsTokenTypes.PARAMETER_TYPE) {
                        parameterTypes.add(node.getText());
                    }
                    node = node.getNextSibling();
                }
            }
            return parameterTypes;
        }

        /**
         * Checks whether the given text looks like a type reference (last segment
         * starts with an uppercase letter).
         *
         * @param referenceText the reference text to check
         * @return {@code true} if the text is a type reference
         */
        private static boolean isTypeReference(String referenceText) {
            final String lastSegment = lastIdentifier(referenceText);
            return Character.isUpperCase(lastSegment.charAt(0));
        }

        /**
         * Checks whether the given text looks like a package reference (all lowercase
         * segments and contains at least one dot).
         *
         * @param referenceText the reference text to check
         * @return {@code true} if the text is a package reference
         */
        private static boolean isPackageReference(String referenceText) {
            return Arrays.stream(referenceText.split("\\" + DOT, -1))
                    .allMatch(segment -> segment.toLowerCase(Locale.ROOT).equals(segment));
        }

        /**
         * Returns the last identifier segment of a dotted name.
         *
         * @param name the dotted name
         * @return the last segment
         */
        private static String lastIdentifier(String name) {
            return name.substring(name.lastIndexOf(DOT) + 1);
        }

        /**
         * Returns the reference category.
         *
         * @return the reference category
         */
        /* package */ Category category() {
            return referenceCategory;
        }

        /**
         * Returns whether the reference points to a field.
         *
         * @return {@code true} if the reference points to a field
         */
        /* package */ boolean isField() {
            return field;
        }

        /**
         * Returns the simple name (member name or type name).
         *
         * @return the simple name
         */
        /* package */ String name() {
            return identifier;
        }

        /**
         * Returns the owning type name, or an empty string for local and non-member
         * references.
         *
         * @return the qualifier
         */
        /* package */ String qualifier() {
            return referenceQualifier;
        }

        /**
         * Returns the text used in violation messages.
         *
         * @return the full reference text
         */
        /* package */ String getText() {
            return text;
        }

        /**
         * Returns the structural ordering key comparing the category and, for member
         * references, whether the reference points to a field rather than a callable.
         * A reference only ever points to a field within a category that groups member
         * references, so checking the category is not necessary here.
         *
         * @return the structural ordering key
         */
        /* package */ int structuralKey() {
            final int kindOffset;
            if (field) {
                kindOffset = 0;
            }
            else {
                kindOffset = 1;
            }
            return (referenceCategory.order() << 1) + kindOffset;
        }

        /**
         * Checks whether this reference is structurally after the given reference.
         *
         * @param other the reference to compare to
         * @return {@code true} if this reference is structurally after the other
         */
        /* package */ boolean structurallyAfterThan(SeeReference other) {
            return structuralKey() >= other.structuralKey();
        }

    }

}
