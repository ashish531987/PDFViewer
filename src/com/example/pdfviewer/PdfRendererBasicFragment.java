/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.pdfviewer;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * This fragment has a big {@ImageView} that shows PDF pages, and 2 {@link android.widget.Button}s to move between
 * pages. We use a {@link android.graphics.pdf.PdfRenderer} to render PDF pages as {@link android.graphics.Bitmap}s.
 */
public class PdfRendererBasicFragment extends Fragment implements View.OnClickListener {

	/**
	 * File descriptor of the PDF.
	 */
	private ParcelFileDescriptor mFileDescriptor;

	/**
	 * {@link android.graphics.pdf.PdfRenderer} to render the PDF.
	 */
	private PdfRenderer mPdfRenderer;

	/**
	 * {@link android.widget.ImageButton} to move to the previous page.
	 */
	private ImageButton mButtonPrevious;

	/**
	 * {@link android.widget.ImageButton} to move to the next page.
	 */
	private ImageButton mButtonNext;

	private Uri mUri;

	/*
	 * Pager and Adapter
	 */
	private ViewPager mViewPager;
	
	private FragmentStatePagerAdapter mPagerAdapter;
	
	private int mCurrentPage = 0;

	public PdfRendererBasicFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		if(arguments != null)
		{
			if(arguments.containsKey(PdfRendererBasicFragment.class.getSimpleName()))
			{
				mUri = arguments.getParcelable(PdfRendererBasicFragment.class.getSimpleName());
			}
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pdf_renderer_root, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Activity activity = getActivity();
		try {
			openRenderer(activity);
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(activity, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
			activity.finish();
		}
		// Retain view references.
		mViewPager = (ViewPager) view.findViewById(R.id.pager);
		mPagerAdapter = new PDFPagerAdapter(getChildFragmentManager(), mPdfRenderer);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				mCurrentPage = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
		
		mButtonPrevious = (ImageButton) view.findViewById(R.id.previous);
		mButtonNext = (ImageButton) view.findViewById(R.id.next);
		// Bind events.
		mButtonPrevious.setOnClickListener(this);
		mButtonNext.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		try {
			closeRenderer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	/**
	 * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
	 */
	private void openRenderer(Context context) throws IOException {
		// In this sample, we read a PDF from the assets directory.
		mFileDescriptor = context.getContentResolver().openFileDescriptor(mUri, "r");
		// This is the PdfRenderer we use to render the PDF.
		mPdfRenderer = new PdfRenderer(mFileDescriptor);
	}

	/**
	 * Closes the {@link android.graphics.pdf.PdfRenderer} and related resources.
	 *
	 * @throws java.io.IOException When the PDF file cannot be closed.
	 */
	private void closeRenderer() throws IOException {
//		if (null != mCurrentPage) {
//			mCurrentPage.close();
//		}
		mPdfRenderer.close();
		mFileDescriptor.close();
	}


	/**
	 * Updates the state of 2 control buttons in response to the current page index.
	 */
//	private void updateUi() {
//		int index = mCurrentPage.getIndex();
//		int pageCount = mPdfRenderer.getPageCount();
//		mButtonPrevious.setEnabled(0 != index);
//		mButtonNext.setEnabled(index + 1 < pageCount);
//		getActivity().setTitle(getString(R.string.app_name_with_index, index + 1, pageCount));
//	}

	/**
	 * Gets the number of pages in the PDF. This method is marked as public for testing.
	 *
	 * @return The number of pages.
	 */
	public int getPageCount() {
		return mPdfRenderer.getPageCount();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.previous: {
			if((mCurrentPage - 1) >= 0){
				mViewPager.setCurrentItem(--mCurrentPage);
			}
			break;
		}
		case R.id.next: {
			if((mCurrentPage + 1) <= mPagerAdapter.getCount()){
				mViewPager.setCurrentItem(++mCurrentPage);
			}
			break;
		}
		}
	}

}
