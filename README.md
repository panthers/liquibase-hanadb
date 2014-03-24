liquibase-hanadb
================

Liquibase extension for SAP's HANA Database

To use the extension, simply add the liquibase-hana.jar to your classpath.

Download the liquibase-hana.jar file from:
* Direct: ["release" section on github](https://github.com/panthers/liquibase-hanadb/releases)
* Maven: Group org.liquibase.ext, Artifact liquibase-hanadb

Forked from https://github.com/liquibase/liquibase-hanadb by @nvoxland
* Incorporated lbitonti (https://github.com/lbitonti/liquibase-hana) changes and updates by @lbitonti
* Added support for 3.1.1
* Hana Schema & Catalog name workaround fix (accessed from system property)
* Reserved keywords added
