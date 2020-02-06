package com.money.manger.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.money.manger.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NewExpensesActivity extends AppCompatActivity {

//    @BindView(R.id.new_toolbar)
//    Toolbar newToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expenses);
        ButterKnife.bind(this);


        // add back arrow to toolbar
//        setSupportActionBar(newToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }







    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.back_left_to_right,R.anim.back_right_to_left);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.back_left_to_right,R.anim.back_right_to_left);
    }

}
