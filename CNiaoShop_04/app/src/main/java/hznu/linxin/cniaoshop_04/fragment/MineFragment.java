package hznu.linxin.cniaoshop_04.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hznu.linxin.cniaoshop_04.R;


/**
 * @author BacSon
 */
public class MineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return  inflater.inflate(R.layout.fragment_mine,container,false);
    }
}
