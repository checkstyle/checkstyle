package org.checkstyle.suppressionxpathfilter.javadoc.settersincetag;

public class InputXpathSetterSinceTagTwoFilter {

    static class InnerClassFilter {

        private Long id;

        /**
         * Set id of the user
         *
         * @param id id of the user
         */
        public void setId(Long id) { // warn
            this.id = id;
        }
    }
}
