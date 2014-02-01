*How to create Eclispe project for Checkstyle:

1. Do build of Checkstyle project in command line (to build generated sources):
mvn verify


2. In Eclipse, do "Import ..." > "Maven / Existing Maven Project".

3. In project context menu "Build Path" > "Configure Build Path ..."
In "Properties for checkstyl" dialog, in "Source" tab, 
press "Add Folder" and select folder "antlr" under "target/generated-sources".



*How to run the JUnit tests in the Eclipse:

1. Select project in Eclipse view "Project Explorer", in context menu "Debug As" > "Debug Configurations ...".
2. Select JUnit element in tree, click "New Launch Configuration" icon button on the top.
3. In Junit configuration, "Test Runner" should be "JUnit4"
4. In tab "Arguments" you need to set the following "VM arguments"

On Linux:
-Dtestinputs.dir=${project_loc}/src/testinputs/com/puppycrawl/tools/checkstyle
-Dtestsrcs.dir=${project_loc}/src/tests/com/puppycrawl/tools/checkstyle
-Dcheckstyle.root=${project_loc}

On Windows: 
-Dtestinputs.dir=${project_loc}\src\testinputs\com\puppycrawl\tools\checkstyle
-Dtestsrcs.dir=${project_loc}\src\tests\com\puppycrawl\tools\checkstyle
-Dcheckstyle.root=${project_loc}

5. Press "Debug".

Note: 
If got "variable references empty selection ${project_loc}" error in Eclipse while running your launch/debug configuration, before you run your launch configuration you need to have your project selected in the project explorer so that Eclipse can know which project’s location you want to refer to. 