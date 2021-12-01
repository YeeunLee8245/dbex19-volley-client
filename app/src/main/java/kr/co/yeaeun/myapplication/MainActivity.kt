package kr.co.yeaeun.myapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kr.co.yeaeun.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private val model: GunplaViewModel by viewModels() // viewModels: 결국엔 내부에 있는 viewModelProvider을 그대로 씀
    private lateinit var adapter: GunplaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Constraint Layout

        adapter = GunplaAdapter(model){mechanic -> adapterOnClick(mechanic) }
        binding.list.apply{
            layoutManager = LinearLayoutManager(applicationContext) // 직선으로 움직이겠다
            setHasFixedSize(true) // 사이즈 고정
            itemAnimator = DefaultItemAnimator()
            adapter = this@MainActivity.adapter
        }

        model.list.observe(this, {
            adapter.notifyDataSetChanged() // (ViewModel list)데이터가 변경되면 어뎁터에게 리스트를 다시 그리라고 알림
        })

        model.requestMechanic()
    }
    private fun adapterOnClick(mechanic: GunplaViewModel.Mechanic) {
        //Toast.makeText(this, mechanic.model, Toast.LENGTH_SHORT).show()
        val uri = Uri.parse("https://www.youtube.com/results?search_query=${mechanic.model}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}