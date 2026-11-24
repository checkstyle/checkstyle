/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CHECKSTYLE stop (\w+) (\w+)"/>
    <property name="onCommentFormat" value="CHECKSTYLE resume (\w+) (\w+)"/>
    <property name="idFormat" value="$1"/>
    <property name="messageFormat" value="$2"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck">
    <property name="id" value="warning"/>
    <property name="format" value="^.*COUNT\(\*\).*$"/>
  </module>
</module>
*/

-- CHECKSTYLE stop warning COUNT
SELECT COUNT(*) FROM filters;
-- // filtered violation above 'Line matches the illegal pattern'

-- CHECKSTYLE resume warning COUNT
SELECT COUNT(*) FROM checks;
-- // violation above 'Line matches the illegal pattern'

-- CHECKSTYLE stop warning NonMatchingMessage
SELECT COUNT(*) FROM reports;
-- // violation above 'Line matches the illegal pattern'
