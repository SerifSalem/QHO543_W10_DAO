import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

/**
 * DAO implementation using Exposed.
 * This is the ONLY class that needs to know about Exposed and the table schema.
 */
class ExposedWadsongsDao : WadsongsDao {

    override fun insertSong(song: Song): Int {
        var newId = 0

        transaction {
            // Insert returns a ResultRow-like object; we retrieve generated id.
            newId = Wadsongs.insert {
                it[title] = song.title
                it[artist] = song.artist
                it[year] = song.year
            }[Wadsongs.id]
        }

        // Important: update the provided entity to match the DB-assigned id.
        song.id = newId
        return newId
    }

    override fun findAllSongs(): List<Song> {
        var songs = emptyList<Song>()

        transaction {
            songs = Wadsongs.selectAll()
                .map {
                    Song(
                        id = it[Wadsongs.id],
                        title = it[Wadsongs.title],
                        artist = it[Wadsongs.artist],
                        year = it[Wadsongs.year]
                    )
                }
        }

        return songs
    }

    override fun findSongById(id: Int): Song? {
        var song: Song? = null

        transaction {
            // singleOrNull() returns exactly one row or null.
            val resultRow = Wadsongs.selectAll()
                .where { Wadsongs.id eq id }
                .singleOrNull()

            // Use ?.let to convert only if resultRow is not null.
            resultRow?.let {
                song = Song(
                    id = it[Wadsongs.id],
                    title = it[Wadsongs.title],
                    artist = it[Wadsongs.artist],
                    year = it[Wadsongs.year]
                )
            }
        }

        return song
    }

    override fun updateSong(modifiedSong: Song): Int {
        var nUpdated = 0

        transaction {
            // update returns number of affected rows
            nUpdated = Wadsongs.update({ Wadsongs.id eq modifiedSong.id }) {
                it[title] = modifiedSong.title
                it[artist] = modifiedSong.artist
                it[year] = modifiedSong.year
            }
        }

        return nUpdated
    }

    override fun deleteSong(id: Int): Int {
        var nDeleted = 0

        transaction {
            // deleteWhere returns number of deleted rows
            nDeleted = Wadsongs.deleteWhere { Wadsongs.id eq id }
        }

        return nDeleted
    }
}
