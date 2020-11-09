package com.example.etccomponent

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pedro.library.AutoPermissions.Companion.loadAllPermissions
import com.pedro.library.AutoPermissions.Companion.parsePermissions
import com.pedro.library.AutoPermissionsListener
import java.io.InputStream


class ShareDataActivity : AppCompatActivity(), AutoPermissionsListener {

    var lblContacts: TextView? = null
    var imgGallerty: ImageView? = null

    var contactBtn: Button? = null
    var galleryBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_data)

        lblContacts = findViewById<View>(R.id.lblcontacts) as TextView
        imgGallerty = findViewById<View>(R.id.imggallery) as ImageView

        contactBtn = findViewById<View>(R.id.btncontacts) as Button
        galleryBtn = findViewById<View>(R.id.btngallery) as Button

        contactBtn!!.setOnClickListener {
            val contactPickerIntent = Intent(
                Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI
            )
            startActivityForResult(contactPickerIntent, 10)
        }

        galleryBtn!!.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 20)
        }
        loadAllPermissions(this, 101)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
        parsePermissions(
            this,
            requestCode,
            permissions as Array<String>,
            this
        )
    }

    override fun onDenied(requestCode: Int, permissions: Array<String>) {
        Toast.makeText(
            this, "permissions denied : " + permissions.size,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onGranted(requestCode: Int, permissions: Array<String>) {
        Toast.makeText(this, "permissions granted : " + permissions.size, Toast.LENGTH_LONG).show()
    }

    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            try {
                val id: String? = Uri.parse(data!!.dataString).getLastPathSegment()
                val cursor: Cursor? = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    arrayOf(
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    ContactsContract.Data._ID + "=" + id,
                    null,
                    null
                )
                cursor!!.moveToFirst()
                val name: String = cursor!!.getString(0)
                val phone: String = cursor.getString(1)
                lblContacts!!.text = "$name:$phone"
                lblContacts!!.textSize = 25f
                cursor.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            val fileUri: Uri? = data!!.data
            val resolver = contentResolver
            try {
                val instream: InputStream? = resolver.openInputStream(fileUri!!)
                val imgBitmap = BitmapFactory.decodeStream(instream)
                imgGallerty!!.setImageBitmap(imgBitmap)
                instream!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}