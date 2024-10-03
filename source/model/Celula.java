package model;

public class Celula {
	
	private int tipo; // Representa a direção da estrada, é correspondente ao número na matriz
	private String classificacao; // Representa se a estrada é uma ENTRADA/SAIDA/CRUZAMENTO/VAZIO
	private int linha;
	private int coluna;
	private boolean celulaDisponivel = true;
	
	public Celula(int linha, int coluna, int tipo) {
		this.linha = linha;
		this.coluna = coluna;
		this.tipo = tipo;
		this.setClassificacao();
	}

	public int getTipo() {
		return tipo;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setClassificacao() {//implementar
	}

	//implementar método para ver se está vazia ou não a célula

}
