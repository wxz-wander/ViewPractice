package com.example.viewpractice.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.viewpractice.BaseActivity;
import com.example.viewpractice.R;

/**
 * 作者：wxz11 on 2019/2/15 16:02
 */
public class ModeDetailActivity extends BaseActivity {
    public static void start(Context context, @ModeType.Type int type) {
        Intent intent = new Intent(context, ModeDetailActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", ModeType.TYPE_ANIMATION);
        Fragment fragment = createFragmentWithType(type);
        getSupportFragmentManager().beginTransaction().add(R.id.flt_content, fragment, "detail").commitAllowingStateLoss();
    }

    private Fragment createFragmentWithType(int type) {
        Fragment fragment;
        switch (type) {
            case ModeType.TYPE_ANIMATION:
                fragment = new AnimationFragment();
                break;
            case ModeType.TYPE_ANIMATE:
                //todo 替换
                fragment = new Fragment();
                break;
            case ModeType.TYPE_DRAG_UNLOCK:
                fragment = new DragUnlockFragment();
                break;
            case ModeType.TYPE_NESTED_SCROLLING:
                fragment = new NestedScrollingFragment();
                break;
            case ModeType.TYPE_SWIPE_REFRESH:
                fragment = new Fragment();
                break;
            case ModeType.TYPE_JNI:
                fragment = new JniFragment();
                break;
            default:
                fragment = new Fragment();
                break;
        }
        return fragment;
    }
}
