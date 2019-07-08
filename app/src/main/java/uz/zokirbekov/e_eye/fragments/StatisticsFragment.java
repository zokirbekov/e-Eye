package uz.zokirbekov.e_eye.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import uz.zokirbekov.e_eye.R;
import uz.zokirbekov.e_eye.managers.DbManager;
import uz.zokirbekov.e_eye.models.Action;

public class StatisticsFragment extends Fragment {

    @BindView(R.id.barchart)
    LineChart chart;

    List<Entry> confirmed;
    List<Entry> unconfirmed;
    List<Action> actions;

    List<Entry> in_progress;

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
        confirmed = new ArrayList<>();
        unconfirmed = new ArrayList<>();
        in_progress = new ArrayList<>();

        insertActionsByStatus(confirmed,DbManager.CONFIRMED,false);
        insertActionsByStatus(unconfirmed,DbManager.UNCONFIRMED,false);
        insertActionsByStatus(in_progress,DbManager.IN_PROGRESS, true);
    }

    private void drawDatas()
    {
        List<ILineDataSet> dataSets = new ArrayList<>();

        LineDataSet confirmedSet = new LineDataSet(confirmed, "Confirmed");
        LineDataSet unconfirmedSet = new LineDataSet(unconfirmed, "Unxonfirmed");
        LineDataSet inProgressSet = new LineDataSet(in_progress, "In progress");

        confirmedSet.setColor(ContextCompat.getColor(getContext(),R.color.colorConfirmed));
        unconfirmedSet.setColor(ContextCompat.getColor(getContext(),R.color.colorUnconfirmed));
        inProgressSet.setColor(ContextCompat.getColor(getContext(),R.color.colorInProgress));

        dataSets.add(confirmedSet);
        dataSets.add(unconfirmedSet);
        dataSets.add(inProgressSet);

        chart.setData(new LineData(dataSets));
        chart.invalidate();
    }

    private void insertActionsByStatus(List<Entry> data, int status, boolean isLast)
    {
        String pattern = "MM.yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        Disposable d =  Observable.fromIterable(actions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(act -> act.getStatus() == status)
                .groupBy(act -> format.format( act.getCreate_date() ) )
                .flatMap( gr -> gr.count()
                                .map(count -> new Pair(gr.getKey(),count))
                                .toObservable())
                .doOnTerminate(
                        () -> {
                            if (isLast)
                                drawDatas();
                        }
                )
                .subscribe(pair -> {
                    long time = format.parse(pair.first.toString()).getTime();
                    long count = (Long)pair.second;
                    data.add(new Entry(time,count));
                });

        System.out.println();
                //.map(x -> )
                //.count();

        //g.subscribe(x -> Toast.makeText(getContext(), String.valueOf(x), Toast.LENGTH_SHORT).show());

    }

}
