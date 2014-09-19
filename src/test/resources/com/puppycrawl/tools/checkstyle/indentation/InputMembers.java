import java.util.HashMap;


/**
 * Some Javadoc.
 */
class FooFieldClass { // indent:0 ; exp:0; ok
    
  boolean flag // indent:2 ; exp:2; ok
     = conditionFirst("Loooooooooooooooooong", new  // indent:5 ; exp:6; warn
      SecondFieldClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:6 ; exp:6; ok
      getInteger(new FooFieldClass(), "Loooooooooooooooooog"), new InnerClassFoo()); // indent:6 ; exp:6; ok
  
  
  String getString(int someInt, String someString) { // indent:2 ; exp:2; ok
    return "String"; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok

  private boolean conditionFirst(String longString, int // indent:2 ; exp:2; ok
      integer, InnerClassFoo someInstance) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  private boolean conditionSecond(double longLongLongDoubleValue, // indent:2 ; exp:2; ok
      String longLongLongString, String secondLongLongString) { // indent:6 ; exp:6; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  class InnerClassFoo { // indent:2 ; exp:2; ok
      
    boolean flag // indent:4 ; exp:4; ok
        = conditionFirst("Loooooooooooooooooong", new // indent:8 ; exp:8; ok
        SecondFieldClassWithVeryVeryVeryLongName("Loooooooooooooooooog"). // indent:8 ; exp:8; ok
            getInteger(new FooFieldClass(), "Loooooooooooooooooog"), // indent:12 ; exp:>8; ok
             new InnerClassFoo()); // indent:13 ; exp:>8; ok
          
    FooFieldClass anonymousClass =
        new FooFieldClass() { // indent:4 ; exp:4; ok
          boolean secondFlag = conditionSecond(10000000000.0, new // indent:6 ; exp:6; ok
              SecondFieldClassWithVeryVeryVeryLongName("Looooooooooooo" // indent:10 ; exp:10; ok
                + "oooooooooooong").getString(new FooFieldClass(), // indent:12 ; exp:>10; ok
                   new SecondFieldClassWithVeryVeryVeryLongName("loooooooooong"). // indent:15 ; exp:>10; ok
                 getInteger(new FooFieldClass(), "loooooooooooooong")), "loooooooooooong"); // indent:13 ; exp:>10; ok
        }; // indent:8 ; exp:8; ok
   } // indent:3 ; exp:2; warn
}

class SecondFieldClassWithVeryVeryVeryLongName { // indent:0 ; exp:0; ok
    
  public SecondFieldClassWithVeryVeryVeryLongName(String string) { // indconditionTenthent:2 ; exp:2; ok
    
  } // indent:2 ; exp:2; ok
    
  String getString(FooFieldClass instance, int integer) { // indent:2 ; exp:2; ok
    return "String"; // indent:4 ; exp:4; ok  
  } // indent:2 ; exp:2; ok
  
  int getInteger(FooFieldClass instance, String string) { // indent:2 ; exp:2; ok
    return -1;   // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  boolean getBoolean(FooFieldClass instance, boolean flag) { // indent:2 ; exp:2; ok
    return false; // indent:4 ; exp:4; ok
  } // indent:2 ; exp:2; ok
  
  SecondFieldClassWithVeryVeryVeryLongName getInstanse() { // indent:2 ; exp:2; ok
    return new SecondFieldClassWithVeryVeryVeryLongName("VeryLoooooooooo" // indent:4 ; exp:4; ok
        + "oongString"); // indent:8 ; exp:8; ok
  } // indent:2 ; exp:2; ok
} // indent:0 ; exp:0; ok

abstract class WithAnnotations { // indent:0 ; exp:0; ok
  @GwtIncompatible("Non-UTF-8" // indent:2 ; exp:2; ok
       + "Charset") // indent:6 ; exp:6; ok
  public static final int FOO_CONSTANT = 111; // indent:2 ; exp:2; ok
  
  @Override
  private void foo34() {}
  final Map<String, String> // indent:2 ; exp:2; ok
      comeMapWithLongName = new HashMap // indent:6 ; exp:6; ok
      <String, String>(); // indent:6 ; exp:6; ok

  @MyAnnotation
  byte[] getBytesInternal() {
    return new byte[] {};
  }
  
  @MyAnnotation public abstract ImmutableMap<R, Map<C, V>> rowMap();
} // indent:0 ; exp:0; ok

@interface GwtIncompatible { // indent:0 ; exp:0; ok
  String value(); // indent:2 ; exp:2; ok
} // indent:0 ; exp:0; ok

@interface MyAnnotation {}

@MyAnnotation
enum MyEnum{
	
}