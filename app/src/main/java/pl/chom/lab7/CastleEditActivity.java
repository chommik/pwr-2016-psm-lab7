package pl.chom.lab7;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class CastleEditActivity extends AppCompatActivity {

    DatabaseHelper db;
    Bundle bundle;
    Integer id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        // delaying the hiding of the ActionBar
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().hide();
            }
        });
        setContentView(R.layout.castle_edit_activity);

        final EditText editTextName = (EditText) findViewById(R.id.editTextCastle);
        final EditText editTextDescription = (EditText) findViewById(R.id.editTextCastleDesctiption);
        final EditText editTextImageSrc = (EditText) findViewById(R.id.editImageCastle);
        final EditText editTextLat = (EditText) findViewById(R.id.editTextLat);
        final EditText editTextLong = (EditText) findViewById(R.id.editTextLong);
        Button buttonSave = (Button) findViewById(R.id.buttonSaveEdit);

        db = new DatabaseHelper(this);
        Map<String, String> map;
        bundle = getIntent().getExtras();
        if(getIntent().hasExtra("Id")){
            id = bundle.getInt("Id");
            map = db.getElement(String.valueOf(id));
            editTextName.setText(map.get("name").toString());
            editTextDescription.setText(map.get("desc").toString());
            editTextImageSrc.setText(map.get("image").toString());
            editTextLat.setText(map.get("lat").toString());
            editTextLong.setText(map.get("long").toString());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id == null) {
                    String str = editTextImageSrc.getText().toString();
                    if(!str.contains("http://")){
                        str = "https://lh3.ggpht.com/lAjUqEGdt1ZOOcKMRALBOVW3sj1NgDy6OiKo60fK6Sp4BjWPZvwv9jOi4K4NZrJATtE=w300";
                    }
                    boolean isInserted = db.insert(editTextName.getText().toString(), editTextDescription.getText().toString(), str,
                            editTextLat.getText().toString(), editTextLong.getText().toString());
                    if (isInserted) {
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                        MainActivity.mAdapter.notifyDataSetChanged();
                    }
                    else {Toast.makeText(getApplicationContext(), "NOT Inserted", Toast.LENGTH_SHORT).show();}
                }
                else if(id != null){
                    boolean isUpdated = db.update(String.valueOf(id), editTextName.getText().toString(), editTextDescription.getText().toString(), editTextImageSrc.getText().toString(),
                            editTextLat.getText().toString(), editTextLong.getText().toString());
                    if(isUpdated){
                        Toast.makeText(getApplicationContext(), "Updated id: " + id, Toast.LENGTH_SHORT).show();
                        MainActivity.mAdapter.notifyDataSetChanged();
                    }
                    else {Toast.makeText(getApplicationContext(), "NOT Updated", Toast.LENGTH_SHORT).show();}
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }
}
