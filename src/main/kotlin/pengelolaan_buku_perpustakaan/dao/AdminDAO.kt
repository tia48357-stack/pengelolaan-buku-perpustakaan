package pengelolaan_buku_perpustakaan.dao

import pengelolaan_buku_perpustakaan.model.Admin
import java.sql.Connection
import java.sql.DriverManager

class AdminDAO {

    private val url = "jdbc:mysql://localhost:3306/pengelolaan_buku_perpustakaan"
    private val user = "root"
    private val pass = ""

    private fun getConnection(): Connection {
        return DriverManager.getConnection(url, user, pass)
    }

    fun login(username: String, password: String): Boolean {
        val sql = "SELECT * FROM admin WHERE username=? AND password=?"

        getConnection().use { conn ->
            conn.prepareStatement(sql).use { ps ->
                ps.setString(1, username)
                ps.setString(2, password)
                val rs = ps.executeQuery()
                return rs.next()
            }
        }
    }
}