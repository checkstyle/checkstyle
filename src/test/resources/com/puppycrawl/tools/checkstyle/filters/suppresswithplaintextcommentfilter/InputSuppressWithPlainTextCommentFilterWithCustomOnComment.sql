/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressWithPlainTextCommentFilter">
    <property name="onCommentFormat" value="CSON (\w+)"/>
    <property name="messageFormat" value="e[l"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck">
    <property name="format" value="^.*COUNT\(\*\).*$"/>
  </module>
</module>
*/

-- CSON count
EXPLAIN SELECT COUNT(*) FROM restaurants
-- // violation above 'Line matches the illegal pattern'
