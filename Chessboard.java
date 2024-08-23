package chineseChess;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Chessboard extends JFrame implements ActionListener {

	protected static CustomButton[][] buttonManager = new CustomButton[10][9]; // [r][c]管理所有按鈕，有屬性occupy可以參照piece物件
	protected static ArrayList<Piece> pieceManager = new ArrayList<>(); // 尋訪piece時會用到
	protected static Boolean[][] hasPiece = new Boolean[10][9]; // 有無棋子

	private Boolean selected = false; // 是否選中棋子
	private Piece selectPiece = null; // 選到的棋子是什麼
	private CustomButton selectButton = null; // 選到的按鈕是哪個
	private String turn = "R"; // 輪到誰了
	private King k_R, k_B; // 全域變數，給dKing和KingToKing參照

	public static void main(String[] argv) {
		new Chessboard(); // 直接執行建構式
	}

	Chessboard() {
		for (int i = 0; i < 10; i++) { // 初始化hasPiece
			for (int j = 0; j < 9; j++) {
				if (hasPiece[i][j] == null) {
					hasPiece[i][j] = false;
				}
			}
		}

//      Frame
		JPanel panel = new JPanel();
		setTitle("Chinese Chess (輪到紅棋)");
		panel.setLayout(new GridLayout(10, 9));
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 600);
		setResizable(false);
		setVisible(true);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}

//		button init
		for (int i = 0; i < 10; i++) { // 初始化buttonManager
			for (int j = 0; j < 9; j++) {
				buttonManager[i][j] = new CustomButton("");
				buttonManager[i][j].setPreferredSize(new Dimension(60, 50));
				panel.add(buttonManager[i][j]);
				buttonManager[i][j].addActionListener(this);
			}
		}
//		init piece:直接用每個類別的建構式使按鈕能直接從.occupy參照物件
		k_B = new King(0, 4, "B");
		k_R = new King(9, 4, "R");
		new Advisor(0, 3, "B");
		new Advisor(0, 5, "B");
		new Advisor(9, 3, "R");
		new Advisor(9, 5, "R");
		new Elephant(0, 2, "B");
		new Elephant(0, 6, "B");
		new Elephant(9, 2, "R");
		new Elephant(9, 6, "R");
		new Horse(0, 1, "B");
		new Horse(0, 7, "B");
		new Horse(9, 1, "R");
		new Horse(9, 7, "R");
		new Chariot(0, 0, "B");
		new Chariot(0, 8, "B");
		new Chariot(9, 0, "R");
		new Chariot(9, 8, "R");
		new Cannon(2, 1, "B");
		new Cannon(2, 7, "B");
		new Cannon(7, 1, "R");
		new Cannon(7, 7, "R");
		new Soldier(3, 0, "B");
		new Soldier(3, 2, "B");
		new Soldier(3, 4, "B");
		new Soldier(3, 6, "B");
		new Soldier(3, 8, "B");
		new Soldier(6, 0, "R");
		new Soldier(6, 2, "R");
		new Soldier(6, 4, "R");
		new Soldier(6, 6, "R");
		new Soldier(6, 8, "R");
	}

	@Override
	public void actionPerformed(ActionEvent e) { // 按鈕事件
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) { // i, j搜尋按鈕的r, c找按鈕

				if ((JButton) e.getSource() == buttonManager[i][j] // 找到是哪個按鈕被按下了
						&& !selected) { // 當還沒選棋子時
					if ((buttonManager[i][j].occupy != null // 選到的按鈕不是空且
							&& turn != buttonManager[i][j].occupy.group) // 還沒輪到
							|| buttonManager[i][j].occupy == null) { // 或選到空按鈕
						break; // 直接中止
					}
					selectButton = buttonManager[i][j]; // 刷新選中按鈕
					buttonManager[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); // 按鈕變色
					selected = true; // 已經選中了
					selectPiece = buttonManager[i][j].occupy; // 刷新選中棋子
					System.out.println("piece selected"); // 回報選中狀態
					break; // 停止搜尋

				} else if ((JButton) e.getSource() == buttonManager[i][j] // 找到棋子了
						&& selected) { // 已經從另一個按鈕選中棋子了
					if (selectPiece.movable(i, j) // 可移動
							&& (buttonManager[i][j].occupy == null // 如果目的地是空的
									|| (buttonManager[i][j].occupy != null
											&& buttonManager[i][j].occupy.group != selectPiece.group))) { // 如果目的地有人，則不能是同隊的
						if (KingToKing(selectPiece, i, j)) { // 王對王
							System.out.println("illegal move : KingToKing");
							break;
						}
						if (dKing(selectPiece, i, j)) { // 移動會害自己人
							System.out.println("illegal move : dKing");
							break;
						}

						selectPiece.move(i, j, selectButton, buttonManager[i][j]); // 移動
						System.out.println("move");

						if (turn == "R") { // 換人
							turn = "B";
							setTitle("Chinese Chess (輪到黑棋)");
						} else {
							turn = "R";
							setTitle("Chinese Chess (輪到紅棋)");
						}
					}
					selected = false;
					selectButton.setBorder(null);

					end("R"); // 遊戲結束
					end("B");

					break;
				}
			}
		}
	}

	private Boolean KingToKing(Piece p, int to_r, int to_c) { // 王不見王
		Boolean original = hasPiece[to_r][to_c]; // 儲存目的地的原本狀態
		hasPiece[to_r][to_c] = true; // 暫時改變hasPiece
		hasPiece[p.r][p.c] = false;

		if (p == k_R // 如果是紅將軍移動，此時p就是to就是k_R
				&& to_c == k_B.c) { // 如果王王在同一直線
			for (int i = k_B.r + 1; i < to_r; i++) { // 搜尋此直線上的所有按鈕
				if (hasPiece[i][to_c]) { // 有棋子
					hasPiece[to_r][to_c] = false; // 改回來
					hasPiece[p.r][p.c] = true;
					return false;
				}
			}
			hasPiece[to_r][to_c] = original; // 改回來
			hasPiece[p.r][p.c] = true;

			return true;

		} else if (p == k_B // 如果是黑將軍移動，此時p就是to就是k_B
				&& to_c == k_R.c) { // 如果王王在同一直線
			for (int i = to_r + 1; i < k_R.r; i++) {
				if (hasPiece[i][to_c]) {
					hasPiece[to_r][to_c] = false; // 改回來
					hasPiece[p.r][p.c] = true;

					return false;
				}
			}
			hasPiece[to_r][to_c] = original; // 改回來
			hasPiece[p.r][p.c] = true;

			return true;

		} else if (k_R.c == k_B.c) { // 如果是一般棋子且在同一條直線
			for (int i = k_B.r + 1; i < k_R.r; i++) {
				if (hasPiece[i][k_R.c]) {
					hasPiece[to_r][to_c] = original; // 改回來
					hasPiece[p.r][p.c] = true;

					return false;
				}
			}
			hasPiece[to_r][to_c] = original; // 改回來
			hasPiece[p.r][p.c] = true;

			return true;
		}
		hasPiece[to_r][to_c] = original; // 改回來
		hasPiece[p.r][p.c] = true;

		return false;
	}

	private Boolean dKing(Piece p, int to_r, int to_c) { // 威脅自己王的移動
		Boolean original = hasPiece[to_r][to_c]; // 儲存目的地的原本狀態
		hasPiece[to_r][to_c] = true; // 暫時改變hasPiece
		hasPiece[p.r][p.c] = false;

		if (p == k_R) { // 如果是帥移動
			for (Piece otherP : pieceManager) {
				if (otherP.group == "B" && otherP.movable(to_r, to_c)) { // 因為已經設定目的地是空，所以可以排除.occupy == null的情況
					hasPiece[to_r][to_c] = original; // 改回來
					hasPiece[p.r][p.c] = true;

					return true;
				}
			}
			hasPiece[to_r][to_c] = original; // 改回來
			hasPiece[p.r][p.c] = true;

			return false;

		} else if (p == k_B) { // 如果是將移動
			for (Piece otherP : pieceManager) {
				if (otherP.group == "R" && otherP.movable(to_r, to_c)) {
					hasPiece[to_r][to_c] = original; // 改回來
					hasPiece[p.r][p.c] = true;

					return true;
				}
			}
			hasPiece[to_r][to_c] = original; // 改回來
			hasPiece[p.r][p.c] = true;

			return false;

		} else { // 如果是一般棋子
			for (Piece otherP : pieceManager) {
				if (otherP.group == "R" && p.group == "B" // 黑方移動是否害死自己
						&& (otherP.movable(k_B.r, k_B.c) && (otherP.r != to_r || otherP.c != to_c))) {
					hasPiece[to_r][to_c] = original; // 改回來
					hasPiece[p.r][p.c] = true;

					return true;

				} else if (otherP.group == "B" && p.group == "R" // 紅方移動是否害死自己
						&& (otherP.movable(k_R.r, k_R.c) && (otherP.r != to_r || otherP.c != to_c))) {
					hasPiece[to_r][to_c] = original; // 改回來
					hasPiece[p.r][p.c] = true;

					return true;

				}
			}
			hasPiece[to_r][to_c] = original; // 改回來
			hasPiece[p.r][p.c] = true;

			return false;
		}
	}

	private Boolean end(String turn) { // 回傳值是用來中止方法的，不需要被接收
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				for (Piece p : pieceManager) {
					if (turn == "R" && p.group == "R" // 如果是紅隊
							&& (p.movable(i, j) // 可移動
									&& (buttonManager[i][j].occupy == null // 如果目的地是空的
											|| (buttonManager[i][j].occupy != null
													&& buttonManager[i][j].occupy.group != p.group)))
							&& !dKing(p, i, j) && !KingToKing(p, i, j)) { // 如果可以移動
						return false;
					} else if (turn == "B" && p.group == "B" && (p.movable(i, j) // 可移動
							&& (buttonManager[i][j].occupy == null // 如果目的地是空的
									|| (buttonManager[i][j].occupy != null
											&& buttonManager[i][j].occupy.group != p.group)))
							&& !dKing(p, i, j) && !KingToKing(p, i, j)) {
						return false;
					}
				}
			}
		}
		JFrame endFrame = new JFrame();
		endFrame.add(new JLabel("GAME OVER"));
		endFrame.setSize(100, 50);
		endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		endFrame.setResizable(false);
		endFrame.setVisible(true);
		return true;
	}
}