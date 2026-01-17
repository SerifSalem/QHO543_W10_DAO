/**
 * DAO interface:
 * Defines WHAT operations the application can perform on the data source,
 * without revealing HOW they are implemented (Exposed, SQL, file, network, etc.).
 */
interface WadsongsDao {
    fun insertSong(song: Song): Int
    fun findAllSongs(): List<Song>
    fun findSongById(id: Int): Song?
    fun updateSong(modifiedSong: Song): Int
    fun deleteSong(id: Int): Int
}
