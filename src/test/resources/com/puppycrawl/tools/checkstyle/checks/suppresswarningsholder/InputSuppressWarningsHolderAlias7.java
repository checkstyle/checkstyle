/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = MethodName=name

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck
format = ^[A-Z][a-zA-Z0-9]*$

com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck
applyToPublic = false


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderAlias7 {

    public void Method1() {}

    @SuppressWarnings("Name")
    private void Method2() {}

    private void Method3() {} // violation 'Name 'Method3' must match pattern.'

    @SuppressWarnings("NAme")
    protected void Method4() {}

    protected void Method5() {} // violation 'Name 'Method5' must match pattern.'

    @SuppressWarnings("NaMe")
    public void method6() {}

    public void method7() {} // violation 'Name 'method7' must match pattern.'

    @SuppressWarnings("NAME")
    private void method8() {}

    private void method9() {} // violation 'Name 'method9' must match pattern.'

    @SuppressWarnings("NAME")
    protected void method10() {}

    protected void method11() {} // violation 'Name 'method11' must match pattern.'

    public void _methodCheck1() {}
    // violation above 'Name '_methodCheck1' must match pattern.'

    @SuppressWarnings("name")
    public void _methodCheck2() {}

    private void _methodCheck3() {}
    // 2 violations above:
    //  'Name '_methodCheck3' must match pattern.'
    //  'Name '_methodCheck3' must match pattern.'

    @SuppressWarnings("NAME")
    private void _methodCheck4() {}

    protected void _methodCheck5() {}
    // 2 violations above:
    //  'Name '_methodCheck5' must match pattern.'
    //  'Name '_methodCheck5' must match pattern.'

    @SuppressWarnings("Name")
    protected void _methodCheck6() {}

}
