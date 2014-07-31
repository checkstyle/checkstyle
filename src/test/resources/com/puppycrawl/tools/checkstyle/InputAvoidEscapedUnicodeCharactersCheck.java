package com.puppycrawl.tools.checkstyle;

public class InputForbidEscapedUnicodeCharactersCheck {

	private String unitAbbrev2 = "\u03bcs";

	private String unitAbbrev3 = "\u03bcs"; // Greek letter mu

	private String unitAbbrev3 = "\u03bcs"; // Greek letter mu

	public Object fooString() {
		String unitAbbrev = "Î¼s";
		String unitAbbrev2 = "\u03bcs";
		String unitAbbrev3 = "\u03bcs"; // Greek letter mu, "s"
		String fakeUnicode = "asd\tsasd";
		String fakeUnicode2 = "\\u23\\u123i\\u";
		return "\ufeff" + content; // byte order mark
	}

	public Object fooChar() {
		char unitAbbrev2 = '\u03bc';
		char unitAbbrev3 = '\u03bc'; // Greek letter mu, "s"
		return '\ufeff' + content; // byte order mark
	}

	public void multiplyString() {
		String unitAbbrev2 = "asd\u03bcsasd";
		String unitAbbrev3 = "aBc\u03bcssdf\u03bc"; /* Greek letter mu, "s" */
		String unitAbbrev4 = "\u03bcaBc\u03bcssdf\u03bc";
		String allCharactersEscaped = "\u03bc\u03bc";
	}

	private static String abbreviate(TimeUnit unit) {
		switch (unit) {
		case NANOSECONDS:
			return "ns";
		case MICROSECONDS:
			return "\u03bcs"; // μs
		case MILLISECONDS:
			return "ms";
		case SECONDS:
			return "s";
		case MINUTES:
			return "min";
		case HOURS:
			return "h";
		case DAYS:
			return "d";
		default:
			throw new AssertionError();
		}

		static final String WHITESPACE_TABLE = ""
				+ "\u2002\u3000\r\u0085\u200A\u2005\u2000\u3000\\"
				+ "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680"
				+ "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009"
				+ "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";
	    }
	
	      public boolean matches(char c) {
	        switch (c) {
	          case '\t':
	          case '\n':
	          case '\013':
	          case '\f':
	          case '\r':
	          case ' ':
	          case '\u0085':
	          case '\u1680':
	          case '\u2028':
	          case '\u2029':
	          case '\u205f':
	          case '\u3000':
	            return true;
	          case '\u2007':
	            return false;
	          default:
	          return c >= '\u2000' && c <= '\u200a';
	      }
	 }
}