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
public class Animal extends Fragment {
	
	Activity root;
	Mydatabase mydb;
	ImageView IvPreviousAnimal, IvList, IvRepeatAnimal, IvNextAnimal, IvAnimal, IvAudioAnimal,
			IvTextOnOffAnimal;
	TextView textOnOffAnimal;
	int stt = 44;
	private SQLiteDatabase database;
	ArrayList<English> list;
	Context context;
	MediaPlayer mp;
	
	
	public Animal() {
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		root = getActivity();

		View rootView = inflater.inflate(R.layout.screen_animal, container,
				false);
		mydb = new Mydatabase(root.getApplicationContext());

		list = mydb.GetAllInEnglish();
		Log.d("------raa", list.size() + "");
		init(rootView);

		return rootView;
	}
	public void init(View rootView) {
		IvPreviousAnimal = (ImageView) rootView.findViewById(R.id.IvPreviousAnimal);
		IvList = (ImageView) rootView.findViewById(R.id.IvList);
		IvRepeatAnimal = (ImageView) rootView.findViewById(R.id.IvRepeatAnimal);
		IvNextAnimal = (ImageView) rootView.findViewById(R.id.IvNextAnimal);
		IvAnimal = (ImageView) rootView.findViewById(R.id.IvAnimal);
		textOnOffAnimal = (TextView) rootView.findViewById(R.id.textOnOffAnimal);
		IvAudioAnimal = (ImageView) rootView.findViewById(R.id.IvAudioAnimal);
		IvTextOnOffAnimal = (ImageView) rootView.findViewById(R.id.IvTextOnOffAnimal);
		showScreenColor(44);
		IvNextAnimal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt++;
				if (stt == 64)

				{
					stt = 44;
				}
				showScreenColor(stt);
			}
		});
		

		IvPreviousAnimal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt--;
				if (stt == 43) {
					stt = 63;

				} // }
				showScreenColor(stt);
				

			}
		});
		IvAudioAnimal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showScreenColor(stt);

			}
		});
		IvRepeatAnimal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				playAudio(list.get(stt - 1).getAudio());
			}
		});
		
		IvTextOnOffAnimal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// chay thu xem
				hide();

			}
		});
	}
	boolean kt = true;

	// chờ tui 1 tí
	public void hide() {
		if (kt == true) {
			textOnOffAnimal.setText("");
			kt = false;
		} else {
			textOnOffAnimal.setText(list.get(stt - 1).getDecription());
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
			InputStream ims = assetManager.open("images/" + nameImageColor  + ".jpg");
			Bitmap bitmap = BitmapFactory.decodeStream(ims);
			// chay xem
			// set image to ImageView
			IvAnimal.setImageBitmap(bitmap);
		} catch (IOException ex) {
			return;
		}

	}

	// show screen
	public void showScreenColor(int stt) {
		English english = new English();
		english = list.get(stt -1 );
		textOnOffAnimal.setText(english.getDecription());
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
