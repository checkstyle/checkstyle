////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;

/**
 * <p>
 * Filter {@code SuppressionXpathSingleFilter} suppresses audit events for Checks
 * violations in the specified file, class, checks, message, module id, and xpath.
 * </p>
 * <p>
 * Rationale: To allow users use suppressions configured in the same config with
 * other modules. SuppressionFilter and SuppressionXpathFilter are require separate file.
 * </p>
 * <p>
 * Advice: If checkstyle configuration is used for several projects, single suppressions
 * on common files/folders is better to put in checkstyle configuration as common rule.
 * All suppression that are for specific file names is better to keep in project
 * specific config file.
 * </p>
 * <p>
 * Attention: This filter only supports single suppression, and will need multiple
 * instances if users wants to suppress multiple violations.
 * </p>
 * <ul>
 * <li>
 * Property {@code files} - Define a Regular Expression matched against the file
 * name associated with an audit event.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code checks} - Define a Regular Expression matched against the name
 * of the check associated with an audit event.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code message} - Define a Regular Expression matched against the message
 * of the check associated with an audit event.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code id} - Define a string matched against the ID of the check
 * associated with an audit event.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code query} - Define a string xpath query.
 * Default value is {@code null}.
 * </li>
 * </ul>
 * <p>
 * To configure to suppress the MethodName check for all methods with
 * name MyMethod inside FileOne and FileTwo files:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;files&quot; value=&quot;File(One|Two)\.java&quot;/&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;MethodName&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;(/CLASS_DEF[@text='FileOne']/OBJBLOCK/
 *             METHOD_DEF[@text='MyMethod']/IDENT)|
 *             (/CLASS_DEF[@text='FileTwo']/OBJBLOCK/METHOD_DEF[@text='MyMethod']/IDENT)&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * public class FileOne {
 *   public void MyMethod() {} // OK
 * }
 *
 * public class FileTwo {
 *   public void MyMethod() {} // OK
 * }
 *
 * public class FileThree {
 *   public void MyMethod() {} // violation, name 'MyMethod'
 *                             // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * To suppress MethodName check for method names matched pattern 'MyMethod[0-9]':
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;MethodName&quot;/&gt;
 *   &lt;property name=&quot;message&quot; value=&quot;MyMethod[0-9]&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public class FileOne {
 *   public void MyMethod1() {} // OK
 *   public void MyMethod2() {} // OK
 *   public void MyMethodA() {} // violation, name 'MyMethodA' must
 *                              // match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * To suppress checks being specified by id property:
 * </p>
 * <pre>
 * &lt;module name=&quot;MethodName&quot;&gt;
 *   &lt;property name=&quot;id&quot; value=&quot;MethodName1&quot;/&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z](_?[a-zA-Z0-9]+)*$&quot;/&gt;
 * &lt;module/&gt;
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;files&quot; value=&quot;FileOne.java&quot;/&gt;
 *   &lt;property name=&quot;id&quot; value=&quot;MethodName1&quot;/&gt;
 * &lt;module/&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * public class FileOne {
 *   public void MyMethod() {} // OK
 * }
 * public class FileTwo {
 *   public void MyMethod() {} // violation,  name 'MyMethod' must
 *                             //match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * To suppress checks for all package definitions:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;PackageName&quot;/&gt;
 *   &lt;property name=&quot;query&quot; query=&quot;/PACKAGE_DEF[@text='File']/IDENT&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * package File; // OK
 *
 * public class FileOne {}
 * </pre>
 * <p>
 * To suppress RedundantModifier check for interface definitions:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;RedundantModifier&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;/INTERFACE_DEF//*&quot;/&gt;
 * &lt;module/&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public interface TestClass {
 *   public static final int CONSTANT1 = 1;  // OK
 * }
 * </pre>
 * <p>
 * To suppress checks in the FileOne file by non-query:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;files&quot; value=&quot;FileOne.java&quot;/&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;MyMethod&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * public class FileOne {
 *   public void MyMethod() {} // OK
 * }
 *
 * public class FileTwo {
 *   public void MyMethod() {} // violation, name 'MyMethod'
 *                             // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * Suppress checks for elements which are either class definitions, either method definitions:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;.*&quot;/&gt;
 *   &lt;property name=&quot;query&quot;
 *             value=&quot;(/CLASS_DEF[@text='FileOne'])|
 *             (/CLASS_DEF[@text='FileOne']/OBJBLOCK/METHOD_DEF[@text='MyMethod']/IDENT)&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * abstract class FileOne { // OK
 *   public void MyMethod() {} // OK
 * }
 *
 * abstract class FileTwo { // violation of the AbstractClassName check,
 *                          // it should match the pattern "^Abstract.+$"
 *   public void MyMethod() {} // violation, name 'MyMethod'
 *                             // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * Suppress checks for MyMethod1 or MyMethod2 methods:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;MethodName&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;/CLASS_DEF[@text='FileOne']/OBJBLOCK/
 *             METHOD_DEF[@text='MyMethod1' or @text='MyMethod2']/IDENT&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code example:
 * </p>
 * <pre>
 * public class FileOne {
 *   public void MyMethod1() {} // OK
 *   public void MyMethod2() {} // OK
 *   public void MyMethod3() {} // violation, name 'MyMethod3' must
 *                              // match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * }
 * </pre>
 * <p>
 * Suppress checks for variable testVariable inside testMethod method inside TestClass class:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;LocalFinalVariableName&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;/CLASS_DEF[@text='TestClass']/OBJBLOCK
 *         /METHOD_DEF[@text='testMethod']/SLIST
 *         /VARIABLE_DEF[@text='testVariable1']/IDENT&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public class TestClass {
 *   public void testMethod() {
 *     final int testVariable1 = 10; // OK
 *     final int testVariable2 = 10; // violation of the LocalFinalVariableName check,
 *                                   // name 'testVariable2' must match pattern '^[A-Z][A-Z0-9]*$'
 *   }
 * }
 * </pre>
 * <p>
 * In the following sample, violations for LeftCurly check will be suppressed
 * for classes with name Main or for methods with name calculate.
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;LeftCurly&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;/CLASS_DEF[@text='TestClass']/OBJBLOCK
 *         /METHOD_DEF[@text='testMethod1']/SLIST*&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public class TestClass {
 *   public void testMethod1()
 *   { // OK
 *   }
 *
 *   public void testMethod2()
 *   { // violation, '{' should be on the previous line
 *   }
 * }
 * </pre>
 * <p>
 * The following example demonstrates how to suppress RequireThis violations for
 * variable age inside changeAge method.
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;RequireThis&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;/CLASS_DEF[@text='InputTest']
 *         //METHOD_DEF[@text='changeAge']//ASSIGN[@text='age']/IDENT&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public class InputTest {
 *   private int age = 23;
 *
 *   public void changeAge() {
 *     age = 24; // violation will be suppressed
 *   }
 * }
 * </pre>
 * <p>
 * Suppress {@code IllegalThrows} violations only for methods with name
 * <i>throwsMethod</i> and only for {@code RuntimeException} exceptions.
 * Double colon is used for axis iterations. In the following example
 * {@code ancestor} axis is used to iterate all ancestor nodes of the current
 * node with type {@code METHOD_DEF} and name <i>throwsMethod</i>.
 * Please read more about xpath axes at
 * <a href="https://www.w3schools.com/xml/xpath_axes.asp">W3Schools Xpath Axes</a>.
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;IllegalThrows&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;//LITERAL_THROWS/IDENT[
 *       ..[@text='RuntimeException'] and ./ancestor::METHOD_DEF[@text='throwsMethod']]&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public class InputTest {
 *   public void throwsMethod() throws RuntimeException { // violation will be suppressed
 *   }
 *
 *   public void sampleMethod() throws RuntimeException { // will throw violation here
 *   }
 * }
 * </pre>
 * <p>
 * The following sample demonstrates how to suppress all violations for method
 * itself and all descendants. {@code descendant-or-self} axis iterates through
 * current node and all children nodes at any level. Keyword {@code node()}
 * selects node elements. Please read more about xpath syntax at
 * <a href="https://www.w3schools.com/xml/xpath_syntax.asp">W3Schools Xpath Syntax</a>.
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;.*&quot;/&gt;
 *   &lt;property name=&quot;query&quot; value=&quot;//METHOD_DEF[@text='TestMethod1']
 *         /descendant-or-self::node()&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Code Example:
 * </p>
 * <pre>
 * public class TestClass {
 *   public void TestMethod1() { // OK
 *     final int num = 10; // OK
 *   }
 *
 *   public void TestMethod2() { // violation of the MethodName check,
 *                               // name 'TestMethod2' must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 *     final int num = 10; // violation of the LocalFinalVariableName check,
 *                         // name 'num' must match pattern '^[A-Z][A-Z0-9]*$'
 *   }
 * }
 * </pre>
 * <p>
 * The following example is an example of what checks would be suppressed while
 * building Spring projects with checkstyle plugin. Please find more information at:
 * <a href="https://github.com/spring-io/spring-javaformat">spring-javaformat</a>
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;files&quot; value=&quot;[\\/]src[\\/]test[\\/]java[\\/]&quot;/&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;Javadoc*&quot;/&gt;
 * &lt;/module&gt;
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;files&quot; value=&quot;.*Tests\.java&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;Javadoc*&quot;&gt;
 * &lt;/module&gt;
 * &lt;module name=&quot;SuppressionXpathSingleFilter&quot;&gt;
 *   &lt;property name=&quot;files&quot; value=&quot;generated-sources&quot;&gt;
 *   &lt;property name=&quot;checks&quot; value=&quot;[a-zA-Z0-9]*&quot;&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @since 8.18
 */
public class SuppressionXpathSingleFilter extends AutomaticBean implements
        TreeWalkerFilter {
    /**
     * XpathFilterElement instance.
     */
    private XpathFilterElement xpathFilter;
    /**
     * Define a Regular Expression matched against the file name associated with an audit event.
     */
    private Pattern files;
    /**
     * Define a Regular Expression matched against the name of the check associated
     * with an audit event.
     */
    private Pattern checks;
    /**
     * Define a Regular Expression matched against the message of the check
     * associated with an audit event.
     */
    private Pattern message;
    /**
     * Define a string matched against the ID of the check associated with an audit event.
     */
    private String id;
    /**
     * Define a string xpath query.
     */
    private String query;

    /**
     * Setter to define a Regular Expression matched against the file name
     * associated with an audit event.
     *
     * @param files the name of the file
     */
    public void setFiles(String files) {
        if (files == null) {
            this.files = null;
        }
        else {
            this.files = Pattern.compile(files);
        }
    }

    /**
     * Setter to define a Regular Expression matched against the name of the check
     * associated with an audit event.
     *
     * @param checks the name of the check
     */
    public void setChecks(String checks) {
        if (checks == null) {
            this.checks = null;
        }
        else {
            this.checks = Pattern.compile(checks);
        }
    }

    /**
     * Setter to define a Regular Expression matched against the message of
     * the check associated with an audit event.
     *
     * @param message the message of the check
     */
    public void setMessage(String message) {
        if (message == null) {
            this.message = null;
        }
        else {
            this.message = Pattern.compile(message);
        }
    }

    /**
     * Setter to define a string matched against the ID of the check associated
     * with an audit event.
     *
     * @param id the ID of the check
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter to define a string xpath query.
     * @param query the xpath query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    protected void finishLocalSetup() {
        xpathFilter = new XpathFilterElement(files, checks, message, id, query);
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
        return xpathFilter.accept(treeWalkerAuditEvent);
    }

}
