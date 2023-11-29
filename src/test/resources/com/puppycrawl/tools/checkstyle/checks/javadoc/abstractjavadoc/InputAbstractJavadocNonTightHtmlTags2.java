/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TightHtmlTagCheck
violateExecutionOnNonTightHtml = true
reportVisitJavadocToken = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <body>
 * <p> This class is only meant for testing. </p>
 * an ending period.'
 * <p> In html, closing all tags is not necessary.
 * <li> neither is opening every tag. <p>Only the first non-tight tag is logged</li>
 * </body>
 *
 * @see "https://www.w3.org/TR/html51/syntax.html#optional-start-and-end-tags"
 */
// violation 6 lines above 'Unclosed HTML tag found: p'

public class InputAbstractJavadocNonTightHtmlTags2 {
    /** <p> <p> paraception </p> </p> */ // violation 'Unclosed HTML tag found: p'
    private int field1;

    /**<li> paraTags should be opened</p> list isn't nested in parse tree </li>*/
    // violation above 'Unclosed HTML tag found: li'
    private int field2;

    /**
     * <p> this paragraph is closed and would be nested in javadoc tree </p>
     * <li> list has an <p> unclosed para, but still the list would get nested </li>
     */
    // violation 2 lines above 'Unclosed HTML tag found: p'

    private int field3;

    /**
     * <li> Complete <p> nesting </p> </li>
     * <tr> Zero </p> nesting despite `tr` is closed </tr>
     */
    // violation 2 lines above 'Unclosed HTML tag found: tr'
    private int field4;

    /**
     * <p> <a href="www.something.com">something</a> paragraph with `htmlTag` </p>
     * <p> <a href="www.something.com"/> Nested paragraph with `singletonTag` </p>
     * <li> Outer tag <li> Inner tag nested </li> not nested </li>
     */
    // violation 2 lines above 'Unclosed HTML tag found: li'
    private int field5;

    /**
     * <body> body <p> paragraph <li> list </li> </p> </body>
     *
     * @return <li> <li> outer list isn't nested in parse tree </li> </li>
     */
    // violation 2 lines above 'Unclosed HTML tag found: li'
    int getField1() {return field1;}

    /***/
    int getField2() {return field2;} //method with empty javadoc

    /**
     * <p>This is a setter method.
     * And paraTag shall be nested in parse tree </p>
     * @param field2 <p> setter
     */
    // violation 2 lines above 'Unclosed HTML tag found: p'
    void setField2(int field2) {this.field2 = field2;}

    /**
     * <p> paragraph with a <br>singletonElement. <hr> And it contains another. </p>
     * <li> List with singletonElement
     * <param name=mov value="~/imitation game.mp4"> <param name=allowfullscreen value=true> </li>
     * @return <tr> tr with <base href="www.something.com"> singletonElement </tr>
     *     <tr> nonTight </th>
     */
    // violation 2 lines above 'Unclosed HTML tag found: tr'
    private int getField3() {return field3;}

    /**
     * @param field3 <td> td with singletonElement <br/> </td>
     */
    private void setField3(int field3) { this.field3 = field3;}

    /**
     * <html> <bR> <Br> <BR> <Br/> <BR/> <bR/> </html> // ok
     * <option> <INPut/> </option> // ok
     * @return <tbody> <input/> <br> </tbody>
     */
    private int getField4() {return field4;}

    /**
     * <thead> <br> </thead> // ok
     * <tfoot> <AREA ALT="alt" COORDS="100,0,200,50" HREF="/href/"> </tfoot> // ok
     * <p> </p> // ok
     * @param field4 value to which {@link #field4} is to be set to
     */
    // violation 3 lines above 'First sentence of Javadoc is missing an ending period.'
    private void setField4(int field4) {this.field4 = field4;}

    /**
     *  <p> <li> <TR> <Td> <tH> <body> <colGROUP> <DD> <dt> <Head> <HTML> <option> <tBody> <tHead>
     *      <tFoot> // ok
     * @param field5 </p> value to which {@link #field5} is to be set to
     */
    // violation 4 lines above 'Unclosed HTML tag found: p'
    private void setField5(int field5) {this.field5 = field5;}
}
