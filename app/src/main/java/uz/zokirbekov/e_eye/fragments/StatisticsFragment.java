package uz.zokirbekov.e_eye.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;

public class StatisticsFragment extends Fragment {

    @BindView(R.id.barchart)
    BarChart chart;

    List<Action> actions;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics,container,false);
        ButterKnife.bind(this,v);
        actions = DbManager.getInstance(getContext()).getAllActions();
        initChart();
        return v;
    }

    private void initChart()
    {
        List<Entry> confirmed = new ArrayList<>();
        List<Entry> unconfirmed = new ArrayList<>();
        List<Entry> in_progress = new ArrayList<>();


    }

    private void insertActionsByStatus(List<Entry> data, int status)
    {
        String pattern = "dd.MM.yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Observable.fromIterable(actions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(act -> act.getStatus() == status)
                .groupBy(act -> act.getCreate_date());
    }

}
