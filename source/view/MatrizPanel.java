package view;

import java.awt.Color;
import java.awt.Dimension;
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

        int numRows = matriz.length;
        int numCols = matriz[0].length;

        // Calcula o tamanho máximo possível para cada quadrado sem sair da tela
        int cellSize = Math.min(getWidth() / numCols, getHeight() / numRows);

        // Centraliza a matriz
        int xOffset = (getWidth() - (numCols * cellSize)) / 2;
        int yOffset = (getHeight() - (numRows * cellSize)) / 2;

        // Desenhar a matriz com o novo tamanho e centralizada
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                // Defina a cor ou a lógica para desenhar com base no valor
                if (matriz[i][j] > 0) {
                    g.setColor(Color.GREEN); // Exemplo de cor
                } else {
                    g.setColor(Color.RED);
                }
                // Desenha o quadrado, ajustando o xOffset e yOffset
                g.fillRect(xOffset + j * cellSize, yOffset + i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(xOffset + j * cellSize, yOffset + i * cellSize, cellSize, cellSize); // Desenha a borda
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        // Definir o tamanho preferido para o JPanel baseado na janela ou na matriz
        return new Dimension(800, 600); // Você pode ajustar esse tamanho
    }
}
