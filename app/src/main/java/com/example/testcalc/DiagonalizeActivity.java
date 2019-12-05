package com.example.testcalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.EigenvalueDecomposition;

import static java.lang.Integer.parseInt;

public class DiagonalizeActivity extends AppCompatActivity {

    int row = 0;
    int col = 0;
    String grid_diag = "grid_diag";
    String grid_diag_result = "grid_diag_result";
    ConstraintLayout thisLayout;
    Jama.Matrix matrixA;
    DecimalFormat df = new DecimalFormat("#.#####");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagonalize);

        Spinner spinnerDim = (Spinner) findViewById(R.id.spinner_diag);
        Button btn_calc = (Button) findViewById(R.id.calc_diag);

        // WHEN USER CHANGES THE VALUE IN THE SPINNER, MATRIX DIMENSION SHOULD UPDATE
        spinnerDim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                row = position + 1;
                col = position + 1;
                setMatrix(grid_diag);
                setMatrix(grid_diag_result);
                matrixA = new Jama.Matrix(row,col); // may need matrixA_result
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // WHEN USER CLICK ON BUTTON CALCULATE, MATRIX SHOULD PERFORM INVERSE AND RETURN RESULT
        btn_calc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GridView grid_eigenvalues = (GridView)findViewById(R.id.grid_diag);
                GridView grid_inverse_result = (GridView)findViewById(R.id.grid_diag_result);

                // Hide the soft keyboard
                thisLayout = (ConstraintLayout) findViewById(R.id.diagonalize_layout);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                View focusedView = getCurrentFocus();
                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

                // Position counter
                int counter_EIG = 0;
                int counter_RESULT = 0;

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

                // Get the Eigenvalue decomposition
                EigenvalueDecomposition eigen = matrixA.eig();
                double [] realPart = eigen.getRealEigenvalues();
                double [] imagPart = eigen.getImagEigenvalues();
                Jama.Matrix eigenDiag = eigen.getD();

                // print eigen vector
                eigenDiag.print(8 ,5 );

                // Set text to show result
                for(int i = 0; i < realPart.length; i++){
                    for(int j = 0; j < realPart.length; j++){
                        LinearLayout ll = (LinearLayout) grid_inverse_result.getChildAt(counter_RESULT);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        et.setText(df.format(eigenDiag.get(i, j)));
                        counter_RESULT++;
                    }
                }

            }
        });

    }

    private void setMatrix(final String matrix) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                GridView grid = (GridView)findViewById(R.id.grid_diag);
                if (matrix.equals("grid_diag_result")) {
                    grid = (GridView)findViewById(R.id.grid_diag_result);
                }
                // INITIALISE YOUR GRID
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
