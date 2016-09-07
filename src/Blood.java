import java.awt.*;

public class Blood {
	
	int x, y, w, h;
	TankClient tc;
	
	private boolean live = true;
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public static final int WIDTH = 15;
	public static final int HEIGHT = 15;
	
	public static int step = 0;
	
	int[][] pos = {
			{300, 260}, {300, 270}, {320,280}, {340, 290}, {360, 300},{420, 310}, {440, 320}, {450, 330},
			{430, 315}, {410, 305}, {400, 300}, {385, 300}, {350, 290}, {340, 290}, {330, 285},
			{320, 280}, {300, 270}
					};
	
	public Blood(TankClient tc){
		this.tc = tc;
		x = pos[0][0];
		y = pos[0][1];
		w = WIDTH;
		h = HEIGHT;
	}
	
	public void draw(Graphics g){
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		move();
		
	}

	private void move() {
		step++;
		if(step >= pos.length){
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
		
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
	
	

}
