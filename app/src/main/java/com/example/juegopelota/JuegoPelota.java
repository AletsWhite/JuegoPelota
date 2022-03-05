package com.example.juegopelota;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class JuegoPelota extends View implements SensorEventListener {
    Paint dibujar = new Paint();
    int alto, ancho, tamanioPelota = 40, borde = 0, visitante = 0, local = 0;
    float ejeX = 0, ejeY = 0, ejeZ1 = 0, ejeZ = 0;
    String X, Y, Z;


    public JuegoPelota(Context context) {
        super(context);
        SensorManager sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = display.getWidth();
        alto = display.getHeight();
        setBackgroundResource(R.drawable.descargar);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ejeX-=sensorEvent.values[0];
        X=Float.toString(ejeX);
        if(ejeX < (tamanioPelota+borde)){
            ejeX = (tamanioPelota+borde);
        } else if(ejeX > (ancho-(tamanioPelota+borde))){
            ejeX = ancho-(tamanioPelota+borde);
        }
        ejeY+=sensorEvent.values[1];
        Y=Float.toString(ejeY);
        if(ejeY < (tamanioPelota+borde)){
            ejeY = (tamanioPelota+borde);


        } else if(ejeY > (alto-tamanioPelota-170)){
            ejeY = alto - tamanioPelota-170;
            //local = local + 1;
        }

        if(ejeY == 40 && ejeX <= 600 && ejeX >= 450){
            visitante = visitante + 1;
            ejeX = 540;
            ejeY = 1025;
        }
        if(ejeY >= 2050 && ejeX <= 600 && ejeX >= 450){
            local = local + 1;
            ejeX = 540;
            ejeY = 1025;
        }
        ejeZ=sensorEvent.values[2];
        Z=String.format("%.2f",ejeZ);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dibujar.setColor(Color.GREEN);
        canvas.drawCircle(ejeX, ejeY, ejeZ+tamanioPelota, dibujar);
        dibujar.setColor(Color.WHITE);
        dibujar.setTextSize(25);
        canvas.drawText("balon", ejeX-35, ejeY+3, dibujar);
        dibujar.setColor(Color.WHITE);
        dibujar.setTextSize(100);
        canvas.drawText(String.valueOf(visitante), 1000, 100, dibujar);
        canvas.drawText(String.valueOf(local), 0, 2100, dibujar);
    }
}
