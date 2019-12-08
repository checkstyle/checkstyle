Check documentation: https://checkstyle.org/config_xxxxxx.html#NameOfAffectedCheck

```
/var/tmp $ javac YOUR_FILE.java
#[[MAKE SURE THERE IS NO OUTPUT]]

/var/tmp $ cat config.xml
#[[PLACE YOUR OUTPUT HERE]]

/var/tmp $ cat YOUR_FILE.java
#[[PLACE YOU OUTPUT HERE]]

/var/tmp $ RUN_LOCALE="-Duser.language=en -Duser.country=US"
/var/tmp $ java $RUN_LOCALE -jar checkstyle-X.XX-all.jar -c config.xml YOUR_FILE.java
#[[PLACE YOUR OUTPUT HERE]]
```

---------------

Describe what you expect in detail.

--------------

Still not clear ???
see example - https://checkstyle.org/report_issue.html#How_to_report_a_bug.3F

ATTENTION: FAILURE TO FOLLOW THE ABOVE TEMPLATE WILL RESULT IN THE ISSUE BEING CLOSED.
