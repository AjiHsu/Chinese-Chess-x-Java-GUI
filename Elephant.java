package chineseChess;

import java.awt.Font;

public class Elephant extends Piece {

	Elephant(int r, int c, String group) {
		super(r, c, group);
		if (group == "R") {
			name = "相";
		} else {
			name = "象";
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
		if (!inBound(r, c)) {
			return false;
		}
		int[] dirr = { 2, 2, -2, -2 };
		int[] dirc = { 2, -2, 2, -2 };
		int[] confiner = { 1, 1, -1, -1 }; // 拐象腳
		int[] confinec = { 1, -1, 1, -1 };
		for (int i = 0; i < 4; i++) {
			if (this.r + dirr[i] == r && this.c + dirc[i] == c) {
				if (!Chessboard.hasPiece[this.r + confiner[i]][this.c + confinec[i]]) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected Boolean inBound(int r, int c) {
		switch (group) {
		case "R":
			return r >= 5 && r <= 9 && c >= 0 && c <= 8;
		case "B":
			return r >= 0 && r <= 4 && c >= 0 && c <= 8;
		default:
			return false;
		}
	}
}