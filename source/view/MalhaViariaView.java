package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

public class MalhaViariaView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private File arquivoSelecionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MalhaViariaView frame = new MalhaViariaView();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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

		JLabel lblNewLabel_2_1 = new JLabel("Insira a quantidade máxima de veículos");
		lblNewLabel_2_1.setBounds(10, 70, 245, 14);
		contentPane.add(lblNewLabel_2_1);

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

		ButtonGroup group = new ButtonGroup();
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
				 * Criação de um filtro para permitir que apenas possa ser selecionado arquivo txt
				 * O primeiro parâmetro representa a descriçÃo do filtro
				 * O segundo é a extensão que será filtrada
				 */
				FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Arquivo de Texto(.txt)", "txt");
				fileChooser.setFileFilter(filtroTxt);
				/*
				 * File Chooser ele retorna um inteiro que indica a ação do usúário
				 * 0 -> Ação deu certo (ok) abriu o arquivo com sucesso
				 * 1-> Usuário clicou emm cancelar ou fechou a janela
				 * -1 -> Ocorreu algum erro
				 */
				int resultado = fileChooser.showOpenDialog(null);
				
				if(resultado == JFileChooser.APPROVE_OPTION) {
					//Seleciono a file que o usuário selecionou
					File arquivoSelecionado = fileChooser.getSelectedFile();
					
					if(verificaPadraoDoArquivo(arquivoSelecionado)) {
						setArquivoSelecionado(arquivoSelecionado);
						btnEscolherMalha.setVisible(false);
						btnExcluirMalhaViaria.setVisible(true);
						labelTxt.setVisible(true);
						labelTxt.setText(arquivoSelecionado.getName());
					} else {
						JOptionPane.showMessageDialog(null, "O arquivo não está no formato esperado. Por favor insira, novamente");
					}
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
                if (arquivoSelecionado != null) {
                    // Instanciar a SimulacaoView passando o nome do arquivo da malha, para posteriormente iniciar a simulação com aquela malha
                	//implementar
                    SimulacaoView simulacaoView = new SimulacaoView(arquivoSelecionado);
                    simulacaoView.setVisible(true);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma malha selecionada.");
                }
            }
        });

    }
	
	public File getArquivoSelecionado() {
		return arquivoSelecionado;
	}
	
	public void setArquivoSelecionado(File arquivoSelecionado) {
		this.arquivoSelecionado = arquivoSelecionado;
	}
	
	public boolean verificaPadraoDoArquivo(File arquivo) {
		try(BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			String linha;
			
			linha = br.readLine();
			if(linha == null || !linha.matches("^\\d+$")) {
				return false;
			}
			
			linha = br.readLine();
			if(linha == null || !linha.matches("^\\d+$")) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
	        return false; // Retornar false em caso de erro de leitura
		}
		
		return true;
	}
}
