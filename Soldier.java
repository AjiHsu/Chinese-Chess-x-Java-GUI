package chineseChess;

import java.awt.Font;

public class Soldier extends Piece {

	Soldier(int r, int c, String group) {
		super(r, c, group);
		if (group == "R") {
			name = "兵";
		} else {
			name = "卒";
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
		int forward; // 兩隊不同移動方式
		if (group == "R") {
			forward = -1;
		} else {
			forward = 1;
		}
		int[] dirr, dirc;
		if (inBound(r, c)) { // 過界or not的移動不同
			dirr = new int[] { forward };
			dirc = new int[] { 0 };
		} else {
			dirr = new int[] { forward, 0, 0 };
			dirc = new int[] { 0, 1, -1 };
		}

		for (int i = 0; i < dirr.length; i++) {
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
			return r >= 5 && r <= 9 && c >= 0 && c <= 8;
		case "B":
			return r >= 0 && r <= 4 && c >= 0 && c <= 8;
		default:
			return false;
		}
	}
}