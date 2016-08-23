package com.freel.simpletraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 16.08.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    //Создаём объект БД
    SQLiteDatabase db;
    // создаем объект для данных
    ContentValues cv = new ContentValues();
    long trainingID;

    public DBHelper(Context context) {
        super(context, "simpleTrainingDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DBHelper", "--- onCreate database ---");
        // создаем таблицу с видами занятий
        try {
            createExerciseTable(sqLiteDatabase);
            createApproachTable(sqLiteDatabase);
//            createTrainingTable(sqLiteDatabase);
            createUserTable(sqLiteDatabase);
            createArgumentTable(sqLiteDatabase);
            createRepeatTable(sqLiteDatabase);


        } catch (Exception e){
            Log.d("DBHelper", "--- Create Error --- " + e);
            return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*Соединение с БД*/
    public void connect() {
        db = this.getWritableDatabase();

    }

    public void disconnect(){
        db.close();
    }

//    /*Чтение данных тренировок*/
//    public void getTrainingData(){
//        Cursor c = db.query("training", null, null, null, null, null, null);
//        if (c.moveToFirst()) {
//
//            // определяем номера столбцов по имени в выборке
//            int id = c.getColumnIndex("id");
//            int timestamp = c.getColumnIndex("timestamp");
//
//            do {
//                // получаем значения по номерам столбцов и пишем все в лог
//                Log.d("TABLE",
//                        "ID = " + c.getInt(id) +
//                                ", timestamp = " + c.getString(timestamp));
//                // переход на следующую строку
//                // а если следующей нет (текущая - последняя), то false - выходим из цикла
//            } while (c.moveToNext());
//        }
//    }

    public List<StringWithId> getExerciseList(){

        Cursor c = db.query("user", null, null, null, null, null, null);

        List<StringWithId> list = new ArrayList<StringWithId>();

        if (c.moveToLast()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int name = c.getColumnIndex("name");

            do {
                Log.d("TABLE",
                        "ID = " + c.getInt(id) +
                                ", name = " + c.getString(name)

                );
                list.add(new StringWithId(c.getString(name), c.getInt(id)));
            } while (c.moveToNext());
        }
        return list;
    }

    /*Чтение данных упражнений*/
    public void getApproachData(){
        Cursor c = db.query("approach", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int exercise = c.getColumnIndex("exercise");
            int training = c.getColumnIndex("training");
            int repeats = c.getColumnIndex("repeats");
            int time = c.getColumnIndex("time");
            int timestamp = c.getColumnIndex("timestamp");
            int arg1 = c.getColumnIndex("arg1");
            int name = c.getColumnIndex("name");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("TABLE",
                        "ID = " + c.getInt(id) +
                                ", exercise = " + c.getString(exercise) +
                                ", training = " + c.getString(training) +
                                ", repeats = " + c.getString(repeats) +
                                ", time = " + c.getString(time) +
                                ", timestamp = " + c.getString(timestamp) +
                                ", arg1 = " + c.getString(arg1) +
                                ", name = " + c.getString(name)

                );
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
    }

    public Map<String, String> getArgumentData(){
        Map<String, String> map = new HashMap<String, String>();

        Cursor c = db.query("user", null, null, null, null, null, null);
        if (c.moveToLast()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int exercise = c.getColumnIndex("exercise");
            int name = c.getColumnIndex("name");
            int default_value = c.getColumnIndex("default_value");
            int timestamp = c.getColumnIndex("timestamp");

            do {
                map.put("id", c.getString(id));
                map.put("exercise", c.getString(exercise));
                map.put("name", c.getString(name));
                map.put("default_value", c.getString(default_value));
                map.put("timestamp", c.getString(timestamp));
            } while (c.moveToNext());
        }
        return map;
    }

    public Map<String, String> getRepeatData(){
        Map<String, String> map = new HashMap<String, String>();

        Cursor c = db.query("user", null, null, null, null, null, null);
        if (c.moveToLast()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int approach = c.getColumnIndex("approach");
            int argument = c.getColumnIndex("argument");
            int value = c.getColumnIndex("value");
            int timestamp = c.getColumnIndex("timestamp");

            do {
                map.put("id", c.getString(id));
                map.put("appro  ach", c.getString(approach));
                map.put("argument", c.getString(argument));
                map.put("value", c.getString(value));
                map.put("timestamp", c.getString(timestamp));
            } while (c.moveToNext());
        }
        return map;
    }

    /*Чтение данных пользователя*/
    public void getUserData(){
        Map<String, String> map = new HashMap<String, String>();

        Cursor c = db.query("user", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int height = c.getColumnIndex("height");
            int weight = c.getColumnIndex("weight");
            int timestamp = c.getColumnIndex("timestamp");

            do {
                map.put("id", c.getString(id));
                map.put("height", c.getString(height));
                map.put("weight", c.getString(weight));
                map.put("timestamp", c.getString(timestamp));
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("TABLE USER",
                        "ID = " + c.getInt(id) +
                                ", height = " + c.getString(height) +
                                ", weight = " + c.getString(weight) +
                                ", timestamp = " + c.getString(timestamp));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
    }

    public Map<String, String> getLastUserData(){
        Map<String, String> map = new HashMap<String, String>();

        Cursor c = db.query("user", null, null, null, null, null, null);
        if (c.moveToLast()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int height = c.getColumnIndex("height");
            int weight = c.getColumnIndex("weight");
            int timestamp = c.getColumnIndex("timestamp");

            do {
                map.put("id", c.getString(id));
                map.put("height", c.getString(height));
                map.put("weight", c.getString(weight));
                map.put("timestamp", c.getString(timestamp));
            } while (c.moveToNext());
        }
        return map;
    }

//    /*Запись данных тернировки*/
//    public void saveTrainingData(){
//        cv.putNull("name");
//        trainingID = db.insert("training", null, cv);
//        cv.clear();
//
//
//        getTrainingData();//Тест записи, чтение всех записей тренировки в лог
//    }


    /*Запись типов упражнения*/
    public void saveExerciseData(String name){
        cv.put("name", name);
        db.insert("exercise", null, cv);
        cv.clear();

        getExerciseList();
    }

    /*Запись данных упражнений*/
    public void saveApproachData(int exercise, long time){
        cv.put("exercise", exercise);
        cv.put("training",trainingID);
        cv.put("time", time);
        db.insert("approach", null, cv);
        cv.clear();

        getApproachData();
    }

    /*Запись переменных для упражнений*/
    public void saveArgumentData(int exercise, String name, int default_value){
        cv.put("exercise", exercise);
        cv.put("name", name);
        cv.put("default_value", default_value);
        db.insert("argument", null, cv);
        cv.clear();

        getArgumentData();
    }

    /*Запись данных упражнения*/
    public void saveRepeatData(int approach, int argument, int value){
        cv.put("approach", approach);
        cv.put("argument", argument);
        cv.put("value", value);
        db.insert("repeat", null, cv);
        cv.clear();

        getRepeatData();
    }

    /*Запись данных пользователя*/
    public void saveUserData(int height, int weight){
        cv.put("height", height);
        cv.put("weight", weight);
        db.insert("user", null, cv);
        cv.clear();

        getUserData();
    }

    /*Создание таблиц*/
    //Названия упражнений
    private void createExerciseTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table exercises ("
                + "id integer primary key autoincrement,"
                + "name text" + ");");
    }

    //Упражнения
    private void createApproachTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table approach ("
                + "id integer primary key autoincrement,"
                + "exercise integer,"
                + "training integer,"
                + "time long,"
                + "timestamp datetime DEFAULT CURRENT_TIMESTAMP" + ");");
    }

//    /*
//    * Тренировки
//    * training
//    * */
//    private void createTrainingTable(SQLiteDatabase sqLiteDatabase){
//
//        sqLiteDatabase.execSQL("create table training ("
//                + "id integer primary key autoincrement,"
//                + "timestamp datetime DEFAULT CURRENT_TIMESTAMP" + ");");
//    }

    /*
    * Аргументы упражнений
    * Например вес по умолчанию,
    * количество повторов по умолчанию
    * итд
    * */
    private void createArgumentTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table argument ("
                + "id integer primary key autoincrement,"
                + "exercise integer,"
                + "name text,"
                + "default_value integer,"
                + "timestamp datetime DEFAULT CURRENT_TIMESTAMP" + ");");
    }

    /*
    * Аргументы подходов (во время тренировки)
    * Например вес,
    * количество повторов,
    * итд
    * */
    private void createRepeatTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table repeat ("
                + "id integer primary key autoincrement,"
                + "approach integer,"
                + "argument integer,"
                + "value integer,"
                + "timestamp datetime DEFAULT CURRENT_TIMESTAMP" + ");");
    }

    //Данные пользователя(рост, вес)
    private void createUserTable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table user ("
                + "id integer primary key autoincrement,"
                + "height integer,"
                + "weight integer,"
                + "timestamp datetime DEFAULT CURRENT_TIMESTAMP" + ");");
    }
}
