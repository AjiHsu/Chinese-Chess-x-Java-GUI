package chineseChess;

import java.awt.Font;

public class Piece {
	protected int r, c; // r:0~9; c:0~8
	protected String group; // R or B
	protected String name;

	Piece(int r, int c, String group) { // 建構式：設置位置
		this.r = r;
		this.c = c;
		this.group = group;
		Chessboard.hasPiece[r][c] = true;
		Chessboard.pieceManager.add(this); // 管理棋子
	}

	protected void move(int r, int c, CustomButton from, CustomButton to) { // set location
		Chessboard.hasPiece[this.r][this.c] = false;
		this.r = r;
		this.c = c;
		Chessboard.hasPiece[r][c] = true;
		from.setText("");
		if (from.occupy.group == "R") {
			to.setText("<html><font color='red'>" + name + "</font></html>");
		} else {
			to.setText(name);
		}
		if (to.occupy != null) { // 刪除被吃掉的棋子
			Chessboard.pieceManager.remove(to.occupy);
		}
		to.occupy = this;
		from.occupy = null;
		Chessboard.buttonManager[r][c].setFont(new Font("標楷體", Font.BOLD, 15));

	}

	protected Boolean movable(int r, int c) {
		return false;
	}

	protected Boolean inBound(int r, int c) {
		return r >= 0 && r <= 9 && c >= 0 && c <= 8;
	}
}