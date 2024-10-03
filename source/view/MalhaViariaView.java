package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.JComboBox;

public class MalhaViariaView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox<String> comboBox;

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

		JLabel lblNewLabel = new JLabel("Escolha a malha");
		lblNewLabel.setBounds(304, 62, 179, 31);
		contentPane.add(lblNewLabel);

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

		comboBox = new JComboBox<String>();
		comboBox.setBounds(302, 94, 163, 22);
		comboBox.addItem("Malha 1");
		comboBox.addItem("Malha 2");
		comboBox.addItem("Malha 3");
		contentPane.add(comboBox);

		// ActionListener do botão "Iniciar Simulação" , botão teste
		btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String malhaSelecionada = getMalhaSelecionada();
                if (malhaSelecionada != null) {
                    // Instanciar a SimulacaoView passando o nome do arquivo da malha, para posteriormente iniciar a simulação com aquela malha
                	//implementar
                    SimulacaoView simulacaoView = new SimulacaoView();
                    simulacaoView.setVisible(true);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Nenhuma malha selecionada.");
                }
            }
        });

    }

	public String getMalhaSelecionada() {
		String malhaSelecionada = (String) comboBox.getSelectedItem();
		if (malhaSelecionada.equals("Malha 1")) {
			return "files/malha1.txt";
		} else if (malhaSelecionada.equals("Malha 2")) {
			return "files/malha2.txt";
		} else if (malhaSelecionada.equals("Malha 3")) {
			return "files/malha3.txt";
		}
		return null;
	}
}
