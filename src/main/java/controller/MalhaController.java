//package controller;
//
//import java.io.ObjectInputFilter.Config;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import model.Carro;
//import model.Celula;
//import model.ClassificacaoCelula;
//import model.ConfiguracoesMalha;
//import model.Malha;
//
//public class MalhaController extends Thread {
//	private List<Carro> carrosEmCirculacao;
//	
//	public MalhaController() {
//		this.carrosEmCirculacao = new ArrayList();
//	}
//	
//	@Override
//	public void run() {
//		super.run();
//	}
//	
//	private void inicializar() {
//		ConfiguracoesMalha.getInstance().emExecucao = true;
//		while(ConfiguracoesMalha.getInstance().emExecucao) {
//			while(ConfiguracoesMalha.getInstance().isInserirNovosCarros() && ConfiguracoesMalha.getInstance().emExecucao) {
//			  for (int linha = 0; linha < Malha.getInstance().getQtdLinhas(); linha++) {
//                    for (int coluna = 0; coluna < Malha.getInstance().getQtdColunas(); coluna++) {
//                        this.AtualizarCelula(linha,coluna);
//                    }
//                }
//			}
//			if(this.getQtdCarrosCirculacao() == 0) {
//				ConfiguracoesMalha.getInstance().emExecucao = false;
//			}
//		}
//		
//	}
//	
//	   private void AtualizarCelula(int linha, int coluna){
//	        Celula celulaAtual = Malha.getInstance().getMatrizMalha()[linha][coluna];
//	        if (!celulaAtual.getClassificacao().equals(ClassificacaoCelula.ENTRADA)) // Tem de ser entrada
//	            return;
//	        if (!celulaAtual.celulaDisponivel()) // Tem de estar disponivel
//	            return;
//	        if (this.getQtdCarrosCirculacao() == ConfiguracoesMalha.getInstance().getQtdCarrosSimulacao()) // Não pode ultrapassar o limite estabelecido
//	            return;
//	        try{
//	            Thread.sleep((long) (ConfiguracoesMalha.getInstance().getIntervaloInsercao()* 1000));
//	            adicionarNovoCarroAMalha(celulaAtual);
//	        } catch (Exception e){
//	            System.out.println(e.getMessage()+"   -   "+ Arrays.toString(e.getStackTrace()));
//	        }
//	    }
//	
//	public int getQtdCarrosCirculacao() {
//		return this.carrosEmCirculacao.size();
//	}
//	
//}
