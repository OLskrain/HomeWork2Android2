package com.example.olskr.homework2android2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SensorCollector extends AppCompatActivity {

    private TextView textHumidity;
    private TextView textTemperature;
    private SensorManager sensorManager;
    private Sensor sensorTemperature;
    private Sensor sensorHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_collector);

        textTemperature = findViewById(R.id.textTemperature);
        textHumidity = findViewById(R.id.textHumidity);


        // Менеджер датчиков
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Датчик темпиратуры
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        //Датчик влажности
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        // Регистрируем слушателя датчика температуры
        //каждый раз когда он будет фиксировать измененния датчика, будет происходить действие, которое мы назначим на слушатель
        sensorManager.registerListener(listenerTemperature, sensorTemperature,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(listenerHumidity, sensorHumidity,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Если приложение свернуто, то не будем тратить энергию на получение информации по датчикам
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemperature, sensorTemperature);
        sensorManager.unregisterListener(listenerHumidity, sensorHumidity);
    }

    // вывод всех сенсоров
    private StringBuilder showSensors(SensorEvent event, Sensor sensor, String name) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("name = ").append(sensor.getName())
                    .append(", type = ").append(sensor.getType())
                    .append("\n")
                    .append("vendor = ").append(sensor.getVendor())
                    .append(" ,version = ").append(sensor.getVersion())
                    .append("\n")
                    .append("max = ").append(sensor.getMaximumRange())
                    .append(", resolution = ").append(sensor.getResolution())
                    .append("\n").append("--------------------------------------").append("\n")
                .append(name).append(" Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        return stringBuilder;
    }

    // Вывод датчика температуры
    private void showTemperatureSensors(SensorEvent event){
        textTemperature.setText(showSensors(event,sensorTemperature, getResources().getString(R.string.temperature)));
    }

    // Вывод датчика влажности
    private void showHumiditySensors(SensorEvent event){
        textHumidity.setText(showSensors(event,sensorHumidity,  getResources().getString(R.string.humidity)));
    }

    // Слушатель датчика температуры
    private final SensorEventListener listenerTemperature = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        //метод в котором мы и будем назначать действия при срабатывании датчика
        public void onSensorChanged(SensorEvent event) {
            showTemperatureSensors(event); //в нашем случае просто выводим на экран значения
        }
    };

    // Слушатель датчика влажности
    private final SensorEventListener listenerHumidity = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumiditySensors(event);
        }
    };
}
