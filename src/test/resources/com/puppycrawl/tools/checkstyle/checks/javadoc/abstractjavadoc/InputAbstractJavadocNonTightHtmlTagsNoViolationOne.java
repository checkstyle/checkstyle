/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$NonTightHtmlTagCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <body>
 * <p> This class is only meant for testing. </p>
 * <p> In html, closing all tags is not necessary.
 * <li> neither is opening every tag <p> </li>
 * </body>
 *
 * @see "https://www.w3.org/TR/html51/syntax.html#optional-start-and-end-tags"
 */
public class InputAbstractJavadocNonTightHtmlTagsNoViolationOne {
    /** <p> <p> paraception </p> </p> */
    private int field1;

    /**<li> paraTags should be opened</p> list isn't nested in parse tree </li>*/
    private int field2;

    /**
     * <p> this paragraph is closed and would be nested in javadoc tree </p>
     * <li> this list has an <p> unclosed para, but still the list would get nested </li>
     */
    private int field3;

    /**
     * <li> Complete <p> nesting </p> </li>
     * <tr> Zero </p> nesting despite `tr` is closed </tr>
     */
    private int field4;

    /**
     * <p> <a href="www.something.com">something</a> paragraph with `htmlTag` </p>
     * <p> <a href="www.something.com"/> Nested paragraph with `singletonTag` </p>
     * <li> Outer tag <li> Inner tag nested </li> not nested </li>
     */
    private int field5;

    /**
     * <th> !isNonTight </th>
     * <th> th with <base/> singletonElement </th>
     * <body> body with <br/> singletonElement </body>
     * <colgroup><col><col><col></colgroup>
     * <dd> dd with <hr> singletonElement </dd>
     * <dt> dt with <img src="~/singletonElement.jpg" alt="" width="100" height="150"/>
     *     singletonElement </dt>
     * <head> head with <img src="~/singletonElement.jpg" alt="" width="100" height="150">
     * singletonElement </head>
     */
    private int field6;

    /**
     * <body> body <p> paragraph <li> list </li> </p> </body>
     *
     * @return <li> <li> outer list isn't nested in parse tree </li> </li>
     */
}
