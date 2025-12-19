/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting4 {
    private static final String tabsInsteadOfSpaces =
            """
                <?xml version="1.0" encoding="UTF-8"?>
                     <beans:beans xmlns="http://www.springframework.org/schema/integration"
                        <channel id="routingChannel" />
                        <payload-type-router input-channel="routingChannel"/>
                        </beans:beans>
                        """;
    // violation above 'Text-block quotes are not vertically aligned'

    private static String testMethod(
        final long processInstanceKey) {
            // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
            // 3 violations 5 lines below:
            //   'Text indentation is less than opening quotes indentation'
            //   'Closing quotes (""") of text-block should not be preceded by non-whitespace'
            //   'Text-block quotes are not vertically aligned'
            String concat = """
                The quick brown fox""" + "  \n" + """
                jumps over the lazy dog a href
                """;
            // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'
            // violation 3 lines above 'Text indentation is less than opening quotes indentation'
            // violation 3 lines above 'Text-block quotes are not vertically aligned'

            // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
            // violation 2 lines below 'Text indentation is less than opening quotes indentation'
            return """
                Expected to migrate process instance '%d' \
                but a concurrent command was executed on the process instance. \
                Please retry the migration."""
                    .formatted(processInstanceKey);
            // 2 violations 2 lines above:
            //   'Closing quotes (""") of text-block should not be preceded by non-whitespace'
            //   'Text-block quotes are not vertically aligned'
    }

    private static String testMethod2() {
        String simplePropertyUpdateScript = "simplePUS";
        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Text indentation is less than opening quotes indentation'
        return ("""
           def newInstance = params.instance;
           def existingInstance = ctx._source;
        """
           + simplePropertyUpdateScript);
        // violation 2 lines above 'Text-block quotes are not vertically aligned'
   }

    private static String testMethod21() {
        String simpleProp = "simplePUS";

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Text indentation is less than opening quotes indentation'
        return """
            def newInstance = params.instance;
            def existingInstance = ctx._source;
            """ // violation 'Text-block quotes are not vertically aligned'
            + simpleProp // violation 2 lines below 'Opening quotes (""") of text-block must be on the new'
                // violation 2 lines below 'Text indentation is less than opening quotes indentation'
            + """
            if (existingInstance.startDate != null && existingInstance.endDate != null) {
              def dateFormatter = new SimpleDateFormat(params.dateFormatPattern);
               .collect(Collectors.toMap(variable -> variable.id, Function(), (oldVar) ->
                  (newVar.version > oldVar.version) ? newVar : oldVar
               )).values();
           }
           """; // violation 'Text-block quotes are not vertically aligned'
    }

    private static String testMethod3() {
        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Text indentation is less than opening quotes indentation'
        return """
            def flowNodesById = existingInstance.flowNodeInstances.stream()
            def newFlowNodes = params.instance.flowNodeInstances;
            """ // violation 'Text-block quotes are not vertically aligned'
            +
            """
            def isUserTaskImport = "user-task".equals(params.sourceExportIndex);
            for (def newFlowNode : newFlowNodes) {}
            """
            + UserTaskDurationScriptUtil.createUpdateUser();
    }

    private static String testMethod4() {
        String simpleProp = "simplePUS";
        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Text indentation is less than opening quotes indentation'
        return """
            def incidentsById = existingInstance.incidents.stream()
        """ // violation 'Text-block quotes are not vertically aligned'
         + simpleProp // violation below 'Opening quotes (""") of text-block must be on the new'
         + """
               if (existingIncident.createTime != null && existingIncident.endTime != null) {
                 def dateFormatter = new SimpleDateFormat(params.dateFormatPattern);
             }
           }
           """
         +
         """
               def flowNodeIdsByFlowNodeInstanceIds = flowNodesById.values()
                 .collect(Collectors.toList());
           """; // violation 'Text-block quotes are not vertically aligned'
    }

    class UserTaskDurationScriptUtil {
        public static String createUpdateUser() {
            return "CreateUpdateUser";
        }
    }
}
