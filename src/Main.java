import controller.GameController;
import model.Chessboard;
import model.Music;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0), mainFrame.getScore(), mainFrame.getStepLeft());
            mainFrame.setVisible(true);

            Music audioPlayWave = new Music("music/music.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            @SuppressWarnings("unused")
            int musicOpenLab = 1;
        });
    }
}
