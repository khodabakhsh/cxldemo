import org.apache.ibatis.session.SqlSession;

public class MyOwnTestMain {
	public static void main(String[] args) {
		for (int i = 0; i < SqlSession.class.getInterfaces().length; i++) {
			String string = SqlSession.class.getInterfaces()[i].getName();
			System.out.println("4444444");
			System.out.println(string);
		}
		boolean foo = true;
		boolean bar = false;
		System.out.println("true|=false   :  " + (foo |= bar));
		foo = true;
		bar = true;
		System.out.println("true|=true   :  " + (foo |= bar));
		foo = false;
		bar = true;
		System.out.println("false|=true   :  " + (foo |= bar));
		foo = false;
		bar = false;
		System.out.println("false|=false   :  " + (foo |= bar));
	}
}
