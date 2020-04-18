package com.atilsamancioglu.fotografpaylasmakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class KullaniciActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        val guncelKullanici = auth.currentUser

        if(guncelKullanici != null) {
            val intent = Intent(applicationContext,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun girisYap(view: View) {

        auth.signInWithEmailAndPassword(emailText.text.toString(),sifreText.text.toString()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext,"Hoşgeldin: ${auth.currentUser?.email.toString()}",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }

    fun kayitOl(view: View){
        val email = emailText.text.toString()
        val sifre = sifreText.text.toString()

        auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener { task ->
            //asenkron
            if(task.isSuccessful){
                //diğer aktiviteye git
                val intent = Intent(applicationContext,FeedActivity::class.java)
                startActivity(intent)
                finish()

            }
        }.addOnFailureListener { exception ->
            if (exception != null){
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }
}