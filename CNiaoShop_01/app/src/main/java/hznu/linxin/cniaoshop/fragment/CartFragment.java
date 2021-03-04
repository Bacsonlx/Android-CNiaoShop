package hznu.linxin.cniaoshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import hznu.linxin.cniaoshop.R;


/**
 * @author BacSon
 */
public class CartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_cart,container,false);
    }
}
