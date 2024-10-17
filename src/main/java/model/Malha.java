package model;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import config.ConfiguracoesMalha;
import constantes.ClassificacaoCelula;
import constantes.TipoCelula;


public class Malha {
	private static Malha instance;
	private int qtdLinhas;
	private int qtdColunas;
	private Scanner matrizScanner;
	private Celula matrizMalha[][];

    private Random random = new Random();
     
    public Malha() {
    	this.leituraArquivo(ConfiguracoesMalha.getInstance().getMalhaAtual());
    	this.inicializarMalha();
    }
    
	public synchronized static Malha getInstance() {
		if (instance == null) {
			reset();
		}
		return instance;
	}

	public synchronized static void reset() {
		instance = new Malha();
	}
	
	private void leituraArquivo(File arquivoMalha) {
		try {
			matrizScanner = new Scanner(arquivoMalha);
			this.qtdLinhas = matrizScanner.nextInt();
			this.qtdColunas = matrizScanner.nextInt();
			this.matrizMalha = new Celula[qtdLinhas][qtdColunas];
		} catch (Exception e) {
            System.out.println(e.getMessage()+" - "+ Arrays.toString(e.getStackTrace()));
		}
	}
	
	private void inicializarMalha() {
		while(matrizScanner.hasNextInt()) {
			for(int linha = 0; linha < this.qtdLinhas; linha++) {
				for(int coluna = 0 ; coluna < this.qtdColunas; coluna++) {
					int tipoCelula = matrizScanner.nextInt();
					Celula celulaAtual = new Celula(coluna, linha, qtdLinhas, qtdColunas, tipoCelula);
					this.matrizMalha [linha][coluna] = celulaAtual;
				}
			}
		}
	}
	

	public Celula getProximaCelula(Celula celulaAtual) {
		if (celulaAtual.getClassificacao().equals(ClassificacaoCelula.SAIDA))
			return null;
		switch (celulaAtual.getTipoCelula()) {

		case TipoCelula.ESTRADA_CIMA:
		case TipoCelula.CRUZAMENTO_CIMA:
			return getCelulaACima(celulaAtual);

		case TipoCelula.ESTRADA_DIREITA:
		case TipoCelula.CRUZAMENTO_DIREITA:
			return getCelulaADireita(celulaAtual);

		case TipoCelula.ESTRADA_BAIXO:
		case TipoCelula.CRUZAMENTO_BAIXO:
			return getCelulaABaixo(celulaAtual);

		case TipoCelula.ESTRADA_ESQUERDA:
		case TipoCelula.CRUZAMENTO_ESQUERDA:
			return getCelulaAEsquerda(celulaAtual);

		case TipoCelula.CRUZAMENTO_CIMA_E_DIREITA:
			if (random.nextInt(2) == 0)
				return getCelulaACima(celulaAtual);
			else
				return getCelulaADireita(celulaAtual);

		case TipoCelula.CRUZAMENTO_CIMA_E_ESQUERDA:
			if (random.nextInt(2) == 0)
				return getCelulaACima(celulaAtual);
			else
				return getCelulaAEsquerda(celulaAtual);

		case TipoCelula.CRUZAMENTO_DIREITA_E_BAIXO:
			if (random.nextInt(2) == 0)
				return getCelulaADireita(celulaAtual);
			else
				return getCelulaABaixo(celulaAtual);

		case TipoCelula.CRUZAMENTO_BAIXO_E_ESQUERDA:
			if (random.nextInt(2) == 0)
				return getCelulaABaixo(celulaAtual);
			else
				return getCelulaAEsquerda(celulaAtual);
		default:
			return null;
		}
	}
	

    public Celula getCelulaSaidaMaisProxima(Celula celula) {
        switch (celula.getTipoCelula()){
            case TipoCelula.CRUZAMENTO_CIMA_E_DIREITA:
            case TipoCelula.CRUZAMENTO_DIREITA:
            case TipoCelula.ESTRADA_CIMA:
                return getCelulaADireita(celula);

            case TipoCelula.CRUZAMENTO_CIMA_E_ESQUERDA:
            case TipoCelula.CRUZAMENTO_CIMA:
            case TipoCelula.ESTRADA_DIREITA:
                return getCelulaACima(celula);

            case TipoCelula.CRUZAMENTO_DIREITA_E_BAIXO:
            case TipoCelula.CRUZAMENTO_BAIXO:
            case TipoCelula.ESTRADA_BAIXO:
                return getCelulaABaixo(celula);

            case TipoCelula.CRUZAMENTO_BAIXO_E_ESQUERDA:
            case TipoCelula.CRUZAMENTO_ESQUERDA:
            case TipoCelula.ESTRADA_ESQUERDA:
                return getCelulaAEsquerda(celula);
            default:
                return null;
        }
    }
	
	private Celula getCelulaACima(Celula celula){
        return matrizMalha[celula.getLinhaAtual()-1][celula.getColunaAtual()];
    }
	
    private Celula getCelulaADireita(Celula celula){
        return matrizMalha[celula.getLinhaAtual()][celula.getColunaAtual()+1];
    }
    
    private Celula getCelulaABaixo(Celula celula){
        return matrizMalha[celula.getLinhaAtual()+1][celula.getColunaAtual()];
    }
    
    private Celula getCelulaAEsquerda(Celula celula){
        return matrizMalha[celula.getLinhaAtual()][celula.getColunaAtual()-1];
    }

	public int getQtdLinhas() {
		return qtdLinhas;
	}
	
	public int getQtdColunas() {
		return qtdColunas;
	}
	
	public Celula[][] getMatrizMalha() {
		return matrizMalha;
	}
	
}
