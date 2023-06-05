package sg.edu.rp.c346.id21023395.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText taskEditText;
    Button btnAdd, btnClear, btnDelete;
    ListView lvTask;
    Spinner addDelete;
    TextView listHeader;
    ArrayList<String>taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.editTextTask);
        btnAdd = findViewById(R.id.buttonAdd);
        btnClear = findViewById(R.id.buttonClear);
        lvTask = findViewById(R.id.TaskListView);
        btnDelete = findViewById(R.id.btnDelete);
        addDelete = findViewById(R.id.spinner);
        listHeader = findViewById(R.id.textView2);

        taskList = new ArrayList<>();
        ArrayAdapter aaTask = new ArrayAdapter<>(this,
                R.layout.custom_list_item, taskList);

        lvTask.setAdapter(aaTask);

        addDelete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        btnDelete.setEnabled(false);
                        btnAdd.setEnabled(true);
                        taskEditText.setHint("Type in a new task here");
                        break;
                    case 1:
                        btnDelete.setEnabled(true);
                        btnAdd.setEnabled(false);
                        taskEditText.setHint("Type in the index of the task to be removed");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Task = taskEditText.getText().toString();
                taskList.add(Task);
                aaTask.notifyDataSetChanged();
                updateListHeaderVisibility();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString();
                if (task.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter an index",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isDigitsOnly(task)){
                    int pos = Integer.parseInt(task);
                    if (taskList.isEmpty()){
                        Toast.makeText(MainActivity. this, "You don't have any task to remove",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (pos < 0 || pos >= taskList.size() ){
                        Toast.makeText(MainActivity. this, "Wrong index number",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    taskList.remove(pos);
                    aaTask.notifyDataSetChanged();
                    updateListHeaderVisibility();

                } else{
                    Toast.makeText(MainActivity. this, "Wrong index number",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.clear();
                aaTask.notifyDataSetChanged();
                updateListHeaderVisibility();
            }
        });
    }
    private void updateListHeaderVisibility() {
        if (taskList.size() > 0) {
            listHeader.setText(R.string.listHeader);
        } else {
            listHeader.setText("");
        }
    }
}