package chineseChess;

import java.awt.*;

public class Advisor extends Piece {

	Advisor(int r, int c, String group) {
		super(r, c, group);
		if (group == "R") {
			name = "仕";
		} else {
			name = "士";
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
		int[] dirr = { 1, 1, -1, -1 };
		int[] dirc = { 1, -1, 1, -1 };
		for (int i = 0; i < 4; i++) {
			if (this.r + dirr[i] == r && this.c + dirc[i] == c) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Boolean inBound(int r, int c) {
		switch (group) {
		case "R":
			return r >= 7 && r <= 9 && c >= 3 && c <= 5;
		case "B":
			return r >= 0 && r <= 2 && c >= 3 && c <= 5;
		default:
			return false;
		}
	}
}