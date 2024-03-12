---
name: Bug report
about: Create a report to help us improve
title: ''
labels: ''
assignees: ''

---

I have read check documentation: https://checkstyle.org/checks/xxxxxx/nameofaffectedcheck.html
I have downloaded the latest checkstyle from: https://checkstyle.org/cmdline.html#Download_and_Run
I have executed the cli and showed it below, as cli describes the problem better than 1,000 words

```bash
/var/tmp $ javac YOUR_FILE.java
#[[MAKE SURE THERE IS SUCCESSFUL COMPILATION]]

/var/tmp $ cat config.xml
#[[PLACE YOUR OUTPUT HERE]]

/var/tmp $ cat YOUR_FILE.java
#[[PLACE YOU OUTPUT HERE]]

/var/tmp $ RUN_LOCALE="-Duser.language=en -Duser.country=US"
/var/tmp $ java $RUN_LOCALE -jar checkstyle-X.XX-all.jar -c config.xml YOUR_FILE.java
#[[PLACE YOUR OUTPUT HERE]]
```

For Windows users, please use `type` instead of `cat` and run

```cmd
set RUN_LOCALE="-Duser.language=en -Duser.country=US"
java %RUN_LOCALE% -jar checkstyle-X.XX-all.jar -c config.xml YOUR_FILE.java
```

in place of the last 2 commands above.

---

**Describe what you expect in detail.**

---

Still not clear ???
see example - https://checkstyle.org/report_issue.html#How_to_report_a_bug.3F

ATTENTION: FAILURE TO FOLLOW THE ABOVE TEMPLATE WILL RESULT IN THE ISSUE BEING CLOSED.
