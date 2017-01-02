package com.rw.velocityapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rw.velocity.Velocity;

public class MainActivity extends AppCompatActivity
{
    private TextView textView;
    private ImageView imageView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        Velocity.initialize(3);
        Velocity.getSettings().setTimeout(6000);
        Velocity.getSettings().setReadTimeout(10000);
        Velocity.getSettings().setMultipartBoundary("----WebKitFormBoundaryQHJL2hsKnlU26Mm3");
        Velocity.getSettings().setMaxTransferBufferSize(1024);

        String m3 = "https://media.caranddriver.com/images/media/331369/m-is-for-mega-2015-bmw-m3-pricing-surfaces-photo-583888-s-450x274.jpg";
        String randomImage = "http://lorempixel.com/400/200/abstract";
        final String file = "http://mirror.internode.on.net/pub/test/5meg.test1";
        String pdf = "http://www.flar.net/uploads/default/calendar/99f3a2304c1754aecffab145a5c80b98.pdf";
        final String sushi = "https://s3-ap-southeast-2.amazonaws.com/platform.cdn/clients/dev/filemanager/root/157e4ce754803c1.94820022_Tempura%20Soba.jpg?response-content-disposition=attachment%3B%20filename%3D%22Tempura%20Soba.jpg%22&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWZTEEFKHKIKCC3A%2F20161228%2Fap-southeast-2%2Fs3%2Faws4_request&X-Amz-Date=20161228T065659Z&X-Amz-SignedHeaders=host&X-Amz-Expires=5400&X-Amz-Signature=651830960fab463cd9fcc9345a3e47cac2181fe0cad793e2785bb1af7c7fae2d";


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("downloading file...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);


        //
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //uploadFile();
                downloadRequest(sushi);
            }
        });


    }

//    private void uploadFile()
//    {
//        String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "m3_2.jpg";
//
//
//        progressDialog.setMessage("uploading image");
//        progressDialog.show();
//
//        Velocity.upload(url)
//                .withRequestMethodPost()
//                .setUploadSource("uploadedfile", "image/jpeg", file)
////                .withParam("first_name", "test_first")
////                .withParam("last_name", "test_last11")
////                .withParam("phone_number", "123456789")
//                .withProgressListener(new Velocity.ProgressListener()
//                {
//                    @Override
//                    public void onFileProgress(int percentage)
//                    {
//                        //Log.d("IMG", "upload progress: " + percentage);
//                        progressDialog.setProgress(percentage);
//                    }
//                })
//                .connect(new Velocity.ResponseListener()
//                {
//                    @Override
//                    public void onVelocitySuccess(Velocity.Response response)
//                    {
//                        progressDialog.dismiss();
//                        textView.setText(response.body);
//                        Log.d("IMG", "uploaded: " + response.body);
//                    }
//
//                    @Override
//                    public void onVelocityFailed(Velocity.Response error)
//                    {
//                        progressDialog.dismiss();
//                        textView.setText(error.body);
//                        Log.d("IMG", "upload error: " + error.body);
//                    }
//                });
//    }


    private void downloadRequest(String url)
    {
        progressDialog.setMessage("downloading file");
        progressDialog.show();
        String filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "sample2.bin";

        Log.d("IMG", "target filename: " + filepath);

        Velocity.download(url)
                .setDownloadFile(filepath)
                .withProgressListener(new Velocity.ProgressListener()
                {
                    @Override
                    public void onFileProgress(int percentage)
                    {

                        progressDialog.setProgress(percentage);
                    }
                })
                .connect(new Velocity.ResponseListener()
                {
                    @Override
                    public void onVelocitySuccess(Velocity.Response response)
                    {
                        Log.d("IMG", "response: " + response.body);
                        textView.setText(response.body);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onVelocityFailed(Velocity.Response error)
                    {
                        Log.d("IMG", "error: " + error.body);
                        textView.setText(error.status + ": " + error.body);
                        progressDialog.dismiss();
                    }
                });
    }

    private void textRequest(String url)
    {
        Velocity.load(url).connect(new Velocity.ResponseListener()
        {
            @Override
            public void onVelocitySuccess(Velocity.Response response)
            {
                //Log.d("IMG", "response: " + response.body);
                textView.setText(response.body);
                if (response.image != null)
                    imageView.setImageBitmap(response.image);
            }

            @Override
            public void onVelocityFailed(Velocity.Response error)
            {
                textView.setText(error.body);
                Log.d("IMG", "response: " + error.body);
            }
        });
    }
}
