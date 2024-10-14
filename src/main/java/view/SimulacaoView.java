package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

import model.ConfiguracoesMalha;

public class SimulacaoView extends JFrame {

	private JPanel painelPrincipal;
	private JTable tabelaMalha;
	private JButton btnEncerrarSimulacao;
	private JButton btnPararEsperar;
	private JTextField tfVeiculosNaMalha;
	private String txtExclusaoMutua;
	private int qtdMaximaVeiculos;
	private File file;
	JScrollPane scrollPane = new JScrollPane(tabelaMalha);

	public SimulacaoView(File arquivoSelecionado) {
		this.file = arquivoSelecionado;
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
			if(ConfiguracoesMalha.getInstance().isInserirNovosCarros()) {
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

		// Painel para a contagem
		JPanel painelContagem = new JPanel(new FlowLayout());
		painelContagem.add(tfVeiculosNaMalha);

		// Adiciona os componentes ao painel

		painelPrincipal.add(painelContagem, BorderLayout.NORTH);
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
		
		this.loadTableModel();
        pack();

		super.setContentPane(this.painelPrincipal);
		super.setVisible(true);
	}
	
    private void loadTableModel() {
        this.tabelaMalha = new JTable();
        tabelaMalha.setModel(new MalhaTableModel());
        tabelaMalha.setRowHeight(32);
        tabelaMalha.setDefaultRenderer(Object.class, new MalhaTableModelCellRenderer());
        
        
        tabelaMalha.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel columnModel = tabelaMalha.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setMaxWidth(40);
        }
        tabelaMalha.setPreferredScrollableViewportSize(tabelaMalha.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(tabelaMalha);
        scrollPane.setPreferredSize(tabelaMalha.getPreferredSize());

        // Adicionar o JScrollPane ao painel principal
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

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
}
