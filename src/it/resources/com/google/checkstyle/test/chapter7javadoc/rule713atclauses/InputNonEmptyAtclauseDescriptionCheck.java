package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

class InputNonEmptyAtclauseDescriptionCheck
{
	/**
	 * Some javadoc
	 * @param a Some javadoc
	 * @param b Some javadoc
	 * @param c Some javadoc
	 * @return Some javadoc
	 * @throws Exception Some javadoc
	 * @deprecated Some javadoc
	 */
	public int foo1(String a, int b, double c) throws Exception
	{
		return 1;
	}

	/**
	 * 
	 * @param a Some javadoc
	 * @param b Some javadoc
	 * @param c Some javadoc
	 * @return Some javadoc
	 * @throws Exception Some javadoc
	 */
	public int foo2(String a, int b, double c) throws Exception
	{
		return 1;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 * @throws Exception
	 * @deprecated
	 */
	public int foo3(String a, int b, double c) throws Exception
	{
		return 1;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public int foo4(String a, int b, double c) throws Exception
	{
		return 1;
	}
}