package model;

import java.util.Random;

public class Carro extends Thread {
	 private int linha;
	 private int coluna;
	 private int velocidade;
	 private String direcao;
	 private boolean ativo; 
	 private Random random;
	 
	 public Carro(int linha, int coluna, int velocidade, String direcao, boolean ativo, Random random) {
		super();
		this.linha = linha;
		this.coluna = coluna;
		this.velocidade = velocidade;
		this.direcao = direcao;
		this.ativo = ativo;
		this.random = random;
	}

	@Override
	public void run() {
		while(ativo) {
			try {
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		super.run();
	}
	 
}
