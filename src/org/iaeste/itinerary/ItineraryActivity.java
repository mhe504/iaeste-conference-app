/******************************************
 * AUTHOR: Martyn Ellison
 * LAST UPDATE: 16/12/2011
 ******************************************/
package org.iaeste.itinerary;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.iaeste.R;

public class ItineraryActivity extends Activity {
	
	private int currDay = 1;
	private GestureDetector gestureDetector;
	private RelativeLayout.LayoutParams relParam;
	private RelativeLayout layoutTop;
	private LayoutInflater inflate;
	private LinearLayout layoutMain;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    layoutMain = new LinearLayout(this);
	    inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    layoutTop = (RelativeLayout) inflate.inflate(R.layout.itinerary, null);
	    relParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
	    
	    layoutMain.setOrientation(LinearLayout.VERTICAL);
	    setContentView(layoutMain);
	    
	    gestureDetector = new GestureDetector(new SwipeGestureDetector());
	    
	    RelativeLayout layoutBottom = (RelativeLayout) inflate.inflate(
	        R.layout.itinerary_d1, null);

	    layoutMain.addView(layoutTop, relParam);
	    layoutMain.addView(layoutBottom, relParam);
	    
	    String date = new SimpleDateFormat("d").format(new Date());
	    String month = new SimpleDateFormat("M").format(new Date());

	    if (month.equals("9"))
	    {
		    switch (Integer.parseInt(date))
		    {
		    	case 10:
		    		currDay = 1;
		    		changeDay(layoutMain,layoutTop,relParam,inflate);
		    		break;
		    	case 11:
		    		currDay = 2;
		    		changeDay(layoutMain,layoutTop,relParam,inflate);
		    		break;
		    	case 12:
		    		currDay = 3;
		    		changeDay(layoutMain,layoutTop,relParam,inflate);
		    		break;
		    	case 13:
		    		currDay = 4;
		    		changeDay(layoutMain,layoutTop,relParam,inflate);
		    		break;
		    	case 14:
		    		currDay = 5;
		    		changeDay(layoutMain,layoutTop,relParam,inflate);
		    		break;
		    }
	    }
	    else
	    {
	    	changeDay(layoutMain,layoutTop,relParam,inflate);
	    }

		final ImageView rightButton = (ImageView) layoutTop.findViewById(R.id.imgView_RightArrow);
		rightButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (currDay < 5)
				{
					currDay++;
				}
				changeDay(layoutMain,layoutTop,relParam,inflate);

				return false;
			}
		});
		
		final ImageView leftButton = (ImageView) findViewById(R.id.imgView_LeftArrow);
		leftButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (currDay > 1)
				{
					currDay--;
				}

				changeDay(layoutMain,layoutTop,relParam,inflate);
				
				return false;
			}
		});

	}
	
	private void changeDay(LinearLayout layoutMain,RelativeLayout layoutTop,RelativeLayout.LayoutParams relParam, LayoutInflater inflate)
	{
		RelativeLayout layoutBottom;
		switch (currDay)
		{
			case 1:
				layoutMain.removeAllViews();
				((ImageView)layoutTop.findViewById(R.id.imgView_LeftArrow)).setVisibility(ImageView.INVISIBLE);
				((ImageView)layoutTop.findViewById(R.id.imgView_RightArrow)).setVisibility(ImageView.VISIBLE);
				((TextView)layoutTop.findViewById(R.id.dayText)).setText("Day 1");
			    layoutMain.addView(layoutTop, relParam);
			    layoutBottom = (RelativeLayout) inflate.inflate(R.layout.itinerary_d1, null);
			    layoutMain.addView(layoutBottom, relParam);
			break;
			case 2:
				layoutMain.removeAllViews();
				((ImageView)layoutTop.findViewById(R.id.imgView_LeftArrow)).setVisibility(ImageView.VISIBLE);
				((ImageView)layoutTop.findViewById(R.id.imgView_RightArrow)).setVisibility(ImageView.VISIBLE);
				((TextView)layoutTop.findViewById(R.id.dayText)).setText("Day 2");
			    layoutMain.addView(layoutTop, relParam);
			    layoutBottom = (RelativeLayout) inflate.inflate(R.layout.itinerary_d2, null);
			    layoutMain.addView(layoutBottom, relParam);
			break;
			case 3:
				layoutMain.removeAllViews();
				((TextView)layoutTop.findViewById(R.id.dayText)).setText("Day 3");
				((ImageView)layoutTop.findViewById(R.id.imgView_LeftArrow)).setVisibility(ImageView.VISIBLE);
				((ImageView)layoutTop.findViewById(R.id.imgView_RightArrow)).setVisibility(ImageView.VISIBLE);
			    layoutMain.addView(layoutTop, relParam);
			    layoutBottom = (RelativeLayout) inflate.inflate(R.layout.itinerary_d3, null);
			    layoutMain.addView(layoutBottom, relParam);
			break;
			case 4:
				layoutMain.removeAllViews();
			    layoutMain.addView(layoutTop, relParam);
			    ((TextView)layoutTop.findViewById(R.id.dayText)).setText("Day 4");
				((ImageView)layoutTop.findViewById(R.id.imgView_LeftArrow)).setVisibility(ImageView.VISIBLE);
				((ImageView)layoutTop.findViewById(R.id.imgView_RightArrow)).setVisibility(ImageView.VISIBLE);
			    layoutBottom = (RelativeLayout) inflate.inflate(R.layout.itinerary_d4, null);
			    layoutMain.addView(layoutBottom, relParam);
			break;
			case 5: 
				layoutMain.removeAllViews();
			    layoutMain.addView(layoutTop, relParam);
			    ((TextView)layoutTop.findViewById(R.id.dayText)).setText("Day 5");
				((ImageView)layoutTop.findViewById(R.id.imgView_LeftArrow)).setVisibility(ImageView.VISIBLE);
				((ImageView)layoutTop.findViewById(R.id.imgView_RightArrow)).setVisibility(ImageView.INVISIBLE);
			    layoutBottom = (RelativeLayout) inflate.inflate(R.layout.itinerary_d5, null);
			    layoutMain.addView(layoutBottom, relParam);
			break;
		}
	}
	
	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
	    if (gestureDetector.onTouchEvent(event)) {
	      return true;
	    }
	    return super.onTouchEvent(event);
	  }

	  private void onLeftSwipe() {
		  if (currDay < 8){
			  currDay++;
			  changeDay(layoutMain,layoutTop,relParam,inflate);
		  }
	  }

	  private void onRightSwipe() {
		  if (currDay > 1){
			  currDay--;
			  changeDay(layoutMain,layoutTop,relParam,inflate);
		  }
	  }
	
	//Private class for gestures
	private class SwipeGestureDetector 
	extends SimpleOnGestureListener {
		// Swipe properties, you can change it to make the swipe 
		// longer or shorter and speed
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 200;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2,
				float velocityX, float velocityY) {
			try {
				float diffAbs = Math.abs(e1.getY() - e2.getY());
				float diff = e1.getX() - e2.getX();
				
				if (diffAbs > SWIPE_MAX_OFF_PATH)
					return false;
				
				// Left swipe
				if (diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					ItineraryActivity.this.onLeftSwipe();
					
					// Right swipe
				} else if (-diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					ItineraryActivity.this.onRightSwipe();
				}
			} catch (Exception e) {
				Log.e("YourActivity", "Error on gestures");
			}
			return false;
		}
	}

}

