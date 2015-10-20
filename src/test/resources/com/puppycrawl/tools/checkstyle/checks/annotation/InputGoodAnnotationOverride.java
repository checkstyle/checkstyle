package com.puppycrawl.tools.checkstyle.checks.annotation;

public class InputGoodAnnotationOverride
{
    Runnable r = new Runnable() {

        public void run() {
            Throwable t = new Throwable() {

                /**
                 * {@inheritDoc}
                 */
                @Override
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
                    @Override
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
                    @java.lang.Override
                    public String toString() {
                        return "junk";
                    }
                };
            }
        });
    }
}
