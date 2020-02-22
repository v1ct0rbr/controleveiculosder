package br.gov.pb.der.controleveiculosder.filtros;

public abstract class AbstractFilter {

	private boolean paginar = true;

	public static final int SORTBYMETHOD_ASC = 1;
	public static final int SORTBYMETHOD_DESC = 2;

	protected int sortBy;
	protected int sortByMethod;

	public boolean isPaginar() {
		return paginar;
	}

	public void setPaginar(boolean paginar) {
		this.paginar = paginar;
	}

	public int getSortBy() {
		return sortBy;
	}

	public void setSortBy(int sortBy) {
		this.sortBy = sortBy;
	}

	public int getSortByMethod() {
		return sortByMethod;
	}

	public void setSortByMethod(int sortByMethod) {
		this.sortByMethod = sortByMethod;
	}

}
