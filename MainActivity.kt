import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiKey = "43041043-d4d06b306b5afe56e075113e0"
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ImageAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(PixabayApi::class.java)
        fetchImages(api)
    }

    private fun fetchImages(api: PixabayApi) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getImages(apiKey, "nature") // Example search query: "nature"
            if (response.isSuccessful) {
                val images = response.body()?.hits ?: emptyList()
                withContext(Dispatchers.Main) {
                    adapter.updateData(images)
                }
            } else {
                // Handle error
                Log.e("API_ERROR", "Failed to fetch images: ${response.message()}")
            }
        }
    }
}
