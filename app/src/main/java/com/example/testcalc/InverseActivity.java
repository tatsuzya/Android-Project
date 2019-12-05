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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class InverseActivity extends AppCompatActivity {

    ConstraintLayout thisLayout;
    int row = 0;
    int col = 0;
    String grid_inverse = "grid_inverse";
    String grid_inverse_result = "grid_inverse_result";
    double[][] matrixA;
    double[][] matrixA_inv;
    DecimalFormat df = new DecimalFormat("#.#####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverse);

        Spinner spinnerDim = (Spinner) findViewById(R.id.spinner_inverse);
        Button btn_calc = (Button) findViewById(R.id.calc_inverse);

        // WHEN USER CHANGES THE VALUE IN THE SPINNER, MATRIX DIMENSION SHOULD UPDATE
        spinnerDim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                row = position + 1;
                col = position + 1;
                setMatrix(grid_inverse);
                setMatrix(grid_inverse_result);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // WHEN USER CLICK ON BUTTON CALCULATE, MATRIX SHOULD PERFORM INVERSE AND RETURN RESULT
        btn_calc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create matrixA with user specified dimension
                matrixA = new double[row][col];
                matrixA_inv = new double[row][col];
                GridView grid_inverse = (GridView)findViewById(R.id.grid_inverse);
                GridView grid_inverse_result = (GridView)findViewById(R.id.grid_inverse_result);

                // Hide the soft keyboard
                thisLayout = (ConstraintLayout) findViewById(R.id.inverse_layout);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                View focusedView = getCurrentFocus();
                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

                // Position counter
                int counter_INV = 0;
                int counter_RESULT = 0;

                // Put values into matrixA
                for(int i=0; i < row; i++){
                    for(int j=0; j < col; j++){
                        LinearLayout ll = (LinearLayout) grid_inverse.getChildAt(counter_INV);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        String value = et.getText().toString();
                        matrixA[i][j] = parseInt(value);
                        counter_INV++;
                    }
                }

                //Print matrixSUM in System.out.println
                for (int i=0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        System.out.print(matrixA[i][j] + " ");
                    }
                    System.out.println("");
                }

                // CREATE A LIST OF MATRIX OBJECT
                List<Double> matrixList_inverse = new ArrayList<>();

                // ADD CONTENTS TO EACH ITEM
                for (int i = 0; i < row; i++)
                {
                    for (int j = 0; j < col; j++)
                    {
                        matrixList_inverse.add(matrixA[i][j]);
                    }
                }

                // CREATE AN ADAPTER  (MATRIX ADAPTER)
                MatrixAdapter adapter = new MatrixAdapter(getApplicationContext(), matrixList_inverse, 1);

                // return determinant of matrix
                System.out.println(determinant(adapter));

                // create if statement to check for dimension 1 2 3
                if(determinant(adapter) == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Determinant = 0, Inverse D.N.E.",
                            Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundColor(Color.RED);
                    toast.show();
                    for(int i = 0; i < row; i++){
                        for(int j = 0; j < col; j++){
                            matrixA_inv[i][j] = 0.0/0.0;
                        }
                    }
                } else {
                    if(adapter.getConut_i() == 1){
                        double a = 1.0 / matrixA[0][0];
                        matrixA_inv[0][0] = Double.parseDouble(df.format(a));
                    } else if(adapter.getConut_i() == 4){
                        double a = (1.0 / determinant(adapter)) * adapter.getItem_i(3);
                        double b = (1.0 / determinant(adapter)) * adapter.getItem_i(1) * -1.0;
                        double c = (1.0 / determinant(adapter)) * adapter.getItem_i(2) * -1.0;
                        double d = (1.0 / determinant(adapter)) * adapter.getItem_i(0);
                        matrixA_inv[0][0] = Double.parseDouble(df.format(a));
                        matrixA_inv[0][1] = Double.parseDouble(df.format(b));
                        matrixA_inv[1][0] = Double.parseDouble(df.format(c));
                        matrixA_inv[1][1] = Double.parseDouble(df.format(d));
                    } else if(adapter.getConut_i() == 9){
                        double a = (1.0 / determinant(adapter)) * ((adapter.getItem_i(4) * adapter.getItem_i(8)) - (adapter.getItem_i(5) * adapter.getItem_i(7)));
                        double b = (1.0 / determinant(adapter)) * ((adapter.getItem_i(1) * adapter.getItem_i(8)) - (adapter.getItem_i(2) * adapter.getItem_i(7))) * -1.0;
                        double c = (1.0 / determinant(adapter)) * ((adapter.getItem_i(1) * adapter.getItem_i(5)) - (adapter.getItem_i(2) * adapter.getItem_i(4)));
                        double d = (1.0 / determinant(adapter)) * ((adapter.getItem_i(3) * adapter.getItem_i(8)) - (adapter.getItem_i(5) * adapter.getItem_i(6))) * -1.0;
                        double e = (1.0 / determinant(adapter)) * ((adapter.getItem_i(0) * adapter.getItem_i(8)) - (adapter.getItem_i(2) * adapter.getItem_i(6)));
                        double f = (1.0 / determinant(adapter)) * ((adapter.getItem_i(0) * adapter.getItem_i(5)) - (adapter.getItem_i(2) * adapter.getItem_i(3))) * -1.0;
                        double g = (1.0 / determinant(adapter)) * ((adapter.getItem_i(3) * adapter.getItem_i(7)) - (adapter.getItem_i(4) * adapter.getItem_i(6)));
                        double h = (1.0 / determinant(adapter)) * ((adapter.getItem_i(0) * adapter.getItem_i(7)) - (adapter.getItem_i(1) * adapter.getItem_i(6))) * -1.0;
                        double i = (1.0 / determinant(adapter)) * ((adapter.getItem_i(0) * adapter.getItem_i(4)) - (adapter.getItem_i(1) * adapter.getItem_i(3)));
                        matrixA_inv[0][0] = Double.parseDouble(df.format(a));
                        matrixA_inv[0][1] = Double.parseDouble(df.format(b));
                        matrixA_inv[0][2] = Double.parseDouble(df.format(c));
                        matrixA_inv[1][0] = Double.parseDouble(df.format(d));
                        matrixA_inv[1][1] = Double.parseDouble(df.format(e));
                        matrixA_inv[1][2] = Double.parseDouble(df.format(f));
                        matrixA_inv[2][0] = Double.parseDouble(df.format(g));
                        matrixA_inv[2][1] = Double.parseDouble(df.format(h));
                        matrixA_inv[2][2] = Double.parseDouble(df.format(i));
                    }
                }
                // SET VALUES FOR matrixA_inv
                for(int row_i = 0; row_i < row; row_i++){
                    for(int row_j=0; row_j < col; row_j++){
                        LinearLayout ll = (LinearLayout) grid_inverse_result.getChildAt(counter_RESULT);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        et.setText(Double.toString(matrixA_inv[row_i][row_j]));
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

                GridView grid = (GridView)findViewById(R.id.grid_inverse);
                if (matrix.equals("grid_inverse_result")) {
                    grid = (GridView)findViewById(R.id.grid_inverse_result);
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

    public static double determinant(MatrixAdapter matrix) {
        double det = 0;

        if (matrix.getConut_i() == 1) {
            det = matrix.getItem_i(0);
        }
        if (matrix.getConut_i() == 4) {
            det = (matrix.getItem_i(0) * matrix.getItem_i(3)) - (matrix.getItem_i(1) * matrix.getItem_i(2));
        }
        if(matrix.getConut_i() == 9) {
            double a = matrix.getItem_i(0) * ((matrix.getItem_i(4) * matrix.getItem_i(8)) - (matrix.getItem_i(5) * matrix.getItem_i(7)));
            double b = matrix.getItem_i(1) * ((matrix.getItem_i(3) * matrix.getItem_i(8)) - (matrix.getItem_i(5) * matrix.getItem_i(6)));
            double c = matrix.getItem_i(2) * ((matrix.getItem_i(3) * matrix.getItem_i(7)) - (matrix.getItem_i(4) * matrix.getItem_i(6)));
            det = a - b + c;
        }
        return det;
    }

}
