package uz.zokirbekov.e_eye.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.adapters.ItemsAdapter;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;
import uz.zokirbekov.e_eye.utils.VerticalSpaceItemDecoration;

public class HomeFragment extends Fragment {

    @BindView(R.id.listView)
    RecyclerView listView;

    @BindView(R.id.text_item_count)
    TextView textItemCount;

    @BindView(R.id.text_item_confirmed_count)
    TextView textConfirmedCount;

    @BindView(R.id.text_item_in_progress_count)
    TextView textInProgressCount;

    @BindView(R.id.text_item_unconfirmed_count)
    TextView textUnconfirmedCount;

    @BindView(R.id.image_all)
    ImageView imageAll;

    @BindView(R.id.image_confirmed)
    ImageView imageConfirmed;

    @BindView(R.id.image_in_progress)
    ImageView imageInProgress;

    @BindView(R.id.image_unconfirmed)
    ImageView imageUnconfirmed;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ImageView lastClickedStatus;

    List<Action> actions;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,v);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new VerticalSpaceItemDecoration(48));
        lastClickedStatus = imageAll;

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary), PorterDuff.Mode.ADD);
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)listView.getLayoutParams();
//        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
//        listView.requestLayout();

        initListView();
        return v;
    }

    private void initListView()
    {
        progressBar.setVisibility(View.VISIBLE);
        actions = DbManager.getInstance(getContext()).getAllActions();
        textItemCount.setText(String.valueOf(actions.size()));

        setCountByStatus(actions,DbManager.CONFIRMED,textConfirmedCount);
        setCountByStatus(actions,DbManager.IN_PROGRESS,textInProgressCount);
        setCountByStatus(actions,DbManager.UNCONFIRMED,textUnconfirmedCount);

        initAdapter(actions);
    }

    private void initAdapter(List<Action> actions)
    {
        ItemsAdapter adapter = new ItemsAdapter(this,actions);
        listView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void setCountByStatus(List<Action> actions, int i, TextView v)
    {
        Observable<Action> act = Observable.fromIterable(actions);

        act.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter( x -> x.getStatus() == i)
        .count()
        .subscribe(count -> v.setText(String.valueOf(count)));
    }

    private void filterByStatus(int status)
    {
        if (status == DbManager.ALL)
        {
            initAdapter(actions);
        }
        else
        {
            Observable<Action> oActs = Observable.fromIterable(actions);
            oActs.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(act -> act.getStatus() == status)
                    .toList()
                    .subscribe(acts -> initAdapter(acts));
        }
    }

    @OnClick({R.id.image_confirmed,R.id.image_in_progress,R.id.image_unconfirmed})
    void onStatusClicked(View v)
    {
        ImageView image = (ImageView)v;
        if (lastClickedStatus == image)
            return;
        if (lastClickedStatus == imageAll)
            imageAll.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.all_transparent));
        else
            lastClickedStatus.setColorFilter(Color.WHITE);

        image.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorLightBlue));
        lastClickedStatus = image;

        switch (image.getId())
        {
            case R.id.image_confirmed : filterByStatus(DbManager.CONFIRMED); break;
            case R.id.image_in_progress : filterByStatus(DbManager.IN_PROGRESS); break;
            case R.id.image_unconfirmed : filterByStatus(DbManager.UNCONFIRMED); break;
        }


    }

    @OnClick(R.id.image_all)
    void onConfirmedClicked()
    {
        if (lastClickedStatus == imageAll)
            return;
        lastClickedStatus.setColorFilter(Color.WHITE);
        imageAll.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.all));
        lastClickedStatus = imageAll;
        filterByStatus(DbManager.ALL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DbManager.getInstance(getContext()).close();
    }

    //    @OnClick(R.id.image_in_progress)
//    void onInProgressClicked()
//    {
//        lastClickedStatus.setColorFilter(Color.WHITE);
//        imageInProgress.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorLightBlue));
//        lastClickedStatus = imageInProgress;
//    }
//
//    @OnClick(R.id.image_unconfirmed)
//    void onUnconfirmedClicked()
//    {
//        lastClickedStatus.setColorFilter(Color.WHITE);
//        imageUnconfirmed.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorLightBlue));
//        lastClickedStatus = imageUnconfirmed;
//    }


}
