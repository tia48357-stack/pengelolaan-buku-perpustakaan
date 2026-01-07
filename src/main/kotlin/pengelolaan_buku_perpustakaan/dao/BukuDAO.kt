package pengelolaan_buku_perpustakaan.dao

import pengelolaan_buku_perpustakaan.model.Buku
import java.sql.Connection
import java.sql.DriverManager

class BukuDAO {

    private val url = "jdbc:mysql://localhost:3306/pengelolaan_buku_perpustakaan"
    private val user = "root"
    private val pass = ""

    private fun getConnection(): Connection =
        DriverManager.getConnection(url, user, pass)

    fun getAll(): List<Buku> {
        val list = mutableListOf<Buku>()
        val sql = "SELECT * FROM buku"

        getConnection().use { conn ->
            conn.prepareStatement(sql).use { ps ->
                val rs = ps.executeQuery()
                while (rs.next()) {
                    list.add(
                        Buku(
                            id_buku = rs.getInt("id_buku"),
                            judul_buku = rs.getString("judul_buku"),
                            pengarang = rs.getString("pengarang"),
                            penerbit = rs.getString("penerbit"),
                            tahun_terbit = rs.getInt("tahun_terbit"),
                            kategori = rs.getString("kategori"),
                            stok = rs.getInt("stok")
                        )
                    )
                }
            }
        }
        return list
    }

    fun search(keyword: String): List<Buku> {
        val list = mutableListOf<Buku>()
        val sql = """
            SELECT * FROM buku
            WHERE judul_buku LIKE ?
               OR pengarang LIKE ?
               OR penerbit LIKE ?
               OR kategori LIKE ?
        """.trimIndent()

        getConnection().use { conn ->
            conn.prepareStatement(sql).use { ps ->
                val key = "%$keyword%"
                ps.setString(1, key)
                ps.setString(2, key)
                ps.setString(3, key)
                ps.setString(4, key)

                val rs = ps.executeQuery()
                while (rs.next()) {
                    list.add(
                        Buku(
                            id_buku = rs.getInt("id_buku"),
                            judul_buku = rs.getString("judul_buku"),
                            pengarang = rs.getString("pengarang"),
                            penerbit = rs.getString("penerbit"),
                            tahun_terbit = rs.getInt("tahun_terbit"),
                            kategori = rs.getString("kategori"),
                            stok = rs.getInt("stok")
                        )
                    )
                }
            }
        }
        return list
    }

    fun insert(b: Buku) {
        val sql = """
            INSERT INTO buku VALUES (?,?,?,?,?,?,?)
        """.trimIndent()

        getConnection().use { conn ->
            conn.prepareStatement(sql).use { ps ->
                ps.setInt(1, b.id_buku)
                ps.setString(2, b.judul_buku)
                ps.setString(3, b.pengarang)
                ps.setString(4, b.penerbit)
                ps.setInt(5, b.tahun_terbit)
                ps.setString(6, b.kategori)
                ps.setInt(7, b.stok)
                ps.executeUpdate()
            }
        }
    }

    fun update(b: Buku) {
        val sql = """
            UPDATE buku SET
            judul_buku=?, pengarang=?, penerbit=?,
            tahun_terbit=?, kategori=?, stok=?
            WHERE id_buku=?
        """.trimIndent()

        getConnection().use { conn ->
            conn.prepareStatement(sql).use { ps ->
                ps.setString(1, b.judul_buku)
                ps.setString(2, b.pengarang)
                ps.setString(3, b.penerbit)
                ps.setInt(4, b.tahun_terbit)
                ps.setString(5, b.kategori)
                ps.setInt(6, b.stok)
                ps.setInt(7, b.id_buku)
                ps.executeUpdate()
            }
        }
    }

    fun delete(id: Int) {
        val sql = "DELETE FROM buku WHERE id_buku=?"
        getConnection().use { conn ->
            conn.prepareStatement(sql).use { ps ->
                ps.setInt(1, id)
                ps.executeUpdate()
            }
        }
    }
}