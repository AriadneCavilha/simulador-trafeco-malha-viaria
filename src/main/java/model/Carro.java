package model;

import java.util.Random;

import constantes.ClassificacaoCelula;
import controller.MalhaController;

public class Carro extends Thread {

	private boolean finalizado;
	private int tempoEspera;
	private Celula celulaAtual;
	private Random random = new Random();
	private MalhaController malha;

	public Carro(MalhaController malhaController, Celula celulaAtual) {
		super();
        this.malha = malhaController;
		this.celulaAtual = celulaAtual;
		this.tempoEspera = (random.nextInt(10) * 100);
	}

	public Celula getCelulaAtual() {
		return celulaAtual;
	}

	@Override
	public void run() {
		while (ConfiguracoesMalha.getInstance().emExecucao && !this.finalizado) {
			Celula proximaCelula = Malha.getInstance().getProximaCelula(celulaAtual);

			if (proximaCelula == null) {
				sairDaMalha();
			} else if (proximaCelula.getClassificacao().equals(ClassificacaoCelula.CRUZAMENTO)) {

			} else {
				locomoverCarro(proximaCelula);
			}

		}
		super.run();
	}

	private void locomoverCarro(Celula proximaCelula) {
		boolean locomoveu = false;
		while (!locomoveu) {
			if (proximaCelula.tentarReservar()) {
				locomover(proximaCelula);
				locomoveu = true;
				proximaCelula.liberar();
			} else
				try {
					sleep(100 + random.nextInt(400));
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(e.getMessage());
				}
		}
	}
	

    public void locomover(Celula celulaDestino){
        this.aguardar();

        this.celulaAtual.setCarroAtual(null);
        this.malha.atualizarIconeDaCelula(celulaAtual);

        celulaDestino.setCarroAtual(this);
        this.celulaAtual = celulaDestino;
        this.malha.atualizarIconeDaCelula(celulaDestino);
    }
	
	private void sairDaMalha() {
		aguardar();
		this.finalizado = true;
	}

	public void aguardar() {
		try {
			Thread.sleep(tempoEspera);
		} catch (InterruptedException ignored) {
			// Ocorreu tudo OK! Ela foi interrompida
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void finalizar() {
		this.interrupt();
	}

	public void printInformacoes() {
		System.out.println("Adicionado novo carro. [linha/coluna]" + this.celulaAtual.getLinhaAtual() + "/"
				+ this.celulaAtual.getColunaAtual() + ". " + this.celulaAtual.getClassificacao() + ". "
				+ this.tempoEspera + ". Total de carros: " + this.malha.getQtdCarrosCirculacao());
	}

}
