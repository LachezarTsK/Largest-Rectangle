import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int numberOfBuildings = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] height = new int[numberOfBuildings];

		for (int i = 0; i < numberOfBuildings; i++) {
			height[i] = Integer.parseInt(st.nextToken());
		}

		long result = largestRectangle(height);
		System.out.println(result);
	}

	/**
	 * Finds the rectangle with the maximum possible area and returns the result.
	 * Applying stacks for the indexes reduces the time complexity to O(n).
	 */
	public static long largestRectangle(int[] height) {
		/**
		 * The array stores the maximum number of preceding and following buildings that
		 * have height greater than or equal to that of a given building.
		 */
		long[] maximumLength = new long[height.length];
		maximumLength[0] = 1;
		Stack<Integer> indexes = new Stack<Integer>();
		indexes.push(0);

		/**
		 * The loop finds the number of preceding buildings that have height greater
		 * than or equal to that of the current building.
		 */
		for (int i = 1; i < height.length; i++) {
			while (!indexes.isEmpty() && height[indexes.peek()] >= height[i]) {
				indexes.pop();
			}
			if (indexes.isEmpty()) {
				maximumLength[i] = i + 1;
			} else {
				maximumLength[i] = i - indexes.peek();
			}
			indexes.push(i);
		}

		indexes.clear();
		indexes.push(height.length - 1);
		long largestRectangle = 0;

		/**
		 * The loop finds the number of following buildings that have height greater
		 * than or equal to that of a given building.
		 * 
		 * Combines the result with that for the preceding buildings and calculates the
		 * area of the rectangle.
		 */
		for (int i = height.length - 2; i >= 0; i--) {
			while (!indexes.isEmpty() && height[indexes.peek()] >= height[i]) {
				indexes.pop();
			}
			if (indexes.isEmpty()) {
				maximumLength[i] += height.length - i - 1;
			} else {
				maximumLength[i] += indexes.peek() - i - 1;
			}

			long area = maximumLength[i] * height[i];
			if (largestRectangle < area) {
				largestRectangle = area;
			}
			indexes.push(i);
		}
		return largestRectangle;
	}
}
