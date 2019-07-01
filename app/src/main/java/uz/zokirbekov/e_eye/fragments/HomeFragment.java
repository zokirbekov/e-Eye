package uz.zokirbekov.e_eye.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.adapters.ItemsAdapter;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;
import uz.zokirbekov.e_eye.utils.VerticalSpaceItemDecoration;

public class HomeFragment extends Fragment {

    @BindView(R.id.listView)
    RecyclerView listView;

    List<Action> actions;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,v);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.addItemDecoration(new VerticalSpaceItemDecoration(48));
        initListView();
        return v;
    }

    private void initListView()
    {
        actions = DbManager.getInstance(getContext()).getAllActions();
        ItemsAdapter adapter = new ItemsAdapter(getContext(),actions);
        listView.setAdapter(adapter);
    }

}
