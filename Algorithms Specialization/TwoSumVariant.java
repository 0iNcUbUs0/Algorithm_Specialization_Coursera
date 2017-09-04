import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class TwoSumVariant {
	private static ArrayList<Long> numbers;
	private final static int DefaultSize = 1000000;

	private static void input(String path) {
		System.out.println("Processing input file...");
		try {
			numbers = new ArrayList<Long>(DefaultSize);
			File file = new File(path);
			Scanner sc = new Scanner(file);
			while (sc.hasNextLong())
				numbers.add(sc.nextLong());
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static int twoSumVariant() {
		System.out.println("Counting two sum...");
		int count = 0;
		for (long target = -10000; target <= 10000; target++) {
			HashSet<Long> set = new HashSet<Long>();
			for (long i : numbers) {
				long need = target - i;
				if (set.contains(need)) {
					if (need != i) {
						count++;
						break;
					}
				}
				set.add(i);
			}
			if (target % 100 == 0)
				System.out.println((double) (target + 10000) / 20000 + "%");
		}
		return count;
	}

	private static int twoSumVariant2() {
		System.out.println("Counting two sum...");
		Collections.sort(numbers);
		int count = 0;
		for (long target = -10000; target <= 10000; target++) {
			int start = 0, end = numbers.size() - 1;
			while (start < end) {
				long sum = numbers.get(start) + numbers.get(end);
				if (sum == target) {
					if (numbers.get(start) != numbers.get(end))
						count++;
					break;
				} else if (sum < target) {
					start++;
				} else {
					end--;
				}
			}
			if (target % 100 == 0)
				System.out.println((double) (target + 10000) / 20000 * 100 + "%");
		}
		return count;
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Please input file path.");
			System.exit(-1);
		}
		input(args[0]);
		System.out.println(twoSumVariant2());
	}
}