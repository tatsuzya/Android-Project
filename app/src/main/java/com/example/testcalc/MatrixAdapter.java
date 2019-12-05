package com.example.testcalc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MatrixAdapter extends BaseAdapter {

    Context context;
    List<Double> matrixList_i;
    List<Matrix> matrixList;

    public MatrixAdapter(Context context, List<Double> matrixList, int test) {
        this.context = context;
        this.matrixList_i = matrixList;
    }

    public MatrixAdapter(Context context, List<Matrix> matrixList) {
        this.context = context;
        this.matrixList = matrixList;
    }

    @Override
    public int getCount() {
        return matrixList.size();
    }

    public int getConut_i() {
        return matrixList_i.size();
    }

    @Override
    public Object getItem(int i) {
        return matrixList.get(i);
    }

    public double getItem_i(int i) {
        return matrixList_i.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(context,R.layout.grid_item,null);
        return v;
    }
}
