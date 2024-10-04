package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MatrizPanel extends JPanel {
	private int[][] matriz;
	
	public MatrizPanel(int[][] leituraMatriz) {
		this.matriz = leituraMatriz;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
	        int tamanho = 50; // Tamanho de cada quadrado

	        for (int i = 0; i < matriz.length; i++) {
	            for (int j = 0; j < matriz[i].length; j++) {
	                // Defina a cor ou a lógica para desenhar com base no valor
	                if (matriz[i][j] > 0) {
	                    g.setColor(Color.GREEN); // Exemplo de cor
	                } else {
	                    g.setColor(Color.RED);
	                }
	                g.fillRect(j * tamanho, i * tamanho, tamanho, tamanho); // Desenha o quadrado
	                g.setColor(Color.BLACK);
	                g.drawRect(j * tamanho, i * tamanho, tamanho, tamanho); // Desenha a borda
	            }
	        }
	}
}
