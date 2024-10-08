package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

public class SimulacaoView extends JFrame { //validar se está ok

	private JPanel painelPrincipal;
	private JTable tabelaMalha;
	private JButton btnEncerrarSimulacao;
	private JButton btnPararEsperar;
	private JTextField tfVeiculosNaMalha;
	private File file;
	

	public SimulacaoView(File arquivoSelecionado) {
		this.file = arquivoSelecionado;
		super.setExtendedState(JFrame.MAXIMIZED_BOTH); // Janela maximizada
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Configuração do painel principal
		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		// Configuração dos botões
		btnEncerrarSimulacao = new JButton("Encerrar Simulação");
		btnPararEsperar = new JButton("Parar / Esperar");

		// Aumentar o tamanho dos botões
		btnEncerrarSimulacao.setPreferredSize(new Dimension(200, 50));
		btnPararEsperar.setPreferredSize(new Dimension(200, 50));

		JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Espaçamento horizontal de 30
		painelBotoes.add(btnPararEsperar);
		painelBotoes.add(btnEncerrarSimulacao);

		// Tabela (grid) que exibirá a malha
		//tem que validar como mostrar essa tabela
		tabelaMalha = new JTable();

		tfVeiculosNaMalha = new JTextField("Veículos na malha: 0");
		tfVeiculosNaMalha.setEditable(false);
		tfVeiculosNaMalha.setHorizontalAlignment(JTextField.CENTER); // Centraliza o texto
		tfVeiculosNaMalha.setFont(new Font("Arial", Font.PLAIN, 24)); // Aumenta a fonte

		// Painel para a contagem
		JPanel painelContagem = new JPanel(new FlowLayout());
		painelContagem.add(tfVeiculosNaMalha);

		// Adiciona os componentes ao painel
		painelPrincipal.add(new JScrollPane(tabelaMalha), BorderLayout.CENTER);
		painelPrincipal.add(painelContagem, BorderLayout.NORTH);
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

		MatrizPanel matrizPanel = new MatrizPanel(leituraMatriz());
		painelPrincipal.add(matrizPanel);
		painelPrincipal.revalidate();
		painelPrincipal.repaint();
		
		super.setContentPane(this.painelPrincipal);
		super.setVisible(true);
	}

	private int[][] leituraMatriz() {//implementar se necessário
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String linha;
			linha = br.readLine();
			int linhas = Integer.parseInt(linha.trim());
			
			linha = br.readLine();
			int colunas = Integer.parseInt(linha.trim());
			
			int matriz[][] = new int[linhas][colunas];
			
			for(int i=0; i < linhas; i++) {
				linha = br.readLine();
				String[] valores = linha.split("\\s+");
				
				for(int j=0; j < colunas; j++) {
					matriz[i][j] = Integer.parseInt(valores[j].trim());
				}
			}
			
			return matriz;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	public void adicionarAcaoEncerrarSimulacao(ActionListener actionListener) {
		btnEncerrarSimulacao.addActionListener(actionListener);
	}

	public void adicionarAcaoPararEsperar(ActionListener actionListener) {
		btnPararEsperar.addActionListener(actionListener);
	}

	public void atualizarNumeroVeiculos(int veiculosNaMalha) {
		tfVeiculosNaMalha.setText("Veículos na malha: " + veiculosNaMalha);
	}
}
