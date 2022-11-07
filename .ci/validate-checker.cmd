@echo on
set JAVA_HOME="C:\Program Files\OpenJDK\jdk-17"
mvn -X -e --no-transfer-progress clean compile -Pchecker-signature-gui-units-init
