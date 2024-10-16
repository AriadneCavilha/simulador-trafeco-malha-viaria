package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
				locomoverNoCruzamento(proximaCelula);
			} else {
				locomoverCarro(proximaCelula);
			}
		}
        this.finalizar();
	}

	private void locomoverNoCruzamento(Celula proximaCelula) {
		List<Celula> rotaCruzamento = obterRotaCruzamento(proximaCelula);
		boolean locomoveu = false;
		while (!locomoveu) {
			List<Celula> celulasReservadas = new ArrayList<>();

			for (Celula celula : rotaCruzamento) {
				if (!celula.tentarReservar()) {
					liberarCelulas(celulasReservadas);
					try {
						// como tem momentos que 4 carros estão disputando o cruzamento, com
						// possiveis rotas longas, 400 era muito pouco, talvez seja interessante
						// calcular uma
						// nova rota
						sleep(100 + random.nextInt(1000));
					} catch (Exception e) {
						System.out.println(e);
						System.out.println(e.getMessage());
					}
					break;
				}

				celulasReservadas.add(celula);
				locomoveu = celulasReservadas.size() == rotaCruzamento.size();
			}
		}
		andarCruzamento(rotaCruzamento);
	}

	private synchronized void liberarCelulas(List<Celula> celulas) {
		for (Celula celula : celulas) {
			celula.liberar();
		}
	}

	private void andarCruzamento(List<Celula> rota) {
		for (Celula celula : rota) {
			this.locomover(celula);
			celula.liberar();
		}
	}

	/*
	 * Verificar quais são as possíveis saídas
	 */
	private List<Celula> obterRotaCruzamento(Celula proximaCelula) {
		LinkedList<Celula> rotaCruzamento = new LinkedList<Celula>();
		rotaCruzamento.add(proximaCelula);

		while (rotaCruzamento.getLast().getClassificacao().equals(ClassificacaoCelula.CRUZAMENTO)) {
			if (rotaCruzamento.size() >= 3) {
				proximaCelula = Malha.getInstance().getCelulaSaidaMaisProxima(proximaCelula);
			} else {
				proximaCelula = Malha.getInstance().getProximaCelula(proximaCelula);
			}
			rotaCruzamento.add(proximaCelula);
		}

		return rotaCruzamento;
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

	public void locomover(Celula celulaDestino) {
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
        this.malha.removerCarroDaMalha(this);
		this.interrupt();
	}

	public void printInformacoes() {
		System.out.println("Adicionado novo carro. [linha/coluna]" + this.celulaAtual.getLinhaAtual() + "/"
				+ this.celulaAtual.getColunaAtual() + ". " + this.celulaAtual.getClassificacao() + ". "
				+ this.tempoEspera + ". Total de carros: " + this.malha.getQtdCarrosCirculacao());
	}

}
