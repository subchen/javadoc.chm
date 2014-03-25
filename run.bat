set classpath=%classpath%;lib/commons-collections-3.2.1.jar
set classpath=%classpath%;lib/commons-io-2.4.jar
set classpath=%classpath%;lib/commons-lang-2.6.jar
set classpath=%classpath%;lib/commons-logging-1.1.1.jar
set classpath=%classpath%;lib/log4j-1.2.17.jar
set classpath=%classpath%;lib/velocity-1.7.jar
set classpath=%classpath%;build/javadoc.chm-2.1.0.jar
 
java -Xms512m -Xmx512m jerbrick.tools.chm.Application D:/Downloads/jdk-8-apidocs/docs/api utf-8

