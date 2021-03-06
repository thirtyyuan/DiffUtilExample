package com.sample.diffutil;

import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRecyclerViewAdapter extends
        RecyclerView.Adapter<EmployeeRecyclerViewAdapter
                .ViewHolder> {

    private List<Employee> mEmployees = new ArrayList<>();

    public EmployeeRecyclerViewAdapter(List<Employee> employeeList) {
        this.mEmployees.addAll(employeeList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Employee employee = mEmployees.get(position);
        holder.name.setText(employee.getName());
        holder.role.setText(employee.getRole());
        Log.d("MyTag", "onBindViewHolder() position=" + position + ", employee=" + employee);
    }

    public void updateEmployeeListItems(List<Employee> employees) {
        final EmployeeDiffCallback diffCallback = new EmployeeDiffCallback(this.mEmployees, employees);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.mEmployees.clear();
        this.mEmployees.addAll(employees);
        for (int i = 0; i < employees.size(); i++) {
            Log.d("MyTag", "updateList() i=" + i + ", employee=" + employees.get(i));
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView role;
        private final TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.employee_name);
            role = (TextView) itemView.findViewById(R.id.employee_role);
        }
    }
}
