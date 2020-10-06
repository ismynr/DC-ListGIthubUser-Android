package com.ismynr.githubuserlist

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataUsername: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataAvatar: TypedArray
    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.lv_user)
        adapter = UserAdapter(this)
        listView.adapter = adapter

        prepare()
        addItem()

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val user = User(
                users[position].name,
                users[position].username,
                users[position].followers,
                users[position].following,
                users[position].location,
                users[position].repository,
                users[position].company,
                users[position].avatar
            )

            val moveWithObjectIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
            moveWithObjectIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
            startActivity(moveWithObjectIntent)
        }
    }

    private fun prepare(){
        dataName = resources.getStringArray(R.array.name)
        dataUsername = resources.getStringArray(R.array.username)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
    }

    private fun addItem(){
        for (position in dataName.indices){
            val user = User(
                dataName[position],
                dataUsername[position],
                dataFollowers[position],
                dataFollowing[position],
                dataLocation[position],
                dataRepository[position],
                dataCompany[position],
                dataAvatar.getResourceId(position, -1)
            )
            users.add(user)
        }

        adapter.users = users
    }

}