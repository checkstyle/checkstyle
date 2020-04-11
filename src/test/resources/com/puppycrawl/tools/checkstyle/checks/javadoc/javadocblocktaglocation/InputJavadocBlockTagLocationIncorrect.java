package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

/**
 * configuration: default
 */
public class InputJavadocBlockTagLocationIncorrect {

    /**
     * Summary. @author me //warn
     * <p>Text</p> @since 1.0 //warn
     * @param param1 a parameter @param, @custom //warn
     * @param param2 a parameter @custom
     * * @throws Exception //warn
    +    * @see PersistenceContext#setReadOnly(Object,boolean) //warn
     * text @return one more @throws text again. //warn
     */
    public void method(int param1, int param2) {
    }

}
