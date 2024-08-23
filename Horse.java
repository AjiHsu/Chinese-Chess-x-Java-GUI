package chineseChess;

import java.awt.Font;

public class Horse extends Piece {
	Horse(int r, int c, String group) {
		super(r, c, group);
		if (group == "R") {
			name = "傌";
		} else {
			name = "馬";
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
		try {
			if (!Chessboard.hasPiece[this.r + 1][this.c]) {
				if (this.r + 2 == r && this.c + 1 == c) {
					return true;
				}
				if (this.r + 2 == r && this.c - 1 == c) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			if (!Chessboard.hasPiece[this.r - 1][this.c]) {
				if (this.r - 2 == r && this.c + 1 == c) {
					return true;
				}
				if (this.r - 2 == r && this.c - 1 == c) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			if (!Chessboard.hasPiece[this.r][this.c + 1]) {
				if (this.r + 1 == r && this.c + 2 == c) {
					return true;
				}
				if (this.r - 1 == r && this.c + 2 == c) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			if (!Chessboard.hasPiece[this.r][this.c - 1]) {
				if (this.r + 1 == r && this.c - 2 == c) {
					return true;
				}
				if (this.r - 1 == r && this.c - 2 == c) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException e) {
		}
		return false;
	}
}