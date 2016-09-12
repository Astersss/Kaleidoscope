import java.awt.Color;

/**
 * Polygon model extends the Model abstract class and help build polygon including 
 * triangle, square, rectangle., etc.
 * @author Ao Sun
 *
 */
public class PolygonModel extends Model {
	int[] positionXarr;
	int[] positionYarr;
	int pointNum;
	int width;
	int height;
	boolean modelSolid = false;
	
	/**
	 * initialize the attributes of a polygon
	 * @param Xarr
	 * @param Yarr
	 * @param pnum
	 * @param xLimit
	 * @param yLimit
	 * @param speedX
	 * @param speedY
	 * @param mC
	 */
	public void initPolygon(int[] Xarr, int[] Yarr,int pnum, int xLimit, int yLimit, int speedX, int speedY, Color mC)
	{
		if(pnum != Xarr.length || pnum != Yarr.length || pnum < 3)
			throw new ArithmeticException();
		super.init(xLimit, yLimit, speedX, speedY, mC);
		positionXarr = copyArray(Xarr);
		positionYarr = copyArray(Yarr);
		this.pointNum = pnum;
		width = arrayMax(positionXarr) - arrayMin(positionXarr);
		height = arrayMax(positionYarr) - arrayMin(positionYarr);
		
	}
	
	/**
	 * set modelSolid to be true or false
	 * @param solid
	 */
	public void setSolid(boolean solid){
		this.modelSolid = solid;
	}
	
	/**
	 * find the maximum length of the polygon
	 * @param arr
	 * @return maximum length of the polygon
	 */
	public int arrayMax(int[] arr){
		int tempMax = arr[0];
		for( int i: arr){
			if(tempMax < i)
				tempMax = i;
		}
		return tempMax;
	}
	
	/**
	 * find the minimum length of the polygon
	 * @param arr
	 * @return minimum length of the polygon
	 */
	public int arrayMin(int[] arr){
		int tempMin = arr[0];
		for( int i: arr){
			if(tempMin > i)
				tempMin = i;
		}
		return tempMin;
	}
	
	/**
	 * get a copy array for the original array
	 * @param arr
	 * @return a copied array
	 */
	public static int[] copyArray(int[] arr){
		int[] dest = new int[arr.length];
		if(arr.length != dest.length){
			throw new ArithmeticException();
		}
		for(int i=0 ; i<arr.length; i++){
			dest[i] = arr[i];
		}
		return dest;
	}
	
	/**
	 * override makeOneStep class declared in Model class
	 * here I define two arrays, each of which helps save the position x and y
	 * of the vertex of the polygon.
	 */
	@Override
	public void makeOneStep(){
		this.positionXarr = plus(this.positionXarr, speedX);
		this.positionYarr = plus(this.positionYarr, speedY);
		if(arrayMax(positionXarr) > xLimit || arrayMin(positionXarr) <0){
			speedX = -speedX;
			this.positionXarr = plus(this.positionXarr, speedX);
		}
		if(arrayMax(positionYarr) > yLimit || arrayMin(positionYarr) <0){
			speedY = -speedY;
			this.positionYarr = plus(this.positionYarr, speedY);
		}
		
	}
	
	/**
	 * getter method to get the positionXarr
	 */
	@Override
	public int getX(){
		return arrayMin(positionXarr);
	}
	
	/**
	 * getter method to get the positionYarr
	 */
	@Override
	public int getY(){
		return arrayMin(positionYarr);
	}
	
	
	/**
	 * check limits for the model
	 */
	@Override
	public void checkLimits(){
		int maxX = arrayMax(positionXarr);
		int maxY = arrayMax(positionYarr);
		int minX = arrayMin(positionXarr);
		int minY = arrayMin(positionYarr);
		if(maxX > xLimit) plus(positionXarr, -maxX + xLimit);
		if(maxY > yLimit) plus(positionYarr, -maxY + yLimit);
		if(minX < 0 ) plus(positionXarr, -minX);
		if(minY < 0 ) plus(positionYarr, -minY);
	}
	
	/**
	 * plus each number in the input array with the input number
	 * @param arr
	 * @param num
	 * @return the new array with each elements in the original array added with the 
	 * input number
	 */
	private static int[] plus(int[] arr, int num){
		int[] copy = new int[arr.length];
		for(int i=0; i<arr.length; i++){
			copy[i] = arr[i] +num;
		}
		return copy;
		
	}
	
	/**
	 * multiply each number in the input array with the input number
	 * @param arr
	 * @param num
	 * @return the new array with each elements in the original array multiplied with the 
	 * input number
	 */
	public static int[] multiply(int[] arr, int num){
		int[] newArr = new int[arr.length];
		for(int i=0; i<arr.length; i++){
			newArr[i] = arr[i]* num;
		}
		return newArr;
	}
	
	/**
	 * divide each number in the input array with the input number
	 * @param arr
	 * @param num
	 * @return the new array with each elements in the original array divided by the 
	 * input number
	 */
	private static int[] divide(int[] arr, int num){
		int[] copy = new int[arr.length];
		for(int i=0; i<arr.length; i++){
			copy[i] = arr[i] / num;
		}
		return copy;
	}
	
	/**
	 * generate an array with each element to be the negative value of that in
	 * the original array
	 * @param arr
	 * @return a new array
	 */
	private static int[] getNegativeArray(int[] arr){
		int[] newArr = new int[arr.length];
		for(int i=0; i<arr.length; i++){
			newArr[i] = -arr[i];
		}
		return newArr;
	}
	
	public int[] getXArray(){
		return copyArray(this.positionXarr);
	}
	
	public int[] getYArray(){
		return copyArray(this.positionYarr);
	}
	
	public int getPointNum(){
		return this.pointNum;
	}
	
	/**
	 * do reflection of the model
	 */
	@Override
	public Model reflect(int refType){
		Model tempModel = new PolygonModel();
		((PolygonModel) tempModel).initPolygon(copyArray(positionXarr), copyArray(positionYarr), pointNum, 
				xLimit, yLimit, speedX, speedY, modelColor);
		((PolygonModel) tempModel).makeReflection(refType);
		return tempModel;
	}
	
	/**
	 * make the 7 reflection image of a single imput model image
	 * @param Type
	 */
	public void makeReflection(int Type){
		if(Type == 1){
			positionYarr = plus(getNegativeArray(positionYarr), yLimit);
		}
		if(Type == 2){
			positionXarr = plus(getNegativeArray(positionXarr), xLimit);
		}
		if(Type == 3){
			positionXarr = plus(getNegativeArray(positionXarr), xLimit);
			positionYarr = plus(getNegativeArray(positionYarr), yLimit);
		}
		if(Type == 4){
			int[] tmpx = divide(multiply(positionYarr, xLimit), yLimit);
			int[] tmpy = divide(multiply(positionXarr, yLimit), xLimit);
			this.positionXarr = tmpx;
			this.positionYarr = tmpy;
		}
		
		if(Type == 5){
			//plus(getNegativeArray(this.positionYarr),yLimit);
			int[] tmpx = divide(multiply(plus(getNegativeArray(this.positionYarr),yLimit),xLimit),yLimit);
			int[] tmpy = divide(multiply(this.positionXarr, yLimit),xLimit);
			this.positionXarr = tmpx;
			this.positionYarr = tmpy;
		}
		
		if(Type == 6){
			int[] tmpx = divide(multiply(this.positionYarr, xLimit), yLimit);
			//plus(getNegativeArray(this.positionXarr),xLimit);
			int[] tmpy = divide(multiply(plus(getNegativeArray(this.positionXarr),xLimit),yLimit),xLimit);
			this.positionXarr = tmpx;
			this.positionYarr = tmpy;
		}
		
		if(Type == 7){
			//plus(getNegativeArray(this.positionYarr),yLimit);
			int[] tmpx = divide(multiply(plus(getNegativeArray(this.positionYarr),yLimit), xLimit),yLimit);
			//plus(getNegativeArray(this.positionXarr),xLimit);
			int[] tmpy = divide(multiply(plus(getNegativeArray(this.positionXarr),xLimit),yLimit),xLimit);
			this.positionXarr = tmpx;
			this.positionYarr = tmpy;
		}
		
	}
	
	
	
	
	
}
