package model;

public class Malha {

    private Celula matrizMalha[][];
    private int qtdLinhas;
    private int qtdColunas;
    
    private Malha() {
    	//implementar
    }

    public Celula[][] getMatrizMalha() {
        return matrizMalha;
    }

    public int getQtdLinhas() {
        return qtdLinhas;
    }

    public int getQtdColunas() {
        return qtdColunas;
    }
    
}
