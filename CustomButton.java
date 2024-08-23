package chineseChess;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
class CustomButton extends JButton {
	public Piece occupy = null;

	public CustomButton(String text) {
		super(text);
		this.setBorder(null);
	}

	@Override
	protected void paintComponent(Graphics g) { // 宣告時使用
		super.paintComponent(g);
		g.drawLine(0, 30, 50, 30);
		g.drawLine(25, 0, 25, 60);
	}
}