package com.fpoly.main;

import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.fpoly.db.Mydatabase;
import com.fpoly.englishfunforkid.R;
import com.fpoly.object.English;

@SuppressLint("NewApi")
public class Number extends Fragment {
	int stt = 1;
	Activity root;
	Mydatabase mydb;
	ImageView IvPrevious, IvList, IvRepeat, IvNext, IvNumber, IvAudio,
			IvTextOnOff;
	TextView textOnOff;
	int nextImageIndex;
	private SQLiteDatabase database;
	ArrayList<English> list;
	Context context;
	MediaPlayer mp;
	String popUpContents[];
	PopupWindow popUpWindowNumber;
	ArrayList<String> numberList;
	Number number;

	public Number(Context context, Number number) {
		this.context = context;
		this.number = number;

	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		root = getActivity();

		View rootView = inflater.inflate(R.layout.screen_number, container,
				false);

		mydb = new Mydatabase(root.getApplicationContext());

		list = mydb.GetAllInEnglish();
		Log.d("------raa", list.size() + "");
		init(rootView);
		ShowList();
		return rootView;
	}

	public void init(View rootView) {
		IvPrevious = (ImageView) rootView.findViewById(R.id.IvPrevious);
		IvList = (ImageView) rootView.findViewById(R.id.IvList);
		IvRepeat = (ImageView) rootView.findViewById(R.id.IvRepeat);
		IvNext = (ImageView) rootView.findViewById(R.id.IvNext);
		IvNumber = (ImageView) rootView.findViewById(R.id.IvNumber);
		textOnOff = (TextView) rootView.findViewById(R.id.textOnOff);
		IvAudio = (ImageView) rootView.findViewById(R.id.IvAudio);
		IvTextOnOff = (ImageView) rootView.findViewById(R.id.IvTextOnOff);
//		ShowList();
		showScreen(1);
		IvNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt++;
				if (stt == 21)

				{
					stt = 1;
				}
				showScreen(stt);
			}
		});

		IvPrevious.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt--;
				if (stt == 0) {
					stt = 20;

				} // }
				showScreen(stt);

			}
		});
		IvAudio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showScreen(stt);

			}
		});
		IvRepeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				playAudio(list.get(stt - 1).getAudio());
			}
		});

		IvTextOnOff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				hide();

			}
		});
	}

	//
	boolean kt = true;

	public void hide() {
		if (kt == true) {
			textOnOff.setText("");
			kt = false;
		} else {
			textOnOff.setText(list.get(stt - 1).getDecription());

			kt = true;

		}
	}

	// show image
	public void loadDataFromAsset(String namImage) {
		AssetManager assetManager = getActivity().getAssets();
		// load image
		try {
			// get input stream
			InputStream ims = assetManager.open("images/" + namImage + ".jpg");
			Bitmap bitmap = BitmapFactory.decodeStream(ims);

			// set image to ImageView
			IvNumber.setImageBitmap(bitmap);
		} catch (IOException ex) {
			return;
		}

	}

	// show screen
	public void showScreen(int stt) {
		English english = new English();
		english = list.get(stt - 1);
		textOnOff.setText(english.getDecription());
		playAudio(english.getAudio());
		loadDataFromAsset(english.getImage());

	}

	// Play Audio
	public void playAudio(String nameAudio) {
		AssetManager assetManager = getActivity().getAssets();
		mp = new MediaPlayer();
		try {
			AssetFileDescriptor descriptor = assetManager.openFd("audio/"
					+ nameAudio + ".mp3");
			mp.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close();
			mp.prepare();

			mp.start();
			mp.setVolume(10, 10);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Stop Audio
	public void stop() {
		mp.stop();
	}

	// show list
	public void ShowList() {
		numberList = new ArrayList<String>();
		numberList.add("1:1");
		numberList.add("2:2");
		numberList.add("3:3");
		numberList.add("4:4");
		numberList.add("5:5");
		numberList.add("6:6");
		numberList.add("7:7");
		numberList.add("8:8");
		numberList.add("9:9");
		numberList.add("10:10");
		numberList.add("11:11");
		numberList.add("12:12");
		numberList.add("13:13");
		numberList.add("14:14");
		numberList.add("15:15");
		numberList.add("16:16");
		numberList.add("17:17");
		numberList.add("18:18");
		numberList.add("19:19");
		numberList.add("20:20");

		// convert to simple array
		popUpContents = new String[numberList.size()];
		numberList.toArray(popUpContents);
		popUpWindowNumber = popUpWindowNumber();
		

		View.OnClickListener handler = new View.OnClickListener() {
			public void onClick(View v) {                                                                                                                                                                                                                                                                                                 
//				
				switch (v.getId()) {

				case R.id.IvList:

					
					 if (popUpWindowNumber == null) {
						 popUpWindowNumber = new PopupWindow();
						 popUpWindowNumber.setOutsideTouchable(true);
			            }

			            if (popUpWindowNumber.isShowing()) {
			            	popUpWindowNumber.dismiss();
			            }
			            else {
			            	popUpWindowNumber.showAsDropDown(v,-21,0);
			            }
				}
				
			}
		};
		IvList.setOnClickListener(handler);
	}

	public PopupWindow popUpWindowNumber() {

		PopupWindow popupWindow = new PopupWindow(root.getApplicationContext());
		ListView listViewNumber = new ListView(root.getApplicationContext());
		listViewNumber.setAdapter(numberAdapter(popUpContents));
		listViewNumber
				.setOnItemClickListener(new DropdownOnItemClickListener());
		// some other visual settings
		
		popupWindow.setWidth(400);
		popupWindow.setHeight(800);
		

		// set the list view as pop up window content
		popupWindow.setContentView(listViewNumber);

		return popupWindow;
	}

	private ArrayAdapter<String> numberAdapter(String numberArray[]) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				root.getApplicationContext(),
				android.R.layout.simple_list_item_1, numberArray) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				String item = getItem(position);
				// setting the ID and text for every items in the list
				String[] itemArr = item.split(":");
				String text = itemArr[0];
				String id = itemArr[1];
				// visual settings for the list item
				TextView listItem = new TextView(root.getApplicationContext());

				listItem.setText(text);
				listItem.setTag(id);
				listItem.setTextSize(22);
				listItem.setPadding(10, 10, 10, 10);
				listItem.setTextColor(Color.WHITE);
				listItem.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						English english = new English();
						english = list.get(stt - 1);
						textOnOff.setText(english.getDecription());
						playAudio(english.getAudio());
						loadDataFromAsset(english.getImage());
						
					}
				});
					
					
				return listItem;
			}

		};

		return adapter;

	}

	public class DropdownOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
			context = v.getContext();

			number = new Number(context, number);
			// add some animation when a list item was clicked
			Animation fadeInAnimation = AnimationUtils.loadAnimation(
					v.getContext(), android.R.anim.fade_in);
		fadeInAnimation.setDuration(10);
			v.startAnimation(fadeInAnimation);

			// dismiss the pop up
			number.popUpWindowNumber.dismiss();
		}
	}

}