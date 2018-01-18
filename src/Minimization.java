import java.util.LinkedList;

import eg.edu.alexu.csd.datastructure.linkedList.cs01_29.SingleLinkedList;


/**
 * @author Personal
 *
 */
public class Minimization {
	private LinkedList<Integer> mintermsResult = new LinkedList<Integer>();
	public LinkedList<Integer> finalPITerms = new LinkedList<Integer>();
	private int PIremaining = 0;

	public void tabularTable(list[] groups, int maxMinterm, LinkedList<Integer> minTerms) {

//		int numOfPI = 0;
//
//		for (int i = 0; i < groups.length; i++) {
//			numOfPI += groups[i].size();
//		}

		list tabularTable = new list();
		for (int i = 0; i <= maxMinterm; i++) {
			tabularTable.add(i);
		}
		int PInumber = 1;   ///PInumber is a number that represents the primeImplicants elly tl3t
		/// 7b2a me7taga 7aga t3ml map lel numbers di bel primeImplicants.
		for(int i = 0; i < groups.length - 1; i++) {
			for(int j = 0; j < groups[i].size(); j++) {
				SingleLinkedList primeImplicantTags = new SingleLinkedList();
				int sizeOfGroupArray = groups[i].arraySize(j);
				for(int arrayIndex = 0 ;arrayIndex<sizeOfGroupArray;arrayIndex++){
					primeImplicantTags.add(groups[i].getTag(j, arrayIndex));
				}
				mintermsResult.clear();
				int groupNumber = (int) groups[i].get(j);
				mintermsCovered(primeImplicantTags, groupNumber);
				removeDCinMintermsCovered(minTerms);
				for (int mintermsFromPI = 0; mintermsFromPI < mintermsResult.size(); mintermsFromPI++) {
					tabularTable.addTag(PInumber, mintermsResult.get(mintermsFromPI));
				}
				PInumber++;
			}
		}
		PIremaining = PInumber - 1;
		removeNonMinterms(tabularTable);
	}

	private void mintermsCovered (SingleLinkedList primeImplicantTags, int groupNumber) {
		if (primeImplicantTags.isEmpty()) {
			return;
		} else {
			if (!(mintermsResult.contains(groupNumber))) {
				mintermsResult.add(groupNumber);
			}
			for (int i = 0; i < primeImplicantTags.size(); i++) {
				int number = groupNumber + (int) primeImplicantTags.get(i);
				if (!(mintermsResult.contains(number))) {
					mintermsResult.add(number);
				}
			}
			int newGroupNumber = groupNumber + (int)primeImplicantTags.get(0);
			primeImplicantTags.remove(0);
			mintermsCovered (primeImplicantTags, newGroupNumber);
		}
	}

	private void removeDCinMintermsCovered (LinkedList<Integer> minTerms) {  // DC = don't cares
		for (int i = 0; i < mintermsResult.size(); i++) {
			if(!(minTerms.contains(mintermsResult.get(i)))) {
				mintermsResult.remove(i);
			}
		}
	}

	private void removeNonMinterms (list tabularTable) {  // remove don't cares and non minterms from tabular table
		for (int i = 0; i < tabularTable.size(); i++) {
			if (tabularTable.arraySize(i) == 0) {
				tabularTable.remove(i);
				i--;
			}
		}
	}

	public void removeEssentialPI (list tabularTable) {  /// remove essential PI and add them to final list
		for (int i = 0; i < tabularTable.size(); i++) {
			if (tabularTable.arraySize(i) == 1) {
				PIremaining--;
				int essentialPI = tabularTable.getTag(i, 0);
				finalPITerms.add(essentialPI);
				for (int j = 0; j < tabularTable.size(); j++) {
					int nodeIndex = j;
					boolean found = false;
					for (int arrayIndex = 0; arrayIndex < tabularTable.arraySize(nodeIndex) && !found; arrayIndex++) {
						if (tabularTable.getTag(nodeIndex, arrayIndex) == essentialPI) {
							tabularTable.remove(nodeIndex);
							found = true;
							j--;
						}
					}
				}
			}
		}
	}

	private list rowDominanceTable (list tabularTable) {
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

	public void rowDominanceCovergae (list tabularTable) {
		list toBeRemoved = new list();
		list RDTable = rowDominanceTable(tabularTable);
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

	public void columnDominanceCovergae (list tabularTable) {
		for (int i = 0; i < tabularTable.size(); i++) {
			if (tabularTable.arraySize(i) == PIremaining) {
				tabularTable.remove(i);
				i--;
			}
		}
	}
}
