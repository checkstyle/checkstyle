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
		// violation below 'Opening quotes (""") of text-block must be on the new line'
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
}
