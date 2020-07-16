package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.StatelessCheck;

/**
 * <p>
 * Checks the order of
 * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html#CHDBEFIF">
 * javadoc block-tags or javadoc tags</a>.
 * </p>
 * <p>
 * Note: Google used the term "at-clauses" for block tags in their guide till 2017-02-28.
 * </p>
 *
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code target} - Specify the list of targets to check at-clauses.
 * Type is {@code int[]}.
 * Default value is
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
 * VARIABLE_DEF</a>.
 * </li>
 * <li>
 * Property {@code tagOrder} - Specify the order by tags.
 * Type is {@code java.lang.String[]}.
 * Default value is
 * {@code @author, @deprecated, @exception, @param, @return, @see, @serial, @serialData, @serialField, @since, @throws, @version}.
 * </li>
 * </ul>
 * <p>
 * Default configuration
 * </p>
 * <pre>
 * &lt;module name=&quot;AtclauseOrder&quot;&gt;
 *   &lt;property name=&quot;tagOrder&quot; value=&quot;&#64;author, &#64;version, &#64;param,
 *   &#64;return, &#64;throws, &#64;exception, &#64;see, &#64;since, &#64;serial,
 *   &#64;serialField, &#64;serialData, &#64;deprecated&quot;/&gt;
 *   &lt;property name=&quot;target&quot; value=&quot;CLASS_DEF, INTERFACE_DEF, ENUM_DEF,
 *   METHOD_DEF, CTOR_DEF, VARIABLE_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code at.clause.order}
 * </li>
 * <li>
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
 *
 * @since 6.0
 */
@StatelessCheck
public class Input1 {

}
