package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.TableColumnModel;

import config.MalhaTableModel;
import config.MalhaTableModelCellRenderer;
import controller.MalhaController;
import model.Celula;
import model.ConfiguracoesMalha;

public class SimulacaoView extends JFrame {

	private JPanel painelPrincipal;
	private JTable tabelaMalha;
	private JButton btnEncerrarSimulacao;
	private JButton btnPararEsperar;
	private JTextField tfVeiculosNaMalha;
	private String txtExclusaoMutua;
	private int qtdMaximaVeiculos;
	
    private MalhaController malhaController;
    private Timer updateTimer; // Timer para atualização periódica

    
	public SimulacaoView(File arquivoSelecionado) {
		super.setExtendedState(JFrame.MAXIMIZED_BOTH);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		painelPrincipal = new JPanel();

		btnEncerrarSimulacao = new JButton("Encerrar Simulação");
		btnPararEsperar = new JButton("Pausar Tráfego");

		btnEncerrarSimulacao.setPreferredSize(new Dimension(200, 50));
		btnPararEsperar.setPreferredSize(new Dimension(200, 50));

		btnEncerrarSimulacao.addActionListener((ActionEvent e) -> {
			ConfiguracoesMalha.getInstance().emExecucao = false;
			ConfiguracoesMalha.reset();
			super.dispose();
			new MalhaViariaView();
		});

		btnPararEsperar.addActionListener((ActionEvent e) -> {
			if (ConfiguracoesMalha.getInstance().isInserirNovosCarros()) {
				ConfiguracoesMalha.getInstance().setInserirNovosCarros(false);
				btnPararEsperar.setText("Continuar Tráfego");
			} else {
				ConfiguracoesMalha.getInstance().setInserirNovosCarros(true);
				btnPararEsperar.setText("Pausar Tráfego");
			}
		});

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Espaçamento horizontal de 30
		painelBotoes.add(btnPararEsperar);
		painelBotoes.add(btnEncerrarSimulacao);

		tfVeiculosNaMalha = new JTextField("Veículos na malha: " + this.getQtdMaximaVeiculos());
		tfVeiculosNaMalha.setEditable(false);
		tfVeiculosNaMalha.setHorizontalAlignment(JTextField.CENTER); // Centraliza o texto
		tfVeiculosNaMalha.setFont(new Font("Arial", Font.PLAIN, 24)); // Aumenta a fonte

		JPanel painelContagem = new JPanel(new FlowLayout());
		painelContagem.add(tfVeiculosNaMalha);

		this.loadTableModel();
		painelPrincipal.add(painelContagem, BorderLayout.NORTH);
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

		super.setContentPane(this.painelPrincipal);
		super.setVisible(true);
		iniciarSimulacao();
	}
	
	  private void iniciarSimulacao() {
	        new Thread(() -> {
	            malhaController = new MalhaController(this);
	            malhaController.iniciarSimulacao();
	        }).start();
	    }

//	private void loadTableModel() {
//	    this.tabelaMalha = new JTable();
//	    tabelaMalha.setModel(new MalhaTableModel());
//	    tabelaMalha.setRowHeight(32);
//	    tabelaMalha.setDefaultRenderer(Object.class, new MalhaTableModelCellRenderer());
//
//	    tabelaMalha.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    TableColumnModel columnModel = tabelaMalha.getColumnModel();
//	    for (int i = 0; i < columnModel.getColumnCount(); i++) {
//	        columnModel.getColumn(i).setMaxWidth(40);
//	    }
//
//	    tabelaMalha.setPreferredScrollableViewportSize(tabelaMalha.getPreferredSize());
//
//	    // Removendo o JScrollPane para evitar scroll
//	    JPanel centralPanel = new JPanel(new GridBagLayout());
//	    centralPanel.add(tabelaMalha);
//
//	    painelPrincipal.setLayout(new BorderLayout());
//	    painelPrincipal.add(centralPanel, BorderLayout.CENTER);
//
//	    tabelaMalha.revalidate();
//	}
	
	private void loadTableModel() {
	    this.tabelaMalha = new JTable();
	    tabelaMalha.setModel(new MalhaTableModel());

	    int cellSize = 32; // Definindo um tamanho de célula quadrada
	    tabelaMalha.setRowHeight(cellSize);

	    tabelaMalha.setDefaultRenderer(Object.class, new MalhaTableModelCellRenderer());

	    tabelaMalha.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    TableColumnModel columnModel = tabelaMalha.getColumnModel();
	    for (int i = 0; i < columnModel.getColumnCount(); i++) {
	        columnModel.getColumn(i).setMaxWidth(cellSize); // Definindo o mesmo valor da altura
	        columnModel.getColumn(i).setMinWidth(cellSize); // Garantindo que não haja redimensionamento
	    }

	    tabelaMalha.setPreferredScrollableViewportSize(tabelaMalha.getPreferredSize());

	    JPanel centralPanel = new JPanel(new GridBagLayout());
	    centralPanel.add(tabelaMalha);

	    painelPrincipal.setLayout(new BorderLayout());
	    painelPrincipal.add(centralPanel, BorderLayout.CENTER);

	    tabelaMalha.revalidate();
	}


	public JTable getTabelaMalha() {
		return tabelaMalha;
	}

	public JButton getBtnEncerrarSimulacao() {
		return btnEncerrarSimulacao;
	}

	public JButton getBtnPararEsperar() {
		return btnPararEsperar;
	}

	public String getTxtExclusaoMutua() {
		return txtExclusaoMutua;
	}

	public int getQtdMaximaVeiculos() {
		return qtdMaximaVeiculos;
	}

	public void adicionarAcaoEncerrarSimulacao(ActionListener actionListener) {
		btnEncerrarSimulacao.addActionListener(actionListener);
	}

	public void atualizarNumeroVeiculos(int veiculosNaMalha) {
		tfVeiculosNaMalha.setText("Veículos na malha: " + veiculosNaMalha);
	}

	public void atualizandoCarrosNaMalha(int qtdCarrosMalha) {
		tfVeiculosNaMalha.setText("Veículos na malha: " + qtdCarrosMalha);
	}

	public void atualizandoIconeDaCelula(Celula celula) {
		MalhaTableModel malhaTableModel = (MalhaTableModel) tabelaMalha.getModel();
		malhaTableModel.fireTableCellUpdated(celula.getLinhaAtual(), celula.getColunaAtual());
		malhaTableModel.fireTableDataChanged();
	}

	public void encerrarSimulacao() {
		btnEncerrarSimulacao.doClick();
	}
}
