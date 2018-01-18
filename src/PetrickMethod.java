//import java.util.Arrays;
//import java.util.LinkedList;
//
///**
// *
// */
//
///**
// * @author Personal
// *
// */
//public class PetrickMethod {
//	LinkedList<String> petrickTable = new LinkedList<String>();
//
//	public PetrickMethod (LinkedList<String> firstRow) {
//		petrickTable = firstRow;
//	}
//	private void sortStringInNodes () {
//		for (int i = 0; i < petrickTable.size(); i++) {
//			char[] convertedChars = petrickTable.get(i).toCharArray();
//			Arrays.sort(convertedChars);
//	        String sorted = new String(convertedChars);
//	        petrickTable.remove(i);
//	        petrickTable.add(i, sorted);
//		}
//	}
//
//	private void checkIdempotent () {
//		sortStringInNodes();
//		for (int i = 0; i < petrickTable.size(); i++) {
//			for (int j = i + 1; j < petrickTable.size(); j++) {
//				if (petrickTable.get(i).equals(petrickTable.get(j))) {
//					petrickTable.remove(j);
//					j--;
//				}
//			}
//		}
//	}
//
//	private void checkAbsorbtion() {
//		checkIdempotent();
//		for (int i = 0; i < petrickTable.size(); i++) {
//			for (int j = 0; j < petrickTable.size(); j++) {
//				int st1Length = petrickTable.get(i).length();
//				int st2Length = petrickTable.get(j).length();
//				if (st1Length < st2Length) {
//					boolean stFound = false;
//					boolean allFound = true;
//					for (int firstString=0;firstString<st1Length && allFound;firstString++) {
//						stFound = false;
//						for (int secondString=0;secondString<st2Length && !stFound;secondString++) {
//							if (petrickTable.get(i).charAt(firstString) ==
//									petrickTable.get(j).charAt(secondString)) {
//								stFound = true;
//							}
//						}
//						if (!stFound) {
//							allFound = false;
//						}
//					}
//					if (allFound) {
//						petrickTable.remove(j);
//						j--;
//						i--;
//					}
//				}
//			}
//		}
//	}
//
//	public void convertPI (list prepetrickTable){
//		int i, j;
//		for(i=0;i<prepetrickTable.size;i++){
//			petrickTable[i] = new LinkedList<String>();
//			for(j=0;j<prepetrickTable.arraySize(i);j++){
//				petrickTable[i].add(String.valueOf(prepetrickTable.getTag(i, j)));
//			}
//		}
//	}
//
//	public void groupPI(int secondRow){
//		int i, j;
//		final int size = petrickTable[0].size();
//		for(i=0;i<size;i++){
//			for(j=0;j<petrickTable[secondRow].size();j++){
//				String temp = petrickTable[0].get(0);
//					if(!temp.contains(petrickTable[secondRow].get(j))){
//					temp+=petrickTable[secondRow].get(j);
//					petrickTable[0].add(temp);
//					}else {
//						petrickTable[0].add(temp);
//					}
//			}
//			petrickTable[0].remove(0);
//		}
//	}
//}
//
//}
