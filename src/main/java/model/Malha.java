package model;

import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

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
		System.out.println("entrou");
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
					Celula celulaAtual = new Celula(coluna, linha, qtdColunas, qtdColunas, tipoCelula);
					this.matrizMalha [linha][coluna] = celulaAtual;
				}
			}
		}
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
