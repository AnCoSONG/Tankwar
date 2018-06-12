package tankwar;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomTank extends Tank {

    final String tank_url_up = "img//p1tankU.gif";
    final String tank_url_down = "img//p1tankD.gif";
    final String tank_url_left = "img//p1tankL.gif";
    final String tank_url_right = "img//p1tankR.gif";

    ArrayList<CustomBullet> bullets;
    ArrayList<CustomBullet> bullets_to_explode;
    CustomBullet bullet;

    public CustomTank(File map) {
        super(0, 0, Direction.UP, 0);
        initViaFile(map);
        bullets = new ArrayList<>();
        bullets_to_explode = new ArrayList<>();
    }

    /**
     * @param x              下一步的x
     * @param y              下一步的y
     * @param singleOneTMaps
     * @return 可否通过
     */
    public boolean canPass(int x, int y, ArrayList<SingleOneTMap> singleOneTMaps) {
        //方法是检测两块中心处位置相距是否小于60，小于则相撞 该方法甚至不需要方向
        if(x<0||x>540||y<0||y>540){
            return false;
        }else {
            int center_x = x + 30;
            int center_y = y + 30;
            boolean canPass = true;

            //这里是检测代码
            for (SingleOneTMap i : singleOneTMaps) {
                if(!i.isCanPass()) {
                    int center_mx = i.getX() + 30;
                    int center_my = i.getY() + 30;

                    if (Math.abs(center_x - center_mx) < 60 && Math.abs(center_y - center_my) < 60) {
                        canPass = false;
                        break;
                    } else {
                        canPass = true;
                    }
                }else{
                    canPass = true;
                }

            }
            return canPass;
        }


    }

    public void walk(ArrayList<SingleOneTMap> singleOneTMaps){
        switch(this.dir) {
            case LEFT:
                if(isLive&&canPass(position_x-speed, position_y,singleOneTMaps)) {
                    System.out.println("leftcanpass");
                    setXY(position_x-speed, position_y);
                }
                try {
                    Thread.sleep(SleepTime.TIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case RIGHT:
                if(isLive&&canPass(position_x+speed, position_y,singleOneTMaps)) {
                    System.out.println("rightcanpass");
                    setXY(position_x + speed, position_y);
                }
                try {
                    Thread.sleep(SleepTime.TIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case DOWN:
                if(isLive&&canPass(position_x, position_y+speed,singleOneTMaps)) {
                    System.out.println("downcanpass");
                    setXY(position_x, position_y+speed);
                }
                try {
                    Thread.sleep(SleepTime.TIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case UP:
                if(isLive&&canPass(position_x, position_y-speed,singleOneTMaps)) {
                    System.out.println("upcanpass");
                    setXY(position_x, position_y-speed);
                }
                try {
                    Thread.sleep(SleepTime.TIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Deprecated
    public void turn(Direction direction){
        last_dir = this.dir;
        this.dir = direction;
        if(last_dir == this.dir) {
            switch(this.dir) {

                case LEFT:
                    setDir(Direction.LEFT);
                    try {
                        Thread.sleep(SleepTime.TIME);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                case RIGHT:
                    setDir(Direction.RIGHT);
                    try {
                        Thread.sleep(SleepTime.TIME);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case DOWN:
                    setDir(Direction.DOWN);
                    try {
                        Thread.sleep(SleepTime.TIME);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case UP:
                    setDir(Direction.UP);
                    try {
                        Thread.sleep(SleepTime.TIME);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    public void initViaFile(File file){
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file); //读入文件
            Element root = document.getRootElement(); //获取根元素
            List<Element> elementList = root.elements(); //获取全部子元素存入链表
            for(Element i : elementList){
                //将element存入内存
                if(i.getName()=="Tank") {
                    //初始化位置
                    int tx = Integer.parseInt(i.getText().split(",")[0]);
                    int ty = Integer.parseInt(i.getText().split(",")[1]);
                    setXY(tx*60,ty*60);
                    //初始化方向
                    switch (i.attributeValue("Direction")){
                        case "UP":setDir(Direction.UP);setImageIcon(tank_url_up);break;
                        case "DOWN":setDir(Direction.DOWN);setImageIcon(tank_url_down);break;
                        case "LEFT": setDir(Direction.LEFT);setImageIcon(tank_url_left);break;
                        case "RIGHT":setDir(Direction.RIGHT);setImageIcon(tank_url_right);break;
                        default:break;
                    }
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics graphics){
        graphics.drawImage(tank_icon.getImage(),position_x,position_y,null);
    }


    @Override
    public void fire() {
        if(bullets.size() < this.bullets_max ) {
            bullet = new CustomBullet(position_x, position_y, dir, 0);
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
}
