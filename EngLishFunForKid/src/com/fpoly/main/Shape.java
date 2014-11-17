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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
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
public class Shape extends Fragment {
	Activity root;
	ImageView IvAudioShape,IvTextOnOffShape,IvShape,IvPreviousShape,IvRepeatShape,IvNextShape,IvList;
	TextView textOnOffShape;
	Mydatabase mydb;
	ArrayList<English>list;
	int stt = 33;
	MediaPlayer mp;
	Context context;

	public Shape() {
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		root = getActivity();

		View rootView = inflater.inflate(R.layout.screen_shape, container,
				false);
		mydb = new Mydatabase(root.getApplicationContext());
		list = mydb.GetAllInEnglish();
		init(rootView);
		return rootView;
	}
	public void init(View rootView) {
		IvPreviousShape = (ImageView) rootView.findViewById(R.id.IvPreviousShape);
		IvList = (ImageView) rootView.findViewById(R.id.IvList);
		IvRepeatShape = (ImageView) rootView.findViewById(R.id.IvRepeatShape);
		IvNextShape = (ImageView) rootView.findViewById(R.id.IvNextShape);
		IvShape = (ImageView) rootView.findViewById(R.id.IvShape);
		textOnOffShape = (TextView) rootView.findViewById(R.id.textOnOffShape);
		IvAudioShape = (ImageView) rootView.findViewById(R.id.IvAudioShape);
		IvTextOnOffShape = (ImageView) rootView.findViewById(R.id.IvTextOnOffShape);
		showScreen(33);
		IvNextShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt++;
				if (stt == 44)

				{
					stt = 33;
				}
				showScreen(stt);
			}
		});
		// cho xiu
		// eclipse bị dien

		IvPreviousShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt--;
				if (stt == 32) {
					stt = 43;

				} // }
				showScreen(stt);

			}
		});
		IvAudioShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showScreen(stt);

			}
		});
		IvRepeatShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				playAudio(list.get(stt - 1).getAudio());
			}
		});
		// on click cái imageview ẩn text đó
		// ngang với cái phát am thanh ok
		IvTextOnOffShape.setOnClickListener(new OnClickListener() {

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
			textOnOffShape.setText("");
			kt = false;
		} else {
			textOnOffShape.setText(list.get(stt - 1).getDecription());
			// chay
			kt = true;

		}
	}

	// show image
	public void loadDataFromAsset(String namImage) {
		AssetManager assetManager = getActivity().getAssets();
		// load image
		try {
			// get input stream
			InputStream ims = assetManager.open("images/" + namImage + ".png");
			Bitmap bitmap = BitmapFactory.decodeStream(ims);
			// chay xem
			// set image to ImageView
			IvShape.setImageBitmap(bitmap);
		} catch (IOException ex) {
			return;
		}

	}

	// show screen
	public void showScreen(int stt) {
		English english = new English();
		english = list.get(stt - 1);
		textOnOffShape.setText(english.getDecription());
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

}
