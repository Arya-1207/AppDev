import com.google.gson.annotations.SerializedName

data class PixabayResponse(
    @SerializedName("hits") val hits: List<ImageItem>
)

data class ImageItem(
    @SerializedName("id") val id: Int,
    @SerializedName("previewURL") val previewUrl: String,
    @SerializedName("largeImageURL") val largeImageUrl: String
)
