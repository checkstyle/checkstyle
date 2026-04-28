package com.openjdk.checkstyle.test.chapter4naming.rule43methodnames;

import org.junit.jupiter.api.Test;

import com.openjdk.checkstyle.test.base.AbstractOpenJdkModuleTestSupport;

public class MethodNameTest extends AbstractOpenJdkModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/openjdk/checkstyle/test/chapter4naming/rule43methodnames";
    }

    @Test
    public void testMethodNameInvalid() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodNameInvalid.java"));
    }

    @Test
    public void testMethodNameValid() throws Exception {
        verifyWithWholeConfig(getPath("InputMethodNameValid.java"));
    }

}
