package com.trodev.trovato.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.trodev.trovato.R;
import com.trodev.trovato.adapters.SlidersAdapter;

public class HomeFragment extends Fragment {

    SliderView sliderView;
    int[] images = {R.drawable.trovato,
            R.drawable.bg_image_001,
            R.drawable.course_image,
            R.drawable.courses,
            R.drawable.love_motion,
            R.drawable.courses};

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = view.findViewById(R.id.image_slider);

        SlidersAdapter sliderAdapter = new SlidersAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        return view;
    }
}