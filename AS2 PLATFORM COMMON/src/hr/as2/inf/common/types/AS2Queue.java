package hr.as2.inf.common.types;
/**
 * Implments a FIFO queue.
 * 
 * @author: zrosko@yahoo.com
 */
public class AS2Queue extends java.util.Vector<Object> {
	private static final long serialVersionUID = 1L;

	public AS2Queue() {
		super();
	}

	public AS2Queue(int initialCapacity) {
		super(initialCapacity);
	}

	public AS2Queue(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
	}

	/**
	 * Append an object to the tail of the zQueue.
	 */
	public synchronized void append(Object o) {
		insertElementAt(o, size());
	}

	/**
	 * Append an MMQueue to the tail of the zQueue.
	 */
	public synchronized void appendQueue(AS2Queue queue2) {
		Object o = queue2.remove();
		while (o != null) {
			append(o);
			o = queue2.remove();
		}

	}

	/**
	 * Removes an object from the head of the zQueue.
	 */
	public synchronized Object remove() {
		int s = size();
		Object o = null;

		if (s > 0) {
			s--;
			o = elementAt(0);
			removeElementAt(0);
		}

		return o;
	}
}
