// Java17
package org.checkstyle.suppressionxpathfilter.naming.recordcomponentname;

/* Config: default
 */
public class InputXpathRecordComponentNameNested {
    class Inner {
        public record MyRecord(int _value) { } // warn
    }
}
