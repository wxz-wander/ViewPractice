package com.example.viewpractice.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.viewpractice.R;
import com.example.viewpractice.detail.ModeDetailActivity;
import com.example.viewpractice.detail.ModeType;

/**
 * 作者：wxz11 on 2019/2/15 14:09
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lvMain;
//    private static final String[] demos = {
//            "view的补间动画",
//            "view的属性动画"
//    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        lvMain = view.findViewById(R.id.lv_main);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mode));
        lvMain.setAdapter(arrayAdapter);
        lvMain.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                ModeDetailActivity.start(getContext(), ModeType.TYPE_ANIMATION);
                break;
            case 1:
                ModeDetailActivity.start(getContext(), ModeType.TYPE_ANIMATE);
                break;
            case 2:
                ModeDetailActivity.start(getContext(), ModeType.TYPE_DRAG_UNLOCK);
                break;
            case 3:
                ModeDetailActivity.start(getContext(), ModeType.TYPE_NESTED_SCROLLING);
                break;
            case 4:
                ModeDetailActivity.start(getContext(), ModeType.TYPE_SWIPE_REFRESH);
                break;
            case 5:
                ModeDetailActivity.start(getContext(), ModeType.TYPE_JNI);
                break;
        }
    }
}
