package com.owenlejeune.gpacalculator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;

/**
 * Created by owenlejeune on 2017-05-09.
 */

public class FragmentGrade extends Fragment {

    private Activity mActivity;
    private View view;
    private Spinner letterGradeSpin, creditHoursSpin;
    private double creditHours;
    private double letterGradeInt;
    private final HashMap<String, Double> gpaPoints = new HashMap<>();
    private Double[] gradeHours = new Double[]{0.5, 1d};

    public void onAttach(Activity act){
        super.onAttach(act);

        mActivity = act;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        view = inflater.inflate(R.layout.grade_input_row, container, false);

        gpaPoints.put("A+", 12d);
        gpaPoints.put("A", 11d);
        gpaPoints.put("A-", 10d);
        gpaPoints.put("B+", 9d);
        gpaPoints.put("B", 8d);
        gpaPoints.put("B-", 7d);
        gpaPoints.put("C+", 6d);
        gpaPoints.put("C", 5d);
        gpaPoints.put("C-", 4d);
        gpaPoints.put("D+", 3d);
        gpaPoints.put("D", 2d);
        gpaPoints.put("D-", 1d);
        gpaPoints.put("D", 0d);

        letterGradeSpin = (Spinner)view.findViewById(R.id.letter_grade_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(mActivity, R.array.letter_grades, android.R.layout.simple_spinner_dropdown_item);
        //ArrayAdapter<Double> adapter = new ArrayAdapter<>(mActivity, android.R.layout.simple_dropdown_item_1line, gradeHours);
        letterGradeSpin.setAdapter(adapter);
        letterGradeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String grade = letterGradeSpin.getItemAtPosition(i).toString();
                letterGradeInt = gpaPoints.get(grade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Spinner", "Nothing Selected");
            }
        });

        creditHoursSpin = (Spinner)view.findViewById(R.id.credit_spinner);
        //adapter = ArrayAdapter.createFromResource(mActivity, R.array.grade_hours, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Double> adapter2 = new ArrayAdapter<Double>(mActivity, android.R.layout.simple_dropdown_item_1line, gradeHours);
        creditHoursSpin.setAdapter(adapter2);
        creditHoursSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cred = creditHoursSpin.getItemAtPosition(i).toString();
                creditHours = Double.parseDouble(cred);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public double[] getVals(){
        return new double[]{letterGradeInt, creditHours};
    }

}
