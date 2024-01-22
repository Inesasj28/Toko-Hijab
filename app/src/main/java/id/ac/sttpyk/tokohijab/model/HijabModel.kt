package id.ac.sttpyk.tokohijab.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HijabModel(
    val `data`: List<Data>,
    val message: String,
    val response_code: Int
):Parcelable {
    @Parcelize
    data class Data(
        val created_at: String,
        val harga: Int,
        val hijab: String,
        val id: Int,
        val updated_at: String
    ):Parcelable
}