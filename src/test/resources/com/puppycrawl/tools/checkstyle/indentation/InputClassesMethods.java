import java.util.Iterator;  // indent:0 ; exp:0; ok

import IndentationCorrectClassInput.InnerClass;

class IndentationCorrectClassInput  // indent:0 ; exp:0; ok
    implements Runnable, Cloneable {  // indent:4 ; exp:4; ok
    
  class InnerClass implements  // indent:2 ; exp:2; ok
          Iterable<String>,  // indent:11 ; exp:>6; ok
             Cloneable {  // indent:13 ; exp:>6; ok
    @Override  // indent:4 ; exp:4; ok
    public Iterator<String> iterator() {  // indent:4 ; exp:4; ok
      return null;  // indent:6 ; exp:6; ok
    }  // indent:4 ; exp:4; ok
  }  // indent:2 ; exp:2; ok
  
  class InnerClass2  // indent:2 ; exp:2; ok
       extends  // indent:7 ; exp:>6; ok
         SecondClassWithLongLongLongLongName {  // indent:10 ; exp:>6; ok
    public InnerClass2(String string) {  // indent:4 ; exp:4; ok
    }  // indent:4 ; exp:4; ok
  }  // indent:2 ; exp:2; ok

  @Override  // indent:2 ; exp:2; ok
  public void run() {  // indent:2 ; exp:2; ok
    SecondClassWithLongLongLongLongName anon =  // indent:4 ; exp:4; ok
        new SecondClassWithLongLongLongLongName() {  // indent:8 ; exp:8; ok
          @MyAnnotation
          @Override
          String longLongLongLongLongMethodName() {  // indent:14 ; exp:14; ok
            return "String";  // indent:12 ; exp:12; ok
          }  // indent:10 ; exp:10; ok
        };  // indent:8 ; exp:8; ok
    
    SecondClassWithLongLongLongLongName anon2 = new  // indent:4 ; exp:4; ok
          SecondClassWithLongLongLongLongName() {  // indent:10 ; exp:>8; ok

    };  // indent:4 ; exp:4; ok
  }  // indent:2 ; exp:2; ok
}

class SecondClassWithLongLongLongLongName  // indent:0 ; exp:0; ok
    extends  // indent:4 ; exp:4; ok
         IndentationCorrectClassInput{  // indent:9 ; exp:>4; ok
  private boolean conditionFirst(String longString, int // indent:2 ; exp:2; ok
      integer, InnerClass someInstance) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionFirst1(String longString, int // indent:2 ; exp:2; ok
      integer, InnerClass someInstance) // indent:6 ; exp:6; ok
          throws Exception { // indent:10 ; exp:>6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
}  // indent:0 ; exp:0; ok

@interface MyAnnotation {}

@MyAnnotation
class Foo {}