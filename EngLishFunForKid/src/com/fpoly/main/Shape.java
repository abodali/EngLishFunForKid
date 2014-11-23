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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
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
public class Shape extends Fragment {
	Activity root;
	ImageView IvAudioShape,IvTextOnOffShape,IvShape,IvPreviousShape,IvRepeatShape,IvNextShape,IvList;
	TextView textOnOffShape;
	Mydatabase mydb;
	ArrayList<English>list;
	int stt = 33;
	MediaPlayer mp;
	Context context;
	ArrayList<English>listNew;
	AlertDialog listDialog;
	ListAdapter adpater;

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
		showScreenShape(33);
		IvNextShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt++;
				if (stt == 44)

				{
					stt = 33;
				}
				showScreenShape(stt);
			}
		});

		IvPreviousShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				stt--;
				if (stt == 32) {
					stt = 43;

				} // }
				showScreenShape(stt);

			}
		});
		IvAudioShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showScreenShape(stt);

			}
		});
		IvRepeatShape.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				playAudio(list.get(stt - 1).getAudio());
			}
		});
		
		IvTextOnOffShape.setOnClickListener(new OnClickListener() {

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
						for (int i = 32; i < 43; i++) {

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

								showScreenShape(position + 33);

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
			textOnOffShape.setText("");
			kt = false;
		} else {
			textOnOffShape.setText(list.get(stt - 1).getDecription());
			
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
	public void showScreenShape(int stt) {
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
