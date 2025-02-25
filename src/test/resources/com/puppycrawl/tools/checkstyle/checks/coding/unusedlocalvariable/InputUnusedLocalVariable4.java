/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariable4 {
    static class HttpServlet3RequestFactory {
        static Servlet3SecurityContextHolderAwareRequestWrapper getOne() {
            HttpServlet3RequestFactory outer = new HttpServlet3RequestFactory();
            return outer.new Servlet3SecurityContextHolderAwareRequestWrapper();
        }

        // private class Servlet3SecurityContextHolderAwareRequestWrapper extends SecurityContextHolderAwareRequestWrapper
        private class Servlet3SecurityContextHolderAwareRequestWrapper  {
        }
    }
}
