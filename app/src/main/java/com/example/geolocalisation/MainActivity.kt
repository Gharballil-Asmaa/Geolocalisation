package com.example.geolocalisation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    //Composants
    lateinit var bt_location: Button
    lateinit var textView1:TextView
    lateinit var textView2 : TextView
    lateinit var textView3: TextView
    lateinit var textView4 : TextView
    lateinit var textView5 :TextView

    //objet
    var fs: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialisation des composants
        bt_location =findViewById(R.id.bt_location)
        textView1 = findViewById(R.id.text_view1)
        textView2 = findViewById(R.id.text_view2)
        textView3 = findViewById(R.id.text_view3)
        textView4 = findViewById(R.id.text_view4)
        textView5 = findViewById(R.id.text_view5)

        fs = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fs!!.getLastLocation().addOnSuccessListener(OnSuccessListener<Location?> { location ->
                if (location != null) {
                    try {
                        //initialiser geocoder
                        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                        //initialiser l’adresse de localisation
                        val addresses =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        //afficher la latitude dans le textview
                        textView1.text = Html.fromHtml(
                            "<font color='#6200EE'><b>Latitude : </b><br></font>"
                                    + addresses!![0].latitude
                        )
                        // afficher la longitude dans le textview
                        textView2.text = Html.fromHtml(
                            (
                                    "<font color='#6200EE'><b>Longitude : </b><br></font>"
                                            + addresses[0].longitude
                                    )
                        )
                        // afficher le pays dans le textview
                        textView3.text = Html.fromHtml(
                            (
                                    "<font color='#6200EE'><b>Nom de pays : </b><br></font>"
                                            + addresses[0].countryName
                                    )
                        )
                        // afficher la localité dans le textview
                        textView4.text = Html.fromHtml(
                            (
                                    "<font color='#6200EE'><b>Localité : </b><br></font>"
                                            + addresses[0].locality
                                    )
                        )
                        // afficher l’address dans le textview
                        textView5.text = Html.fromHtml(
                            (
                                    "<font color='#6200EE'><b>Adresse : </b><br></font>"
                                            + addresses[0].getAddressLine(0)
                                    )
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(
                        applicationContext, "Aucune position enregistrée",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        fun getLocation() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    44
                )
            } else {
                fs!!.getLastLocation()
                    .addOnFailureListener(OnFailureListener { e ->
                        Toast.makeText(
                            applicationContext,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
            }
        }

    }
}