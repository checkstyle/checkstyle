/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$NonTightHtmlTagTolerantCheck
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
// violation 9 lines above 'tag BODY_TAG_START'
// violation 9 lines above 'tag P_TAG_START'
// 2 violations 8 lines above:
//                            'Unclosed HTML tag found: p'
//                            'tag P_TAG_START'
// 2 violations 10 lines above:
//                            'tag LI_TAG_START'
//                            'tag P_TAG_START'

public class InputAbstractJavadocNonTightHtmlTags2 {
    /** <p> <p> paraception </p> </p> */
    // 3 violations above
    //                    'Unclosed HTML tag found: p'
    //                    'tag P_TAG_START'
    //                    'tag P_TAG_START'
    private int field1;

    /**<li> paraTags should be opened</p> list isn't nested in parse tree </li>*/
    // 2 violations above
    //                    'Unclosed HTML tag found: li'
    //                    'tag LI_TAG_START'
    private int field2;

    /**
     * <p> this paragraph is closed and would be nested in javadoc tree </p>
     * <li> list has an <p> unclosed para, but still the list would get nested </li>
     */
    // violation 3 lines above 'P_TAG_START'
    // 3 violations 3 lines above:
    //                    'Unclosed HTML tag found: p'
    //                    'tag LI_TAG_START'
    //                    'tag P_TAG_START'
    private int field3;

    /**
     * <li> Complete <p> nesting </p> </li>
     * <tr> Zero </p> nesting despite `tr` is closed </tr>
     */
    // 2 violations 3 lines above:
    //                            'tag LI_TAG_START'
    //                            'tag P_TAG_START'
    // violation 5 lines above 'Unclosed HTML tag found: tr'
    private int field4;

    /**
     * <p> <a href="www.something.com">something</a> paragraph with `htmlTag` </p>
     * <p> <a href="www.something.com"/> Nested paragraph with `singletonTag` </p>
     * <li> Outer tag <li> Inner tag nested </li> not nested </li>
     */
    // violation 4 lines above 'tag P_TAG_START'
    // violation 4 lines above 'tag P_TAG_START'
    // 3 violations 4 lines above:
    //                            'Unclosed HTML tag found: li'
    //                            'tag LI_TAG_START'
    //                            'tag LI_TAG_START'
    private int field5;

    /**
     * <body> body <p> paragraph <li> list </li> </p> </body>
     *
     * @return <li> <li> outer list isn't nested in parse tree </li> </li>
     */
    // 3 violations 4 lines above:
    //                            'tag BODY_TAG_START'
    //                            'tag P_TAG_START'
    //                            'tag LI_TAG_START'
    // 3 violations 6 lines above:
    //                            'Unclosed HTML tag found: li'
    //                            'tag LI_TAG_START'
    //                            'tag LI_TAG_START'
    int getField1() {return field1;}

    /***/
    int getField2() {return field2;} //method with empty javadoc

    /**
     * <p>This is a setter method.
     * And paraTag shall be nested in parse tree </p>
     * @param field2 <p> setter
     */
    // violation 4 lines above 'tag P_TAG_START'
    // 2 violations 3 lines above:
    //                            'Unclosed HTML tag found: p'
    //                            'tag P_TAG_START'
    void setField2(int field2) {this.field2 = field2;}

    /**
     * <p> paragraph with a <br>singletonElement. <hr> And it contains another. </p>
     * <li> List with singletonElement
     * <param name=mov value="~/imitation game.mp4"> <param name=allowfullscreen value=true> </li>
     * @return <tr> tr with <base href="www.something.com"> singletonElement </tr>
     *     <tr> nonTight </th>
     */
    // violation 6 lines above 'tag P_TAG_START'
    // violation 6 lines above 'tag LI_TAG_START'
    // violation 4 lines above 'Unclosed HTML tag found: tr'
    private int getField3() {return field3;}
}
