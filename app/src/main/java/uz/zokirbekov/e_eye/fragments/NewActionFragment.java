package uz.zokirbekov.e_eye.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;
import uz.zokirbekov.e_eye.utils.BitmapConverter;

public class NewActionFragment extends Fragment {

    @BindView(R.id.cardView)
    CardView cardView;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.editText_title)
    EditText editTextTitle;

    @BindView(R.id.editText_subscription)
    EditText editTextSubscription;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_action,container,false);
        ButterKnife.bind(this,v);
        cardView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gradient_for_subscription));
        return v;
    }

    @OnClick(R.id.imageView)
    void onImageViewClicked()
    {
        String action = MediaStore.ACTION_IMAGE_CAPTURE;
        Intent intent = new Intent(action);
        startActivityForResult(intent,0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0)
        if (data.getExtras() != null)
            if (data.getExtras().containsKey("data")) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                Bitmap resized = Bitmap.createScaledBitmap(image, imageView.getWidth(), imageView.getHeight(), true);
                imageView.setImageBitmap(resized);
            }
    }

    @OnClick(R.id.button_add)
    void onAddClicked()
    {
        String title = editTextTitle.getText().toString().trim();
        String subscription = editTextSubscription.getText().toString().trim();

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        byte[] byteArray = BitmapConverter.getInstance().bitmapToByteArray(bitmap);

        DbManager.getInstance(getContext()).addAction(title,subscription,byteArray);
    }


}
