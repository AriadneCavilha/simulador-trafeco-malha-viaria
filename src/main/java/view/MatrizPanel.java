package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
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
				int valorQuadrado = matriz[i][j];

				// Desenha o fundo do quadrado
				Color backgroundColor = getBackgroundColor(valorQuadrado);
				g.setColor(backgroundColor);
				g.fillRect(xOffset + j * cellSize, yOffset + i * cellSize, cellSize, cellSize);

				try {
					BufferedImage image;
					BufferedImage resizedImage;
					int x;
					int y;
					switch(valorQuadrado) {
					case 1:
						image = ImageIO.read(getClass().getResource("../assets/icon1.png"));
						resizedImage = resizeImageSmallerThanSquare(image, cellSize);
						
						x = xOffset + j * cellSize + (cellSize - resizedImage.getWidth()) / 2;
						y = yOffset + i * cellSize + (cellSize - resizedImage.getHeight()) / 2;
						g.drawImage(resizedImage, x, y, null);
						break;
					case 2:
						image = ImageIO.read(getClass().getResource("../assets/icon2.png"));
						resizedImage = resizeImageSmallerThanSquare(image, cellSize);

						x = xOffset + j * cellSize + (cellSize - resizedImage.getWidth()) / 2;
						y = yOffset + i * cellSize + (cellSize - resizedImage.getHeight()) / 2;
						g.drawImage(resizedImage, x, y, null);
						break;
					case 3:
						image = ImageIO.read(getClass().getResource("../assets/icon3.png"));
						resizedImage = resizeImageSmallerThanSquare(image, cellSize);

						x = xOffset + j * cellSize + (cellSize - resizedImage.getWidth()) / 2;
						y = yOffset + i * cellSize + (cellSize - resizedImage.getHeight()) / 2;
						g.drawImage(resizedImage, x, y, null);
						break;
					case 4:
						image = ImageIO.read(getClass().getResource("../assets/icon4.png"));
						resizedImage = resizeImageSmallerThanSquare(image, cellSize);

						x = xOffset + j * cellSize + (cellSize - resizedImage.getWidth()) / 2;
						y = yOffset + i * cellSize + (cellSize - resizedImage.getHeight()) / 2;
						g.drawImage(resizedImage, x, y, null);
						break;
					}
					
				} catch (IOException e) {
					System.err.println("Erro ao carregar a imagem: " + e.getMessage());
				}

				// Desenha a borda do quadrado
				g.setColor(Color.BLACK);
				g.drawRect(xOffset + j * cellSize, yOffset + i * cellSize, cellSize, cellSize);
			}
		}

	}

	private Color getBackgroundColor(int valorQuadrado) {
		switch (valorQuadrado) {
		case 0:
			return Color.WHITE;
		case 1:
			return Color.PINK;
		case 2:
			return Color.CYAN;
		case 3:
			return Color.BLUE;
		case 4:
			return Color.DARK_GRAY;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			return Color.LIGHT_GRAY;
		default:
			return Color.getHSBColor(0f, 0f, 0.83f);
		}
	}

	private BufferedImage resizeImageSmallerThanSquare(BufferedImage originalImage, int squareSize) {
		int newSize = (int) (squareSize * 0.8); // 80% do tamanho do quadrado

		BufferedImage resizedImage = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.drawImage(originalImage, 0, 0, newSize, newSize, null);
		graphics.dispose();

		return resizedImage;
	}

	@Override
	public Dimension getPreferredSize() {
		// Definir o tamanho preferido para o JPanel baseado na janela ou na matriz
		return new Dimension(800, 600); // Você pode ajustar esse tamanho
	}
}
