package view;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import model.Malha;

public class MalhaTableModel extends AbstractTableModel {

    @Override
    public int getRowCount() {
        return Malha.getInstance().getQtdLinhas();
    }

    @Override
    public int getColumnCount() {
        return Malha.getInstance().getQtdColunas();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return new ImageIcon(Malha.getInstance().getMatrizMalha()[rowIndex][columnIndex].getIcon());
    }
}
