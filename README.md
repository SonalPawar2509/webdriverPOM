# README

**Tools used**

- TestNG -> Unit Testing Framework
- Maven -> Build Tool
- Allure Report -> For HTML Report Generations
- Log4j -> logging for Java
- Git -> Version control

**Prerequisites**
 - Install Java 1.8 or above >> Set the environment variable
 - Configure Maven >> Set the environment variable
 - Install Allure Report >> Set the environment variable
 - Download the Framework from GIT


**How to Execute**
  - Open Terminal
  - Navigate to project path from Terminal
  - Type the below command

	mvn clean test -Dbrowser=chrome -DcountryCode=uk

   - Alternatively  you can also execute tests using runner.xml file by just right clicking on it and select as "Run as TestNG" 
   
**Reports**
  - Reports will be generated under target/allure-reports after execution

**Viewing the Report**
  - Open Terminal
  - Navigate to the project folder
  - Run the command
	
	allure serve target/allure-results