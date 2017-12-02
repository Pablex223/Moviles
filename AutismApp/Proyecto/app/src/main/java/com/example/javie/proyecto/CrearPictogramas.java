package com.example.javie.proyecto;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.javie.proyecto.Entidades.Pictograma;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.graphics.BitmapFactory.decodeFile;

/**
 * Created by javie on 11/22/2017.
 */

public class CrearPictogramas extends Fragment {

    Button btnFoto, btnGuardarPic, btnGaleria;
    LinearLayout camposDatos;
    ImageView imgFoto;
    private Uri mImageCaptureUri;
    static final int CAM_REQUEST = 1;
    static final int GALLERY_REQUEST = 2;
    String mCurrentPhotoPath;
    public CrearPictogramas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_pictograma, container, false);
        imgFoto = (ImageView) view.findViewById(R.id.imgFoto);
        btnFoto = (Button) view.findViewById(R.id.btnFoto);
        btnGaleria = (Button) view.findViewById(R.id.btnGaleria);
        btnGuardarPic = (Button) view.findViewById(R.id.btnGuardarPic);
        camposDatos = (LinearLayout) view.findViewById(R.id.layoutDatosPictograma);
        btnFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent cameraIntent =
//                        new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
//                        mImageCaptureUri);
//
//                if (cameraIntent.resolveActivity(getActivity().getPackageManager())!=null){
//                    startActivityForResult(cameraIntent,CAM_REQUEST);
//                }
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Toast.makeText(getActivity(), "No se pudo guardar la foto...",Toast.LENGTH_SHORT).show();
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getContext(),
                                "com.example.javie.proyecto",
                                photoFile);
                        //Toast.makeText(getActivity(), mImageCaptureUri.toString(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Se creo la foto",Toast.LENGTH_SHORT).show();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(takePictureIntent, CAM_REQUEST);
                    }
                }
            }
        });
        btnGaleria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);



                // where do we want to find the data?

                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String pictureDirectoryPath = pictureDirectory.getPath();

                // finally, get a URI representation

                Uri data = Uri.parse(pictureDirectoryPath);



                // set the data and type.  Get all image types.

                photoPickerIntent.setDataAndType(data, "image/*");



                // we will invoke this activity, and get something back from it.

                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
        return view;

    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg",  /* suffix */
                storageDir/* directory */
        );

       // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
        // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Toast.makeText(getActivity(), "Se creo la foto",Toast.LENGTH_SHORT).show();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "No se pudo guardar la foto...",Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.javie.proyecto",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAM_REQUEST);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imageBitmap);
            habilitarCamposDatos();
        }
        else
            if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
//                String path = getPathFromCameraData(data, this.getActivity());
//                Log.i("PICTURE", "Path: " + path);
//                if (path != null) {
//                    imgFoto.setImageBitmap(BitmapFactory.decodeFile(path));
//                    //setFullImageFromFilePath(imgFoto, path);
//                }
                // if we are here, we are hearing back from the image gallery.



                // the address of the image on the SD Card.

                Uri imageUri = data.getData();



                // declare a stream to read the image data from the SD Card.

                InputStream inputStream;



                // we are getting an input stream, based on the URI of the image.

                try {

                    inputStream = getActivity().getContentResolver().openInputStream(imageUri);



                    // get a bitmap from the stream.

                    Bitmap image = BitmapFactory.decodeStream(inputStream);





                    // show the image to the user

                    imgFoto.setImageBitmap(image);
                    habilitarCamposDatos();
                    Toast.makeText(getActivity(), "Foto agregada",Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {

                    e.printStackTrace();

                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(getActivity(), "No se pudo subir la foto",Toast.LENGTH_SHORT).show();

                }



            }
    }

    private void habilitarCamposDatos(){
        camposDatos.setVisibility(View.VISIBLE);
        btnGuardarPic.setEnabled(true);
    }
}
