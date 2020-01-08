package com.rujal.hamrobazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class TermsAndConditionActivity extends AppCompatActivity {
    CheckBox cbTermsAndCondition, cbSafetyTips, cbAdPostingRule;
    Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        cbTermsAndCondition = findViewById(R.id.cbTermsAndCondition);
        cbSafetyTips = findViewById(R.id.cbSafetyTips);
        cbAdPostingRule = findViewById(R.id.cbAdPostingRule);

        btnAgree = findViewById(R.id.btnAgree);

        cbTermsAndCondition.setOnClickListener(i -> startCustomActivity(TermsAndConditionActivity.this, TermsActivity.class));
        cbSafetyTips.setOnClickListener(i -> startCustomActivity(TermsAndConditionActivity.this, SafetyTipsActivity.class));
        cbAdPostingRule.setOnClickListener(i -> startCustomActivity(TermsAndConditionActivity.this, AdPostingRuleActivity.class));
        btnAgree.setOnClickListener(i -> startCustomActivity(TermsAndConditionActivity.this, DashboardActivity.class));


    }

    private void startCustomActivity(Context currentContext, Class<?> nextActivity ) {
        Intent intent = new Intent(currentContext, nextActivity);
        startActivity(intent);
    }
}
