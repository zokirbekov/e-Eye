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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.R;

public class StatisticsFragment extends Fragment {

    @BindView(R.id.barchart)
    BarChart chart;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics,container,false);
        ButterKnife.bind(this,v);
        initChart();
        return v;
    }

    private void initChart()
    {
        ArrayList n = new ArrayList();
        n.add(new BarEntry(10, new float[] {1,2,3,4,5,6}));
        n.add(new BarEntry(20, new float[] {1,2,3,4,5,6}));
        n.add(new BarEntry(30, new float[] {1,2,3,4,5,6}));

        ArrayList<String> year = new ArrayList<>();
        year.add("1");
        year.add("2");
        year.add("3");

        BarDataSet dataSet = new BarDataSet(n,"Qwerty");
        chart.animateY(5000);
        BarData data = new BarData(dataSet);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        chart.setData(data);
}

}
