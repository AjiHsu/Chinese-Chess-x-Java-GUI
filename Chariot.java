package chineseChess;

import java.awt.Font;

public class Chariot extends Piece {

	Chariot(int r, int c, String group) {
		super(r, c, group);
		if (group == "R") {
			name = "俥";
		} else {
			name = "車";
		}
		if (group == "R") { // 以按鈕管理員直接參照新建物件;
			Chessboard.buttonManager[r][c].setText("<html><font color='red'>" + name + "</font></html>");
		} else {
			Chessboard.buttonManager[r][c].setText(name);
		}
		Chessboard.buttonManager[r][c].setFont(new Font("標楷體", Font.BOLD, 15));
		Chessboard.buttonManager[r][c].occupy = this;
	}

	@Override
	protected Boolean movable(int r, int c) {
		if (this.r == r) {
			if (this.c != c) {
				for (int i = Math.min(this.c, c) + 1; i < Math.max(this.c, c); i++) { // c動
					if (Chessboard.hasPiece[r][i]) {
						return false;
					}
				}
				return true;
			}
			return false;
		} else if (this.c == c) {
			if (this.r != r) {
				for (int i = Math.min(this.r, r) + 1; i < Math.max(this.r, r); i++) { // r動，不能吃子
					if (Chessboard.hasPiece[i][c]) {
						return false;
					}
				}
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
}