package kr.co.yeaeun.myapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

class GunplaViewModel(application: Application) : AndroidViewModel(application) {
    companion object{
        const val QUEUE_TAG = "VolloeyRequest"
    }

    private lateinit var mQueue: RequestQueue
    data class Mechanic(
        val id: Int,
        val name: String,
        val model: String,
        val manufacturer: String,
        val armor: String,
        val height: Double,
        val weight: Double
    )

    val list = MutableLiveData<ArrayList<Mechanic>>()
    private val gunpla = ArrayList<Mechanic>()

    init {
        list.value = gunpla
        // singleton으로 VolleyRequest를 만들어 사용
        // application = this.applicationContext
        mQueue = VolleyRequest.getInstance(application).requestQueue
    }

    override fun onCleared() { // 요청 중지
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }

    fun getGunpla(i: Int) = gunpla[i]

    fun getSize() = gunpla.size



    fun requestMechanic() {
        // NOTE: 서버 주소는 본인의 서버 주소 사용할 것
        val url = "https://dbex-volley-server-vraal.run.goorm.io/gunpladb/mechanic"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                //binding.result.text = it.toString()
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                gunpla.clear() // 중복방지
                parseMechanicJSON(it) // 셋팅
                list.value = gunpla
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                //binding.result.text = it.toString()
            }
        )

        request.tag = QUEUE_TAG
        mQueue.add(request)
    }
    private fun parseMechanicJSON(items: JSONArray) {
        for (i in 0 until items.length()) {
            val item: JSONObject = items.getJSONObject(i)
            val id = item.getInt("id")
            val name = item.getString("name")
            val model = item.getString("model")
            val manufacturer = item.getString("manufacturer")
            val armor = item.getString("armor")
            val height = item.getDouble("height")
            val weight = item.getDouble("weight")

            gunpla.add(Mechanic(id, name, model, manufacturer, armor,
                height, weight))
        }
    }
}