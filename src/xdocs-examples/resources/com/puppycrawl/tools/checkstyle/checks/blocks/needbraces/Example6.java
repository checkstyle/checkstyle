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

// xdoc section -- start
class Example6 {
  enum HttpMethod {GET, OPTIONS}
  Object result = new Object();
  private CustomCompletableFuture<Object> allowedFuture;
  Example6() {
    allowedFuture = new CustomCompletableFuture<>();
    // violation below ''->' construct must use '{}'s'
    allowedFuture.addCallback(result -> assertEquals("Invalid response",
                    EnumSet.of(HttpMethod.GET, HttpMethod.OPTIONS), result),
            ex -> fail(ex.getMessage()));
    allowedFuture.addCustomCallback(() -> {
      return assertEquals("Invalid response",
              EnumSet.of(HttpMethod.GET, HttpMethod.OPTIONS), result);},
            ex -> fail(ex.getMessage()));}
  private Object assertEquals(String invalidResponse, EnumSet<HttpMethod> get,
                              Object result) {
    return result;}
  private String fail(String message) {
    return message;
  }}
class CustomCompletableFuture<T> {
  private CompletableFuture<T> allowedFuture;
  public CustomCompletableFuture() {
    allowedFuture = new CompletableFuture<>();
  }
  public void addCallback(java.util.function.Consumer<T> successCallback,
          java.util.function.Consumer<Throwable> failureCallback) {}
  public void addCustomCallback(java.util.function.Supplier<Object> successLambda,
          java.util.function.Consumer<Throwable> failureLambda) {}
}
// xdoc section -- end
