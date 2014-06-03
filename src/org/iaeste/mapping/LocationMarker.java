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
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;

public class LocationMarker extends Overlay {

	private final Point screenCoords = new Point();

    private final int FOREGROUND_COLOUR = Color.parseColor("#129415");
    private final int BACKGROUND_COLOUR =  Color.WHITE;

    private GeoPoint mLocation;
    private boolean labelVisible;

	public LocationMarker(final Context ctx) {
    	super(ctx);
    	labelVisible = true;
    }

	
	@Override
	protected void draw(Canvas c, MapView osmv, boolean arg2) {
		
		if (mLocation != null)
		{
	        final Projection pj = osmv.getProjection();
	        pj.toMapPixels(this.getmLocation(), screenCoords);
			
	        //Add Marker
		    Paint paint = new Paint();
		    paint.setColor(FOREGROUND_COLOUR);
		    c.drawCircle(screenCoords.x, screenCoords.y, 4, paint);
		    
		    paint.setStyle(Style.STROKE);
		    paint.setStrokeWidth(2f);
		    
		    c.drawCircle(screenCoords.x, screenCoords.y, 8, paint);

		    if (labelVisible)
		    { 
			    c.drawLine(screenCoords.x, screenCoords.y, screenCoords.x + 10, screenCoords.y -10, paint);
			    c.drawLine(screenCoords.x + 10, screenCoords.y- 10, screenCoords.x + 19, screenCoords.y -10, paint);
			    
			    Point lineEndPoint = new Point();
			    lineEndPoint.x = screenCoords.x + 19;
			    lineEndPoint.y = screenCoords.y -10;
			    
			    //Add Label
			    paint.setStyle(Style.FILL);
			    paint.setStrokeWidth(1f);
			    c.drawRect(new Rect(lineEndPoint.x,lineEndPoint.y+10,lineEndPoint.x+82,lineEndPoint.y-10), paint);
			    paint.setColor(BACKGROUND_COLOUR);
			    c.drawRect(new Rect(lineEndPoint.x + 2,lineEndPoint.y+ 8,lineEndPoint.x+80,lineEndPoint.y-8), paint);
	
			    paint.setColor(FOREGROUND_COLOUR);
			    c.drawText("Your Location", lineEndPoint.x + 4, lineEndPoint.y+ 4, paint);
		    }
		}
	}


	public GeoPoint getmLocation() {
		return mLocation;
	}


	public void setmLocation(GeoPoint mLocation) {
		this.mLocation = mLocation;
	}


	public boolean getLabelVisibility() {
		return labelVisible;
	}


	public void setLabelVisibility(boolean labelVisible) {
		this.labelVisible = labelVisible;
	}

}
