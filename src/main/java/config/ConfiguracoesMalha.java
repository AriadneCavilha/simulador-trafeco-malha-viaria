package config;

import java.io.File;

public class ConfiguracoesMalha {

	private static ConfiguracoesMalha instance;
	private File malhaAtual;
	private double intervaloInsercao;
	private String mecanismoExclusao;
	private int qtdCarrosSimulacao;
	private boolean inserirNovosCarros = true;
	
    public static final String ICONS_PATH = "src/main/java/assets/";
	public boolean emExecucao = false;

	public static synchronized ConfiguracoesMalha getInstance() {
		if (instance == null)
			reset();
		return instance;
	}
	
	public static synchronized void reset() {
		instance = new ConfiguracoesMalha();
	}
	
	public static void setInstance(ConfiguracoesMalha instance) {
		ConfiguracoesMalha.instance = instance;
	}
	
	public ConfiguracoesMalha setMalhaAtual(File malhaAtual) {
		this.malhaAtual = malhaAtual;
		return instance;
	}
	
	public File getMalhaAtual() {
		return malhaAtual;
	}

	public int getQtdCarrosSimulacao() {
		return qtdCarrosSimulacao;
	}
	
	public ConfiguracoesMalha setQtdCarrosSimulacao(int qtdCarrosSimulacao) {
		this.qtdCarrosSimulacao = qtdCarrosSimulacao;
		return instance;
	}
	

	public double getIntervaloInsercao() {
		return intervaloInsercao;
	}

	public ConfiguracoesMalha setIntervaloInsercao(double intervaloInsercao) {
		this.intervaloInsercao = intervaloInsercao;
		return instance;
	}

	public void setInserirNovosCarros(boolean inserirNovosCarros) {
		this.inserirNovosCarros = inserirNovosCarros;
	}

	public boolean isInserirNovosCarros() {
		return inserirNovosCarros;
	}

	public ConfiguracoesMalha setMecanismoExclusao(String mecanismoExclusao) {
		this.mecanismoExclusao = mecanismoExclusao;
		return instance;
	}
	
	public String getMecanismoExclusao() {
		return mecanismoExclusao;
	}
}
