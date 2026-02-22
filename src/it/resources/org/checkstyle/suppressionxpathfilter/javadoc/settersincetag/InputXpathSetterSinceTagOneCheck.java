package org.checkstyle.suppressionxpathfilter.javadoc.settersincetag;

public class InputXpathSetterSinceTagOneCheck {

    private Long id;

    private Long id2;

    /**
     * Set id of user
     *
     * @param id id of user
     * @since 13.4.0
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set id of user
     *
     * @param id2 id of user
     */
    public void setId2(Long id2) { // warn
        this.id2 = id2;
    }

}
