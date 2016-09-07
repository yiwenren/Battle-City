import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Missile {
	public static int num = 1;
	
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH =10;
	public static final int HEIGHT = 10;
	
	//add pictures for missiles
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image[] missileImgs = {
			tk.getImage(Missile.class.getClassLoader().getResource("missileD.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileL.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileLD.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileLU.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileR.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileRD.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileRU.gif")),
			tk.getImage(Missile.class.getClassLoader().getResource("missileU.gif")),
			
	};
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static{
		imgs.put("D", missileImgs[0]);
		imgs.put("L", missileImgs[1]);
		imgs.put("LD", missileImgs[2]);
		imgs.put("LU", missileImgs[3]);
		imgs.put("R", missileImgs[4]);
		imgs.put("RD", missileImgs[5]);
		imgs.put("RU", missileImgs[6]);
		imgs.put("U", missileImgs[7]);
	}
	
	
	int x, y;
	Direction dir;
	
	private boolean live = true; //show or not show this missile
	
	private boolean good;
	
	private TankClient tc;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc){
		this(x,y,dir);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		if(!this.live) tc.missiles.remove(this);
		
		
		switch(dir){
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;	
		}
		
		move();
		
		
	}

	private void move() {
		switch(dir){
		case L: 
			x -= XSPEED; 
			break;
		case LU: 
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		
		//judge whether to show the missile
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT){
			live = false;
			tc.missiles.remove(this); //not showing this missile if it is out of bound
		}
		
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank(Tank t){
		if(this.isLive() && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()){
			if(t.isGood()){
				t.setLife(t.getLife() - 20);
				if(t.getLife() <= 0) 
					t.setLive(false);	
			} else {
				t.setLive(false);
			}
			this.setLive(false);
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
				
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i = 0; i< tc.tanks.size(); i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public boolean missileHitWall(Wall w){
		if(this.isLive() && this.getRect().intersects(w.getRect())){
			this.setLive(false);
			tc.missiles.remove(this);
			return true;	
		}
		return false;
	}
	
	

}
