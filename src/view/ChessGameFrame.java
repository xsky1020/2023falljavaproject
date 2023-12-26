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
    private JLabel backgroundLabel;

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
        addImage("image/1.jpg");
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
        initUI();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public ScoreComponent getScore() {
        return this.Score;
    }

    public StepLeftComponent getStepLeft() {
        return this.StepLeft;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    private void addGameProcessFileFolder() {
        String folderPath = "gameProcess";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return;
        } else {
            boolean created = folder.mkdir();
            if (created) {
                System.out.println("create file folder successfully");
            } else {
                System.out.println("fail to create file folder");
            }
        }
    }

    private void addStateSavedFileFolder() {
        String folderPath = "gameProcess\\stateSaved";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return;
        } else {
            boolean created = folder.mkdir();
            if (created) {
                System.out.println("create file folder successfully");
            } else {
                System.out.println("fail to create file folder");
            }
        }
    }

    private void addPreviousStateFileFolder() {
        String folderPath = "gameProcess\\previousState";
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return;
        } else {
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

    private void addScoreComponent() {
        Score = new ScoreComponent(this, WIDTH, HEIGTH);
    }

    private void addStepLeftComponent() {
        StepLeft = new StepLeftComponent(this, WIDTH, HEIGTH);
    }

    /**
     * 在游戏面板中增加一个按钮,用于保存当前状态
     */
    private void saveNowState() {
        //System.out.println("Click save");
        File stateSaved = new File("gameProcess/stateSaved");
        JFileChooser jfc = new JFileChooser(stateSaved);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.showDialog(new JLabel(), "选择");
        File file = jfc.getSelectedFile();
        if (file.isDirectory()) {
            System.out.println("文件夹:" + file.getAbsolutePath());
        } else if (file.isFile()) {
            System.out.println("文件:" + file.getAbsolutePath());
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
        add(button);
        button.addActionListener(e -> {
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

    private boolean loadVerify(File file) {
        String filename = file.getName();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        if (!suffix.toLowerCase(Locale.ROOT).equals(".txt")) {
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
            JFileChooser jfc = new JFileChooser(stateSaved);
            jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jfc.showDialog(new JLabel(), "选择");
            File file = jfc.getSelectedFile();
            if (loadVerify(file)) {
                if (file.isDirectory()) {
                    System.out.println("文件夹:" + file.getAbsolutePath());
                } else if (file.isFile()) {
                    System.out.println("文件:" + file.getAbsolutePath());
                }
                System.out.println(jfc.getSelectedFile().getName());
                String path = String.valueOf(file);
                chessboardComponent.loadGameFromFile(path);
            }
        });
    }

    private void addNewGameButton() {
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

    private void addRetractFalseMove() {
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

    private void addExitButton() {
        JButton button = new JButton("Exit");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Interaction.isSave()) {
                    saveNowState();
                }
                dispose();
                System.exit(0);
            }
        });
    }



    private void addImage(String imagePath) {
        if (backgroundLabel != null) {
            getLayeredPane().remove(backgroundLabel); // 移除旧的背景图
        }
        JPanel imPanel = (JPanel) this.getContentPane();
        imPanel.setOpaque(false);
        ImageIcon icon = new ImageIcon(imagePath);
        backgroundLabel = new JLabel(icon);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        icon.setImage(icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
        getLayeredPane().add(backgroundLabel,  Integer.valueOf(Integer.MIN_VALUE));
        getLayeredPane().repaint();
    }

    private void initUI() {
        // 创建一个按钮，用于打开主题设置窗口
        JButton btnOpenThemeWindow = new JButton("Set Theme");
        btnOpenThemeWindow.setLocation(HEIGTH   , HEIGTH / 10 + 540);
        btnOpenThemeWindow.setSize(200, 50);
        btnOpenThemeWindow.setFont(new Font("Rockwell", Font.BOLD, 20));
        btnOpenThemeWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openThemeWindow();
            }
        });
        add(btnOpenThemeWindow);
    }

    private void openThemeWindow() {
        // 创建一个对话框或者一个新的小窗口
        JDialog themeDialog = new JDialog(this, "Choose Theme", true);
        themeDialog.setLocation(HEIGTH/2 +200   , HEIGTH / 10 +380);
        themeDialog.setSize(300, 100);
        themeDialog.setLayout(new FlowLayout());

        // 在这个小窗口中添加主题切换的组件
        String[] themes = {"1", "2", "3"};
        JComboBox<String> themeComboBox = new JComboBox<>(themes);
        themeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchTheme((String) themeComboBox.getSelectedItem());
            }
        });

        themeDialog.add(themeComboBox);
        themeDialog.setVisible(true);
    }

    private void switchTheme(String themeName) {
        try {
            switch (themeName) {
                case "1":
                    addImage("image/1.jpg");
                    break;
                case "2":
                    addImage("image/2.jpg");
                    break;
                case "3":
                    addImage("image/3.jpg");
                    break;
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}