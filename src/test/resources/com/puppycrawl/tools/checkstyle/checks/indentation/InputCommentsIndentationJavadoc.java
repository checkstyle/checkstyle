package com.puppycrawl.tools.checkstyle.checks.indentation;

  /**
   * violation
   */
public class InputCommentsIndentationJavadoc {

/** violation */
    int i;

        /**
         * violation
         */
    void foo() {}

    enum Bar {
          /** violation */
        A;
    }

}
