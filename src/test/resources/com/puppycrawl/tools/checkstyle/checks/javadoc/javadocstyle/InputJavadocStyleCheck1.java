/*
JavadocStyle


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleCheck1 {

    private int field4;

   /** NM Webapp address.**/
   public static final String NM_WEBAPP_ADDRESS = "webapp.address";

    /** // violation 'First sentence should end with a period'
     * <thead> <br> </thead>
     * <tfoot> <AREA ALT="alt" Coordination="100,0,200,50" HREF="/href/"> </tfoot> comment
     * <p> </p>
     * @param field4 value to which {@link #field4} is to be set to
     */
    // violation 4 lines above 'Unclosed HTML tag found: .*'
    private void setField4(int field4) {this.field4 = field4;}
}
