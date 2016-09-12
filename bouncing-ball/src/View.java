import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;


/**
 * The View "observes" and displays what is going on in the Model.
 * In this example, the Model contains oval, rectangle and polygon shape.
 * 
 * @author David Matuszek, modified by swapneel
 */
public class View extends JPanel implements Observer {
    
    /** This is what we will be observing. */
   // private Model model;
    private Model[] models;
    private Color bgColor;
    private int[] reflectTypes;
   
    
    /**
     * contructer for view class
     * @param model
     */
    public View(Model[] model) {
    	this.models = model;
    	this.bgColor = Color.BLACK;
    	reflectTypes  = new int[] {1,0,0,0,0,0,0,0};
    }
    
    /**
     * change background color
     * @param background
     */
    public void changeBGC(Color background)
    {
    	bgColor = background;
    }
    
    /**
     * set the reflection type of an image according to demand
     * @param refType
     */
    public void setReflection(int refType)
    {
    	reflectTypes[refType] =1;
    }
    
    /**
     * cancel the reflection type of the model image
     * @param refType
     */
    public void cancelReflection(int refType)
    {
    	reflectTypes[refType] =0;
    }
    

//    @Override
//    public void paint(Graphics g) {
////    	System.out.println(SwingUtilities.isEventDispatchThread());
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.RED);
//        g.fillOval(model.getX(), model.getY(),
//                    model.BALL_SIZE, model.BALL_SIZE); 
//    }
    
    @Override
    public void paint(Graphics g) {
//    	System.out.println(SwingUtilities.isEventDispatchThread());
       // g.setColor(Color.WHITE);
    	g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        paintFigures(g);
       
//        double x = getWidth() / 2;
//        double y = getHeight() / 2;
//        
//        g.setColor(Color.RED);
//        g.fillOval(model.getX(), model.getY(),
//                    model.BALL_SIZE, model.BALL_SIZE); 
    }
    
    public void paintFigures(Graphics g)
    {
    	for(int i=0; i<models.length; i++){
    		if(models[i] != null && models[i].getClass() == OvalModel.class)
    		{
    			OvalModel tempModel = (OvalModel) models[i];
    			g.setColor(tempModel.modelColor);
    			for(int j = 0; j < reflectTypes.length; j++)
    			{
    				if(reflectTypes[j] ==1)
    				{
    					OvalModel reflectModel = (OvalModel) tempModel.reflect(j);
    					g.fillOval(reflectModel.getX(), reflectModel.getY(), reflectModel.width, reflectModel.height);
    				}
    			}
    		}
    		if(models[i] != null && models[i].getClass() == RectModel.class)
    		{
    			RectModel tempModel = (RectModel) models[i];
    			g.setColor(tempModel.modelColor);
    			for(int j = 0; j < reflectTypes.length; j++)
    			{
    				if(reflectTypes[j] ==1)
    				{
    					RectModel reflectModel = (RectModel) tempModel.reflect(j);
    					g.fillOval(reflectModel.getX(), reflectModel.getY(), reflectModel.width, reflectModel.height);
    				}
    			}
    		}
    		if(models[i] != null && models[i].getClass() == RectModel.class)
    		{
    			RectModel tempModel = (RectModel) models[i];
    			g.setColor(tempModel.modelColor);
    			for(int j = 0; j < reflectTypes.length; j++)
    			{
    				if(reflectTypes[j] ==1)
    				{
    					RectModel reflectModel = (RectModel) tempModel.reflect(j);
    					g.fillRect(reflectModel.getX(), reflectModel.getY(), reflectModel.width, reflectModel.height);
    				}
    			}
    		}
    		if(models[i] != null && models[i].getClass() == PolygonModel.class)
    		{
    			PolygonModel tempModel = (PolygonModel) models[i];
    			g.setColor(tempModel.modelColor);
    			for(int j = 0; j < reflectTypes.length; j++)
    			{
    				if(reflectTypes[j] ==1)
    				{
    					PolygonModel reflectModel = (PolygonModel) tempModel.reflect(j);
    					g.fillPolygon(reflectModel.getXArray(), reflectModel.getYArray(), reflectModel.getPointNum());
    				}
    			}
    		}
    	}
    	
    	if(models[0] ==null){
    		g.setColor(bgColor);
    		g.fillRect(0, 0, getWidth(), getHeight());
    	}
    	
    }

    /**
     * When an Observer notifies Observers (and this View is an Observer),
     * this is the method that gets called.
     * 
     * @param obs Holds a reference to the object being observed.
     * @param arg If notifyObservers is given a parameter, it is received here.
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable obs, Object arg) {
//    	System.out.println(SwingUtilities.isEventDispatchThread());
        repaint();
    }
}