package com.reconinstruments.metricsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.reconinstruments.os.metrics.HUDMetricsID;

public class ExternalSensorActivity extends Activity {

    MetricView mHeartRate = null;
    MetricView mSpeedCadenceCadence = null;
    MetricView mSpeedCadenceSpeed = null;
    MetricView mSpeed = null;
    MetricView mCadence = null;

    ExternalSensorModel mExternalSensorModel;

    private static final float WHEEL_CIRCUMFERENCE = 2.07f; // in meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extsensors);

        mHeartRate = new MetricView((TextView)findViewById(R.id.HeartRateCurrent), (TextView)findViewById(R.id.HeartRatePrevious));
        mSpeedCadenceCadence = new MetricView((TextView)findViewById(R.id.SpeedCadenceCadenceCurrent), (TextView)findViewById(R.id.SpeedCadenceCadencePrevious));
        mSpeedCadenceSpeed = new MetricView((TextView)findViewById(R.id.SpeedCadenceSpeedCurrent), (TextView)findViewById(R.id.SpeedCadenceSpeedPrevious));
        mSpeed = new MetricView((TextView)findViewById(R.id.SpeedOnlyCurrent), (TextView)findViewById(R.id.SpeedOnlyPrevious));
        mCadence = new MetricView((TextView)findViewById(R.id.CadenceOnlyCurrent), (TextView)findViewById(R.id.CadenceOnlyPrevious));

        mExternalSensorModel = new ExternalSensorModel(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        mExternalSensorModel.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        mExternalSensorModel.onPause();
    }

    public void UpdateValueChangeText(int metricID, float value) {
        MetricView metricView = GetData(metricID);
        if (metricView != null) {
            if (metricID == HUDMetricsID.SPEED_EXT || metricID == HUDMetricsID.SPEED_CADENCE_SPEED) {
                // Speed is in rotations per minute. Convert to km/hr.
                value = value * WHEEL_CIRCUMFERENCE * 60f * 0.001f;
            }
            metricView.addValue(value);
        }
    }

    private MetricView GetData(int metricID) {
        if (metricID == HUDMetricsID.HEART_RATE) {
            return mHeartRate;
        } else if (metricID == HUDMetricsID.SPEED_CADENCE_CADENCE) {
            return mSpeedCadenceCadence;
        } else if (metricID == HUDMetricsID.SPEED_CADENCE_SPEED) {
            return mSpeedCadenceSpeed;
        } else if (metricID == HUDMetricsID.SPEED_EXT) {
            return mSpeed;
        } else if (metricID == HUDMetricsID.CADENCE_EXT) {
            return mCadence;
        }
        return null;
    }
}
