package com.chmap.kloop.confchmap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chmap.kloop.confchmap.database.ExternalDbOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by android on 09.04.2015.
 */
public class FragmentGpsSearch extends Fragment implements View.OnClickListener{

    private static Resources mRes;
    private EditText edtLat;
    private EditText edtLong;
    private LocationManager locationManager;
    private Button definePolution;
    private Button getGps;
    static HashMap<String,String> result;
    static Vector<InfAboutCity> nearCities;
    static ArrayList<String> inf;

    public static Vector<InfAboutCity> getNearCities(){return  nearCities;}
    public static HashMap<String,String> getPolution(){return result;}
    public static ArrayList<String> getInf(){return inf;}

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_gps_search, container, false);

        definePolution= (Button) rootView.findViewById(R.id.btnSearch);
        getGps = (Button) rootView.findViewById(R.id.btnGpsSearch);
        edtLat= (EditText) rootView.findViewById(R.id.edtLat);
        edtLong=(EditText) rootView.findViewById(R.id.edtLong);


        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        getGps.setOnClickListener(this);
        definePolution.setOnClickListener(this);
        mRes=getResources();
        return rootView;
    }

    public static Resources mGetRes(){return mRes;}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearch:
                BackgroundDefinePolution mt =new BackgroundDefinePolution();
                mt.execute();
                break;
            case R.id.btnGpsSearch:
                if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000 * 10, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000 * 10, 0, locationListener);
                break;
                
        }
    }

   public class BackgroundDefinePolution extends AsyncTask<Void, Void, Void> {
        String outputs;
        InfoAboutMaps CurrentInfProviderMap;
        private ExternalDbOpenHelper dbOpenHelper;
        private SQLiteDatabase dbOfCordinates;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            outputs=new String();
            result=new HashMap<String,String>();
            inf=new ArrayList<String>();

        }

        @Override
        protected Void doInBackground(Void... params) {

            CurrentInfProviderMap=new InfoAboutMaps();

            double lat,longs;
            lat=Double.parseDouble(edtLat.getText().toString());
            longs=Double.parseDouble(edtLong.getText().toString());

            CurrentInfProviderMap.InitPosition(getActivity(),lat,longs);;
            nearCities=new Vector();



            dbOpenHelper = new ExternalDbOpenHelper(getActivity(),"cordinates.sqlite3");
            dbOfCordinates = dbOpenHelper.openDataBase();
            double dlat,ulat,llong,rlong;

            dlat=lat-0.045065;
            ulat=lat+0.045065;

            llong=longs-0.075817;
            rlong=longs+0.075817;


            //заполняем векторы

            Cursor InpCursor = dbOfCordinates.query("cities", new String[] {
                    "name","lat","long" },  "("+Double.toString(dlat)+" < lat) AND ("+Double.toString(ulat)+" > lat) AND ("+Double.toString(llong)+" < long) AND ("+Double.toString(rlong)+" > long)", null, null, null, null);




            InfAboutCity buf;
            InpCursor.moveToFirst();


            if (!InpCursor.isAfterLast()) {
                do {

                    buf=new InfAboutCity();
                    buf.name=InpCursor.getString(0);
                    buf.distance=Math.sqrt(((InpCursor.getDouble(1)-lat)*111)*((InpCursor.getDouble(1)-lat)*111)+(((InpCursor.getDouble(2)-longs)*65.5)*((InpCursor.getDouble(2)-longs)*65.5)));
                    nearCities.add(buf);


                } while (InpCursor.moveToNext());
            }
            InpCursor.close();



            int colour=CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(0).colour;

            String selectLocale=CurrentInfProviderMap.getSelectLocaleName();
            inf.add(selectLocale);
            inf.add(Double.toString(lat));
            inf.add(Double.toString(longs));
            outputs=selectLocale+" область:\n";
            outputs=outputs+"CS-137:\n";
            for (int i=0;i<CurrentInfProviderMap.mAllColoursOfCurrentPosition.size();i++)
            {
                switch (CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).colour)
                {
                    case 0xffeef3c1:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"less 0.1\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"less 0.1");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: less 0.1", Toast.LENGTH_LONG).show();
                        break;
                    case 0xfffffbc8:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"0.1-0.2 ku/km2\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"0.1-0.2 ku/km2");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: 0.1-0.2 ku/km2", Toast.LENGTH_LONG).show();
                        break;
                    case 0xfffedbb4:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"0.2-0.5 ku/km\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"0.2-0.5 ku/km");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: 0.2-0.5 ku/km2", Toast.LENGTH_LONG).show();
                        break;
                    case 0xfff8b5b2:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"0.5-1 ku/km2\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"0.5-1 ku/km2");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: 0.5-1 ku/km2", Toast.LENGTH_LONG).show();
                        break;

                    case 0xffea95a4:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"1-5 ku/km2\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"1-5 ku/km2");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: 1-5 ku/km2", Toast.LENGTH_LONG).show();
                        break;

                    case 0xfff27180:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"5-15 ku/km\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"5-15 ku/km");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: 5-15 ku/km2", Toast.LENGTH_LONG).show();
                        break;

                    case 0xffd74c64:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"15-40 ku/km2\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"15-40 ku/km2");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: 15-40 ku/km2", Toast.LENGTH_LONG).show();
                        break;

                    case  0xffbe106e:
                        outputs=outputs+CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year+" год:"+"over 40 ku/km2\n";
                        result.put(Integer.toString(CurrentInfProviderMap.mAllColoursOfCurrentPosition.get(i).year),"over 40 ku/km2");
                        //Toast.makeText(MainActivity.getInstance(), selectLocale+" область:\nCS-137: over 40 ku/km2", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        outputs="Position not found";
                        break;

                }
            }

            outputs+="\nБлижайшие нас. пункты:\n";


            int min_index;
            Vector <InfAboutCity> sortNearCities =new Vector();
            for (int i=0;i<nearCities.size();i++)
            {
                min_index=i;
                for (int j=i;j<nearCities.size();j++)
                {
                    if (nearCities.get(j).distance < nearCities.get(min_index).distance)
                        min_index=j;
                }


                buf=new InfAboutCity();
                buf=nearCities.get(min_index);
                nearCities.set(min_index, nearCities.get(i));


                nearCities.set(i,buf);


            }

            for (int i=0;i<nearCities.size();i++)
                outputs=outputs+nearCities.get(i).name+": "+String.format("%1$.2f", nearCities.get(i).distance)+"km \n";

            return null;


        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

           // Toast.makeText(getActivity(), outputs, Toast.LENGTH_LONG).show();
            FragmentManager manager = getFragmentManager();
            DialogShowResult dlgResult = new DialogShowResult();
            dlgResult.show(manager, "fragment_edit_name");

            //pd.cancel
            //MainActivity.getInstance().dlg=MainActivity.getInstance().onCreateDialog(outputs,CurrentInfProviderMap.Inps);
            // mWaite.setVisibility(View.INVISIBLE);
            //mBar.setVisibility(View.INVISIBLE);
            // MainActivity.getInstance().dlg.show();

        }
    }

    @Override
    public void onPause() {
       super.onPause();
       locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                //    tvStatusGPS.setText("Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                //  tvStatusNet.setText("Status: " + String.valueOf(status));
            }
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            edtLat.setText(Double.toString(location.getLatitude()));
            edtLong.setText(Double.toString(location.getLongitude()));

            // tvLocationGPS.setText(formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
           edtLong.setText(Double.toString(location.getLongitude()));
           edtLat.setText(Double.toString(location.getLatitude()));;
        }
    }



    private void checkEnabled() {
        ;
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("GPS не включен. Желаете включить?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
