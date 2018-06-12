package tankwar;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 
* @ClassName: Tank 
* @Description: 坦克类根类 包含坦克基本的信息
* @author AnCo
* @date 2018年3月27日 上午11:53:02 
* 
*
 */
public class Tank {
	
	//坦克本身的属性
	//边界
	static final int border_x = 540;
	static final int border_y = 550;
	//有位置
	protected int position_x = 50;
	protected int position_y = 50;
	//有方向
	Direction dir = Direction.UP;
	Direction last_dir;
	//有存活状态
	protected boolean isLive = false;
	//有子弹
	Bullet bullet;
	ArrayList<Bullet> bullet_list;
	ArrayList<Bullet> bullet_to_explode;
	//有是否撞墙状态
	private boolean isMoveable = true;
	//有速度
	protected int speed = 3;
	
	//开火声音文件
	AudioStream as;
	
	//创建事件源
	protected TankLiveListener listener;
	protected TankWinListener winListener;
	
	//坦克图片
	ImageIcon tank_icon = new ImageIcon("img//p1tankU.gif");
	String tank_url = "img//p1tankU.gif" ;
	String tank_url_up = "img//p1tankU.gif";
	String tank_url_down = "img//p1tankD.gif";
	String tank_url_left = "img//p1tankL.gif";
	String tank_url_right = "img//p1tankR.gif";
	
	//获取坦克所在的地图类
	int map_type = 0;
	static final int CITY_MAP = 0;
	static final int SEA_MAP = 1;
	
	//子弹发射数量
	protected int bullets_max = 1;//默认只能单发 可以通过吃道具增加发弹数量

	//子弹类型
	protected int bullet_type;
	protected final int bullet_tank = 0;
	protected final int bullet_enemy = 1;
	
	//血量 初始为100
	protected int hp = 100;
	
	
	//调整倍率
	private int rate = (int)((60/speed)-1);
	//创建监听器方法
	public void addTankLiveListener(TankLiveListener listener) {
		this.listener = listener;
	}
	//创建可以触发监听器的方法
	public void beDead() {
		if(listener != null) {
			Even even = new Even(this);
			this.listener.dead(even);
		}
	}
	
	public Tank() {
		//无参构造
	}

	/**
	 *
	 * @param x x绘制坐标
	 * @param y y绘制坐标
	 * @param dir 方向
	 * @param bullet_type 0 is tankbullet and 1 is enemybullet
	 */
	public Tank(int x ,int y,Direction dir,int bullet_type) {
		setXY(x,y);
		setDir(dir);
		setLife(true);
		setBulletType(bullet_type);
		bullet = new Bullet(position_x, position_y, dir, this.bullet_type);
		bullet_list = new ArrayList<Bullet>();
		bullet_to_explode = new ArrayList<Bullet>();
	}
	
	protected void setBulletType(int bullet_type) {
		// TODO Auto-generated method stub
		this.bullet_type = bullet_type;
	}

	//setUrl
	protected void setUrl(String url){
		this.tank_url = url;
	}
	
	//tank
	public void moveInNoGamePanel() {
		// TODO Auto-generated method stub
		switch(this.dir) {
		
		case LEFT: 
					setXY(position_x-speed, position_y);
				
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case RIGHT:
					setXY(position_x+speed, position_y);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case DOWN:
					setXY(position_x, position_y+speed);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case UP: 
					setXY(position_x, position_y-speed);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
	}
}

	
	
	//画出tank后调用这个方法可以自动前进！！
	public void move(ArrayList<BaseSteel> list) {
		// TODO Auto-generated method stub
		switch(this.dir) {
		
		case LEFT: 
				if(isLive&&isMove(map_type,position_x-speed, position_y,list)) {
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
				if(isLive&&isMove(map_type,position_x+speed, position_y,list))
					setXY(position_x+speed, position_y);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case DOWN:
				if(isLive&&isMove(map_type,position_x, position_y+speed,list)) {
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
				if(isLive&&isMove(map_type,position_x, position_y-speed,list)) {
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
	/**
	 * 
	 * @Title: setXY 
	 * @Description: 设置坦克位置
	 * @param @param x x坐标位置
	 * @param @param y y坐标位置
	 * @return void    返回类型 
	 * @throws 
	 * @param x
	 * @param y
	 */
	public void setXY(int x,int y) {
		this.position_x = x;
		this.position_y = y;
	}
	
	public Point getPosition() {
		
		return new Point(position_x, position_y);
		
	}
	
	public void addBullet() {
		this.bullets_max += 1;
	}
	
	public void reduceBullet() {
		if(bullets_max > 1)
			bullets_max -= 1;
	}
	
	public void setImageIcon(String url) {
		tank_icon = new ImageIcon(url);
	}
	
	public void setLife(boolean life) {
		isLive = life;
	}
	
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	
	public void move(Direction dir,ArrayList<BaseSteel> list) {
		last_dir = this.dir;
		this.dir = dir;
		if(last_dir == this.dir) {
		switch(this.dir) {
		
		case LEFT: 
					if(isLive&&isMove(map_type,position_x-speed, position_y,list)) {
						setXY(position_x-speed, position_y);
					}
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		case RIGHT:
				if(isLive&&isMove(map_type,position_x+speed, position_y,list))
					setXY(position_x+speed, position_y);
				try {
					Thread.sleep(SleepTime.TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case DOWN:
				if(isLive&&isMove(map_type,position_x, position_y+speed,list)) {
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
				if(isLive&&isMove(map_type,position_x, position_y-speed,list)) {
					setImageIcon(tank_url_up);
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
	}
	public Direction getDir() {
		return dir;
	}
	
	public boolean isMove(int type,int x, int y, ArrayList<BaseSteel> list) {
		if(x < 0 || x >border_x || y < 0 || y > border_y) {
			isMoveable = false;
		}else {
			isMoveable = canMove(type, x, y,list);
		}
		return isMoveable;
	}
	
	public void setMapType(int type) {
		this.map_type = type;
	}
	
	public void addHP(int num) {
		this.hp+=num;
		if(this.hp>100) {
			this.hp = 100;
		}
	}
	
	public void addHP() {
		this.hp += 20;
		if(this.hp > 100) {
			this.hp = 100;
		}
	}
	
	public void cutHP(int damage) {
		this.hp -= damage;
	}
	
	public void cutHP() {
		this.hp -= 10;
	}
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public boolean getLife() {
		if(this.hp <= 0) {
			this.isLive = false;
		}else {
			this.isLive = true;
		}
		return this.isLive;
	}
	@Deprecated 
	public boolean isHit(int x, int y) {//已弃用
		boolean hit = false;

		if( (position_y >= y && position_y <= y + 45 )&& position_x == x + 50 ) { //从右边打
			hit = true;
		}else if ( (position_y >= y && position_y <= y + 45 ) && position_x == x-10 ) { //从左边打
			hit = true;
		}else if (( position_x >= x && position_x <= x + 45 ) && position_y == y  ) { //从上边打
			hit = true;
		}else if (( position_x >= x && position_x <= x + 45 ) && position_y == y + 50) { //从下边打
			hit = true;
		}else {
			hit = false;
		}
		return hit;
		
	}
		
	
	public boolean isHit(Direction dir, int x, int y) {
		boolean hit = false;
		
		if(
				(dir == Direction.RIGHT&&(y >= position_y-10 && y <= position_y+50)&&(x >= position_x && x <= position_x + 50))//从左边打
				||((dir == Direction.LEFT)&&(y >= position_y-10 && y <= position_y+50)&&(x >= position_x && x <= position_x + 50))//从右边打
				||((dir == Direction.UP)&&(y >= position_y && y <= position_y+50)&&( x >= position_x-10 && x <= position_x + 50 )) //从下边打
				||((dir == Direction.DOWN)&&(y >= position_y && y <= position_y+50)&&( x >= position_x - 10 && x <= position_x + 50))  //从上边打
				)
			{
			hit = true;
		}
		return hit;
	}
	
	
	public void fire() {	
		//播放开火声音
		if(bullet_list.size() < this.bullets_max ) {
			bullet = new Bullet(position_x, position_y, dir, bullet_type);
			bullet.setLife(true);
			bullet_list.add(bullet);
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
	
	public boolean canMove(int type, int x, int y,ArrayList<BaseSteel> list) {
		boolean is_move = true;
		switch(type) {
		case CITY_MAP://大于减(60/speed-1)*speed 小于减speed
			if( ((x >= 120-rate*speed&&x<=360-speed) && (y >= 60-rate*speed && y <= 120-speed))
					|| ((x>=120-rate*speed&&x <= 180-speed)&&(y>=180-rate*speed&&y<=480-speed))
					|| ((x>=420-rate*speed&&x<=480-speed)&&(y>=60-rate*speed&&y<=360-speed))
					|| ((x>=240-rate*speed&&x<=480-speed)&&(y>=420-rate*speed&&y<=480-speed))
					) {
				is_move = false;
			}else is_move = !cannotPassBaseSteel(x,y,list);
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
	
	public boolean cannotPassBaseSteel(int x, int y, ArrayList<BaseSteel> list) { //待优化 模仿canMove进行优化
		boolean isBlock = false;
		for(BaseSteel temp : list ) {
			int y1 = temp.getPosition().y;
			int x1 = temp.getPosition().x;
			if(!temp.isBreak) {
				if(
					((x >= x1 - speed*rate && x <= x1 + 30 - speed)&&(y >= y1 - rate*speed&& y <= y1 + 30 - speed))
					) {
					//list.remove(temp);
					System.out.println(temp.index+"号石块挡住了坦克");
					isBlock= true;
					break;
				}
			}
		}
		return isBlock;
	}
	
	public void toolWork(int tool_type) {
		switch(tool_type) {
		case Tools.ammo_up:
			this.addBullet();
			break;
		case Tools.hp_up:
			this.addHP();
			break;
		}
	}
	
	public void initTank() {
		this.bullet_list.clear();
		this.bullet_to_explode.clear();
		this.setXY(0, 540);
		this.setLife(true);
		this.setHP(100);
		this.setDir(Direction.UP);
		this.setUrl(tank_url_up);
		this.setImageIcon(tank_url);
		this.initBulletsMax();
	}
	
	public void initBulletsMax() {
		this.bullets_max = 1;
	}
	//创建添加监听器方法
	public void addTankWinListener(TankWinListener winListener) {
		this.winListener = winListener;
	}
	//创建可以触发监听器的方法
	public void standOnBaseToWin() { //一走到边缘就会触发...想要的效果是走到里面一点再触发 下一步要加入根据不同方向夺旗的判断
		if(map_type == CITY_MAP) {
			if( (position_x >= 270 && position_x <= 330)&&( position_y >=  240 && position_y <= 300) ) {
				if(winListener != null) {
					WinEvent event = new WinEvent(this);
					this.winListener.win(event);
				}
			}
		}
	}
	

}

class EnemyTank extends Tank{
	
	//初始化随机数
	Random random = new Random();

	//敌方难度
	Difficulty difficulty = Difficulty.Fool;
	
	
	
	String enemy_url = "img//enemy1D.gif";
	String enemy_url_left = "img//enemy1L.gif";
	String enemy_url_down = "img//enemy1D.gif";
	String enemy_url_right = "img//enemy1R.gif";
	String enemy_url_up = "img//enemy1U.gif";
	ImageIcon enemy_icon = new ImageIcon("img//enemy1D.gif");
	
	//敌方坦克子弹
	
	public EnemyTank(int x, int y, Direction dir,int bullet_type) {
	
		super(x,y,dir,bullet_type);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @Title: turn 
	 * @Description: 转向方法
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public Direction turn() {
		switch(difficulty) {
		case Fool: 
			switch(random.nextInt(50)) {
			case 1: setDir(Direction.UP); this.setUrl(enemy_url_up);this.setImageIcon_e(this.enemy_url);break;
			case 2: setDir(Direction.RIGHT); this.setUrl(enemy_url_right);this.setImageIcon_e(this.enemy_url);break;
			case 3: setDir(Direction.LEFT); this.setUrl(enemy_url_left);this.setImageIcon_e(this.enemy_url);break;
			case 4: setDir(Direction.DOWN); this.setUrl(enemy_url_down);this.setImageIcon_e(this.enemy_url);break;
			default: break;
			}
			break;
		default: break;
		}
		return dir;
	}
	
	public void turn(Direction dir) {
		setDir(dir);
		switch(dir) {
		case UP: this.setUrl(enemy_url_up); this.setImageIcon_e(this.enemy_url);break;
		case DOWN:this.setUrl(enemy_url_down);this.setImageIcon_e(this.enemy_url);break;
		case LEFT:this.setUrl(enemy_url_left);this.setImageIcon_e(this.enemy_url);break;
		case RIGHT:this.setUrl(enemy_url_right);this.setImageIcon_e(this.enemy_url);break;
		}
	}
	
	@Override
	protected void setUrl(String url){
		this.enemy_url = url;
	}
	
	public void setImageIcon_e(String url) {
		enemy_icon = new ImageIcon(url);
	}
	
	/**
	 * 
	 * @Title: environmentAwareness 
	 * @Description: 环境感知系统 第一种 只对敌方位置进行获取，从而得到是否要开火的信号
	 * @param x 对方坦克位置x
	 * @param y 对方坦克位置y
	 * @param @return    设定文件 
	 * @return boolean    返回类型 是否开火
	 * @throws 
	 * @return
47*/
	public boolean environmentAwareness(int x, int y) {
		boolean isFire = false;
		
		if( x < this.position_x && y == this.position_y ) {
			if(dir != Direction.LEFT) {
				this.turn(Direction.LEFT);
			}
			isFire = true;
		}else if( x == this.position_x && y < this.position_y) {
			if(dir != Direction.UP) {
				this.turn(Direction.UP);
			}
			isFire = true;
		}else if( x == this.position_x && y > this.position_y) {
			if(dir != Direction.DOWN) {
				this.turn(Direction.DOWN);
			}
			isFire = true;
		}else if( y == this.position_y && x > this.position_x ) {
			if(dir != Direction.RIGHT ) {
				this.turn(Direction.RIGHT);
			}
			isFire = true;
		}
		
		return isFire;
	}
	
	public void setDifficulty(Difficulty d) {
		difficulty = d;
	}
	
	public void initEnemyTank() {
		this.setXY(540, 0);
		this.setLife(true);
		this.setHP(100);
		this.setDir(Direction.DOWN);
		this.setUrl(enemy_url_down);
		this.setImageIcon_e(enemy_url);
		this.bullet_list.clear();
		this.bullet_to_explode.clear();
		this.initBulletsMax();
	}
	
}
//事件监听器
interface TankLiveListener {
	public void dead(Even even);
}

//创建事件对象
class Even {
	private Tank tank;
	private EnemyTank enemyTank;
	
	public Even(EnemyTank enemyTank) {
		super();
		this.enemyTank = enemyTank;
	}
	
	public Even(Tank tank) {
		super();
		this.tank = tank;
	}
	
	public Even() {
		super();
	}

	
	public Tank getTank() {
		return this.tank;
	}
	
	public EnemyTank getEnemyTank() {
		return this.enemyTank;
	}
	
	public void setTank(Tank tank) {
		this.tank = tank;
	}
	
	public void setEnemyTank(EnemyTank enemyTank) {
		this.enemyTank = enemyTank;
	}	
}

interface TankWinListener{
	public void win(WinEvent event);
}

class WinEvent{
	private Tank tank;
	private EnemyTank enemyTank;
	
	public WinEvent(EnemyTank enemyTank) {
		super();
		this.enemyTank = enemyTank;
	}
	
	public WinEvent(Tank tank) {
		super();
		this.tank = tank;
	}
	
	public WinEvent() {
		super();
	}
	
	public Tank getTank() {
		return this.tank;
	}
	
	public EnemyTank getEnemyTank() {
		return this.enemyTank;
	}
	
	public void setTank(Tank tank) {
		this.tank = tank;
	}
	
	public void setEnemyTank(EnemyTank enemyTank) {
		this.enemyTank = enemyTank;
	}
}