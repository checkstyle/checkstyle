/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="-- CHECKSTYLE OFF"/>
    <property name="onCommentFormat" value="-- CHECKSTYLE ON"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck">
    <property name="eachLine" value="true"/>
  </module>
</module>
*/

-- CHECKSTYLE OFF
	SELECT COUNT(1) FROM filters;
-- // filtered violation above 'Line contains a tab character.'

-- CHECKSTYLE ON
	SELECT COUNT(1) FROM checks;
-- // violation above 'Line contains a tab character.'
