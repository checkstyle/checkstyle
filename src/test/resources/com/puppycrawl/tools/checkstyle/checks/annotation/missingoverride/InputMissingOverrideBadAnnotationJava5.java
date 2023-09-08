/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadAnnotationJava5
{
    Runnable r = new Runnable() {

        /**
         * {@inheritDoc}
         */
        public void run() {
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                public String toString() {
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
            public void run() {
                Throwable t = new Throwable() {

                    /**
                     * {@inheritDoc}
                     */
                    public String toString() {
                        return "junk";
                    }
                };
            }
        });
    }
}
