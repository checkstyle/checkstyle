/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingWithTabs {

	void testMethod1() {
		String[] arr = {
			"""
            First block
            """, // violation 'Text-block quotes are not vertically aligned'
				"""
            Second block
                """ // violation 'Text-block quotes are not vertically aligned'
		};
	}

	private static String testMethod2() {
		// violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
		// violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
		return """
				def flowNodesById = existingInstance.flowNodeInstances.stream()
				def newFlowNodes = params.instance.flowNodeInstances;
				""" // violation 'Text-block quotes are not vertically aligned'
				+
				"""
				def isUserTaskImport = "user-task".equals(params.sourceExportIndex);
				for (def newFlowNode : newFlowNodes) {}
	            """; // violation 'Text-block quotes are not vertically aligned'
	}

	private void testMethod3() {
		char[] channelNames =
				getMethodOne(
				// violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
				// violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
						new ObjectString("""
								<doc type="one">
								</doc>
								""")) // violation 'Text-block quotes are not vertically aligned'
						.toCharArray();
	}

	public String getMethodOne(ObjectString s1) {
		return s1 + "";
	}

    class ObjectString {
        public ObjectString(String s1) {}
    }
}
