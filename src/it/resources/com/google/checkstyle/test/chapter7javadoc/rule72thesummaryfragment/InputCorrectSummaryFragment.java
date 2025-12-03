package com.google.checkstyle.test.chapter7javadoc.rule72thesummaryfragment;

/** Some Javadoc A {@code Foo} is a simple Javadoc. */
class InputCorrectSummaryFragment {

  /** Some Javadoc This method returns. */
  public static final byte NUL = 0;

  /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. */
  void foo3() {}

  /**
   * This is valid.
   *
   * @throws Exception if a problem occurs.
   */
  void foo4() throws Exception {}

  /** An especially This method returns short bit of Javadoc. */
  void foo5() {}

  /** An especially short bit of Javadoc. This method returns. */
  void foo6() {}

  /** This is valid. <a href="mailto:vlad@htmlbook.ru"/> */
  class InnerInputCorrectJavaDocParagraphCheck {

    /** Foooo@foooo. */
    public static final byte NUL = 0;

    /** Some java@doc. This method returns. */
    public static final byte NUL_2 = 0;

    /** Returns the customer ID. This method returns. */
    int getId() {
      return 666;
    }

    /** This is valid. <a href="mailto:vlad@htmlbook.ru"/>. */
    void foo2() {}

    /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. This method returns. */
    void foo3() {}

    /**
     * This is valid.
     *
     * @throws Exception if a problem occurs.
     */
    void foo4() throws Exception {}

    /**
     * JAXB Provider Use Only: Provides partial default implementations for some javax.xml.bind
     * interfaces.
     */
    void foo5() {}

    /** An especially short (int... A) bit of Javadoc. This method returns. */
    void foo6() {}
  }

  /**
   * Some javadoc. A {@code Foo} is a simple Javadoc.
   *
   * <p>Some Javadoc. A {@code Foo} is a simple Javadoc.
   */
  InnerInputCorrectJavaDocParagraphCheck anon =
      new InnerInputCorrectJavaDocParagraphCheck() {

        /** JAXB 1.0 only default validation event handler. */
        public static final byte NUL = 0;

        /** Returns the current state. This method returns. */
        boolean emulated(String s) {
          return false;
        }

        /** Some Javadoc. This method returns some javadoc. */
        boolean emulated() {
          return false;
        }

        /** Some Javadoc. This method returns some javadoc. Some Javadoc. */
        boolean emulated1() {
          return false;
        }

        /** As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}. */
        void foo3() {}

        /**
         * This is valid.
         *
         * @throws Exception if a problem occurs.
         */
        void foo4() throws Exception {}

        /** An especially short bit of Javadoc. */
        void foo5() {}

        /** An especially short bit of Javadoc. */
        void foo6() {}

        /**
         * This is valid.
         *
         * @return Some Javadoc the customer ID.
         */
        int geId() {
          return 666;
        }
      };
}
