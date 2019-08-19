package net.gahfy.mvvmposts.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("rows")
    val rows: List<Row>,
    @SerializedName("title")
    val title: String
) {
    data class Row(
        @SerializedName("description")
        val description: String,
        @SerializedName("imageHref")
        val imageHref: String?,
        @SerializedName("title")
        val title: String
    )
}