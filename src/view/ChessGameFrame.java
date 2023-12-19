package view;
import model.Music;
import model.MusicOne;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private ChessboardComponent chessboardComponent;
    private ScoreComponent Score;
    private StepLeftComponent StepLeft;
    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addImage();
        addChessboard();
        addScoreComponent();
        addStepLeftComponent();
        addSaveButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addNewGameButton();
        addRetractFalseMove();
        addExitButton();
        addGameProcessFileFolder();
        addStateSavedFileFolder();
        addPreviousStateFileFolder();

    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public ScoreComponent getScore(){
        return this.Score;
    }
    public StepLeftComponent getStepLeft(){
        return this.StepLeft;
    }
    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }
    private void addGameProcessFileFolder(){
        String folderPath = "gameProcess";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return;
        }
        else {
            boolean created = folder.mkdir();
            if (created) {
                System.out.println("create file folder successfully");
            } else {
                System.out.println("fail to create file folder");
            }
        }
    }
    private void addStateSavedFileFolder(){
        String folderPath = "gameProcess\\stateSaved";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return;
        }
        else {
            boolean created = folder.mkdir();
            if (created) {
                System.out.println("create file folder successfully");
            } else {
                System.out.println("fail to create file folder");
            }
        }
    }
    private void addPreviousStateFileFolder(){
        String folderPath = "gameProcess\\previousState";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return;
        }
        else {
            boolean created = folder.mkdir();
            if (created) {
                System.out.println("create file folder successfully");
            } else {
                System.out.println("fail to create file folder");
            }
        }
    }
    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }
    private void addScoreComponent(){
        Score = new ScoreComponent(this, WIDTH, HEIGTH);
    }
    private void addStepLeftComponent(){
        StepLeft = new StepLeftComponent(this, WIDTH, HEIGTH);
    }

    /**
     * 在游戏面板中增加一个按钮,用于保存当前状态
     */
    private void saveNowState(){
        //System.out.println("Click save");
        File stateSaved = new File("gameProcess/stateSaved");
        JFileChooser jfc=new JFileChooser(stateSaved);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }
        else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());
        String path = String.valueOf(file);
        chessboardComponent.saveGameToFile(path);
    }
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);button.addActionListener(e -> {
            MusicOne audioPlayWave = new MusicOne("music/click.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            saveNowState();
        });
    }

    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener(e -> {
            MusicOne audioPlayWave = new MusicOne("music/click.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            chessboardComponent.swapChess();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener(e -> {
            MusicOne audioPlayWave = new MusicOne("music/click.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            chessboardComponent.nextStep();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private boolean loadVerify(File file){
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        if (!suffix.toLowerCase(Locale.ROOT).equals(".txt")){
            JOptionPane.showMessageDialog(this, "101");
            return false;
        }
        return true;
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            MusicOne audioPlayWave = new MusicOne("music/click.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            File stateSaved = new File("gameProcess/stateSaved");
            JFileChooser jfc=new JFileChooser(stateSaved);
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
            jfc.showDialog(new JLabel(), "选择");
            File file=jfc.getSelectedFile();
            if (loadVerify(file)){
                if(file.isDirectory()){
                    System.out.println("文件夹:"+file.getAbsolutePath());
                }
                else if(file.isFile()){
                    System.out.println("文件:"+file.getAbsolutePath());
                }
                System.out.println(jfc.getSelectedFile().getName());
                String path = String.valueOf(file);
                chessboardComponent.loadGameFromFile(path);
            }
        });
    }
    private void addNewGameButton(){
        JButton button = new JButton("New Game");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            MusicOne audioPlayWave = new MusicOne("music/click.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            chessboardComponent.newGame();
        });
}

    private void addRetractFalseMove(){
        JButton button = new JButton("Retract a false move");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 16));
        add(button);
        button.addActionListener(e -> {
            MusicOne audioPlayWave = new MusicOne("music/click.wav");// 开音乐(冒号里的内容与音乐文件名一致)
            audioPlayWave.start();
            chessboardComponent.retractFalseMove();
        });
    }
    private void addExitButton(){
        JButton button = new JButton("Exit");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Interaction.isSave()){
                    saveNowState();
                }
                dispose();
                System.exit(0);
            }
        });
    }
    private void addImage(){
        JPanel imPanel=(JPanel) this.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon=new ImageIcon("image/image.jpg");//背景图
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, this.getWidth(), this.getHeight());//设置标签位置大小，记得大小要和窗口一样大
        icon.setImage(icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));//图片自适应标签大小
        this.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板

    }

}
