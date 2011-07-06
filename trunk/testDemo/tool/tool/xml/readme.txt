用Java解析XML文档，最常用的有两种方法：使用基于事件的XML简单API（Simple API for XML）称为SAX和
基于树和节点的文档对象模型（Document Object Module）称为DOM。
Sun公司提供了Java API for XML Parsing（JAXP）接口来使用SAX和DOM，通过JAXP，我们可以使用任何与JAXP兼容的XML解析器。
　　
　　JAXP接口包含了三个包：
　　
　　（1）org.w3c.dom　W3C推荐的用于XML标准规划文档对象模型的接口。
　　
　　（2）org.xml.sax　 用于对XML进行语法分析的事件驱动的XML简单API（SAX）
　　
　　（3）javax.xml.parsers解析器工厂工具，程序员获得并配置特殊的特殊语法分析器。
　　

　　
　　DOM编程不要其它的依赖包，因为JDK里自带的JDK里含有的上面提到的org.w3c.dom、org.xml.sax 和javax.xml.parsers包就可以满足条件了。