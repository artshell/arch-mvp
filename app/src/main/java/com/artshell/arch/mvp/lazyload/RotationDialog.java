package com.artshell.arch.mvp.lazyload;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;

import com.arch.mvp.BaseAnnotatedDialogFragment;
import com.arch.mvp.BaseContract;
import com.arch.mvp.Viewable;
import com.artshell.arch.mvp.R;

/**
 * @author artshell on 2018/6/1
 */

@Viewable(presenter = RotationPresenter.class, layout = R.layout.fragment_rotation_dialog)
public class RotationDialog extends BaseAnnotatedDialogFragment<BaseContract.View, BaseContract.Presenter<BaseContract.View>> {

    private static final String TAG = "RotationDialog";

    public static RotationDialog newInstance() {
        return new RotationDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedState) {
        // 关闭title
        // 直接调用 Dialog#requestWindowFeature() 方法无效
        AppCompatDialog dialog = (AppCompatDialog) super.onCreateDialog(savedState);
        dialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        return dialog;
    }

    public static void showDialog(@NonNull FragmentManager manager) {
        RotationDialog.newInstance().show(manager, TAG);
    }

    public static void dismissDialog(@NonNull FragmentManager manager) {
        RotationDialog fragment = (RotationDialog) manager.findFragmentByTag(TAG);
        if (fragment != null && fragment.isAdded()) {
            fragment.dismissAllowingStateLoss();
        }
    }
}
