package chineseChess;

import java.awt.Font;

public class Cannon extends Piece {

	Cannon(int r, int c, String group) {
		super(r, c, group);
		if (group == "R") {
			name = "炮";
		} else {
			name = "包";
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
		int spanPiece = 0;
		if (this.r == r) {
			if (this.c != c) {
				for (int i = Math.min(this.c, c) + 1; i < Math.max(this.c, c); i++) { // c動
					if (Chessboard.hasPiece[r][i]) {
						spanPiece++;
					}
					if (spanPiece == 2) { // 跨超過1個棋子，不能吃
						return false;
					}
				}
				if (spanPiece == 1 && Chessboard.hasPiece[r][c]) { // 吃子
					return true;
				} else if (spanPiece == 0) { // 移動
					if (Chessboard.hasPiece[r][c]) { // 炮移動不能吃子
						return false;
					}
					return true;
				}
			}
			return false;
		} else if (this.c == c) {
			if (this.r != r) {
				for (int i = Math.min(this.r, r) + 1; i < Math.max(this.r, r); i++) { // r動，不能吃子
					if (Chessboard.hasPiece[i][c]) {
						spanPiece++;
					}
					if (spanPiece == 2) {
						return false;

					}
				}
				if (spanPiece == 1 && Chessboard.hasPiece[r][c]) { // 吃子
					return true;
				} else if (spanPiece == 0) { // 移動
					if (Chessboard.hasPiece[r][c]) { // 炮移動不能吃子
						return false;
					}
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
}