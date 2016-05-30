package pl.chom.lab7;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class CastleActivity extends AppCompatActivity {

    Bundle bundle;
    DatabaseHelper db;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.castle_layout);
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        db = new DatabaseHelper(this);

        ImageView imageViewCastle = (ImageView) findViewById(R.id.imageViewCastle);
        TextView textViewCastle = (TextView) findViewById(R.id.textViewCastle);
        TextView textViewCastleDesc = (TextView) findViewById(R.id.textViewCastleDescription);

        Map<String, String> map;
        bundle = getIntent().getExtras();
        if(bundle != null){
            int id = bundle.getInt("Id");
            map = db.getElement(String.valueOf(id));

            new DownloadImageTask(imageViewCastle).execute(map.get("image").toString());
            textViewCastle.setText(map.get("name").toString());
            textViewCastleDesc.setText(map.get("desc").toString());
        }



    }
}
