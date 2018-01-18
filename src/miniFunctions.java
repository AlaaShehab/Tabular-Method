/**
 *
 */

/**
 * @author Personal
 *
 */

public class miniFunctions {

	public boolean checkSubtraction (int num1, int num2){
		int result = num2 - num1;
		int i =0;
		while (result >= (Math.pow(2,i))){
			if(result == Math.pow(2, i)){
				return true;
			}
			i++;
		}
		return false;
	}

	public String convertToVariables(int minTerm, int [] minTerms, int maxNumOfBits){

		char [] expression = new char[maxNumOfBits];
		String finalExp = "";
		int i;
		//convert the val to binary and return an array of char contains
		//the representation of the val in binary
		for (i = 0;i<minTerms.length;i++){
			expression[minTerms[i]] = 'X';
		}

		for(i=0;i<expression.length;i++){
			if(expression[i] == '1'){
				finalExp+=("A"+i);
			}else if (expression[i] == '0'){
				finalExp+=("A"+i+"'");
			}
		}
		return finalExp;

	}
}


