2012.12.3 petclinic基于spring 3.0.0.0 RELEASE

1.需要从D:\cxl\lib\spring-framework-3.0.0.RELEASE\projects\org.springframework.web.servlet\src\main\resources
把这里面的tld文件放到web工程的WEB-INF文件夹中。貌似与文件夹名无关。。假定是/petclinic/src/main/webapp/WEB-INF/anyWhereCanBeOk文件夹下

2.从web、web-servlet、context的pom文件中拷贝dependency过来（要调整，各种调整,各种依赖,额..）

3.虽然把core、beans加到源码文件夹中，但是他们在Maven dependency中的配置还是保留。