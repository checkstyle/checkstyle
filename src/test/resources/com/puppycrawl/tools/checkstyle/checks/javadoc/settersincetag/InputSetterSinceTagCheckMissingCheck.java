/*
SetterSinceTag


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.settersincetag;

public class InputSetterSinceTagCheckMissingCheck {

    private boolean enabled;

    private boolean enabled2;

    private boolean enabled3;

    /**
     * Sets the enabled flag.
     *
     * @param enabled the value to set
     */
    public void setEnabled(boolean enabled) { // violation, '@since' tag is missing
        this.enabled = enabled;
    }

    public void setEnabled2(boolean enabled2) { // violation, '@since' tag is missing
        this.enabled2 = enabled2;
    }

    /**
     * Sets the enabled flag.
     *
     * @param enabled3 the value to set
     * @propertySince 13.4.0
     */
    public void setEnabled3(boolean enabled3) {
        this.enabled3 = enabled3;
    }
}

class ExtendedClassForOverrideCheck extends InputSetterSinceTagCheckMissingCheck {

    private boolean deprecated1;

    private boolean deprecated2;

    /**
     * Sets the enabled flag.
     *
     * @param enabled the value to set
     * @since 13.4.0
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Deprecated
    @Override
    public void setEnabled2(boolean enabled2) {
        super.setEnabled2(enabled2);
    }

    /**
     * @param enabled3 the value to set
     * @since 13.4.0
     */
    @Override
    @Deprecated
    public void setEnabled3(boolean enabled3) {
        super.setEnabled3(enabled3);
    }

    /**
     * @param deprecated1 class deprecated
     * @since 13.4.0
     */
    @Deprecated
    public void setDeprecated1(boolean deprecated1) {
        this.deprecated1 = deprecated1;
    }

    /**
     * @param deprecated2 class deprecated
     * @since 13.4.0
     */
    public void setDeprecated2(boolean deprecated2) {
        this.deprecated2 = deprecated2;
    }

    /**
     * @param something the value
     * @since 13.4.0
     */
    @java.lang.Deprecated
    public void setSomething(boolean something) { // no violation - covers ident == null branch
        this.deprecated1 = something;
    }
}
