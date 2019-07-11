package uz.zokirbekov.e_eye.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.utils.BitmapConverter;

public class ImageDialogFragment extends DialogFragment {

    @BindView(R.id.imageView)
    ImageView imageView;

    public static ImageDialogFragment newInstance(Bitmap bitmap) {

        Bundle args = null;
        if (bitmap != null) {
            args = new Bundle();
            args.putParcelable("image", bitmap);
        }
        ImageDialogFragment fragment = new ImageDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_image, container, false);
        ButterKnife.bind(this,v);
        if (getArguments() != null)
            imageView.setImageBitmap(getArguments().getParcelable("image"));
        else
            imageView.setColorFilter(Color.BLACK);
        return v;
    }
}
