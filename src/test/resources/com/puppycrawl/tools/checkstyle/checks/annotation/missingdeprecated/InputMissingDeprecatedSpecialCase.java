package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated bleh
 * @deprecated boo
 */
@Deprecated
public class InputMissingDeprecatedSpecialCase
{
    /**
     * @deprecated bleh
     * @deprecated boo
     */
    public int i;

    /**
     * @deprecated
     */
    public void foo() {

    }

    /**
     * @deprecated
     */
    @Deprecated
    public void foo2() {

    }

    /**
     * @deprecated
     * @deprecated
     */
    @Deprecated
    public void foo3() {

    }
    
    /**
     * @deprecated bleh
     * @deprecated
     */
    @Deprecated
    public void foo4() {

    }
    
    /**
     * @deprecated
     * @deprecated bleh
     */
    @Deprecated
    public void foo5() {

    }
    
    void local(@Deprecated String s) {

    }

    void local2(
        /** @deprecated bleh*/
        String s) {

    }

    void local3(/** @deprecated */ @Deprecated String s) {

    }
    
    /**
     * @Deprecated
     */
    void dontUse() {
        
    }
    
    /**
     * @Deprecated
     * @deprecated
     *  because I said.
     */
    @Deprecated
    void dontUse2() {
        
    }
}
