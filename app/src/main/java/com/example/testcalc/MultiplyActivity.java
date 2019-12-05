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

import java.util.ArrayList;
import java.util.List;

public class MultiplyActivity extends AppCompatActivity {

    ConstraintLayout thisLayout;
    int row_A = 0;
    int col_A = 0;
    int row_B = 0;
    int col_B = 0;
    String grid_A = "grid_A";
    String grid_B = "grid_B";
    String grid_product = "grid_product";
    int[][] matrixA;
    int[][] matrixB;
    int[][] matrixPRODUCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiply);

        Spinner spinnerA_row = (Spinner) findViewById(R.id.spinner_row_A);
        final Spinner spinnerA_col = (Spinner) findViewById(R.id.spinner_col_A);
        final Spinner spinnerB_row = (Spinner) findViewById(R.id.spinner_row_B);
        Spinner spinnerB_col = (Spinner) findViewById(R.id.spinner_col_B);
        Button btn_calc = (Button) findViewById(R.id.calc_product);

        // WHEN USER CHANGES THE VALUE OF SPINNER_ROW_A, MATRIX DIMENSION SHOULD UPDATE
        spinnerA_row.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                row_A = position + 1;
                setMatrix(grid_A);
                setMatrix(grid_product);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        // WHEN USER CHANGES THE VALUE OF SPINNER_COL_A, MATRIX DIMENSION SHOULD UPDATE
        spinnerA_col.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                col_A = position + 1;
                row_B = col_A;
                setMatrix(grid_A);
                setMatrix(grid_B);
                // update spinner_B_row value
                spinnerB_row.setSelection(row_B - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        // WHEN USER CHANGES THE VALUE OF SPINNER_ROW_B, MATRIX DIMENSION SHOULD UPDATE
        spinnerB_row.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                row_B = position + 1;
                col_A = row_B;
                setMatrix(grid_A);
                setMatrix(grid_B);
                // update spinner_A_col value
                spinnerA_col.setSelection(col_A - 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        // WHEN USER CHANGES THE VALUE OF SPINNER_COL_B, MATRIX DIMENSION SHOULD UPDATE
        spinnerB_col.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                col_B = position + 1;
                setMatrix(grid_B);
                setMatrix(grid_product);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        // WHEN USER CLICK ON BUTTON CALCULATE, MATRIX SHOULD PERFORM ADDITION AND RETURN RESULT
        btn_calc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create matrixA and matrixB with user specified dimension
                matrixA = new int[row_A][col_A];
                matrixB = new int[row_B][col_B];
                matrixPRODUCT = new int[row_A][col_B];
                GridView grid_A = (GridView)findViewById(R.id.grid_A);
                GridView grid_B = (GridView)findViewById(R.id.grid_B);
                GridView grid_product = (GridView)findViewById(R.id.grid_product);

                // Hide the soft keyboard
                thisLayout = (ConstraintLayout) findViewById(R.id.add_layout);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);View focusedView = getCurrentFocus();
                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                // Position counter
                int counter_A = 0;
                int counter_B = 0;
                int counter_SUM = 0;

                // Put values into matrixA
                for(int i=0; i < row_A; i++){
                    for(int j=0; j < col_B; j++){
                        LinearLayout ll = (LinearLayout) grid_A.getChildAt(counter_A);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        String value = et.getText().toString();
                        matrixA[i][j] = Integer.parseInt(value);
                        counter_A++;
                    }
                }

                // Put value into matrixB
                for(int i=0; i < row_B; i++){
                    for(int j=0; j < col_B; j++){
                        LinearLayout ll = (LinearLayout) grid_B.getChildAt(counter_B);
                        EditText et = (EditText)ll.findViewById(R.id.matrix_entry);
                        System.out.println(findViewById(R.id.matrix_entry));
                        String value = et.getText().toString();
                        matrixB[i][j] = Integer.parseInt(value);
                        counter_B++;
                    }
                }

                //Calculate and print matrixPRODUCT in System.out.println
                for (int i=0; i < row_A; i++) {
                    for (int j = 0; j < col_B; j++) {
                        for (int k = 0; k < col_A; k++){
                            matrixPRODUCT[i][j] += matrixA[i][k] * matrixB[k][j] ;
                            System.out.print(matrixPRODUCT[i][j] + " ");
                        }
                    }
                    System.out.println("");
                }

                // SET VALUES FOR MATRIX_SUM
                for(int i=0; i < row_A; i++){
                    for(int j=0; j < col_B; j++){
                        LinearLayout ll = (LinearLayout) grid_product.getChildAt(counter_SUM);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        et.setText(Integer.toString(matrixPRODUCT[i][j]));
                        counter_SUM++;
                    }
                }

            }
        });
    }

    private void setMatrix(final String matrix) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                GridView grid = (GridView) findViewById(R.id.grid_A);
                // INITIALISE YOUR GRID
                if (matrix.equals("grid_B")) {
                    grid = (GridView) findViewById(R.id.grid_B);
                    grid.setNumColumns(col_B);
                    MatrixAdapter adapter = addZerosToMatrix(row_B, col_B);
                    // ATTACH THE ADAPTER TO GRID
                    grid.setAdapter(adapter);
                } else if (matrix.equals("grid_product")) {
                    grid = (GridView) findViewById(R.id.grid_product);
                    grid.setNumColumns(col_B);
                    MatrixAdapter adapter = addZerosToMatrix(row_A, col_B);
                    // ATTACH THE ADAPTER TO GRID
                    grid.setAdapter(adapter);
                } else {
                    grid.setNumColumns(col_A);
                    MatrixAdapter adapter = addZerosToMatrix(row_A, col_A);
                    // ATTACH THE ADAPTER TO GRID
                    grid.setAdapter(adapter);
                }
            }
        });
    }

    private MatrixAdapter addZerosToMatrix(int row, int col){
        // CREATE A LIST OF MATRIX OBJECT
        List<Matrix> matrixList = new ArrayList<>();

        // ADD SOME CONTENTS TO EACH ITEM
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrixList.add(new Matrix(i, j));
            }
        }

        // CREATE AN ADAPTER  (MATRIX ADAPTER)
        MatrixAdapter adapter = new MatrixAdapter(getApplicationContext(), matrixList);

        return adapter;
    }
}
