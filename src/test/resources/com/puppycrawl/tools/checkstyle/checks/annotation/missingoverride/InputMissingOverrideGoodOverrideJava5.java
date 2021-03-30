package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/* Config:
 * javaFiveCompatibility = "true"
 */
public class InputMissingOverrideGoodOverrideJava5
{
    Runnable r = new Runnable() {

        public void run() {
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                @Override       // ok
                public String toString() {
                    return "junk";
                }
            };
        }
    };

    void doFoo(Runnable r) {
        doFoo(new Runnable() {

            public void run() {
                Throwable t = new Throwable() {

                    /**
                     * {@inheritDoc}
                     */
                    @Override       // ok
                    public String toString() {
                        return "junk";
                    }
                };
            }
        });
    }

    void doFoo2(Runnable r) {
        doFoo(new Runnable() {

            public void run() {
                Throwable t = new Throwable() {

                    /**
                     * {@inheritDoc}
                     */
                    @java.lang.Override     // ok
                    public String toString() {
                        return "junk";
                    }
                };
            }
        });
    }
}
