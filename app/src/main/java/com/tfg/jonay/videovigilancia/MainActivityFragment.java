package com.tfg.jonay.videovigilancia;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v_principal = inflater.inflate(R.layout.fragment_main, container, false);

        Button btn_camera = (Button) v_principal.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_camera = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent_camera);
            }
        });

        Button btn_servidor = (Button) v_principal.findViewById(R.id.btn_servidor);
        btn_servidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_servidor = new Intent(getActivity(), ServidorActivity.class);
                startActivity(intent_servidor);
            }
        });

        Button btn_robot = (Button) v_principal.findViewById(R.id.btn_robot);
        btn_robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_robot = new Intent(getActivity(), RobotActivity.class);
                startActivity(intent_robot);
            }
        });

        return v_principal;
    }
}
