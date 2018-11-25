package com.mobiapp4u.pc.voters.view

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.mobiapp4u.pc.voters.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_of_on_click_fab.*
import kotlinx.android.synthetic.main.view_of_on_click_fab.view.*


class MainActivity : AppCompatActivity() {



    lateinit var dBase:SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dBase = openOrCreateDatabase("voters_db",Context.MODE_PRIVATE,null)
        dBase.execSQL("PRAGMA foreign_keys=ON;")
        dBase.execSQL("create table if not exists constituency (id integer primary key autoincrement, name varchar(200), dist varchar(200),state varchar(200))")
        dBase.execSQL("create table if not exists voter(vid varchar(25), name varchar(200),age integer, aadhar integer, phone integer,cid integer primary key autoincrement, FOREIGN KEY(cid) REFERENCES constituency(id))")


        floatingActionButton.setOnClickListener {
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Please Fill..")
//            alert.setMessage("Please Enter all details....")
            alert.setIcon(R.drawable.ic_account_circle_black_24dp)
            var view = LayoutInflater.from(this).inflate(R.layout.view_of_on_click_fab,null)

            var vAadhar = view.findViewById<EditText>(R.id.et_aadhar)
            var vAge = view.findViewById<EditText>(R.id.et_age)
            var vId = view.findViewById<EditText>(R.id.et_vid)
            var vName = view.findViewById<EditText>(R.id.et_name)
            var vPhone  =view.findViewById<EditText>(R.id.et_phone)

            var cName = view.findViewById<EditText>(R.id.et_name)
            var cDist = view.findViewById<EditText>(R.id.et_cdist)
            var cState = view.findViewById<EditText>(R.id.et_cstate)

            alert.setView(view)
            alert.setPositiveButton("Submit") { dialog, which ->
                if(vId!!.text.toString().equals("") || vName!!.text.toString().equals("") || vAge!!.text.toString().equals("") || vPhone!!.text.toString().equals("") ||
                    vAadhar!!.text.toString().equals("") ||  cName!!.text.toString().equals("") || cDist!!.text.toString().equals("") ||
                    cState!!.text.toString().equals("")){

                    Toast.makeText(this@MainActivity,"Please Fill All Fields",Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }else{
                    insertVotersData(view)

                }
                dialog.dismiss()
            }
            alert.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            alert.show()

        }

        button_search.setOnClickListener {

        }

    }

    private fun insertVotersData(view:View) {
        var vAadhar = view.findViewById<EditText>(R.id.et_aadhar)
        var vAge = view.findViewById<EditText>(R.id.et_age)
        var vId = view.findViewById<EditText>(R.id.et_vid)
        var vName = view.findViewById<EditText>(R.id.et_name)
        var vPhone  =view.findViewById<EditText>(R.id.et_phone)

        var cName = view.findViewById<EditText>(R.id.et_cname)
        var cDist = view.findViewById<EditText>(R.id.et_cdist)
        var cState = view.findViewById<EditText>(R.id.et_cstate)

        val cv = ContentValues()
        cv.put("name",cName!!.text.toString())
        cv.put("dist",cDist!!.text.toString())
        cv.put("state",cState!!.et_cstate.text.toString())
       var status =  dBase.insert("constituency",null,cv)
        if (status!=-1L){

            val cv1 = ContentValues()
            //vid integer,
            // name varchar(200),
            // age integer,
            // aadhar integer,
            // phone integer,cid integer, FOREIGN KEY(cid) REFERENCES constituency(id))")
            cv1.put("vid",vId!!.text.toString())
            cv1.put("name",vName!!.text.toString())
            cv1.put("age",vAge!!.text.toString())
            cv1.put("aadhar",vAadhar!!.text.toString())
            cv1.put("phone",vPhone!!.text.toString())

            var status1 = dBase.insert("voter",null,cv1)

            if(status1!=-1L){
                Toast.makeText(this@MainActivity,"Data Insertion SUCCESS",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@MainActivity,"Data Insertion in  FAILED",Toast.LENGTH_LONG).show()

            }

        }else{

            Toast.makeText(this@MainActivity,"Data Insertion in  FAILED",Toast.LENGTH_LONG).show()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater

        inflater.inflate(R.menu.menu_item, menu)

        return true


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.powerOf -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
