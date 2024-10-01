package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

public class MalhaViariaView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
    private File selectedFile;


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
		setBounds(100, 100, 533, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Adicione o txt da malha");
		lblNewLabel.setBounds(332, 60, 179, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Sistema de Malha Viária\r\n");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 27));
		lblNewLabel_1.setBounds(80, 11, 297, 31);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Insira o intervalo de inserção");
		lblNewLabel_2.setBounds(10, 137, 200, 14);
		contentPane.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 95, 65, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("Insira a quantidade máxima de veículos");
		lblNewLabel_2_1.setBounds(10, 70, 245, 14);
		contentPane.add(lblNewLabel_2_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(10, 162, 65, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Iniciar simulação");
		btnNewButton.setBounds(10, 232, 133, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Encerrar inserção");
		btnNewButton_1.setBounds(165, 232, 145, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Encerrar simulação");
		btnNewButton_2.setBounds(336, 232, 151, 23);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel_3 = new JLabel("Selecione o mecanismo de");
		lblNewLabel_3.setBounds(332, 135, 200, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("exclusão mútua");
		lblNewLabel_4.setBounds(332, 148, 111, 14);
		contentPane.add(lblNewLabel_4);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Semáforos");
		rdbtnNewRadioButton.setBounds(332, 172, 133, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnMonitores = new JRadioButton("Monitores");
		rdbtnMonitores.setBounds(332, 198, 133, 23);
		contentPane.add(rdbtnMonitores);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnMonitores);
		
		final JButton btnAdicionarTxt = new JButton("Adicionar txt");
		btnAdicionarTxt.setBounds(332, 94, 117, 23);
		contentPane.add(btnAdicionarTxt);
		
		final JLabel labelTxt = new JLabel("");
		labelTxt.setBounds(332, 94, 117, 23); // Mesmo tamanho e posição do botão
		labelTxt.setVisible(false); // Começa invisível
		contentPane.add(labelTxt);
		 
		final JButton btnRemoverArquivo = new JButton("X");
        btnRemoverArquivo.setBounds(464, 94, 45, 23);
        btnRemoverArquivo.setVisible(false); // Começa invisível
        contentPane.add(btnRemoverArquivo);
        
		 
        this.selectedFile = null;
		 btnAdicionarTxt.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                JFileChooser fileChooser = new JFileChooser();
	                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Apenas arquivos

	                // Filtro para apenas arquivos .txt
	                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos de texto", "txt"));

	                int result = fileChooser.showOpenDialog(null); // Abrir o diálogo

	                // Verifica se o usuário escolheu um arquivo
	                if (result == JFileChooser.APPROVE_OPTION) {
	                    selectedFile = fileChooser.getSelectedFile();
	                    JOptionPane.showMessageDialog(null, "Arquivo selecionado: " + selectedFile.getAbsolutePath());
	                        // Substituir o botão pelo nome do arquivo
                        btnAdicionarTxt.setVisible(false); // Esconder o botão
                        labelTxt.setText(selectedFile.getName()); // Exibir o nome do arquivo
                        labelTxt.setVisible(true); // Tornar o label visível
                        btnRemoverArquivo.setVisible(true);
	                }
	            }
	        });
		 
			   btnRemoverArquivo.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                labelTxt.setVisible(false); // Esconder o nome do arquivo
		                labelTxt.setText(""); // Limpar o texto do label
		                btnRemoverArquivo.setVisible(false); // Esconder o botão "X"
		                btnAdicionarTxt.setVisible(true); // Mostrar o botão "Adicionar txt" novamente
		                selectedFile = null;
		            }
		        });
	    }
}
