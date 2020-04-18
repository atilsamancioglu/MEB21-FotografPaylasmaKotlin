package com.atilsamancioglu.fotografpaylasmakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    var postList = ArrayList<Post>()
    private lateinit var recyclerViewAdapter : FeedRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        verileriAl()

        //RecyclerView

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = FeedRecyclerAdapter(postList)
        recyclerView.adapter = recyclerViewAdapter
    }

    fun verileriAl(){

        db.collection("Post").orderBy("tarih",Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->

            if (exception != null) {
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if (snapshot != null) {
                    if(!snapshot.isEmpty) {

                        val documents = snapshot.documents

                        postList.clear()

                        for (document in documents) {

                            val kullaniciEmail = document.get("kullaniciemail") as String
                            val kullaniciYorum = document.get("kullaniciyorum") as String
                            val gorselUrl = document.get("gorselurl") as String
                            val timestamp = document.get("tarih") as Timestamp
                            val tarih = timestamp.toDate()

                            val indirilenPost = Post(kullaniciEmail,kullaniciYorum,gorselUrl)
                            postList.add(indirilenPost)

                        }

                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.options_menu_fotograf,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fotograf_ekle) {
            val intent = Intent(applicationContext,FotografActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.cikis_yap) {
            auth.signOut()
            val intent = Intent(applicationContext,KullaniciActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}