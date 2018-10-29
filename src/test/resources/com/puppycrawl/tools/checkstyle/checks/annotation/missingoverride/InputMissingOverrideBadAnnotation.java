package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadAnnotation
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
