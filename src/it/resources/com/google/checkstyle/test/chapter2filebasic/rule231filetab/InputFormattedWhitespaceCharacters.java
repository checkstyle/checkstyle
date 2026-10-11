package com.google.checkstyle.test.chapter2filebasic.rule231filetab;

final class InputFormattedWhitespaceCharacters {
  // Long line ----------------------------------------------------------------
  // Contains a tab ->	<- // violation 'Line contains a tab character.'
  // Contains trailing whitespace ->

  /**
   * Some javadoc.
   *
   * @param badFormat1 bad format
   * @param badFormat2 bad format
   * @param badFormat3 bad format
   * @return hack
   * @throws Exception abc
   */
  int test1(int badFormat1, int badFormat2, final int badFormat3) throws Exception {
    return 0;
  }

  // A very, very long line that is OK because it matches the regexp "^.*is OK.*regexp.*$"
  // long line that has a tab ->	<- and would be OK if tab counted as 1 char
  // violation above 'Line contains a tab character.'

  // tabs that count as one char because of their position ->	<-   ->	<-
  // violation above 'Line contains a tab character.'

  /** Some lines to test the column after tabs. */
  void violateColumnAfterTabs() {
    // with tab-width 8 all statements below start at the same column,
    // with different combinations of ' ' and '\t' before the statement
    int tab0 = 1;
    int tab1 = 1;
    int tab2 = 1;
    int tab3 = 1;
    int tab4 = 1;
    int tab5 = 1;
  }
}
