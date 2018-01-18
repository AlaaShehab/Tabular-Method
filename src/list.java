/**
 * @author Personal
 *
 */
public class list {

	Node head = new Node(null, null);
	Node tail = new Node(null, head);
	public int size;

	public void add (int element){
		Node newNode = new Node(null, null);
		newNode.setValue(element);
		tail.getPrev().setNext(newNode);
		newNode.setPrev(tail.getPrev());

		tail.setPrev(newNode);
		newNode.setNext(tail);
		size++;

	}
	public boolean contains(final int o) {
		Node newNode = new Node(null, null);
		newNode = head.getNext();
		for (int i = 0; i < size; i++) {
			if (newNode.getValue() == (o)) {
				return true;
			}
			newNode = newNode.getNext();
		}
		return false;
	}

	public int size (){
		return size;
	}


	public void add(final int index,
			final int element) {
		final int q = 0, w = -1, e = 1;
		if (index < q || index > size+1) {
			throw null;
		}

		Node newNode = new Node(null, null);
		newNode.setValue(element);

		if(index == size +1){
			tail.getPrev().setNext(newNode);
			newNode.setPrev(tail.getPrev());
			newNode.setNext(tail);
			tail.setPrev(newNode);
			size++;
			return ;
		}


		Node temp = head;

		if (index == q) {
			if (head.getNext() == null &&
					tail.getPrev() == head) {
				newNode.setNext(tail);
				newNode.setPrev(head);
				head.setNext(newNode);
				tail.setPrev(newNode);
				tail.setNext(null);
				size++;

			} else {
				head.getNext().setPrev(newNode);
				newNode.setNext(head.getNext());
				newNode.setPrev(head);
				head.setNext(newNode);
				size++;

			}
		} else {
			for (int i = w; i < index - e; i++) {
				temp = temp.getNext();
			}
			if (temp.getNext() == null) {
				tail.setPrev(newNode);
				temp.setNext(newNode);
				newNode.setPrev(temp);
				newNode.setNext(tail);
				size++;
			} else {
				temp.getNext().setPrev(newNode);
				newNode.setNext(temp.getNext());
				newNode.setPrev(temp);
				temp.setNext(newNode);
				size++;
			}
		}

	}

	public void print() {
		Node i = head;
		while (i.getNext() != tail
				&& i.getNext() != null) {
			System.out.println
			(i.getNext().getValue());
			i = i.getNext();
		}
	}

	public int getTag(int nodeIndex, int arrayIndex){
		Node newNode = new Node(null,null);
		newNode = determineMinterm(nodeIndex);
		return newNode.getTags(arrayIndex);

	}
	public void addTag(int tag, int nodeIndex){
		Node newNode = new Node(null,null);
		newNode = determineMinterm(nodeIndex);
		newNode.setTags(tag);
	}
	public void removeTags(int tag, int nodeIndex){
		Node newNode = new Node(null,null);
		newNode = determineMinterm(nodeIndex);
		newNode.removeTag(tag);
	}
	public void addTagToEnd(int tag){
		Node newNode = new Node(null,null);
		newNode = tail.getPrev();
		newNode.setTags(tag);
	}
	public int arraySize(int nodeIndex){
		Node newNode = new Node(null,null);
		newNode = determineMinterm(nodeIndex);
		return newNode.place;

	}
	public void clear() {
		Node node = head.getNext();
		Node temp;
		while (size != 0) {
			temp = node.getNext();
			node.setNext(null);
			node = temp;
			size--;
		}
		head.setNext(null);
		tail.setNext(null);
	}
	public void setCase(int minTerm, boolean minTermCase){
		Node place = new Node(null,null);
		place = determineMinterm(minTerm);
		place.minTermCase = minTermCase;
	}

	public int [] returnArray (int nodeIndex){
		Node place = new Node(null,null);
		place = determineMinterm(nodeIndex);
		return place.tags;
	}

	public boolean getminTermCase(int minTerm){
		Node place = new Node(null,null);
		place = determineMinterm(minTerm);
		return place.minTermCase;
	}
	public Object get(final int index) {
		final int e = 0, w = 1;
		int size = size();
		if (index < e || index >= size ) {
			throw new RuntimeException();
		}
		if(index == size - 1){
			return tail.getPrev().getValue();
		}
		Node temp = head;
		for (int i = 0; i < index + w; i++) {
			temp = temp.getNext();
		}
		return temp.getValue();

	}

	public void set(final int index,
			final int element) {
		final int e = 0;
		if (index < e || index >= size()) {
			throw new RuntimeException();
		}
		if (isEmpty()) {
			throw new RuntimeException();

		}
		Node temp = head;
		for (int i = e; i <= index; i++) {
			temp = temp.getNext();
		}
		temp.setValue(element);

	}

	public boolean isEmpty() {
		final int e = 0;
		if (size() == e || (head.getNext()
				== null && tail.getPrev() == head)) {
			return true;
		}

		return false;

	}

	public Node determineMinterm (int nodeIndex){

		Node place = new Node(null,null);
		place = head.getNext();
		int counter =0;
		while (counter != nodeIndex){
			place = place.getNext();
			counter++;

		}
		return place;

	}

	public void remove(final int index) {
		final int e = 0, w = 1;

		if (index < e || index >= size
				|| isEmpty()) {
			throw new RuntimeException();
		}
		if(index == size - 1){
			Node temp = tail.getPrev();
			temp.getPrev().setNext(tail);
			tail.setPrev(temp.getPrev());
			temp.setNext(null);
			temp.setPrev(null);
			size--;
			return ;
		}
		Node temp = head;
		for (int i = e; i < index + w; i++) {
			temp = temp.getNext();
		}
		temp.getNext().setPrev(temp.getPrev());
		temp.getPrev().setNext(temp.getNext());
		temp.setNext(null);
		temp.setPrev(null);
		size--;
		return ;

	}

}