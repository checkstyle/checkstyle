package com.puppycrawl.tools.checkstyle;

public class InputForbidEscapedUnicodeCharactersCheck {
	
	private String unitAbbrev2 = "\u03bcs";

	private String unitAbbrev3 = "\u03bcs"; //Greek letter mu

	private String unitAbbrev3 =
			"\u03bcs"; //Greek letter mu

	public Object fooString()
	{
		String unitAbbrev = "μs";
		String unitAbbrev2 = "\u03bcs";
		String unitAbbrev3 = "\u03bcs"; // Greek letter mu, "s"
		String fakeUnicode = "asd\tsasd";
		String fakeUnicode2 = "\\u23\\u123i\\u";
		return "\ufeff" + content; // byte order mark
	}

	public Object fooChar()
	{
		char unitAbbrev2 = '\u03bc';
		char unitAbbrev3 = '\u03bc'; // Greek letter mu, "s"
		return '\ufeff' + content; // byte order mark
	}

	public void multiplyString()
	{
		String unitAbbrev2 = "asd\u03bcsasd";
		String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; /* Greek letter mu, "s"*/
		String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
		String allCharactersEscaped = "\u03bc\u03bc";
	}
}