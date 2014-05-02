package com.puppycrawl.tools.checkstyle.grammars.java8;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class LambdaTest extends BaseCheckTestSupport {

	@Test
	public void testLambdaInVariableInitialization()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest1.java"), expected);

	}

	@Test
	public void testWithoutArgsOneLineLambdaBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest2.java"), expected);

	}

	@Test
	public void testWithoutArgsFullLambdaBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest3.java"), expected);

	}

	@Test
	public void testWithOneArgWithOneLineBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest4.java"), expected);

	}

	@Test
	public void testWithOneArgWithFullBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest5.java"), expected);

	}

	@Test
	public void testWithOneArgWIthoutTypeOneLineBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest6.java"), expected);

	}

	@Test
	public void testWithOneArgWIthoutTypeFullBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest7.java"), expected);

	}

	@Test
	public void testWithFewArgsWithoutTypeOneLineBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest8.java"), expected);

	}

	@Test
	public void testWithFewArgsWithoutTypeFullBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest9.java"), expected);

	}

	@Test
	public void testWithOneArgWIthoutParenthesesWithoutTypeOneLineBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest10.java"), expected);

	}

	@Test
	public void testWithOneArgWIthoutParenthesesWithoutTypeFullBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest11.java"), expected);

	}

	@Test
	public void testWithFewArgWIthTypeOneLine()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest12.java"), expected);

	}

	@Test
	public void testWithFewArgWithTypeFullBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest13.java"), expected);

	}

	@Test
	public void testWIthMultilineBody()
			throws Exception
	{
		final DefaultConfiguration checkConfig =
				createCheckConfig(MemberNameCheck.class);
		final String[] expected = {};
		verify(checkConfig, getPath("grammars/java8/InputLambdaTest14.java"), expected);

	}

}
