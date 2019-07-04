package uz.zokirbekov.e_eye.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uz.zokirbekov.e_eye.R;

public class KeyValueView extends LinearLayout {

    @BindView(R.id.key)
    TextView textKey;

    @BindView(R.id.value)
    TextView textValue;

    private String key = "";
    private String value = "";

    public KeyValueView(Context context) {
        super(context);
        init();
    }

    public KeyValueView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public KeyValueView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    public KeyValueView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
        init();
    }

    private void init()
    {
        inflate(getContext(), R.layout.layout_key_value,this);
        ButterKnife.bind(this,this);

        textKey.setText(key + " : ");
        textValue.setText(value);

    }

    private void initAttrs(AttributeSet attrs)
    {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.KeyValueView,
                0, 0);
        try {
            key = a.getString(R.styleable.KeyValueView_key);
            value = a.getString(R.styleable.KeyValueView_value);
        } finally {
            a.recycle();
        }
    }
}
