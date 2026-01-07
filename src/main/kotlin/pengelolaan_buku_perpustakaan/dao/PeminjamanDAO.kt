package pengelolaan_buku_perpustakaan.dao

import pengelolaan_buku_perpustakaan.model.Peminjaman
import java.sql.Connection
import java.sql.DriverManager

class PeminjamanDAO {

    // ================= GET ALL =================
    fun getAll(): List<Peminjaman> {
        val list = mutableListOf<Peminjaman>()
        val sql = "SELECT * FROM peminjaman ORDER BY id_peminjaman ASC"

        val conn = KoneksiDB.getConnection()
        val stmt = conn.prepareStatement(sql)
        val rs = stmt.executeQuery()

        while (rs.next()) {
            list.add(
                Peminjaman(
                    id_peminjaman = rs.getInt("id_peminjaman"),
                    nama_peminjam = rs.getString("nama_peminjam"),
                    kelas = rs.getString("kelas"),
                    id_buku = rs.getInt("id_buku"),
                    tanggal_pinjam = rs.getString("tanggal_pinjam"),
                    tanggal_kembali = rs.getString("tanggal_kembali"),
                    status = rs.getString("status")
                )
            )
        }

        rs.close()
        stmt.close()
        conn.close()

        return list
    }

    // ================= INSERT =================
    fun insert(p: Peminjaman) {
        val sql = """
            INSERT INTO peminjaman 
            (id_peminjaman, nama_peminjam, kelas, id_buku, tanggal_pinjam, tanggal_kembali, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """

        val conn = KoneksiDB.getConnection()
        val stmt = conn.prepareStatement(sql)

        stmt.setInt(1, p.id_peminjaman)
        stmt.setString(2, p.nama_peminjam)
        stmt.setString(3, p.kelas)
        stmt.setInt(4, p.id_buku)
        stmt.setString(5, p.tanggal_pinjam)
        stmt.setString(6, p.tanggal_kembali)
        stmt.setString(7, p.status)

        stmt.executeUpdate()

        stmt.close()
        conn.close()
    }

    // ================= UPDATE =================
    fun update(p: Peminjaman) {
        val sql = """
            UPDATE peminjaman SET
                nama_peminjam = ?,
                kelas = ?,
                id_buku = ?,
                tanggal_pinjam = ?,
                tanggal_kembali = ?,
                status = ?
            WHERE id_peminjaman = ?
        """

        val conn = KoneksiDB.getConnection()
        val stmt = conn.prepareStatement(sql)

        stmt.setString(1, p.nama_peminjam)
        stmt.setString(2, p.kelas)
        stmt.setInt(3, p.id_buku)
        stmt.setString(4, p.tanggal_pinjam)
        stmt.setString(5, p.tanggal_kembali)
        stmt.setString(6, p.status)
        stmt.setInt(7, p.id_peminjaman)

        stmt.executeUpdate()

        stmt.close()
        conn.close()
    }

    // ================= DELETE =================
    fun delete(id: Int) {
        val sql = "DELETE FROM peminjaman WHERE id_peminjaman = ?"

        val conn = KoneksiDB.getConnection()
        val stmt = conn.prepareStatement(sql)

        stmt.setInt(1, id)
        stmt.executeUpdate()

        stmt.close()
        conn.close()
    }
}