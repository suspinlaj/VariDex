import com.example.harrypotter.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // "http://localhost:9393/api"
    private const val BASE_URL = "http://10.0.2.2:9393/api/" //10.0.2.2 para emulador

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
