package InputAnonymousClasses;

import InputAnonymousClasses.StrangeInstance;
import InputAnonymousClasses.InputAnonymousClasses;

@MyAnnotation
public class
    InputAnonymousClasses { // indent:0 ; exp:0; ok
  public InputAnonymousClasses(String longString, String secondLongString) { // indent:2 ; exp:2; ok
    
  } // indent:2 ; exp:2; ok
  public boolean foo() { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  void foo2(StrangeInstance instance) {} // indent:2 ; exp:2; ok
} // indent:0 ; exp:0; ok

class WithAnonnymousClass {
  public static final InputAnonymousClasses anon = new InputAnonymousClasses("Looooooooooooooooong", // indent:2 ; exp:2; ok
      "SecondLoooooooooooong") { // indent:6 ; exp:6; ok
    @Override public boolean foo() { // indent:4 ; exp:4; ok
      return false; // indent:6 ; exp:6; ok
    } // indent:4 ; exp:4; ok
  }; // indent:2 ; exp:2; ok
  
  InputAnonymousClasses foo() { // indent:2 ; exp:2; ok
    return new InputAnonymousClasses(
        "Loooooooooooooooong", "SecondLoooooooooong") { // indent:4 ; exp:4; ok
          @Override public boolean foo() { // indent:6 ; exp:6; ok
            InputAnonymousClasses InputAnonymousClasses = new InputAnonymousClasses("", ""); // indent:8 ; exp:8; ok
            InputAnonymousClasses.equals(new StrangeInstance(new InputAnonymousClasses("", "")) { // indent:8 ; exp:8; ok
              @Override void foo (String loongString, String secondLongString) {} // indent:10 ; exp:10; ok
            }); // indent:8 ; exp:8; ok
            return false; // indent:8 ; exp:8; ok
          } // indent:6 ; exp:6; ok
        }; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
}

class StrangeInstance { // indent:0 ; exp:0; ok
  public StrangeInstance(InputAnonymousClasses InputAnonymousClasses) {} // indent:2 ; exp:2; ok
  void foo (String loongString, String secondLongString) {} // indent:2 ; exp:2; ok
} // indent:0 ; exp:0; ok

@interface MyAnnotation {}