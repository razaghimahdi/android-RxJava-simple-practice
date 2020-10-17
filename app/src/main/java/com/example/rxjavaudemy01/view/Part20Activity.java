package com.example.rxjavaudemy01.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rxjavaudemy01.R;
import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxTextView;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Unit;

public class Part20Activity extends AppCompatActivity {

    private EditText edtInputField;
    private TextView txtInput;
    private Button btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part20);

        edtInputField=findViewById(R.id.edtInputField);
        btnClear=findViewById(R.id.btnClear);
        txtInput=findViewById(R.id.txtInput);
/*
        edtInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtInput.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtInputField.setText("");
                txtInput.setText("Empty");
            }
        });

*/

        Disposable disposable= RxTextView.textChanges(edtInputField)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Throwable {
                        txtInput.setText(charSequence);
                    }
                });

        Disposable disposable1= RxView.clicks(btnClear)
                .subscribe(new Consumer<Unit>() {
                    @Override
                    public void accept(Unit unit) throws Throwable {

                        edtInputField.setText("");
                        txtInput.setText("Empty");
                    }
                });



    }
}