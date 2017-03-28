package cs.unbroomfinder.ClassView;

/**
 * Created by kylebrideau on 2017-02-18.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cs.unbroomfinder.R;


public class ClassFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.class_tabs, container, false);

        Button btn = (Button) rootView.findViewById(R.id.btn_add_class);
        btn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AddClassActivity.class);
        startActivity(intent);
    }
}
