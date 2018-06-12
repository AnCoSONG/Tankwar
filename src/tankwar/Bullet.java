package tankwar;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

/**
 * 
* @ClassName: Bullet 
* @Description: 子弹类根类 包含子弹的基本属性
* @author A18ccms a18ccms_gmail_com 
* @date 2018年3月27日 上午11:58:59 
*
 */
public class Bullet {
	
	static final int border_x = 560;
	static final int border_y = 560;
	
	private int position_x;
	private int position_y;
	Direction dir;
	protected boolean life = false;
	
	private String bullet_url = null;
	private String tank_url =  "img//tankmissile1.gif";
	private String enemy_url = "enemymissile.gif";
	/*private String[] explode = {"img//blast1.gif","img//blast2.gif","img//blast3.gif","img//blast4.gif","img//blast5.gif","img//blast6.gif",
			"img//blast7.gif","img//blast8.gif"};*/
	private int speed = 30;
	private ImageIcon bullet_image;
	//子弹类型
	private int type = 0;
	
	//计数器
	int up_status = 0;
	int down_status = 0;
	int left_status = 0;
	int right_status = 0;
	
	//子弹所在地图
	int map_type = 0;
	private static final int CITY_MAP = 0;
	private static final int SEA_MAP = 1;
	//倍率
	private int rate = (int)((60/speed)-1);
	
	public static final int damage = 10;
	
	
	/**
	 * <p>Title: 子弹</p> 
	 * <p>Description: 实例化子弹</p> 
	 * @param x 子弹x坐标
	 * @param y 子弹y坐标
	 * @param dir 子弹方向
	 * @param bullet_type 子弹类型 0 tank 1 enemy
	 */
	public Bullet(int x, int y,Direction dir,int bullet_type) {
		// TODO Auto-generated constructor stub
		setXY(x, y);
		setDir(dir);
		setBulletType(bullet_type);
		bullet_image = new ImageIcon(bullet_url);
	}
	/**
	 * 
	 * @Title: setBulletType 
	 * @Description: 设置子弹的类型
	 * @param @param bullet_type    0为普通
	 * @return void    返回类型 
	 * @throws 
	 * @param bullet_type
	 */
	private void setBulletType(int bullet_type) {
		// TODO Auto-generated method stub
		this.type = bullet_type;
		switch(type) {
		case 0: setURL(tank_url);
			break;
		case 1: setURL(enemy_url);
			break;
		default: break;
		}
	}
	
	public void setLife(boolean life) {
		this.life = life;
	}
	
	public boolean isLive(int x, int y, ArrayList<BaseSteel> list) {
		if(position_x < 0 || position_x > border_x | position_y < 0 || position_y > border_y ) {
			life = false;
		}else { 
			if (canMove(position_x, position_y)){
				life = !(isHit(x, y, list));
				//life = true;
		}else {
			life = false;
		}
		}
		return life;
		
	}

	private void setXY(int x, int y) {
		this.position_x = x;
		this.position_y = y;
	}
	
	public Point getPosition() {
		
		return new Point(position_x, position_y);
		
	}
	
	
	private void setURL(String url) {
		this.bullet_url = url;
	}
	
	protected Direction getDir() {
		return dir;
	}
	
	private void setDir(Direction dir) {
		this.dir = dir;
	}
	
	public ImageIcon getImageIcon() {
		return bullet_image;
	}
	
	public void fly() {
		switch(this.dir) {
		case LEFT: 
				//if(isLive())
					setXY(position_x-speed, position_y);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case RIGHT:
				//if(isLive())
					setXY(position_x+speed, position_y);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case DOWN:
				//if(isLive()) 
					setXY(position_x, position_y+speed);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case UP: 
				//if(isLive()) 
					setXY(position_x, position_y-speed);
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
	
	public boolean canMove(int x, int y) {
		boolean is_move = true;
		switch(map_type) {
		case CITY_MAP://大于减(60/speed-1)*speed 小于减speed
			if( ((x >= 120-rate*speed&&x<=360-speed) && (y >= 60-rate*speed && y <= 120-speed))
					|| ((x>=120-rate*speed&&x <= 180-speed)&&(y>=180-rate*speed&&y<=480-speed))
					|| ((x>=420-rate*speed&&x<=480-speed)&&(y>=60-rate*speed&&y<=360-speed))
					|| ((x>=240-rate*speed&&x<=480-speed)&&(y>=420-rate*speed&&y<=480-speed))) {
				is_move = false;
			}else is_move = true;
			break;
		case SEA_MAP:
			if( ((y>=60-speed*rate&&y<=180-speed)&&(x>=0-rate*speed&&x<=240-speed))
				||((y>=60-rate*speed&&y<=180-speed)&&(x>=360-rate*speed&&x<=600-speed))
				||((x>=180-rate*speed&&x<=300-speed)&&(y>=240-rate*speed&&y<=300-speed))
				||((x>=300-rate*speed&&x<=420-speed)&&(y>=300-rate*speed&&y<=360-speed))
				||((y>=420-rate*speed&&y<=540-speed)&&(x>=0-rate*speed&&x<=240-speed))
				||((y>=420-rate*speed&&y<=540-speed)&&(x>=360-rate*speed&&x<=600-speed))) {
				is_move = false;
			}else is_move = true;
			break;
		}
		return is_move;
	}
//	public boolean isHit(int x, int y) {//左边打 上边打有问题
//		boolean hit = false;
//		
//		if( (position_y >= y && position_y <= y + 60 )&& position_x == x + 60 ) { //从右边打
//			hit = true;
//		}else if ( (position_y >= y && position_y <= y + 60 ) && position_x == x ) { //从左边打
//			
//			hit = true;
//		}else if (( position_x >= x && position_x <= x + 60 ) && position_y == y  ) { //从上边打
//			hit = true;
//		}else if (( position_x >= x && position_x <= x + 60 ) && position_y == y + 60) { //从下边打
//			hit = true;
//		}else {
//			hit = false;
//		}
//		
//		return hit;
//		
//	}
	
	public boolean isHit(int x, int y, ArrayList<BaseSteel> list ) {
		boolean hit = false;
		
		if( //判断是否打中普通坦克
				((dir == Direction.RIGHT)&&(position_y >= y-10 && position_y <= y+50)&&(position_x >= x && position_x <= x + 50))//从左边打
				||((dir == Direction.LEFT)&&(position_y >= y-10 && position_y <= y+50)&&(position_x >= x && position_x <= x + 50))//从右边打
				||((dir == Direction.UP)&&(position_y >= y && position_y <= y+50)&&( position_x >= x-10 && position_x <= x + 50 )) //从下边打
				||((dir == Direction.DOWN)&&(position_y >= y && position_y <= y+50)&&( position_x >= x - 10 && position_x <= x + 50))  //从上边打
				)
			{
			hit = true;
		}else if(this.map_type == Map.CITY_MAP) { //判断是否打中基地墙
			hit = isHitBaseSteel(list);
		}
		return hit;
		
	}
	
	public void setMapType(int type) {
		map_type = type;
	}
	//算法有问题 ——》基本修复 但右上角块依然得上下右边打才能打掉 左边始终不行 ——》已解决 加入 list,remove即可！！
	public boolean isHitBaseSteel(ArrayList<BaseSteel> list) {
		boolean isHit = false;
		for(BaseSteel temp : list ) {
			int y = temp.getPosition().y;
			int x = temp.getPosition().x;
			if(!temp.isBreak) {
				if(
					((dir == Direction.RIGHT)&&(position_y >= y-30 && position_y <= y+0)&&(position_x >= x-20 && position_x <= x + 10))
					||((dir == Direction.LEFT)&&(position_y >= y-30 && position_y <= y+0)&&(position_x >= x-20 && position_x <= x + 10))//从右边打
					||((dir == Direction.UP)&&(position_y >= y && position_y <= y+30)&&( position_x >= x-30 && position_x <= x + 0 )) //从下边打
					||((dir == Direction.DOWN)&&(position_y >= y && position_y <= y+30)&&( position_x >= x-30 && position_x <= x + 0))
					) {
					temp.toBreak();
					list.remove(temp);//加上这一行之后可以正常的破坏
					System.out.println(temp.index+"号石块被打坏");
					isHit = true;
					break;
				}
			}
		}
		return isHit;
	}
}

