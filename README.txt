JMoney - A Personal Finance Manager
Copyright (C) 2000-2003 Johann Gyger <jgyger@users.sf.net>
________________________________________________________________________________

JMoney is a personal finance manager written in Java. It supports multiple 
accounts in different currencies, double entry banking, income/expense 
categories, various reports and QIF file import/export.

Languages currently supported are English, German, Italian and Portuguese. 
If you want to translate JMoney into another language, please contact me.

JMoney is in development stage. This means that there are bugs that might occur.
Please use the bug tracker on SourceForge to submit bugs.

Many features are planned: graphs, reconciling, online banking (HBCI), etc.


PREREQUISITES
-------------

First of all, you need a running Java (at least version 1.4.0) environment
(see http://www.java.com for further information).


STARTUP
-------

Unzip the JMoney distribution file. At the command prompt, change to the 
directory where you unzipped JMoney and type the follwing:

	java -jar lib/jmoney.jar


COMPILATION
-----------

JMoney depends on several libraries. They are included in the binary and source
distribution but are not on CVS. Put the corresponding jar files into the lib
directory if you want to compile from CVS.

JasperReports 0.4.5
    Report generation tool
	http://jasperreports.sourceforge.net



Kunststoff 2.0.1 (optional)
    Preferred look and feel.
    http://www.incors.org

If you want to compile the sources you need Ant 
(see http://jakarta.apache.org/ant). At the command prompt, type

	ant jar

and you're done.

The website can be built using Maven 
(http://jakarta.apache.org/turbine/maven). Type

	maven site:generate

to create the website locally, and

	maven site:deploy

to move it to the webserver (specified in project.xml).
