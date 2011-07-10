package tool.sql;


/**
 * 【SQL Server】查询过程中，单引号“'”是特殊字符，所以在查询的时候要转换成双单引号“''”。
 * 但这只是特殊字符的一个，在实际项目中，发现对于like操作还有以下特殊字符：下划线“_”，百分号“%”，方括号“[]”以及尖号“^”。 其用途如下：
 * 下划线：用于代替一个任意字符（相当于正则表达式中的 ? ）
 * 百分号：用于代替任意数目的任意字符（相当于正则表达式中的 * ）
 * 方括号：用于转义（事实上只有左方括号用于转义，右方括号使用最近优先原则匹配最近的左方括号）
 * 尖号：用于排除一些字符进行匹配（这个与正则表达式中的一样）
 * 
 * 以下是一些匹配的举例，需要说明的是，只有like操作才有这些特殊字符，=操作是没有的。 
 * a_b... a[_]b% 
 * a%b... a[%]b%
 * a[b... a[[]b% 
 * a]b... a]b% 
 * a[]b... a[[]]b% 
 * a[^]b... a[[][^]]b% 
 * a[^^]b... a[[][^][^]]b%
 * 
 * 在实际进行处理的时候，
 * 对于=操作，我们一般只需要如此替换： 
 * ' -> '' 
 * 对于like操作，需要进行以下替换（注意顺序也很重要）
 *  [ -> [[] (这个必须是第一个替换的!!)
 *   % -> [%] (这里%是指希望匹配的字符本身包括的%而不是专门用于匹配的通配符) 
 *   _ -> [_] 
 *   ^ -> [^]
 * 
 */
/**
 * 转义sql特殊字符，
 */
public class EscapeSqlServerValue {
	public static String escapeSqlServerValue(String value) {
		if (value != null) {
			value = value.replaceAll("\\[", "[[]");
			value = value.replaceAll("_", "[_]");
			value = value.replaceAll("%", "[%]");
			value = value.replaceAll("\\^", "[^]");
			value = value.replaceAll("'", "['']");//=操作，其他的是like操作
			value = value.replaceAll("\\{", "[{]");
			value = value.replaceAll("\\}", "[}]");
		}

		return value;
	}
	public static void main(String[] args) {
		String bookNameString = "a%b";
		String sqlString = "select * from books where name like %"+escapeSqlServerValue(bookNameString)+"%";
		System.out.println(sqlString);
	}

}
