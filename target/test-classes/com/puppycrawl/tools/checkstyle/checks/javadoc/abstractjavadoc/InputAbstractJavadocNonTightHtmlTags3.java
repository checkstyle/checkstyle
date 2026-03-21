/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$NonTightHtmlTagTolerantCheck
violateExecutionOnNonTightHtml = true
reportVisitJavadocToken = true

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

public class InputAbstractJavadocNonTightHtmlTags3 {
    private int field3;
    private int field4;
    private int field5;
    /**
     * @param field3 <td> td with singletonElement </td>
     */ // violation above 'tag PARAM_BLOCK_TAG'
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
    // violation 2 lines above 'tag PARAM_BLOCK_TAG'
    private void setField4(int field4) {this.field4 = field4;}

    /**
     *  <p> <li> <TR> <Td> <tH> <body> <colGROUP> <DD> <dt> <Head> <HTML> <option> <tBody> <tHead>
     *      <tFoot>
     * @param field5 value to which {@link #field5} is to be set to
     */
    // violation 4 lines above 'Unclosed HTML tag found: p'
    // violation 3 lines above 'tag PARAM_BLOCK_TAG'
    private void setField5(int field5) {this.field5 = field5;}
}
