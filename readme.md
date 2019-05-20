readme first

this project can generate WAR and JAR both ;)

minimum tomcat version for WAR file is 8.5
jar file has embedded server, so no need to worry.

there are 4 environments available to be configured 
as a best practice keep your configurations replicated in all these environments
dev,sandbox,staging,prod

and 2 build output which are JAR and WAR

the combination of environment and build output is managed via maven profile switch.

to run project to test WAR file mode with dev environment configs
mvn clean jetty:run -Pwar,dev

to generate prod mode self executable jar in target folder
mvn clean install -Pjar,prod
