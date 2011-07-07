package simpleThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal是如何做到为每一个线程维护变量的副本的呢？
 * 其实实现的思路很简单，在ThreadLocal类中有一个Map，用于存储每一个线程的变量的副本。比如下面的示例实现：
 */
/**
 * 当然ThreadLocal并不能替代同步机制，两者面向的问题领域不同。
 * 同步机制是为了同步多个线程对相同资源的并发访问，是为了多个线程之间进行通信的有效方式；
 * 而ThreadLocal是隔离多个线程的数据共享，从根本上就不在多个线程之间共享资源（变量），这样当然不需要对多个线程进行同步了。
 * 所以，
 * 假如你需要进行多个线程之间进行通信，则使用同步机制；
 * 假如需要隔离多个线程之间的共享冲突，可以使用ThreadLocal。 
 */
public class SimpleThreadLocal {
	private Map values = Collections.synchronizedMap(new HashMap());

	public Object get() {
		Thread curThread = Thread.currentThread();
		Object o = values.get(curThread);
		if (o == null && !values.containsKey(curThread)) {
			o = initialValue();
			values.put(curThread, o);
		}
		return o;
	}

	public void set(Object newValue) {
		values.put(Thread.currentThread(), newValue);
	}

	public Object initialValue() {
		return null;
	}
}
