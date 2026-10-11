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
    public void run() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
      Throwable t = new Throwable() {

        /**
         * {@inheritDoc}
         */
        public String toString() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
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
      public void run() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
        Throwable t = new Throwable() {

          /**
           * {@inheritDoc}
           */
          public String toString() {// violation 'include.*@java.lang.Override.*when.*'@inheritDoc''
            return "junk";
          }
        };
      }
    });
  }
}
