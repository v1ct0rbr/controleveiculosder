package br.gov.pb.der.controleveiculosder.utils;

public class MyId<T> {

	private T id;

	public MyId() {
		super();
	}

	public MyId(T id) {
		super();
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

}
