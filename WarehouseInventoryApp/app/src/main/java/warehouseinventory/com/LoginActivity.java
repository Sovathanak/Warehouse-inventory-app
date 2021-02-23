package warehouseinventory.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText UserName, PassWord;
    String userName, passWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        UserName = findViewById(R.id.Username);
        PassWord = findViewById(R.id.Password);
    }

    public void OnClick(View v) {
        Intent intent;
        userName = UserName.getText().toString();
        passWd = PassWord.getText().toString();
        if (userName.equals("developer") && passWd.equals("developer123")) {
            Toast.makeText(this, "Welcome developer", Toast.LENGTH_LONG).show();
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (userName.equals("29400090") && passWd.equals("29400090")) {
            Toast.makeText(this, "Welcome back Sovathanak", Toast.LENGTH_LONG).show();
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (userName.equals("tutor") && passWd.equals("tutor123")) {
            Toast.makeText(this, "Welcome Sovathanak's Tutor", Toast.LENGTH_LONG).show();
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Invalid Username/Password have been entered, please try again", Toast.LENGTH_LONG).show();

        }

    }
}


