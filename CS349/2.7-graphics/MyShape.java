/*
*  MyShape: See ShapeDemo2 for an example how to use this class.
*
*/
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.vecmath.*;


// simple shape class
class MyShape {

    // shape model
    ArrayList<Point2d> points;
    Boolean isFilled = true; // shape is polyline or polygon
    Boolean isClosed = true; // polygon is filled or not
    Color colour = Color.BLACK;
	float strokeThickness = 3.0f;
    public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
    public float getStrokeThickness() {
		return strokeThickness;
	}
	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}
	public Boolean getIsFilled() {
		return isFilled;
	}
	public void setIsFilled(Boolean isFilled) {
		this.isFilled = isFilled;
	}
	public Boolean getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	// for selection
	boolean isSelected;
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	// for drawing
    Boolean hasChanged = false; // dirty bit if shape geometry changed
    int[] x_points, y_points;

	public float rotation = 0;
	public double scale = 1.0;

    // replace all points with array
    public void setPoints(double[][] pts) {
        points = new ArrayList<Point2d>();
        for (double[] p : pts) {
            points.add(new Point2d(p[0],p[1]));
        }
        hasChanged = true;
    }
    
    // add a point to end of shape
    public void addPoint(double x, double y) {
    	if (points == null)
    		points = new ArrayList<Point2d>();
    	points.add(new Point2d(x,y));
    	hasChanged = true;
    }

    
    // paint the shape
    public void paint(Graphics2D g2) {

        //update the shape in java Path2D object if it changed
        if (hasChanged) {
            x_points = new int[points.size()];
            y_points = new int[points.size()];
            for (int i=0; i < points.size(); i++) {
                x_points[i] = (int)points.get(i).x;
                y_points[i] = (int)points.get(i).y;
            }
            hasChanged = false;
        }

        //don't draw if path2D is empty (not shape)
        if (x_points != null) {
        	
        	// special draw for selection
        	if (isSelected) {
        		g2.setColor(Color.YELLOW);
        		g2.setStroke(new BasicStroke(strokeThickness * 4));
            	if (isClosed)
                    g2.drawPolygon(x_points, y_points, points.size());
                else
                    g2.drawPolyline(x_points, y_points, points.size());
        	}
        	
        	g2.setColor(colour);

            // call right drawing function
            if (isFilled) {
                g2.fillPolygon(x_points, y_points, points.size());
            }
            else {
            	g2.setStroke(new BasicStroke(strokeThickness)); 
            	if (isClosed)
                    g2.drawPolygon(x_points, y_points, points.size());
                else
                    g2.drawPolyline(x_points, y_points, points.size());
            }
        }
    }
    
    // find closest point
    static Point2d closestPoint(Point2d M, Point2d P1, Point2d P2)
    {
        // TODO: implement

        return new Point2d();
    }
    
    // return perpendicular vector
    static public Vector2d perp(Vector2d a)
    {
    	return new Vector2d(-a.y, a.x);
    }
    
    // line-line intersection
    // return (NaN,NaN) if not intersection, otherwise returns intersecting point
    static Point2d lineLineIntersection(Point2d P0, Point2d P1, Point2d Q0, Point2d Q1)
    {
    	
    	// TODO: implement

    	return new Point2d();
    }
    

    // affine transform helper
    // return P_prime = T * P    
    Point2d transform( AffineTransform T, Point2d P) {
    	Point2D.Double p = new Point2D.Double(P.x, P.y);
    	Point2D.Double q = new Point2D.Double();
    	T.transform(p, q);
    	return new Point2d(q.x, q.y);
    	
    	
    }
    
    // hit test with this shape
    public boolean hittest(double x, double y)
    {   
    	if (points != null) {

            // TODO Implement

    	}
    	
    	return false;
    }
}
