package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.FrameBorderStyle;


public class TankGamingPane extends JPanel{
	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -6013338473630693264L;

	String tip = "";
			
	Font font_tip = new Font("苹方 粗体", Font.PLAIN, 16);
	
	//地图类
	static final int city_map = 0;
	static final int sea_map = 1;
	
	Tools tools = new Tools(0);
	
	Map map = new Map(city_map);
	Base base = new Base();
	
	JLabel tips;
	
	JFrame gameover = new JFrame();
	
	@SuppressWarnings("unused")
	private String tank_url = "img//p1tankU.gif";
	@SuppressWarnings("unused")
	private String enemy_url = "img//enemy1D.gif";
	private String[] explode = {"img//blast1.gif","img//blast1.gif","img//blast1.gif","img//blast2.gif","img//blast2.gif","img//blast2.gif",
			"img//blast3.gif","img//blast3.gif","img//blast3.gif","img//blast4.gif","img//blast4.gif","img//blast4.gif",
			"img//blast5.gif","img//blast5.gif","img//blast5.gif","img//blast6.gif","img//blast6.gif","img//blast6.gif",
			"img//blast7.gif","img//blast7.gif","img//blast7.gif","img//blast8.gif","img//blast8.gif","img//blast8.gif"};
	int pages_t = 0;
	int pages_e = 0;
	//敌方坦克的图片链接
	String enemy_url_left = "img//enemy1L.gif";
	String enemy_url_down = "img//enemy1D.gif";
	String enemy_url_right = "img//enemy1R.gif";
	String enemy_url_up = "img//enemy1U.gif";

	//初始化坦克类
	Tank tank = new Tank(0,540,Direction.UP,0);
	EnemyTank enemyTank = new EnemyTank(540, 0,Direction.DOWN,1);
	//初始化toolkit
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	//记录己方坦克和敌方坦克位置
	int player_tank_x = tank.getPosition().x;
	int player_tank_y = tank.getPosition().y;
	int enemy_tank_x = enemyTank.getPosition().x;
	int enemy_tank_y = enemyTank.getPosition().y;
	
	Random random = new Random();
	
	
	//记录子弹位置
//	ArrayList<Point> player_bullet = new ArrayList<>(5);
//	ArrayList<Point> enemy_bullet = new ArrayList<>(5);
	
	public TankGamingPane() {
		// TODO Auto-generated constructor stub
		super();
		this.setLayout(null);
		this.setLookAndFeel();
		this.setBackground(Color.PINK);
		
		tips = new JLabel(tip);
		tips.setFont(font_tip);
		tips.setForeground(Color.WHITE);
		tips.setBounds(20, 20, 600, 30);
		this.add(tips);
//		tools = new Tools(map.map_type);
	}
	
	protected void paintComponent(Graphics g) {//已可以画出子弹并多发
		super.paintComponent(g);
		
		//draw 背景
		g.drawImage(toolkit.getImage("timg.jpg"), 0, 0, null);
		//draw MAP
		for(int i = 0; i < 10; i ++) {
			for(int j = 0; j < 10; j ++) {
				ImageIcon mapImage = new ImageIcon(map.hm.get(map.map[i][j]));
				g.drawImage(mapImage.getImage(),60*j, 60*i, null);
				}
		}
		
		//draw Base
		if(this.map.map_type == Map.CITY_MAP) {
			base.drawBase(g);
		}
		//draw 道具 
		switch(random.nextInt(100)) { //随机数使道具life属性变成true，从而可以画出道具，否则道具不会画出，只会在内存中
		case 0: tools.setLife(true);
			break;
		default:break;
		}
		if(tools.getLife()) {
			tools.drawTool(g);
			if(//这里的方法可以借鉴到bullet判断是否打中目标的方法上，详细可以参见tank isHit()方法
					(tank.getDir()==Direction.UP&&(player_tank_x>=tools.getXY().x-59&&player_tank_x<=tools.getXY().x+59)&(player_tank_y<=tools.getXY().y+59&&player_tank_y>=tools.getXY().y))
					||(tank.getDir()==Direction.DOWN&&(player_tank_x>=tools.getXY().x-59&&player_tank_x<=tools.getXY().x+59)&(player_tank_y<=tools.getXY().y&&player_tank_y>=tools.getXY().y-59))
					||(tank.getDir()==Direction.RIGHT&&(player_tank_x>=tools.getXY().x-59&&player_tank_x<=tools.getXY().x)&&(player_tank_y>=tools.getXY().y-59&&player_tank_y<=tools.getXY().y+59))
					||(tank.getDir()==Direction.LEFT&&(player_tank_x>=tools.getXY().x&&player_tank_x<=tools.getXY().x+59)&&(player_tank_y>=tools.getXY().y-59&&player_tank_y<=tools.getXY().y+59))
					) {
				tank.toolWork(tools.getToolType());
				tools = new Tools(map.map_type);
			}else if(
					(enemyTank.getDir()==Direction.UP&&(enemy_tank_x>=tools.getXY().x-59&&enemy_tank_x<=tools.getXY().x+59)&(enemy_tank_y<=tools.getXY().y+59&&enemy_tank_y>=tools.getXY().y))
					||(enemyTank.getDir()==Direction.DOWN&&(enemy_tank_x>=tools.getXY().x-59&&enemy_tank_x<=tools.getXY().x+59)&(enemy_tank_y<=tools.getXY().y&&enemy_tank_y>=tools.getXY().y-59))
					||(enemyTank.getDir()==Direction.RIGHT&&(enemy_tank_x>=tools.getXY().x-59&&enemy_tank_x<=tools.getXY().x)&&(enemy_tank_y>=tools.getXY().y-59&&enemy_tank_y<=tools.getXY().y+59))
					||(enemyTank.getDir()==Direction.LEFT&&(enemy_tank_x>=tools.getXY().x&&enemy_tank_x<=tools.getXY().x+59)&&(enemy_tank_y>=tools.getXY().y-59&&enemy_tank_y<=tools.getXY().y+59))
					) {
				enemyTank.toolWork(tools.getToolType());
				tools = new Tools(map.map_type);
			}
		}
		//这个针对的是从向上吃到道具
		
		
				
		//draw Tank
		//tank.setImageIcon(this.tank_url); 原来的方法是frame里改链接 paint里改图片 现在改为直接改图片 tank类中图片直接实例化 防止空指
		g.drawImage(tank.tank_icon.getImage(), tank.getPosition().x, tank.getPosition().y, null);
		//加上tank.move()即可自动行动 带方向的move其实更像转向
		tank.move(base.bsList);
		
		//DRAW  EnemyTank
		//enemyTank.setMapType(this.map.map_type);
		/*第一种方法
		//enemyTank.turn(); //turn方法只将dir和enemy_url对应的图片路径改变 enemyTank也不重写setImageIcon方法 
		//this.setEnemyURL(enemyTank.enemy_url); //设置这里的enemy_url为turn中设置好的EnemyTank中的enmey_url
		//enemyTank.setImageIcon(this.enemy_url); //enemytank的tank_icon为之前的新方向的icon
		 */
		//第二种方法 重写setImageIcon_e 并初始化
		g.drawImage(enemyTank.enemy_icon.getImage(), enemyTank.getPosition().x, enemyTank.getPosition().y, null);
		enemyTank.turn();
		enemyTank.move(base.bsList);
		//判断开火
		if(enemyTank.environmentAwareness(player_tank_x, player_tank_y)) {
			enemyTank.fire();
		}
		//重复确定位置
		player_tank_x = tank.getPosition().x;
		player_tank_y = tank.getPosition().y;
		enemy_tank_x = enemyTank.getPosition().x;
		enemy_tank_y = enemyTank.getPosition().y;
		
		System.out.println("\n"+"player_tank:"+player_tank_x+", "+player_tank_y);
		System.out.println("enemy_tank:"+enemy_tank_x+", "+enemy_tank_y);

		
		//draw player hp
		g.setColor(Color.BLACK);
		g.drawRect(player_tank_x-1, player_tank_y-21, 62, 12);
		g.setColor(Color.RED);
		g.fillRect(player_tank_x, player_tank_y-20, (int)((tank.hp*60)/100), 10);
		
		//draw enemy hp
		g.setColor(Color.BLACK);
		g.drawRect(enemy_tank_x-1, enemy_tank_y-21, 62, 12);
		g.setColor(Color.RED);
		g.fillRect(enemy_tank_x, enemy_tank_y-20, (int)((enemyTank.hp*60)/100), 10);
		
		//创建存储子弹位置的链表和索引
		int pbn = 0;
		int ebn = 0;
		ArrayList<Bullet> player_bullet = new ArrayList<>();
		ArrayList<Bullet> enemy_bullet = new ArrayList<>();
				
		//draw player bullet
		try {
		for(Bullet i : tank.bullet_list ) {
			i.setMapType(map.map_type);
			i.fly();
			player_bullet.add(pbn,i);
			System.out.println("player_bullet:"+i.getPosition());
			pbn+=1;
			if(i.isLive(enemy_tank_x, enemy_tank_y,base.bsList))
				//给子弹的获取位置加一个重载：可以根据坦克方向纠正子弹飞行位置基本和弹管相齐
				g.drawImage(i.getImageIcon().getImage(), i.getPosition().x+21 , i.getPosition().y+20, null);
			else { 
				tank.bullet_list.remove(i);
				tank.bullet_to_explode.add(i);
			}
			
		}
		}catch(ConcurrentModificationException e) {
			//ignore this 
		}
		
		//draw enemy bullet
		try {
			for(Bullet i: enemyTank.bullet_list ) {
				i.setMapType(map.map_type);
				i.fly();
				enemy_bullet.add(ebn, i);
				System.out.println("enemy_bullet:"+i.getPosition());
				ebn++;
				if(i.isLive(player_tank_x,player_tank_y,base.bsList)) {
					g.drawImage(i.getImageIcon().getImage(), i.getPosition().x+21 , i.getPosition().y+20, null);
				}
				else {
					enemyTank.bullet_list.remove(i);
					enemyTank.bullet_to_explode.add(i);
				}
			}
		}catch(ConcurrentModificationException e){
			//ignore this
		}
		//画出爆炸效果 同时 将 该Bullet从tank.bullet_list中remove
		if(pages_t<24) {
			for(Bullet t: tank.bullet_to_explode) {
				ImageIcon icon = new ImageIcon(explode[pages_t]);
				g.drawImage(icon.getImage(), t.getPosition().x-30, t.getPosition().y, null);
			}
			pages_t+=1;
			if(pages_t == 24) {
				pages_t = 0;
				tank.bullet_to_explode.clear();
			}
		}
		if(pages_e<24) {
			for(Bullet t: enemyTank.bullet_to_explode) {
				ImageIcon icon = new ImageIcon(explode[pages_e]);
				g.drawImage(icon.getImage(), t.getPosition().x-30, t.getPosition().y, null);
			}
			pages_e+=1;
			if(pages_e == 24) {
				pages_e = 0;
				enemyTank.bullet_to_explode.clear();
			}
		}
		
		if(base.bs.isBreak = false) {
			
		}
		
		
		
		//判断坦克是否被打并触发死亡等情况
		for(Bullet i: enemy_bullet) {
			if(tank.isHit(i.getDir(),i.getPosition().x, i.getPosition().y)) {
				tank.cutHP(Bullet.damage);
				System.out.println("tank.isHit方法认为击中了坦克");
				System.out.println("bullet:"+i.getDir().toString()+i.getPosition().x+", " + i.getPosition().y+"\n坦克:"+tank.getPosition());
				if(!tank.getLife()) {
					tank.beDead();
				}
			}
		}
		
		for(Bullet t: player_bullet) {
			if(enemyTank.isHit(t.getDir(),t.getPosition().x, t.getPosition().y)) {
				enemyTank.cutHP(Bullet.damage);
				System.out.println("enemy.isHit方法认为击中了坦克");
				System.out.println("bullet:"+t.getDir().toString()+t.getPosition().x+", " + t.getPosition().y+"\n敌方坦克:"+enemyTank.getPosition());
				if(!enemyTank.getLife()) {
					enemyTank.beDead();
				}
			}
		}
		
		//判断坦克是否走到了基地上
		//由于standOnBaseToWin自带判断所以不用加判断了
		if(map.map_type == city_map) {
			tank.standOnBaseToWin();
			enemyTank.standOnBaseToWin();
		}
		
		//再加上不能走

		
	}
	
	
	private void setLookAndFeel() {
		// TODO Auto-generated method stub
		try {
			BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setURL(String url) {
		tank_url = url;
	}
	
	@SuppressWarnings("unused")
	private void setEnemyURL(String url) {
		enemy_url = url;
	}
	
	public void initWholePane() {
		//玩家坦克初始化 
		//拟采用initTank的新方法来完整init
//		tank.bullet_list.clear();
//		tank.bullet_to_explode.clear();
//		tank.setXY(0, 540);
//		tank.setLife(true);
//		tank.setHP(100);
//		tank.setDir(Direction.UP);
//		this.setURL(tank.tank_url_up);
//		tank.initBulletsMax();
		tank.initTank();
		//敌方坦克初始化 采用链表 以解决以后敌方坦克数量多之后的问题
		enemyTank.initEnemyTank();
		//base初始化
		base.initBase();
		//道具随机
		tools = new Tools(map.map_type);
//		player_bullet.clear();
//		enemy_bullet.clear();
		//地图恢复
	}
	

}




