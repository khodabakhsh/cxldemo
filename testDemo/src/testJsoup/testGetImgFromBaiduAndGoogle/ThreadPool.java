
package testJsoup.testGetImgFromBaiduAndGoogle;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * 线程池
 */
public class ThreadPool {

	private static Executor executor;
	
	private static int ThreadPoolSize= 30; //线程池大小

	static {
		executor = Executors.newFixedThreadPool(ThreadPoolSize);
	}

	public static void execute(Runnable run) {
		executor.execute(run);
	}
}
