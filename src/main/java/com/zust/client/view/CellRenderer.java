package com.zust.client.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zzzz on 2018/1/11.
 */
public class CellRenderer extends JLabel implements ListCellRenderer {

    Icon[] icons;
    public CellRenderer(){};
//    public CellRenderer(Icon[] icons) {
//        // TODO Auto-generated constructor stub
//        this.icons=icons;
//    }


    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String s = value.toString();
        setText(s);
        setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 5));//加入宽度为5的空白边框
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setIcon(new ImageIcon(getClass().getResource("image/1.png")));//设置图片
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}
