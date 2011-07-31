package primeNumber;
/**
 * 求numbers以内的质数
 */
public class PrimeNumber {

	public static void primeNumber(int numbers) {
		int n = 0;
		for (int i = 2; i <= numbers; i++) {
			int k = 0;
			for (int j = 2; i >= j * j; j++) {
				if (i % j == 0) {
					k++;
					break;
				}
			}
			if (k < 1) {
				n++;
				System.out.println("PrimeNumber " + n + ": -> " + i);
			}

		}
	}
	public static void main(String[] args) {
		primeNumber(100);
	}
}
