package edu.tomerbu.calc;

import android.widget.SeekBar;

public interface P extends SeekBar.OnSeekBarChangeListener {
    @Override
    default void onStartTrackingTouch(SeekBar seekBar){}

    @Override
    default void onStopTrackingTouch(SeekBar seekBar){}
}
