@echo off
java -Djava.ext.dirs=webapp/WEB-INF/lib;packages -cp webapp/WEB-INF/classes com.liusoft.dlog4j.upgrade.Main %1 %2 %3 %4 %5