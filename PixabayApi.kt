import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("/api/")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): PixabayResponse
}
