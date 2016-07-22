package com.albie.test.sdk;

public class IEvent {

	protected String id;
	protected int order;
	protected ICallback callback;

	public IEvent() {
		;
	}

	public IEvent(ICallback callback) {
		this.callback = callback;
	}

	public interface ICallback {
		void onCallback(Object... args);
	}
}
