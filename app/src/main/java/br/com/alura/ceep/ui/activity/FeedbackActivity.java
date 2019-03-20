package br.com.alura.ceep.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.alura.ceep.R;

public class FeedbackActivity extends AppCompatActivity {

    private String titulo = "Feedback";

    private TextInputLayout email;
    private TextInputLayout feedback;
    private ConstraintLayout layoutEnviandoFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle(titulo);

        email = findViewById(R.id.txt_ipt_layout_email);
        feedback = findViewById(R.id.txt_ipt_layout_feedback);
        layoutEnviandoFeedback = findViewById(R.id.layout_enviando_feedback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_formulario_feedback_ic_enviar) {
            email.setEnabled(false);
            feedback.setEnabled(false);
            layoutEnviandoFeedback.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    email.setEnabled(true);
                    feedback.setEnabled(true);
                    layoutEnviandoFeedback.setVisibility(View.GONE);
                    Toast.makeText(FeedbackActivity.this, "Feedback enviado.\nObrigado por contribuir", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }, 2000);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (layoutEnviandoFeedback.getVisibility() == View.GONE)
            super.onBackPressed();
    }
}
