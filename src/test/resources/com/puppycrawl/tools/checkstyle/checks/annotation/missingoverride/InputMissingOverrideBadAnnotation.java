package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/* Config:
 * javaFiveCompatibility = "false"
 */
public class InputMissingOverrideBadAnnotation
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
