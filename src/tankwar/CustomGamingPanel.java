package tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

/**
 * 自定义游戏时显示这个页面
 */
public class CustomGamingPanel extends JPanel implements KeyListener {

    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -766568908924451941L;
	boolean pause = false;
    CustomTank customTank = null;
    CustomEnemy customEnemy;
    TMap tMap = null;
    Quadtree quadtree = new Quadtree(0,new Rectangle(0,0,600,600));
    Random random = new Random();
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    private String[] explode = {"img//blast1.gif","img//blast1.gif","img//blast1.gif","img//blast2.gif","img//blast2.gif","img//blast2.gif",
            "img//blast3.gif","img//blast3.gif","img//blast3.gif","img//blast4.gif","img//blast4.gif","img//blast4.gif",
            "img//blast5.gif","img//blast5.gif","img//blast5.gif","img//blast6.gif","img//blast6.gif","img//blast6.gif",
            "img//blast7.gif","img//blast7.gif","img//blast7.gif","img//blast8.gif","img//blast8.gif","img//blast8.gif"};
    int pages = 0;
    int pages_e = 0;

    public CustomGamingPanel(){
        super(null);
        this.addKeyListener(this);
    }

    public boolean loadFile(File file){
        tMap = new TMap(file);
        if(tMap!=null){
                return true;
            }else{
                return false;
        }
    }

    public boolean loadTank(File file){
        customTank = new CustomTank(file);
        if(customTank!=null){
            return true;
        }else{
            return false;
        }
    }

    public boolean loadEnemy(File file){
        customEnemy = new CustomEnemy(file);
        if(customEnemy!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(toolkit.getImage("timg.jpg"),0,0,null);
        if(tMap!=null){
            tMap.drawTMap(g);
            quadtree.clear();
            for(SingleOneTMap i : tMap.singelTMaps){
                quadtree.insert(new Rectangle(i.getX(),i.getY(),i.width,i.height));
            }
            List<Rectangle> returnObjects = new ArrayList<>();
            for(SingleOneTMap i : tMap.singelTMaps){
                returnObjects.clear();
                quadtree.retrieve(returnObjects,new Rectangle(i.getX(),i.getY(),i.width,i.height));
            }

//            for(SingleOneTMap i : returnObjects){
//
//            }
        }

        if(customTank!=null){
            //坦克绘制自己
            customTank.draw(g);
            customTank.walk(tMap.singelTMaps);
        }

        if(customEnemy!=null){
            customEnemy.draw(g);
            customEnemy.walk(tMap.singelTMaps);
            switch (random.nextInt(75)){
                case 0:customEnemy.setDir(Direction.UP);customEnemy.setImageIcon(customEnemy.enemy_url_up);
                    break;
                case 1:customEnemy.setDir(Direction.DOWN);customEnemy.setImageIcon(customEnemy.enemy_url_down);
                    break;
                case 2:customEnemy.setDir(Direction.LEFT);customEnemy.setImageIcon(customEnemy.enemy_url_left);
                    break;
                case 3:customEnemy.setDir(Direction.RIGHT);customEnemy.setImageIcon(customEnemy.enemy_url_right);
                    break;
                default:break;
            }
        }
        //draw player hp
        g.setColor(Color.BLACK);
        g.drawRect(customTank.position_x-1, customTank.position_y-21, 62, 12);
        g.setColor(Color.RED);
        g.fillRect(customTank.position_x, customTank.position_y-20, (int)((customTank.hp*60)/100), 10);

        //draw enemy hp
        g.setColor(Color.BLACK);
        g.drawRect(customEnemy.position_x-1, customEnemy.position_y-21, 62, 12);
        g.setColor(Color.RED);
        g.fillRect(customEnemy.position_x, customEnemy.position_y-20, (int)((customEnemy.hp*60)/100), 10);

        if(customEnemy.environmentAwareness(customTank.getPosition().x, customTank.getPosition().y)) {
            customEnemy.fire();
        }

        ArrayList<CustomBullet> player_bullet = new ArrayList<>();
        ArrayList<CustomBullet> enemy_bullet = new ArrayList<>();
        try {

            for (CustomBullet i : customTank.bullets) {
                i.fly();
                player_bullet.add(i);
                if (i.isLiveCustom(customEnemy.position_x, customEnemy.position_y, tMap.singelTMaps)) {
                    i.draw(g);
                } else {
                    customTank.bullets.remove(i);
                    customTank.bullets_to_explode.add(i);
                }
            }

            for (CustomBullet i : customEnemy.bullets) {
                i.fly();
                enemy_bullet.add(i);
                if (i.isLiveCustom(customTank.position_x, customTank.position_y, tMap.singelTMaps)) {
                    i.draw(g);
                } else {
                    customEnemy.bullets.remove(i);
                    customEnemy.bullets_to_explode.add(i);
                }
            }
        }catch (ConcurrentModificationException e){
            //ignore
        }

        if(pages<24) {
            for (CustomBullet i : customTank.bullets_to_explode) {
                ImageIcon ex = new ImageIcon(explode[pages]);
                g.drawImage(ex.getImage(), i.getPosition().x-30, i.getPosition().y-5, null);
            }
            pages+=1;
            if(pages == 24){
                pages = 0;
                customTank.bullets_to_explode.clear();
            }
        }

        if(pages_e<24){
            for(CustomBullet i : customEnemy.bullets_to_explode){
                ImageIcon e = new ImageIcon(explode[pages_e]);
                g.drawImage(e.getImage(),i.getPosition().x-30,i.getPosition().y-5,null);
            }
            pages_e+=1;
            if(pages_e == 24){
                pages_e = 0;
                customEnemy.bullets_to_explode.clear();
            }
        }

        for(CustomBullet i : player_bullet){
            if(i.isHitTank){
                customEnemy.cutHP(Bullet.damage);
                if(!customEnemy.getLife()){
                    customEnemy.beDead();
                }
            }
        }

        for(CustomBullet i : enemy_bullet){
            if(i.isHitTank){
                customTank.cutHP(Bullet.damage);
                if(!customTank.getLife()){
                    customTank.beDead();
                }
            }
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            System.out.println("调用");
            customTank.setImageIcon(customTank.tank_url_up);
            customTank.setDir(Direction.UP);
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            System.out.println("调用");
            customTank.setImageIcon(customTank.tank_url_down);
            customTank.setDir(Direction.DOWN);
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            System.out.println("调用");
            customTank.setImageIcon(customTank.tank_url_left);
            customTank.setDir(Direction.LEFT);
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            System.out.println("调用");
            customTank.setImageIcon(customTank.tank_url_right);
            customTank.setDir(Direction.RIGHT);

        }

        if(e.getKeyCode() == KeyEvent.VK_J){
            customTank.fire();
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(!pause){
                pause = true;
            }else{
                pause = false;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void init(File file){
        tMap = null;
        loadFile(file);
        customEnemy = new CustomEnemy(file);
        customTank = new CustomTank(file);
    }
}
