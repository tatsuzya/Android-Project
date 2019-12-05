package com.example.testcalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import com.example.testcalc.*;

public class MainActivity extends AppCompatActivity {

    private ArrayList<NavItem> mNavList;
    private RecyclerView mRecyclerView;
    private NavAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNavList();
        buildRecyclerView();
    }

    public void createNavList(){
        mNavList =  new ArrayList<>();
        mNavList.add(new NavItem(R.drawable.ic_add, "ADD", "This is for adding two matrices of the same dimension."));
        mNavList.add(new NavItem(R.drawable.ic_subtract, "SUBTRACT", "This is for subtracting two matrices of the same dimension."));
        mNavList.add(new NavItem(R.drawable.ic_multiply, "MULTIPLY", "This is for multiplying two matrices."));
        mNavList.add(new NavItem(R.drawable.ic_sentiment_very_satisfied_black_24dp, "DETERMINANT", "This is for finding the determinant of a matrix."));
        mNavList.add(new NavItem(R.drawable.ic_inverse, "INVERSE", "This is for finding the inverse of a matrix."));
        mNavList.add(new NavItem(R.drawable.ic_sentiment_neutral_black_24dp, "DIAGONALIZE", "This is for diagonalizing a matrix."));
        mNavList.add(new NavItem(R.drawable.ic_sentiment_dissatisfied_black_24dp, "EIGENVALUES", "This is for finding the eigenvalues of a matrix."));
        mNavList.add(new NavItem(R.drawable.ic_sentiment_very_dissatisfied_black_24dp, "EIGENVECTORS", "This is for finding the eigenvectors of a matrix."));
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NavAdapter(mNavList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new NavAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, SubtractActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, MultiplyActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(MainActivity.this, DeterminantActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, InverseActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, DiagonalizeActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(MainActivity.this, EigenvaluesActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(MainActivity.this, EigenvectorsActivity.class);
                        startActivity(intent);
                        break;


                    default:
                        break;
                }
            }

        });
    }
}
