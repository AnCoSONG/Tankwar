package tankwar;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.net.URL;

public class MapEditorSingleMapBoxPane  {

    private Point id;
    private int x;
    private int y;
    private int width;
    private int height;
    protected int imageChoice = 0;
    protected boolean canPass = false;
   //地图类型编号和素材链接
    static final int steel = 1;
    static final int steels = 2;//4格steel
    static final int wall = 3;
    static final int walls = 4;//4格wall
    static final int water = 5;
    static final int grass = 6;
    String steel_url = "cache/cachesteel.gif";
    String steels_url = "cache/cachesteels.gif";
    String wall_url = "cache/cachewall.gif";
    String walls_url = "cache/cachewalls.gif";
    String water_url = "cache/cachewater.gif";
    String grass_url = "cache/cachegrass.png";

    /**used to show the preview of the map */
    private ImageIcon image;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    private boolean isSelected = false;
    private boolean isMapPoint = false;
    private boolean isTankPoint = false;
    private boolean isEnemyPoint = false;
    private String tankDir = "UP";
    private String enemyDir = "UP";

    Font font = new Font("苹方 粗体",Font.PLAIN,15);

    public MapEditorSingleMapBoxPane(Point id,int x, int y, int width, int height){
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;
        image = new ImageIcon("");
    }


    public void setImage(File file){
        image = new ImageIcon(file.getPath());
    }

    public void setImage(String url){
        image = new ImageIcon(url);
    }

    public void setImage(URL url){
        image = new ImageIcon(url);
    }

    public void setImage(int mapType){
        imageChoice = mapType;
        switch (imageChoice){
            case steels: this.setImage(steels_url);
                break;
            case grass: this.setImage(grass_url);
                break;
            case water: this.setImage(water_url);
                break;
            case walls: this.setImage(walls_url);
                break;
            case -1:this.imageChoice = 0;
                break;
            default:
                break;
        }
    }


    public void draw(Graphics graphics){
        graphics.setFont(font);
        graphics.drawRect(x, y, width, height);
        if(isSelected){
            if(isMapPoint) {
                graphics.drawImage(image.getImage(), x, y, null);
            } else if(isTankPoint){
                //画出代表坦克出生点的图
                graphics.drawString("T",x+10,y+20);
            }else if(isEnemyPoint){
                //画出代表敌方出生点的图
                graphics.drawString("E",x+10,y+20);
            }
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Point getId() {
        return id;
    }

    public int getImageChoice() {
        return imageChoice;
    }

    public boolean isCanPass() {
        return canPass;
    }

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }
    public boolean isMapPoint() {
        return isMapPoint;
    }

    public void setMapPoint(boolean mapPoint) {
        isMapPoint = mapPoint;
    }

    public boolean isTankPoint() {
        return isTankPoint;
    }

    public void setTankPoint(boolean tankPoint) {
        isTankPoint = tankPoint;
    }

    public boolean isEnemyPoint() {
        return isEnemyPoint;
    }

    public void setEnemyPoint(boolean enemyPoint) {
        isEnemyPoint = enemyPoint;
    }

    public String getTankDir() {
        return tankDir;
    }

    public void setTankDir(String tankDir) {
        this.tankDir = tankDir;
    }

    public String getEnemyDir() {
        return enemyDir;
    }

    public void setEnemyDir(String enemyDir) {
        this.enemyDir = enemyDir;
    }
}
