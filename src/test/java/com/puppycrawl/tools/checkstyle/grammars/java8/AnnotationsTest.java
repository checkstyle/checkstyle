package com.puppycrawl.tools.checkstyle.grammars.java8;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class AnnotationsTest extends BaseCheckTestSupport {

	@Test
	public void testInlineAnnotation()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputAnnotationsTest1.java"), expected);

	}

	@Test
	public void testRepeatebleAnnotation()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputAnnotationsTest2.java"), expected);

	}
}
