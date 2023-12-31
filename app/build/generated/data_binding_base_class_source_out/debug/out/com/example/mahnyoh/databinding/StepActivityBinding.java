// Generated by view binder compiler. Do not edit!
package com.example.mahnyoh.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.mahnyoh.R;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class StepActivityBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FloatingActionButton backButton;

  @NonNull
  public final LineChart lineChart;

  @NonNull
  public final RecyclerView stepCountRecyclerview;

  private StepActivityBinding(@NonNull ConstraintLayout rootView,
      @NonNull FloatingActionButton backButton, @NonNull LineChart lineChart,
      @NonNull RecyclerView stepCountRecyclerview) {
    this.rootView = rootView;
    this.backButton = backButton;
    this.lineChart = lineChart;
    this.stepCountRecyclerview = stepCountRecyclerview;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static StepActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static StepActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.step_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static StepActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backButton;
      FloatingActionButton backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.lineChart;
      LineChart lineChart = ViewBindings.findChildViewById(rootView, id);
      if (lineChart == null) {
        break missingId;
      }

      id = R.id.step_count_recyclerview;
      RecyclerView stepCountRecyclerview = ViewBindings.findChildViewById(rootView, id);
      if (stepCountRecyclerview == null) {
        break missingId;
      }

      return new StepActivityBinding((ConstraintLayout) rootView, backButton, lineChart,
          stepCountRecyclerview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
