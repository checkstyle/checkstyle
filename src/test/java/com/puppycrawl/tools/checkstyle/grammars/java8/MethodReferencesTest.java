package com.puppycrawl.tools.checkstyle.grammars.java8;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class MethodReferencesTest extends BaseCheckTestSupport {

	@Test
	public void testCanParse()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputMethodReferencesTest.java"), expected);

	}

	@Test
	public void testFromSpec()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputMethodReferencesTest2.java"), expected);

	}
}
