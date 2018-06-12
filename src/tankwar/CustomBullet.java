package tankwar;

import java.awt.*;
import java.util.ArrayList;

public class CustomBullet extends Bullet {

    boolean isHitTank = false;
    /**
     * <p>Title: 子弹</p>
     * <p>Description: 实例化子弹</p>
     *
     * @param x           子弹x坐标
     * @param y           子弹y坐标
     * @param dir         子弹方向
     * @param bullet_type 子弹类型 <code>0</code> tank <code>1</code> enemy
     */
    public CustomBullet(int x, int y, Direction dir, int bullet_type) {
        super(x, y, dir, bullet_type);
    }

    public boolean isLiveCustom(int x, int y, ArrayList<SingleOneTMap> singleOneTMaps) {
        Point temp = getPosition();
        if(temp.x <0 || temp.x>540||temp.y<0||temp.y>540){
            setLife(false);
        }else{
            setLife(!isHitCustom(x,y,singleOneTMaps));
        }
        return life;
    }

    /**
     * <p>检测是否打到敌方坦克或者打到墙</p>
     * @param x  敌方x
     * @param y  敌方y
     * @param singleOneTMaps 地图链表
     * @return true 打中
     */
    public boolean isHitCustom(int x, int y, ArrayList<SingleOneTMap> singleOneTMaps) {
        boolean isHit = false;
        Point temp = getPosition();
        double center_x = temp.x + 7.5;
        double center_y = temp.y + 7.5;
        if(Math.abs(center_x-x)<37.5&&(Math.abs(center_y-y))<37.5){
            //是否打中坦克
            isHitTank = true;
            isHit = true;
        }else{
            for(SingleOneTMap i : singleOneTMaps){
                if((Math.abs(center_x - i.getX()) < 37.5) && (Math.abs(center_y - i.getY())<37.5)) {
                    isHit = true;
                    break;
                }else{
                    isHit = false;
                }
            }
        }
        return isHit;

    }

    public void draw(Graphics graphics){
        graphics.drawImage(getImageIcon().getImage(),getPosition().x+21,getPosition().y+20,null);
    }

}
