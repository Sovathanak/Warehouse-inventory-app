package warehouseinventory.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;

import warehouseinventory.com.provider.Item;
import warehouseinventory.com.provider.ItemViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TRACING_ACT";
    public static final String DES_FILE = "DES_FILE";
    public static final String ITEM_FILE = "ITEM_FILE";
    public static final String COST_FILE = "COST_FILE";
    public static final String QUAN_FILE = "QUAN_FILE";
    public static final String GIANT_FILE = "GIANT_FILE";
    public static final String STATE_FILE = "STATE_FILE";
    EditText input_itemName;
    EditText inputQuantity;
    EditText inputCost;
    EditText inputDes;
    ToggleButton frozenButton;
    String itemName;
    String quantity;
    String cost;
    String des;
    boolean pressed = false;

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FloatingActionButton fab;
    ConstraintLayout main_activity;

    private ItemViewModel itemViewModel;

    int startX, startY, endX, endY;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_drawer_layout);

        input_itemName = findViewById(R.id.ItemNameText);
        inputQuantity = findViewById(R.id.QuantityText);
        inputCost = findViewById(R.id.CostText);
        inputDes = findViewById(R.id.DescriptionText);
        frozenButton = findViewById(R.id.frozenButton);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        BroadCastReceiver ThisBroadCastReceiver = new BroadCastReceiver();
        registerReceiver(ThisBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        fab = findViewById(R.id.add_item_button);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavListener());

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        main_activity = findViewById(R.id.main_act);

        main_activity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        endX = (int) event.getRawX();
                        endY = (int) event.getRawY();
                        if (endX > startX && startY - 10 < endY || endX > startX && startY + 10 > endY) {
                            itemName = input_itemName.getText().toString();
                            quantity = inputQuantity.getText().toString();
                            cost = inputCost.getText().toString();
                            des = inputDes.getText().toString();
                            Item items = new Item(itemName, Double.parseDouble(cost), Double.parseDouble(quantity), des, frozenButton.isChecked());
                            itemViewModel.insert(items);
                            Toast.makeText(MainActivity.this, "New item " + "( " + itemName + " ) has been added", Toast.LENGTH_LONG).show();
                        } else if (endX < startX && startY - 10 < endY || endX < startX && startY + 10 > endY) {
                            input_itemName.setText("");
                            inputQuantity.setText("");
                            inputCost.setText("");
                            inputDes.setText("");
                            frozenButton.setChecked(false);
                            restoreSharedPreferences();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName = input_itemName.getText().toString();
                quantity = inputQuantity.getText().toString();
                cost = inputCost.getText().toString();
                des = inputDes.getText().toString();
                Item items = new Item(itemName, Double.parseDouble(cost), Double.parseDouble(quantity), des, frozenButton.isChecked());
                itemViewModel.insert(items);
                Snackbar.make(v, "New item " + "( " + itemName + " ) has been added",
                        Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d("DEBUG_TAG", "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d("DEBUG_TAG", "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d("DEBUG_TAG", "Action was UP");
                return true;
            default:
                return super.onTouchEvent(event);

        }
    }


    private void saveAndCall() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    class NavListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.add_item) {
                itemName = input_itemName.getText().toString();
                quantity = inputQuantity.getText().toString();
                cost = inputCost.getText().toString();
                des = inputDes.getText().toString();
                Item items = new Item(itemName, Double.parseDouble(cost), Double.parseDouble(quantity), des, frozenButton.isChecked());
                itemViewModel.insert(items);
                Toast.makeText(MainActivity.this, "New item " + "( " + itemName + " ) has been added", Toast.LENGTH_LONG).show();
            } else if (id == R.id.clear_all_items) {
                input_itemName.setText("");
                inputQuantity.setText("");
                inputCost.setText("");
                inputDes.setText("");
                frozenButton.setChecked(false);
                restoreSharedPreferences();
            } else if (id == R.id.list_all_items) {
                saveAndCall();
            } else if (id == R.id.log_out) {
                // destory the app (go back to login page)
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_item) {
            itemName = input_itemName.getText().toString();
            quantity = inputQuantity.getText().toString();
            cost = inputCost.getText().toString();
            des = inputDes.getText().toString();
            Item items = new Item(itemName, Double.parseDouble(cost), Double.parseDouble(quantity), des, frozenButton.isChecked());
            itemViewModel.insert(items);
            Toast.makeText(MainActivity.this, "New item " + "( " + itemName + " ) has been added", Toast.LENGTH_LONG).show();
        } else if (id == R.id.clear_all_items) {
            input_itemName.setText("");
            inputQuantity.setText("");
            inputCost.setText("");
            inputDes.setText("");
            frozenButton.setChecked(false);
            restoreSharedPreferences();
        } else if (id == R.id.log_out) {
            // destory the app (go back to login page)
            finish();
        }
        return true;
    }

    class BroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            StringTokenizer sT = new StringTokenizer(msg, ";");
            String itemNameSMS = sT.nextToken();
            String quantitySMS = sT.nextToken();
            String costSMS = sT.nextToken();
            String desSMS = sT.nextToken();
            String stateSMS = sT.nextToken();
            if (stateSMS.equals("true"))
                frozenButton.setChecked(true);
            else
                frozenButton.setChecked(false);
            input_itemName.setText(itemNameSMS);
            inputQuantity.setText(quantitySMS);
            inputCost.setText(costSMS);
            inputDes.setText(desSMS);
        }
    }

    public void clickedAdd(View v) {
        itemName = input_itemName.getText().toString();
        quantity = inputQuantity.getText().toString();
        cost = inputCost.getText().toString();
        des = inputDes.getText().toString();
        Item items = new Item(itemName, Double.parseDouble(cost), Double.parseDouble(quantity), des, frozenButton.isChecked());
        itemViewModel.insert(items);
        Toast.makeText(MainActivity.this, "New item " + "( " + itemName + " ) has been added", Toast.LENGTH_LONG).show();
    }

    public void clickedClear(View v) {
        input_itemName.setText("");
        inputQuantity.setText("");
        inputCost.setText("");
        inputDes.setText("");
        frozenButton.setChecked(false);
        restoreSharedPreferences();
    }

    protected void onStart() {
        restoreSharedPreferences();
        if (quantity.equals("") && cost.equals("")) {
            if (des.equals("") && itemName.equals("")) {
                if (!pressed) {
                    cost = "0.0";
                    quantity = "0";
                }
            }
        }
        inputDes.setText(des);
        inputCost.setText(cost);
        inputQuantity.setText(quantity);
        input_itemName.setText(itemName);
        frozenButton.setChecked(pressed);
        super.onStart();
        Log.i(TAG, "onStart");
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    protected void onStop() {
        itemName = input_itemName.getText().toString();
        quantity = inputQuantity.getText().toString();
        cost = inputCost.getText().toString();
        des = inputDes.getText().toString();
        pressed = frozenButton.isChecked();
        super.onStop();
        Log.i(TAG, "onStop");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    protected void onSaveInstanceState(Bundle outState) {
        saveSharedPreferences();
        outState.putString(DES_FILE, des);
        outState.putString(ITEM_FILE, itemName);
        outState.putString(COST_FILE, cost);
        outState.putString(QUAN_FILE, quantity);
        outState.putBoolean(STATE_FILE, pressed);
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle inState) {
        des = inState.getString(DES_FILE, "");
        itemName = inState.getString(ITEM_FILE, "");
        cost = inState.getString(COST_FILE, "0.0");
        quantity = inState.getString(QUAN_FILE, "0");
        pressed = inState.getBoolean(STATE_FILE, false);
        super.onRestoreInstanceState(inState);
        Log.i(TAG, "onRestoreInstanceState");

    }

    private void saveSharedPreferences() {
        SharedPreferences sp = getSharedPreferences(GIANT_FILE, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ITEM_FILE, itemName);
        editor.putString(DES_FILE, des);
        editor.putString(QUAN_FILE, quantity);
        editor.putString(COST_FILE, cost);
        editor.putBoolean(STATE_FILE, frozenButton.isChecked());
        editor.apply();


    }

    private void restoreSharedPreferences() {
        SharedPreferences rsp = getSharedPreferences(GIANT_FILE, 0);
        itemName = rsp.getString(ITEM_FILE, "");
        des = rsp.getString(DES_FILE, "");
        quantity = rsp.getString(QUAN_FILE, "0");
        cost = rsp.getString(COST_FILE, "0.0");
        pressed = rsp.getBoolean(STATE_FILE, false);
        rsp.edit().clear().apply();


    }
}
