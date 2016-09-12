import java.awt.Color;

/**
 * This is the rectangle model class that extends model class
 * which helps create a rectangle model
 * @author aosun
 *
 */
public class RectModel extends OvalModel {
	
	/**
	 * initialize the rectangle parameters
	 * @param width
	 * @param height
	 * @param posX
	 * @param posY
	 * @param bX
	 * @param bY
	 * @param speedX
	 * @param speedY
	 * @param mC
	 */
	public void initRect(int width, int height, int posX, int posY,
			int bX, int bY, int speedX, int speedY, Color mC)
	{
		super.initOval(width, height, posX, posY, bX, bY, speedX, speedY, mC);
	}
	
	/**
	 * do reflection of the model, same as the ovalModel
	 */
	@Override
	public Model reflect(int refType)
	{
		Model tmpModel = new RectModel();
		((RectModel) tmpModel).initRect(width, height, positionX - width, positionY - height,
			xLimit, yLimit, speedX, speedY,  modelColor);
		((OvalModel) tmpModel).generateReflection(refType);
		return tmpModel;
	}
}
