package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Carro;
import model.Celula;
import model.ClassificacaoCelula;
import model.ConfiguracoesMalha;
import model.Malha;
import observer.Observer;

public class MalhaController extends Thread {

    private List<Carro> carrosEmCirculacao;
    private List<Observer> observers;

    public MalhaController() {
        this.carrosEmCirculacao = new ArrayList<>();
        this.observers= new ArrayList<>();
    }

    @Override
    public void run() {
        inicializar();
    }

    private void inicializar() {
        ConfiguracoesMalha.getInstance().emExecucao = true;
        while (ConfiguracoesMalha.getInstance().emExecucao){
            while (ConfiguracoesMalha.getInstance().isInserirNovosCarros() && ConfiguracoesMalha.getInstance().emExecucao){
                for (int linha = 0; linha < Malha.getInstance().getQtdLinhas(); linha++) {
                    for (int coluna = 0; coluna < Malha.getInstance().getQtdColunas(); coluna++) {
                        this.AtualizarCelula(linha,coluna);
                    }
                }
            }
            if (this.getQtdCarrosCirculacao() == 0)
            	ConfiguracoesMalha.getInstance().emExecucao = false;
        }
        encerrarSimulacao();
    }

    private void AtualizarCelula(int linha, int coluna){
        Celula celulaAtual = Malha.getInstance().getMatrizMalha()[linha][coluna];
        if (!celulaAtual.getClassificacao().equals(ClassificacaoCelula.ENTRADA)) // Tem de ser entrada
            return;
        if (!celulaAtual.celulaDisponivel()) // Tem de estar disponivel
            return;
        if (this.getQtdCarrosCirculacao() == ConfiguracoesMalha.getInstance().getQtdCarrosSimulacao()) // Não pode ultrapassar o limite estabelecido
            return;
        try{
            Thread.sleep((long) (ConfiguracoesMalha.getInstance().getIntervaloInsercao()* 1000));
            adicionarNovoCarroAMalha(celulaAtual);
        } catch (Exception e){
            System.out.println(e.getMessage()+"   -   "+ Arrays.toString(e.getStackTrace()));
        }
    }

    private void adicionarNovoCarroAMalha(Celula celulaInicial){
        Carro carro = new Carro(this, celulaInicial);

        carrosEmCirculacao.add(carro);
        this.atualizarQuantidadeDeCarrosDaMalha();
        this.atualizarIconeDaCelula(celulaInicial);
        carro.printInformacoes();
        carro.start();
    }

    public void removerCarroDaMalha(Carro carro){
        this.carrosEmCirculacao.remove(carro);
        Celula celula = carro.getCelulaAtual();
        celula.setCarroAtual(null);
        this.atualizarIconeDaCelula(celula);
        this.atualizarQuantidadeDeCarrosDaMalha();
    }

    public void anexarObserver(Observer observer){
        this.observers.add(observer);
    }

    public int getQtdCarrosCirculacao(){
        return this.carrosEmCirculacao.size();
    }

    public void atualizarIconeDaCelula(Celula celula) {
        for (Observer obs: observers){
            obs.atualizandoIconeDaCelula(celula);
        }
    }

    public void atualizarQuantidadeDeCarrosDaMalha(){
        for (Observer obs: observers){
            obs.atualizandoCarrosNaMalha(this.getQtdCarrosCirculacao());
        }
    }

    public void encerrarSimulacao(){
        for (Observer obs: observers){
            obs.encerrarSimulacao();
        }
    }

}
