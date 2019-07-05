package uz.zokirbekov.e_eye.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.fragments.ImageDialogFragment;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;
import uz.zokirbekov.e_eye.utils.AnimationHelper;
import uz.zokirbekov.e_eye.utils.DateFormatter;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.VH> {

    private List<Action> items;
    private Context context;
    private Fragment fragment;
    private LayoutInflater inflater;

    public ItemsAdapter(Fragment fragment, List<Action> its)
    {
        items = its;
        this.context = fragment.getContext();
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(inflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        Action item = items.get(position);

        holder.item_title.setText(item.getTitle());
        holder.item_date.setText(DateFormatter.getFormatter().dateToString(item.getCreate_date()));
        holder.item_additional.setText(item.getAdditional());

        holder.setStatus(item.getStatus());

        byte[] data = item.getImage();

        if (data != null)
            holder.item_image.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation);
        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView item_title;
        TextView item_date;
        TextView item_additional;

        ImageView item_image;
        ImageView item_status;

        CardView cardView;

        private boolean isFrontSide = true;

        LinearLayout front,back;
        public VH(View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_date = itemView.findViewById(R.id.item_date);
            item_additional = itemView.findViewById(R.id.item_additional);

            item_image = itemView.findViewById(R.id.item_image);
            item_status = itemView.findViewById(R.id.item_status);

            front = itemView.findViewById(R.id.item_front_side);
            back = itemView.findViewById(R.id.item_back_side);

            cardView = (CardView)itemView;

            itemView.setOnClickListener(this);
            item_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cardView:
                    if (isFrontSide) {
                        AnimationHelper.getInstance().fadeOut(front);
                        AnimationHelper.getInstance().fadeIn(back);
                        isFrontSide = false;
                    } else {
                        AnimationHelper.getInstance().fadeOut(back);
                        AnimationHelper.getInstance().fadeIn(front);
                        isFrontSide = true;
                    }
                    break;
                case R.id.item_image:
                    Bitmap bitmap = ((BitmapDrawable)item_image.getDrawable()).getBitmap();
                    ImageDialogFragment.newInstance(bitmap)
                            .show(fragment.getFragmentManager(),"IMAGE_DIALOG_FRAGMENT");
                    break;
            }
        }
        private void setStatus(int status)
        {
            switch (status)
            {
                case DbManager.CONFIRMED:
                    cardView.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient_color_confirmed));
                    item_status.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.confirmed));
                    break;

                case DbManager.IN_PROGRESS:
                    cardView.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient_color_in_progress));
                    item_status.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.progress));
                    break;

                case DbManager.UNCONFIRMED:
                    cardView.setBackground(ContextCompat.getDrawable(context,R.drawable.gradient_color_unconfirmed));
                    item_status.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.uncofirmed));
                    break;
            }
        }
    }


}
