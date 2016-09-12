
import java.awt.Color;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import java.util.Timer;



/**
 * This is the Model class for kaleidoscope. It is an Observable,
 * which means that it can notifyObservers that something in the
 * model has changed, and they should take appropriate actions.
 * 
 * @author Ao Sun
 */
abstract public class Model extends Observable {
   
    protected int xLimit, yLimit;
    private Timer timer;
    Color modelColor;
    int speedX;
    int speedY;
    int MAX_SPEED = 15;
    boolean randomMove = false;
    
    /**
     * initialize common parameters
     * @param bX
     * @param bY
     * @param speedX
     * @param speedY
     * @param mC
     */
    public void init(int bX, int bY, int speedX, int speedY, Color mC)
    {
    	this.xLimit = bX;
    	this.yLimit = bY;
    	this.speedX = speedX;
    	this.speedY = speedY;
    	this.modelColor = mC;
    }
    
    /**
     * declare an abstract method which helps check limits for
     * the models
     */
    abstract public void checkLimits();
    
    /**
     * set the randomMove parameters to be what the input is
     * @param setRand
     */
    public void setRandomMove(boolean setRand)
    {
    	this.randomMove = setRand;
    }

	/**
     * Sets the "walls" that the model should bounce off from.
     * 
     * @param xLimit The position (in pixels) of the wall on the right.
     * @param yLimit The position (in pixels) of the floor.
     */
    public void setLimits(int xLimit, int yLimit) {
//        this.xLimit = xLimit - BALL_SIZE;
//        this.yLimit = yLimit - BALL_SIZE;
    	this.xLimit = xLimit;
    	this.yLimit = yLimit;
//        xPosition = Math.min(xPosition, xLimit);
//        yPosition = Math.min(yPosition, yLimit);
    	checkLimits();
    }
    
    /**
     * set the maximum speed
     * @param spMax
     */
    public void setMaxSpeed(int spMax)
    {
    	 this.MAX_SPEED = spMax;
    }
    
    /**
     * abstract class called makeOneStep which will be implemented in the 
     * concret class
     */
    abstract public void makeOneStep();
    
    abstract public int getX();
    abstract public int getY();
    
    

    /**
     * Tells the ball to start moving. This is done by starting a Timer
     * that periodically executes a TimerTask. The TimerTask then tells
     * the ball to make one "step."
  **/
    public void start()
	{
		timer = new Timer(true);
	    timer.schedule(new Strobe(), 0, 40);
	}
	
    
    /**
     * Tells the ball to stop where it is.
     */
    public void stop() {
        timer.cancel();
    }
    
    public boolean isStarted()
	{
	//	timer
		return false;
	}
    
    public void randomMove()
	{
		if(randomMove)
		{
			Random rand = new Random();
			int randInt = rand.nextInt(100);
			if(randInt == 0)//randomly change speed
			{
				changeSpeed();
			}
			if(rand.nextInt(1000) == 1)
			{
				fastSpeed();
			}
		}
	}
    
    public int abs(int n)
	{
		if(n < 0) return - n;
		else return n;
	}
	
	public int sign(int n)
	{
		return (n >= 0)? 1: -1;
	}
	
	public void fastSpeed()
	{
		int currentMax, currentMin;
		if(speedX >= speedY)
		{
			currentMax = speedX;
			currentMin = speedY;
		}
		else 
		{
			currentMax = speedY;
			currentMin = speedX;
		}
		if(2 * abs(currentMax) > MAX_SPEED && abs(currentMax) < MAX_SPEED)
		{
			speedX = (int)((float)speedX *  ((float) MAX_SPEED / (float) abs(currentMax)));
			speedY = (int)((float)speedY *  ((float) MAX_SPEED / (float) abs(currentMax)));
		}
		else if(2 * abs(currentMax) < MAX_SPEED)
		{
			speedX = 2 * speedX;
			speedY = 2 * speedY;
		}

		
		if(speedX == 0 && speedY == 0)
		{
			speedX = 1;
			speedY = 1;
		}
	//	IS_FAST = true;
	}
	
	public void slowSpeed()
	{
		int tempSpeedX = speedX / 2;
		int tempSpeedY = speedY / 2;
		if(tempSpeedX == 0 && speedX != 0) tempSpeedX = sign(speedX);
		if(tempSpeedY == 0 && speedY != 0) tempSpeedY = sign(speedY);
		speedX = tempSpeedX;
		speedY = tempSpeedY;
	//	IS_FAST = false;
	}
    
   
    abstract public Model reflect(int refType);
    
    public void changeColor()
    {
    	Color randColor;
    	Random rand = new Random();
    	int randInt = rand.nextInt(7);
    	
    	switch(randInt)
    	{
    		case 0: randColor = Color.BLUE; break;
    		case 1: randColor = Color.RED; break;
    		case 2: randColor = Color.YELLOW; break;
    		case 3: randColor = Color.GREEN; break;
    		case 4: randColor = Color.CYAN; break;
    		case 5: randColor = Color.ORANGE; break;
    		case 6: randColor = Color.PINK; break;
    		default: randColor = modelColor;
    		
    	}
    	modelColor = randColor;
    }
    
    public void changeSpeed(){
    	int randSpeedX, randSpeedY;
    	int times = 0;
    	Random rand = new Random();
    	int randIntX = rand.nextInt(MAX_SPEED) - MAX_SPEED / 2 + speedX;
    	int randIntY = rand.nextInt(MAX_SPEED) - MAX_SPEED / 2 + speedY;
    	while( randIntX * randIntX + randIntY * randIntY > MAX_SPEED * MAX_SPEED && times< 10){
    		randIntX = rand.nextInt(MAX_SPEED) - MAX_SPEED / 2 + speedX;
			randIntY = rand.nextInt(MAX_SPEED) - MAX_SPEED / 2 + speedY;
			times++; 
    	}
    	if(times < 10)
		{
			speedX = randIntX / 2;
			speedY = randIntY / 2;
		}
		if(speedX == 0 && speedY == 0)
		{
			speedX = rand.nextInt(2);
			speedY = rand.nextInt(2);
		}
    }

    private class Strobe extends TimerTask
	{
		@Override
		public void run()
		{
			randomMove();
			makeOneStep();
		}
	}
}