package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/* Config:
 * javaFiveCompatibility = "true"
 */
public class InputMissingOverrideBadAnnotationJava5
{
    Runnable r = new Runnable() {

        /**
         * {@inheritDoc}
         */
        public void run() {     // ok
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                public String toString() {      // ok
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
            public void run() {     // ok
                Throwable t = new Throwable() {

                    /**
                     * {@inheritDoc}
                     */
                    public String toString() {      // ok
                        return "junk";
                    }
                };
            }
        });
    }
}
