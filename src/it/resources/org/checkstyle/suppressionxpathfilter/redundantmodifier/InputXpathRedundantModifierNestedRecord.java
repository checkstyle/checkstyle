package org.checkstyle.suppressionxpathfilter.redundantmodifier;

public class InputXpathRedundantModifierNestedRecord {

     static record nestedRecord(int id, String name) {
         return null;
     } //warn

}
