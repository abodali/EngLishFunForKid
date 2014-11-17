package com.fpoly.main;

import java.io.IOException;
import java.io.InputStream;
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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.db.Mydatabase;
import com.fpoly.englishfunforkid.R;
import com.fpoly.object.English;

@SuppressLint("NewApi")
public class Color extends Fragment {
	Activity root;
	Mydatabase mydb;
	ImageView IvPreviousColor, IvList, IvRepeatColor, IvNextColor, IvColor, IvAudioColor,
			IvTextOnOffColor;
	TextView textOnOffColor;
	int stt = 21;
	private SQLiteDatabase database;
	ArrayList<English> list;
	Context context;
	MediaPlayer mp;

	public Color() {
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		root = getActivity();

		View rootView = inflater.inflate(R.layout.screen_color, container,
				false);
		mydb = new Mydatabase(root.getApplicationContext());

		list = mydb.GetAllInEnglish();
		Log.d("------raa", list.size() + "");
		init(rootView);
		return rootView;
	}

	public void init(View rootView) {
		IvPreviousColor = (ImageView) rootView.findViewById(R.id.IvPreviousColor);
		IvList = (ImageView) rootView.findViewById(R.id.IvList);
		IvRepeatColor = (ImageView) rootView.findViewById(R.id.IvRepeatColor);
		IvNextColor = (ImageView) rootView.findViewById(R.id.IvNextColor);
		IvColor = (ImageView) rootView.findViewById(R.id.IvColor);
		textOnOffColor = (TextView) rootView.findViewById(R.id.textOnOffColor);
		IvAudioColor = (ImageView) rootView.findViewById(R.id.IvAudioColor);
		IvTextOnOffColor = (ImageView) rootView.findViewById(R.id.IvTextOnOffColor);
		showScreenColor(21);
		IvNextColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt++;
				if (stt == 33)

				{
					stt = 21;
				}
				showScreenColor(stt);
			}
		});
		

		IvPreviousColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt--;
				if (stt == 20) {
					stt = 32;

				} // }
				showScreenColor(stt);
				

			}
		});
		IvAudioColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showScreenColor(stt);

			}
		});
		IvRepeatColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				playAudio(list.get(stt - 1).getAudio());
			}
		});
		
		IvTextOnOffColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// chay thu xem
				hide();

			}
		});
	}

	//
	boolean kt = true;

	// chờ tui 1 tí
	public void hide() {
		if (kt == true) {
			textOnOffColor.setText("");
			kt = false;
		} else {
			textOnOffColor.setText(list.get(stt - 1).getDecription());
			// chay
			kt = true;

		}
	}

	// show image
	public void loadDataFromAssetColor(String nameImageColor) {
		AssetManager assetManager = getActivity().getAssets();
		// load image
		try {
			// get input stream
			InputStream ims = assetManager.open("images/" + nameImageColor  + ".png");
			Bitmap bitmap = BitmapFactory.decodeStream(ims);
			// chay xem
			// set image to ImageView
			IvColor.setImageBitmap(bitmap);
		} catch (IOException ex) {
			return;
		}

	}

	// show screen
	public void showScreenColor(int stt) {
		English english = new English();
		english = list.get(stt -1 );
		textOnOffColor.setText(english.getDecription());
		playAudio(english.getAudio());
		loadDataFromAssetColor(english.getImage());
		Log.d("--------",english.getImage());

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

}
