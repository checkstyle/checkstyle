/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar;

/**
 * Input for Java 7 try-with-resources.
 */
public class InputJava7TryWithResources // ok
{
    public static class MyResource implements AutoCloseable {
        @Override
        public void close() throws Exception { }
    }

    public static void main(String[] args) throws Exception {
        try (MyResource resource = new MyResource()) { }

        try (MyResource resource = new MyResource()) { }
        finally { }

        try (MyResource resource = new MyResource();) { }
        catch (Exception e) { }

        try (MyResource resource = new MyResource();) { }
        catch (Exception e) { }
        catch (Throwable t) { }
        finally { }

        try (MyResource resource = new MyResource(); MyResource resource2 = new MyResource()) { }
        catch (Exception e) { }
        catch (Throwable t) { }
        finally { }

        try (MyResource resource = new MyResource(); MyResource resource2 = new MyResource();) { }
        catch (Exception e) { }
        catch (Throwable t) { }
        finally { }

        try (@SuppressWarnings("all") final MyResource resource = new MyResource()) { }
    }
}
