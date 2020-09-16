package com.wangjixin22.wangjixin.lect10Net;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.wangjixin22.wangjixin.R;

public class SignActivity extends AppCompatActivity {
    EditText mEditText;
    Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mEditText = (EditText)findViewById(R.id.sign_text);
        mDoneButton = (Button) findViewById(R.id.sign_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wy = mEditText.getText().toString();
                Toast.makeText(SignActivity.this, "修改完成", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("mySign",wy);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}