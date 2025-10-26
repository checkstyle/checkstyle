/*
UnusedTryResourceShouldBeUnnamed

*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

public class InputUnusedTryResourceShouldBeUnnamed {
    void test() {
      // violation below, 'Unused try resource 'a' should be unnamed'
      try (AutoCloseable a = lock()) {}
      catch (Exception e) {}

      try (AutoCloseable b = lock()) {
          AutoCloseable c = b;
      } catch (Exception e) {}

      try (AutoCloseable d = lock()) {
          d.close();
      } catch (Exception e){
          System.out.println(e.getMessage());
      }

      // violation below, 'Unused try resource 'e' should be unnamed'
      try (AutoCloseable e = lock()) {}
      catch (Exception e){
          System.out.println(e.getMessage());
      }

      try (AutoCloseable _ = lock()) {}
      catch (Exception e) {}

      try {}
      catch (Exception e) {}

      // violation 2 lines below 'Unused try resource 'b' should be unnamed'
      try (AutoCloseable a =  lock();
           AutoCloseable b = lock()) {
          System.out.println(a);
      }
      catch (Exception e){}

      try (AutoCloseable a = lock();
           AutoCloseable _ = lock()) {
          System.out.println(a);
      }
      catch (Exception e){}
    }

    void test2(){
      try (AutoCloseable autoCloseable = lock()) {
        try {}
        catch (Exception e){}

        // violation below, 'Unused try resource 'autoCloseable2' should be unnamed'
        try (AutoCloseable autoCloseable2 = lock()){}
        catch (Exception e){}

        Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println(autoCloseable);
                }
            };
      }
      catch (Exception e) {}
    }

    void test3() {
      Object v2 = new Object();
      Object v3 = new Object() {
          public void close() {
          }
     };

      // violation below, 'Unused try resource 'v2' should be unnamed'
      try (v2) {
          fail("not an AutoCloseable");
      }
      // violation below, 'Unused try resource 'v3' should be unnamed'
      try (v3) {
          fail("not an AutoCloseable although has close() method");
      }
      try (java.lang.Object) {
          fail("not a variable access");
      }
      try (java.lang) {
          fail("not a variable access");
      }
    }

    static void fail(String reason) {
        throw new RuntimeException(reason);
    }

    AutoCloseable lock() {
        return null;
    }
}
