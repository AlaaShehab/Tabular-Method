
/**
 * @author personal
 *
 */
public class Node {

	public int value;

	public int [] tags = new int[30];
	int count = 0;
	boolean minTermCase = false;

	public int getTags(int arrayIndex) {
		return tags[arrayIndex];
	}
	public int place = 0;

	public void setTags(int tags) {
		this.tags[place] = tags;
		place++;
	}
	public void removeTag(int tags) {
		boolean found = false;
		for (int i =0; i < place; i++) {
			if (found) {
				this.tags[i-1] = this.tags[i];
			}
			if(this.tags[i] == tags) {
				found = true;
			}
		}
		if (found) {
			place--;
		}


	}
	public Node next = null;

	public Node prev = null;

	public Node(Node next, Node prev) {

		this.next = next;
		this.prev = prev;
	}

	public int getValue() {
		return value;
	}

	public void setValue(final int value) {
		this.value = value;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(final Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(final Node prev) {
		this.prev = prev;
	}


}
