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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks for implicit modifiers on interface members and nested types.
 * </p>
 * <p>
 * This check is effectively the opposite of
 * <a href="https://checkstyle.org/config_modifier.html#RedundantModifier">RedundantModifier</a>.
 * It checks the modifiers on interface members, ensuring that certain modifiers are explicitly
 * specified even though they are actually redundant.
 * </p>
 * <p>
 * Methods in interfaces are {@code public} by default, however from Java 9 they can also be
 * {@code private}. This check provides the ability to enforce that {@code public} is explicitly
 * coded and not implicitly added by the compiler.
 * </p>
 * <p>
 * From Java 8, there are three types of methods in interfaces - static methods marked with
 * {@code static}, default methods marked with {@code default} and abstract methods which do not
 * have to be marked with anything. From Java 9, there are also private methods marked with
 * {@code private}. This check provides the ability to enforce that {@code abstract} is
 * explicitly coded and not implicitly added by the compiler.
 * </p>
 * <p>
 * Fields in interfaces are always {@code public static final} and as such the compiler does not
 * require these modifiers. This check provides the ability to enforce that these modifiers are
 * explicitly coded and not implicitly added by the compiler.
 * </p>
 * <p>
 * Nested types within an interface are always {@code public static} and as such the compiler
 * does not require the {@code public static} modifiers. This check provides the ability to
 * enforce that the {@code public} and {@code static} modifiers are explicitly coded and not
 * implicitly added by the compiler.
 * </p>
 * <pre>
 * public interface AddressFactory {
 *   // check enforces code contains "public static final"
 *   public static final String UNKNOWN = "Unknown";
 *
 *   String OTHER = "Other";  // violation
 *
 *   // check enforces code contains "public" or "private"
 *   public static AddressFactory instance();
 *
 *   // check enforces code contains "public abstract"
 *   public abstract Address createAddress(String addressLine, String city);
 *
 *   List&lt;Address&gt; findAddresses(String city);  // violation
 *
 *   // check enforces default methods are explicitly declared "public"
 *   public default Address createAddress(String city) {
 *     return createAddress(UNKNOWN, city);
 *   }
 *
 *   default Address createOtherAddress() {  // violation
 *     return createAddress(OTHER, OTHER);
 *   }
 * }
 * </pre>
 * <p>
 * Rationale for this check: Methods, fields and nested types are treated differently
 * depending on whether they are part of an interface or part of a class. For example, by
 * default methods are package-scoped on classes, but public in interfaces. However, from
 * Java 8 onwards, interfaces have changed to be much more like abstract classes.
 * Interfaces now have static and instance methods with code. Developers should not have to
 * remember which modifiers are required and which are implied. This check allows the simpler
 * alternative approach to be adopted where the implied modifiers must always be coded explicitly.
 * </p>
 * <ul>
 * <li>
 * Property {@code violateImpliedPublicField} - Control whether to enforce that {@code public}
 * is explicitly coded on interface fields.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedStaticField} - Control whether to enforce that {@code static}
 * is explicitly coded on interface fields.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedFinalField} - Control whether to enforce that {@code final}
 * is explicitly coded on interface fields.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedPublicMethod} - Control whether to enforce that {@code public}
 * is explicitly coded on interface methods.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedAbstractMethod} - Control whether to enforce that {@code abstract}
 * is explicitly coded on interface methods.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedPublicNested} - Control whether to enforce that {@code public}
 * is explicitly coded on interface nested types.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code violateImpliedStaticNested} - Control whether to enforce that {@code static}
 * is explicitly coded on interface nested types.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check so that it checks that all implicit modifiers on methods, fields
 * and nested types are explicitly specified in interfaces.
 * </p>
 * <p>
 * Configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;InterfaceMemberImpliedModifier&quot;/&gt;
 * </pre>
 * <p>
 * Code:
 * </p>
 * <pre>
 * public interface AddressFactory {
 *
 *   public static final String UNKNOWN = "Unknown";  // valid
 *
 *   String OTHER = "Other";  // violation
 *
 *   public static AddressFactory instance();  // valid
 *
 *   public abstract Address createAddress(String addressLine, String city);  // valid
 *
 *   List&lt;Address&gt; findAddresses(String city);  // violation
 *
 *   interface Address {  // violation
 *
 *     String getCity();  // violation
 *   }
 * }
 * </pre>
 * <p>
 * This example checks that all implicit modifiers on methods and fields are
 * explicitly specified, but nested types do not need to be.
 * </p>
 * <p>
 * Configuration:
 * </p>
 * <pre>
 * &lt;module name=&quot;InterfaceMemberImpliedModifier&quot;&gt;
 *   &lt;property name=&quot;violateImpliedPublicNested&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;violateImpliedStaticNested&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code:
 * </p>
 * <pre>
 * public interface RoadFeature {
 *
 *   String STOP = "Stop";  // violation
 *
 *   enum Lights {  // valid because of configured properties
 *
 *     RED, YELLOW, GREEN;
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code interface.implied.modifier}
 * </li>
 * </ul>
 *
 * @since 8.12
 */
@StatelessCheck
public class InterfaceMemberImpliedModifierCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "interface.implied.modifier";

    /** Name for 'public' access modifier. */
    private static final String PUBLIC_ACCESS_MODIFIER = "public";

    /** Name for 'abstract' keyword. */
    private static final String ABSTRACT_KEYWORD = "abstract";

    /** Name for 'static' keyword. */
    private static final String STATIC_KEYWORD = "static";

    /** Name for 'final' keyword. */
    private static final String FINAL_KEYWORD = "final";

    /**
     * Control whether to enforce that {@code public} is explicitly coded
     * on interface fields.
     */
    private boolean violateImpliedPublicField = true;

    /**
     * Control whether to enforce that {@code static} is explicitly coded
     * on interface fields.
     */
    private boolean violateImpliedStaticField = true;

    /**
     * Control whether to enforce that {@code final} is explicitly coded
     * on interface fields.
     */
    private boolean violateImpliedFinalField = true;

    /**
     * Control whether to enforce that {@code public} is explicitly coded
     * on interface methods.
     */
    private boolean violateImpliedPublicMethod = true;

    /**
     * Control whether to enforce that {@code abstract} is explicitly coded
     * on interface methods.
     */
    private boolean violateImpliedAbstractMethod = true;

    /**
     * Control whether to enforce that {@code public} is explicitly coded
     * on interface nested types.
     */
    private boolean violateImpliedPublicNested = true;

    /**
     * Control whether to enforce that {@code static} is explicitly coded
     * on interface nested types.
     */
    private boolean violateImpliedStaticNested = true;

    /**
     * Setter to control whether to enforce that {@code public} is explicitly coded
     * on interface fields.
     *
     * @param violateImpliedPublicField
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedPublicField(boolean violateImpliedPublicField) {
        this.violateImpliedPublicField = violateImpliedPublicField;
    }

    /**
     * Setter to control whether to enforce that {@code static} is explicitly coded
     * on interface fields.
     *
     * @param violateImpliedStaticField
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedStaticField(boolean violateImpliedStaticField) {
        this.violateImpliedStaticField = violateImpliedStaticField;
    }

    /**
     * Setter to control whether to enforce that {@code final} is explicitly coded
     * on interface fields.
     *
     * @param violateImpliedFinalField
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedFinalField(boolean violateImpliedFinalField) {
        this.violateImpliedFinalField = violateImpliedFinalField;
    }

    /**
     * Setter to control whether to enforce that {@code public} is explicitly coded
     * on interface methods.
     *
     * @param violateImpliedPublicMethod
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedPublicMethod(boolean violateImpliedPublicMethod) {
        this.violateImpliedPublicMethod = violateImpliedPublicMethod;
    }

    /**
     * Setter to control whether to enforce that {@code abstract} is explicitly coded
     * on interface methods.
     *
     * @param violateImpliedAbstractMethod
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedAbstractMethod(boolean violateImpliedAbstractMethod) {
        this.violateImpliedAbstractMethod = violateImpliedAbstractMethod;
    }

    /**
     * Setter to control whether to enforce that {@code public} is explicitly coded
     * on interface nested types.
     *
     * @param violateImpliedPublicNested
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedPublicNested(boolean violateImpliedPublicNested) {
        this.violateImpliedPublicNested = violateImpliedPublicNested;
    }

    /**
     * Setter to control whether to enforce that {@code static} is explicitly coded
     * on interface nested types.
     *
     * @param violateImpliedStaticNested
     *        True to perform the check, false to turn the check off.
     */
    public void setViolateImpliedStaticNested(boolean violateImpliedStaticNested) {
        this.violateImpliedStaticNested = violateImpliedStaticNested;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ScopeUtil.isInInterfaceBlock(ast) && !ScopeUtil.isInCodeBlock(ast)) {
            switch (ast.getType()) {
                case TokenTypes.METHOD_DEF:
                    processMethod(ast);
                    break;
                case TokenTypes.VARIABLE_DEF:
                    processField(ast);
                    break;
                case TokenTypes.CLASS_DEF:
                case TokenTypes.INTERFACE_DEF:
                case TokenTypes.ENUM_DEF:
                    processNestedType(ast);
                    break;
                default:
                    throw new IllegalStateException(ast.toString());
            }
        }
    }

    /**
     * Check method in interface.
     *
     * @param ast the method AST
     */
    private void processMethod(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (violateImpliedPublicMethod
                && modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null
                && modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) == null) {
            log(ast, MSG_KEY, PUBLIC_ACCESS_MODIFIER);
        }
        if (violateImpliedAbstractMethod
                && modifiers.findFirstToken(TokenTypes.LITERAL_PRIVATE) == null
                && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null
                && modifiers.findFirstToken(TokenTypes.LITERAL_DEFAULT) == null
                && modifiers.findFirstToken(TokenTypes.ABSTRACT) == null) {
            log(ast, MSG_KEY, ABSTRACT_KEYWORD);
        }
    }

    /**
     * Check field in interface.
     *
     * @param ast the field AST
     */
    private void processField(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (violateImpliedPublicField
                && modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) == null) {
            log(ast, MSG_KEY, PUBLIC_ACCESS_MODIFIER);
        }
        if (violateImpliedStaticField
                && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
            log(ast, MSG_KEY, STATIC_KEYWORD);
        }
        if (violateImpliedFinalField
                && modifiers.findFirstToken(TokenTypes.FINAL) == null) {
            log(ast, MSG_KEY, FINAL_KEYWORD);
        }
    }

    /**
     * Check nested types in interface.
     *
     * @param ast the nested type AST
     */
    private void processNestedType(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (violateImpliedPublicNested
                && modifiers.findFirstToken(TokenTypes.LITERAL_PUBLIC) == null) {
            log(ast, MSG_KEY, PUBLIC_ACCESS_MODIFIER);
        }
        if (violateImpliedStaticNested
                && modifiers.findFirstToken(TokenTypes.LITERAL_STATIC) == null) {
            log(ast, MSG_KEY, STATIC_KEYWORD);
        }
    }

}
