package com.example.pdfviewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PDFPageFragment extends Fragment 
{
	private ImageView mImageView;
	private Bitmap mBitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			if(arguments.containsKey(PDFPageFragment.class.getSimpleName()))
			{
				mBitmap = arguments.getParcelable(PDFPageFragment.class.getSimpleName());
			}
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pdf_renderer_page, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mImageView = (ImageView) view.findViewById(R.id.image);
		mImageView.setImageBitmap(mBitmap);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBitmap = null;
	}
	
	
}
