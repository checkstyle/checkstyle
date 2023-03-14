package com.google.checkstyle.test.chapter4formatting.rule4841indentation; //indent:0 exp:0

public class InputIndentationCorrectNewChildren { //indent:0 exp:0

  private final StringBuffer mFilter = //indent:2 exp:2
      new StringBuffer( //indent:6 exp:6
          new CharSequence() { //indent:10 exp:10
            @Override //indent:12 exp:12
            public char charAt(int index) { //indent:12 exp:12
              return 'A'; //indent:14 exp:14
            } //indent:12 exp:12

            public int length() { //indent:12 exp:12
              return 1; //indent:14 exp:14
            } //indent:12 exp:12

            public CharSequence subSequence(int start, int end) { //indent:12 exp:12
              return this; //indent:14 exp:14
            } //indent:12 exp:12

            public String toString() { //indent:12 exp:12
              return "Foo"; //indent:14 exp:14
            } //indent:12 exp:12
          }); //indent:10 exp:10
} //indent:0 exp:0
