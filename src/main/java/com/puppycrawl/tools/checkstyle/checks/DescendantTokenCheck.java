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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.PropertyType;
import com.puppycrawl.tools.checkstyle.XdocsPropertyType;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks for restricted tokens beneath other tokens.
 * </p>
 * <p>
 * WARNING: This is a very powerful and flexible check, but, at the same time,
 * it is low-level and very implementation-dependent because its results depend
 * on the grammar we use to build abstract syntax trees. Thus, we recommend using
 * other checks when they provide the desired functionality. Essentially, this
 * check just works on the level of an abstract syntax tree and knows nothing
 * about language structures.
 * </p>
 * <ul>
 * <li>
 * Property {@code limitedTokens} - Specify set of tokens with limited occurrences as descendants.
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenTypesSet}.
 * Default value is {@code ""}.
 * </li>
 * <li>
 * Property {@code minimumDepth} - Specify the minimum depth for descendant counts.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code maximumDepth} - Specify the maximum depth for descendant counts.
 * Type is {@code int}.
 * Default value is {@code 2147483647}.
 * </li>
 * <li>
 * Property {@code minimumNumber} - Specify a minimum count for descendants.
 * Type is {@code int}.
 * Default value is {@code 0}.
 * </li>
 * <li>
 * Property {@code maximumNumber} - Specify a maximum count for descendants.
 * Type is {@code int}.
 * Default value is {@code 2147483647}.
 * </li>
 * <li>
 * Property {@code sumTokenCounts} - Control whether the number of tokens found
 * should be calculated from the sum of the individual token counts.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code minimumMessage} - Define the violation message
 * when the minimum count is not reached.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code maximumMessage} - Define the violation message
 * when the maximum count is exceeded.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code anyTokenTypesSet}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to produce a violation on a switch statement with no default case:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_SWITCH&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;2&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_DEFAULT&quot;/&gt;
 *   &lt;property name=&quot;minimumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *  public static void main(String[] args) {
 *    int x = 1;
 *    switch (x) { // ok
 *      case 1:
 *        System.out.println("hi");
 *        break;
 *      default:
 *        System.out.println("Default");
 *        break;
 *    }
 *
 *    int y = 1;
 *    switch (y) { // violation
 *      case 1:
 *        System.out.println("hi");
 *        break;
 *    }
 *  }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a switch with too many cases:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_SWITCH&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_CASE&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;2&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     int x = 1;
 *     switch (x) { // ok
 *       case 1:
 *         // Some code
 *         break;
 *       default:
 *         // Some code
 *         break;
 *     }
 *
 *     switch (x) { // violation
 *       case 1:
 *         // Some code
 *         break;
 *       case 2:
 *         // Some code
 *         break;
 *       default:
 *         // Some code
 *         break;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a switch that is nested in another switch:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_SWITCH&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_SWITCH&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;0&quot;/&gt;
 *   &lt;property name=&quot;minimumDepth&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     int x = 1;
 *     int y = 2;
 *     switch (x) { // ok
 *       case 1:
 *         System.out.println("xyz");
 *         break;
 *     }
 *     switch (y) { // violation
 *       case 1:
 *         switch (y) {
 *           case 2:
 *             System.out.println("xyz");
 *             break;
 *         }
 *         break;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a condition in {@code for}
 * which performs no check:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;FOR_CONDITION&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;EXPR&quot;/&gt;
 *   &lt;property name=&quot;minimumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public static void main(String[] args) {
 *     for (int i = 0; i != 10; i++) { // ok
 *       System.out.println(i);
 *     }
 *     int k = 0;
 *     for (; ; ) { // violation
 *       System.out.println(k);
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on an initializer in {@code for}
 * performs no setup (where a {@code while} statement could be used instead):
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;FOR_INIT&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;EXPR&quot;/&gt;
 *   &lt;property name=&quot;minimumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     int[] array = new int[] {1, 2, 3, 4, 5};
 *
 *     for (int i = 0; i != array.length; i++) { // ok
 *       System.out.println(i);
 *     }
 *
 *     int j = 0;
 *     for (; j != array.length;) { // violation
 *       System.out.println(j);
 *       j++;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a return statement from
 * within a catch or finally block:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_FINALLY,LITERAL_CATCH&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_RETURN&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;0&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     try {
 *       // Some code
 *     } catch (Exception e) {
 *         System.out.println("xyz");
 *         return; // violation
 *     } finally {
 *         System.out.println("xyz");
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a try statement within a catch or finally block:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_CATCH,LITERAL_FINALLY&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_TRY&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;0&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     try {
 *         // Some code
 *     } catch (Exception e) { // ok
 *         System.out.println("xyz");
 *         return;
 *     } finally { // ok
 *         System.out.println("xyz");
 *     }
 *     try {
 *         // Some code
 *     } catch (Exception e) {
 *         try { // violation
 *           // Some code
 *       } catch (Exception ex) {
 *           // handle exception
 *       }
 *     } finally {
 *         try { // violation
 *           // Some code
 *       } catch (Exception e) {
 *           // handle exception
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a method with too many local variables:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;METHOD_DEF&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;VARIABLE_DEF&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;2&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() { // ok
 *     int var1 = 1;
 *   }
 *   public void boo() { // violation
 *     int var1 = 1;
 *     int var2 = 2;
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a method with too many returns:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;METHOD_DEF&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_RETURN&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public int foo(int x) { // ok
 *     if (x == -1) {
 *         return -1;
 *     } else if (x == 0) {
 *           return 0;
 *     }
 *   }
 *   public int boo(int x) { // violation
 *     if (x == -1) {
 *         return -1;
 *     } else if (x == 0) {
 *         return 0;
 *     } else {
 *         return x;
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a method which throws too many exceptions:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_THROWS&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;IDENT&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Foo {
 *   public void foo() throws ArithmeticException { // ok
 *       // ...
 *   }
 * }
 * class Boo {
 *   public void boo() throws ArithmeticException, ArithmeticException { // violation
 *       // ...
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a method with too many expressions:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;METHOD_DEF&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;EXPR&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Foo {
 *   public void foo() { // ok
 *       int x = 1;
 *       int z = x + 2;
 *   }
 * }
 * class Boo {
 *   public void boo() { // violation
 *       int x = 1;
 *       int y = 2;
 *       int z = x + y;
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on an empty statement:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;EMPTY_STAT&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;EMPTY_STAT&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;0&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;0&quot;/&gt;
 *   &lt;property name=&quot;maximumMessage&quot;
 *     value=&quot;Empty statement is not allowed.&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() { // ok
 *       System.out.println("Hello");
 *   }
 *   public void boo() {
 *       ; // violation
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a class or interface with too many fields:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;CLASS_DEF,INTERFACE_DEF&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;VARIABLE_DEF&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;2&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class A { // ok
 *   private int field1;
 *
 *   // Some code
 * }
 * class B { // violation
 *   private int field1;
 *   private int field2;
 *
 *   // Some code
 * }
 * interface C { // ok
 *   int FIELD_1 = 1;
 * }
 * interface D { // violation
 *   int FIELD_1 = 1;
 *   int FIELD_2 = 2;
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on comparing {@code this} with
 * {@code null}:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;EQUAL,NOT_EQUAL&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;LITERAL_THIS,LITERAL_NULL&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;1&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;1&quot;/&gt;
 *   &lt;property name=&quot;sumTokenCounts&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     if (this == null) { // violation
 *       System.out.println("xyz");
 *     }
 *     if (this != null) { // violation
 *       System.out.println("xyz");
 *     }
 *
 *     Object obj = new Object();
 *     if (obj == null) { // ok
 *       System.out.println("xyz");
 *     }
 *     if (obj != null) { // ok
 *       System.out.println("xyz");
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on a {@code String} literal equality check:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;EQUAL,NOT_EQUAL&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;STRING_LITERAL&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;0&quot;/&gt;
 *   &lt;property name=&quot;maximumDepth&quot; value=&quot;1&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *  public void foo() {
 *    String str = "abc";
 *    if (str.equals("abc")) { // ok
 *      System.out.println("equal.");
 *   }
 *   if (str == "abc") { // violation
 *     System.out.println("equal.");
 *   }
 *  }
 * }
 * </pre>
 * <p>
 * To configure the check to produce a violation on an assert statement that may
 * have side effects:
 * </p>
 * <pre>
 * &lt;module name=&quot;DescendantToken&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_ASSERT&quot;/&gt;
 *   &lt;property name=&quot;limitedTokens&quot; value=&quot;ASSIGN,DEC,INC,POST_DEC,
 *     POST_INC,PLUS_ASSIGN,MINUS_ASSIGN,STAR_ASSIGN,DIV_ASSIGN,MOD_ASSIGN,
 *     BSR_ASSIGN,SR_ASSIGN,SL_ASSIGN,BAND_ASSIGN,BXOR_ASSIGN,BOR_ASSIGN,
 *     METHOD_CALL&quot;/&gt;
 *   &lt;property name=&quot;maximumNumber&quot; value=&quot;0&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   public void foo() {
 *     int a = 5;
 *     assert a++ == 0 : "is not"; // violation
 *     System.out.println(a);
 *     assert a == 0 : "is not"; // ok
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
 * {@code descendant.token.max}
 * </li>
 * <li>
 * {@code descendant.token.min}
 * </li>
 * <li>
 * {@code descendant.token.sum.max}
 * </li>
 * <li>
 * {@code descendant.token.sum.min}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class DescendantTokenCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MIN = "descendant.token.min";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_MAX = "descendant.token.max";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SUM_MIN = "descendant.token.sum.min";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_SUM_MAX = "descendant.token.sum.max";

    /** Specify the minimum depth for descendant counts. */
    private int minimumDepth;
    /** Specify the maximum depth for descendant counts. */
    private int maximumDepth = Integer.MAX_VALUE;
    /** Specify a minimum count for descendants. */
    private int minimumNumber;
    /** Specify a maximum count for descendants. */
    private int maximumNumber = Integer.MAX_VALUE;
    /**
     * Control whether the number of tokens found should be calculated from
     * the sum of the individual token counts.
     */
    private boolean sumTokenCounts;
    /** Specify set of tokens with limited occurrences as descendants. */
    @XdocsPropertyType(PropertyType.TOKEN_ARRAY)
    private int[] limitedTokens = CommonUtil.EMPTY_INT_ARRAY;
    /** Define the violation message when the minimum count is not reached. */
    private String minimumMessage;
    /** Define the violation message when the maximum count is exceeded. */
    private String maximumMessage;

    /**
     * Counts of descendant tokens.
     * Indexed by (token ID - 1) for performance.
     */
    private int[] counts = CommonUtil.EMPTY_INT_ARRAY;

    @Override
    public int[] getAcceptableTokens() {
        return TokenUtil.getAllTokenIds();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // reset counts
        Arrays.fill(counts, 0);
        countTokens(ast, 0);

        if (sumTokenCounts) {
            logAsTotal(ast);
        }
        else {
            logAsSeparated(ast);
        }
    }

    /**
     * Log violations for each Token.
     *
     * @param ast token
     */
    private void logAsSeparated(DetailAST ast) {
        // name of this token
        final String name = TokenUtil.getTokenName(ast.getType());

        for (int element : limitedTokens) {
            final int tokenCount = counts[element - 1];
            if (tokenCount < minimumNumber) {
                final String descendantName = TokenUtil.getTokenName(element);

                if (minimumMessage == null) {
                    minimumMessage = MSG_KEY_MIN;
                }
                log(ast,
                        minimumMessage,
                        String.valueOf(tokenCount),
                        String.valueOf(minimumNumber),
                        name,
                        descendantName);
            }
            if (tokenCount > maximumNumber) {
                final String descendantName = TokenUtil.getTokenName(element);

                if (maximumMessage == null) {
                    maximumMessage = MSG_KEY_MAX;
                }
                log(ast,
                        maximumMessage,
                        String.valueOf(tokenCount),
                        String.valueOf(maximumNumber),
                        name,
                        descendantName);
            }
        }
    }

    /**
     * Log validation as one violation.
     *
     * @param ast current token
     */
    private void logAsTotal(DetailAST ast) {
        // name of this token
        final String name = TokenUtil.getTokenName(ast.getType());

        int total = 0;
        for (int element : limitedTokens) {
            total += counts[element - 1];
        }
        if (total < minimumNumber) {
            if (minimumMessage == null) {
                minimumMessage = MSG_KEY_SUM_MIN;
            }
            log(ast,
                    minimumMessage,
                    String.valueOf(total),
                    String.valueOf(minimumNumber), name);
        }
        if (total > maximumNumber) {
            if (maximumMessage == null) {
                maximumMessage = MSG_KEY_SUM_MAX;
            }
            log(ast,
                    maximumMessage,
                    String.valueOf(total),
                    String.valueOf(maximumNumber), name);
        }
    }

    /**
     * Counts the number of occurrences of descendant tokens.
     *
     * @param ast the root token for descendants.
     * @param depth the maximum depth of the counted descendants.
     */
    private void countTokens(DetailAST ast, int depth) {
        if (depth <= maximumDepth) {
            // update count
            if (depth >= minimumDepth) {
                final int type = ast.getType();
                if (type <= counts.length) {
                    counts[type - 1]++;
                }
            }
            DetailAST child = ast.getFirstChild();
            final int nextDepth = depth + 1;
            while (child != null) {
                countTokens(child, nextDepth);
                child = child.getNextSibling();
            }
        }
    }

    /**
     * Setter to specify set of tokens with limited occurrences as descendants.
     *
     * @param limitedTokensParam tokens to ignore.
     */
    public void setLimitedTokens(String... limitedTokensParam) {
        limitedTokens = new int[limitedTokensParam.length];

        int maxToken = 0;
        for (int i = 0; i < limitedTokensParam.length; i++) {
            limitedTokens[i] = TokenUtil.getTokenId(limitedTokensParam[i]);
            if (limitedTokens[i] >= maxToken + 1) {
                maxToken = limitedTokens[i];
            }
        }
        counts = new int[maxToken];
    }

    /**
     * Setter to specify the minimum depth for descendant counts.
     *
     * @param minimumDepth the minimum depth for descendant counts.
     */
    public void setMinimumDepth(int minimumDepth) {
        this.minimumDepth = minimumDepth;
    }

    /**
     * Setter to specify the maximum depth for descendant counts.
     *
     * @param maximumDepth the maximum depth for descendant counts.
     */
    public void setMaximumDepth(int maximumDepth) {
        this.maximumDepth = maximumDepth;
    }

    /**
     * Setter to specify a minimum count for descendants.
     *
     * @param minimumNumber the minimum count for descendants.
     */
    public void setMinimumNumber(int minimumNumber) {
        this.minimumNumber = minimumNumber;
    }

    /**
      * Setter to specify a maximum count for descendants.
      *
      * @param maximumNumber the maximum count for descendants.
      */
    public void setMaximumNumber(int maximumNumber) {
        this.maximumNumber = maximumNumber;
    }

    /**
     * Setter to define the violation message when the minimum count is not reached.
     *
     * @param message the violation message for minimum count not reached.
     *     Used as a {@code MessageFormat} pattern with arguments
     *     <ul>
     *     <li>{0} - token count</li>
     *     <li>{1} - minimum number</li>
     *     <li>{2} - name of token</li>
     *     <li>{3} - name of limited token</li>
     *     </ul>
     */
    public void setMinimumMessage(String message) {
        minimumMessage = message;
    }

    /**
     * Setter to define the violation message when the maximum count is exceeded.
     *
     * @param message the violation message for maximum count exceeded.
     *     Used as a {@code MessageFormat} pattern with arguments
     *     <ul>
     *     <li>{0} - token count</li>
     *     <li>{1} - maximum number</li>
     *     <li>{2} - name of token</li>
     *     <li>{3} - name of limited token</li>
     *     </ul>
     */

    public void setMaximumMessage(String message) {
        maximumMessage = message;
    }

    /**
     * Setter to control whether the number of tokens found should be calculated
     * from the sum of the individual token counts.
     *
     * @param sum whether to use the sum.
     */
    public void setSumTokenCounts(boolean sum) {
        sumTokenCounts = sum;
    }

}
