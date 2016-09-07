import java.awt.*;


public class Explode {
	int x,y;
	boolean live =  true;
	private TankClient tc;
	
	//int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6}; replaced by images
	int step = 0;
	public static Toolkit tk = Toolkit.getDefaultToolkit();
	public static boolean init = false;
	
	private static Image[] imgs = {
			tk.getImage(Explode.class.getClassLoader().getResource("0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("10.gif")),
	};
	
	public Explode(int x, int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		
		if(!init){
			for (int j = 0; j < imgs.length; j++) {
				g.drawImage(imgs[j], -100, -100, null);
			}
			
			init = true;
		}
		
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		if(step == imgs.length) {
			this.live = false;
			step = 0;
			return;
		}

		g.drawImage(imgs[step], x, y, null);
	
		
		step++;
	
	}
	
	
	
	

}
