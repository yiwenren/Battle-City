import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	
	public static Random r = new Random();
	
	private BloodBar bb = new BloodBar();
	
	//add pictures for tanks
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	public static Image[] tankImgs = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	static{
		tankImgs = new Image[] {
			tk.getImage(Explode.class.getClassLoader().getResource("tankL.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankLU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankRU.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankR.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankRD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankD.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("tankld.gif"))
		};
		imgs.put("L", tankImgs[0]);
		imgs.put("LU", tankImgs[1]);
		imgs.put("U", tankImgs[2]);
		imgs.put("RU", tankImgs[3]);
		imgs.put("R", tankImgs[4]);
		imgs.put("RD", tankImgs[5]);
		imgs.put("D", tankImgs[6]);
		imgs.put("LD", tankImgs[7]);
		
		
		
	}
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private boolean live = true;
	
	private int life = 100;
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private int step = r.nextInt(12) + 3;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	TankClient tc;
	Direction ptDir = Direction.D;
	
	
	private boolean bL=false, bU=false, bR=false, bD=false; //stands for whether the four buttons have been pressed
	
	
	int x, y, oldX, oldY;
	
	private boolean good;
	
	public boolean isGood() {
		return good;
	}

	Direction dir = Direction.STOP;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc){
		this(x, y, good);//call the other constructor
		this.tc = tc;
		this.dir = dir;
		
	}
	
	
	
	public void draw(Graphics g){
		if(!this.live) {
			if(!good) {
				tc.tanks.remove(this);
			} 
			return; //if the tank is not alive, then won't draw it.
		}
		
		
		//draw blood bar for our tank
		if(good) {
			bb.draw(g);
		}
		
		//draw gun barrel
		switch(ptDir){
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
		case STOP:
			break;
		}
		
		move();
	}
	
	public void move(){
		oldX = x;
		oldY = y;
		//control the moves of enemy tanks
		if(!good){
			if(step == 0){
				Direction[] dirs = Direction.values();
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
				step = r.nextInt(12)+3;
			}
			step--;
			
			if(r.nextInt(50)>46){
				this.fire();
			}
	
		}
		
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
		case STOP:
			break;
		
		}
		
		if(x < 0) x = 0;
		if(y < 20) y = 20;
		if(x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT; 
		
		
		if(dir != Direction.STOP){
			ptDir = dir;
		}	
	}
	
	public void locateDirection(){
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		if(bL && bU && !bR && !bD) dir = Direction.LU;
		if(!bL && bU && !bR && !bD) dir = Direction.U;
		if(!bL && bU && bR && !bD) dir = Direction.RU;
		if(!bL && !bU && bR && !bD) dir = Direction.R;
		if(!bL && !bU && bR && bD) dir = Direction.RD;
		if(!bL && !bU && !bR && bD) dir = Direction.D;
		if(bL && !bU && !bR && bD) dir = Direction.LD;
		if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}
	
	public void KeyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		
		case KeyEvent.VK_LEFT: bL = true; break;
		case KeyEvent.VK_UP: bU = true; break;
		case KeyEvent.VK_RIGHT: bR = true; break;
		case KeyEvent.VK_DOWN: bD= true; break;
		
		}
		
		locateDirection(); //if press key button, the direction changes
	}
 

	public void KeyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_Z: {
			fire();
			break;
		}
		case KeyEvent.VK_LEFT: bL = false; break;
		case KeyEvent.VK_UP: bU = false; break;
		case KeyEvent.VK_RIGHT: bR = false; break;
		case KeyEvent.VK_DOWN: bD= false; break;
		case KeyEvent.VK_A: this.superFire(); break;
		case KeyEvent.VK_F2: this.live = true; this.life = 100; break;
		
		}
		
		locateDirection(); //if release key button, the direction changes
	}

	public Missile fire(){
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2;
		int y = this.y + Tank.WIDTH/2;
		Missile m = new Missile(x, y, good, ptDir, tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir){
		if(!live) return null;
		int x = this.x + Tank.WIDTH/2;
		int y = this.y + Tank.WIDTH/2;
		Missile m = new Missile(x, y, good, dir, tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public void stays(){
		x = oldX;
		y = oldY;
	}
	
	public boolean tankHitsWall(Wall w){
		if(this.isLive() && this.getRect().intersects(w.getRect())){
			this.stays();
			return true;
		}
		return false;
	}
	
	public boolean hitsTank(java.util.List<Tank> tanks){
		for(int i = 0; i <tanks.size(); i++){
			Tank t = tanks.get(i);
			if(this.live && t.isLive() && this != t && this.getRect().intersects(t.getRect())){
				this.stays();
				t.stays(); 
				return true;
			}
		}
		return false;
	}
	
	public void superFire(){
		Direction[] dirs = Direction.values();
		for(int i = 0; i < dirs.length - 1; i++){
			this.fire(dirs[i]);
		}
	}
	
	private class BloodBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.red);
			g.drawRect(x+7, y - 10, WIDTH + 3, 10);
			g.fillRect(x+7, y - 10, (WIDTH + 3)*life/100, 10);
			g.setColor(c);
		}
	}
	
	public boolean eat(Blood bb){
		if(live && bb.isLive() && this.getRect().intersects(bb.getRect())){
			bb.setLive(false);
			this.life = 100;
			return true;
		}
		return false;
	}
	
}
