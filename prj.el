;; Configuration for JDEE (Java Development Environment for Emacs)
(jde-set-project-name "checkstyle")
(jde-set-variables
 '(jde-db-source-directories (quote ("./src/checkstyle/")))
 '(jde-global-classpath (quote ("./classes/checkstyle" "./classes/tests" "./dist/checkstyle-1.4/jakarta-regexp-1.2.jar")))
 '(jde-run-application-class "com.puppycrawl.tools.checkstyle.Main")
 '(jde-run-option-application-args (quote ("VerifierImpl.java")))

 ;; indent each level of indentation by 4 spaces
 '(c-basic-offset 4)
)
