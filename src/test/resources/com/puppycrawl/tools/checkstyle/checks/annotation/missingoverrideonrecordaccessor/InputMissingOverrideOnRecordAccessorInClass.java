/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

/**
 * Class containing a record.
 */
public class InputMissingOverrideOnRecordAccessorInClass {

    public record InnerRecord(String value) {
        public String value() {
// violation above, 'Record component accessor method must include @java.lang.Override annotation.'
            return value.trim();
        }
    }
}
