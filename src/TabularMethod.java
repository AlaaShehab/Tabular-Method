import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */

/**
 * @author Personal
 *
 */
public class TabularMethod {
	public LinkedList<Integer>  minTerms = new LinkedList<Integer>();
	private LinkedList<Integer> mintermsCoveredbyPI = new LinkedList<Integer>();
	public LinkedList<Integer> finalPITerms = new LinkedList<Integer>();
	public LinkedList<Integer> essentialPIList = new LinkedList<Integer>();
	public LinkedList<Integer> PIfromMinimization = new LinkedList<Integer>();
	public LinkedList<Integer> dcTerms = new LinkedList<Integer>();
	private list tabularTable = new list();
	private list mappedPI = new list();
	private int PIremaining = 0;
	private int maxMinterm = 0;
	public String content = "";
	public String essential = "";

	private list[] Grouping () {

		list[] groups = covertToGroupArray();
		miniFunctions x = new miniFunctions();

		boolean same = true;
		int counter=0;
		int removed=0;
		int counterLoop = maxBits() - 1;
		while (counter < counterLoop){
		int i = 0;
		for(i = 0;i<groups.length - 1; i++){
			removed = 0;
			final int sizeOfGroup=groups[i].size();
			for(int j = 0;j<sizeOfGroup;j++){
				for(int k =0 ;k<groups[i+1].size();k++){
					same = true;
					if((x.checkSubtraction((Integer)groups[i].get(removed), (Integer)groups[i+1].get(k))) &&
							(groups[i].arraySize(removed) == groups[i+1].arraySize(k))){
						for(int arrayIndex =0 ;arrayIndex<groups[i].arraySize(removed);arrayIndex++){
							if (groups[i].getTag(removed, arrayIndex)!= groups[i+1].getTag(k, arrayIndex)){
								same = false;
							}
						}
						if (same && groups[i].arraySize(removed) != 0) {
						groups[i].add((Integer)groups[i].get(removed));
							for (int sizeThisArray = 0; sizeThisArray<groups[i].arraySize(removed);sizeThisArray++){
								groups[i].addTagToEnd(groups[i].getTag(removed, sizeThisArray));
							}
							groups[i].addTagToEnd((Integer)groups[i+1].get(k)-(Integer)groups[i].get(removed));
							groups[i].setCase(removed, true);
							groups[i+1].setCase(k, true);
						}else if(same && groups[i].arraySize(removed) == 0){
							groups[i].add((Integer)groups[i].get(removed));
							groups[i].addTagToEnd((Integer)groups[i+1].get(k)-(Integer)groups[i].get(removed));
							groups[i].setCase(removed, true);
							groups[i+1].setCase(k, true);
						}
					}
				}
				if(groups[i].getminTermCase(removed)){
					groups[i].remove(removed);
				}else{
					removed++;
				}
			}
		}
		for (int c = 0; c < groups[i].size; c++) {
			if (groups[i].getminTermCase(c)) {
				groups[i].remove(c);
			}
		}
			counter ++;
				for(int t = 0;t<groups.length;t++){
					for(int j = 0;j<groups[t].size();j++){
						for(int k =0 ;k<groups[t].arraySize(j);k++){
							System.out.print(groups[t].get(j) +" ");
							if (counter != 1) {
								System.out.print("(");
								for (int l = 0; l < groups[t].arraySize(j); l++) {
									System.out.println(groups[t].getTag(j, l) + ",");
								}
								System.out.println(")");
							}
							System.out.print(" grouped with ");
							int num =groups[t].getTag(j, k);
							int num2 = (Integer)groups[t].get(j) + num;
							System.out.println(num2);
						}
					}
					System.out.println();
				}
		}

		for(int i=0;i<groups.length;i++){
			for(int j=0;j<groups[i].size();j++){
				for(int z = j+1;z<groups[i].size();z++){
				if((groups[i].get(j) == groups[i].get(z))  &&
						(groups[i].arraySize(j) == groups[i].arraySize(z))){
						same = true;
						for(int k = 0;k<groups[i].arraySize(j) && same;k++){
							same = false;
							for(int m=0;m<groups[i].arraySize(z);m++){
								if(groups[i].getTag(j, k) == groups[i].getTag(z, m)){
									same = true;
								}
							}
						}
						if (same){
							groups[i].remove(z);
						}
					}
				}

			}
		}

	return groups;
	}

	private list[] covertToGroupArray () {
		LinkedList<Integer> allTerms = new LinkedList<Integer>();
		for (int i = 0; i < minTerms.size(); i++) {
			allTerms.add(minTerms.get(i));
		}
		for (int i = 0; i < dcTerms.size(); i++) {
			allTerms.add(dcTerms.get(i));
		}
		int maxNumberOfOnes = 0;
		if (allTerms.size() != 0) {
			maxNumberOfOnes = countNumberOfOnes(allTerms.get(0));
		}
		for (int i = 0; i < allTerms.size(); i++) {
			if (countNumberOfOnes(allTerms.get(i)) > maxNumberOfOnes) {
				maxNumberOfOnes = countNumberOfOnes(allTerms.get(i));
			}
		}
		list[] groups = new list[maxNumberOfOnes + 1];
		for (int i = 0; i < groups.length; i++) {
			groups[i] = new list();
		}

		for (int i = 0; i < allTerms.size(); i++) {
			groups[countNumberOfOnes(allTerms.get(i))].add(allTerms.get(i));
		}
		return groups;
	}

	public String[] readfile (String pathname) {
		minTerms.clear();
		dcTerms.clear();

		String[] terms = new String[2];
		FileReader file = null;
		try {
			file = new FileReader(pathname);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid File");
			return terms;
		}
		BufferedReader readFile = new BufferedReader(file);

		String minterms = null;
		boolean mintermsExist = true;
		try {
			String temp;
			temp = readFile.readLine();
			if (temp != null) {
				minterms = temp;
			} else {
				mintermsExist = false;
			}
		} catch (IOException e) {
			System.out.println("Invalid file Input");
			mintermsExist = false;
		}

		if (mintermsExist) {
			terms[0] = minterms.replaceAll(",", " ");
			minterms += ",";
			String regex = "(\\d+,)";
			Matcher pattern = Pattern.compile(regex).matcher(minterms);
			while (pattern.find()) {
				String[] spliter;
				spliter = pattern.group().split(",");
				minTerms.add(Integer.valueOf(spliter[0]));
				if (minTerms.getLast() > maxMinterm) {
					maxMinterm = minTerms.getLast();
				}
			}
		} else {
			terms[0] = "";
		}

		String dontCares = null;
		boolean dontCaresExist = true;

		try {
			String temp;
			temp = readFile.readLine();
			if (temp != null) {
				dontCares = temp;
			} else {
				dontCaresExist = false;
			}
		} catch (IOException e) {
			dontCaresExist = false;
		}

		if (dontCaresExist) {
			terms[1] = dontCares.replaceAll(",", " ");
			dontCares += ",";
			String regex = "(\\d+ )";
			Matcher pattern = Pattern.compile(regex).matcher(dontCares);
			String[] spliter;
			while (pattern.find()) {
				spliter = pattern.group().split(",");
				dcTerms.add(Integer.valueOf(spliter[0]));
			}
		} else {
			terms[1] = "";
		}
		return terms;
	}

	public void readMinterms (String minterms) {
		minTerms.clear();
		minterms += " ";
		String regex = "(\\d+ )";
		String[] spliter;
		Matcher pattern = Pattern.compile(regex).matcher(minterms);
		while (pattern.find()) {
			spliter = pattern.group().split(" ");
			minTerms.add(Integer.valueOf(spliter[0]));
			if (minTerms.getLast() > maxMinterm) {
				maxMinterm = minTerms.getLast();
			}
		}
	}

	public void readDC (String dontCares) {
		dcTerms.clear();
		dontCares += " ";
		String regex = "(\\d+ )";
		String[] spliter;
		Matcher patter = Pattern.compile(regex).matcher(dontCares);
		while (patter.find()) {
			spliter = patter.group().split(" ");
			dcTerms.add(Integer.valueOf(spliter[0]));
		}

	}

	public void scan() {
		System.out.println("Please Enter Minterms: ");
		Scanner scan = new Scanner(System.in);
		String minterms = scan.nextLine();
		System.out.println("Please Enter Don't Care Terms -if doesn't exist press any key-: ");
		String dontCares = scan.nextLine();
		readMinterms(minterms);
		readDC(dontCares);
	}

	private Integer countNumberOfOnes(Integer u) {
		Integer uCount = 0;
		uCount = u - ((u >> 1) & 033333333333) - ((u >> 2) & 011111111111);
		return ((uCount + (uCount >> 3)) & 030707070707) % 63;
	}
//// make tabular table with minterms and PI covering them
	public void tabularTable() {
		tabularTable.clear();
		essentialPIList.clear();
		finalPITerms.clear();
		PIfromMinimization.clear();

		list[] groups = Grouping();
		for (int i = 0; i <= maxMinterm; i++) {
			tabularTable.add(i);
		}
		int PInumber = 1;   ///PInumber is a number that represents the primeImplicants elly tl3t
		/// 7b2a me7taga 7aga t3ml map lel numbers di bel primeImplicants.
		for(int i = 0; i < groups.length - 1; i++) {
			for(int j = 0; j < groups[i].size(); j++) {
				LinkedList<Integer> primeImplicantTags = new LinkedList<Integer>();
				int sizeOfGroupArray = groups[i].arraySize(j);
				for(int arrayIndex = 0 ;arrayIndex<sizeOfGroupArray;arrayIndex++){
					primeImplicantTags.add(groups[i].getTag(j, arrayIndex));
				}
				mintermsCoveredbyPI.clear();
				int groupNumber = (int) groups[i].get(j);
				mintermsCovered(primeImplicantTags, groupNumber);
				removeDCinMintermsCovered(minTerms);
				for (int mintermsFromPI = 0; mintermsFromPI < mintermsCoveredbyPI.size(); mintermsFromPI++) {
					tabularTable.addTag(PInumber, mintermsCoveredbyPI.get(mintermsFromPI));
				}
				PInumber++;
			}
		}
		PIremaining = PInumber - 1;
		removeNonMinterms();
		minimization();
		try {
			print(groups);
		} catch (IOException e) {

		}
	}

	private void mintermsCovered (LinkedList<Integer> primeImplicantTags, int groupNumber) {
		if (primeImplicantTags.isEmpty()) {
			return;
		} else {
			if (!(mintermsCoveredbyPI.contains(groupNumber))) {
				mintermsCoveredbyPI.add(groupNumber);
			}
			for (int i = 0; i < primeImplicantTags.size(); i++) {
				int number = groupNumber + primeImplicantTags.get(i);
				if (!(mintermsCoveredbyPI.contains(number))) {
					mintermsCoveredbyPI.add(number);
				}
			}
			int newGroupNumber = groupNumber + primeImplicantTags.get(0);
			primeImplicantTags.remove(0);
			mintermsCovered (primeImplicantTags, newGroupNumber);
		}
	}

	private void removeDCinMintermsCovered (LinkedList<Integer> minTerms) {  // DC = don't cares
		for (int i = 0; i < mintermsCoveredbyPI.size(); i++) {
			if(!(minTerms.contains(mintermsCoveredbyPI.get(i)))) {
				mintermsCoveredbyPI.remove(i);
			}
		}
	}

	private void removeNonMinterms () {  // remove don't cares and non minterms from tabular table
		for (int i = 0; i < tabularTable.size(); i++) {
			if (tabularTable.arraySize(i) == 0) {
				tabularTable.remove(i);
				i--;
			}
		}
	}
/////minimization row dom, col dom, essential
	private void removeEssentialPI () {  /// remove essential PI and add them to final list
		for (int i = 0; i < tabularTable.size(); i++) {
			if (tabularTable.arraySize(i) == 1) {
				PIremaining--;
				int essentialPI = tabularTable.getTag(i, 0);
				essentialPIList.add(essentialPI);
				for (int j = 0; j < tabularTable.size(); j++) {
					int nodeIndex = j;
					boolean found = false;
					for (int arrayIndex = 0; arrayIndex < tabularTable.arraySize(nodeIndex) && !found; arrayIndex++) {
						if (tabularTable.getTag(nodeIndex, arrayIndex) == essentialPI) {
							tabularTable.remove(nodeIndex);
							found = true;
							j--;
							if (j < i) {
								i--;
							}
						}
					}
				}
			}
		}
	}

	private void removeAfterDominance () {  /// remove essential PI and add them to final list
		for (int i = 0; i < tabularTable.size(); i++) {
			if (tabularTable.arraySize(i) == 1) {
				PIremaining--;
				int essentialPI = tabularTable.getTag(i, 0);
				PIfromMinimization.add(essentialPI);
				for (int j = 0; j < tabularTable.size(); j++) {
					int nodeIndex = j;
					boolean found = false;
					for (int arrayIndex = 0; arrayIndex < tabularTable.arraySize(nodeIndex) && !found; arrayIndex++) {
						if (tabularTable.getTag(nodeIndex, arrayIndex) == essentialPI) {
							tabularTable.remove(nodeIndex);
							found = true;
							j--;
							if (j < i) {
								i--;
							}
						}
					}
				}
			}
		}
	}

	private list rowDominanceTable () {
		list RDTable = new list();
		for (int i = 0; i < tabularTable.size(); i++) {
			for (int arrayIndex = 0; arrayIndex < tabularTable.arraySize(i); arrayIndex++) {
				int PI = tabularTable.getTag(i, arrayIndex);
				if (!(RDTable.contains(PI))) {
					RDTable.add(PI);
					RDTable.addTagToEnd((int) tabularTable.get(i));
				} else {
					boolean found = false;
					for (int j = 0; j < RDTable.size() && !found; j++) {
						if ((int) RDTable.get(j) == PI) {
							RDTable.addTag((int) tabularTable.get(i), j);
							found = true;
						}
					}
				}
			}
		}
		return RDTable;
	}

	private void rowDominanceCoverage () {
		list toBeRemoved = new list();
		list RDTable = rowDominanceTable();
		int tableSize = RDTable.size();
		for (int i = 0; i < tableSize; i++) {
			for (int j = 0;j < tableSize; j++) {
				int firstArraySize = RDTable.arraySize(i);
				int secArraySize = RDTable.arraySize(j);
				if (firstArraySize < secArraySize) {
					boolean allFound = true;
					boolean minTermFound = false;
					for (int firstArr = 0; firstArr < firstArraySize && allFound; firstArr++) {
						for (int secArray =0; (secArray < secArraySize && !minTermFound); secArray++) {
							if (RDTable.getTag(i, firstArr) == RDTable.getTag(j, secArray)) {
								minTermFound = true;
							}
						}
						if (!minTermFound) {
							allFound = false;
						}
					}
					if (allFound) {
						toBeRemoved.add((int) RDTable.get(i));
					}
				}
			}
		}
		PIremaining = PIremaining - toBeRemoved.size();
		for (int i = 0; i < tabularTable.size(); i++) {
			for (int j = 0; j < toBeRemoved.size(); j++) {
				tabularTable.removeTags((int) toBeRemoved.get(j), i);
			}
		}
	}

	private void columnDominanceCoverage () {
		for (int i = 0; i < tabularTable.size() &&  tabularTable.size() != 1; i++) {
			if (tabularTable.arraySize(i) == PIremaining) {
				tabularTable.remove(i);
				i--;
			}
		}
	}
//// petrick method
	private void sortStringInNodes (LinkedList<String> rowInTable) {
		for (int i = 0; i < rowInTable.size(); i++) {
			char[] convertedChars = rowInTable.get(i).toCharArray();
			Arrays.sort(convertedChars);
	        String sorted = new String(convertedChars);
	        rowInTable.remove(i);
	        rowInTable.add(i, sorted);
		}
	}

	private void checkIdempotent (LinkedList<String> rowInTable) {
		sortStringInNodes(rowInTable);
		for (int i = 0; i < rowInTable.size(); i++) {
			for (int j = i + 1; j < rowInTable.size(); j++) {
				if (rowInTable.get(i).equals(rowInTable.get(j))) {
					rowInTable.remove(j);
					j--;
				}
			}
		}
	}

	private LinkedList<String> checkAbsorbtion(LinkedList<String> rowInTable) {
		checkIdempotent(rowInTable);
		for (int i = 0; i < rowInTable.size(); i++) {
			for (int j = 0; j < rowInTable.size(); j++) {
				int st1Length = rowInTable.get(i).length();
				int st2Length = rowInTable.get(j).length();
				if (st1Length < st2Length) {
					boolean stFound = false;
					boolean allFound = true;
					for (int firstString=0;firstString<st1Length && allFound;firstString++) {
						stFound = false;
						for (int secondString=0;secondString<st2Length && !stFound;secondString++) {
							if (rowInTable.get(i).charAt(firstString) ==
									rowInTable.get(j).charAt(secondString)) {
								stFound = true;
							}
						}
						if (!stFound) {
							allFound = false;
						}
					}
					if (allFound) {
						rowInTable.remove(j);
						j--;
						if (j < i) {
							i--;
						}
					}
				}
			}
		}
		return rowInTable;
	}

	private LinkedList<String> PetrickMethod (LinkedList<String> rowInTable) {
		return checkAbsorbtion(rowInTable);
	}

	private void convertTabularTable (LinkedList<String>[] petrickTable){
		int i, j;
		for(i=0;i<tabularTable.size;i++){
			petrickTable[i] = new LinkedList<String>();
			for(j=0;j<tabularTable.arraySize(i);j++){
				petrickTable[i].add(String.valueOf(tabularTable.getTag(i, j)));
			}
		}
	}

	private LinkedList<String>[] groupPI(){
		final int size = tabularTable.size();
		LinkedList<String>[] petrickTable = new LinkedList[size];
		for(int i=0;i<tabularTable.size;i++){
			petrickTable[i] = new LinkedList<String>();
			for(int j=0;j<tabularTable.arraySize(i);j++){
				petrickTable[i].add(String.valueOf(tabularTable.getTag(i, j)));
			}
		}
		for (int k = 1; k < petrickTable.length; k++) {
			int sizeFirstTable = petrickTable[0].size();
			int sizeSecondTable = petrickTable[k].size();
			for(int i=0;i<sizeFirstTable;i++){
				for(int j=0;j<sizeSecondTable;j++){
					String temp = petrickTable[0].get(0);
						if(!temp.contains(petrickTable[k].get(j))){
						temp+=petrickTable[k].get(j);
						petrickTable[0].add(temp);
						}else {
							petrickTable[0].add(temp);
						}
				}
				petrickTable[0].remove(0);
			}
			petrickTable[0] = PetrickMethod(petrickTable[0]);
		}
		return petrickTable;
	}

	private void minimization () {
		LinkedList<String>[] tempPetrick = null;
		removeEssentialPI();
		while (true) {
			final int size = tabularTable.size();
			rowDominanceCoverage();
			columnDominanceCoverage();
			removeAfterDominance();
			if (size == tabularTable.size()) {
				break;
			}
		}

		if (PIremaining > 1) {
			tempPetrick = groupPI();
			for (int i = 0; i < tempPetrick[0].size(); i++) {
				finalPITerms.add(Integer.valueOf(tempPetrick[0].get(i)));
			}
		}
	}

	// we print here
	private LinkedList<String> [] converToLiterals(LinkedList<Integer> convertedList){
		LinkedList<String> [] finalPI = new LinkedList[convertedList.size()];
		int i, primeImplicant = 0;
		for(i=0;i<convertedList.size();i++){
			finalPI[i] = new LinkedList<String>();
			primeImplicant = convertedList.get(i);
			while(primeImplicant / 10  != 0){
				int PI = primeImplicant%10;
				String literalPI = convertToVariables((Integer)mappedPI.get(PI-1), mappedPI.returnArray(PI-1), maxBits());
				finalPI[i].add(literalPI);
				primeImplicant = primeImplicant/10;
			}
			int PI = primeImplicant%10;
			String literalPI = convertToVariables((Integer)mappedPI.get(PI-1), mappedPI.returnArray(PI-1), maxBits());
			finalPI[i].add(literalPI);
			primeImplicant = primeImplicant/10;
		}
		return finalPI;
	}

	private void mapPI(list[] groups){
		mappedPI.clear();
		int i, j, counter=0;
		for(i=0;i<groups.length;i++){
			for(j=0;j<groups[i].size();j++){
					mappedPI.add((Integer)groups[i].get(j));
					for(int k=0;k<groups[i].arraySize(j);k++){
						mappedPI.addTag(groups[i].getTag(j, k), counter);
				}
					counter++;
			}
		}
	}

	private int maxBits () {
		if(minTerms.size() == 0){
			return 0;
		}
		int max1 = minTerms.get(0);
		for(int i =0; i <minTerms.size()- 1; i++){
			if (minTerms.get(i) < minTerms.get(i+1)) {
				max1 = minTerms.get(i+1);
			}
		}
		int max2 = 0;
		if (dcTerms.size() != 0) {
			 max2 = dcTerms.get(0);
			for(int i =0; i <dcTerms.size()- 1; i++){
				if (dcTerms.get(i) < dcTerms.get(i+1)) {
					max2 = dcTerms.get(i+1);
				}
			}
		}
		int max = max1;
		if (max2 >max1) {
			max = max2;
		}
		LinkedList<Integer> list = new LinkedList<Integer>();
		int remainder = 0;
		while (max != 0) {
			remainder = max % 2;
			list.add(0, remainder);
			max /= 2;
		}
		return list.size();
	}

	private String convertToVariables(int minTerm, int [] minTerms, int maxNumOfBits){

		char [] expression = new char[maxNumOfBits];
		String finalExp = "";
		int i;
		expression = bitConversion(minTerm, maxNumOfBits);
		for (i = 0;i<minTerms.length && minTerms[i] != 0;i++){
			expression[maxNumOfBits - (int)( Math.log(minTerms[i])/Math.log(2)) - 1] = 'X';

		}

		for(i=0;i<expression.length;i++){
			if(expression[i] == '1'){
				char temp = (char) ('A'+ i);
				finalExp = finalExp +temp ;
			}else if (expression[i] == '0'){
				char temp = (char) ('A'+ i);
				finalExp = finalExp +temp +"'";
			}
		}
		return finalExp;

	}

	private char []  bitConversion (int number, int maxNumbersBit) {
	LinkedList<Character> list = new LinkedList<Character>();
	///convert the number to the binary representation
	int remainder = 0;
	while (number != 0) {
		remainder = number % 2;
		list.add(0, (char) (remainder +'0'));
		number /= 2;
	}
	/// fill remaining bits with zeroes
	while (list.size() < maxNumbersBit) {
		list.add(0, (char) (0+'0'));
	}
	char [] expression = new char [list.size()];
	for(int i=0;i<list.size();i++){
		expression[i] = list.get(i);
	}

	return expression;
}

	public String print (list[] groups) throws IOException {
		LinkedList<String> []  essentialPILiterals = new LinkedList[essentialPIList.size()];
		LinkedList<String> []  petrickPIliterals = new LinkedList [finalPITerms.size()];
		LinkedList<String> []  afterMinimizationPI = new LinkedList [PIfromMinimization.size()];
		mapPI(groups);
		essentialPILiterals = converToLiterals(essentialPIList);
		petrickPIliterals = converToLiterals(finalPITerms);
		afterMinimizationPI = converToLiterals(PIfromMinimization);

		File file = new File("outputFile.txt");
		FileOutputStream out = null;
			out = new FileOutputStream(file);

		if (!file.exists()) {
			file.createNewFile();
		}

		if (essentialPILiterals.length != 0) {
				System.out.println("Essential prime Implicants");
				essential += "Essential prime Implicants";
			for(int i=0;i<essentialPILiterals.length;i++){
				System.out.print(essentialPILiterals[i].get(0));
				essential += essentialPILiterals[i].get(0);
				if (i < essentialPILiterals.length - 1) {
					essential += ",";
					System.out.print(", ");
				}
			}
		}
		System.out.println();
		if (petrickPIliterals.length != 0) {
			for(int i=0;i<petrickPIliterals.length;i++){
				for(int j=0;j<petrickPIliterals[i].size();j++){
					content += petrickPIliterals[i].get(j);
					System.out.print(petrickPIliterals[i].get(j));
					if((j != petrickPIliterals[i].size()-1)){
						content += " + ";
						System.out.print(" + ");
						}
				}
				if (afterMinimizationPI.length != 0) {
					content += " + ";
					System.out.print("+ ");
				}
				for(int p=0;p<afterMinimizationPI.length;p++){
						for(int k=0;k<afterMinimizationPI[p].size();k++){
							content += afterMinimizationPI[p].get(k);
							System.out.print(afterMinimizationPI[p].get(k));
						}
						if(!(p==afterMinimizationPI.length-1)){
							content += " + ";
							System.out.print(" + ");
						}
				}
				if (essentialPILiterals.length != 0) {
					content += " + ";
					System.out.print("+ ");
				}
				for(int j=0;j<essentialPILiterals.length;j++){
					System.out.print(essentialPILiterals[j].get(0));
					content += essentialPILiterals[j].get(0);
					if(!(j==essentialPILiterals.length-1)){
					content += " + ";
					System.out.print("+ ");
					}
				}
				content += '\n';
				System.out.println();
			}
		} else {
			for(int p=0;p<afterMinimizationPI.length;p++){
				for(int k=0;k<afterMinimizationPI[p].size();k++){
					content += afterMinimizationPI[p].get(k);
					System.out.print(afterMinimizationPI[p].get(k));
				}
				if(!(p==afterMinimizationPI.length-1)){
					content += " + ";
					System.out.print(" + ");
				}
			}
			if (essentialPILiterals.length != 0
					&& afterMinimizationPI.length != 0) {
				content += " + ";
				System.out.print("+ ");
			}
			for(int r=0;r<essentialPILiterals.length;r++){
				System.out.print(essentialPILiterals[r].get(0));
				content += essentialPILiterals[r].get(0);
				if(!(r==essentialPILiterals.length-1)){
				content += " + ";
				System.out.print(" + ");
				}
			}
		}
		byte[] contentInBytes = content.getBytes();


		out.write(contentInBytes);
		out.flush();
		out.close();

		return content;
	}

}