package tankwar;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.util.List;

public class CustomEnemy extends CustomTank {


    final String enemy_url_left = "img//enemy1L.gif";
    final String enemy_url_down = "img//enemy1D.gif";
    final String enemy_url_right = "img//enemy1R.gif";
    final String enemy_url_up = "img//enemy1U.gif";

    public CustomEnemy(File map) {
        super(map);
    }

    @Override
    public void initViaFile(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file); //读入文件
            Element root = document.getRootElement(); //获取根元素
            List<Element> elementList = root.elements(); //获取全部子元素存入链表
            for(Element i : elementList){
                //将element存入内存
                if(i.getName()=="Enemy") {
                    //初始化位置
                    int tx = Integer.parseInt(i.getText().split(",")[0]);
                    int ty = Integer.parseInt(i.getText().split(",")[1]);
                    setXY(tx*60,ty*60);
                    //初始化方向
                    switch (i.attributeValue("Direction")){
                        case "UP":setDir(Direction.UP);setImageIcon(enemy_url_up);break;
                        case "DOWN":setDir(Direction.DOWN);setImageIcon(enemy_url_down);break;
                        case "LEFT": setDir(Direction.LEFT);setImageIcon(enemy_url_left);break;
                        case "RIGHT":setDir(Direction.RIGHT);setImageIcon(enemy_url_right);break;
                        default:break;
                    }
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fire() {
        if(bullets.size() < this.bullets_max ) {
            bullet = new CustomBullet(position_x, position_y, dir, 1);
            bullet.setLife(true);
            bullets.add(bullet);
            try{
                FileInputStream in = new FileInputStream("img//fire.wav");
                BufferedInputStream buff = new BufferedInputStream(in);
                as = new AudioStream(buff);
                AudioPlayer.player.start(as);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean environmentAwareness(int x, int y) {
        boolean isFire = false;

        if( x < this.position_x && y == this.position_y ) {
            if(dir != Direction.LEFT) {
                this.setDir(Direction.LEFT);
                this.setImageIcon(enemy_url_left);
            }
            isFire = true;
        }else if( x == this.position_x && y < this.position_y) {
            if(dir != Direction.UP) {
                this.setDir(Direction.UP);
                this.setImageIcon(enemy_url_up);
            }
            isFire = true;
        }else if( x == this.position_x && y > this.position_y) {
            if(dir != Direction.DOWN) {
                this.setDir(Direction.DOWN);
                this.setImageIcon(enemy_url_down);
            }
            isFire = true;
        }else if( y == this.position_y && x > this.position_x ) {
            if(dir != Direction.RIGHT ) {
                this.setDir(Direction.RIGHT);
                this.setImageIcon(enemy_url_right);
            }
            isFire = true;
        }

        return isFire;
    }
}
