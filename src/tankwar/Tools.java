package tankwar;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Random;

public class Tools {
	
	int x = 0;
	int y = 0;
	Random rand = new Random();
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	boolean life = false;
	
	int map_type = 0;
	final static int map_city = 0;
	final static int map_sea = 1;
	
	private int tool_type = 0;
	static final int ammo_up = 0;
	static final int hp_up = 1;
	
	String url = null;
	final String url_ammo_up = "img2//AmmoUp.png";
	final String url_hp_up = "img2//HpUp.png";
	
	
	public Tools(int map_type) {
		setMapType(map_type);
		setLife(false);
		
		switch(rand.nextInt(2)) {
		case 0:
			setToolType(ammo_up);
			break;
		case 1:
			setToolType(hp_up);
			break;
		}
		setUrl();
		randomXY();
	}
	/**
	 * 
	 * <p>Title: 有参构造</p> 
	 * <p>Description: 提供道具放置的地图类型并设置生命状态和随机位置,创建之后调用drawTools即可画出道具</p> 
	 * @param map_type 地图类型 
	 * @param tool_type 道具类型
	 */
	public Tools(int map_type, int tool_type) {
		setToolType(tool_type);
		setUrl();
		setMapType(map_type);
		setLife(false);
		randomXY();
		}

	protected void setUrl(String url) {
		this.url = url;
	}
	
	protected void setUrl() {
		switch(tool_type) {
		case ammo_up: 
			this.url = url_ammo_up;
			break;
		case hp_up:
			this.url = url_hp_up;
			break;
		}
	}
	public void drawTool(Graphics g) {
		g.drawImage(toolkit.getImage(url), x, y, null);
	}
	
	public void randomXY() {
		switch(map_type) {
		case map_sea :
			y = rand.nextInt(160)+200;
			x = rand.nextInt(120);
			break;
		case map_city :
			switch(rand.nextInt(4)) {
			case 0:  //左边
				y = rand.nextInt(541);
				x = rand.nextInt(61);
				break;
			case 1:
				y = rand.nextInt(541);
				x = rand.nextInt(61)+480;
				break;
			case 2:
				y = 0;
				x = rand.nextInt(540);
				break;
			case 3:
				y = rand.nextInt(61)+480;
				x = rand.nextInt(540);
				break;
			}
			break;
		}
	}
	
	public void setMapType(int map_type) {
		this.map_type = map_type;
	}
	
	public int getMapType() {
		return this.map_type;
	}
	
	public void setLife(boolean life) {
		this.life = life;
	}
	
	public boolean getLife() {
		return this.life;
	}
	
	public void setToolType(int type) {
		this.tool_type = type;
	}
	
	public Point getXY() {
		return new Point(this.x,this.y);
	}
	
	public int getToolType() {
		return this.tool_type;
	}
	

}
