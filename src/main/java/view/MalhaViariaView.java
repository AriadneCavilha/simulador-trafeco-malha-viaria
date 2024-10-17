package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.ConfiguracoesMalha;
import model.Malha;

public class MalhaViariaView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private File arquivoSelecionado;
	private int qtdMaxVeiculos;

	/**
	 * Create the frame.
	 */
	public MalhaViariaView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 560, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Sistema de Malha Viária\r\n");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblNewLabel_1.setBounds(121, 11, 297, 31);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Insira o intervalo de inserção");
		lblNewLabel_2.setBounds(10, 137, 200, 14);
		contentPane.add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(10, 95, 189, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel qtdMaximaVeiculos = new JLabel("Insira a quantidade máxima de veículos");
		qtdMaximaVeiculos.setBounds(10, 70, 245, 14);
		contentPane.add(qtdMaximaVeiculos);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 162, 189, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton = new JButton("Iniciar simulação");
		btnNewButton.setBounds(196, 218, 153, 35);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel("Selecione o mecanismo de");
		lblNewLabel_3.setBounds(302, 137, 163, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("exclusão mútua");
		lblNewLabel_4.setBounds(302, 151, 111, 14);
		contentPane.add(lblNewLabel_4);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Semáforos");
		rdbtnNewRadioButton.setBounds(304, 172, 91, 23);
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnMonitores = new JRadioButton("Monitores");
		rdbtnMonitores.setBounds(420, 172, 91, 23);
		contentPane.add(rdbtnMonitores);

		final ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnMonitores);

		final JButton btnEscolherMalha = new JButton("Escolha a malha");
		btnEscolherMalha.setBounds(302, 70, 129, 34);
		contentPane.add(btnEscolherMalha);

		final JLabel labelTxt = new JLabel("");
		labelTxt.setBounds(302, 80, 129, 14);
		contentPane.add(labelTxt);
		labelTxt.setVisible(false);

		final JButton btnExcluirMalhaViaria = new JButton("X");
		btnExcluirMalhaViaria.setBounds(441, 76, 44, 23);
		contentPane.add(btnExcluirMalhaViaria);
		btnExcluirMalhaViaria.setVisible(false);

		btnEscolherMalha.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				/*
				 * Criação de um filtro para permitir que apenas possa ser selecionado arquivo
				 * txt O primeiro parâmetro representa a descriçÃo do filtro O segundo é a
				 * extensão que será filtrada
				 */

				// Filtro para permitir apenas arquivos .txt
				FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Arquivo de Texto(.txt)", "txt");
				fileChooser.setFileFilter(filtroTxt);

				/*
				 * File Chooser ele retorna um inteiro que indica a ação do usúário 0 -> Ação
				 * deu certo (ok) abriu o arquivo com sucesso 1-> Usuário clicou emm cancelar ou
				 * fechou a janela -1 -> Ocorreu algum erro
				 */

				int resultado = fileChooser.showOpenDialog(null);

				if (resultado == JFileChooser.APPROVE_OPTION) {
					// Seleciona o arquivo que o usuário escolheu
					File arquivoSelecionado = fileChooser.getSelectedFile();

					// Mensagem de depuração
					System.out.println("Arquivo selecionado: " + arquivoSelecionado.getAbsolutePath());

					// Verifica se o arquivo está vazio
					if (arquivoSelecionado.length() == 0) {
						JOptionPane.showMessageDialog(null,
								"O arquivo está vazio. Por favor, insira um arquivo válido.");
						return;
					}

					// Verifica se o arquivo está no formato esperado
					if (verificaPadraoDoArquivo(arquivoSelecionado)) {
						setArquivoSelecionado(arquivoSelecionado);
						btnEscolherMalha.setVisible(false);
						btnExcluirMalhaViaria.setVisible(true);
						labelTxt.setVisible(true);
						labelTxt.setText(arquivoSelecionado.getName());
					} else {
						JOptionPane.showMessageDialog(null,
								"O arquivo não está no formato esperado. Por favor insira, novamente");
					}
				} else {
					System.out.println("Operação cancelada pelo usuário.");
				}
			}

		});

		btnExcluirMalhaViaria.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setArquivoSelecionado(null);
				JOptionPane.showMessageDialog(null, "Arquivo excluído com sucesso");
				labelTxt.setVisible(false);
				btnEscolherMalha.setVisible(true);
			}
		});

		// ActionListener do botão "Iniciar Simulação" , botão teste
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qtdMaxVeiculosText = textField_1.getText().trim();

				if (qtdMaxVeiculosText.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, insira a quantidade máxima de veículos.");
					return; 
				}
				if(group.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Por favor, selecione o mecanismo de exclusão mútua.");
					return; 
				}
				String txtExclusaoMutua = getBotaoSelecionado(group);
				try {
					int qtdMaxVeiculos = Integer.parseInt(qtdMaxVeiculosText);
					
					if (qtdMaxVeiculos <= 0) {
						JOptionPane.showMessageDialog(null,
								"A quantidade máxima de veículos deve ser um número positivo.");
						return; 
					}
					setQtdMaxVeiculos(qtdMaxVeiculos);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							"Por favor, insira um número válido para a quantidade máxima de veículos.");
					return; 
				}
				if (arquivoSelecionado != null) {
					ConfiguracoesMalha.reset();
					ConfiguracoesMalha.getInstance()
									  .setMalhaAtual(arquivoSelecionado)
									  .setMecanismoExclusao(txtExclusaoMutua)
									  .setQtdCarrosSimulacao(qtdMaxVeiculos)
									  .setIntervaloInsercao(Double.parseDouble(textField_1.getText()));
					Malha.reset();
					new SimulacaoView(arquivoSelecionado);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Nenhuma malha selecionada.");
				}
			}
		});
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
        super.setVisible(true);

	}
	
	  public String getBotaoSelecionado(ButtonGroup buttonGroup) {
	        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();

	            if (button.isSelected()) {
	                return button.getText();
	            }
	        }

	        return null;
	    }

	public boolean verificaPadraoDoArquivo(File arquivo) {
		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String linha;

			// Verifica as duas primeiras linhas
			for (int i = 0; i < 2; i++) {
				linha = br.readLine();
				if (linha == null || !linha.trim().matches("^\\d+$")) {
					return false; // Linha deve ser um número inteiro
				}
			}

			// Verifica as linhas restantes
			while ((linha = br.readLine()) != null) {
				if (!linha.trim().matches("^(\\d+\\s+)*\\d+$")) {
					return false; // Cada linha deve conter apenas números e espaços
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false; // Retorna false em caso de erro de leitura
		}

		return true;
	}

	public File getArquivoSelecionado() {
		return arquivoSelecionado;
	}

	public void setArquivoSelecionado(File arquivoSelecionado) {
		this.arquivoSelecionado = arquivoSelecionado;
	}
	
	public int getQtdMaxVeiculos() {
		return qtdMaxVeiculos;
	}
	
	public void setQtdMaxVeiculos(int qtdMaxVeiculos) {
		this.qtdMaxVeiculos = qtdMaxVeiculos;
	}
}
