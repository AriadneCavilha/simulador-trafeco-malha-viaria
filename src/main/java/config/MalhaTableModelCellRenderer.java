package config;


import java.awt.Component;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class MalhaTableModelCellRenderer extends JLabel implements TableCellRenderer {

    private static final int MAX_ICON_SIZE = 32;
    private Map<Image, ImageIcon> cachedIcons = new HashMap<>();

    public MalhaTableModelCellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            Image image = icon.getImage();

            if (image != null) {
                ImageIcon scaledIcon = getCachedScaledIcon(image);
                setIcon(scaledIcon);
            } else {
                setIcon(null);
            }
        } else {
            setText(value.toString());
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }

    private ImageIcon getCachedScaledIcon(Image image) {
        if (!cachedIcons.containsKey(image)) {
            int width = Math.min(MAX_ICON_SIZE, image.getWidth(null));
            int height = Math.min(MAX_ICON_SIZE, image.getHeight(null));
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            cachedIcons.put(image, new ImageIcon(scaledImage));
        }
        return cachedIcons.get(image);
    }
}
