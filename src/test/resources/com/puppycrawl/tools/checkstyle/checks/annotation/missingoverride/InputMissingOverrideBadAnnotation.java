/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadAnnotation
{
    Runnable r = new Runnable() {

        /**
         * {@inheritDoc}
         */
        public void run() { // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                public String toString() { // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
                    return "junk";
                }
            };
        }
    };

    void doFoo(Runnable r) {
        doFoo(new Runnable() {

            /**
             * {@inheritDoc}
             */
            public void run() { // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
                Throwable t = new Throwable() {

                    /**
                     * {@inheritDoc}
                     */
                    public String toString() { // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
                        return "junk";
                    }
                };
            }
        });
    }
}
