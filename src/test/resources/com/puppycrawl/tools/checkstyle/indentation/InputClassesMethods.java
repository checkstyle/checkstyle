package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

import java.util.Iterator;  //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
class IndentationCorrectClassInput  //indent:0 exp:0
    implements Runnable, Cloneable {  //indent:4 exp:4

  class InnerClass implements  //indent:2 exp:2
          Iterable<String>,  //indent:10 exp:>=6
             Cloneable {  //indent:13 exp:>=6
    @Override  //indent:4 exp:4
    public Iterator<String> iterator() {  //indent:4 exp:4
      return null;  //indent:6 exp:6
    }  //indent:4 exp:4
  }  //indent:2 exp:2

  class InnerClass2  //indent:2 exp:2
       extends  //indent:7 exp:>=6
         SecondClassWithLongLongLongLongName {  //indent:9 exp:>=6
    public InnerClass2(String string) {  //indent:4 exp:4
    }  //indent:4 exp:4
  }  //indent:2 exp:2

  @Override  //indent:2 exp:2
  public void run() {  //indent:2 exp:2
    SecondClassWithLongLongLongLongName anon =  //indent:4 exp:4
        new SecondClassWithLongLongLongLongName() {  //indent:8 exp:8
          @MyAnnotation2 //indent:10 exp:10
          String longLongLongLongLongMethodName() {  //indent:10 exp:10
            return "String";  //indent:12 exp:12
          }  //indent:10 exp:10
        };  //indent:8 exp:8

    SecondClassWithLongLongLongLongName anon2 = new  //indent:4 exp:4
          SecondClassWithLongLongLongLongName() {  //indent:10 exp:>=8

    };  //indent:4 exp:4
  }  //indent:2 exp:2
} //indent:0 exp:0

class SecondClassWithLongLongLongLongName  //indent:0 exp:0
    extends  //indent:4 exp:4
         IndentationCorrectClassInput{  //indent:9 exp:>=4
  private boolean conditionFirst(String longString, int //indent:2 exp:2
      integer, InnerClass someInstance) { //indent:6 exp:6
    return false; //indent:4 exp:4
  } //indent:2 exp:2

  private boolean conditionFirst1(String longString, int //indent:2 exp:2
      integer, InnerClass someInstance) //indent:6 exp:6
          throws Exception { //indent:10 exp:>=6
    return false; //indent:4 exp:4
  } //indent:2 exp:2
}  //indent:0 exp:0

@interface MyAnnotation2 {} //indent:0 exp:0

@MyAnnotation2 //indent:0 exp:0
class Foo {} //indent:0 exp:0
