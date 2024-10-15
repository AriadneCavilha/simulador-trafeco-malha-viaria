package model;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import constantes.ClassificacaoCelula;
import constantes.TipoCelula;

public class Celula {

	private int colunaAtual;
	private int linhaAtual;
	private int qtdLinhas;
	private int qtdColunas;
	private String classificacao; // ENTRADA/SAÍDA/VAZIO/ESTRADA/CRUZAMENTO
	private int ultimaLinhaDaMalha;
	private int ultimaColunaDaMalha;
	private int tipoCelula;
	private Carro carroAtual;
	private Boolean celulaDisponivel;
	private Lock lock;
	private Semaphore semaforo;

	public Celula(int colunaAtual, int linhaAtual, int qtdLinhas, int qtdColunas, int tipoCelula) {
		this.colunaAtual = colunaAtual;
		this.linhaAtual = linhaAtual;
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		this.ultimaLinhaDaMalha = qtdLinhas - 1;
		this.ultimaColunaDaMalha = qtdColunas - 1;
		this.tipoCelula = tipoCelula;
		this.semaforo = new Semaphore(1);
		this.lock = new ReentrantLock();
		this.setClassificacao();
	}

	private void setClassificacao() {

		switch (this.tipoCelula) {
		case TipoCelula.VAZIO:
			this.classificacao = ClassificacaoCelula.VAZIO;
			break;
		case TipoCelula.ESTRADA_BAIXO:
			if (this.linhaAtual == ultimaLinhaDaMalha)
				this.classificacao = ClassificacaoCelula.SAIDA;
			else if (this.linhaAtual == 0)
				this.classificacao = ClassificacaoCelula.ENTRADA;
			else
				this.classificacao = ClassificacaoCelula.ESTRADA;
			break;
		case TipoCelula.ESTRADA_CIMA:
			if (this.linhaAtual == ultimaLinhaDaMalha)
				this.classificacao = ClassificacaoCelula.ENTRADA;
			else if (this.linhaAtual == 0)
				this.classificacao = ClassificacaoCelula.SAIDA;
			else
				this.classificacao = ClassificacaoCelula.ESTRADA;
			break;
		case TipoCelula.ESTRADA_DIREITA:
			if (this.colunaAtual == ultimaColunaDaMalha)
				this.classificacao = ClassificacaoCelula.SAIDA;
			else if (this.colunaAtual == 0)
				this.classificacao = ClassificacaoCelula.ENTRADA;
			else
				this.classificacao = ClassificacaoCelula.ESTRADA;
			break;
		case TipoCelula.ESTRADA_ESQUERDA:
			if (this.colunaAtual == ultimaColunaDaMalha)
				this.classificacao = ClassificacaoCelula.ENTRADA;
			else if (this.colunaAtual == 0)
				this.classificacao = ClassificacaoCelula.SAIDA;
			else
				this.classificacao = ClassificacaoCelula.ESTRADA;
			break;
		default:
			this.classificacao = ClassificacaoCelula.CRUZAMENTO;
		}
	}

	public String getIcon() {
		if (this.carroAtual != null) {
			return ConfiguracoesMalha.ICONS_PATH + "carro.png";
		} else {
			return ConfiguracoesMalha.ICONS_PATH + "icon" + this.tipoCelula + ".png";
		}
	}

	public boolean tentarReservar() {
		if (this.carroAtual != null) {
			return false;
		}

		if (ConfiguracoesMalha.getInstance().getMecanismoExclusao() == "Semáforos") {
			return tentarReservarSemaforo();
		} else {
			return tentarReservarMonitor();
		}
	}

	public void liberar() {
		if (ConfiguracoesMalha.getInstance().getMecanismoExclusao() == "Semáforo")
			this.liberarSemaforo();
		else
			this.liberarMonitor();
	}

	public void liberarSemaforo() {
		try {
			this.semaforo.release();
		} catch (Exception e) {
		}
	}

	public void liberarMonitor() {
		try {
			this.lock.unlock();
		} catch (Exception e) {
		}
	}

	private boolean tentarReservarMonitor() {
		try {
			return this.lock.tryLock(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}

	private boolean tentarReservarSemaforo() {
		try {
			return this.semaforo.tryAcquire(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println(e.getStackTrace());
			return false;
		}
	}

	public void setCarroAtual(Carro carroAtual) {
		this.carroAtual = carroAtual;
	}

	public boolean celulaDisponivel() {
		return this.carroAtual == null;
	}

	public int getTipoCelula() {
		return tipoCelula;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public Carro getCarroAtual() {
		return carroAtual;
	}

	public Boolean getCelulaDisponivel() {
		return celulaDisponivel;
	}

	public int getColunaAtual() {
		return colunaAtual;
	}

	public int getLinhaAtual() {
		return linhaAtual;
	}

	public int getQtdColunas() {
		return qtdColunas;
	}

	public int getQtdLinhas() {
		return qtdLinhas;
	}
}
