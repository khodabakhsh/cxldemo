rem 【bat测试运行java程序(或者可以以打包jar的形式运行)，发邮件】
rem “echo off”表示不显示命令
@echo off 
rem FastMailer存在于包testJavaMail中，而且依赖另外一个类tool.GetDnsIp,
rem 所以，目录结构如下
rem d 
rem  - testJavaMail
rem     -FastMailer.java  
rem  - tool
rem     - GetDnsIp.class
d:
rem 所依赖的jar包,都放在d盘下
set classpath=.;activation-1.1.jar;mail-1.4.jar 
rem 源文件是utf-8编码,FastMailer.java位于d:\testJavaMail\FastMailer.java
javac -encoding utf-8 testJavaMail\FastMailer.java 
rem “start javaw”不显示dos窗口，javaw跟java的不同就是javaw关掉了dos窗口程序也继续GetDnsIp
start javaw testJavaMail.FastMailer 
rem 【pause】 命令表示停止，如果bat文件一闪而过，可以加上这个命令