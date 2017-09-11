package com.owenlejeune.gpacalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    private Spinner numClassesSpin;
    private int numClasses;
    private ArrayList<FragmentGrade> gradeFragments;
    private Integer[] numberClasses = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    //private FrameLayout[] containers;
    private int[] ids = new int[]{R.id.content1, R.id.content2, R.id.content3, R.id.content4, R.id.content5, R.id.content6, R.id.content7, R.id.content8, R.id.content9, R.id.content10};
    private Button calculate;
    //private HashMap<Integer, Double> gpaValues;
    private ArrayList<double[]> gpaValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gradeFragments = new ArrayList<>();
        gpaValues = new ArrayList<>();

//        FrameLayout fm1 = (FrameLayout)findViewById(R.id.content1);
//        FrameLayout fm2 = (FrameLayout)findViewById(R.id.content2);
//        FrameLayout fm3 = (FrameLayout)findViewById(R.id.content3);
//        FrameLayout fm4 = (FrameLayout)findViewById(R.id.content4);
//        FrameLayout fm5 = (FrameLayout)findViewById(R.id.content5);
//        FrameLayout fm6 = (FrameLayout)findViewById(R.id.content6);
//        FrameLayout fm7 = (FrameLayout)findViewById(R.id.content7);
//        FrameLayout fm8 = (FrameLayout)findViewById(R.id.content8);
//        FrameLayout fm9 = (FrameLayout)findViewById(R.id.content9);
//        FrameLayout fm10 = (FrameLayout)findViewById(R.id.content10);
//
//        containers = new FrameLayout[]{fm1, fm2, fm3, fm4, fm5, fm6, fm7, fm8, fm9, fm10};

        numClassesSpin = (Spinner)findViewById(R.id.number_classes);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.number_classes, android.R.layout.simple_spinner_dropdown_item);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.number_classes, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, numberClasses);
        numClassesSpin.setAdapter(adapter);
        numClassesSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numClasses = Integer.parseInt(numClassesSpin.getItemAtPosition(i).toString());
                updateFragments();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("Spinner", "Nothing Selected");
            }
        });

        calculate = (Button)findViewById(R.id.calculate_button);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createValsMap();
                calculateGPA();
            }
        });
    }

    public void clearFragments(){
        if(gradeFragments.size() > 0){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            for(int i = 0; i < gradeFragments.size(); i++){
                ft.remove(gradeFragments.get(i));
            }
            gradeFragments.clear();
            ft.commit();
        }
    }

    public void updateFragments(){
        clearFragments();

        for(int i = 0; i < numClasses; i++){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment fragment = fm.findFragmentById(ids[i]);
            FragmentGrade frag = new FragmentGrade();

            if(fragment == null){
                ft.add(ids[i], frag, "GradeFragment");
                gradeFragments.add(frag);
            }else{
                ft.add(ids[i], frag, "GradeFragment");
                gradeFragments.add(frag);
            }


            ft.commit();
        }
    }

    public void createValsMap(){
        gpaValues.clear();
//        Toast.makeText(this, "" + gradeFragments.size(), Toast.LENGTH_SHORT).show();
        for(FragmentGrade fg : gradeFragments){
            double[] temp = fg.getVals();
            gpaValues.add(temp);
        }
    }

    public void calculateGPA(){
        double gpa = 0;
        double runningTotal = 0;
        double runningCredits = 0;

        for(double[] d : gpaValues){
            runningTotal += (d[0]*d[1]);
            runningCredits += d[1];
        }

        gpa = runningTotal/runningCredits;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("GPA: " + gpa);
        alert.setPositiveButton("Ok", null);
        alert.show();
    }
}
