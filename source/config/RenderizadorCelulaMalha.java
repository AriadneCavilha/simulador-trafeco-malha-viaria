package config;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderizadorCelulaMalha extends DefaultTableCellRenderer {

	//responsável por exibir icones e estradas de cada célula.
    @Override
    public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean isSelected, boolean hasFocus, int linha, int coluna) {
        // Centraliza o conteúdo da célula e define o ícone
        this.setHorizontalAlignment(SwingConstants.CENTER);
        setIcon((ImageIcon) valor);
        return this;
    }
}