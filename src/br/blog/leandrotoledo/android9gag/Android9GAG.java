package br.blog.leandrotoledo.android9gag;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

public class Android9GAG extends Activity {
	/** Called when the activity is first created. */
	private ViewFlipper viewFlipper;

	private LinearLayout hot;
	private LinearLayout fresh;
	private LinearLayout trending;
	private LinearLayout featured;

	private float oldTouchValue;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.update:
			return true;
		case R.id.category:
			showDialogMenuCategory();
			return true;
		case R.id.about:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void updateViewFlipperTitle() {
		int id = viewFlipper.getCurrentView().getId();

		if (id == hot.getId()) {
			setTitle("9GAG: Hot");
		} else if (id == fresh.getId()) {
			setTitle("9GAG: New - Fresh");
		} else if (id == trending.getId()) {
			setTitle("9GAG: New - Trending");
		} else if (id == featured.getId()) {
			setTitle("9GAG: New - Featured");
		}
	}

	private void showDialogMenuCategory() {
		final CharSequence[] options = { "Hot", "New: Fresh", "New: Trending",
				"New: Featured" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Category");

		builder.setItems(options, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// viewFlipper.setDisplayedChild(0);
				switch (which) {
				case 0:
					// Option 1: Hot
					viewFlipper.setDisplayedChild(0);
					updateViewFlipperTitle();
					break;
				case 1:
					// Option 2: Fresh
					viewFlipper.setDisplayedChild(1);
					updateViewFlipperTitle();
					break;
				case 2:
					// Option 3: Trending
					viewFlipper.setDisplayedChild(2);
					updateViewFlipperTitle();
					break;
				case 3:
					// Option 4: Featured
					viewFlipper.setDisplayedChild(3);
					updateViewFlipperTitle();
					break;
				}
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

		hot = (LinearLayout) findViewById(R.id.hot);
		fresh = (LinearLayout) findViewById(R.id.fresh);
		trending = (LinearLayout) findViewById(R.id.trending);
		featured = (LinearLayout) findViewById(R.id.featured);

		setTitle("9GAG: Hot");
	}

	@Override
	public boolean onTouchEvent(MotionEvent touchEvent) {
		final Animation animFlipInNext = AnimationUtils.loadAnimation(this,
				R.anim.flipinnext);
		final Animation animFlipOutNext = AnimationUtils.loadAnimation(this,
				R.anim.flipoutnext);
		final Animation animFlipInPrevious = AnimationUtils.loadAnimation(this,
				R.anim.flipinprevious);
		final Animation animFlipOutPrevious = AnimationUtils.loadAnimation(
				this, R.anim.flipoutprevious);

		switch (touchEvent.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			oldTouchValue = touchEvent.getX();
		}
		case MotionEvent.ACTION_UP: {
			float currentX = touchEvent.getX();
			if (oldTouchValue < currentX) {
				viewFlipper.setInAnimation(animFlipInPrevious);
				viewFlipper.setOutAnimation(animFlipOutPrevious);
				viewFlipper.showNext();
			}
			if (oldTouchValue > currentX) {
				viewFlipper.setInAnimation(animFlipInNext);
				viewFlipper.setOutAnimation(animFlipOutNext);
				viewFlipper.showPrevious();
			}
			break;
		}
		}

		updateViewFlipperTitle();

		return true;
	}
}