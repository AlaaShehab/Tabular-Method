import java.util.LinkedList;

/**
 *
 */

/**
 * @author Personal
 *
 */
public class MinTerms {


	public LinkedList<Integer>  bitConversion (int number, int maxNumbersBit) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		///convert the number to the binary representation
		int remainder = 0;
		while (number != 0) {
			remainder = number % 2;
			list.add(0, remainder);
			number /= 2;
		}
		/// fill remaining bits with zeroes
		while (list.size() < maxNumbersBit) {
			list.add(0, 0);
		}
		return list;
	}

	public int maxBits (LinkedList<Integer> minTerms) {
		int i = 0;
		int max = minTerms.get(i);
		// find larger minTerm of inputs.
		while (i < minTerms.size() - 1) {
			if (minTerms.get(i) > minTerms.get(i+1)) {
				max = minTerms.get(i);
			}
			i++;
		}
		//convert this minTerm to binary representation and store it in a list.
		LinkedList<Integer> list = new LinkedList<Integer>();
		int remainder = 0;
		while (max != 0) {
			remainder = max % 2;
			list.add(0, remainder);
			max /= 2;
		}
		/// return list size which is number of bits of the max number.
		return list.size();
	}

}
