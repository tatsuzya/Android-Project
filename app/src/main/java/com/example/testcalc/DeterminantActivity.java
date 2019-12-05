package com.example.testcalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import Jama.*;

import static java.lang.Integer.parseInt;

public class DeterminantActivity extends AppCompatActivity {

    int row = 0;
    int col = 0;
    ConstraintLayout thisLayout;
    Jama.Matrix matrixA;
    DecimalFormat df = new DecimalFormat("#.#####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_determinant);

        Spinner spinnerDim = (Spinner) findViewById(R.id.spinner_det);
        Button btn_calc = (Button) findViewById(R.id.calc_det);
        final TextView result = (TextView) findViewById(R.id.textView_det_result);

        // WHEN USER CHANGES THE VALUE IN THE SPINNER, MATRIX DIMENSION SHOULD UPDATE
        spinnerDim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                row = position + 1;
                col = position + 1;
                setMatrix();
                matrixA = new Jama.Matrix(row,col);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // WHEN USER CLICK ON BUTTON CALCULATE, MATRIX SHOULD PERFORM INVERSE AND RETURN RESULT
        btn_calc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GridView grid_eigenvalues = (GridView)findViewById(R.id.grid_det);

                // Hide the soft keyboard
                thisLayout = (ConstraintLayout) findViewById(R.id.determinant_layout);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                View focusedView = getCurrentFocus();
                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

                // Position counter
                int counter_EIG = 0;

                // Put values into matrixA
                for(int i = 0; i < row; i++) {
                    for(int j = 0; j < col; j++) {
                        LinearLayout ll = (LinearLayout) grid_eigenvalues.getChildAt(counter_EIG);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        int temp = parseInt(et.getText().toString());
                        double val = temp * 1.0;
                        matrixA.set(i, j, val);
                        // matrixA.set(j, i, val);
                        counter_EIG++;
                    }
                }

                //Print matrixA in System.out.println
                for (int i=0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.print(matrixA.get(i, j) + " ");
                    }
                    System.out.println("");
                }

                // Set text to show result
                result.setText(df.format(matrixA.det()));

            }
        });

    }

    private void setMatrix() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                GridView grid = (GridView)findViewById(R.id.grid_det);
                grid.setNumColumns(col);

                // CREATE A LIST OF MATRIX OBJECT
                List<Matrix> matrixList_inverse = new ArrayList<>();

                // ADD SOME CONTENTS TO EACH ITEM
                for (int i = 0; i < row; i++)
                {
                    for (int j = 0; j < col; j++)
                    {
                        matrixList_inverse.add(new Matrix(i,j));
                    }
                }

                // CREATE AN ADAPTER  (MATRIX ADAPTER)
                MatrixAdapter adapter = new MatrixAdapter(getApplicationContext(), matrixList_inverse);

                // ATTACH THE ADAPTER TO GRID
                grid.setAdapter(adapter);
            }
        });
    }

}
