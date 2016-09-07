import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;



public class TankClient extends Frame{
	
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 600;
	
	Tank tk = new Tank(100, 150, true, Direction.STOP, this);
	Wall w = new Wall(200, 100, 150, 20, this);
	Wall w2 = new Wall(500, 500, 150, 20, this);
	List<Tank> tanks = new ArrayList<Tank>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	Blood bb = new Blood(this);
	
	
	
	int x = 50;
	int y = 50;
	Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		
		if(tanks.size() <= 0){
			for(int i = 0; i < PropertyMgr.getProperty("reproduceTankCount"); i++){
				Tank k = new Tank(x + 40*i, y, false, Direction.D, this);
				tanks.add(k);
				k.draw(g);
			}
		}
		
		g.drawString("missiles count: " + missiles.size(), 500, 60);
		g.drawString("explode count: " + explodes.size(), 500, 80);
		g.drawString("tanks count: " + tanks.size(), 500, 100);
		g.drawString("tank  life: " + tk.getLife(), 500, 110);
		
		//missile
		for(int i = 0; i < missiles.size(); i++){
			Missile m =missiles.get(i);
			m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(tk);
			m.missileHitWall(w);
			//another kind of removing the missile out of bound
			//if(!m.isLive()) missiles.remove(m);
			//else m.draw(g);	
		}
		
		for(int i = 0; i < explodes.size(); i++){
			explodes.get(i).draw(g);
		}
		
		//tank
		tk.draw(g);
		tk.tankHitsWall(w2);
		tk.eat(bb);
		for(int i=0; i<tanks.size(); i++){
			tanks.get(i).tankHitsWall(w);
			tanks.get(i).hitsTank(tanks);
			tanks.get(i).draw(g);

		}
		w.draw(g);
		w2.draw(g);
		
		bb.draw(g);
		
		
	}
	

	@Override
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);	
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);//draw at offScreen image
		g.drawImage(offScreenImage, 0, 0, null);//draw at front image
		
		
		
	}





	public void launchFrame(){
		//get tank config information from property files
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		//new enemy
		for(int i=1; i<=PropertyMgr.getProperty("initTankCount"); i++){
			Tank t = new Tank(x + 40*i, y, false, Direction.D, this);
			tanks.add(t);
		}
		
		setLocation(400, 300);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		this.setResizable(false);
		this.setBackground(Color.black);
		
		this.addKeyListener(new KeyMonitor());
		setVisible(true);
		
		new Thread(new PaintThread()).start();;
		
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyReleased(KeyEvent e) {
			
			tk.KeyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			tk.KeyPressed(e);
		}
		
	}
	
	private class PaintThread implements Runnable{
		
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	public static void main(String args[]){
		TankClient tc = new TankClient();
		tc.launchFrame();
		
	}
}