package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import constantes.ClassificacaoCelula;
import model.Carro;
import model.Celula;
import model.ConfiguracoesMalha;
import model.Malha;
import view.SimulacaoView;

public class MalhaController {

	private List<Carro> carrosEmCirculacao;
	private SimulacaoView view;

	public MalhaController(SimulacaoView view) {
		this.carrosEmCirculacao = new ArrayList<>();
		this.view = view;
	}

	public void iniciarSimulacao() {
		ConfiguracoesMalha.getInstance().emExecucao = true;

		// Loop de controle principal da simulação
		while (ConfiguracoesMalha.getInstance().emExecucao) {
			// Loop para inserir novos carros
			while (ConfiguracoesMalha.getInstance().isInserirNovosCarros()
					&& ConfiguracoesMalha.getInstance().emExecucao) {
				for (int linha = 0; linha < Malha.getInstance().getQtdLinhas(); linha++) {
					for (int coluna = 0; coluna < Malha.getInstance().getQtdColunas(); coluna++) {
						this.AtualizarCelula(linha, coluna);
					}
				}
			}
			if (this.getQtdCarrosCirculacao() == 0) {
				ConfiguracoesMalha.getInstance().emExecucao = false;
			}
		}
	}

	private void AtualizarCelula(int linha, int coluna) {
		Celula celulaAtual = Malha.getInstance().getMatrizMalha()[linha][coluna];
		if (!celulaAtual.getClassificacao().equals(ClassificacaoCelula.ENTRADA)) // Tem de ser entrada
			return;
		if (!celulaAtual.celulaDisponivel()) // Tem de estar disponivel
			return;
		if (this.getQtdCarrosCirculacao() == ConfiguracoesMalha.getInstance().getQtdCarrosSimulacao()) // Não pode
			return;
		try {
			Thread.sleep((long) (ConfiguracoesMalha.getInstance().getIntervaloInsercao()));
			adicionarNovoCarroAMalha(celulaAtual);
		} catch (Exception e) {
			System.out.println(e.getMessage() + "   -   " + Arrays.toString(e.getStackTrace()));
		}
	}

	private void adicionarNovoCarroAMalha(Celula celulaInicial) {
		Carro carro = new Carro(this, celulaInicial);

		carrosEmCirculacao.add(carro);
		view.atualizandoCarrosNaMalha(this.getQtdCarrosCirculacao());
		view.atualizandoIconeDaCelula(celulaInicial);
		carro.printInformacoes();
		carro.start();
	}

	public void removerCarroDaMalha(Carro carro) {
		this.carrosEmCirculacao.remove(carro);
		Celula celula = carro.getCelulaAtual();
		celula.setCarroAtual(null);
		view.atualizandoCarrosNaMalha(this.getQtdCarrosCirculacao());
		view.atualizandoIconeDaCelula(celula);
	}

	public void encerrarSimulacao() {
		view.encerrarSimulacao();
	}

	public int getQtdCarrosCirculacao() {
		return this.carrosEmCirculacao.size();
	}

	public void atualizarIconeDaCelula(Celula celulaAtual) {
		view.atualizandoIconeDaCelula(celulaAtual);
	}
}
