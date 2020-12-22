# Feature request template

---
name: Feature request
about: Suggest an idea for this project
title: ''
labels: ''
assignees: ''

---

**Is your feature request related to a problem? Please describe.**
A clear and concise description of what the problem is.

**How it works Now:** Show the current checkstyle running on your code,
so we could know more about your feature request and how checkstyle is
behaving currently. 
```bash
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

**Describe the solution you'd like**
A clear and detailed description of what you want to happen.


**Additional context**
Add any other context or screenshots about the feature request here.

Still not clear ???
see examples:

- https://checkstyle.org/report_issue.html#How_to_request_new_feature_for_existing_functionality.3F

- https://checkstyle.org/report_issue.html#How_to_request_new_Check.2FModule.3F

**Not a feature:?**
If (Check, property, new behavior) that you are requesting is already
implemented and you are unsatisfied with their behaviour, report it here
in bug template format:

- https://checkstyle.org/report_issue.html#How_to_report_a_bug.3F.

ATTENTION: FAILURE TO FOLLOW THE ABOVE TEMPLATE WILL RESULT IN THE ISSUE BEING CLOSED.
