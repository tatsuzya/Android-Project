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

public class AddActivity extends AppCompatActivity {

    ConstraintLayout thisLayout;
    int row = 0;
    int col = 0;
    String grid = "grid";
    String grid2 = "grid2";
    String grid_sum = "grid_sum";
    int[][] matrixA;
    int[][] matrixB;
    int[][] matrixSUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner spinnerRow = (Spinner) findViewById(R.id.spinner_row);
        Spinner spinnerCol = (Spinner) findViewById(R.id.spinner_col);
        Button btn_calc = (Button) findViewById(R.id.calc_product);

        // WHEN USER CHANGES THE VALUE IN THE SPINNER, MATRIX DIMENSION SHOULD UPDATE
        spinnerRow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                row = position + 1;
                setMatrix(grid);
                setMatrix(grid2);
                setMatrix(grid_sum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        // WHEN USER CHANGED THE VALUE IN THE SPINNER, MATRIX DIMENSION SHOULD UPDATE
        spinnerCol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                col = position + 1;
                setMatrix(grid);
                setMatrix(grid2);
                setMatrix(grid_sum);
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
                matrixA = new int[row][col];
                matrixB = new int[row][col];
                matrixSUM = new int[row][col];
                GridView grid = (GridView)findViewById(R.id.grid_A);
                GridView grid2 = (GridView)findViewById(R.id.grid_B);
                GridView grid_sum = (GridView)findViewById(R.id.grid_product);

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
                for(int i=0; i < row; i++){
                    for(int j=0; j < col; j++){
                        LinearLayout ll = (LinearLayout) grid.getChildAt(counter_A);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        String value = et.getText().toString();
                        matrixA[i][j] = Integer.parseInt(value);
                        counter_A++;
                    }
                }

                // Put value into matrixB
                for(int i=0; i < row; i++){
                    for(int j=0; j < col; j++){
                        LinearLayout ll = (LinearLayout) grid2.getChildAt(counter_B);
                        EditText et = (EditText)ll.findViewById(R.id.matrix_entry);
                        System.out.println(findViewById(R.id.matrix_entry));
                        String value = et.getText().toString();
                        matrixB[i][j] = Integer.parseInt(value);
                        counter_B++;
                    }
                }

                //Calculate and print matrixSUM in System.out.println
                for (int i=0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        matrixSUM[i][j] = matrixA[i][j] + matrixB[i][j];
                        System.out.print(matrixSUM[i][j] + " ");
                    }
                    System.out.println("");
                }

                // SET VALUES FOR MATRIX_SUM
                for(int i=0; i < row; i++){
                    for(int j=0; j < col; j++){
                        LinearLayout ll = (LinearLayout) grid_sum.getChildAt(counter_SUM);
                        EditText et = (EditText) ll.findViewById(R.id.matrix_entry);
                        et.setText(Integer.toString(matrixSUM[i][j]));
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

                GridView grid = (GridView)findViewById(R.id.grid_A);
                // INITIALISE YOUR GRID
                if (matrix.equals("grid2")) {
                    grid = (GridView)findViewById(R.id.grid_B);
                } else if (matrix.equals("grid_sum")){
                    grid = (GridView)findViewById(R.id.grid_product);
                }
                grid.setNumColumns(col);

                // CREATE A LIST OF MATRIX OBJECT
                List<Matrix> matrixList = new ArrayList<>();

                // ADD SOME CONTENTS TO EACH ITEM
                for (int i = 0; i < row; i++)
                {
                    for (int j = 0; j < col; j++)
                    {
                        matrixList.add(new Matrix(i,j));
                    }
                }

                // CREATE AN ADAPTER  (MATRIX ADAPTER)
                MatrixAdapter adapter = new MatrixAdapter(getApplicationContext(),matrixList);

                // ATTACH THE ADAPTER TO GRID
                grid.setAdapter(adapter);
            }
        });
    }

}
