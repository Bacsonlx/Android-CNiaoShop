package hznu.linxin.cniaoshop.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import hznu.linxin.cniaoshop.R;


/**
 * @author BacSon
 */
public class CategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_category,container,false);
    }
}



