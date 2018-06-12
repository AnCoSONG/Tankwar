package tankwar;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Base {
	
	int x = 0;
	int y = 0;
	
	int city_x = 270;
	int city_y = 240;

	
	//基地是为城市地图设置 在周围加一圈墙;
	
	BaseSteel bs;//当被打中时将被破坏
	ArrayList<BaseSteel> bsList = new ArrayList<>();
	int bscount = 0;

	String url = "base.png";
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public Base() {
		bscount = 0;
		setXY();
		for(int i = -1; i < 3; i ++) {
			bs = new BaseSteel();
			bs.setIndex(bscount);
			bscount++;
			bs.setXY(this.x-30, this.y+30*i);
			bsList.add(bs);
		}
			
		for(int i = 0; i < 2 ; i ++ ) {
			bs = new BaseSteel();
			bs.setIndex(bscount);
			bscount++;
			bs.setXY(this.x+30*i, this.y+60);
			bsList.add(bs);
		}
		
		for(int i = -1; i < 3; i ++) {
			bs = new BaseSteel();
			bs.setIndex(bscount);
			bscount++;
			bs.setXY(this.x+60, this.y+30*i);
			bsList.add(bs);
		}
		
		for(int i = 0; i < 2 ; i ++ ) {
			bs = new BaseSteel();
			bs.setIndex(bscount);
			bscount++;
			bs.setXY(this.x+30*i, this.y-30);
			bsList.add(bs);
		}
	}

	public void setXY() {
			this.x = city_x;
			this.y = city_y;

	}
	
	public void drawBase(Graphics g) {
		g.drawImage(toolkit.getImage(url), this.x, this.y, null);
		for(BaseSteel t : bsList) {
			if(!t.isBreak) {
				g.drawImage(toolkit.getImage(t.urlToDraw), t.getPosition().x, t.getPosition().y, null);
			}
		}
	}
	
	public void initBase() {
		bsList = new ArrayList<>();
		setXY();
		for(int i = -1; i < 3; i ++) {
			bs = new BaseSteel();
			bs.setXY(this.x-30, this.y+30*i);
			bsList.add(bs);
		}
			
		for(int i = 0; i < 2 ; i ++ ) {
			bs = new BaseSteel();
			bs.setXY(this.x+30*i, this.y+60);
			bsList.add(bs);
		}
		
		for(int i = -1; i < 3; i ++) {
			bs = new BaseSteel();
			bs.setXY(this.x+60, this.y+30*i);
			bsList.add(bs);
		}
		
		for(int i = 0; i < 2 ; i ++ ) {
			bs = new BaseSteel();
			bs.setXY(this.x+30*i, this.y-30);
			bsList.add(bs);
		}
	}
	
	
}
