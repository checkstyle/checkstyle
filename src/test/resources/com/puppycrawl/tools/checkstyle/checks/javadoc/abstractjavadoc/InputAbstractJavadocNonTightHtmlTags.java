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
public class InputAbstractJavadocNonTightHtmlTags {
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
    int getField1() {return field1;}

    /***/
    int getField2() {return field2;} //method with empty javadoc

    /**
     * <tr> <li> list is going to be nested in the parse tree </li> </tr>
     *
     * @param field1 {@code <p> paraTag will not be recognized} in javadoc tree </p>
     */
    void setField1(int field1) {this.field1 = field1;}

    /**
     * <p>This is a setter method.
     * And paraTag shall be nested in parse tree </p>
     * @param field2 <p> setter
     */
    void setField2(int field2) {this.field2 = field2;}

    /**
     * <p> paragraph with a <br>singletonElement. <hr> And it contains another one. </p>
     * <li> List with singletonElement
     * <param name=mov value="~/imitation game.mp4"> <param name=allowfullscreen value=true> </li>
     * @return <tr> tr with <base href="www.something.com"> singletonElement </tr>
     *     <tr> nonTight </th>
     */
    private int getField3() {return field3;}

    /**
     * @param field3 <td> td with singletonElement <br/> </td>
     */
    private void setField3(int field3) { this.field3 = field3;}

    /**
     * <html> <bR> <Br> <BR> <Br/> <BR/> <bR/> </html>
     * <option> <INPut/> </option>
     * @return <tbody> <input/> <br> </tbody>
     */
    private int getField4() {return field4;}

    /**
     * <thead> <br> </thead>
     * <tfoot> <AREA ALT="alt" COORDS="100,0,200,50" HREF="/href/"> </tfoot>
     * <p> </p>
     * @param field4 value to which {@link #field4} is to be set to
     */
    private void setField4(int field4) {this.field4 = field4;}

    /**
     * <li> </li>
     * <TR> </tr>
     * <Td> </td>
     * <tH> </th>
     * <body> </body>
     * <colGROUP> </COLgroup>
     * <DD> </dd>
     * <dt> </dt>
     * <Head> </head>
     * <HTML> </HTML>
     * <option> </option>
     * <tBody> </TbODY>
     * <tHead> </ThEAD>
     * <tFoot> </TfOOT>
     * @param field5 value to which {@link #field5} is to be set to
     */
    private void setField5(int field5) {this.field5 = field5;}

    /**
     *  <p> <li> <TR> <Td> <tH> <body> <colGROUP> <DD> <dt> <Head> <HTML> <option> <tBody> <tHead>
     *      <tFoot>
     * @param field6 </p> value to which {@link #field6} is to be set to
     */
    private void setField6(int field6) {this.field6 = field6;}
}
