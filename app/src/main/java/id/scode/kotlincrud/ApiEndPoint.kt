package id.scode.kotlincrud

/**
 * Created by Dani on 1/2/2019.
 * Yogi Arif Widodo
 * static variabel untuk menampung sebuah url dari RestAPI.
 */

class ApiEndPoint
{
    companion object {
        @JvmStatic
        private val SERVER = "http://willy-nilly-wagons.000webhostapp.com/image/"
        @JvmStatic
        val CREATE = SERVER+"create.php"
        val READ = SERVER+"read.php"
        val UPDATE = SERVER+"update.php"
        val DELETE = SERVER+"delete.php"

        private const val SERVER_IMG = "http://willy-nilly-wagons.000webhostapp.com/image_api/"

        const val RIMG= SERVER_IMG+"apImage.php"

        private const val SERVER_DATA = "http://willy-nilly-wagons.000webhostapp.com/areport/"
        const val DATAVIEW = SERVER_DATA+"read.php"

    }
}