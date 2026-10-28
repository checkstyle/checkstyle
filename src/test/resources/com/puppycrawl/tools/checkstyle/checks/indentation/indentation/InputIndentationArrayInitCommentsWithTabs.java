/* Config: tabWidth and basicOffset are supplied by the test. */
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentationArrayInitCommentsWithTabs {
	int[] comments = { // comment
		1
	};
	int[] aligned = { 1,
	                  2};
	@Values({ /* comment */
		1
	})
	int value;
	@interface Values {
		int[] value();
	}
}
