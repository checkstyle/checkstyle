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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that {@code catch} parameter names conform to a specified pattern.
 * </p>
 * <p>
 * Default pattern has the following characteristic:
 * </p>
 * <ul>
 * <li>allows names beginning with two lowercase letters followed by at least one uppercase or
 * lowercase letter</li>
 * <li>allows {@code e} abbreviation (suitable for exceptions end errors)</li>
 * <li>allows {@code ex} abbreviation (suitable for exceptions)</li>
 * <li>allows {@code t} abbreviation (suitable for throwables)</li>
 * <li>prohibits numbered abbreviations like {@code e1} or {@code t2}</li>
 * <li>prohibits one letter prefixes like {@code pException}</li>
 * <li>prohibits two letter abbreviations like {@code ie} or {@code ee}</li>
 * <li>prohibits any other characters than letters</li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="CatchParameterName"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyTest {
 *   public void myTest() {
 *     try {
 *       // ...
 *     } catch (ArithmeticException e) { // OK
 *       // ...
 *     } catch (ArrayIndexOutOfBoundsException ex) { // OK
 *       // ...
 *     } catch (Throwable t) { // OK
 *       // ...
 *     } catch (IndexOutOfBoundsException e123) { // violation, digits
 *                                // not allowed
 *       // ...
 *     } catch (NullPointerException ab) { // violation, should have at least
 *                              // three characters if not e|t|ex
 *       // ...
 *     } catch (ArrayStoreException abc) { // OK
 *       // ...
 *     } catch (InterruptedException aBC) { // violation, first two characters
 *                               // should be in lowercase
 *       // ...
 *     } catch (RuntimeException abC) { // OK
 *       // ...
 *     } catch (Exception abCD) { // OK
 *       // ...
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with a lower case letter,
 * followed by any letters or digits is:
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="CatchParameterName"&gt;
 *   &lt;property name="format" value="^[a-z][a-zA-Z0-9]+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class MyTest {
 *   public void myTest() {
 *     try {
 *       // ...
 *     } catch (ArithmeticException ex) { // OK
 *       // ...
 *     } catch (ArrayIndexOutOfBoundsException ex2) { // OK
 *       // ...
 *     } catch (IOException thirdException) { // OK
 *       // ...
 *     } catch (Exception FourthException) { // violation, the initial letter
 *                                           // should be lowercase
 *       // ...
 *     }
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
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 6.14
 */
public class CatchParameterNameCheck extends AbstractNameCheck {

    /**
     * Creates a new {@code CatchParameterNameCheck} instance.
     */
    public CatchParameterNameCheck() {
        super("^(e|t|ex|[a-z][a-z][a-zA-Z]+)$");
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
        return new int[] {TokenTypes.PARAMETER_DEF};
    }

    @Override
    protected boolean mustCheckName(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.LITERAL_CATCH;
    }

}
