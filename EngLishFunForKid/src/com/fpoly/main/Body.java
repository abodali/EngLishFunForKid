package com.fpoly.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.fpoly.adapter.ListAdapter;
import com.fpoly.db.Mydatabase;
import com.fpoly.englishfunforkid.R;
import com.fpoly.object.English;

@SuppressLint("NewApi")
public class Body extends Fragment {
	Activity root;
	Mydatabase mydb;
	ImageView IvPreviousBody, IvList, IvRepeatBody, IvNextBody, IvBody,
			IvAudioBody, IvTextOnOffBody;
	TextView textOnOffBody;
	int stt = 116;
	private SQLiteDatabase database;
	ArrayList<English> list;
	Context context;
	MediaPlayer mp;
	ArrayList<English>listNew;
	AlertDialog listDialog;
	public Body() {
	}

	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		root = getActivity();

		View rootView = inflater
				.inflate(R.layout.screen_body, container, false);
		mydb = new Mydatabase(root.getApplicationContext());

		list = mydb.GetAllInEnglish();
		Log.d("------raa", list.size() + "");
		init(rootView);

		return rootView;
	}
	public void init(View rootView) {
		IvPreviousBody = (ImageView) rootView.findViewById(R.id.IvPreviousBody);
		IvList = (ImageView) rootView.findViewById(R.id.IvList);
		IvRepeatBody = (ImageView) rootView.findViewById(R.id.IvRepeatBody);
		IvNextBody = (ImageView) rootView.findViewById(R.id.IvNextBody);
		IvBody = (ImageView) rootView.findViewById(R.id.IvBody);
		textOnOffBody = (TextView) rootView.findViewById(R.id.textOnOffBody);
		IvAudioBody = (ImageView) rootView.findViewById(R.id.IvAudioBody);
		IvTextOnOffBody = (ImageView) rootView.findViewById(R.id.IvTextOnOffBody);
		showScreenBody(116);
		IvNextBody.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt++;
				if (stt == 141)

				{
					stt = 116;
				}
				showScreenBody(stt);
			}
		});
		

		IvPreviousBody.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt--;
				if (stt == 115) {
					stt = 140;

				} // }
				showScreenBody(stt);
				

			}
		});
		IvAudioBody.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showScreenBody(stt);

			}
		});
		IvRepeatBody.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				playAudio(list.get(stt - 1).getAudio());
			}
		});
		
		IvTextOnOffBody.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				hide();

			}
		});
		// show list view
		IvList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(root);
				mydb = new Mydatabase(root);
				listNew = new ArrayList<English>();
				for (int i = 115; i < 140; i++) {

					listNew.add(list.get(i));

				}
				ListView listTail = new ListView(root);
				listTail.setAdapter(new ListAdapter(root
						.getApplicationContext(), listNew));
				listTail.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						if (listDialog.isShowing()) {
							listDialog.dismiss();

						}

						showScreenBody(position + 116);

					}
				});
				builder.setView(listTail);
				listDialog = builder.create();
				listDialog.show();

			}
		});
	}
	boolean kt = true;


	public void hide() {
		if (kt == true) {
			textOnOffBody.setText("");
			kt = false;
		} else {
			textOnOffBody.setText(list.get(stt - 1).getDecription());
			
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
			
			// set image to ImageView
			IvBody.setImageBitmap(bitmap);
		} catch (IOException ex) {
			return;
		}

	}

	// show screen
	public void showScreenBody(int stt) {
		English english = new English();
		english = list.get(stt -1 );
		textOnOffBody.setText(english.getDecription());
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
			mp.setVolume(100, 100);

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
