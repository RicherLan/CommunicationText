package thefirstchange.example.com.communicationtext.usersignin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import thefirstchange.example.com.communicationtext.R;
import thefirstchange.example.com.communicationtext.activity.BaseForCloseActivity;
import thefirstchange.example.com.communicationtext.util.SearchAdapter;


public class RegisterSchoolInfoActivity extends BaseForCloseActivity implements View.OnClickListener{

    private boolean autocompleteispopupshowing = false;   //MyCompleteTextView是否正在展示提示框

    private LinearLayout registerschoolinfo_linelayout;
    private ArrayAdapter<String> adapter = null;

    private ImageView registerschoolnameback_tv;
    private TextView registerschoolinfonext_tv;

    private MyCompleteTextView registerschoollname_et;
    private EditText registercollege_et;
    private EditText registermajor_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerschoolinfo);
        init();
        initevent();

    }

    public void init(){
        registerschoollname_et = (MyCompleteTextView)this.findViewById(R.id.registerschoollname_et);
        registercollege_et = (EditText)this.findViewById(R.id.registercollege_et);
        registermajor_et = (EditText)this.findViewById(R.id.registermajor_et);
        registerschoolnameback_tv = (ImageView)this.findViewById(R.id.registerschoolnameback_tv);
        registerschoolinfonext_tv = (TextView)this.findViewById(R.id.registerschoolinfonext_tv);

        registerschoolinfo_linelayout = (LinearLayout)this.findViewById(R.id.registerschoolinfo_linelayout);
        registerschoolinfo_linelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                // Log.e("aa","bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                if(view!=registerschoollname_et){

                    if(autocompleteispopupshowing){
                        registerschoollname_et.setText("");
                        registerschoollname_et.dismissDropDown();
                    }
                    autocompleteispopupshowing = false;

                }
            }
        });

        registerschoollname_et.setThis(RegisterSchoolInfoActivity.this);
        registerschoollname_et.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                autocompleteispopupshowing = false;

            }
        });
        registerschoollname_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                autocompleteispopupshowing = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        registerschoollname_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Log.e("aa","gggggggggggggggggggggggggggggggggggggg");
                InputMethodManager inputManager =
                        (InputMethodManager)registerschoollname_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(registerschoollname_et, 0);
                if(registerschoollname_et.getText()!=null&&registerschoollname_et.getText().toString().trim()!="") {
                    registerschoollname_et.showDropDown();
                }
                registerschoollname_et.requestFocus();
                autocompleteispopupshowing = true;
                return true;
            }
        });

        SearchAdapter<String> adapter = new SearchAdapter<String>(RegisterSchoolInfoActivity.this,
                android.R.layout.simple_list_item_1, AllSchoolString.list, SearchAdapter.ALL);

        registerschoollname_et.setAdapter(adapter);


    }

    public void initevent(){
        registerschoolnameback_tv.setOnClickListener(this);
        registerschoolinfonext_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.registerschoolnameback_tv:

                finish();
                break;
            case R.id.registerschoolinfonext_tv:

                String schoolname = registerschoollname_et.getText().toString().trim();
                String collegename = registercollege_et.getText().toString().trim();
                String majorname = registermajor_et.getText().toString().trim();

                if(schoolname.isEmpty()){
                    registerschoollname_et.setText("");
                    registerschoollname_et.requestFocus();
                    Toast.makeText(this,"请填写学校名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AllSchoolString.list.contains(schoolname)){
                    registerschoollname_et.setText("");
                    registerschoollname_et.requestFocus();
                    Toast.makeText(this,"该学校未找到",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(collegename.isEmpty()){
                    registercollege_et.setText("");
                    registercollege_et.requestFocus();
                    Toast.makeText(this,"请填写院系名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(majorname.isEmpty()){
                    registermajor_et.setText("");
                    registermajor_et.requestFocus();
                    Toast.makeText(this,"请填写专业名称",Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(this,SelectRuxueYearActivity.class);
                intent.putExtra("schoolname",schoolname);
                intent.putExtra("collegename",collegename);
                intent.putExtra("majorname",majorname);
                startActivity(intent);
                break;
        }

    }
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
