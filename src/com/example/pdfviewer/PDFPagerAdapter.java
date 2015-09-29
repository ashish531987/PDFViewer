package com.example.pdfviewer;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.graphics.pdf.PdfRenderer.Page;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PDFPagerAdapter extends FragmentStatePagerAdapter {

	private PdfRenderer mPdfRenderer;
	private int count = -1;

	public PDFPagerAdapter(FragmentManager fm, 	PdfRenderer pdfRenderer) {
		super(fm);
		this.mPdfRenderer = pdfRenderer;
		count = mPdfRenderer.getPageCount();
	}

	@Override
	public Fragment getItem(int index) {
		Page mCurrentPage = mPdfRenderer.openPage(index);
		// Important: the destination bitmap must be ARGB (not RGB).
		Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(), mCurrentPage.getHeight(),
				Bitmap.Config.ARGB_8888);
		// Here, we render the page onto the Bitmap.
		// To render a portion of the page, use the second and third parameter. Pass nulls to get
		// the default result.
		// Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
		mCurrentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

		mCurrentPage.close();
		Bundle arguments = new Bundle();
		arguments.putParcelable(PDFPageFragment.class.getSimpleName(), bitmap);
		PDFPageFragment fragment = new PDFPageFragment();
		fragment.setArguments(arguments);
		return fragment;
	}

	@Override
	public int getCount() {
		return count;
	}

}
