/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NeedBraces">
      <property name="tokens" value="LAMBDA"/>
      <property name="allowSingleLineStatement" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Assertions;

// xdoc section -- start
class Example6 {
  enum HttpMethod {
      GET,
      OPTIONS
  }
  private CustomCompletableFuture<Object> allowedFuture;
  Example6() {
    allowedFuture = new CustomCompletableFuture<>();
    allowedFuture.addCallback(
            result -> {
              Assertions.assertEquals(
                      "Invalid response",
                      EnumSet.of(HttpMethod.GET, HttpMethod.OPTIONS));},
            ex -> fail(ex.getMessage()) // Need to handle fail method properly
    );
    allowedFuture.addCustomCallback(
            () -> {
              Assertions.assertEquals(
                      "Invalid response",
                      EnumSet.of(HttpMethod.GET, HttpMethod.OPTIONS));},
            ex -> fail(ex.getMessage()) // Need to handle fail method properly
    );
  }
  private String fail(String message) {
    return message;
  }
}
class CustomCompletableFuture<T> {
  private CompletableFuture<T> allowedFuture;
  public CustomCompletableFuture() {
    allowedFuture = new CompletableFuture<>();
  }
  public void addCallback(
          java.util.function.Consumer<T> successCallback,
          java.util.function.Consumer<Throwable> failureCallback) {}
  public void addCustomCallback(
          Runnable successLambda,
          java.util.function.Consumer<Throwable> failureLambda) {}
}
// xdoc section -- end
