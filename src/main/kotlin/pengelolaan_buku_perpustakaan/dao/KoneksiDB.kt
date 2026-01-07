package pengelolaan_buku_perpustakaan.dao

import java.sql.Connection
import java.sql.DriverManager

object KoneksiDB {

    private const val URL =
        "jdbc:mysql://127.0.0.1:3306/pengelolaan_buku_perpustakaan?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Jakarta"
    private const val USER = "root"
    private const val PASSWORD = ""

    fun getConnection(): Connection {
        Class.forName("com.mysql.cj.jdbc.Driver")
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}