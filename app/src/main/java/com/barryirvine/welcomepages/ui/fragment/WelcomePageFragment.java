package com.barryirvine.welcomepages.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barryirvine.welcomepages.R;
import com.barryirvine.welcomepages.model.WelcomePage;
import com.squareup.picasso.Picasso;

public class WelcomePageFragment extends Fragment {

    private int mPageNumber;

    public WelcomePageFragment() {

    }

    public static WelcomePageFragment newInstance(final int pageNumber) {
        Log.d("BARRY", "Creating new instance for " + pageNumber);
        final WelcomePageFragment fragment = new WelcomePageFragment();
        final Bundle args = new Bundle();
        args.putInt(Args.PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public final void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(Args.PAGE);
    }

    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_welcome_page, container, false);
        // Only the pages between 1 and 3 are valid. The outer pages will never be created.
        if (mPageNumber > 0 && mPageNumber <= WelcomePage.values().length) {
            final WelcomePage page = WelcomePage.values()[mPageNumber - 1];
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(page.getMessage());
            rootView.setBackgroundColor(ContextCompat.getColor(getContext(), page.getColor()));
        }
        return rootView;
    }

    private static class Args {
        private static final String PAGE = "page";
    }
}
