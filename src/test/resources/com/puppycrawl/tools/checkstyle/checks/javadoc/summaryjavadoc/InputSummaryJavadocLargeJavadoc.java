/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

// violation below 'Summary javadoc is missing.'
/**
 * <body>
 * <p> This class is only meant for testing. </p>
 * <p> In html, closing all tags is not necessary.
 * </body>
 *
 * @see "https://www.w3.org/TR/html51/syntax.html#optional-start-and-end-tags"
 */
public class InputSummaryJavadocLargeJavadoc {
    private int field4;
    
    private int field5;

    // violation below 'First sentence of Javadoc is missing an ending period.'
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
    
    // violation below 'Summary javadoc is missing.'
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

    // violation below 'Summary javadoc is missing.'
    /**
     *  <p> <li> <TR> <Td> <tH> <body> <colGROUP> <DD>
     *      <dt> <Head> <HTML> <option> <tBody> <tHead>
     *      <tFoot>
     * @param field6 value to which {@link #field6} is to be set to
     */
    private void setField6(int field6) {this.field6 = field6;}
}
