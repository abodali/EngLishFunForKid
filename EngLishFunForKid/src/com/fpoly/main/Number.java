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
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.adapter.ListAdapter;
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
	ArrayList<English> listNew;
	Context context;
	MediaPlayer mp;
	Number number;
	ListView listDetail;
	AlertDialog listDialog;
	ListAdapter adapter;

	

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
		// listDetail = (ListView) rootView.findViewById(R.id.list_item);

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
		// show list view
		IvList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(root);
				mydb = new Mydatabase(root);
				listNew = new ArrayList<English>();
				for (int i = 0; i < 20; i++) {

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
						
						showScreen(position + 1);

					}
				});
				builder.setView(listTail);
				listDialog = builder.create();
				listDialog.show();

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