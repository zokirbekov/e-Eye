package uz.zokirbekov.e_eye.utils;

import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;

public class HourAxisValueFormatter extends DefaultAxisValueFormatter {
    /**
     * Constructor that specifies to how many digits the value should be
     * formatted.
     *
     * @param digits
     */
    public HourAxisValueFormatter(int digits) {
        super(digits);
    }

}
