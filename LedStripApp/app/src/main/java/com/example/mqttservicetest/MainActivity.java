package com.example.mqttservicetest;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
public class MainActivity extends AppCompatActivity {
    static String MQTTHost ="tcp://192.168.1.53:1883";
    static String Username ="";
    static String Password ="";
    String Topicstr;
    MqttAndroidClient client;
    Button  Publish,publishoff;
    TextView SubText,hexvalue;
    EditText topictxt,hosttxt;
    ImageView imageColor;
    View colorView;Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Publish = findViewById(R.id.Publish);
        publishoff = findViewById(R.id.publishoff);
        colorView = findViewById(R.id.colorView);
        hexvalue = findViewById(R.id.hexvalue);
        imageColor = findViewById(R.id.imageColor);
        imageColor.setDrawingCacheEnabled(true);
        imageColor.buildDrawingCache(true);

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(),MQTTHost,clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this,"Fail",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        Publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Topicstr = topictxt.getText().toString();
                String topic = Topicstr;
                String message = "on";
                byte[] encodedPayload = new byte[0];
                //String message = "on";
                try
                {
                    client.publish(topic,message.getBytes(),0,false);
                }catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        publishoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = Topicstr;
                String message = "off";
                byte[] encodedPayload = new byte[0];
                //String message = "on";
                try
                {
                    client.publish(topic,message.getBytes(),0,false);
                }catch (MqttPersistenceException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        imageColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()== MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    String topic = Topicstr;
                    bitmap = imageColor.getDrawingCache();
                    int pixel = bitmap.getPixel((int)event.getX(),(int)event.getY());
                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    String hex = Integer.toHexString(pixel);
                    colorView.setBackgroundColor(Color.rgb(r,g,b));
                    hexvalue.setText(""+r+g+b);
                    String message = hex;
                    byte[] encodedPayload = new byte[0];
                    //String message = "on";
                    try
                    {
                        client.publish(topic,message.getBytes(),0,false);
                    }catch (MqttPersistenceException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        });
//        Subcribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String topic = "room/light/state";
//                int qos = 0;
//                try {
//                    final IMqttToken subToken = client.subscribe(topic, qos);
//                    subToken.setActionCallback(new IMqttActionListener() {
//                        @Override
//                        public void onSuccess(IMqttToken asyncActionToken) {
//
//                        }
//
//                        @Override
//                        public void onFailure(IMqttToken asyncActionToken,
//                                              Throwable exception) {
//                            // The subscription could not be performed, maybe the user was not
//                            // authorized to subscribe on the specified topic e.g. using wildcards
//
//                        }
//                    });
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                SubText.setText(message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
//        disconnect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    IMqttToken disconToken = client.disconnect();
//                    disconToken.setActionCallback(new IMqttActionListener() {
//                        @Override
//                        public void onSuccess(IMqttToken asyncActionToken) {
//                            // we are now successfully disconnected
//                        }
//
//                        @Override
//                        public void onFailure(IMqttToken asyncActionToken,
//                                              Throwable exception) {
//                            // something went wrong, but probably we are disconnected anyway
//                        }
//                    });
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
