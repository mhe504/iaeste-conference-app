/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.mapping;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.Overlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class PoiMarker extends Overlay {

	private final Point screenCoords = new Point();

    private int FOREGROUND_COLOUR = Color.parseColor("#bc0606");
    private int BACKGROUND_COLOUR =  Color.WHITE;

    private GeoPoint mLocation;
    private String label;
    private boolean labelVisible;
    

    public PoiMarker(final Context ctx, String label, GeoPoint location) {
    	super(ctx);
        this.label = label;
        mLocation = location;
        labelVisible = true;
    }

    public void setLocation(final GeoPoint mp) {
            this.mLocation = mp;
    }

    public GeoPoint getMyLocation() {
            return this.mLocation;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean getLabelVisibility() {
		return labelVisible;
	}
	
	public void setLabelVisibility(boolean labelVisible) {
		this.labelVisible = labelVisible;
	}
	
	@Override	
	protected void draw(Canvas c, MapView osmv, boolean arg2) {
		
        if (this.mLocation != null) {
            final Projection pj = osmv.getProjection();
            pj.toMapPixels(this.mLocation, screenCoords);
            
            Paint paint = new Paint();
            paint.setColor(FOREGROUND_COLOUR);//(1px larger Rect)
            paint.setStrokeWidth(2f);
            c.drawLine(screenCoords.x - 7, screenCoords.y - 7, screenCoords.x + 7, screenCoords.y + 7, paint);
            c.drawLine(screenCoords.x + 7, screenCoords.y - 7, screenCoords.x - 7, screenCoords.y + 7, paint);
            
            if (labelVisible)
            {
	            //Draw Text Background
	            c.drawRect(new Rect(screenCoords.x + 13,
	            					screenCoords.y - 10,
	            					screenCoords.x + (int)(java.lang.Math.pow(label.length(), 1.7)) + 2,
	            					screenCoords.y + 10), 
	            					paint);
	
	            paint.setStrokeWidth(1f);
	            c.drawLine(screenCoords.x, screenCoords.y, screenCoords.x + 13, screenCoords.y, paint);
	
	            paint.setColor(BACKGROUND_COLOUR);
	            c.drawRect(new Rect(screenCoords.x + 15,
	            					screenCoords.y - 8, 
	            					screenCoords.x + (int)(java.lang.Math.pow(label.length(), 1.7)), 
	            					screenCoords.y + 8), 
	            					paint);
	
	            //Draw Label Text
	            paint.setColor(FOREGROUND_COLOUR);
	            c.drawText(label, screenCoords.x + 18, screenCoords.y +4, paint);
            }
        }
	}

}