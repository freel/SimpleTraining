package com.freel.simpletraining;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 16.08.2016.
 */
public class TrainingActivity extends AppCompatActivity{

    private Chronometer mChronometer;//Хронометр
    long timer = 0;//Время на таймере

    List<StringWithId> exerciseType;
    int exercise = 0;
    int repeat;
    int[] repeatsArray = new int[5];

    DBHelper dbHelper = new DBHelper(this);

    //Флаг нажатия старт/повтор
    boolean startFlag=false;
    //Флаг нажатия стоп/выход
    boolean stopFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);



        setDBConnection();

        setListAdapter();//Адаптер списка упражнений
    }

    private void setListAdapter(){
        exerciseType = dbHelper.getExerciseList();
        // адаптер
        ArrayAdapter<StringWithId> adapter = new ArrayAdapter<StringWithId>(this, android.R.layout.simple_spinner_item, exerciseType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Вид");

        repeatActivate();
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Log.d("LOG ", "Position = " + position);
                exercise = position;
                repeatActivate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    /*При нажатии на кнопке Старт/Повтор запускается таймер,
    * надпись на кнопке заменяется
    * счётчик повторений +1
    * */
    public void onStartClick(View view) {
        if(!startFlag){ //Когда старт не был нажат, упражнение начинается
            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setText("Закончить Повтор");
            startFlag = true;
            Button stopButton = (Button) findViewById(R.id.stopButton);
            stopButton.setText("Закончить Упражнение");
            stopFlag = true;
            startTimer();
            repeatCount();
        } else { //Заканчиваем повторение упражнения
            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setText("Начать повтор");
            startFlag = false;
            stopTimer();
        }
    }

    /*При нажатии на кнопке Стоп/Закончить
    * останавливается таймер,
    * надпись на кнопке заменяется
    * */
    public void onStopClick(View view) {
        if(!stopFlag){ //Когда стоп не был нажат, кнопка "Выйти"
            //Тут выход на начальный экран
            saveApproachData();
            super.finish();
        } else {
            Button stopButton = (Button) findViewById(R.id.stopButton);
            stopButton.setText("Выйти");
            stopFlag = false;
            Button startButton = (Button) findViewById(R.id.startButton);
            startButton.setText("Начать упражнение");
            startFlag = false;
            stopTimer();
            saveApproachData();
        }

    }

    private void repeatActivate() {
        repeat = repeatsArray[exercise];
        TextView repeatsText = (TextView) findViewById(R.id.repeatView);
        repeatsText.setText(String.format("%s",repeat));
    }

    private void repeatCount(){//Счётчик повторов
        repeat++;
        repeatsArray[exercise] = repeat;
        TextView repeatsText = (TextView) findViewById(R.id.repeatView);
        repeatsText.setText(String.format("%s",repeat));
    }

    private void startTimer(){//Начало отсчёта
        mChronometer.setBase(SystemClock.elapsedRealtime()+timer);
        mChronometer.start();
    }

    private void stopTimer(){//Пауза отсчёта
        timer = mChronometer.getBase()-SystemClock.elapsedRealtime();
        mChronometer.stop();
    }

    private void setDBConnection(){//Соединение с БД
        dbHelper.connect();
//        dbHelper.saveTrainingData();
    }

    private void saveApproachData() {//Запись данных в БД
        dbHelper.saveApproachData(exercise, timer);
    }
}
