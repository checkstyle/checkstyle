/*xml
<module name="Checker">
<property name="fileExtensions" value="sql"/>
<module name="RegexpSingleline">
<property name="format"
 value="^.*JOIN\s.+\s(ON|USING)$"/>
<property name="message"
 value="Don't use JOIN, use sub-select instead."/>
</module>
<module name="LineLength">
<property name="max" value="60"/>
</module>
<module name="SuppressWithPlainTextCommentFilter">
<property name="offCommentFormat"
 value="CSOFF\: ([\w\|]+)"/>
<property name="onCommentFormat"
 value="CSON\: ([\w\|]+)"/>
<property name="checkFormat" value="$1"/>
</module>
</module>
*/
-- xdoc section - start
-- CSOFF: RegexpSinglelineCheck
-- CSOFF: LineLengthCheck
SELECT name, job_name
FROM users AS u
-- suppressed violation below (RegexpSinglelineCheck)
JOIN jobs AS j ON u.job_id = j.id
WHERE u.registration_date >= '2023-01-01' AND u.status = 'active';
-- // filtered violation above 'Line is longer ...'
-- xdoc section - end
