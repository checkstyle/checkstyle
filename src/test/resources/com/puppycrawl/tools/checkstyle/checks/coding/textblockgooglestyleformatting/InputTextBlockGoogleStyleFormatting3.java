/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting3 {
    private static final String tabsInsteadOfSpaces =
  			"""
  					<?xml version="1.0" encoding="UTF-8"?>
  					<beans:beans xmlns="http://www.springframework.org/schema/integration"
  						xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 						xmlns:beans="http://www.springframework.org/schema/beans"
  						xsi:schemaLocation="http://www.springframework.org/schema/beans
 							https://www.springframework.org/schema/beans/spring-beans.xsd
  							http://www.springframework.org/schema/integration
  							https://www.springframework.org/schema/spring-integration.xsd">
  
  						<channel id="routingChannel" />
 						<payload-type-router input-channel="routingChannel"/>
 					</beans:beans>
  					""";
	// violation above 'Text-block quotes are not vertically aligned'

    private static String testMethod(
        final long processInstanceKey) {
		    // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
			// 2 violations 4 lines below:
			//   'Closing quotes (""") of text-block must be on the new line'
            //   'Text-block quotes are not vertically aligned'
            String concat = """
                The quick brown fox""" + "  \n" + """
                jumps over the lazy dog a href
                """;
			// violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'
			// violation 2 lines above 'Text-block quotes are not vertically aligned'

			// violation below 'Opening quotes (""") of text-block must be on the new line'
        	return """
            	Expected to migrate process instance '%d' \
            	but a concurrent command was executed on the process instance. \
            	Please retry the migration."""
                	.formatted(processInstanceKey);
			// 2 violations 2 lines above:
        	//   'Closing quotes (""") of text-block must be on the new line.'
        	//   'Text-block quotes are not vertically aligned'
   }

   private static String testMethod2() {
	   // violation below 'Opening quotes (""") of text-block must be on the new line'
       return ("""
	       def newInstance = params.instance;
           def existingInstance = ctx._source;
         """
           + simplePropertyUpdateScript);
	   // violation 2 lines above 'Text-block quotes are not vertically aligned'
   }
}
