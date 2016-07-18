package osc.innovator.arbitrarygen.model;

/**
 * 
 * @author AlbieLiang
 *
 * @param <Key>
 * @param <Value>
 */
public class KeyValuePair<Key, Value> {
	private Key key;
	private Value value;

	public KeyValuePair() {
	}

	public KeyValuePair(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
}