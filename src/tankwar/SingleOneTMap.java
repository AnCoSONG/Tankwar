package tankwar;

import javax.swing.*;
import java.awt.*;

/**
 * <p>单个地图类</p>
 */
public class SingleOneTMap {


    /**绘制时x的值*/
    private int x;
    /**绘制时y的值*/
    private int y;
    /**是否被选中*/
    private Point ID;
    /**地图图片的类型*/
    private int imageType;
    /**是否可以通过*/
    private boolean canPass;
    /**是否被选中*/
    private boolean selected;
    /**是否被破坏*/
    private boolean broken = false;
    /**是否可破坏*/
    private boolean canBreak = false;
    /**图像*/
    private ImageIcon image = new ImageIcon("");

    public final int width = 60;
    public final int height = 60;



    //定义地图物件的类型
    int map_thing_type = 0;
    static final int steel = 1;
    static final int steels = 2;//4格steel
    static final int wall = 3;
    static final int walls = 4;//4格wall
    static final int water = 5;
    static final int grass = 6;


    //定义这些地图物品的链接地址
    String steel_url = "img//steel.gif";
    String steels_url = "img//steels.gif";
    String wall_url = "img//wall.gif";
    String walls_url = "img//walls.gif";
    String water_url = "img//water.gif";
    String grass_url = "img//grass.png";



    public void draw(Graphics graphics) {
        if(this.isCanBreak()){
            if(!this.isBroken()){
                graphics.drawImage(image.getImage(),getX(),getY(),null);
            }
        }else{
            graphics.drawImage(image.getImage(),getX(),getY(),null);
        }
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public boolean isCanBreak() {
        return canBreak;
    }

    public void setCanBreak(boolean canBreak) {
        this.canBreak = canBreak;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     *
     * @param ID 传入的字符串将经过转换成为Point并setID
     * @param imageType 传入的字符串将被转换成整型数
     * @param canPass 传入的字符串将被转换成布尔值
     */
    public SingleOneTMap(String ID, String imageType, String canPass){
        int px = Integer.parseInt(ID.split(",")[0]);
        int py = Integer.parseInt(ID.split(",")[1]);
        Point id = new Point(px,py);
        setID(id);
        setImageType(Integer.parseInt(imageType));
        boolean temp = false;
        switch (canPass){
            case "true": temp = true; break;
            case "false": temp = false; break;
        }
        setCanPass(temp);
        setSelected(true);

    }


    public int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    public Point getID() {
        return ID;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
        switch (imageType){
            case steels: image = new ImageIcon(steels_url);
                break;
            case walls: image = new ImageIcon(walls_url);
                break;
            case water:image = new ImageIcon(water_url);
                break;
            case grass:image = new ImageIcon(grass_url);
                break;
            default:
                break;
        }
    }

    public boolean isCanPass() {
        return canPass;
    }

    public void setID(Point ID) {
        this.ID = ID;
        setX(ID.x*60);
        setY(ID.y*60);
    }

    public void setCanPass(boolean canPass) {
        this.canPass = canPass;
    }
}
