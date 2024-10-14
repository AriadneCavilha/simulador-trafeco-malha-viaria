package model;

import java.util.Random;

public class Carro extends Thread {
	
	 private boolean ativo; 
	 private int tempoEspera;
	 private Celula celulaAtual;
	 private static Malha instance;
	 private Random random = new Random();
	 
	 public Carro (int tempoEspera, Celula celulaAtual) {
		super();
		this.celulaAtual = celulaAtual;
		this.tempoEspera = (random.nextInt(10) * 100);
	}
	 
	public Celula getCelulaAtual() {
		return celulaAtual;
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
