package view;

import javax.swing.*;
import model.Version;
public class Interaction {
    public static void show(String s){
        JOptionPane.showMessageDialog(null, s);
    }
    public static Version chooseVersion(){
        String[] s = {"Easy version", "Normal version", "Hard version"};
        int res = JOptionPane.showOptionDialog(null,"choose your version","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        switch (res) {
            case 0:
                return Version.EASYVERSION;
            case 1:
                return Version.NORMALVERSION;
            case 2:
                return Version.HARDVERSION;
        }
        return Version.EASYVERSION;
    }
    public static int WinnerChoose(){
        String[] s = {"start a new game", "try next version"};
        int res = JOptionPane.showOptionDialog(null,"you can choose","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        return res;
    }
    public static int LoserChoose(){
        String[] s = {"start a new game", "try this version again"};
        int res = JOptionPane.showOptionDialog(null,"you can choose","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        return res;
    }
    public static boolean isSave(){
        String[] s = {"Yes", "No"};
        int res = JOptionPane.showOptionDialog(null,"Do you want to save?","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        if(res == 0)
            return true;
        else
            return false;
    }
    public static boolean isRetractFalseMove(){
        String[] s = {"Yes", "No"};
        int res = JOptionPane.showOptionDialog(null,"Determine whether to retract your swap.","",
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,s,"中号");
        if(res == 0)
            return true;
        if(res == 1)
            return false;
        return false;
    }
}
