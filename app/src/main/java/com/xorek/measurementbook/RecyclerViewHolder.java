package com.xorek.measurementbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MEA on 7/20/2017.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public TextView name, userName, id1;
    public ImageView img;
    private Context cont;
    private long rowId;
    ProgressDialog progressDialog;





    public RecyclerViewHolder(View itemView, Context context) {
        super(itemView);

        name=(TextView)itemView.findViewById(R.id.headLine);
        img = (ImageView)itemView.findViewById(R.id.img);
        userName = (TextView)itemView.findViewById(R.id.details);
        id1 = (TextView)itemView.findViewById(R.id.rid);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        cont = context;
       // rowId = id;

        progressDialog = new ProgressDialog(cont, R.style.MyDialog);



    }

    @Override
    public void onClick(View view) {

        rowId =Long.parseLong(id1.getText().toString());
        Intent in = new Intent(cont, Profilepro.class);
        in.putExtra("id", rowId);
        progressDialog.dismiss();
        progressDialog.setTitle("Client Details");
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
//        progressDialog.dismiss();
        progressDialog.show();
        cont.startActivity(in);
//        progressDialog.dismiss();
        android.os.Handler h = new android.os.Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {

            progressDialog.dismiss();
            }

    },2000);
    }


    @Override
    public boolean onLongClick(View view) {
        rowId =Long.parseLong(id1.getText().toString());
        String  nam = "This would take <b>"+name.getText().toString()+"</b> off your client list.";

       final AlertDialog a = new AlertDialog.Builder(cont, R.style.MyDialog)
                .setMessage(Html.fromHtml(nam))
                .setTitle("Delete Client?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // ok button
                        DbHandler db = new DbHandler(cont);
                        db.deleteCon(rowId);
                        Intent in = new Intent(cont, MeasurementBook.class);
                        cont.startActivity(in);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel button

                    }
                }).create();
//        //You have to use below line, otherwise you will get "Unable to add window -- token null is not for an application"
//        a.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        a.setCanceledOnTouchOutside(false);

        a.show();

        return true;
    }


}