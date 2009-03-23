package com.puppycrawl.tools.checkstyle.annotation;

public class GoodAnnonOverride
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
}
