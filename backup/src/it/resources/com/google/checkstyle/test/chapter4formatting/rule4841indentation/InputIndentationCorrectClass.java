package com.google.checkstyle.test.chapter4formatting.rule4841indentation;  //indent:0 exp:0

import java.util.Iterator;  //indent:0 exp:0

public class InputIndentationCorrectClass  //indent:0 exp:0
    implements Runnable, Cloneable {  //indent:4 exp:4

  class InnerClass implements  //indent:2 exp:2
          Iterable<String>,  //indent:10 exp:>=6
             Cloneable {  //indent:13 exp:>=6
    @Override //indent:4 exp:4
    public Iterator<String> iterator() {  //indent:4 exp:4
      return null;  //indent:6 exp:6
    }  //indent:4 exp:4
  }  //indent:2 exp:2

  class InnerClass2  //indent:2 exp:2
       extends  //indent:7 exp:>=6
          SecondClassReturnWithVeryVeryVeryLongName {  //indent:10 exp:>=6
    public InnerClass2(String string) {  //indent:4 exp:4
      super();  //indent:6 exp:6
      // OOOO Auto-generated constructor stub //indent:6 exp:6
    }  //indent:4 exp:4
  }  //indent:2 exp:2

  @Override  //indent:2 exp:2
  public void run() {  //indent:2 exp:2
    SecondClassWithLongLongLongLongName anon =  //indent:4 exp:4
          new SecondClassWithLongLongLongLongName() {  //indent:10 exp:>=8

          };  //indent:10 exp:10

    SecondClassWithLongLongLongLongName anon2 = new  //indent:4 exp:4
          SecondClassWithLongLongLongLongName() {  //indent:10 exp:>=8

    };  //indent:4 exp:4
  }  //indent:2 exp:2
} //indent:0 exp:0

class SecondClassWithLongLongLongLongName  //indent:0 exp:0
    extends  //indent:4 exp:4
         InputIndentationCorrectClass{  //indent:9 exp:>=4

}  //indent:0 exp:0

class SecondClassReturnWithVeryVeryVeryLongName  //indent:0 exp:0
    extends  //indent:4 exp:4
         InputIndentationCorrectClass{  //indent:9 exp:>=4

}  //indent:0 exp:0
