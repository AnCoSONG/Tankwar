package tankwar;

import java.awt.Point;
import java.util.HashMap;

/**
 * 
* @ClassName: Map 
* @Description: Map的根类 定义map的基本属性
* @author AnCo 
* @date 2018年4月3日 上午10:32:33 
*
 */
public class Map {
	
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
	//定义map二维数组
	int[][] map = null;
	private int[][] map_city = 
			{{0,0,0,0,0,0,0,0,0,0},
			 {0,0,2,2,2,2,0,2,0,0},
			 {0,0,0,0,0,0,0,2,0,0},
			 {0,0,2,0,0,0,0,2,0,0},
			 {0,0,2,0,0,0,0,2,0,0},
			 {0,0,2,0,0,0,0,2,0,0},
			 {0,0,2,0,0,0,0,0,0,0},
			 {0,0,2,0,2,2,2,2,0,0},
			 {0,0,0,0,0,0,0,0,0,0},
			 {0,0,0,0,0,0,0,0,0,0}};
	
	private int[][] map_sea = 
			{{0,0,0,0,0,0,0,0,0,0},
			 {5,5,5,5,0,0,5,5,5,5},
			 {5,5,5,5,0,0,5,5,5,5},
			 {0,0,0,0,0,0,0,0,0,0},
			 {0,0,0,5,5,0,0,0,0,0},
			 {0,0,0,0,0,5,5,0,0,0},
			 {0,0,0,0,0,0,0,0,0,0},
			 {5,5,5,5,0,0,5,5,5,5},
			 {5,5,5,5,0,0,5,5,5,5},
			 {0,0,0,0,0,0,0,0,0,0}};
	
	//设置地图类型
	int map_type = 0;
	static final int CITY_MAP = 0;
	static final int SEA_MAP = 1;
	
	//hashMap
	HashMap<Integer,String> hm = new HashMap<>(6);
	
	public Map() {
		
	}

	public Map(int type) {
		// 
		setMapType(type);
		hm.put(Integer.valueOf(steel), steel_url);
		hm.put(Integer.valueOf(steels), steels_url);
		hm.put(Integer.valueOf(wall), wall_url);
		hm.put(Integer.valueOf(walls), walls_url);
		hm.put(Integer.valueOf(water), water_url);
		hm.put(Integer.valueOf(grass), grass_url);
		//建立hashmap
	}
	
	//设置地图类型,绘制地图的过程是在GamingPane内执行的
	public void setMapType(int type) {
		switch(type) {
		case CITY_MAP: map = map_city; map_type = type;break;
		case SEA_MAP: map = map_sea; map_type = type;break;
		default: break;
		}
	}
	@Deprecated
	public boolean canMove(int type, int x, int y) {
		boolean is_move = true;
		switch(type) {
		case CITY_MAP:
			if( ((x >= 120&&x<=360) && (y >= 60 && y <= 120))
					|| ((x>=120&&x <= 180)&&(y>=180&&y<=480))
					|| ((x>=420&&x<=480)&&(y>=60&&y<=360))
					|| ((x>=240&&x<=480)&&(y>=420&&y<=480))) {
				is_move = false;
			}else is_move = true;
			break;
		case SEA_MAP:
			if( ((y>=60&&y<=180)&&(x<=240&&x>=0))
				||((y>=60&&y<=180)&&(x>=360&&x<=600))
				||((x>=180&&x<=300)&&(y>=240&&y<=300))
				||((x>=300&&x<=420)&&(y>=300&&y<=360))
				||((y>=420&&y<=540)&&(x>=0&&x<=240))
				||((y>=420&&y<=540)&&(x>=360&&x<=600))) {
				is_move = false;
			}else is_move = true;
			break;
		}
		return is_move;
	}

}

class BaseSteel{
	
	boolean isBreak = false;
	
	int index = 0;
	
	private int x = -30;
	private int y = -30;
	
	String urlToDraw = "img//steel.gif";
	
	public BaseSteel () {
		
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void toBreak() {
		isBreak = true;
		setDrawUrl("");
	}
	
	public void setDrawUrl (String url) {
		this.urlToDraw = url;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point getPosition() {
		return new Point(x, y);
	}
	
}

