
import java.awt.Color;

/**
 * This is the oval model class that extends model class
 * which helps create an oval shape model
 * @author Ao Sun
 */
public class OvalModel extends Model {
	
	int positionX;
	int positionY;
	int width;
	int height;
	
	/**
	 * initialize parameters for Oval Model except the basic ones which are initialized
	 * in the abstract class
	 * @param width
	 * @param height
	 * @param posX
	 * @param posY
	 * @param bX
	 * @param bY
	 * @param spX
	 * @param spY
	 * @param mC
	 */
	public void initOval(int width, int height, int posX, int posY, int bX, int bY, int spX, int spY, Color mC){
		super.init(bX, bY, spX, spY, mC);
		this.width = width;
		this.height = height;
		this.positionX = posX + width;
		this.positionY = posY + height;
	}
	
	/**
	 * changes position of x and y every step
	 */
	public void makeOneStep(){
		 
	        // Do the work
	        this.positionX += speedX;
	        if (positionX < width || positionX >= xLimit) {
	            speedX = -speedX;
	            positionX += speedX;
	        }
	        positionY += speedY;
	        if (positionY < 0 || positionY >= yLimit) {
	            speedY = -speedY;
	            positionY += speedY;
	        }
	        // Notify observers
	        setChanged();
	        notifyObservers();
	}
	
	/**
	 * generate reflect for the original shape
	 */
	@Override
	public Model reflect(int refType){
		OvalModel tempModel = new OvalModel();
		tempModel.initOval(width, height,positionX-width, positionY-width, xLimit, yLimit, speedX, speedY, modelColor);
		tempModel.generateReflection(refType);
		return tempModel;
	}
	
	public void generateReflection(int refType){
		if(refType == 1)
		{
			this.positionX = positionX;
			this.positionY = yLimit - positionY +height;
		}
		if(refType == 2){
			positionX = xLimit - positionX +width;
			positionY = positionY;
		}
		if(refType == 3){
			positionX = xLimit - positionX +width;
			positionY = yLimit - positionY +height;
		}
		if(refType == 4){
			int tempX = (positionY) * xLimit / yLimit;
			int tempY = (positionX) * yLimit / xLimit;
			this.positionX = tempX;
			this.positionY = tempY;
		}
		if(refType == 5){
			int tempX = (yLimit - positionY +height) * xLimit / yLimit;
			int tempY = (positionX) * yLimit / xLimit;
			this.positionX= tempX;
			this.positionY = tempY;
		}
		if(refType == 6){
			int tempY = (xLimit - positionX +width) * yLimit / xLimit;
			int tempX = (positionY) * xLimit / yLimit;
			this.positionX= tempX;
			this.positionY = tempY;
		}
		if(refType == 7){
			int tempX = (yLimit - positionY + height) * xLimit / yLimit;
			int tempY = (xLimit - positionX + width) * yLimit /xLimit;
			this.positionX =tempX;
			this.positionY = tempY;
		}	
	}
	
	/**
	 * check limits with regard to the bound
	 */
	public void checkLimits()
	{
		if(this.positionX > xLimit)
			positionX = xLimit;
		if(this.positionY > yLimit)
			positionY = yLimit;
		if(this.positionX < width)
			positionX = width;
		if(this.positionY <height)
			positionY = height;
	}
	
	/**
	 * getter method for getting X value
	 */
	public int getX(){
		return positionX - width;
	}
	
	/**
	 * getter method for getting Y value
	 */
	public int getY(){
		return positionY - height;
	}
	
}
